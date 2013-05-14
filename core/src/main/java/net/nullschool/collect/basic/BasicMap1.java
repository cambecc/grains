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

import java.util.*;

import static net.nullschool.collect.basic.BasicConstMap.condense;
import static net.nullschool.collect.basic.BasicTools.*;

/**
 * 2013-03-17<p/>
 *
 * @author Cameron Beccario
 */
final class BasicMap1<K, V> extends AbstractBasicConstMap<K, V> {

    private final K k0;
    private final V v0;

    @SuppressWarnings("unchecked")
    BasicMap1(Object k0, Object v0) {
        this.k0 = (K)k0;
        this.v0 = (V)v0;
    }

    @Override public int size() {
        return 1;
    }

    @Override public boolean isEmpty() {
        return false;
    }

    @Override public boolean containsKey(Object key) {
        return Objects.equals(key, k0);
    }

    @Override public boolean containsValue(Object value) {
        return Objects.equals(value, v0);
    }

    @Override K getKey(int index) {
        if (index == 0) {
            return k0;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override V getValue(int index) {
        if (index == 0) {
            return v0;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override public V get(Object key) {
        return Objects.equals(key, k0) ? v0 : null;
    }

    @Override public ConstSet<K> keySet() {
        return BasicConstSet.setOf(k0);
    }

    @Override public ConstCollection<V> values() {
        return BasicConstList.listOf(v0);
    }

    @Override public ConstSet<Entry<K, V>> entrySet() {
        MapIterator<K, V> iter = iterator();
        iter.next();
        return BasicConstSet.setOf(iter.entry());
    }

    @Override public ConstMap<K, V> with(K key, V value) {
        if (Objects.equals(key, k0)) {
            if (Objects.equals(value, v0)) {
                return this;
            }
            return new BasicMap1<>(k0, value);
        }
        return new BasicMapN<>(new Object[] {k0, key}, new Object[] {v0, value});
    }

    @Override public ConstMap<K, V> withAll(Map<? extends K, ? extends V> map) {
        if (map.isEmpty()) {
            return this;
        }
        MapColumns mc = copy(map);
        return condense(unionInto(new Object[] {k0}, new Object[] {v0}, mc.keys, mc.values));
    }

    @Override public ConstMap<K, V> without(Object key) {
        return !containsKey(key) ? this : BasicConstMap.<K, V>emptyMap();
    }

    @Override public ConstMap<K, V> withoutAll(Collection<?> keys) {
        return !keys.contains(k0) ? this : BasicConstMap.<K, V>emptyMap();
    }

    @Override public int hashCode() {
        return AbstractEntry.hashCode(k0, v0);
    }
}
