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


/**
 * 2013-03-29<p/>
 *
 * An object that visits and operates on types, both concrete and generic. This interface provides a pattern for
 * decomposing {@link Type}s into their constituent parts to perform an operation, such as erasure or
 * immutification.<p/>
 *
 * Implementers should provide type-specific (i.e., Class, ParameterizedType, WildcardType, etc.) behaviors of
 * the desired operation. The most generalized method, {@link #apply(Type)}, should then dispatch to the appropriate
 * type-specific method. The {@link AbstractTypeOperator} class provides a suitable implementation of
 * {@link #apply(Type)}.
 *
 * @param <T> the result type of the operation. Operations that have no result can use {@link Void}.
 *
 * @author Cameron Beccario
 */
public interface TypeOperator<T> {

    /**
     * Invoked for a {@link Class} object, like {@code String.class}.
     *
     * @param clazz the class object.
     * @return the operation result.
     */
    T apply(Class<?> clazz);

    /**
     * Invoked for a parameterized type, such as {@code List&lt;String&gt;}.
     *
     * @param pt the parameterized type.
     * @return the operation result.
     */
    T apply(ParameterizedType pt);

    /**
     * Invoked for a generic array type, such as {@code E[]} or {@code List&lt;String&gt;[]}.
     *
     * @param gat the generic array type.
     * @return the operation result.
     */
    T apply(GenericArrayType gat);

    /**
     * Invoked for a wildcard. Example: {@code ? extends String}.
     *
     * @param wt the wildcard type.
     * @return the operation result.
     */
    T apply(WildcardType wt);

    /**
     * Invoked for a type variable, such as {@code E} from the declaration {@code List&lt;E&gt;}.
     *
     * @param tv the type variable.
     * @return the operation result.
     */
    T apply(TypeVariable<?> tv);

    /**
     * Invoked on a general Type. The usual pattern is to inspect the type and then call the appropriate
     * specialized {@code invoke} method.
     *
     * @param type the type.
     * @return the operation result.
     * @throws IllegalArgumentException if type refers to an unknown and unsupported implementation of {@link Type}.
     */
    T apply(Type type);
}
