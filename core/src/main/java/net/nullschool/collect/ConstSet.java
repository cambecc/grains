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

package net.nullschool.collect;

import java.util.Collection;
import java.util.Set;


/**
 * 2013-02-15<p/>
 *
 * A persistent (i.e., immutable) {@link Set}. See a description of persistent data structures on
 * <a href="http://en.wikipedia.org/wiki/Persistent_data_structures">Wikipedia</a>.<p/> A ConstSet instance is
 * guaranteed to never visibly change. New elements can be added or removed using the {@link #with}, {@link #without},
 * and related methods, which allocate a new instance if necessary while leaving the original instance unchanged.<p/>
 *
 * Because a ConstSet is effectively immutable, the {@link #add}, {@link #addAll}, {@link #remove},
 * {@link #removeAll}, {@link #retainAll}, and {@link #clear} methods inherited from {@link Set} always throw
 * {@link UnsupportedOperationException}. They are marked <i>deprecated</i> to signify to the developer that they
 * should not be invoked.
 *
 * @param <E> the element type
 *
 * @author Cameron Beccario
 */
public interface ConstSet<E> extends Set<E>, ConstCollection<E> {

    /**
     * Returns a ConstSet containing the elements of this set plus the specified element if it is not already
     * contained in this set. No visible change to this set occurs. In this way, the {@code with} method may be
     * considered a factory method for creating new instances of ConstSet.<p/>
     *
     * Depending on the implementation, ConstSets may refuse to add the specified element. Well behaved
     * implementations should throw an exception detailing the reason for refusal, except in the case where this set
     * already contains the element. In that case, where the resulting set R would otherwise equal this set,
     * {@code R.equals(this)}, then the method may simply return this set, unchanged.
     *
     * @param e the element to add.
     * @return a ConstSet containing this set's elements conjoined with the specified element.
     * @throws ClassCastException if the element is of a type not suitable for this or the resulting set.
     * @throws NullPointerException if the element is null and either this or the resulting set does not allow nulls.
     * @throws IllegalArgumentException if some property of the element is not suitable for this or the resulting set.
     */
    @Override ConstSet<E> with(E e);

    /**
     * Returns a ConstSet containing the elements of this set plus all the unique elements of the specified
     * collection. No visible change to this set occurs. In this way, the {@code withAll} method may be
     * considered a factory method for creating new instances of ConstSet.<p/>
     *
     * The effect of this call is equivalent to invoking {@link #with} for each element in the specified collection.
     * Depending on the implementation, ConstSets may refuse to add any of the specified elements. Well behaved
     * implementations should throw an exception detailing the reason for refusal, except in the case where this
     * set already contains any of the elements. In that case, where the resulting set R would otherwise equal this
     * set, {@code R.equals(this)}, then the method may simply return this set, unchanged.
     *
     * @param c the elements to conjoin with the elements in this set.
     * @return a ConstSet containing this set's elements plus the elements in the provided collection.
     * @throws ClassCastException if one of the elements is of a type not suitable for this or the resulting set.
     * @throws NullPointerException if the specified collection is null, or if one of its elements is null and either
     *                              this or the resulting set does not allow nulls.
     * @throws IllegalArgumentException if some property of an element in the specified collection is not suitable for
     *                                  this or the resulting set.
     */
    @Override ConstSet<E> withAll(Collection<? extends E> c);

    /**
     * Returns a ConstSet containing the elements of this collection minus the specified element. No visible
     * change to this set occurs. In this way, the {@code without} method may be considered a factory method
     * for creating new instances of ConstSet.<p/>
     *
     * If this set does not contain the specified element, i.e., if the resulting set R would otherwise equal this
     * set, {@code R.equals(this)}, then the method may simply return this set, unchanged.
     *
     * @param o the element to remove.
     * @return a ConstSet containing this set's elements minus the specified element.
     * @throws ClassCastException if the element is of a type not suitable for this set.
     * @throws NullPointerException if the element is null and this set does not allow null elements.
     */
    @Override ConstSet<E> without(Object o);

    /**
     * Returns a ConstSet containing the elements of this set minus all the elements of the specified set. No visible
     * change to this set occurs. In this way, the {@code withoutAll} method may be considered a factory method for
     * creating new instances of ConstSet.<p/>
     *
     * The effect of this call is equivalent to invoking {@link #without} for each element in the specified
     * collection. If this set does not contain any of the elements, i.e., if the resulting set R would otherwise
     * equal this set, {@code R.equals(this)}, then the method may simply return this set, unchanged.
     *
     * @param c the elements to remove.
     * @return a ConstSet containing this set's elements minus the specified elements.
     * @throws ClassCastException if one of the specified elements is of a type not suitable for this set.
     * @throws NullPointerException if the specified collection is null, or if one its elements is null and this
     *                              set does not allow nulls.
     */
    @Override ConstSet<E> withoutAll(Collection<?> c);

    // =================================================================================================================
    // Mutation methods marked @Deprecated to signify they should not be invoked.

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #with}
     */
    @Deprecated @Override boolean add(E e);

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #withAll}
     */
    @Deprecated @Override boolean addAll(Collection<? extends E> c);

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #without}
     */
    @Deprecated @Override boolean remove(Object o);

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #withoutAll}
     */
    @Deprecated @Override boolean removeAll(Collection<?> c);

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated retainAll is not supported.
     */
    @Deprecated @Override boolean retainAll(Collection<?> c);

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #without}
     */
    @Deprecated @Override void clear();
}
