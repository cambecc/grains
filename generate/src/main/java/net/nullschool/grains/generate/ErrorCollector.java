package net.nullschool.grains.generate;

import org.stringtemplate.v4.STErrorListener;
import org.stringtemplate.v4.misc.STMessage;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 2013-03-24<p/>
 *
 * Collects errors emitted by the StringTemplate engine for later reporting.
 *
 * @author Cameron Beccario
 */
final class ErrorCollector implements STErrorListener {

    private final Collection<String> errors = new CopyOnWriteArrayList<>();

    Collection<String> getErrors() {
        return errors;
    }

    @Override public void compileTimeError(STMessage msg) {
        errors.add("compileTimeError: " + msg);
    }

    @Override public void runTimeError(STMessage msg) {
        errors.add("runTimeError: " + msg);
    }

    @Override public void IOError(STMessage msg) {
        errors.add("IOError: " + msg);
    }

    @Override public void internalError(STMessage msg) {
        errors.add("internalError: " + msg);
    }
}
