package net.nullschool.grains.generate;

import java.lang.reflect.Type;


/**
 * 2013-02-26<p/>
 *
 * @author Cameron Beccario
 */
final class CastFunctionSymbol {  // UNDONE: can be FieldDeclaration

    private final String name;
    private final TypeSymbol type;

    CastFunctionSymbol(String name, Type type, TypePrinterFactory factory) {
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
