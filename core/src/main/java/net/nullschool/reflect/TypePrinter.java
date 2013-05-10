package net.nullschool.reflect;


/**
 * 2013-03-24<p/>
 *
 * An object to which the constituent parts of Types can be rendered as Strings.
 *
 * @author Cameron Beccario
 */
public interface TypePrinter {

    /**
     * Print a plain char to this printer.
     *
     * @param c the char
     * @return this TypePrinter
     */
    TypePrinter print(char c);

    /**
     * Print a plain String. If the string is null, the four characters {@code "null"} are printed.
     *
     * @param s the String
     * @return this TypePrinter
     */
    TypePrinter print(String s);

    /**
     * Print a Class. If the class is null, the four characters {@code "null"} are printed. The behavior
     * of this method is implementation specific. For example, some implementations print the fully qualified
     * class name where others may print just the {@link Class#getSimpleName simple name}.
     *
     * @param clazz the Class to print
     * @return this TypePrinter
     */
    TypePrinter print(Class<?> clazz);

    /**
     * Return everything printed to this TypePrinter as a String.
     */
    @Override String toString();
}
