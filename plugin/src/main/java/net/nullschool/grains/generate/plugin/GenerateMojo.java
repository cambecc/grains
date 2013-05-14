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
 * Mojo for the "generate" goal. Holds the configuration for standard (not test) source generation.
 *
 * @author Cameron Beccario
 */
@Mojo(
    name = "generate",
    defaultPhase = LifecyclePhase.GENERATE_SOURCES,
    requiresProject = true,
    requiresDependencyResolution = ResolutionScope.TEST,
    threadSafe = true)
public class GenerateMojo extends AbstractGenerateMojo {

    @Parameter(property = "targetDirectory", defaultValue = "${project.build.directory}/generated-sources/grains")
    private String targetDirectory;
    /**
     * The location to save generated source files.
     */
    @Override public String getTargetDirectory() {
        return targetDirectory;
    }

    @Override List<String> getGeneratorClasspath() throws DependencyResolutionRequiredException {
        return getProject().getRuntimeClasspathElements();
    }

    @Override List<String> getSearchClasspath() throws DependencyResolutionRequiredException {
        return getSearchProjectDependencies() ?
            getProject().getRuntimeClasspathElements() :
            Arrays.asList(getProject().getBuild().getOutputDirectory());
    }

    @Override List<String> getCompilerIncludes() {
        // Extract the include filters from the pom, if any.
        //     <execution>
        //         <phase>generate-sources</phase>
        //         <goals><goal>compile</goal></goals>
        //         <configuration>
        //             <includes><include>com/acme/model/**</include></includes>
        //         </configuration>
        //     </execution>

        Xpp3Dom config = findMavenConfigurationFor("maven-compiler-plugin", "generate-sources");
        if (config != null) {
            return valuesOf(childrenNamed("include", childrenNamed("includes", asList(config))));
        }
        return Collections.emptyList();
    }

    @Override void addSourceRoot(Path path) {
        getProject().addCompileSourceRoot(path.toString());
    }
}
