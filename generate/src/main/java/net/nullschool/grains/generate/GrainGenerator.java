package net.nullschool.grains.generate;

import net.nullschool.collect.basic.BasicConstSet;
import net.nullschool.grains.GrainSchema;
import net.nullschool.grains.GrainTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static net.nullschool.util.ThreadTools.newDaemonThreadFactory;
import static net.nullschool.util.ThreadTools.newNamingThreadFactory;
import static net.nullschool.grains.generate.NamingPolicy.Name;

/**
 * 2013-02-13<p/>
 *
 * @author Cameron Beccario
 */
public class GrainGenerator implements Callable<Void> {

    private static final Logger log = LoggerFactory.getLogger(GrainGenerator.class);


    private final Configuration config;
    private final NamingPolicy namingPolicy;

    private class GenerateTask implements Callable<Boolean> {

        private final GrainGeneratorDriver generator;
        private final Class<?> schema;
        private final Template template;
        private final Path out;

        private GenerateTask(GrainGeneratorDriver generator, Class<?> schema, Template template, Path out) {
            this.generator = generator;
            this.schema = schema;
            this.template = template;
            this.out = out;
        }

        private boolean write(GenerationResult result, Path out) throws IOException {
            if (!result.getErrors().isEmpty()) {
                log.error("[{}] While generating {}:", Thread.currentThread().getName(), out);
                for (String error : result.getErrors()) {
                    log.error("[{}]    {}", Thread.currentThread().getName(), error);
                }
                throw new RuntimeException("Failed to write: " + out);
            }

            byte[] bytes = result.getText().getBytes(config.getCharset());
            if (Files.exists(out) && Files.size(out) == bytes.length && Arrays.equals(Files.readAllBytes(out), bytes)) {
                log.debug("[{}] Unchanged: {}", Thread.currentThread().getName(), out);
                return false;
            }
            else {
                log.debug("[{}] Generating {}", Thread.currentThread().getName(), out);
                Files.write(out, bytes);
                return true;
            }
        }

        @Override public Boolean call() throws IOException {
            GenerationResult result = generator.generate(schema, template);
            return write(result, out);
        }
    }

    private String filename(Class<?> schema, Name name) {
        return namingPolicy.getSimpleName(schema, name) + ".java";
    }

    private List<GenerateTask> configureTasks(Class<?> schema, GrainGeneratorDriver generator)
        throws ClassNotFoundException, IOException, IntrospectionException {

        Path target = config.getOutput().resolve(GrainTools.targetPackageOf(schema).replace('.', '/'));
        Files.createDirectories(target);
        List<GenerateTask> tasks = new ArrayList<>();

        tasks.add(new GenerateTask(generator, schema, Templates.newFactoryEnumTemplate(config), target.resolve(filename(schema, Name.factory))));
        tasks.add(new GenerateTask(generator, schema, Templates.newGrainInterfaceTemplate(config), target.resolve(filename(schema, Name.grain))));
        tasks.add(new GenerateTask(generator, schema, Templates.newBuilderInterfaceTemplate(config), target.resolve(filename(schema, Name.builder))));
        return tasks;
    }

    public GrainGenerator(Configuration configuration) {
        this.config = Objects.requireNonNull(configuration);
        this.namingPolicy = new NamingPolicy();
    }

    @Override public Void call() throws Exception {
        long start = System.currentTimeMillis();

        ExecutorService executor =
            Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors(),
                newDaemonThreadFactory(newNamingThreadFactory("%s", Executors.defaultThreadFactory())));

        GrainGeneratorDriver generator = new GrainGeneratorDriver(config, namingPolicy);
        List<GenerateTask> tasks = new ArrayList<>();

        Set<Class<?>> schemas = new HashSet<>();
        for (String searchPackage : config.getSearchPackages()) {
            schemas.addAll(
                new Reflector(searchPackage).findClassesAnnotatedWith(GrainSchema.class, config.getSearchLoader()));
        }

        for (Class<?> schema : schemas) {
            tasks.addAll(configureTasks(schema, generator));
        }

        int count = 0;
        for (Future<Boolean> future : executor.invokeAll(tasks)) {
            if (future.get()) {
                count++;
            }
        }

        log.info(
            "Generation took {} ms.{}",
            System.currentTimeMillis() - start,
            count > 0 ? " Generated " + count + " files." : " Nothing to generate - all sources up to date.");
        executor.shutdown();
        return null;
    }

    public static void main(String[] args) throws Exception {
        Thread.currentThread().setName("main");
        Configuration config = new Configuration();
        config.setOutput(Paths.get(args[0]));
        config.setSearchPackages(BasicConstSet.asSet(Arrays.asList(args).subList(1, args.length)));
        new GrainGenerator(config).call();
        Thread.sleep(10); // yeah, just so logging catches up.
    }
}
