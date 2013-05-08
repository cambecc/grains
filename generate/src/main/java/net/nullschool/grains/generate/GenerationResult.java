package net.nullschool.grains.generate;

import java.util.Collection;

/**
 * 2013-03-24<p/>
 *
 * @author Cameron Beccario
 */
class GenerationResult {

    private final String text;
    private final Collection<String> errors;

    public GenerationResult(String text, Collection<String> errors) {
        this.text = text;
        this.errors = errors;
    }

    public String getText() {
        return text;
    }

    public Collection<String> getErrors() {
        return errors;
    }
}
