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

import net.nullschool.util.ObjectTools;

import java.util.*;

import static net.nullschool.collect.basic.BasicTools.*;
import static net.nullschool.util.ArrayTools.EMPTY_CLASS_ARRAY;
import static net.nullschool.util.ArrayTools.EMPTY_OBJECT_ARRAY;


/**
 * 2013-06-04<p/>
 *
 * Utility methods for constructing the entire family of const collections that use basic arrays to store their
 * elements.
 *
 * @see BasicConstList
 * @see BasicConstSet
 * @see BasicConstSortedSet
 * @see BasicConstMap
 * @see BasicConstSortedMap
 *
 * @author Cameron Beccario
 */
public final class BasicCollections {

    private BasicCollections() {
        throw new AssertionError();
    }


    // -----------------------------------------------------------------------------------------------------------------
    // BasicConstList utility methods

    /**
     * Returns an empty ConstList.
     *
     * @return a persistent empty list.
     */
    public static <E> BasicConstList<E> emptyList() {
        return BasicList0.instance();
    }

    /**
     * Returns a ConstList with a single element.
     *
     * @param e0 the element.
     * @return a persistent list containing the specified element.
     */
    public static <E> BasicConstList<E> listOf(E e0) {
        return new BasicList1<>(e0);
    }

    /**
     * Returns a ConstList of two elements.
     *
     * @param e0 the first element.
     * @param e1 the second element.
     * @return a persistent list containing the specified elements.
     */
    public static <E> BasicConstList<E> listOf(E e0, E e1) {
        return new BasicListN<>(new Object[] {e0, e1});
    }

    /**
     * Returns a ConstList of three elements.
     *
     * @param e0 the first element.
     * @param e1 the second element.
     * @param e2 the third element.
     * @return a persistent list containing the specified elements.
     */
    public static <E> BasicConstList<E> listOf(E e0, E e1, E e2) {
        return new BasicListN<>(new Object[] {e0, e1, e2});
    }

    /**
     * Returns a ConstList of four elements.
     *
     * @return a persistent list containing the specified elements.
     */
    public static <E> BasicConstList<E> listOf(E e0, E e1, E e2, E e3) {
        return new BasicListN<>(new Object[] {e0, e1, e2, e3});
    }

    /**
     * Returns a ConstList of five elements.
     *
     * @return a persistent list containing the specified elements.
     */
    public static <E> BasicConstList<E> listOf(E e0, E e1, E e2, E e3, E e4) {
        return new BasicListN<>(new Object[] {e0, e1, e2, e3, e4});
    }

    /**
     * Returns a ConstList of many elements.
     *
     * @param additional all additional elements past the sixth element.
     * @return a persistent list containing the specified elements in the order they appear.
     */
    @SafeVarargs
    public static <E> BasicConstList<E> listOf(E e0, E e1, E e2, E e3, E e4, E e5, E... additional) {
        Object[] elements = new Object[6 + additional.length];
        elements[0] = e0;
        elements[1] = e1;
        elements[2] = e2;
        elements[3] = e3;
        elements[4] = e4;
        elements[5] = e5;
        System.arraycopy(additional, 0, elements, 6, additional.length);
        return new BasicListN<>(elements);
    }

    /**
     * Converts the specified array of elements into a ConstList.
     *
     * @param elements the elements of the list.
     * @return a persistent list containing the specified elements in the order they appear.
     * @throws NullPointerException if {@code elements} is null.
     */
    public static <E> BasicConstList<E> asList(E[] elements) {
        return condenseToList(copy(elements));
    }

    /**
     * Converts the specified collection into a ConstList. The list is constructed from the elements encountered while
     * iterating over the collection.
     *
     * @param collection the collection.
     * @return a persistent list containing the elements of the specified collection in the order they appear.
     * @throws NullPointerException if {@code collection} is null.
     */
    public static <E> BasicConstList<E> asList(Collection<? extends E> collection) {
        if (collection instanceof BasicConstList) {
            @SuppressWarnings("unchecked") BasicConstList<E> covariant = (BasicConstList<E>)collection;
            return covariant;  // The collection is already a ConstList.
        }
        return condenseToList(copy(collection));
    }

