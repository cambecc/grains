package net.nullschool.grains.generate;

import net.nullschool.reflect.TypeTools;

import java.lang.reflect.Type;

import static java.util.Objects.*;


/**
 * 2013-03-24<p/>
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

    public String getFullName() {
        return TypeTools.toString(type);
    }

    public String getSimpleName() {
        return new SimpleNameWriter().invoke(type).toString();
    }

    public String getAsDiamond() {
        return new DiamondWriter(factory.newPrinter()).invoke(type).toString();
    }

    @Override public String toString() {
        return TypeTools.toString(type, factory.newPrinter());
    }
}
