package net.nullschool.grains.generate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;


/**
 * 2013-03-01<p/>
 *
 * Digs through a classpath, looking for classes annotated with a specific annotation.
 *
 * @author Cameron Beccario
 */
final class Reflector {

    private static final Logger log = LoggerFactory.getLogger(Reflector.class);

    private final String packageName;

    /**
     * Build a reflector for the desired package.
     *
     * @param packageName the package to search in, including sub-packages.
     */
    Reflector(String packageName) {
        this.packageName = Objects.requireNonNull(packageName);
    }

    private interface Action<T> {
        T apply(Path path) throws IOException;
    }

    /**
     * Converts the specified uri to a Path and invokes the action with it, returning the action's result.
     */
    private static <T> T process(URI uri, Action<T> action) throws IOException {
        try {
            return action.apply(Paths.get(uri));
        }
        catch (FileSystemNotFoundException e) {
            Map<String, ?> env = Collections.emptyMap();
            try (FileSystem fs = FileSystems.newFileSystem(uri, env, Thread.currentThread().getContextClassLoader())) {
                return action.apply(fs.provider().getPath(uri));
            }
        }
    }

    /**
     * File visitor that collects the fully qualified class names of all .class files located under a particular
     * path/directory.
     */
    private class Visitor implements FileVisitor<Path> {

        private final Path base;
        private final Set<String> classNames = new LinkedHashSet<>();

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

            // Convert the file's absolute path to a relative path rooted at the search package.
            // Example: ~/foo/target/classes/com/acme/order/model/Foo.class --> order/model/Foo.class
            file = base.relativize(file);

            // Build the fully qualified name by converting file separators to '.'
            // Example: order/model/Foo.class --> com.acme.order.model.Foo.class
            if (file.getNameCount() > 0) {
                sb.append(packageName).append('.').append(file.getName(0));
                for (int i = 1; i < file.getNameCount(); i++) {
                    sb.append('.').append(file.getName(i));
                }
            }
            String name = sb.toString();

            // Strip off the ".class" and remember this fully qualified class name.
            // Example: com.acme.order.model.Foo.class --> com.acme.order.model.Foo
            if (name.endsWith(".class")) {
                classNames.add(name.substring(0, name.length() - 6));
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

        Set<String> getClassNames() {
            return classNames;
        }
    }

    /**
     * Walk all files underneath the path specified by the URI. Return any .class files as fully qualified,
     * dot-delimited class names. The URI must be represent a {@link FileSystem} path.
     *
     * @param uri the path to search.
     * @return the set of fully qualified class names for all classes underneath the path.
     * @throws IOException
     */
    private Set<String> listClasses(URI uri) throws IOException {
        return process(
            uri,
            new Action<Set<String>>() {
                public Set<String> apply(Path path) throws IOException {
                    Visitor visitor = new Visitor(path);
                    Files.walkFileTree(
                        path,
                        EnumSet.of(FileVisitOption.FOLLOW_LINKS),
                        Integer.MAX_VALUE,
                        visitor);
                    return visitor.getClassNames();
                }
            });
    }

    /**
     * Returns the set of classes annotated with the annotation, located under the search package, and visible to
     * the provided search loader.
     *
     * @param annotation the annotation to search for.
     * @param searchLoader the loader to use for finding classes.
     * @return the set of classes having the desired annotation.
     * @throws IOException
     */
    Set<Class<?>> findClassesAnnotatedWith(
        Class<? extends Annotation> annotation,
        ClassLoader searchLoader) throws IOException {

        long start = System.currentTimeMillis();

        Set<Class<?>> results = new LinkedHashSet<>();
        ClassLoader inspectLoader = Thread.currentThread().getContextClassLoader();
        if (searchLoader == null) {
            searchLoader = inspectLoader;
        }

        // Get a list of all URIs for the desired package from the search loader.
        List<URI> uris = new ArrayList<>();
        for (URL url : Collections.list(searchLoader.getResources(packageName.replace('.', '/')))) {
            log.debug("Searching: {}", url);
            try {
                uris.add(url.toURI());
            }
            catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        // Get the fully qualified names for all classes under each URI.
        Set<String> classNames = new LinkedHashSet<>();
        for (URI uri : uris) {
            classNames.addAll(listClasses(uri));
        }

        // Load all classes, looking for the annotation.
        for (String name : classNames) {
            log.debug("Inspecting: {}", name);
            try {
                Class<?> clazz = inspectLoader.loadClass(name);
                if (clazz.getAnnotation(annotation) != null) {
                    log.debug("Found: " + clazz);
                    results.add(clazz);
                }
            }
            catch (ClassNotFoundException e) {
                log.debug("Cannot load, skipping {}: {}", name, e);
            }
        }

        log.debug("Scanned {} items in {} ms.", classNames.size(), System.currentTimeMillis() - start);
        return results;
    }
}
