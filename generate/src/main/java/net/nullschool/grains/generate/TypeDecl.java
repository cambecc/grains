package net.nullschool.grains.generate;


/**
 * 2013-02-13<p/>
 *
 * @author Cameron Beccario
 */
final class TypeDecl {

    private final String name;

    TypeDecl(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
