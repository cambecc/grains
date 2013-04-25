package net.nullschool.util;


import java.util.Locale;


/**
 * 2013-02-27<p/>
 *
 * Utility methods for operating on strings.
 *
 * @author Cameron Beccario
 */
public enum StringTools {;

    /**
     * Capitalizes the specified string by changing the first character to upper case using {@link Locale#ENGLISH}
     * rules. If the string is null or empty, this method does nothing.
     *
     * @param s the string whose first character is to be made upper case.
     * @return the capitalized string.
     */
    public static String capitalize(String s) {
        return s == null || s.isEmpty() ? s : s.substring(0, 1).toUpperCase(Locale.ENGLISH) + s.substring(1);
    }
}
