package net.nullschool.collect;

import java.util.Collection;
import java.util.List;


/**
 * 2013-02-24<p/>
 *
 * @author Cameron Beccario
 */
public interface ConstList<E> extends List<E>, ConstCollection<E> {

    /**
     * Returns a ConstList containing the elements of this list plus the specified element added to the end. No
     * visible change to this list occurs. In this way, the {@code with} method may be considered a factory method
     * for creating new instances of ConstList.<p/>
     *
     * Depending on the implementation, ConstLists may refuse to add the specified element. Well behaved
     * implementations should throw an exception detailing the reason for refusal.
     *
     * @param e the element to add.
     * @return a ConstList containing this list's elements conjoined with the specified element.
     * @throws ClassCastException if the element is of a type not suitable for this list or the resulting list.
     * @throws NullPointerException if the element is null and either this or the resulting list does not allow null
     *                              elements.
     * @throws IllegalArgumentException if some property of the element is not suitable for this or the resulting list.
     */
    @Override ConstList<E> with(E e);

    /**
     * Returns a ConstList containing the elements of this list plus the specified element inserted at the specified
     * index. All existing elements from the index, if any, are shifted down to make room for the new element
     * (i.e., their indices increase by one). No visible change to this list occurs.<p/>
     *
     * Depending on the implementation, ConstLists may refuse to add the specified element. Well behaved
     * implementations should throw an exception detailing the reason for refusal.
     *
     * @param index the index at which to add the element.
     * @param e the element to add.
     * @return a ConstList containing this list's elements conjoined with the specified element.
     * @throws ClassCastException if the element is of a type not suitable for this list or the resulting list.
     * @throws NullPointerException if the element is null and either this or the resulting list does not allow null
     *                              elements.
     * @throws IllegalArgumentException if some property of the element is not suitable for this or the resulting list.
     * @throws IndexOutOfBoundsException if index is less than zero or greater than the size of this list.
     */
    ConstList<E> with(int index, E e);

    /**
     * Returns a ConstList containing the elements of this list plus all the elements of the specified collection
     * added to the end. No visible change to this list occurs.<p/>
     *
     * The effect of this call is equivalent to invoking {@link #with} for each element in the specified collection.
     * Depending on the implementation, ConstLists may refuse to add any of the specified elements. Well behaved
     * implementations should throw an exception detailing the reason for refusal. In the case where the resulting
     * list R would otherwise equal this list, {@code R.equals(this)}, then the method may simply return this list,
     * unchanged.
     *
     * @param c the elements to conjoin with the elements in this list.
     * @return a ConstList containing this list's elements plus the elements in the provided collection.
     * @throws ClassCastException if one of the specified elements is of a type not suitable for this or the resulting
     *                            list.
     * @throws NullPointerException if the specified collection is null, or if one of its elements is null and either
     *                              this or the resulting list does not allow nulls.
     * @throws IllegalArgumentException if some property of an element in the specified collection is not suitable for
     *                                  this or the resulting list.
     */
    @Override ConstList<E> withAll(Collection<? extends E> c);

    /**
     * Returns a ConstList containing the elements of this list plus all the elements of the specified collection
     * inserted at the specified index. All existing elements from the index, if any, are shifted down to make room
     * for the new elements (i.e., their indices increase by the size of the collection). No visible change to this
     * list occurs.<p/>
     *
     * The effect of this call is equivalent to invoking {@link #with} for each element in the specified collection
     * at an index that increases by one for each collection element. Depending on the implementation, ConstLists
     * may refuse to add any of the specified elements. Well behaved implementations should throw an exception
     * detailing the reason for refusal. In the case where the resulting list R would otherwise equal this list,
     * {@code R.equals(this)}, then the method may simply return this list, unchanged.
     *
     * @param index the index at which to add the elements.
     * @param c the elements to conjoin with the elements in this list.
     * @return a ConstList containing this list's elements plus the elements in the provided collection.
     * @throws ClassCastException if one of the specified elements is of a type not suitable for this or the resulting
     *                            list.
     * @throws NullPointerException if the specified collection is null, or if one of its elements is null and either
     *                              this or the resulting list does not allow nulls.
     * @throws IllegalArgumentException if some property of an element in the specified collection is not suitable for
     *                                  this or the resulting list.
     * @throws IndexOutOfBoundsException if index is less than zero or greater than the size of this list.
     */
    ConstList<E> withAll(int index, Collection<? extends E> c);

