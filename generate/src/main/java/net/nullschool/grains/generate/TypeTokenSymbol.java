package net.nullschool.grains.generate;


/**
 * 2013-03-25<p/>
 *
 * A symbol to represent the declaration of a {@link net.nullschool.reflect.TypeToken} and its associated cast
 * function declaration.
 *
 * @author Cameron Beccario
 */
final class TypeTokenSymbol extends FieldSymbol {

    private final FieldSymbol castFunction;

    TypeTokenSymbol(String name, TypeSymbol type, FieldSymbol castFunction) {
        super(name, type);
        this.castFunction = castFunction;
    }

    public FieldSymbol getCastFunction() {
        return castFunction;
    }
}
