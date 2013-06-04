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

package net.nullschool.collect.basic;

import net.nullschool.collect.ConstSortedSet;
import net.nullschool.util.ObjectTools;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.*;

import static net.nullschool.collect.basic.BasicTools.*;
import static net.nullschool.util.ArrayTools.EMPTY_OBJECT_ARRAY;

/**
 * 2013-03-18<p/>
 *
 * Utility methods for constructing instances of {@link ConstSortedSet} that use arrays to store their elements,
 * providing a memory efficient implementation of ConstSortedSet but with O(log(N)) complexity for most set query
 * operations, and O(N) complexity for most set construction operations. Set membership is determined using
 * {@link Comparator#compare}, or {@link Comparable natural ordering} if the comparator is {@code null}. These sets
 * allow {@code null} elements only if the associated {@link Comparator} allows nulls.
 *
 * @author Cameron Beccario
 */
public abstract class BasicConstSortedSet<E> extends BasicConstSet<E> implements ConstSortedSet<E> {

    /**
     * Returns an empty ConstSortedSet with the ordering of the specified comparator.
     *
     * @param comparator the comparator, or null for {@link Comparable natural ordering}.
     * @return a persistent empty sorted set.
     */
    public static <E> BasicConstSortedSet<E> emptySortedSet(Comparator<? super E> comparator) {
        return BasicSortedSet0.instance(comparator);
    }

    /**
     * Returns a ConstSortedSet with a single element and the ordering of the specified comparator.
     *
     * @param comparator the comparator, or null for {@link Comparable natural ordering}.
     * @param e0 the element.
     * @return a persistent sorted set containing the specified element.
     * @throws NullPointerException if the element is null and the comparator is null or does not permit nulls.
     * @throws ClassCastException if the element is of a type not compatible for comparison.
     */
    public static <E> BasicConstSortedSet<E> sortedSetOf(Comparator<? super E> comparator, E e0) {
        return new BasicSortedSet1<>(comparator, checkType(comparator, e0));
    }

    /**
     * Returns a ConstSortedSet comprised of the unique elements from the provided arguments, having the ordering
     * of the specified comparator.
     *
     * @param comparator the comparator, or null for {@link Comparable natural ordering}.
     * @param e0 the first element.
     * @param e1 the (potential) second element.
     * @return a persistent sorted set containing the unique elements from the provided arguments.
     * @throws NullPointerException if any element is null and the comparator is null or does not permit nulls.
     * @throws ClassCastException if any element is of a type not compatible for comparison.
     */
    public static <E> BasicConstSortedSet<E> sortedSetOf(Comparator<? super E> comparator, E e0, E e1) {
        int cmp = ObjectTools.compare(e0, e1, comparator);
        return cmp == 0 ?
            new BasicSortedSet1<>(comparator, e0) :
            cmp < 0 ?
                new BasicSortedSetN<>(comparator, new Object[] {e0, e1}) :
                new BasicSortedSetN<>(comparator, new Object[] {e1, e0});
    }

    /**
     * Returns a ConstSortedSet comprised of the unique elements from the provided arguments, having the ordering
     * of the specified comparator.
     *
     * @param comparator the comparator, or null for {@link Comparable natural ordering}.
     * @param e0 the first element.
     * @param e1 the (potential) second element.
     * @param e2 the (potential) third element.
     * @return a persistent sorted set containing the unique elements from the provided arguments.
     * @throws NullPointerException if any element is null and the comparator is null or does not permit nulls.
     * @throws ClassCastException if any element is of a type not compatible for comparison.
     */
    public static <E> BasicConstSortedSet<E> sortedSetOf(Comparator<? super E> comparator, E e0, E e1, E e2) {
        return condense(comparator, unionInto(EMPTY_OBJECT_ARRAY, new Object[] {e0, e1, e2}, comparator));
    }

    /**
     * Returns a ConstSortedSet comprised of the unique elements from the provided arguments, having the ordering
     * of the specified comparator.
     *
     * @param comparator the comparator, or null for {@link Comparable natural ordering}.
     * @return a persistent sorted set containing the unique elements from the provided arguments.
     * @throws NullPointerException if any element is null and the comparator is null or does not permit nulls.
     * @throws ClassCastException if any element is of a type not compatible for comparison.
     */
    public static <E> BasicConstSortedSet<E> sortedSetOf(Comparator<? super E> comparator, E e0, E e1, E e2, E e3) {
        return condense(comparator, unionInto(EMPTY_OBJECT_ARRAY, new Object[] {e0, e1, e2, e3}, comparator));
    }

    /**
     * Returns a ConstSortedSet comprised of the unique elements from the provided arguments, having the ordering
     * of the specified comparator.
     *
     * @param comparator the comparator, or null for {@link Comparable natural ordering}.
     * @return a persistent sorted set containing the unique elements from the provided arguments.
     * @throws NullPointerException if any element is null and the comparator is null or does not permit nulls.
     * @throws ClassCastException if any element is of a type not compatible for comparison.
     */
    public static <E> BasicConstSortedSet<E> sortedSetOf(
        Comparator<? super E> comparator,
        E e0,
        E e1,
        E e2,
        E e3,
        E e4) {

        return condense(comparator, unionInto(EMPTY_OBJECT_ARRAY, new Object[] {e0, e1, e2, e3, e4}, comparator));
    }

