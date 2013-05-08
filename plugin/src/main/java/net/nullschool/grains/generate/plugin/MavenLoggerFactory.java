package net.nullschool.grains.generate.plugin;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;


/**
 * 2013-02-27<p/>
 *
 * A bridge between SLF4J's logging system and the native Maven logging system. This bridge allows plugins and
 * their associated libraries to use SLF4J loggers for output during Maven builds.<p/>
 *
 * MavenLoggerFactory is bound to SLF4J as the singleton provider of {@link Logger} instances using
 * {@link org.slf4j.impl.StaticLoggerBinder}. When any component requests an SLF4J logger, the factory returns
 * a logger that delegates all logging events to Maven.<p/>
 *
 * The Maven {@link Log} made available to a plugin upon invocation of {@link org.apache.maven.plugin.Mojo#execute}
 * should be {@link #setMavenLog registered} with MavenLoggerFactory as early as possible. For example:
 *
 * <pre>
 * public class MyMojo extends AbstractMojo {
 *     public void execute() {
 *         MavenLoggerFactory.INSTANCE.setMavenLog(getLog(), false);
 *         ...
 *     }
 * }
 * </pre>
 *
 * Any use of SLF4J before registration occurs will cause logging events to be routed to Maven's default, and
 * undesirable, console logger.
 *
 * @author Cameron Beccario
 */
public enum MavenLoggerFactory implements ILoggerFactory {
    INSTANCE;

    /**
     * The adapter to provide to any component requesting an SLF4J logger.
     */
    private volatile Logger activeSLF4JLogger = new SLF4JAdapter(new SystemStreamLog(), false);

    /**
     * Registers the specified Maven log with this factory. All SLF4J events will immediately start routing
     * to the specified Maven log.
     *
     * @param mavenLog the log provided by Maven.
     * @param traceEnabled true if trace events should be logged.
     * @throws NullPointerException if the Maven log is null.
     */
    public void setMavenLog(Log mavenLog, boolean traceEnabled) {
        activeSLF4JLogger = new SLF4JAdapter(mavenLog, traceEnabled);
    }

    @Override public Logger getLogger(String name) {
        return activeSLF4JLogger;
    }

    /**
     * Logger implementation that converts all SLF4J log invocations into equivalent Maven log invocations.
     */
    private static class SLF4JAdapter extends MarkerIgnoringBase implements Logger {

        private final Log mavenLog;
        private final boolean traceEnabled;

        private SLF4JAdapter(Log mavenLog, boolean traceEnabled) {
            this.mavenLog = mavenLog;
            this.name = mavenLog.toString();
            this.traceEnabled = traceEnabled;
        }

        // =============================================================================================================
        // TRACE

        @Override public boolean isTraceEnabled() {
            return traceEnabled;
        }

        @Override public void trace(String msg) {
            if (isTraceEnabled()) {
                debug(msg);
            }
        }

        @Override public void trace(String msg, Throwable t) {
            if (isTraceEnabled()) {
                debug(msg, t);
            }
        }

        @Override public void trace(String format, Object arg) {
            if (isTraceEnabled()) {
                debug(format, arg);
            }
        }

        @Override public void trace(String format, Object arg1, Object arg2) {
            if (isTraceEnabled()) {
                debug(format, arg1, arg2);
            }
        }

        @Override public void trace(String format, Object[] argArray) {
            if (isTraceEnabled()) {
                debug(format, argArray);
            }
        }

        // =============================================================================================================
        // DEBUG

        @Override public boolean isDebugEnabled() {
            return mavenLog.isDebugEnabled();
        }

        @Override public void debug(String msg) {
            mavenLog.debug(msg);
        }

        @Override public void debug(String msg, Throwable t) {
            if (t == null) {
                mavenLog.debug(msg);  // SystemStreamLog throws NPE if passed a null exception.
            }
            else {
                mavenLog.debug(msg, t);
            }
        }

        private void debug(FormattingTuple result) {
            debug(result.getMessage(), result.getThrowable());
        }

        @Override public void debug(String format, Object arg) {
            if (isDebugEnabled()) {
                debug(MessageFormatter.format(format, arg));
            }
        }

        @Override public void debug(String format, Object arg1, Object arg2) {
            if (isDebugEnabled()) {
                debug(MessageFormatter.format(format, arg1, arg2));
            }
        }

        @Override public void debug(String format, Object[] argArray) {
            if (isDebugEnabled()) {
                debug(MessageFormatter.arrayFormat(format, argArray));
            }
        }

        // =============================================================================================================
        // INFO

        @Override public boolean isInfoEnabled() {
            return mavenLog.isInfoEnabled();
        }

        @Override public void info(String msg) {
            mavenLog.info(msg);
        }

        @Override public void info(String msg, Throwable t) {
            if (t == null) {
                mavenLog.info(msg);  // SystemStreamLog throws NPE if passed a null exception.
            }
            else {
                mavenLog.info(msg, t);
            }
        }

        private void info(FormattingTuple result) {
            info(result.getMessage(), result.getThrowable());
        }

        @Override public void info(String format, Object arg) {
            if (isInfoEnabled()) {
                info(MessageFormatter.format(format, arg));
            }
        }

        @Override public void info(String format, Object arg1, Object arg2) {
            if (isInfoEnabled()) {
                info(MessageFormatter.format(format, arg1, arg2));
            }
        }

        @Override public void info(String format, Object[] argArray) {
            if (isInfoEnabled()) {
                info(MessageFormatter.arrayFormat(format, argArray));
            }
        }

        // =============================================================================================================
        // WARN

        @Override public boolean isWarnEnabled() {
            return mavenLog.isWarnEnabled();
        }

        @Override public void warn(String msg) {
            mavenLog.warn(msg);
        }

        @Override public void warn(String msg, Throwable t) {
            if (t == null) {
                mavenLog.warn(msg);  // SystemStreamLog throws NPE if passed a null exception.
            }
            else {
                mavenLog.warn(msg, t);
            }
        }

        private void warn(FormattingTuple result) {
            warn(result.getMessage(), result.getThrowable());
        }

        @Override public void warn(String format, Object arg) {
            if (isWarnEnabled()) {
                warn(MessageFormatter.format(format, arg));
            }
        }

        @Override public void warn(String format, Object arg1, Object arg2) {
            if (isWarnEnabled()) {
                warn(MessageFormatter.format(format, arg1, arg2));
            }
        }

        @Override public void warn(String format, Object[] argArray) {
            if (isWarnEnabled()) {
                warn(MessageFormatter.arrayFormat(format, argArray));
            }
        }

        // =============================================================================================================
        // ERROR

        @Override public boolean isErrorEnabled() {
            return mavenLog.isErrorEnabled();
        }

        @Override public void error(String msg) {
            mavenLog.error(msg);
        }

        @Override public void error(String msg, Throwable t) {
            if (t == null) {
                mavenLog.error(msg);  // SystemStreamLog throws NPE if passed a null exception.
            }
            else {
                mavenLog.error(msg, t);
            }
        }

        private void error(FormattingTuple result) {
            error(result.getMessage(), result.getThrowable());
        }

        @Override public void error(String format, Object arg) {
            if (isErrorEnabled()) {
                error(MessageFormatter.format(format, arg));
            }
        }

        @Override public void error(String format, Object arg1, Object arg2) {
            if (isErrorEnabled()) {
                error(MessageFormatter.format(format, arg1, arg2));
            }
        }

        @Override public void error(String format, Object[] argArray) {
            if (isErrorEnabled()) {
                error(MessageFormatter.arrayFormat(format, argArray));
            }
        }


        private static final long serialVersionUID = 1;
    }
}
