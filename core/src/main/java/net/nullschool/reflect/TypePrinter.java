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
