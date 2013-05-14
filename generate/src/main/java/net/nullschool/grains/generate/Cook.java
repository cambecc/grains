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

import net.nullschool.reflect.*;

import java.lang.reflect.*;
import java.util.HashSet;
import java.util.Set;

import static net.nullschool.reflect.TypeTools.erase;
import static net.nullschool.reflect.TypeTools.isInnerClass;


/**
 * 2013-03-29<p/>
 *
 * An operator that instantiates raw types into equivalent parameterized types. For example, the raw type
 * {@code List} should be considered {@code List&lt;? extends Object&gt;} for certain purposes, such as verifying
 * the type represents an immutable type. Other examples:
 * <pre>
 * Map      ->  Map&lt;? extends Object, ? extends Object&gt;
 * EnumSet  ->  EnumSet&lt;? extends Enum&gt;
 * </pre>
 * This class is not thread safe.
 *
 * @author Cameron Beccario
 */
class Cook extends AbstractTypeOperator<Type> {

    /**
     * Keeps track of type variables currently being instantiated, for cycle detection.
     */
    private final Set<TypeVariable<?>> activeVariables = new HashSet<>();

    @Override public Type apply(Class<?> clazz) {
        if (clazz.isArray()) {
            // Cook arrays that might have generic component types, like List[].
            Type componentType = apply(clazz.getComponentType());
            if (!(componentType instanceof Class)) {
                return new LateGenericArrayType(componentType);
            }
        }
        else {
            // Cook generic classes (i.e., any class that has, or whose owner has, type parameters).
            Type ownerType = isInnerClass(clazz) ? apply(clazz.getEnclosingClass()) : clazz.getEnclosingClass();

            TypeVariable<?>[] typeParameters = clazz.getTypeParameters();
            if (typeParameters.length > 0 || ownerType instanceof ParameterizedType) {
                return apply(new LateParameterizedType(clazz, ownerType, typeParameters));
            }
        }
        return clazz;
    }

    @Override public Type apply(ParameterizedType pt) {
        Class<?> rawType = erase(pt.getRawType());
        Type[] typeArguments = pt.getActualTypeArguments();
        return containsActiveVariable(typeArguments) ?  // Check for cycles like "Enum<T extends Enum<T>>"
            rawType :
            new LateParameterizedType(
                rawType,
                isInnerClass(rawType) ? apply(pt.getOwnerType()) : pt.getOwnerType(),
                TypeTools.apply(this, typeArguments));
    }

    @Override public Type apply(GenericArrayType gat) {
        return new LateGenericArrayType(apply(gat.getGenericComponentType()));
    }

    @Override public Type apply(WildcardType wt) {
        if (wt.getLowerBounds().length > 0) {
            Type[] lowerBounds = TypeTools.apply(this, wt.getLowerBounds());
            return containsWildcard(lowerBounds) ?  // Check for "? super ? extends Foo"
                new LateWildcardType("? super", erase(lowerBounds[0])) :
                new LateWildcardType("? super", lowerBounds);
        }
        else {
            Type[] upperBounds = TypeTools.apply(this, wt.getUpperBounds());
            return containsWildcard(upperBounds) ?  // Check for "? extends ? extends Foo"
                new LateWildcardType("? extends", erase(upperBounds[0])) :
                new LateWildcardType("? extends", upperBounds);
        }
    }

    @Override public Type apply(TypeVariable<?> tv) {
        if (activeVariables.contains(tv)) {
            return erase(tv.getBounds()[0]);
        }
        activeVariables.add(tv);
        try {
            Type bound = apply(tv.getBounds()[0]);
            return bound instanceof WildcardType ?  // Check for "? extends ? extends Foo"
                new LateWildcardType("? extends", erase(bound)) :
                new LateWildcardType("? extends", bound);
        }
        finally {
            activeVariables.remove(tv);
        }
    }

    private boolean containsActiveVariable(Type[] types) {
        for (Type type : types) {
            if (type instanceof TypeVariable && activeVariables.contains(type)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsWildcard(Type[] types) {
        for (Type type : types) {
            if (type instanceof WildcardType) {
                return true;
            }
        }
        return false;
    }
}
