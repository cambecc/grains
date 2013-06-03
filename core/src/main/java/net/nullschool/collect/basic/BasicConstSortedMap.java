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

import net.nullschool.collect.*;
import net.nullschool.util.ObjectTools;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.*;

import static net.nullschool.collect.basic.BasicTools.*;
import static net.nullschool.util.ArrayTools.EMPTY_CLASS_ARRAY;
import static net.nullschool.util.ArrayTools.EMPTY_OBJECT_ARRAY;


/**
 * 2013-04-29<p/>
 *
 * Utility methods for constructing instances of {@link ConstSortedMap} that use arrays to store their entries,
 * providing a memory efficient implementation of ConstSortedMap but with O(log(N)) complexity for most map query
 * operations, and O(N) complexity for most map construction operations. Map membership is determined using
 * {@link Comparator#compare}, or {@link Comparable natural ordering} if the comparator is {@code null}. These maps
 * allow {@code null} keys only if the associated {@link Comparator} allows nulls.
 *
 * @author Cameron Beccario
 */
public abstract class BasicConstSortedMap<K, V> extends BasicConstMap<K, V> implements ConstSortedMap<K, V> {

    /**
     * Returns an empty ConstSortedMap with the ordering of the specified comparator.
     *
     * @param comparator the comparator, or null for {@link Comparable natural ordering}.
     * @return a persistent empty sorted map.
     */
    public static <K, V> ConstSortedMap<K, V> emptySortedMap(Comparator<? super K> comparator) {
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
    public static <K, V> ConstSortedMap<K, V> sortedMapOf(Comparator<? super K> comparator, K k0, V v0) {
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
    public static <K, V> ConstSortedMap<K, V> sortedMapOf(Comparator<? super K> comparator, K k0, V v0, K k1, V v1) {
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
    public static <K, V> ConstSortedMap<K, V> sortedMapOf(
        Comparator<? super K> comparator,
        K k0, V v0,
        K k1, V v1,
        K k2, V v2) {

        return condense(
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
    public static <K, V> ConstSortedMap<K, V> sortedMapOf(
        Comparator<? super K> comparator,
        K k0, V v0,
        K k1, V v1,
        K k2, V v2,
        K k3, V v3) {

        return condense(
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
    public static <K, V> ConstSortedMap<K, V> sortedMapOf(
        Comparator<? super K> comparator,
        K k0, V v0,
        K k1, V v1,
        K k2, V v2,
        K k3, V v3,
        K k4, V v4) {

        return condense(
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
    public static <K, V> ConstSortedMap<K, V> asSortedMap(Comparator<? super K> comparator, K[] keys, V[] values) {
        return condense(comparator, unionInto(EMPTY_OBJECT_ARRAY, EMPTY_OBJECT_ARRAY, keys, values, comparator));
    }

    /**
     * Converts the specified sorted map into a ConstSortedMap with the same entries and ordering.
     *
     * @param map the sorted map.
     * @return a persistent sorted map containing the exact entries and ordering of the specified map.
     * @throws NullPointerException if {@code map} is null.
     */
    public static <K, V> ConstSortedMap<K, V> asSortedMap(SortedMap<K, V> map) {
        if (map instanceof BasicConstSortedMap) {
            return (ConstSortedMap<K, V>)map;  // The map is already a ConstSortedMap.
        }
        return condense(map.comparator(), copy(map));
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
    public static <K, V> ConstSortedMap<K, V> asSortedMap(
        Comparator<? super K> comparator,
        Map<? extends K, ? extends V> map) {

        if (map instanceof SortedMap) {
            @SuppressWarnings("unchecked") SortedMap<K, V> covariant = (SortedMap<K, V>)map;
            if (Objects.equals(comparator, covariant.comparator())) {
                return asSortedMap(covariant);  // the map is already in the desired sorted order.
            }
        }
        MapColumns mc = copy(map);
        return condense(comparator, unionInto(EMPTY_OBJECT_ARRAY, EMPTY_OBJECT_ARRAY, mc.keys, mc.values, comparator));
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
    static <K, V> BasicConstSortedMap<K, V> condense(
        Comparator<? super K> comparator,
        MapColumns trustedColumns) {

        return condense(comparator, trustedColumns.keys, trustedColumns.values);
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
    static <K, V> BasicConstSortedMap<K, V> condense(
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


    // -------------------------------------------------------------------------
    // Abstract implementation

    final Comparator<? super K> comparator;

    BasicConstSortedMap(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }

    @Override public Comparator<? super K> comparator() {
        return comparator;
    }

    int compare(K left, K right) {
        return ObjectTools.compare(left, right, comparator);
    }

    @Override public abstract ConstSortedSet<K> keySet();

    @Override public abstract ConstCollection<V> values();

    @Override public abstract ConstSet<Map.Entry<K, V>> entrySet();

    // -------------------------------------------------------------------------
    // Java serialization support

    Object writeReplace() {
        return new SortedMapProxy(this);
    }

    private void readObject(ObjectInputStream in) throws InvalidObjectException {
        throw new InvalidObjectException("proxy expected");
    }

    private static final long serialVersionUID = 1;
}
