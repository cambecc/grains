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
import net.nullschool.util.ArrayTools;

import java.util.*;

import static net.nullschool.collect.basic.BasicTools.*;
import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-04-29<p/>
 *
 * @author Cameron Beccario
 */
@PublicInterfaceRef(BasicConstSortedMap.class)
final class BasicSortedMapN<K, V> extends BasicConstSortedMap<K, V> {

    private final K[] keys;
    private final V[] values;

    @SuppressWarnings("unchecked")
    BasicSortedMapN(Comparator<? super K> comparator, Object[] keys, Object[] values) {
        super(comparator);
        assert keys.getClass() == Object[].class;
        assert values.getClass() == Object[].class;
        assert keys.length == values.length;
        assert keys.length > 1;
        this.keys = (K[])keys;
        this.values = (V[])values;
    }

    @Override public int size() {
        return keys.length;
    }

    private int indexOf(Object o) {
        // Cast to K is safe here because the comparator will do type checking when the compare method is invoked.
        @SuppressWarnings("unchecked") K k = (K)o;
        return Arrays.binarySearch(keys, k, comparator);
    }

    @Override public boolean containsKey(Object key) {
        return indexOf(key) >= 0;
    }

    @Override public boolean containsValue(Object value) {
        return ArrayTools.indexOf(value, values) >= 0;
    }

    @Override protected boolean containsEntry(Object key, Object value) {
        int index = indexOf(key);
        return index >= 0 && Objects.equals(value, values[index]);
    }

    @Override K getKey(int index) {
        return keys[index];
    }

    @Override V getValue(int index) {
        return values[index];
    }

    @Override public V get(Object key) {
        int index = indexOf(key);
        return index >= 0 ? values[index] : null;
    }

    @Override public K firstKey() {
        return keys[0];
    }

    @Override public K lastKey() {
        return keys[keys.length - 1];
    }

    @Override public ConstSortedSet<K> keySet() {
        return condenseToSortedSet(comparator, keys);
    }

    @Override public ConstCollection<V> values() {
        return condenseToList(values);
    }

    @Override public ConstSet<Entry<K, V>> entrySet() {
        // UNDONE: this is the same as BasicMapN...
        return new BasicConstEntriesView() {

            @Override public ConstSet<Entry<K, V>> with(Entry<K, V> entry) {
                // UNDONE: conversion to standard set means the equality test has changed. The set of entries
                //         may no longer be a proper set. Is this the case? Need to uniquify here?
                return contains(entry) ? this : BasicCollections.<Entry<K, V>>condenseToSet(toArray()).with(entry);
            }

            @Override public ConstSet<Entry<K, V>> withAll(Collection<? extends Entry<K, V>> c) {
                return c.isEmpty() ? this : BasicCollections.<Entry<K, V>>condenseToSet(toArray()).withAll(c);
            }

            @Override public ConstSet<Entry<K, V>> without(Object entry) {
                return !contains(entry) ? this : BasicCollections.<Entry<K, V>>condenseToSet(toArray()).without(entry);
            }

            @Override public ConstSet<Entry<K, V>> withoutAll(Collection<?> c) {
                return c.isEmpty() ? this : BasicCollections.<Entry<K, V>>condenseToSet(toArray()).withoutAll(c);
            }
        };
    }

    @Override public ConstSortedMap<K, V> with(K key, V value) {
        int index = indexOf(key);
        if (index >= 0) {
            if (Objects.equals(value, values[index])) {
                return this;
            }
            return new BasicSortedMapN<>(comparator, keys, BasicTools.replace(values, index, value));
        }
        index = flip(index);
        return new BasicSortedMapN<>(comparator, insert(keys, index, key), insert(values, index, value));
    }

    @Override public ConstSortedMap<K, V> withAll(Map<? extends K, ? extends V> map) {
        if (map.isEmpty()) {
            return this;
        }
        MapColumns mc = copy(map);
        return condenseToSortedMap(comparator, unionInto(keys, values, mc.keys, mc.values, comparator));
    }

    @Override public ConstSortedMap<K, V> without(Object key) {
        int index = indexOf(key);
        return index < 0 ?
            this :
            BasicCollections.<K, V>condenseToSortedMap(comparator, delete(keys, index), delete(values, index));
    }

    @Override public ConstSortedMap<K, V> withoutAll(Collection<?> keysToDelete) {
        if (keysToDelete.isEmpty()) {
            return this;
        }
        return condenseToSortedMap(comparator, deleteAll(keys, values, keysToDelete));
    }

    private ConstSortedMap<K, V> subMap(int fromIndex, int toIndex) {
        if (fromIndex < 0) {
            fromIndex = flip(fromIndex);
        }
        if (toIndex < 0) {
            toIndex = flip(toIndex);
        }
        if (fromIndex == 0 && toIndex == keys.length) {
            return this;
        }
        return condenseToSortedMap(
            comparator,
            Arrays.copyOfRange(keys, fromIndex, toIndex),
            Arrays.copyOfRange(values, fromIndex, toIndex));
    }

    @Override public ConstSortedMap<K, V> headMap(K toKey) {
        return subMap(0, indexOf(toKey));
    }

    @Override public ConstSortedMap<K, V> tailMap(K fromKey) {
        return subMap(indexOf(fromKey), keys.length);
    }

    @Override public ConstSortedMap<K, V> subMap(K fromKey, K toKey) {
        if (compare(fromKey, toKey) > 0) {
            throw new IllegalArgumentException("fromKey cannot be greater than toKey");
        }
        return subMap(indexOf(fromKey), indexOf(toKey));
    }

    @Override public int hashCode() {
        // UNDONE: this is the same as BasicMapN...
        int result = 0;
        for (int i = 0; i < keys.length; i++) {
            result += AbstractEntry.hashCode(keys[i], values[i]);
        }
        return result;
    }
}
