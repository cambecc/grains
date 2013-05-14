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
 * 2013-04-03<p/>
 *
 * A partial implementation of TypeOperator that dispatches invocation of {@link #apply(Type)} to the appropriate
 * narrower overload.
 *
 * @author Cameron Beccario
 */
public abstract class AbstractTypeOperator<T> implements TypeOperator<T> {

    /**
     * Invokes this operator on the specified type.<p/>
     *
     * This implementation inspects the runtime type of the specified type and invokes the appropriate narrower
     * overload. If type is null, then null is returned.
     *
     * @param type the type.
     * @return the result.
     * @throws IllegalArgumentException if type refers to an unknown and unsupported implementation of {@link Type}.
     */
    @Override public T apply(Type type) {
        if (type instanceof Class) {
            return apply((Class<?>)type);
        }
        else if (type instanceof ParameterizedType) {
            return apply((ParameterizedType)type);
        }
        else if (type instanceof WildcardType) {
            return apply((WildcardType)type);
        }
        else if (type instanceof GenericArrayType) {
            return apply((GenericArrayType)type);
        }
        else if (type instanceof TypeVariable) {
            return apply((TypeVariable<?>)type);
        }
        else if (type == null) {
            return null;
        }
        throw new IllegalArgumentException("Unsupported Type implementation: " + type.getClass());
    }
}
