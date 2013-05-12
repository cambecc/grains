package net.nullschool.grains.generate;

import java.util.Collection;

/**
 * 2013-03-24<p/>
 *
 * An object to hold the results of one code generating operation.
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

    /**
     * Returns the generated code.
     */
    public String getText() {
        return text;
    }

    /**
     * Returns error messages encountered during code generation, or an empty collection if none.
     */
    public Collection<String> getErrors() {
        return errors;
    }
}
