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
    private final CastFunctionSymbol castFunction;

    TypeTokenDecl(String name, Type type, CastFunctionSymbol castFunction, TypePrinterFactory factory) {
        this.name = name;
        this.type = new TypeSymbol(type, factory);
        this.castFunction = castFunction;
    }

    public String getName() {
        return name;
    }

    public TypeSymbol getType() {
        return type;
    }

    public CastFunctionSymbol getCastFunction() {
        return castFunction;
    }
}
