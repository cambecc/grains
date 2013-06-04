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
import net.nullschool.reflect.PublicInterfaceRef;

import java.util.*;
import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-04-29<p/>
 *
 * @author Cameron Beccario
 */
@PublicInterfaceRef(BasicConstSortedMap.class)
final class BasicSortedMap0<K, V> extends BasicConstSortedMap<K, V> {

    private static final BasicSortedMap0 NATURAL_INSTANCE = new BasicSortedMap0<Object, Object>(null);

    @SuppressWarnings("unchecked")
    static <K, V> BasicSortedMap0<K, V> instance(Comparator<? super K> comparator) {
        return comparator == null ? NATURAL_INSTANCE : new BasicSortedMap0<>(comparator);
    }

    private BasicSortedMap0(Comparator<? super K> comparator) {
        super(comparator);
    }

    @Override public int size() {
        return 0;
    }

    @Override public boolean isEmpty() {
        return true;
    }

    @Override public MapIterator<K, V> iterator() {
        return IteratorTools.emptyMapIterator();
    }

    @Override public boolean containsKey(Object key) {
        return false;
    }

    @Override public boolean containsValue(Object value) {
        return false;
    }

    @Override K getKey(int index) {
        throw new IndexOutOfBoundsException();
    }

    @Override V getValue(int index) {
        throw new IndexOutOfBoundsException();
    }

    @Override public V get(Object key) {
        return null;
    }

    @Override public K firstKey() {
        throw new NoSuchElementException();
    }

    @Override public K lastKey() {
        throw new NoSuchElementException();
    }

    @Override public ConstSortedSet<K> keySet() {
        return emptySortedSet(comparator);
    }

    @Override public ConstCollection<V> values() {
        return emptyList();
    }

    @Override public ConstSet<Entry<K, V>> entrySet() {
        return emptySet();
    }

    @Override public ConstSortedMap<K, V> with(K key, V value) {
        return sortedMapOf(comparator, key, value);
    }

    @Override public ConstSortedMap<K, V> withAll(Map<? extends K, ? extends V> map) {
        return map.isEmpty() ? this : asSortedMap(comparator, map);
    }

    @Override public ConstSortedMap<K, V> without(Object key) {
        return this;
    }

    @Override public ConstSortedMap<K, V> withoutAll(Collection<?> keys) {
        Objects.requireNonNull(keys);
        return this;
    }

    @Override public ConstSortedMap<K, V> headMap(K toKey) {
        compare(toKey, toKey);  // type check
        return this;
    }

    @Override public ConstSortedMap<K, V> tailMap(K fromKey) {
        compare(fromKey, fromKey);  // type check
        return this;
    }

    @Override public ConstSortedMap<K, V> subMap(K fromKey, K toKey) {
        if (compare(fromKey, toKey) > 0) {
            throw new IllegalArgumentException("fromKey cannot be greater than toKey");
        }
        return this;
    }

    @Override public boolean equals(Object that) {
        return this == that || that instanceof Map && ((Map<?, ?>)that).isEmpty();
    }

    @Override public int hashCode() {
        return 0;
    }

    @Override public String toString() {
        return "{}";
    }
}
