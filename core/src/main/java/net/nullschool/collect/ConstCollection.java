package net.nullschool.collect;

import java.util.Collection;


/**
 * 2013-02-15<p/>
 *
 * A persistent (i.e., immutable) {@link Collection}. See a description of persistent data structures on
 * <a href="http://en.wikipedia.org/wiki/Persistent_data_structures">Wikipedia</a>.<p/> A ConstCollection instance
 * is guaranteed to never visibly change. New elements can be added or removed using the {@link #with},
 * {@link #without}, and related methods, which allocate a new instance if necessary while leaving the original
 * instance unchanged.<p/>
 *
 * Because a ConstCollection is effectively immutable, the {@link #add}, {@link #addAll}, {@link #remove},
 * {@link #removeAll}, {@link #retainAll}, and {@link #clear} methods inherited from {@link Collection} always throw
 * {@link UnsupportedOperationException}. They are marked <i>deprecated</i> to signify to the developer that they
 * should not be invoked.
 *
 * @param <E> the element type
 *
 * @author Cameron Beccario
 */
public interface ConstCollection<E> extends Collection<E> {

    /**
     * Returns a ConstCollection containing the elements of this collection plus the specified element. No
     * visible change to this collection occurs. In this way, the {@code with} method may be considered a factory
     * method for creating new instances of ConstCollection.<p/>
     *
     * Depending on the implementation, ConstCollections may refuse to add the specified element. Well behaved
     * implementations should throw an exception detailing the reason for refusal, except in the case where
     * this collection already contains the element and this collection disallows duplicate elements. In that
     * case, where the resulting collection R would otherwise equal this collection, {@code R.equals(this)},
     * then the method may simply return this collection, unchanged.
     *
     * @param e the element to add.
     * @return a ConstCollection containing this collection's elements conjoined with the specified element.
     * @throws ClassCastException if the element is of a type not suitable for this collection or the resulting
     *                            collection.
     * @throws NullPointerException if the element is null and either this or the resulting collection does not
     *                              allow null elements.
     * @throws IllegalArgumentException if some property of the element is not suitable for this or the resulting
     *                                  collection.
     */
    ConstCollection<E> with(E e);

    /**
     * Returns a ConstCollection containing the elements of this collection plus all the elements of the specified
     * collection. No visible change to this collection occurs. In this way, the {@code withAll} method may be
     * considered a factory method for creating new instances of ConstCollection.<p/>
     *
     * The effect of this call is equivalent to invoking {@link #with} for each element in the specified collection.
     * Depending on the implementation, ConstCollections may refuse to add any of the specified elements. Well behaved
     * implementations should throw an exception detailing the reason for refusal, except in the case where this
     * collection already contains any of the elements and this collection disallows duplicate elements. In that case,
     * where the resulting collection R would otherwise equal this collection, {@code R.equals(this)}, then the method
     * may simply return this collection, unchanged.
     *
     * @param c the elements to conjoin with the elements in this collection.
     * @return a ConstCollection containing this collection's elements plus the elements in the provided collection.
     * @throws ClassCastException if one of the specified elements is of a type not suitable for this or the resulting
     *                            collection.
     * @throws NullPointerException if the specified collection is null, or if one of its elements is null and either
     *                              this or the resulting collection does not allow nulls.
     * @throws IllegalArgumentException if some property of an element in the specified collection is not suitable for
     *                                  this or the resulting collection.
     */
    ConstCollection<E> withAll(Collection<? extends E> c);

    /**
     * Returns a ConstCollection containing the elements of this collection minus the specified element. No visible
     * change to this collection occurs. In this way, the {@code without} method may be considered a factory method
     * for creating new instances of ConstCollection.<p/>
     *
     * If this collection does not contain the specified element, i.e., if the resulting collection R would otherwise
     * equal this collection, {@code R.equals(this)}, then the method may simply return this collection, unchanged.
     *
     * @param o the element to remove.
     * @return a ConstCollection containing this collection's elements minus the specified element.
     * @throws ClassCastException if the element is of a type not suitable for this collection.
     * @throws NullPointerException if the element is null and this collection does not allow null elements.
     */
    ConstCollection<E> without(Object o);

    /**
     * Returns a ConstCollection containing the elements of this collection minus all the elements of the specified
     * collection. No visible change to this collection occurs. In this way, the {@code withoutAll} method may be
     * considered a factory method for creating new instances of ConstCollection.<p/>
     *
     * The effect of this call is equivalent to invoking {@link #without} for each element in the specified
     * collection. If this collection does not contain any of the elements, i.e., if the resulting collection R would
     * otherwise equal this collection, {@code R.equals(this)}, then the method may simply return this collection,
     * unchanged.
     *
     * @param c the elements to remove.
     * @return a ConstCollection containing this collection's elements minus the specified elements.
     * @throws ClassCastException if one of the specified elements is of a type not suitable for this collection.
     * @throws NullPointerException if the specified collection is null, or if one its elements is null and this
     *                              collection does not allow nulls.
     */
    ConstCollection<E> withoutAll(Collection<?> c);

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
