package net.nullschool.grains.generate;

import java.lang.reflect.Type;


/**
 * 2013-03-25<p/>
 *
 * @author Cameron Beccario
 */
final class TypeTokenDecl {

    private final String name;
    private final TypeSymbol type;
    private final Checker checker;

    TypeTokenDecl(String name, Type type, Checker checker, TypePrinterFactory factory) {
        this.name = name;
        this.type = new TypeSymbol(type, factory);
        this.checker = checker;
    }

    public String getName() {
        return name;
    }

    public TypeSymbol getType() {
        return type;
    }

    public Checker getChecker() {
        return checker;
    }
}
