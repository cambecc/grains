package net.nullschool.grains.generate.plugin;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugins.annotations.*;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;


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
    requiresDependencyResolution = ResolutionScope.TEST,
    threadSafe = true)
public class TestGenerateMojo extends AbstractGenerateMojo {

    @Parameter(property = "targetDirectory", defaultValue = "${project.build.directory}/generated-test-sources/grains")
    private String targetDirectory;
    /**
     * The location to save generated test source files.
     */
    @Override public String getTargetDirectory() {
        return targetDirectory;
    }

    @Override List<String> getGeneratorClasspath() throws DependencyResolutionRequiredException {
        return getProject().getTestClasspathElements();
    }

    @Override List<String> getSearchClasspath() throws DependencyResolutionRequiredException {
        return getSearchProjectDependencies() ?
            getProject().getTestClasspathElements() :
            Arrays.asList(getProject().getBuild().getTestOutputDirectory());
    }

    @Override void addSourceRoot(Path path) {
        getProject().addTestCompileSourceRoot(path.toString());
    }
}
