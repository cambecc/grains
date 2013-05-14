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
 * 2013-03-24<p/>
 *
 * Utility class used for testing. Allows access to type parameter T as a Type instance as produced by Java
 * reflection rather than the late type implementations LateGenericArrayType, LateParameterizedType, LateTypeVariable,
 * or LateWildcardType as would be produced by {@link TypeToken}. Use this class by inheriting it with an anonymous
 * class, providing for T the desired type.
 *
 * @author Cameron Beccario
 */
@SuppressWarnings("UnusedDeclaration")
abstract class JavaToken<T> {

    Type asType() {
        return ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    ParameterizedType asParameterizedType() {
        return (ParameterizedType)asType();
    }

    WildcardType asWildcardType() {
        return (WildcardType)asParameterizedType().getActualTypeArguments()[0];
    }

    GenericArrayType asGenericArrayType() {
        return (GenericArrayType)asType();
    }

    TypeVariable<?> asTypeVariable() {
        return (TypeVariable<?>)asType();
    }
}
