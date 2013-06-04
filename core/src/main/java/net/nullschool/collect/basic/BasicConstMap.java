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


/**
 * 2013-03-17<p/>
 *
 * A {@link ConstMap} that uses arrays to store its entries, providing a memory efficient implementation of ConstMap
 * but with O(N) complexity for most map query operations, and O(N^2) complexity for most map construction operations.
 * This maps allows {@code null} keys and values, and uses {@link Object#equals} and {@link Object#hashCode} to test
 * for map membership.<p/>
 *
 * Note that this map is not sorted. See {@link BasicConstSortedMap} for a ConstSortedMap implementation.<p/>
 *
 * See {@link BasicCollections} for utility methods that construct instances of this map.
 *
 * @see BasicCollections#emptyMap
 * @see BasicCollections#mapOf
 *
 * @author Cameron Beccario
 */
public abstract class BasicConstMap<K, V> extends AbstractIterableMap<K, V> implements ConstMap<K, V>, Serializable {

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
