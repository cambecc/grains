package net.nullschool.grains.generate.plugin;

import net.nullschool.grains.generate.Configuration;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.nio.file.Path;
import java.util.List;

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

    @Parameter(property = "encoding", defaultValue = "${project.build.sourceEncoding}")
    private String encoding;
    /**
     * The character encoding to use for generated source files.
     */
    public String getEncoding() {
        return encoding;
    }

    @Parameter(property = "searchPackages")
    private String[] searchPackages;
    /**
     * The set of packages to search for annotated grains, e.g., ["com.foo.order.model", "com.foo.admin.model"]
     */
    public String[] getSearchPackages() {
        return searchPackages;
    }

    @Parameter(property = "searchProjectDependencies", defaultValue = "false")
    private boolean searchProjectDependencies;
    /**
     * True if project dependencies are to be searched for annotated grains.
     */
    public boolean getSearchProjectDependencies() {
        return searchProjectDependencies;
    }

    @Parameter(property = "lineWidth", defaultValue = Configuration.DEFAULT_LINE_WIDTH)
    private int lineWidth;
    /**
     * The (approximate) generated source's line width.
     */
    public int getLineWidth() {
        return lineWidth;
    }

    @Parameter(property = "lineSeparator", defaultValue = Configuration.DEFAULT_LINE_SEPARATOR)
    private String lineSeparator;
    /**
     * The line separator to use for generated source.
     */
    public String getLineSeparator() {
        return lineSeparator;
    }

    @Parameter(property = "constPolicy", defaultValue = Configuration.DEFAULT_CONST_POLICY)
    private String constPolicy;
    /**
     * The const policy to use for the generated grains.
     */
    public String getConstPolicy() {
        return constPolicy;
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

    String getProperty(String key, String defaultValue) {
        return coalesce(
            getSession().getUserProperties().getProperty(key),
            getSession().getSystemProperties().getProperty(key, defaultValue));
    }

    @Override public void execute() throws MojoExecutionException {
        // Register the Maven log into SLF4J so this plugin can use SLF4J for logging.
        String traceEnabled = getProperty("SLF4JAdapter.enable-trace-as-debug", "false");
        MavenLoggerFactory.INSTANCE.setMavenLog(getLog(), "true".equalsIgnoreCase(traceEnabled));

        new GenerateAction(this).execute();
    }
}
