package net.nullschool.grains.generate;

import net.nullschool.reflect.TypeTools;

import java.lang.reflect.Type;


/**
 * 2013-04-13<p/>
 *
 * @author Cameron Beccario
 */
final class FieldSymbol {

    private final Type type;
    private final String name;
    private final TypePrinterFactory factory;

    FieldSymbol(Type type, String name, TypePrinterFactory factory) {
        this.type = type;
        this.name = name;
        this.factory = factory;
    }

    @Override public String toString() {
        return TypeTools.toString(type, factory.newPrinter()) + '.' + name;
    }
}
