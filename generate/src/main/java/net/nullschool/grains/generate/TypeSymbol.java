package net.nullschool.grains.generate;

import net.nullschool.reflect.TypeTools;

import java.lang.reflect.Type;

import static java.util.Objects.requireNonNull;


/**
 * 2013-03-24<p/>
 *
 * A symbol for generating type names in a variety of formats.
 *
 * @author Cameron Beccario
 */
final class TypeSymbol implements Symbol {

    private final Type type;
    private final TypePrinterFactory factory;

    TypeSymbol(Type type, TypePrinterFactory factory) {
        this.type = requireNonNull(type, "null type");
        this.factory = requireNonNull(factory, "null factory");
    }

    /**
     * Returns this type using fully qualified names for each type argument.
     */
    public String getFullName() {
        return TypeTools.toString(type);
    }

    /**
     * Returns this type, rendered with simple names only.
     */
    public String getSimpleName() {
        return new SimpleNameWriter().apply(type).toString();
    }

    /**
     * Returns this type with a diamond operator, if applicable. {@code List&lt;Long&gt;} becomes {@code List&lt;&gt;}.
     */
    public String getAsDiamond() {
        return new DiamondWriter(factory.newPrinter()).apply(type).toString();
    }

    /**
     * Returns this type as printed using this symbol's printer factory.
     */
    @Override public String toString() {
        return TypeTools.toString(type, factory.newPrinter());
    }
}