    /**
     * Constructs a ConstList from the sequence of elements encountered while iterating with the specified iterator.
     *
     * @param iterator the iterator.
     * @return a persistent list containing the elements of the iteration in the order they appear.
     * @throws NullPointerException if {@code iterator} is null.
     */
    public static <E> BasicConstList<E> asList(Iterator<? extends E> iterator) {
        return condenseToList(copy(iterator));
    }

    /**
     * Instantiates the appropriate AbstractBasicConstList implementation from the specified array of elements. The
     * array reference <b>must be trusted</b>:
     * <ol>
     *     <li>the array was defensively copied or is guaranteed to be invisible to external clients</li>
     *     <li>the component type is Object instead of a narrower type such as String or Integer</li>
     * </ol>
     *
     * @param trustedElements the Object array of elements.
     * @return a size-appropriate implementation of AbstractBasicConstList.
     */
    static <E> BasicConstList<E> condenseToList(Object[] trustedElements) {
        assert trustedElements.getClass() == Object[].class;
        switch (trustedElements.length) {
            case 0: return BasicList0.instance();
            case 1: return new BasicList1<>(trustedElements[0]);
            default: return new BasicListN<>(trustedElements);
        }
    }


    // -----------------------------------------------------------------------------------------------------------------
    // BasicConstSet utility methods

    /**
     * Returns an empty ConstSet.
     *
     * @return a persistent empty set.
     */
    public static <E> BasicConstSet<E> emptySet() {
        return BasicSet0.instance();
    }

    /**
     * Returns a ConstSet with a single element.
     *
     * @param e0 the element.
     * @return a persistent set containing the specified element.
     */
    public static <E> BasicConstSet<E> setOf(E e0) {
        return new BasicSet1<>(e0);
    }

    /**
     * Returns a ConstSet of up to two elements. If the second argument is equal to the first, then a ConstSet
     * containing just the first element is returned. {@link Object#equals} and {@link Object#hashCode} are used
     * to test for uniqueness.
     *
     * @param e0 the first element.
     * @param e1 the (potential) second element.
     * @return a persistent set containing the unique elements from the provided arguments in the order they appear.
     */
    public static <E> BasicConstSet<E> setOf(E e0, E e1) {
        return Objects.equals(e1, e0) ?
            new BasicSet1<E>(e0) :
            new BasicSetN<E>(new Object[] {e0, e1});
    }

    /**
     * Returns a ConstSet comprised of the unique elements from the provided arguments. {@link Object#equals} and
     * {@link Object#hashCode} are used to test for uniqueness.
     *
     * @param e0 the first element.
     * @param e1 the (potential) second element.
     * @param e2 the (potential) third element.
     * @return a persistent set containing the unique elements from the provided arguments in the order they appear.
     */
    public static <E> BasicConstSet<E> setOf(E e0, E e1, E e2) {
        return condenseToSet(unionInto(EMPTY_OBJECT_ARRAY, new Object[] {e0, e1, e2}));
    }

    /**
     * Returns a ConstSet comprised of the unique elements from the provided arguments. {@link Object#equals} and
     * {@link Object#hashCode} are used to test for uniqueness.
     *
     * @return a persistent set containing the unique elements from the provided arguments in the order they appear.
     */
    public static <E> BasicConstSet<E> setOf(E e0, E e1, E e2, E e3) {
        return condenseToSet(unionInto(EMPTY_OBJECT_ARRAY, new Object[] {e0, e1, e2, e3}));
    }

    /**
     * Returns a ConstSet comprised of the unique elements from the provided arguments. {@link Object#equals} and
     * {@link Object#hashCode} are used to test for uniqueness.
     *
     * @return a persistent set containing the unique elements from the provided arguments in the order they appear.
     */
    public static <E> BasicConstSet<E> setOf(E e0, E e1, E e2, E e3, E e4) {
        return condenseToSet(unionInto(EMPTY_OBJECT_ARRAY, new Object[] {e0, e1, e2, e3, e4}));
    }

