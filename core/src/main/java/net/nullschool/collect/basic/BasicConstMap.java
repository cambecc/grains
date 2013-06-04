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

import java.io.*;
import java.util.*;

import static net.nullschool.collect.basic.BasicTools.*;
import static net.nullschool.util.ArrayTools.EMPTY_OBJECT_ARRAY;

/**
 * 2013-03-17<p/>
 *
 * Utility methods for constructing instances of {@link ConstMap} that use arrays to store their entries, providing
 * a memory efficient implementation of ConstMap but with O(N) complexity for most map query operations, and O(N^2)
 * complexity for most map construction operations. These maps allow {@code null} keys and values, and use
 * {@link Object#equals} and {@link Object#hashCode} to test for map membership.<p/>
 *
 * Note that these maps are not sorted. See {@link BasicConstSortedMap} to construct instances of ConstSortedMap.
 *
 * @author Cameron Beccario
 */
public abstract class BasicConstMap<K, V> extends AbstractIterableMap<K, V> implements ConstMap<K, V>, Serializable {

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
        return condense(
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
        return condense(
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
        return condense(
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
        return condense(unionInto(EMPTY_OBJECT_ARRAY, EMPTY_OBJECT_ARRAY, keys, values));
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
        return condense(unionInto(EMPTY_OBJECT_ARRAY, EMPTY_OBJECT_ARRAY, mc.keys, mc.values));
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
    static <K, V> BasicConstMap<K, V> condense(MapColumns trustedColumns) {
        return condense(trustedColumns.keys, trustedColumns.values);
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
    static <K, V> BasicConstMap<K, V> condense(Object[] trustedKeys, Object[] trustedValues) {
        assert trustedKeys.getClass() == Object[].class;
        assert trustedValues.getClass() == Object[].class;
        assert trustedKeys.length == trustedValues.length;
        switch (trustedKeys.length) {
            case 0: return BasicMap0.instance();
            case 1: return new BasicMap1<>(trustedKeys[0], trustedValues[0]);
            default: return new BasicMapN<>(trustedKeys, trustedValues);
        }
    }


    // -------------------------------------------------------------------------
    // Abstract implementation

    BasicConstMap() {
    }

    abstract K getKey(int index);

    abstract V getValue(int index);

    private class Iter implements MapIterator<K, V> {

        private final int maxIndex = size() - 1;
        private int cursor = -1;

        @Override public boolean hasNext() {
            return cursor != maxIndex;
        }

        @Override public K next() {
            final int i = cursor;
            if (i != maxIndex) {
                return getKey(cursor = i + 1);
            }
            throw new NoSuchElementException();
        }

        @Override public V value() {
            final int i = cursor;
            if (i >= 0) {
                return getValue(i);
            }
            throw new IllegalStateException();
        }

        @Override public Map.Entry<K, V> entry() {
            final int i = cursor;
            if (i >= 0) {
                return new AbstractMap.SimpleImmutableEntry<>(getKey(i), getValue(i));
            }
            throw new IllegalStateException();
        }

        @Override public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override public MapIterator<K, V> iterator() {
        return new Iter();
    }

    @Override public abstract ConstSet<K> keySet();

    @Override public abstract ConstCollection<V> values();

    protected abstract class BasicConstEntriesView extends EntriesView implements ConstSet<Map.Entry<K, V>> {

        // -------------------------------------------------------------------------
        // Mutation methods marked final, always throw UnsupportedOperationException

        @Deprecated @Override public final boolean add(Map.Entry<K, V> entry)                      { throw unsupported(); }
        @Deprecated @Override public final boolean addAll(Collection<? extends Map.Entry<K, V>> c) { throw unsupported(); }
        @Deprecated @Override public final boolean remove(Object o)                            { throw unsupported(); }
        @Deprecated @Override public final boolean removeAll(Collection<?> c)                  { throw unsupported(); }
        @Deprecated @Override public final boolean retainAll(Collection<?> c)                  { throw unsupported(); }
        @Deprecated @Override public final void clear()                                        { throw unsupported(); }
    }

    @Override public abstract ConstSet<Map.Entry<K, V>> entrySet();

    // -------------------------------------------------------------------------
    // Mutation methods marked final, always throw UnsupportedOperationException

    @Deprecated @Override public final V put(K key, V value)                            { throw unsupported(); }
    @Deprecated @Override public final void putAll(Map<? extends K, ? extends V> map)   { throw unsupported(); }
    @Deprecated @Override protected final boolean removeKey(Object key)                 { throw unsupported(); }
    @Deprecated @Override protected final boolean removeValue(Object value)             { throw unsupported(); }
    @Deprecated @Override protected final boolean removeEntry(Object key, Object value) { throw unsupported(); }
    @Deprecated @Override public final V remove(Object key)                             { throw unsupported(); }
    @Deprecated @Override public final void clear()                                     { throw unsupported(); }

    private static UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException();
    }

    // -------------------------------------------------------------------------
    // Java serialization support

    Object writeReplace() {
        return new MapProxy(this);
    }

    private void readObject(ObjectInputStream in) throws InvalidObjectException {
        throw new InvalidObjectException("proxy expected");
    }

    private static final long serialVersionUID = 1;
}
