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

import static net.nullschool.reflect.TypeTools.erase;
import static net.nullschool.reflect.TypeTools.isInnerClass;


/**
 * 2013-05-09<p/>
 *
 * A type operator that converts Type instances to their immutable forms. This process deeply traverses the structure
 * of a Type, converting each Class object to its immutable analog as defined by {@link TypeTable#immutify}.
 *
 * @author Cameron Beccario
 */
final class Immutify extends AbstractTypeOperator<Type> {

    private final TypeTable typeTable;

    Immutify(TypeTable typeTable) {
        this.typeTable = typeTable;
    }

    @Override public Class<?> apply(Class<?> clazz) {
        return typeTable.immutify(clazz);
    }

    @Override public Type apply(ParameterizedType pt) {
        Class<?> immutableRawType = apply(erase(pt.getRawType()));
        return new LateParameterizedType(
            immutableRawType,
            isInnerClass(immutableRawType) ? apply(pt.getOwnerType()) : pt.getOwnerType(),
            TypeTools.apply(this, pt.getActualTypeArguments()));
    }

    @Override public Type apply(GenericArrayType gat) {
        // Arrays are never immutable. Just erase and send to type table.
        return apply(erase(gat));
    }

    @Override public Type apply(WildcardType wt) {
        return wt.getLowerBounds().length > 0 ?
            apply(Object.class) :  // a "? super" wildcard allows Object as its widest type.
            new LateWildcardType("? extends", TypeTools.apply(this, wt.getUpperBounds()));
    }

    @Override public Type apply(TypeVariable<?> tv) {
        return apply(erase(tv));  // not sure if this makes sense
    }
}
