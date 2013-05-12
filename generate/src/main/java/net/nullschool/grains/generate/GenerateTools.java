package net.nullschool.grains.generate;

import net.nullschool.reflect.LateParameterizedType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 2013-02-10<p/>
 *
 * Utility methods for code generation.
 *
 * @author Cameron Beccario
 */
enum GenerateTools {;

    private static final Set<String> javaReservedWords =
        new HashSet<>(
            Arrays.asList(
                "abstract", "assert", "boolean", "break", "byte", "case",
                "catch", "char", "class", "const", "continue", "default",
                "do", "double", "else", "enum", "extends", "false",
                "final", "finally", "float", "for", "goto", "if",
                "implements", "import", "instanceof", "int", "interface", "long",
                "native", "new", "null", "package", "private", "protected",
                "public", "return", "short", "static", "strictfp", "super",
                "switch", "synchronized", "this", "throw", "throws", "transient",
                "true", "try", "void", "volatile", "while"));

    /**
     * Any identifier starting with '$' or ending with '_' is reserved for internal use in grain implementations.
     */
    static boolean isGrainReservedWord(String s) {
        return s.startsWith("$") || s.endsWith("_");
    }

    /**
     * Returns true if the string is a Java keyword.
     */
    static boolean isJavaReserved(String s) {
        return javaReservedWords.contains(s);
    }

    /**
     * Appends '_' if the identifier is a Java keyword or a grain reserved word.
     */
    static String escape(String identifier) {
        return javaReservedWords.contains(identifier) || isGrainReservedWord(identifier) ?
            identifier + '_' :
            identifier;
    }

    static LateParameterizedType asLateParameterizedType(Type type) {
        return type instanceof ParameterizedType ? LateParameterizedType.copyOf((ParameterizedType)type) : null;
    }

    static Type genericSuperclass(Type type) {
        LateParameterizedType lpt = asLateParameterizedType(type);
        return lpt != null ? lpt.getSuperclass() : ((Class<?>)type).getGenericSuperclass();
    }

    static Type[] genericInterfaces(Type type) {
        LateParameterizedType lpt = asLateParameterizedType(type);
        return lpt != null ? lpt.getInterfaces() : ((Class<?>)type).getGenericInterfaces();
    }
}
