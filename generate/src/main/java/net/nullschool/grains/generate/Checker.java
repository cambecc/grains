package net.nullschool.grains.generate;

import java.lang.reflect.Type;


/**
 * 2013-02-26<p/>
 *
 * @author Cameron Beccario
 */
final class Checker {

    private final String name;
    private final TypeSymbol type;

    Checker(String name, Type type, TypePrinterFactory factory) {
        this.name = name;
        this.type = new TypeSymbol(type, factory);
    }

    public String getName() {
        return name;
    }

    public TypeSymbol getType() {
        return type;
    }
}
