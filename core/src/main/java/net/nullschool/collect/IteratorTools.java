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

package net.nullschool.collect;

import java.util.*;
import java.util.Map.Entry;

/**
 * 2013-02-14<p/>
 *
 * A set of utilities for working with iterators.
 *
 * @author Cameron Beccario
 */
public enum IteratorTools {;

    /**
     * A MapIterator that has no iterations.
     */
    private static final class EmptyMapIterator<K, V> implements MapIterator<K, V> {
        private static final EmptyMapIterator INSTANCE = new EmptyMapIterator();

        @Override public boolean hasNext() { return false; }
        @Override public K next() { throw new NoSuchElementException(); }
        @Override public V value() { throw new IllegalStateException(); }
        @Override public Entry<K, V> entry() { throw new IllegalStateException(); }
        @Override public void remove() { throw new IllegalStateException(); }
    }

    /**
     * Returns a MapIterator that has no elements.
     *
     * @param <K> the key type.
     * @param <V> the value type.
     * @return an empty MapIterator.
     */
    @SuppressWarnings("unchecked")
    public static <K, V> MapIterator<K, V> emptyMapIterator() {
        // Immutable singleton instance can be safely casted to any desired type arguments.
        return (MapIterator<K, V>)EmptyMapIterator.INSTANCE;
    }

    /**
     * Converts an iterator of map entries (such as those from the {@link Map#entrySet() entrySet} of a map)
     * into an iterator having MapIterator behavior.
     */
    private static final class MapIteratorAdapter<K, V> implements MapIterator<K, V> {

        private final Iterator<Entry<K, V>> inner;
        private volatile Entry<K, V> current;

        private MapIteratorAdapter(Iterator<Entry<K, V>> inner) {
            this.inner = inner;
        }

        private Entry<K, V> getCurrent() {
            if (current != null) {
                return current;
            }
            throw new IllegalStateException();
        }

        @Override public boolean hasNext() { return inner.hasNext(); }
        @Override public K next() { return (current = inner.next()).getKey(); }
        @Override public V value() { return getCurrent().getValue(); }
        @Override public Entry<K, V> entry() { return getCurrent(); }
        @Override public void remove() {
            inner.remove();
            current = null;
        }
    }

    /**
     * Returns a new iterator over the specified map as a {@link MapIterator}. If the map is an {@link IterableMap},
     * then this method invokes {@link IterableMap#iterator()}, otherwise an adaptor on top of an iterator from the
     * map's {@link Map#entrySet() entrySet} is returned. If the map is empty, {@link #emptyMapIterator()} is returned.
     *
     * @param map the map for which to construct a new MapIterator.
     * @param <K> the key type.
     * @param <V> the value type.
     * @return a new MapIterator for the map.
     * @throws NullPointerException if map is null.
     */
    public static <K, V> MapIterator<K, V> newMapIterator(Map<K, V> map) {
        if (map.isEmpty()) {
            return emptyMapIterator();
        }
        return map instanceof IterableMap ?
            ((IterableMap<K, V>)map).iterator() :
            new MapIteratorAdapter<>(map.entrySet().iterator());
    }

    /**
     * Chains two MapIterator instances into one iteration.
     */
    private static final class DualMapIterator<K, V> implements MapIterator<K, V> {

        // The iterator currently being used.
        private MapIterator<K, V> currentIterator;
        // The next iterator in the chain, or null if the end of the chain is reached.
        private MapIterator<K, V> nextIterator;

        private DualMapIterator(MapIterator<K, V> first, MapIterator<K, V> second) {
            this.currentIterator = first;
            this.nextIterator = second;
        }

        @Override public boolean hasNext() {
            do {
                if (currentIterator.hasNext()) {
                    return true;
                }
                // Current iterator is done. Advance to the next iterator and try again.
                if (nextIterator == null) {
                    return false;  // End of the chain reached.
                }
                currentIterator = nextIterator;
                nextIterator = null;
            } while (true);
        }

        @Override public K next() {
            do {
                try {
                    return currentIterator.next();
                }
                catch (NoSuchElementException e) {
                    // Someone called next() without calling hasNext(). Not best practice usage, but need to handle it.
                    // Current iterator is done. Advance to the next iterator and try again.
                    if (nextIterator == null) {
                        throw e;  // End of the chain reached. Rethrow the exception thrown by the current iterator.
                    }
                    currentIterator = nextIterator;
                    nextIterator = null;
                }
            } while (true);
        }

        @Override public V value() { return currentIterator.value(); }
        @Override public Map.Entry<K, V> entry() { return currentIterator.entry(); }
        @Override public void remove() { currentIterator.remove(); }
    }

    /**
     * Chains two MapIterator instances into one iteration. The resulting iterator iterates over all elements
     * from the first iterator, then successively iterates over all elements from the second iterator.
     *
     * @param first the first iterator.
     * @param second the second iterator.
     * @param <K> the key type.
     * @param <V> the value type.
     * @return an iterator that combines two separate iterators into one.
     * @throws NullPointerException if either argument is null.
     */
    public static <K, V> MapIterator<K, V> chainMapIterators(MapIterator<K, V> first, MapIterator<K, V> second) {
        if (first == null || second == null) {
            throw new NullPointerException();
        }
        return second == EmptyMapIterator.INSTANCE ?
            first :
            first == EmptyMapIterator.INSTANCE ?
                second :
                new DualMapIterator<>(first, second);
    }
}
