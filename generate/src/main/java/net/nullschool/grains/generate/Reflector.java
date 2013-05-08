package net.nullschool.grains.generate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;


/**
 * 2013-03-01<p/>
 *
 * maven build of Sample
 * jar:file:/Users/cambecc/code/grain/plugin/target/grain-plugin-0.9.0-SNAPSHOT.jar!/net/nullschool
 * jar:file:/Users/cambecc/code/grain/generator/target/grain-generator-0.9.0-SNAPSHOT.jar!/net/nullschool
 * jar:file:/Users/cambecc/code/grain/runtime/target/grain-runtime-0.9.0-SNAPSHOT.jar!/net/nullschool
 *
 * maven build of Generator
 * file:/Users/cambecc/code/grain/generator/target/test-classes/net/nullschool
 * file:/Users/cambecc/code/grain/generator/target/classes/net/nullschool
 * jar:file:/Users/cambecc/code/grain/runtime/target/grain-runtime-0.9.0-SNAPSHOT.jar!/net/nullschool
 *
 * intellij invocation of Generator
 * file:/Users/cambecc/code/grain/generator/target/test-classes/net/nullschool
 * file:/Users/cambecc/code/grain/generator/target/classes/net/nullschool
 * file:/Users/cambecc/code/grain/runtime/target/classes/net/nullschool
 *
 * @author Cameron Beccario
 */
final class Reflector {

    private static final Logger log = LoggerFactory.getLogger(Reflector.class);

    private final String packageName;

    Reflector(String packageName) {
        this.packageName = Objects.requireNonNull(packageName);
    }

    interface Action<T> {
        T invoke(Path path) throws IOException;
    }

    private static <T> T process(URI uri, Action<T> action) throws IOException {
        try {
            return action.invoke(Paths.get(uri));
        }
        catch (FileSystemNotFoundException e) {
            Map<String, ?> env = Collections.emptyMap();
            try (FileSystem fs = FileSystems.newFileSystem(uri, env, Thread.currentThread().getContextClassLoader())) {
                return action.invoke(fs.provider().getPath(uri));
            }
        }
    }

    private static class Visitor implements FileVisitor<Path> {

        private final Path base;
        private final Set<String> classes = new LinkedHashSet<>();

        private Visitor(Path base) {
            this.base = base;
        }

        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attributes) throws IOException {
            log.debug("Visiting directory: {}", dir);
            return FileVisitResult.CONTINUE;
        }

        public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
            log.debug("Visiting file: {}", file);
            StringBuilder sb = new StringBuilder();
            file = base.relativize(file);
            if (file.getNameCount() > 0) {
                sb.append(file.getName(0));
                for (int i = 1; i < file.getNameCount(); i++) {
                    sb.append('.').append(file.getName(i));
                }
            }
            String name = sb.toString();
            if (name.endsWith(".class")) {
                classes.add(name.substring(0, name.length() - 6));
            }
            return FileVisitResult.CONTINUE;
        }

        public FileVisitResult visitFileFailed(Path file, IOException e) throws IOException {
            if (e instanceof FileSystemLoopException) {
                log.warn("Encountered cycle: {}", file, e);
            }
            else {
                log.error("Failed to visit file: {}", file, e);
            }
            return FileVisitResult.CONTINUE;
        }

        public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
            if (e != null) {
                log.error("Failed to finish directory: {}", dir, e);
            }
            else {
                log.debug("Finished directory: {}", dir);
            }
            return FileVisitResult.CONTINUE;
        }

        Set<String> getClasses() {
            return classes;
        }
    }

    private static Set<String> listClasses(URI uri) throws IOException {
        return process(
            uri,
            new Action<Set<String>>() {
                public Set<String> invoke(Path path) throws IOException {
                    Visitor visitor = new Visitor(path);
                    Files.walkFileTree(
                        path,
                        EnumSet.of(FileVisitOption.FOLLOW_LINKS),
                        Integer.MAX_VALUE,
                        visitor);
                    return visitor.getClasses();
                }
            });
    }

    Set<Class<?>> findClassesAnnotatedWith(
        Class<? extends Annotation> annotation,
        ClassLoader searchLoader) throws Exception {

        long start = System.currentTimeMillis();
        Set<Class<?>> results = new LinkedHashSet<>();
        ClassLoader inspectLoader = Thread.currentThread().getContextClassLoader();
        if (searchLoader == null) {
            searchLoader = inspectLoader;
        }
        List<URI> uris = new ArrayList<>();
        for (URL url : Collections.list(searchLoader.getResources(packageName.replace('.', '/')))) {
            log.info("Searching: " + url);
            uris.add(url.toURI());
        }
        int total = 0;
        for (URI uri : uris) {
            Set<String> classes = listClasses(uri);
            total += classes.size();
            for (String s : classes) {
                String name = packageName + "." + s;
                log.debug("Inspecting: " + name);
                try {
                    Class<?> clazz = inspectLoader.loadClass(name);
                    if (clazz.getAnnotation(annotation) != null) {
                        log.debug("==== FOUND " + clazz);
                        results.add(clazz);
                    }
                }
                catch (ClassNotFoundException e) {
                    log.debug("Cannot load, skipping: " + name);
                }
            }
        }
        log.info("Scanned {} items in {} ms.", total, System.currentTimeMillis() - start);
        return results;
    }
}
