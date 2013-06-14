/*
 * Copyright 2013 Cameron Beccario
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
class GenerateTools {

    private GenerateTools() {
        throw new AssertionError();
    }

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
     * Any identifier starting with '$' or '_' is reserved for internal use in grain implementations.
     */
    static boolean isGrainReservedWord(String s) {
        return s.startsWith("$") || s.startsWith("_");
    }

    /**
     * Returns true if the string is a Java keyword.
     */
    static boolean isJavaReserved(String s) {
        return javaReservedWords.contains(s);
    }

    /**
     * Returns true if the string is an illegal Java identifier, such as staring with a number character.
     */
    static boolean isIllegalIdentifier(String s) {
        switch (s.charAt(0)) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return true;
            default:
                return false;
        }
    }

    /**
     * Prepends '_' if the string is a Java keyword, grain reserved word, or an illegal identifier.
     */
    static String escape(String s) {
        return javaReservedWords.contains(s) || isIllegalIdentifier(s) || isGrainReservedWord(s) ?
            '_' + s :
            s;
    }

    /**
     * Returns the specified type as an instance of LateParameterizedType if it is a ParameterizedType, otherwise
     * returns null.
     */
    static LateParameterizedType asLateParameterizedType(Type type) {
        return type instanceof ParameterizedType ? LateParameterizedType.copyOf((ParameterizedType)type) : null;
    }

    static Type genericSuperclassOf(Type type) {
        LateParameterizedType lpt = asLateParameterizedType(type);
        return lpt != null ? lpt.getSuperclass() : ((Class<?>)type).getGenericSuperclass();
    }

    static Type[] genericInterfacesOf(Type type) {
        LateParameterizedType lpt = asLateParameterizedType(type);
        return lpt != null ? lpt.getInterfaces() : ((Class<?>)type).getGenericInterfaces();
    }
}
