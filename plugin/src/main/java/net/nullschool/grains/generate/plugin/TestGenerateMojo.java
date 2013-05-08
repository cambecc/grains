package net.nullschool.grains.generate.plugin;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.nio.file.Path;


/**
 * 2013-02-27<p/>
 *
 * Mojo for the "testGenerate" goal. Holds the configuration for test source generation.
 *
 * @author Cameron Beccario
 */
@Mojo(
    name = "testGenerate",
    defaultPhase = LifecyclePhase.GENERATE_TEST_SOURCES,
    requiresProject = true,
    threadSafe = true)
public class TestGenerateMojo extends AbstractGenerateMojo {

    @Parameter(property = "targetDirectory", defaultValue = "${project.build.directory}/generated-test-sources/grains")
    private String targetDirectory;
    /**
     * The location to save generated test source files.
     */
    @Override String getTargetDirectory() {
        return targetDirectory;
    }

    @Override String getCompilerOutputDirectory() {
        return getProject().getBuild().getTestOutputDirectory();
    }

    @Override void addSourceRoot(Path path) {
        getProject().addTestCompileSourceRoot(path.toString());
    }
}