    /**
     * Returns a ConstSet comprised of the unique elements from the provided arguments. {@link Object#equals} and
     * {@link Object#hashCode} are used to test for uniqueness.
     *
     * @param additional all additional elements past the sixth element.
     * @return a persistent set containing the unique elements from the provided arguments in the order they appear.
     */
    @SafeVarargs
    public static <E> BasicConstSet<E> setOf(E e0, E e1, E e2, E e3, E e4, E e5, E... additional) {
        Object[] elements = new Object[6 + additional.length];
        elements[0] = e0;
        elements[1] = e1;
        elements[2] = e2;
        elements[3] = e3;
        elements[4] = e4;
        elements[5] = e5;
        System.arraycopy(additional, 0, elements, 6, additional.length);
        return condenseToSet(unionInto(EMPTY_OBJECT_ARRAY, elements));
    }

    /**
     * Converts the specified array of elements into a ConstSet comprised of the unique elements from the array.
     * {@link Object#equals} and {@link Object#hashCode} are used to test for uniqueness.
     *
     * @param elements the elements from which to build the set.
     * @return a persistent set containing the unique elements from the array in the order they appear.
     * @throws NullPointerException if {@code elements} is null.
     */
    public static <E> BasicConstSet<E> asSet(E[] elements) {
        return condenseToSet(unionInto(EMPTY_OBJECT_ARRAY, elements));
    }

    /**
     * Converts the specified collection into a ConstSet comprised of the unique elements encountered while iterating
     * over the collection. {@link Object#equals} and {@link Object#hashCode} are used to test for uniqueness.
     *
     * @param collection the collection.
     * @return a persistent set containing the unique elements from the collection in the order they appear.
     * @throws NullPointerException if {@code collection} is null.
     */
    public static <E> BasicConstSet<E> asSet(Collection<? extends E> collection) {
        if (collection instanceof BasicConstSet && !(collection instanceof BasicConstSortedSet)) {
            @SuppressWarnings("unchecked") BasicConstSet<E> covariant = (BasicConstSet<E>)collection;
            return covariant;  // The collection is already a non-sorted ConstSet.
        }
        return condenseToSet(unionInto(EMPTY_OBJECT_ARRAY, collection.toArray()));
    }

    /**
     * Constructs a ConstSet from the unique elements encountered while iterating with the specified iterator.
     * {@link Object#equals} and {@link Object#hashCode} are used to test for uniqueness.
     *
     * @param iterator the iterator.
     * @return a persistent set containing the unique elements of the iteration in the order they appear.
     * @throws NullPointerException if {@code iterator} is null.
     */
    public static <E> BasicConstSet<E> asSet(Iterator<? extends E> iterator) {
        return condenseToSet(unionInto(EMPTY_OBJECT_ARRAY, copy(iterator)));
    }

    /**
     * Instantiates the appropriate AbstractBasicConstSet implementation from the specified array of elements. The
     * array reference <b>must be trusted</b>:
     * <ol>
     *     <li>the array was defensively copied or is guaranteed to be invisible to external clients</li>
     *     <li>the component type is Object instead of a narrower type such as String or Integer</li>
     *     <li><i>the array contains only unique elements</i></li>
     * </ol>
     *
     * @param trustedElements the Object array of elements.
     * @return a size-appropriate implementation of AbstractBasicConstSet.
     */
    static <E> BasicConstSet<E> condenseToSet(Object[] trustedElements) {
        assert trustedElements.getClass() == Object[].class;
        switch (trustedElements.length) {
            case 0: return BasicSet0.instance();
            case 1: return new BasicSet1<>(trustedElements[0]);
            default: return new BasicSetN<>(trustedElements);
        }
    }


