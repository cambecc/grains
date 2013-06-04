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

package net.nullschool.grains.generate.plugin;

import net.nullschool.collect.ConstSet;
import net.nullschool.grains.generate.Configuration;
import net.nullschool.grains.generate.GrainGenerator;
import org.apache.maven.plugin.MojoExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;

import static net.nullschool.util.ObjectTools.*;
import static net.nullschool.util.ThreadTools.*;
import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-02-28<p/>
 *
 * Interprets the mojo configuration and invokes the grain generator.
 *
 * @author Cameron Beccario
 */
final class GenerateAction {

    // Use daemon threads that will not impede shutdown of the JVM.
    private static final ThreadFactory defaultFactory =
        newNamingThreadFactory(
            GenerateAction.class.getSimpleName() + "%d",
            newDaemonThreadFactory(Executors.defaultThreadFactory()));


    private final Logger log = LoggerFactory.getLogger(GenerateAction.class);
    private final AbstractGenerateMojo mojo;

    GenerateAction(AbstractGenerateMojo mojo) {
        this.mojo = mojo;
    }

    private Charset prepareEncoding() {
        String encoding = coalesce(mojo.getEncoding(), "");
        if (encoding.isEmpty()) {
            encoding = mojo.getProperty("file.encoding", "unknown");
            log.warn("Using platform encoding ({}) to generate sources, i.e. build is platform dependent!", encoding);
        }
        else {
            log.info("Using '{}' encoding to generate sources.", encoding);
        }
        return Charset.forName(encoding);
    }

    private Path prepareTargetDirectory() {
        Path output = Paths.get(mojo.getTargetDirectory());
        log.info("Generating sources to: {}", output);
        return output;
    }

    ConstSet<String> process(List<String> includes) {
        ConstSet<String> results = emptySet();
        for (String include : includes) {
            if (include.endsWith("*")) {
                // Remove stars and convert path separators to dots.
                String cleaned = include.replace("*", "").replace('/', '.').replace('\\', '.');
                // Now remove leading/trailing/duplicate dots.
                StringBuilder sb = new StringBuilder();
                for (String s : cleaned.split("\\.")) {
                    if (!s.isEmpty()) {
                        sb.append('.').append(s);
                    }
                }
                results = results.with(sb.substring(1));
            }
        }
        return results;
    }

    private ConstSet<String> prepareSearchPackages() {
        // Search the packages explicitly configured for this plugin, if any.
        ConstSet<String> packages = asSet(mojo.getSearchPackages());
        if (!packages.isEmpty()) {
            log.info("Searching packages: {}", packages);
            return packages;
        }

        // Otherwise, convert the compiler plugin's <include> filters to packages and search them, if any.
        packages = process(mojo.getCompilerIncludes());
        if (!packages.isEmpty()) {
            log.info("Searching packages: {} (result of compiler configuration)", packages);
            return packages;
        }

        // If all else fails, use the project's group id as the search package.
        log.info("Searching package: {} (result of ${project.groupId})", mojo.getProject().getGroupId());
        return setOf(mojo.getProject().getGroupId());
    }

    private int prepareLineWidth() {
        return mojo.getLineWidth();
    }

    private static String unescape(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\') {
                switch (c = s.charAt(++i)) {
                    case 'n':  c = '\n'; break;
                    case 'r':  c = '\r'; break;
                    default:
                        throw new IllegalArgumentException("Unexpected escape char: " + c);
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    private String prepareLineSeparator() {
        return unescape(mojo.getLineSeparator());
    }

    private ClassLoader prepareGeneratorClassLoader() throws Exception {
        log.debug("Preparing classpath for generator invocation...");
        List<URL> result = new ArrayList<>();
        for (String element : mojo.getGeneratorClasspath()) {
            log.debug("adding: {}", element);
            result.add(Paths.get(element).toUri().toURL());
        }
        // Use the plugin's classloader as the generator classloader's parent to pick up any plugin dependencies.
        return new URLClassLoader(result.toArray(new URL[result.size()]), GenerateAction.class.getClassLoader());
    }

    private ClassLoader prepareSearchClassLoader() throws Exception {
        if (mojo.getSearchProjectDependencies()) {
            log.info("Including project dependencies in search.");
        }
        log.debug("Preparing classpath for grain search...");
        List<URL> result = new ArrayList<>();
        for (String element : mojo.getSearchClasspath()) {
            log.debug("adding: {}", element);
            result.add(Paths.get(element).toUri().toURL());
        }
        // This classloader has no parent because the search path does not include plugin dependencies.
        return new URLClassLoader(result.toArray(new URL[result.size()]));
    }

    void execute() throws MojoExecutionException {
        try {
            Configuration config = new Configuration();
            config.setCharset(prepareEncoding());
            config.setLineWidth(prepareLineWidth());
            config.setLineSeparator(prepareLineSeparator());
            config.setOutput(prepareTargetDirectory());
            config.setTypePolicy(mojo.getTypePolicy());
            config.setSearchPackages(prepareSearchPackages());
            config.setSearchLoader(prepareSearchClassLoader());

            // Invoke the generator on a thread having access to all the same classes as the project would use
            // at runtime. Wait for the task to finish.
            Executors.newCachedThreadPool(newContextThreadFactory(prepareGeneratorClassLoader(), defaultFactory))
                .submit(new GrainGenerator(config))
                .get();

            // Modify the project to include the freshly generated sources for compilation.
            Path sourceRoot = config.getOutput().toAbsolutePath();
            log.debug("Adding source root: {}", sourceRoot);
            mojo.addSourceRoot(sourceRoot);
        }
        catch (Exception e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            throw new MojoExecutionException("Grain generation failed.", e);
        }
    }
}
