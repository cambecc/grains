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


import net.nullschool.collect.basic.BasicConstMap;
import net.nullschool.util.ArrayTools;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static net.nullschool.reflect.TypeTools.erase;

/**
 * 2013-03-24<p/>
 *
 * An immutable {@link ParameterizedType} that represents a generic type instantiation. For example:
 * <pre>
 *     List&lt;Integer&gt;
 *     Map&lt;String, ? extends Number&gt;
 * </pre>
 *
 * @author Cameron Beccario
 */
public final class LateParameterizedType implements ParameterizedType {

    private final Class<?> rawType;
    private final Type ownerType;
    private final Type[] typeArguments;
    private final Resolver resolver;

    /**
     * Constructs a type that represents a type instance of a generic type. For example, the type
     * {@code Map.Entry&lt;String, Integer&gt;} is a type instance of  {@code Map.Entry&lt;K, V&gt;}, and would
     * be constructed as:
     * <pre>
     * new LateParameterizedType(Entry.class, Map.class, String.class, Integer.class);
     * </pre>
     *
     * @param rawType the class or interface that declared this type.
     * @param ownerType the type that this type is a member of (can be null).
     * @param actualTypeArguments the type arguments of this type.
     * @throws NullPointerException if rawType or any type argument is null.
     * @throws IllegalArgumentException if the number of supplied type arguments does not match the raw type's
     *                                  number of type parameters, or if any type argument is unsuitable to be
     *                                  matched to its associated type parameter.
     */
    public LateParameterizedType(Type rawType, Type ownerType, Type... actualTypeArguments) {
        this.rawType = erase(requireNonNull(rawType, "null rawType"));
        this.ownerType = ownerType;
        this.typeArguments = actualTypeArguments.clone();

        int i;
        if ((i = ArrayTools.indexOf(null, typeArguments)) >= 0) {
            throw new NullPointerException("null type argument at index " + i);
        }

        TypeVariable<?>[] typeParameters = this.rawType.getTypeParameters();
        if (typeArguments.length != typeParameters.length) {
            throw new IllegalArgumentException(String.format(
                "Supplied type arguments %s do not match type parameters %s of %s",
                Arrays.toString(typeArguments),
                Arrays.toString(typeParameters),
                rawType));
        }
        this.resolver = new Resolver(BasicConstMap.asMap(typeParameters, typeArguments));
    }

    /**
     * Builds a LateParameterizedType representation of the specified ParameterizedType.
     *
     * @param pt the type to copy.
     * @return a LateParameterizedType copy of the provided type.
     * @throws NullPointerException if pt is null, or its raw type or any type argument is null.
     * @throws IllegalArgumentException if any type argument is unsuitable.
     */
    public static LateParameterizedType copyOf(ParameterizedType pt) {
        return new LateParameterizedType(pt.getRawType(), pt.getOwnerType(), pt.getActualTypeArguments());
    }

    /**
     * {@inheritDoc}
     */
    @Override public Class<?> getRawType() {
        return rawType;
    }

    /**
     * {@inheritDoc}
     */
    @Override public Type getOwnerType() {
        return ownerType;
    }

    /**
     * {@inheritDoc}
     */
    @Override public Type[] getActualTypeArguments() {
        return typeArguments.clone();
    }

    /**
     * Returns a new type having all of its type variables (if any) replaced with their associated type arguments
     * from this parameterized type. For example, if this parameterized type is {@code Map&lt;String, Integer&gt;}
     * which is a type instance of {@code Map&lt;K, V&gt;}, then this method replaces all occurrences of
     * {@code K} with {@code String} and {@code V} with {@code Integer}. Invoking this method with a type that
     * represents {@code Set&lt;Map.Entry&lt;? extends K, ? extends V&gt;&gt;} would <i>resolve</i> the type variables
     * and return {@code Set&lt;Map.Entry&lt;? extends String, ? extends Integer&gt;&gt;}.<p/>
     *
     * If a type variable has no associated type argument, such as if the variable belongs to a different generic
     * declaration, then the type variable remains unresolved in the result.
     *
     * @param type the type to resolve.
     * @return a type with its type variables resolved in the context of this parameterized type, or null if
     *         the type is null.
     */
    public Type resolve(Type type) {
        return resolver.apply(type);
    }

    /**
     * Returns a new array of types that have been resolved in the context of this parameterized type.
     * See {@link #resolve(Type)}.
     *
     * @param types the types to resolve.
     * @return a new array of resolved types.
     */
    public Type[] resolve(Type[] types) {
        return TypeTools.apply(resolver, types);
    }

    /**
     * Returns the superclass of this type instance. Any type variables in the generic superclass are resolved
     * to the arguments of this type instance. For example, the superclass of {@code AbstractList&lt;Integer&gt;}
     * is {@code AbstractCollection&lt;Integer&gt;}. See {@link #resolve(Type)}.<p/>
     *
     * This method returns null if this type instance is an interface, otherwise a Class or LateParameterizedType
     * is returned.
     *
     * @return the resolved superclass of this type instance.
     */
    public Type getSuperclass() {
        return resolve(rawType.getGenericSuperclass());
    }

    /**
     * Returns the interfaces directly implemented by this type instance. Any type variables are resolved to
     * the arguments of this type instance. For example, the interface of {@code List&lt;Integer&gt;} is
     * {@code Collection&lt;Integer&gt;}. See {@link #resolve(Type)}<p/>
     *
     * This method returns an empty array if this type implements no interfaces. Otherwise, the array is populated
     * with Class or LateParameterizedType instances.
     *
     * @return the resolved interfaces of this type.
     */
    public Type[] getInterfaces() {
        return resolve(rawType.getGenericInterfaces());
    }

    private boolean equals(ParameterizedType that) {
        return
            this.rawType.equals(that.getRawType()) &&
            Objects.equals(this.ownerType, that.getOwnerType()) &&
            Arrays.equals(this.typeArguments, that.getActualTypeArguments());
    }

    /**
     * Returns true if the specified argument is a parameterized type having the same raw type, owner type,
     * and type arguments.
     */
    @Override public boolean equals(Object that) {
        return this == that || that instanceof ParameterizedType && this.equals((ParameterizedType)that);
    }

    /**
     * The hash code for a parameterized type is defined to be the hash code of its raw type XOR'd with the owner
     * type's hash code, XOR'd with the hash code of the type arguments, as per Oracle's implementation.
     */
    @Override public int hashCode() {
        return rawType.hashCode() ^ Objects.hashCode(ownerType) ^ Arrays.hashCode(typeArguments);
    }

    /**
     * Returns a string representation of this type in the form described by {@link TypeTools#toString(Type)}.
     */
    @Override public String toString() {
        return TypeTools.toString(this);
    }
}