    // -----------------------------------------------------------------------------------------------------------------
    // BasicConstSortedSet utility methods

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
        return condenseToSortedSet(comparator, unionInto(EMPTY_OBJECT_ARRAY, new Object[] {e0, e1, e2}, comparator));
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
        return condenseToSortedSet(
            comparator,
            unionInto(EMPTY_OBJECT_ARRAY, new Object[] {e0, e1, e2, e3}, comparator));
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

        return condenseToSortedSet(
            comparator,
            unionInto(EMPTY_OBJECT_ARRAY, new Object[] {e0, e1, e2, e3, e4}, comparator));
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
        return condenseToSortedSet(comparator, unionInto(EMPTY_OBJECT_ARRAY, elements, comparator));
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
        return condenseToSortedSet(comparator, unionInto(EMPTY_OBJECT_ARRAY, elements, comparator));
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
        return condenseToSortedSet(set.comparator(), set.toArray());
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
        return condenseToSortedSet(comparator, unionInto(EMPTY_OBJECT_ARRAY, collection.toArray(), comparator));
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

        return condenseToSortedSet(comparator, unionInto(EMPTY_OBJECT_ARRAY, copy(iterator), comparator));
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
    static <E> BasicConstSortedSet<E> condenseToSortedSet(Comparator<? super E> comparator, Object[] trustedElements) {
        assert trustedElements.getClass() == Object[].class;
        switch (trustedElements.length) {
            case 0: return BasicSortedSet0.instance(comparator);
            case 1: return new BasicSortedSet1<>(comparator, trustedElements[0]);
            default: return new BasicSortedSetN<>(comparator, trustedElements);
        }
    }


    // -----------------------------------------------------------------------------------------------------------------
    // BasicConstMap utility methods

    /**
     * Returns an empty ConstMap.
     *
     * @return a persistent empty map.
     */
    public static <K, V> BasicConstMap<K, V> emptyMap() {
        return BasicMap0.instance();
    }

    /**
     * Returns a ConstMap with a single entry.
     *
     * @param k0 the key.
     * @param v0 the value.
     * @return a persistent map containing the specified entry.
     */
    public static <K, V> BasicConstMap<K, V> mapOf(K k0, V v0) {
        return new BasicMap1<>(k0, v0);
    }

    /**
     * Returns a ConstMap comprised of the unique entries from the provided arguments. Duplicate keys are not retained,
     * but their associated values replace the values associated with existing keys, just as repeated calls to
     * {@link Map#put} behaves. {@link Object#equals} and {@link Object#hashCode} are used to test for uniqueness.
     *
     * @param k0 the first key.
     * @param v0 the first value.
     * @param k1 the second key.
     * @param v1 the second value.
     * @return a persistent map containing the unique entries from the provided arguments in the order they appear.
     */
    public static <K, V> BasicConstMap<K, V> mapOf(K k0, V v0, K k1, V v1) {
        return Objects.equals(k1, k0) ?
            new BasicMap1<K, V>(k0, v1) :
            new BasicMapN<K, V>(new Object[] {k0, k1}, new Object[] {v0, v1});
    }

    /**
     * Returns a ConstMap comprised of the unique entries from the provided arguments. Duplicate keys are not retained,
     * but their associated values replace the values associated with existing keys, just as repeated calls to
     * {@link Map#put} behaves. {@link Object#equals} and {@link Object#hashCode} are used to test for uniqueness.
     *
     * @return a persistent map containing the unique entries from the provided arguments in the order they appear.
     */
    public static <K, V> BasicConstMap<K, V> mapOf(K k0, V v0, K k1, V v1, K k2, V v2) {
        return condenseToMap(
            unionInto(
                EMPTY_OBJECT_ARRAY,
                EMPTY_OBJECT_ARRAY,
                new Object[] {k0, k1, k2},
                new Object[] {v0, v1, v2}));
    }

    /**
     * Returns a ConstMap comprised of the unique entries from the provided arguments. Duplicate keys are not retained,
     * but their associated values replace the values associated with existing keys, just as repeated calls to
     * {@link Map#put} behaves. {@link Object#equals} and {@link Object#hashCode} are used to test for uniqueness.
     *
     * @return a persistent map containing the unique entries from the provided arguments in the order they appear.
     */
    public static <K, V> BasicConstMap<K, V> mapOf(K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3) {
        return condenseToMap(
            unionInto(
                EMPTY_OBJECT_ARRAY,
                EMPTY_OBJECT_ARRAY,
                new Object[] {k0, k1, k2, k3},
                new Object[] {v0, v1, v2, v3}));
    }

