package net.nullschool.grains.generate.plugin;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.nio.file.Path;


/**
 * 2013-02-27<p/>
 *
 * Mojo for the "generate" goal. Holds the configuration for standard (not test) source generation.
 *
 * @author Cameron Beccario
 */
@Mojo(
    name = "generate",
    defaultPhase = LifecyclePhase.GENERATE_SOURCES,
    requiresProject = true,
    threadSafe = true)
public class GenerateMojo extends AbstractGenerateMojo {

    @Parameter(property = "targetDirectory", defaultValue = "${project.build.directory}/generated-sources/grains")
    private String targetDirectory;
    /**
     * The location to save generated source files.
     */
    @Override String getTargetDirectory() {
        return targetDirectory;
    }

    @Override String getCompilerOutputDirectory() {
        return getProject().getBuild().getOutputDirectory();
    }

    @Override void addSourceRoot(Path path) {
        getProject().addCompileSourceRoot(path.toString());
    }
}
