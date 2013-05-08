package net.nullschool.grains.generate;

import net.nullschool.collect.basic.BasicConstSet;
import net.nullschool.grains.GrainSchema;
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


/**
 * 2013-02-13<p/>
 *
 * @author Cameron Beccario
 */
public class GrainGenerator implements Callable<Void> {

    private static final Logger log = LoggerFactory.getLogger(GrainGenerator.class);


    private final Configuration config;

    private class GenerateTask implements Callable<Void> {

        private final GrainGeneratorDriver generator;
        private final Class<?> schema;
        private final Path targetDir;
        private final Template template;
        private final String suffix;

        private GenerateTask(
            GrainGeneratorDriver generator,
            Class<?> schema,
            Path targetDir,
            Template template,
            String suffix) {

            this.generator = generator;
            this.schema = schema;
            this.targetDir = targetDir;
            this.template = template;
            this.suffix = suffix;
        }

        private void write(GenerationResult result, Path out) throws IOException {
            byte[] bytes = result.getText().getBytes(config.getCharset());
            if (Files.exists(out) && Files.size(out) == bytes.length && Arrays.equals(Files.readAllBytes(out), bytes)) {
                log.info("[{}] Skipping {}", Thread.currentThread().getName(), out);
            }
            else {
                log.info("[{}] +Writing {}", Thread.currentThread().getName(), out);
                Files.write(out, bytes);
            }
            for (String error : result.getErrors()) {
                log.error("[{}]     ERROR: {}", Thread.currentThread().getName(), error);
            }
        }

        @Override public Void call() throws Exception {
            GenerationResult result = generator.generate(schema, template);
            write(result, targetDir.resolve(schema.getSimpleName() + suffix));
            return null;
        }
    }

    private List<GenerateTask> configureTasks(Class<?> schema, GrainGeneratorDriver generator)
        throws ClassNotFoundException, IOException, IntrospectionException {

        Path target = config.getOutput().resolve(Types.targetPackage(schema).replace('.', '/'));
        Files.createDirectories(target);
        List<GenerateTask> tasks = new ArrayList<>();
        tasks.add(new GenerateTask(generator, schema, target, Templates.newFactoryEnumTemplate(config), "Factory.java"));
        tasks.add(new GenerateTask(generator, schema, target, Templates.newGrainInterfaceTemplate(config), "Grain.java"));
        tasks.add(new GenerateTask(generator, schema, target, Templates.newBuilderInterfaceTemplate(config), "Builder.java"));
        return tasks;
    }

    public GrainGenerator(Configuration configuration) {
        this.config = Objects.requireNonNull(configuration);
    }

    @Override public Void call() throws Exception {
        long start = System.currentTimeMillis();

        ExecutorService executor =
            Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors(),
                newDaemonThreadFactory(newNamingThreadFactory("%s", Executors.defaultThreadFactory())));

        GrainGeneratorDriver generator = new GrainGeneratorDriver(config);
        List<GenerateTask> tasks = new ArrayList<>();

        Set<Class<?>> schemas = new HashSet<>();
        for (String searchPackage : config.getSearchPackages()) {
            schemas.addAll(
                new Reflector(searchPackage).findClassesAnnotatedWith(GrainSchema.class, config.getSearchLoader()));
        }

        for (Class<?> schema : schemas) {
            tasks.addAll(configureTasks(schema, generator));
        }

        for (Future<Void> future : executor.invokeAll(tasks)) {
            future.get();
        }

        log.info("Generation took {} ms", System.currentTimeMillis() - start);
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