    /**
     * Returns a ConstMap comprised of the unique entries from the provided arguments. Duplicate keys are not retained,
     * but their associated values replace the values associated with existing keys, just as repeated calls to
     * {@link Map#put} behaves. {@link Object#equals} and {@link Object#hashCode} are used to test for uniqueness.
     *
     * @return a persistent map containing the unique entries from the provided arguments in the order they appear.
     */
    public static <K, V> BasicConstMap<K, V> mapOf(K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return condenseToMap(
            unionInto(
                EMPTY_OBJECT_ARRAY,
                EMPTY_OBJECT_ARRAY,
                new Object[] {k0, k1, k2, k3, k4},
                new Object[] {v0, v1, v2, v3, v4}));
    }

    /**
     * Converts the specified arrays of keys and values into a ConstMap comprised of the unique entries represented
     * by these arrays, where each index i represents the entry {@code {keys[i], values[i]}}. Duplicate keys are not
     * retained, but their associated values replace the values associated with existing keys, just as repeated calls
     * to {@link Map#put} behaves. Extra keys or values, when the arrays differ in length, are ignored.
     * {@link Object#equals} and {@link Object#hashCode} are used to test for uniqueness.
     *
     * @param keys the map keys.
     * @param values the map values.
     * @return a persistent map containing the unique entries from the arrays in the order they appear.
     * @throws NullPointerException if {@code keys} or {@code values} is null.
     */
    public static <K, V> BasicConstMap<K, V> asMap(K[] keys, V[] values) {
        return condenseToMap(unionInto(EMPTY_OBJECT_ARRAY, EMPTY_OBJECT_ARRAY, keys, values));
    }

    /**
     * Converts the specified map into a ConstMap containing the unique entries encountered while iterating
     * over the specified map. {@link Object#equals} and {@link Object#hashCode} are used to test for uniqueness.
     *
     * @param map the map.
     * @return a persistent map containing the unique entries from the map in the order they appear.
     * @throws NullPointerException if {@code map} is null.
     */
    public static <K, V> BasicConstMap<K, V> asMap(Map<? extends K, ? extends V> map) {
        if (map instanceof BasicConstMap && !(map instanceof BasicConstSortedMap)) {
            @SuppressWarnings("unchecked") BasicConstMap<K, V> covariant = (BasicConstMap<K, V>)map;
            return covariant;  // The map is already a non-sorted ConstMap.
        }
        // Unfortunately, we must build the map from scratch. The provided map may have different uniqueness semantics.
        MapColumns mc = copy(map);
        return condenseToMap(unionInto(EMPTY_OBJECT_ARRAY, EMPTY_OBJECT_ARRAY, mc.keys, mc.values));
    }

    /**
     * Instantiates the appropriate AbstractBasicConstMap implementation from the specified columns. The embedded
     * columns <b>must be trusted</b>:
     * <ol>
     *     <li>the arrays were defensively copied or are guaranteed to be invisible to external clients</li>
     *     <li>the component type is Object instead of a narrower type such as String or Integer</li>
     *     <li><i>the keys column contains only unique keys</i></li>
     * </ol>
     *
     * @param trustedColumns the map columns.
     * @return a size-appropriate implementation of AbstractBasicConstMap.
     */
    static <K, V> BasicConstMap<K, V> condenseToMap(MapColumns trustedColumns) {
        return condenseToMap(trustedColumns.keys, trustedColumns.values);
    }

