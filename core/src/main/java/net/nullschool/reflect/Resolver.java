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

import net.nullschool.collect.ConstMap;

import java.lang.reflect.*;
import java.util.Objects;

import static net.nullschool.reflect.TypeTools.buildArrayType;
import static net.nullschool.util.ObjectTools.coalesce;

/**
 * 2013-03-29<p/>
 *
 * A type operator that resolves type variables. See {@link LateParameterizedType#resolve(Type)}.<p/>
 *
 * As an example, consider the type Map&lt;String, Integer&gt;. In this context, the type variable K is bound
 * to String and V is bound to Integer. A resolver constructed with these bindings will deeply search any type
 * for the variables K and V and substitute them for String and Integer, respectively.
 *
 * @author Cameron Beccario
 */
final class Resolver extends AbstractTypeOperator<Type> {

    /**
     * A mapping of type variables to the types they represent for this resolver.
     */
    private final ConstMap<TypeVariable<?>, Type> variableBindings;

    Resolver(ConstMap<TypeVariable<?>, Type> variableBindings) {
        this.variableBindings = Objects.requireNonNull(variableBindings);
    }

    /**
     * A class resolves to itself.
     */
    @Override public Type apply(Class<?> clazz) {
        return clazz;
    }

    /**
     * A parameterized type may have type variables in both the owner type and type arguments, such as
     * {@code Foo&lt;T&gt;.Bar&lt;U&gt;}, so resolve them first and build a new parameterized type from the results.
     */
    @Override public Type apply(ParameterizedType pt) {
        return new LateParameterizedType(
            pt.getRawType(),
            apply(pt.getOwnerType()),
            TypeTools.apply(this, pt.getActualTypeArguments()));
    }

    /**
     * A generic array type resolves to an array of whatever its component type resolves to. If the component type
     * resolves to a concrete Class object C, then we must return the concrete Class object for C[]. Once the
     * component type of an array is no longer generic, then the array itself is no longer generic.
     */
    @Override public Type apply(GenericArrayType gat) {
        Type resolvedComponentType = apply(gat.getGenericComponentType());
        return resolvedComponentType instanceof Class ?
            buildArrayType((Class<?>)resolvedComponentType) :
            new LateGenericArrayType(resolvedComponentType);
    }

    /**
     * A wildcard simply resolves to a wildcard of its resolved bounds.
     */
    @Override public Type apply(WildcardType wt) {
        return wt.getLowerBounds().length > 0 ?
            new LateWildcardType("? super", TypeTools.apply(this, wt.getLowerBounds())) :
            new LateWildcardType("? extends", TypeTools.apply(this, wt.getUpperBounds()));
    }

    /**
     * A type variable T resolves to whatever T means in this context, as determined by the variable bindings
     * provided to this resolver during construction. If T is not bound to anything in this context, then T is
     * returned unchanged.
     */
    @Override public Type apply(TypeVariable<?> tv) {
        return coalesce(variableBindings.get(tv), tv);
    }
}
