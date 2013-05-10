package net.nullschool.reflect;

/**
 * 2013-05-10<p/>
 *
 * A partial implementation of TypePrinter using a {@link StringBuilder} for string construction.
 *
 * @author Cameron Beccario
 */
public abstract class AbstractTypePrinter implements TypePrinter {

    protected final StringBuilder sb = new StringBuilder();

    @Override public TypePrinter print(char c) {
        sb.append(c);
        return this;
    }

    @Override public TypePrinter print(String s) {
        sb.append(s);
        return this;
    }

    @Override public String toString() {
        return sb.toString();
    }
}
