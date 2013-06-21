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

import net.nullschool.grains.generate.Configuration;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.Xpp3Dom;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static net.nullschool.util.ObjectTools.coalesce;


/**
 * 2013-04-06<p/>
 *
 * Base class to define common configuration for generate and testGenerate goals.
 *
 * @author Cameron Beccario
 */
abstract class AbstractGenerateMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;
    MavenProject getProject() {
        return project;
    }

    @Parameter(defaultValue = "${session}", required = true, readonly = true)
    private MavenSession session;
    MavenSession getSession() {
        return session;
    }

    /**
     * The character encoding to use for generated source files. Example: "UTF-8".
     */
    @Parameter(property = "encoding", defaultValue = "${project.build.sourceEncoding}")
    private String encoding;
    public String getEncoding() {
        return encoding;
    }

    /**
     * The set of packages to search for annotated grains. Examples: "com.foo.order.model", "com.foo.admin.model".
     * Default are the packages configured for compilation during the generate-sources phase, if any, otherwise
     * "${project.groupId}".
     */
    @Parameter(property = "searchPackages")
    private String[] searchPackages;
    public String[] getSearchPackages() {
        return coalesce(searchPackages, new String[0]);
    }

    /**
     * Set to "true" if project dependencies are to be searched for annotated grains. Otherwise, only classes defined
     * by the module are searched.
     */
    @Parameter(property = "searchProjectDependencies", defaultValue = "false")
    private boolean searchProjectDependencies;
    public boolean getSearchProjectDependencies() {
        return searchProjectDependencies;
    }

    /**
     * The (approximate) maximum line width of the generated source.
     */
    @Parameter(property = "lineWidth", defaultValue = Configuration.DEFAULT_LINE_WIDTH)
    private int lineWidth;
    public int getLineWidth() {
        return lineWidth;
    }

    /**
     * The line separator to use for generated source. Default value: "\n"
     */
    @Parameter(property = "lineSeparator", defaultValue = Configuration.DEFAULT_LINE_SEPARATOR)
    private String lineSeparator;
    public String getLineSeparator() {
        return lineSeparator;
    }

    /**
     * The custom type policy to use for grain generation. This property should be the fully qualified name of a class
     * and the name of a static method or field that returns the type policy instance, separated by a dot. For example:
     * "com.acme.foo.CustomTypePolicy.INSTANCE".
     */
    @Parameter(property = "typePolicy", defaultValue = Configuration.DEFAULT_TYPE_POLICY)
    private String typePolicy;
    public String getTypePolicy() {
        return typePolicy;
    }

    /**
     * The location to save generated source files.
     */
    public abstract String getTargetDirectory();

    /**
     * The classpath to use for invoking the grain generator, as a list of file names.
     */
    abstract List<String> getGeneratorClasspath() throws DependencyResolutionRequiredException;

    /**
     * The classpath to use for searching for grains, as a list of file names.
     */
    abstract List<String> getSearchClasspath() throws DependencyResolutionRequiredException;

    /**
     * Add the specified path to the project as a source root appropriate for this mojo.
     *
     * @param path the source root to add.
     */
    abstract void addSourceRoot(Path path);

    /**
     * Extracts the "include" filters this mojo prefers from the compiler's configuration, if any.
     */
    abstract List<String> getCompilerIncludes();

    String getProperty(String key, String defaultValue) {
        return coalesce(
            getSession().getUserProperties().getProperty(key),
            getSession().getSystemProperties().getProperty(key, defaultValue));
    }

    Xpp3Dom findMavenConfigurationFor(String pluginArtifactId, String phase) {
        for (Plugin plugin : getProject().getBuildPlugins()) {
            if (pluginArtifactId.equals(plugin.getArtifactId())) {
                for (PluginExecution execution : plugin.getExecutions()) {
                    if (phase.equals(execution.getPhase()) && execution.getConfiguration() instanceof Xpp3Dom) {
                        return (Xpp3Dom)execution.getConfiguration();
                    }
                }
            }
        }
        return null;
    }

    static List<Xpp3Dom> childrenNamed(String name, List<Xpp3Dom> nodes) {
        List<Xpp3Dom> results = new ArrayList<>();
        for (Xpp3Dom node : nodes) {
            results.addAll(asList(node.getChildren(name)));
        }
        return results;
    }

    static List<String> valuesOf(List<Xpp3Dom> nodes) {
        List<String> results = new ArrayList<>();
        for (Xpp3Dom node : nodes) {
            results.add(node.getValue());
        }
        return results;
    }

    @Override public void execute() throws MojoExecutionException {
        // Register the Maven log into SLF4J so this plugin can use SLF4J for logging.
        String traceEnabled = getProperty("SLF4JAdapter.enable-trace-as-debug", "false");
        MavenLoggerFactory.INSTANCE.setMavenLog(getLog(), "true".equalsIgnoreCase(traceEnabled));

        new GenerateAction(this).execute();
    }
}
