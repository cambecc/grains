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

package net.nullschool.grains;

import net.nullschool.collect.ConstSet;
import net.nullschool.collect.basic.BasicCollections;
import net.nullschool.transform.Transform;
import net.nullschool.reflect.TypeToken;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.UUID;


/**
 * 2013-05-08<p/>
 *
 * A contract that describes the behavior of types used with Grains. Specifically, instances of this interface
 * a) define which types are considered immutable, and b) construct operators for converting instances of a type to
 * another type.<p/>
 *
 * NOTE: a TypePolicy is used by the grain generator during code generation. See the generator plugin's "typePolicy"
 * configuration for details.
 *
 * @author Cameron Beccario
 */
public interface TypePolicy {

    /**
     * Returns true if the specified class is an immutable type as defined by this TypePolicy.
     *
     * @param clazz the type to test for immutability.
     * @return true if the type is immutable.
     * @throws NullPointerException if clazz is null.
     */
    boolean isImmutableType(Class<?> clazz);

    /**
     * Returns the immutable representation of the specified type, or null if none is possible. The returned class is
     * either a) the specified type itself, or b) a subtype of the specified type. For example, a TypePolicy may
     * define ConstList to be the immutable representation of List.
     *
     * @param clazz the type to translate to an immutable type.
     * @return the immutable representation, or null if none.
     * @throws NullPointerException if clazz is null.
     */
    Class<?> asImmutableType(Class<?> clazz);

    /**
     * Returns a transform operator that converts objects to the type represented by the specified token. For example,
     * a TypePolicy may return transforms that perform an unchecked cast to T, or more elaborate transforms that
     * convert each element of any list to String when T is List&lt;String&gt;.
     *
     * @param token a token for the type T.
     * @param <T> the destination type.
     * @return a transform function capable of converting objects to type T.
     * @throws NullPointerException if token is null.
     * @throws IllegalArgumentException if this policy cannot create a transform for this token.
     */
    <T> Transform<T> newTransform(TypeToken<T> token);

// UNDONE: extension processing. And if this is ever used, then should also consider creating a transform
//         for _all_ properties, not just those with generic types. Then dynamic puts/withs would all be
//         channeled through the type policy.
//    Object transformExtension(Object o);

    /**
     * All primitive types.
     */
    public static final ConstSet<Class<?>> PRIMITIVE_TYPES =
        BasicCollections.setOf(
            boolean.class,
            byte.class,
            short.class,
            int.class,
            long.class,
            float.class,
            double.class,
            char.class,
            void.class);

    /**
     * All primitive wrapper types.
     */
    public static final ConstSet<Class<?>> BOXED_PRIMITIVE_TYPES =
        BasicCollections.setOf(
            Boolean.class,
            Byte.class,
            Short.class,
            Integer.class,
            Long.class,
            Float.class,
            Double.class,
            Character.class,
            Void.class);

    /**
     * Additional fundamental, immutable types defined by Java.
     */
    public static final ConstSet<Class<?>> ANCILLARY_TYPES =
        BasicCollections.<Class<?>>setOf(
            BigInteger.class,
            BigDecimal.class,
            String.class,
            UUID.class,
            URI.class);
}
