package net.nullschool.grains.generate;


/**
 * 2013-03-25<p/>
 *
 * A symbol to represent the declaration of a {@link net.nullschool.reflect.TypeToken} and its associated transform
 * function declaration.
 *
 * @author Cameron Beccario
 */
final class TypeTokenSymbol extends FieldSymbol {

    private final FieldSymbol transform;

    TypeTokenSymbol(String name, TypeSymbol type, FieldSymbol transform) {
        super(name, type);
        this.transform = transform;
    }

    public FieldSymbol getTransform() {
        return transform;
    }
}
