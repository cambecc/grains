package net.nullschool.grains.generate;


/**
 * 2013-03-25<p/>
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
