/*
 * Copyright 2013 Cameron Beccario
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.nullschool.grains.generate;

import net.nullschool.grains.GrainSchema;
import net.nullschool.grains.GrainTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

import static net.nullschool.grains.generate.NamingPolicy.Name;
import static net.nullschool.util.ThreadTools.newDaemonThreadFactory;
import static net.nullschool.util.ThreadTools.newNamingThreadFactory;
import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-02-13<p/>
 *
 * The grain generator. Searches for schemas and generates grain/builder/factory implementations for them. This
 * class uses an executor to generate these files concurrently on multiple threads.
 *
 * @author Cameron Beccario
 */
public class GrainGenerator implements Callable<Void> {

    private static final Logger log = LoggerFactory.getLogger(GrainGenerator.class);


    private final Configuration config;
    private final NamingPolicy namingPolicy;

    /**
     * An executor task that generates the contents of one file.
     */
    private class GenerateTask implements Callable<Boolean> {

        private final GrainGeneratorDriver generator;
        private final Class<?> schema;
        private final TemplateHandle handle;
        private final Path out;

        private GenerateTask(GrainGeneratorDriver generator, Class<?> schema, TemplateHandle handle, Path out) {
            this.generator = generator;
            this.schema = schema;
            this.handle = handle;
            this.out = out;
        }

        private boolean contentsIdentical(byte[] bytes, Path filename) throws IOException {
            return
                Files.exists(filename) &&
                    Files.size(filename) == bytes.length &&
                    Arrays.equals(Files.readAllBytes(filename), bytes);
        }

        /**
         * Writes the result of code generation to disk as a file with the specified name.
         *
         * @param result the generated code.
         * @param filename the file to create.
         * @return true if file write succeeded.
         * @throws IOException if a filesystem error occurs.
         */
        private boolean write(GenerationResult result, Path filename) throws IOException {
            if (!result.getErrors().isEmpty()) {
                // Code generation did not succeed, so would be incorrect to write the result to disk.
                log.error("[{}] While generating {}:", Thread.currentThread().getName(), filename);
                for (String error : result.getErrors()) {
                    log.error("[{}]    {}", Thread.currentThread().getName(), error);
                }
                throw new RuntimeException("Failed to write: " + filename);
            }

            // Encode the result with the requested charset.
            byte[] bytes = result.getText().getBytes(config.getCharset());

            // Compare with what's already on disk, if anything. Let's not rewrite the file if its contents are
            // identical. This avoids sending noise to file system listeners.
            if (contentsIdentical(bytes, filename)) {
                log.debug("[{}] Unchanged: {}", Thread.currentThread().getName(), filename);
                return false;
            }
            else {
                log.debug("[{}] Generating {}", Thread.currentThread().getName(), filename);
                Files.write(filename, bytes);
                return true;
            }
        }

        @Override public Boolean call() throws IOException {
            GenerationResult result = generator.generate(schema, handle);
            return write(result, out);
        }
    }

    private String filename(Class<?> schema, Name name) {
        return namingPolicy.getSimpleName(schema, name) + ".java";
    }

    private List<GenerateTask> configureTasks(Class<?> schema, GrainGeneratorDriver generator)
        throws ClassNotFoundException, IOException, IntrospectionException {

        Path outputDirectory = config.getOutput().resolve(GrainTools.targetPackageOf(schema).replace('.', '/'));
        Files.createDirectories(outputDirectory);
        List<GenerateTask> tasks = new ArrayList<>();

        // Create a task for each of the three files to generate.
        tasks.add(
            new GenerateTask(
                generator,
                schema,
                TemplateHandles.newFactoryEnumTemplate(config),
                outputDirectory.resolve(filename(schema, Name.factory))));  // FooFactory.java
        tasks.add(
            new GenerateTask(
                generator,
                schema,
                TemplateHandles.newGrainInterfaceTemplate(config),
                outputDirectory.resolve(filename(schema, Name.grain))));  // FooGrain.java
        tasks.add(
            new GenerateTask(
                generator,
                schema,
                TemplateHandles.newBuilderInterfaceTemplate(config),
                outputDirectory.resolve(filename(schema, Name.builder))));  // FooBuilder.java
        return tasks;
    }

    public GrainGenerator(Configuration configuration) {
        this.config = Objects.requireNonNull(configuration);
        this.namingPolicy = new NamingPolicy();
    }

    @Override public Void call() throws Exception {
        long start = System.currentTimeMillis();

        // Create an executor.
        ExecutorService executor =
            Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors(),
                newDaemonThreadFactory(newNamingThreadFactory("%s", Executors.defaultThreadFactory())));

        GrainGeneratorDriver generator = new GrainGeneratorDriver(config, namingPolicy);
        List<GenerateTask> tasks = new ArrayList<>();

        // Search for grain schemas.
        Set<Class<?>> schemas = new HashSet<>();
        for (String searchPackage : config.getSearchPackages()) {
            schemas.addAll(
                new Reflector(searchPackage).findClassesAnnotatedWith(GrainSchema.class, config.getSearchLoader()));
        }

        // For each schema found, create a bunch of generate tasks and submit them to the executor.
        for (Class<?> schema : schemas) {
            tasks.addAll(configureTasks(schema, generator));
        }

        // Wait for all tasks to finish.
        int count = 0;
        for (Future<Boolean> future : executor.invokeAll(tasks)) {
            if (future.get()) {
                count++;
            }
        }

        // Done!
        log.info(
            "Generation took {} ms.{}",
            System.currentTimeMillis() - start,
            count > 0 ? " Generated " + count + " files." : " Nothing to generate - all sources up to date.");
        executor.shutdown();
        return null;
    }

    /**
     * Used only for testing.
     */
    public static void main(String[] args) throws Exception {
        Thread.currentThread().setName("main");
        Configuration config = new Configuration();
        config.setOutput(Paths.get(args[0]));
        config.setSearchPackages(asSet(Arrays.asList(args).subList(1, args.length)));
        new GrainGenerator(config).call();
        Thread.sleep(10); // yeah, just so logging catches up.
    }
}
