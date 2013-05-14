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

import static net.nullschool.reflect.TypeTools.buildArrayType;

/**
 * 2013-04-03<p/>
 *
 * A type operator that performs
 * <a href="http://www.angelikalanger.com/GenericsFAQ/FAQSections/TechnicalDetails.html#What%20is%20type%20erasure?">
 * type erasure</a>.
 *
 * @author Cameron Beccario
 */
final class Eraser extends AbstractTypeOperator<Class<?>> {

    static final Eraser INSTANCE = new Eraser();

    /**
     * The erasure of a class object is the class object itself. Object --> Object
     */
    @Override public Class<?> apply(Class<?> clazz) {
        return clazz;
    }

    /**
     * The erasure of a parameterized type is the erasure of its raw type. Set&lt;Long&gt; --> Set
     *
     * @throws NullPointerException if the argument is null.
     */
    @Override public Class<?> apply(ParameterizedType pt) {
        return apply(pt.getRawType());
    }

    /**
     * The erasure of a generic array type is the class object representing an array of the erasure of the
     * component type. E[] --> Object[]
     *
     * @throws NullPointerException if the argument is null.
     */
    @Override public Class<?> apply(GenericArrayType gat) {
        return buildArrayType(apply(gat.getGenericComponentType()));
    }

    /**
     * The erasure of a wildcard type is Object for unbounded or lower-bounded wildcards, and is the erasure of
     * the upper bound for upper-bounded wildcards. Examples:
     * <ul>
     *     <li>? --> Object.class</li>
     *     <li>? super Number --> Object.class</li>
     *     <li>? extends Callable&lt;Number&gt; --> Callable.class</li>
     * </ul>
     *
     * @throws NullPointerException if the argument is null.
     */
    @Override public Class<?> apply(WildcardType wt) {
        return wt.getLowerBounds().length != 0 ? Object.class : apply(wt.getUpperBounds()[0]);
    }

    /**
     * The erasure of a type variable is its left-most bound or Object if unbounded. Examples:
     * <ul>
     *     <li>E --> Object.class</li>
     *     <li>E extends Enum&lt;E&gt; --> Enum.class</li>
     *     <li>E extends Serializable & Comparable&lt;E&gt; --> Serializable.class</li>
     * </ul>
     *
     * @throws NullPointerException if the argument is null.
     */
    @Override public Class<?> apply(TypeVariable<?> tv) {
        return apply(tv.getBounds()[0]);
    }
}