    /**
     * Instantiates the appropriate AbstractBasicConstMap implementation from the specified arrays of entries. The
     * array references <b>must be trusted</b>:
     * <ol>
     *     <li>the arrays were defensively copied or are guaranteed to be invisible to external clients</li>
     *     <li>the component type is Object instead of a narrower type such as String or Integer</li>
     *     <li><i>the keys array contains only unique keys</i></li>
     *     <li><i>the arrays are the same length</i></li>
     * </ol>
     *
     * @param trustedKeys the Object array of keys.
     * @param trustedValues the Object array of values.
     * @return a size-appropriate implementation of AbstractBasicConstMap.
     */
    static <K, V> BasicConstMap<K, V> condenseToMap(Object[] trustedKeys, Object[] trustedValues) {
        assert trustedKeys.getClass() == Object[].class;
        assert trustedValues.getClass() == Object[].class;
        assert trustedKeys.length == trustedValues.length;
        switch (trustedKeys.length) {
            case 0: return BasicMap0.instance();
            case 1: return new BasicMap1<>(trustedKeys[0], trustedValues[0]);
            default: return new BasicMapN<>(trustedKeys, trustedValues);
        }
    }


    // -----------------------------------------------------------------------------------------------------------------
    // BasicConstSortedMap utility methods

    /**
     * Returns an empty ConstSortedMap with the ordering of the specified comparator.
     *
     * @param comparator the comparator, or null for {@link Comparable natural ordering}.
     * @return a persistent empty sorted map.
     */
    public static <K, V> BasicConstSortedMap<K, V> emptySortedMap(Comparator<? super K> comparator) {
        return BasicSortedMap0.instance(comparator);
    }

    /**
     * Returns a ConstSortedMap with a single entry and the ordering of the specified comparator.
     *
     * @param comparator the comparator, or null for {@link Comparable natural ordering}.
     * @param k0 the key.
     * @param v0 the value.
     * @return a persistent sorted map containing the specified entry.
     * @throws NullPointerException if the key is null and the comparator is null or does not permit nulls.
     * @throws ClassCastException if the key is of a type not compatible for comparison.
     */
    public static <K, V> BasicConstSortedMap<K, V> sortedMapOf(Comparator<? super K> comparator, K k0, V v0) {
        return new BasicSortedMap1<>(comparator, checkType(comparator, k0), v0);
    }

    /**
     * Returns a ConstSortedMap comprised of the unique entries from the provided arguments, having the ordering
     * of the specified comparator. Duplicate keys are not retained, but their associated values replace the values
     * associated with existing keys, just as repeated calls to {@link Map#put} behaves.
     *
     * @param comparator the comparator, or null for {@link Comparable natural ordering}.
     * @param k0 the first key.
     * @param v0 the first value.
     * @param k1 the second key.
     * @param v1 the second value.
     * @return a persistent map containing the unique entries from the provided arguments.
     * @throws NullPointerException if any key is null and the comparator is null or does not permit nulls.
     * @throws ClassCastException if any key is of a type not compatible for comparison.
     */
    public static <K, V> BasicConstSortedMap<K, V> sortedMapOf(
        Comparator<? super K> comparator,
        K k0, V v0,
        K k1, V v1) {

        int cmp = ObjectTools.compare(k0, k1, comparator);
        return cmp == 0 ?
            new BasicSortedMap1<K, V>(comparator, k0, v1) :
            cmp < 0 ?
                new BasicSortedMapN<K, V>(comparator, new Object[] {k0, k1}, new Object[] {v0, v1}) :
                new BasicSortedMapN<K, V>(comparator, new Object[] {k1, k0}, new Object[] {v1, v0});
    }

    /**
     * Returns a ConstSortedMap comprised of the unique entries from the provided arguments, having the ordering
     * of the specified comparator. Duplicate keys are not retained, but their associated values replace the values
     * associated with existing keys, just as repeated calls to {@link Map#put} behaves.
     *
     * @param comparator the comparator, or null for {@link Comparable natural ordering}.
     * @return a persistent map containing the unique entries from the provided arguments.
     * @throws NullPointerException if any key is null and the comparator is null or does not permit nulls.
     * @throws ClassCastException if any key is of a type not compatible for comparison.
     */
    public static <K, V> BasicConstSortedMap<K, V> sortedMapOf(
        Comparator<? super K> comparator,
        K k0, V v0,
        K k1, V v1,
        K k2, V v2) {

        return condenseToSortedMap(
            comparator,
            unionInto(
                EMPTY_OBJECT_ARRAY,
                EMPTY_CLASS_ARRAY,
                new Object[] {k0, k1, k2},
                new Object[] {v0, v1, v2},
                comparator));
    }

