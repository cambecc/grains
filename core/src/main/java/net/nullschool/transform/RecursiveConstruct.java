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

package net.nullschool.transform;

import net.nullschool.reflect.*;

import java.lang.reflect.*;


/**
 * 2013-05-13<p/>
 *
 * @author Cameron Beccario
 */
class RecursiveConstruct extends AbstractTypeOperator<Transform<?>> {

    private static final Transform[] EMPTY = new Transform[0];

    private Transform[] apply(Type[] types) {
        Transform[] result = types.length > 0 ? new Transform[types.length] : EMPTY;
        for (int i = 0; i < types.length; i++) {
            result[i] = apply(types[i]);
        }
        return result;
    }

    private final TransformFactory factory;

    public RecursiveConstruct(TransformFactory factory) {
        this.factory = factory;
    }

    @Override public Transform<?> apply(Class<?> clazz) {
        return factory.newBuilder(clazz).build();
    }

    @Override public Transform<?> apply(ParameterizedType pt) {
        Class<?> rawType = TypeTools.erase(pt.getRawType());
        TransformFactory.Builder<?> builder = factory.newBuilder(rawType);

        // UNDONE: translation of type arguments to match parameters of target type: builder.getType().
        //         for example, MultiSet<Integer> extends Set<List<Integer>> should set cast function
        //         for List<Integer> if target type of the builder is Set.class rather than MultiSet.class.
        Transform[] argCasts = apply(pt.getActualTypeArguments());
        builder.setArgumentCasts(argCasts);

        return builder.build();
    }

    @Override public Transform<?> apply(GenericArrayType gat) {
        return factory
            .newBuilder(Object[].class)
            .setArgumentCasts(new Transform[] {apply(gat.getGenericComponentType())})
            .build();
    }

    @Override public Transform<?> apply(WildcardType wt) {
        return wt.getLowerBounds().length > 0 ? apply(Object.class) : apply(wt.getUpperBounds()[0]);
    }

    @Override public Transform<?> apply(TypeVariable<?> tv) {
        throw new IllegalArgumentException();
    }

    public <T> Transform<T> apply(TypeToken<T> token) {
        @SuppressWarnings("unchecked") Transform<T> result = (Transform<T>)apply(token.asType());
        return result;
    }
}
