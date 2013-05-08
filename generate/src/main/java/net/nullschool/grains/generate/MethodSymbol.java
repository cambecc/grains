package net.nullschool.grains.generate;

import net.nullschool.reflect.TypeTools;

import java.lang.reflect.Type;


/**
 * 2013-05-08<p/>
 *
 * @author Cameron Beccario
 */
final class MethodSymbol implements Symbol {

    private final Type type;
    private final String name;
    private final TypePrinterFactory factory;

    MethodSymbol(Type type, String name, TypePrinterFactory factory) {
        this.type = type;
        this.name = name;
        this.factory = factory;
    }

    @Override public String toString() {
        return TypeTools.toString(type, factory.newPrinter()) + '.' + name + "()";
    }
}
