package net.nullschool.grains.generate;


/**
 * 2013-05-12<p/>
 *
 * Represents a field declaration.
 *
 * @author Cameron Beccario
 */
class FieldSymbol implements Symbol {

    private final String name;
    private final TypeSymbol type;

    FieldSymbol(String name, TypeSymbol type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public TypeSymbol getType() {
        return type;
    }
}