    /**
     * Returns a ConstSortedMap comprised of the unique entries from the provided arguments, having the ordering
     * of the specified comparator. Duplicate keys are not retained, but their associated values replace the values
     * associated with existing keys, just as repeated calls to {@link Map#put} behaves.
     *
     * @param comparator the comparator, or null for {@link Comparable natural ordering}.
     * @return a persistent map containing the unique entries from the provided arguments.
     * @throws NullPointerException if any key is null and the comparator is null or does not permit nulls.
     * @throws ClassCastException if any key is of a type not compatible for comparison.
     */
    public static <K, V> BasicConstSortedMap<K, V> sortedMapOf(
        Comparator<? super K> comparator,
        K k0, V v0,
        K k1, V v1,
        K k2, V v2,
        K k3, V v3) {

        return condenseToSortedMap(
            comparator,
            unionInto(
                EMPTY_OBJECT_ARRAY,
                EMPTY_CLASS_ARRAY,
                new Object[] {k0, k1, k2, k3},
                new Object[] {v0, v1, v2, v3},
                comparator));
    }

    /**
     * Returns a ConstSortedMap comprised of the unique entries from the provided arguments, having the ordering
     * of the specified comparator. Duplicate keys are not retained, but their associated values replace the values
     * associated with existing keys, just as repeated calls to {@link Map#put} behaves.
     *
     * @param comparator the comparator, or null for {@link Comparable natural ordering}.
     * @return a persistent map containing the unique entries from the provided arguments.
     * @throws NullPointerException if any key is null and the comparator is null or does not permit nulls.
     * @throws ClassCastException if any key is of a type not compatible for comparison.
     */
    public static <K, V> BasicConstSortedMap<K, V> sortedMapOf(
        Comparator<? super K> comparator,
        K k0, V v0,
        K k1, V v1,
        K k2, V v2,
        K k3, V v3,
        K k4, V v4) {

        return condenseToSortedMap(
            comparator,
            unionInto(
                EMPTY_OBJECT_ARRAY,
                EMPTY_CLASS_ARRAY,
                new Object[] {k0, k1, k2, k3, k4},
                new Object[] {v0, v1, v2, v3, v4},
                comparator));
    }

    /**
     * Converts the specified arrays of keys and values into a ConstSortedMap comprised of the unique entries
     * represented by these arrays, where each index i represents the entry {@code {keys[i], values[i]}}, and having
     * the ordering of the specified comparator. Duplicate keys are not retained, but their associated values replace
     * the values associated with existing keys, just as repeated calls to {@link Map#put} behaves. Extra keys or
     * values, when the arrays differ in length, are ignored.
     *
     * @param comparator the comparator, or null for {@link Comparable natural ordering}.
     * @param keys the map keys.
     * @param values the map values.
     * @return a persistent sorted map containing the unique entries from the arrays.
     * @throws NullPointerException if any key is null and the comparator is null or does not permit nulls, or if
     *                              the {@code keys} or {@code values} array is null.
     * @throws ClassCastException if any key is of a type not compatible for comparison.
     */
    public static <K, V> BasicConstSortedMap<K, V> asSortedMap(Comparator<? super K> comparator, K[] keys, V[] values) {
        return condenseToSortedMap(
            comparator,
            unionInto(EMPTY_OBJECT_ARRAY, EMPTY_OBJECT_ARRAY, keys, values, comparator));
    }