    /**
     * Returns a ConstSortedSet comprised of the unique elements from the provided arguments, having the ordering
     * of the specified comparator.
     *
     * @param comparator the comparator, or null for {@link Comparable natural ordering}.
     * @param additional all additional elements past the sixth element.
     * @return a persistent sorted set containing the unique elements from the provided arguments.
     * @throws NullPointerException if any element is null and the comparator is null or does not permit nulls.
     * @throws ClassCastException if any element is of a type not compatible for comparison.
     */
    @SafeVarargs
    public static <E> BasicConstSortedSet<E> sortedSetOf(
        Comparator<? super E> comparator,
        E e0,
        E e1,
        E e2,
        E e3,
        E e4,
        E e5,
        E... additional) {

        Object[] elements = new Object[6 + additional.length];
        elements[0] = e0;
        elements[1] = e1;
        elements[2] = e2;
        elements[3] = e3;
        elements[4] = e4;
        elements[5] = e5;
        System.arraycopy(additional, 0, elements, 6, additional.length);
        return condense(comparator, unionInto(EMPTY_OBJECT_ARRAY, elements, comparator));
    }

    /**
     * Converts the specified array of elements into a ConstSortedSet comprised of the unique elements from the array,
     * having the ordering of the specified comparator.
     *
     * @param comparator the comparator, or null for {@link Comparable natural ordering}.
     * @param elements the elements from which to build the set.
     * @return a persistent sorted set containing the unique elements from the array in sorted order.
     * @throws NullPointerException if any element is null and the comparator is either null or does not permit nulls,
     *                              or the {@code elements} array itself is null.
     * @throws ClassCastException if any element is of a type not compatible for comparison.
     */
    public static <E> BasicConstSortedSet<E> asSortedSet(Comparator<? super E> comparator, E[] elements) {
        return condense(comparator, unionInto(EMPTY_OBJECT_ARRAY, elements, comparator));
    }

    /**
     * Converts the specified sorted set into a ConstSortedSet with the same elements and ordering.
     *
     * @param set the sorted set.
     * @return a persistent sorted set containing the exact elements and ordering of the specified set.
     */
    public static <E> BasicConstSortedSet<E> asSortedSet(SortedSet<E> set) {
        if (set instanceof BasicConstSortedSet) {
            return (BasicConstSortedSet<E>)set;  // The set is already a ConstSortedSet.
        }
        return condense(set.comparator(), set.toArray());
    }

    /**
     * Converts the specified collection into a ConstSortedSet comprised of the unique elements from the collection,
     * having the ordering of the specified comparator.
     *
     * @param comparator the comparator, or null for {@link Comparable natural ordering}.
     * @param collection the collection.
     * @return a persistent sorted set containing the unique elements from the collection in sorted order.
     * @throws NullPointerException if any element is null and the comparator is either null or does not permit nulls,
     *                              or the {@code collection} itself is null.
     * @throws ClassCastException if any element is of a type not compatible for comparison.
     */
    public static <E> BasicConstSortedSet<E> asSortedSet(
        Comparator<? super E> comparator,
        Collection<? extends E> collection) {

        if (collection instanceof SortedSet) {
            @SuppressWarnings("unchecked") SortedSet<E> covariant = (SortedSet<E>)collection;
            if (Objects.equals(comparator, covariant.comparator())) {
                return asSortedSet(covariant);  // the set is already in the desired sorted order.
            }
        }
        return condense(comparator, unionInto(EMPTY_OBJECT_ARRAY, collection.toArray(), comparator));
    }

    /**
     * Constructs a ConstSortedSet from the unique elements encountered while iterating with the specified iterator.
     * The resulting elements are sorted with the specified comparator.
     *
     * @param comparator the comparator, or null for {@link Comparable natural ordering}.
     * @param iterator the iterator.
     * @return a persistent sorted set containing the unique elements of the iteration in sorted order.
     * @throws NullPointerException if any element is null and the comparator is either null or does not permit nulls,
     *                              or the {@code iterator} itself is null.
     * @throws ClassCastException if any element is of a type not compatible for comparison.
     */
    public static <E> BasicConstSortedSet<E> asSortedSet(
        Comparator<? super E> comparator,
        Iterator<? extends E> iterator) {

        return condense(comparator, unionInto(EMPTY_OBJECT_ARRAY, copy(iterator), comparator));
    }

    /**
     * Instantiates the appropriate AbstractBasicConstSortedSet implementation from the specified array of elements.
     * The array reference <b>must be trusted</b>:
     * <ol>
     *     <li>the array was defensively copied or is guaranteed to be invisible to external clients</li>
     *     <li>the component type is Object instead of a narrower type such as String or Integer</li>
     *     <li><i>the array contains only unique elements</i></li>
     *     <li><i>the array is already sorted using the specified comparator</i></li>
     * </ol>
     *
     * @param trustedElements the Object array of elements.
     * @return a size-appropriate implementation of AbstractBasicConstSet.
     */
    static <E> BasicConstSortedSet<E> condense(Comparator<? super E> comparator, Object[] trustedElements) {
        assert trustedElements.getClass() == Object[].class;
        switch (trustedElements.length) {
            case 0: return BasicSortedSet0.instance(comparator);
            case 1: return new BasicSortedSet1<>(comparator, trustedElements[0]);
            default: return new BasicSortedSetN<>(comparator, trustedElements);
        }
    }


    // -------------------------------------------------------------------------
    // Abstract implementation

    final Comparator<? super E> comparator;

    BasicConstSortedSet(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    @Override public Comparator<? super E> comparator() {
        return comparator;
    }

    int compare(E left, E right) {
        return ObjectTools.compare(left, right, comparator);
    }

    // -------------------------------------------------------------------------
    // Java serialization support

    Object writeReplace() {
        return new SortedSetProxy(this);
    }

    private void readObject(ObjectInputStream in) throws InvalidObjectException {
        throw new InvalidObjectException("proxy expected");
    }

    private static final long serialVersionUID = 1;
}
