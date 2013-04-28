package net.nullschool.collect;

import java.util.Collection;
import java.util.SortedSet;


/**
 * 2013-03-18<p/>
 *
 * A persistent (i.e., immutable) {@link SortedSet}. See a description of persistent data structures on
 * <a href="http://en.wikipedia.org/wiki/Persistent_data_structures">Wikipedia</a>.<p/> A ConstSortedSet instance is
 * guaranteed to never visibly change. New elements can be added or removed using the {@link #with}, {@link #without},
 * and related methods, which allocate a new instance if necessary while leaving the original instance unchanged.<p/>
 *
 * Because a ConstSortedSet is effectively immutable, the {@link #add}, {@link #addAll}, {@link #remove},
 * {@link #removeAll}, {@link #retainAll}, and {@link #clear} methods inherited from {@link SortedSet} always throw
 * {@link UnsupportedOperationException}. They are marked <i>deprecated</i> to signify to the developer that they
 * should not be invoked.
 *
 * @param <E> the element type
 *
 * @author Cameron Beccario
 */
public interface ConstSortedSet<E> extends SortedSet<E>, ConstSet<E> {

    /**
     * See {@link ConstSet#with}.
     *
     * @param e the element to add.
     * @return a ConstSortedSet containing this set's elements conjoined with the specified element.
     * @throws ClassCastException if the element is of a type not suitable for this or the resulting set.
     * @throws NullPointerException if the element is null and either this or the resulting set does not allow nulls.
     * @throws IllegalArgumentException if some property of the element is not suitable for this or the resulting set.
     */
    @Override ConstSortedSet<E> with(E e);

    /**
     * See {@link ConstSet#withAll}.
     *
     * @param c the elements to conjoin with the elements in this set.
     * @return a ConstSortedSet containing this set's elements plus the elements in the provided collection.
     * @throws ClassCastException if one of the elements is of a type not suitable for this or the resulting set.
     * @throws NullPointerException if the specified collection is null, or if one of its elements is null and either
     *                              this or the resulting set does not allow nulls.
     * @throws IllegalArgumentException if some property of an element in the specified collection is not suitable for
     *                                  this or the resulting set.
     */
    @Override ConstSortedSet<E> withAll(Collection<? extends E> c);

    /**
     * See {@link ConstSet#without}.
     *
     * @param o the element to remove.
     * @return a ConstSortedSet containing this set's elements minus the specified element.
     * @throws ClassCastException if the element is of a type not suitable for this set.
     * @throws NullPointerException if the element is null and this set does not allow null elements.
     */
    @Override ConstSortedSet<E> without(Object o);

    /**
     * See {@link ConstSet#withoutAll}.
     *
     * @param c the elements to remove.
     * @return a ConstSortedSet containing this set's elements minus the specified elements.
     * @throws ClassCastException if one of the specified elements is of a type not suitable for this set.
     * @throws NullPointerException if the specified collection is null, or if one its elements is null and this
     *                              set does not allow nulls.
     */
    @Override ConstSortedSet<E> withoutAll(Collection<?> c);

    /**
     * Returns a new ConstSortedSet containing all the elements of this set less than {@code toElement}.
     *
     * @param toElement the end point (exclusive) of the resulting set.
     * @return a ConstSortedSet containing this set's elements up to but not including the specified element.
     * @throws ClassCastException if the element is of a type not suitable for this set.
     * @throws NullPointerException if the element is null and this set does not allow nulls.
     */
    @Override ConstSortedSet<E> headSet(E toElement);

    /**
     * Returns a new ConstSortedSet containing all the elements of this set greater than or equal to
     * {@code fromElement}.
     *
     * @param fromElement the start point (inclusive) of the resulting set.
     * @return a ConstSortedSet containing this set's elements from the specified element.
     * @throws ClassCastException if the element is of a type not suitable for this set.
     * @throws NullPointerException if the element is null and this set does not allow nulls.
     */
    @Override ConstSortedSet<E> tailSet(E fromElement);

    /**
     * Returns a new ConstSortedSet containing all the elements of this set greater than or equal to
     * {@code fromElement} and less than {@code toElement}.
     *
     * @param fromElement the start point (inclusive) of the resulting set.
     * @param toElement the end point (exclusive) of the resulting set.
     * @return a ConstSortedSet containing this set's elements between fromElement (inclusive) and toElement
     *         (exclusive).
     * @throws ClassCastException if either element is of a type not suitable for this set.
     * @throws NullPointerException if either element is null and this set does not allow nulls.
     * @throws IllegalArgumentException if fromElement is greater than toElement.
     */
    @Override ConstSortedSet<E> subSet(E fromElement, E toElement);

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
