package net.nullschool.grains.generate.plugin;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugins.annotations.*;
import org.codehaus.plexus.util.xml.Xpp3Dom;

import java.nio.file.Path;
import java.util.*;

import static java.util.Arrays.asList;


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

    @Override List<String> getCompilerIncludes() {
        // Extract the include filters from the pom, if any.
        //     <execution>
        //         <phase>generate-test-sources</phase>
        //         <goals><goal>compile</goal></goals>
        //         <configuration>
        //             <testIncludes><testInclude>com/acme/model/**</testInclude></testIncludes>
        //         </configuration>
        //     </execution>

        Xpp3Dom configuration = findMavenConfigurationFor("maven-compiler-plugin", "generate-test-sources");
        if (configuration != null) {
            return valuesOf(childrenNamed("testInclude", childrenNamed("testIncludes", asList(configuration))));
        }
        return Collections.emptyList();
    }

    @Override void addSourceRoot(Path path) {
        getProject().addTestCompileSourceRoot(path.toString());
    }
}
