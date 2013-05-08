package net.nullschool.grains.generate;

import org.stringtemplate.v4.STErrorListener;
import org.stringtemplate.v4.misc.STMessage;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 2013-03-24<p/>
 *
 * @author Cameron Beccario
 */
final class ErrorCollector implements STErrorListener {

    private final Collection<String> errors = new CopyOnWriteArrayList<>();

    Collection<String> getErrors() {
        return errors;
    }

    public @Override void compileTimeError(STMessage msg) {
        errors.add("compileTimeError: " + msg);
    }

    public @Override void runTimeError(STMessage msg) {
        errors.add("runTimeError: " + msg);
    }

    public @Override void IOError(STMessage msg) {
        errors.add("IOError: " + msg);
    }

    public @Override void internalError(STMessage msg) {
        errors.add("internalError: " + msg);
    }
}
