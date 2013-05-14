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

import java.lang.reflect.*;
import java.util.Objects;


/**
 * 2013-03-24<p/>
 *
 * A type operator that prints generic and concrete types to a TypePrinter. All types are printed using their
 * fully qualified names, and nested types are delimited with '.' (rather than '$' as Class.toString() does).
 * Arguments to parameterized types are enclosed in angle brackets {@code &lt;&gt;}, bounds are joined with
 * {@code &}, and each array dimension is represented with square brackets {@code []}.
 *
 * @author Cameron Beccario
 */
public class TypeWriter extends AbstractTypeOperator<TypePrinter> {

    protected final TypePrinter printer;

    /**
     * Construct a new TypeWriter that will print to the specified TypePrinter.
     *
     * @param printer the printer.
     * @throws NullPointerException if printer is null.
     */
    public TypeWriter(TypePrinter printer) {
        this.printer = Objects.requireNonNull(printer);
    }

    /**
     * Prints a class. If the class is an array, then prints the component type followed by square brackets,
     * otherwise, prints the class and its enclosing type, if any, separated with a period.
     *
     * @param clazz the class object.
     * @return this printer.
     */
    @Override public TypePrinter apply(Class<?> clazz) {
        return clazz.isArray() ?
            apply(clazz.getComponentType()).print("[]") :
            apply(clazz, clazz.getEnclosingClass());
    }

    /**
     * Prints a parameterized type. The type arguments, if any, are enclosed in angle brackets {@code &lt;&gt;}
     * and delimited with commas. If the type is enclosed by another type, print it first separated with a period.
     * Example: {@code java.util.Map.Entry&lt;String, Object&gt;}.
     *
     * @param pt the parameterized type.
     * @return this printer.
     */
    @Override public TypePrinter apply(ParameterizedType pt) {
        apply(TypeTools.erase(pt.getRawType()), pt.getOwnerType());
        Type[] typeArguments = pt.getActualTypeArguments();
        if (typeArguments.length > 0) {
            printer.print('<');
            join(typeArguments, ", ");
            printer.print('>');
        }
        return printer;
    }

    /**
     * Prints a generic array. The component type is printed first, followed by square brackets.
     *
     * @param gat the generic array type.
     * @return this printer.
     */
    @Override public TypePrinter apply(GenericArrayType gat) {
        return apply(gat.getGenericComponentType()).print("[]");
    }

    /**
     * Prints a wildcard. The bounds of the wildcard, if any, are delimited with ampersand &. If the bounds are
     * lower bounds, then the prefix {@code "? super "} is printed. If the bounds are upper bounds, then the prefix
     * {@code "? extends "} is printed. If the only upper bound is Object, then {@code "?"} is printed, omitting
     * the bound.
     *
     * @param wt the wildcard type.
     * @return this printer.
     */
    @Override public TypePrinter apply(WildcardType wt) {
        Type[] lowerBounds = wt.getLowerBounds();
        if (lowerBounds.length > 0) {
            printer.print("? super ");
            return join(lowerBounds, " & ");
        }
        Type[] upperBounds = wt.getUpperBounds();
        if (upperBounds.length > 0 && upperBounds[0] != Object.class) {
            printer.print("? extends ");
            return join(upperBounds, " & ");
        }
        return printer.print('?');
    }

    /**
     * Prints a type variable. Just the name is printed--the bounds are omitted.
     *
     * @param tv the type variable.
     * @return this printer.
     */
    @Override public TypePrinter apply(TypeVariable<?> tv) {
        return printer.print(tv.getName());
    }

    /**
     * Prints a class and its enclosing type separated with a '.' instead of the usual '$'. If there is no enclosing
     * type, let the underlying printer render the class as appropriate.<p/>
     *
     * This method digs upwards until a class with no enclosing type is found, at which point it passes it to the
     * underlying printer. Each enclosed class is joined with a '.' and its simple name. This covers cases such as
     * {@code com.acme.Foo&lt;String&gt;$Bar&lt;Integer&gt;$Baz&lt;Long&gt;}.
     *
     * @param clazz the class to print.
     * @param enclosing the enclosing type.
     * @return this printer.
     */
    protected TypePrinter apply(Class<?> clazz, Type enclosing) {
        return enclosing != null ?
            apply(enclosing).print('.').print(clazz.getSimpleName()) :
            printer.print(clazz);
    }

    /**
     * Prints several types delimited with a separator. For example, prints {@code A & B & C} where the array is
     * {@code [A, B, C]} and the separator is {@code " & "}.
     *
     * @param types the types to print.
     * @param separator the delimiter to print between elements of the array.
     * @return this printer.
     */
    protected TypePrinter join(Type[] types, String separator) {
        if (types.length > 0) {
            apply(types[0]);
            for (int i = 1; i < types.length; i++) {
                printer.print(separator);
                apply(types[i]);
            }
        }
        return printer;
    }
}