    /**
     * Converts the specified sorted map into a ConstSortedMap with the same entries and ordering.
     *
     * @param map the sorted map.
     * @return a persistent sorted map containing the exact entries and ordering of the specified map.
     * @throws NullPointerException if {@code map} is null.
     */
    public static <K, V> BasicConstSortedMap<K, V> asSortedMap(SortedMap<K, V> map) {
        if (map instanceof BasicConstSortedMap) {
            return (BasicConstSortedMap<K, V>)map;  // The map is already a ConstSortedMap.
        }
        return condenseToSortedMap(map.comparator(), copy(map));
    }

    /**
     * Converts the specified map into a ConstSortedMap comprised of the unique entries from the map having the
     * ordering of the specified comparator.
     *
     * @param comparator the comparator, or null for {@link Comparable natural ordering}.
     * @param map the map.
     * @return a persistent sorted map containing the unique entries from the map in sorted order.
     * @throws NullPointerException if any key is null and the comparator is null or does not permit nulls, or if
     *                              the {@code map} is null.
     * @throws ClassCastException if any key is of a type not compatible for comparison.
     */
    public static <K, V> BasicConstSortedMap<K, V> asSortedMap(
        Comparator<? super K> comparator,
        Map<? extends K, ? extends V> map) {

        if (map instanceof SortedMap) {
            @SuppressWarnings("unchecked") SortedMap<K, V> covariant = (SortedMap<K, V>)map;
            if (Objects.equals(comparator, covariant.comparator())) {
                return asSortedMap(covariant);  // the map is already in the desired sorted order.
            }
        }
        MapColumns mc = copy(map);
        return condenseToSortedMap(
            comparator,
            unionInto(EMPTY_OBJECT_ARRAY, EMPTY_OBJECT_ARRAY, mc.keys, mc.values, comparator));
    }

    /**
     * Instantiates the appropriate AbstractBasicConstSortedMap implementation from the specified columns. The
     * embedded columns <b>must be trusted</b>:
     * <ol>
     *     <li>the arrays were defensively copied or are guaranteed to be invisible to external clients</li>
     *     <li>the component type is Object instead of a narrower type such as String or Integer</li>
     *     <li><i>the keys column contains only unique keys</i></li>
     *     <li><i>the arrays are already sorted using the specified comparator</i></li>
     * </ol>
     *
     * @param comparator the comparator, or null for {@link Comparable natural ordering}.
     * @param trustedColumns the map columns.
     * @return a size-appropriate implementation of AbstractBasicConstSortedMap.
     */
    static <K, V> BasicConstSortedMap<K, V> condenseToSortedMap(
        Comparator<? super K> comparator,
        MapColumns trustedColumns) {

        return condenseToSortedMap(comparator, trustedColumns.keys, trustedColumns.values);
    }

    /**
     * Instantiates the appropriate AbstractBasicConstSortedMap implementation from the specified arrays of entries.
     * The array references <b>must be trusted</b>:
     * <ol>
     *     <li>the arrays were defensively copied or are guaranteed to be invisible to external clients</li>
     *     <li>the component type is Object instead of a narrower type such as String or Integer</li>
     *     <li><i>the keys array contains only unique keys</i></li>
     *     <li><i>the arrays are already sorted using the specified comparator and are the same length</i></li>
     * </ol>
     *
     * @param comparator the comparator, or null for {@link Comparable natural ordering}.
     * @param trustedKeys the Object array of keys.
     * @param trustedValues the Object array of values.
     * @return a size-appropriate implementation of AbstractBasicConstSortedMap.
     */
    static <K, V> BasicConstSortedMap<K, V> condenseToSortedMap(
        Comparator<? super K> comparator,
        Object[] trustedKeys,
        Object[] trustedValues) {

        assert trustedKeys.getClass() == Object[].class;
        assert trustedValues.getClass() == Object[].class;
        assert trustedKeys.length == trustedValues.length;
        switch (trustedKeys.length) {
            case 0: return BasicSortedMap0.instance(comparator);
            case 1: return new BasicSortedMap1<>(comparator, trustedKeys[0], trustedValues[0]);
            default: return new BasicSortedMapN<>(comparator, trustedKeys, trustedValues);
        }
    }
}
