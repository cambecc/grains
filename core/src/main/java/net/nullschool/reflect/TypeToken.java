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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static net.nullschool.reflect.TypeTools.*;


/**
 * 2013-02-19<p/>
 *
 * An object that represents a generic type {@code T}, following the
 * <a href="http://gafter.blogspot.jp/2006/12/super-type-tokens.html">Super Type Tokens</a> pattern. To use, extend
 * this class and provide for {@code T} the type the token should represent. For example, the following uses an
 * anonymous class to construct a token that represents the type List&lt;Integer&gt;:
 * <pre>
 * TypeToken&lt;List&lt;Integer&gt;&gt; token = new TypeToken&lt;List&lt;Integer&gt;&gt;(){};
 * </pre>
 * The token can then be used to retrieve the {@link Type} object for {@code T}:
 * <pre>
 * System.out.println(token.type);  // outputs "java.util.List&lt;java.lang.Integer&gt;"
 * </pre>
 *
 * @param <T> the type this token represents.
 *
 * @author Cameron Beccario
 */
public abstract class TypeToken<T> {

    private final Type type;

    /**
     * Constructs this type token by extracting the type T from the generic superclass of the {@code this}
     * reference's Class.
     * @throws IllegalArgumentException if no type argument was provided for T, or if the {@code this}
     *                                  reference's Class does not directly inherit TypeToken.
     */
    protected TypeToken() {
        Class<?> clazz = this.getClass();
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            throw new IllegalArgumentException(
                String.format("%s must define a type argument to its super class.", clazz));
        }
        ParameterizedType pt = (ParameterizedType)genericSuperclass;
        if (pt.getRawType() != TypeToken.class) {
            throw new IllegalArgumentException(
                String.format("%s does not directly extend %s", clazz, TypeToken.class));
        }
        this.type = copyOf(pt.getActualTypeArguments()[0]);  // Extract the T.
    }

//    /**
//     * Constructs a TypeToken for the given Class object.
//     */
//    private TypeToken(Class<T> clazz) {
//        this.type = Objects.requireNonNull(clazz);
//    }

    /**
     * Returns the type this token represents.
     */
    public final Type asType() {
        return type;
    }

    /**
     * Returns the value {@code null} typed as T.
     */
    public final T asNull() {
        return null;
    }

    /**
     * Two type tokens are equal if the types they represent are equal.
     */
    @Override public final boolean equals(Object that) {
        return this == that || that instanceof TypeToken && this.type.equals(((TypeToken)that).asType());
    }

    /**
     * The hash code of a type token is the hash code of the type it represents.
     */
    @Override public final int hashCode() {
        return type.hashCode();
    }

    /**
     * String representation of this token and the type it represents.
     */
    @Override public final String toString() {
        return "TypeToken<" + print(type) + '>';
    }

// UNDONE: add this?
//    private static final class ClassToken<T> extends TypeToken<T> {
//        ClassToken(Class<T> clazz) {
//            super(clazz);
//        }
//    }
//
//    /**
//     * Constructs a new token representing the specified class.
//     *
//     * @param clazz the Class object to construct a token for.
//     * @return the token.
//     * @throws NullPointerException if clazz is null.
//     */
//    public static <T> TypeToken<T> tokenOf(Class<T> clazz) {
//        return new ClassToken<>(clazz);
//    }
}
