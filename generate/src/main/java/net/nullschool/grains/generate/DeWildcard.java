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


/**
 * 2013-03-29<p/>
 *
 * A type operator that removes wildcards as it deeply traverses the structure of a type. Any "?" or "? super"
 * wildcards are replaced with {@code Object}, and "? extends" wildcards are replaced with the leftmost bound. For
 * example, {@code List&lt;? extends Map&lt;? super Comparator, ? extends Number&gt;&gt;} after wildcards removal
 * is {@code List&lt;Map&lt;Object, Number&gt;&gt;}.
 *
 * @author Cameron Beccario
 */
class DeWildcard extends AbstractTypeOperator<Type> {

    @Override public Type apply(Class<?> clazz) {
        return clazz;
    }

    @Override public Type apply(ParameterizedType pt) {
        return new LateParameterizedType(
            pt.getRawType(),
            apply(pt.getOwnerType()),  // remove wildcards in owner
            TypeTools.apply(this, pt.getActualTypeArguments()));  // remove wildcards in type arguments
    }

    @Override public Type apply(GenericArrayType gat) {
        return new LateGenericArrayType(apply(gat.getGenericComponentType()));
    }

    @Override public Type apply(WildcardType wt) {
        return wt.getLowerBounds().length > 0 ? Object.class : apply(wt.getUpperBounds()[0]);
    }

    @Override public Type apply(TypeVariable<?> tv) {
        return tv;
    }
}
