package net.nullschool.reflect;

/**
 * 2013-04-03<p/>
 *
 * @author Cameron Beccario
 */
public class StringTypePrinter implements TypePrinter {

    private final StringBuilder sb = new StringBuilder();

    @Override public TypePrinter print(char c) {
        sb.append(c);
        return this;
    }

    @Override public TypePrinter print(String s) {
        sb.append(s);
        return this;
    }

    @Override public TypePrinter print(Class<?> clazz) {
        sb.append(clazz != null ? clazz.getName() : null);
        return this;
    }

    @Override public String toString() {
        return sb.toString();
    }
}
