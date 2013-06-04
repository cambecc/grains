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

/**
 * 2013-03-17<p/>
 *
 * @author Cameron Beccario
 */
@PublicInterfaceRef(BasicConstMap.class)
final class BasicMapN<K, V> extends BasicConstMap<K, V>  {

    private final K[] keys;
    private final V[] values;

    @SuppressWarnings("unchecked")
    BasicMapN(Object[] keys, Object[] values) {
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

    @Override public boolean containsKey(Object key) {
        return ArrayTools.indexOf(key, keys) >= 0;
    }

    @Override public boolean containsValue(Object value) {
        return ArrayTools.indexOf(value, values) >= 0;
    }

    @Override protected boolean containsEntry(Object key, Object value) {
        int index = ArrayTools.indexOf(key, keys);
        return index >= 0 && Objects.equals(value, values[index]);
    }

    @Override K getKey(int index) {
        return keys[index];
    }

    @Override V getValue(int index) {
        return values[index];
    }

    @Override public V get(Object key) {
        int index = ArrayTools.indexOf(key, keys);
        return index >= 0 ? values[index] : null;
    }

    @Override public ConstSet<K> keySet() {
        return BasicConstSet.condense(keys);
    }

    @Override public ConstCollection<V> values() {
        return BasicConstList.condense(values);
    }

    @Override public ConstSet<Entry<K, V>> entrySet() {
        return new BasicConstEntriesView() {

            @Override public ConstSet<Entry<K, V>> with(Entry<K, V> entry) {
                return contains(entry) ? this : BasicConstSet.<Entry<K, V>>condense(toArray()).with(entry);
            }

            @Override public ConstSet<Entry<K, V>> withAll(Collection<? extends Entry<K, V>> c) {
                return c.isEmpty() ? this : BasicConstSet.<Entry<K, V>>condense(toArray()).withAll(c);
            }

            @Override public ConstSet<Entry<K, V>> without(Object entry) {
                return !contains(entry) ? this : BasicConstSet.<Entry<K, V>>condense(toArray()).without(entry);
            }

            @Override public ConstSet<Entry<K, V>> withoutAll(Collection<?> c) {
                return c.isEmpty() ? this : BasicConstSet.<Entry<K, V>>condense(toArray()).withoutAll(c);
            }
        };
    }

    @Override public ConstMap<K, V> with(K key, V value) {
        final int index = ArrayTools.indexOf(key, keys);
        if (index >= 0) {
            if (Objects.equals(value, values[index])) {
                return this;
            }
            return new BasicMapN<>(keys, replace(values, index, value));
        }
        final int length = keys.length;
        return new BasicMapN<>(insert(keys, length, key), insert(values, length, value));
    }

    @Override public ConstMap<K, V> withAll(Map<? extends K, ? extends V> map) {
        if (map.isEmpty()) {
            return this;
        }
        MapColumns mc = copy(map);
        return condense(unionInto(keys, values, mc.keys, mc.values));
    }

    @Override public ConstMap<K, V> without(Object key) {
        int index = ArrayTools.indexOf(key, keys);
        return index < 0 ? this : BasicConstMap.<K, V>condense(delete(keys, index), delete(values, index));
    }

    @Override public ConstMap<K, V> withoutAll(Collection<?> keysToDelete) {
        if (keysToDelete.isEmpty()) {
            return this;
        }
        return condense(deleteAll(keys, values, keysToDelete));
    }

    @Override public int hashCode() {
        int result = 0;
        for (int i = 0; i < keys.length; i++) {
            result += AbstractEntry.hashCode(keys[i], values[i]);
        }
        return result;
    }
}
