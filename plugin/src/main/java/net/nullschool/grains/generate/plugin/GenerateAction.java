package net.nullschool.grains.generate.plugin;

import net.nullschool.collect.ConstSet;
import net.nullschool.collect.basic.BasicConstSet;
import net.nullschool.grains.generate.Configuration;
import net.nullschool.grains.generate.GrainGenerator;
import org.apache.maven.plugin.MojoExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.*;

import static net.nullschool.util.ObjectTools.*;
import static net.nullschool.util.ThreadTools.*;

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

    private ConstSet<String> prepareSearchPackages() {
        ConstSet<String> packages = BasicConstSet.asSet(coalesce(mojo.getSearchPackages(), new String[0]));
        if (packages.isEmpty()) {
            packages = BasicConstSet.setOf(mojo.getProject().getGroupId());
            log.info("Searching project.groupId package: {}", mojo.getProject().getGroupId());
        }
        else {
            log.info("Searching packages: {}", packages);
        }
        return packages;
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

    private URL[] prepareSearchDirectories() throws MalformedURLException {
        return new URL[] {Paths.get(mojo.getCompilerOutputDirectory()).toUri().toURL()};
    }

    private ClassLoader prepareClassLoader() throws MalformedURLException {
        return new URLClassLoader(prepareSearchDirectories(), Thread.currentThread().getContextClassLoader());
    }

    private ClassLoader prepareSearchLoader() throws MalformedURLException {
        return !mojo.getIncludeProjectDependencies() ? new URLClassLoader(prepareSearchDirectories()) : null;
    }

    void execute() throws MojoExecutionException {
        try {
            Configuration config = new Configuration();
            config.setCharset(prepareEncoding());
            config.setOutput(prepareTargetDirectory());
            config.setSearchPackages(prepareSearchPackages());
            config.setSearchLoader(prepareSearchLoader());
            config.setLineWidth(prepareLineWidth());
            config.setLineSeparator(prepareLineSeparator());
            config.setImmutabilityStrategy(mojo.getImmutabilityStrategy());

            // Invoke the generator on a thread whose classloader has access to the classes the
            // generator will process.
            Executors.newCachedThreadPool(newContextThreadFactory(prepareClassLoader(), defaultFactory))
                .submit(new GrainGenerator(config))
                .get();

            // Modify the project to include the freshly generated sources for compilation.
            Path sourceRoot = config.getOutput().toAbsolutePath();
            log.info("Adding source root: {}", sourceRoot);
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