    /**
     * Returns a ConstList containing the elements of this list but with the element at the specified index replaced
     * with the new provided element. No visible change to this list occurs.<p/>
     *
     * Depending on the implementation, ConstLists may refuse to add the specified element. Well behaved
     * implementations should throw an exception detailing the reason for refusal.
     *
     * @param index the index at which to replace the existing element.
     * @param e the new element.
     * @return a ConstList containing this list's elements but with the new element at the specified index.
     * @throws ClassCastException if the element is of a type not suitable for this list or the resulting list.
     * @throws NullPointerException if the element is null and either this or the resulting list does not allow null
     *                              elements.
     * @throws IllegalArgumentException if some property of the element is not suitable for this or the resulting list.
     * @throws IndexOutOfBoundsException if index is less than zero or greater than the size of this list.
     */
    ConstList<E> replace(int index, E e);

    /**
     * Returns a ConstList containing the elements of this list minus the specified element. No visible change to this
     * list occurs.<p/>
     *
     * If this list does not contain the specified element, i.e., if the resulting list R would otherwise equal this
     * list, {@code R.equals(this)}, then the method may simply return this list, unchanged.
     *
     * @param o the element to remove.
     * @return a ConstList containing this list's elements minus the specified element.
     * @throws ClassCastException if the element is of a type not suitable for this list.
     * @throws NullPointerException if the element is null and this list does not allow null elements.
     */
    @Override ConstList<E> without(Object o);

    /**
     * Returns a ConstList containing the elements of this list minus the element at the specified index. All elements
     * greater than the index are shifted up (i.e., their indices decrease by one). No visible change to this list
     * occurs.<p/>
     *
     * @param index the index of the element to remove.
     * @return a ConstList containing this list's elements minus the specified element.
     * @throws ClassCastException if the element is of a type not suitable for this list.
     * @throws NullPointerException if the element is null and this list does not allow null elements.
     * @throws IndexOutOfBoundsException if index is less than zero or greater than or equal to the size of this list.
     */
    ConstList<E> without(int index);

    /**
     * Returns a ConstList containing the elements of this list minus all the elements of the specified collection.
     * No visible change to this list occurs.<p/>
     *
     * The effect of this call is equivalent to invoking {@link #without} for each element in the specified
     * collection. If this list does not contain any of the elements, i.e., if the resulting list R would otherwise
     * equal this list, {@code R.equals(this)}, then the method may simply return this list, unchanged.
     *
     * @param c the elements to remove.
     * @return a ConstList containing this list's elements minus the specified elements.
     * @throws ClassCastException if one of the specified elements is of a type not suitable for this list.
     * @throws NullPointerException if the specified collection is null, or if one its elements is null and this
     *                              list does not allow nulls.
     */
    @Override ConstList<E> withoutAll(Collection<?> c);

    /**
     * Returns a ConstList containing the elements of this list between fromIndex (inclusive) and toIndex (exclusive).
     * No visible change to this list occurs.<p/>
     *
     * If the resulting list R would otherwise equal this list, {@code R.equals(this)}, then the method may simply
     * return this list, unchanged.
     *
     * @param fromIndex the starting index (inclusive).
     * @param toIndex the ending index (exclusive).
     * @return a new ConstList containing the elements of this list between fromIndex and toIndex.
     * @throws IllegalArgumentException if fromIndex is greater than toIndex.
     * @throws IndexOutOfBoundsException if fromIndex is less than 0 or toIndex is greater than the size of this list.
     */
    @Override ConstList<E> subList(int fromIndex, int toIndex);

    // =================================================================================================================
    // Mutation methods marked @Deprecated to signify they should not be invoked.

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #with(Object)}
     */
    @Deprecated @Override boolean add(E e);

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #with(int, Object)}
     */
    @Deprecated @Override void add(int index, E element);

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #withAll(Collection)}
     */
    @Deprecated @Override boolean addAll(Collection<? extends E> c);

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #withAll(int, Collection)}
     */
    @Deprecated @Override boolean addAll(int index, Collection<? extends E> c);

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #replace}
     */
    @Deprecated @Override E set(int index, E element);

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #without(Object)}.
     */
    @Deprecated @Override boolean remove(Object o);

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #without(int)}
     */
    @Deprecated @Override E remove(int index);

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
     * @deprecated see {@link #withoutAll}
     */
    @Deprecated @Override void clear();
}
