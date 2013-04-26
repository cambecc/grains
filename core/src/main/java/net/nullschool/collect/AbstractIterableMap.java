package net.nullschool.collect;

import java.util.*;

/**
 * 2013-02-11<p/>
 *
 * An implementation of {@link IterableMap} that implements most operations on top of a {@link MapIterator}
 * (rather than {@link Map#entrySet} as {@link AbstractMap} does).<p/>
 *
 * By default, maps that extend this implementation are unmodifiable if the supplied {@link MapIterator}
 * implementation does not support {@link MapIterator#remove} and the entries returned by the iterator do
 * not support {@link Entry#setValue}. To make a mutable map, the programmer must implement these methods
 * and override the {@link #put} method on this class.<p/>
 *
 * Simple implementations of the {@link #keySet}, {@link #values}, and {@link #entrySet} views are provided,
 * with these views delegating most of their operations to {@link AbstractIterableMap} itself and its
 * accompanying {@link MapIterator}. Programmers should extend these view classes to override their behavior
 * when necessary, but doing so requires overriding the associated view's retrieval method.<p/>
 *
 * For efficiency, most programmers will override the {@link #get}, {@link #containsKey}, and {@link #containsEntry}
 * methods, and for mutable maps, the {@link #remove}, {@link #removeKey}, and {@link #removeEntry} methods.
 *
 * @param <K> the key type
 * @param <V> the value type
 *
 * @author Cameron Beccario
 */
public abstract class AbstractIterableMap<K, V> implements IterableMap<K, V> {

    /**
     * {@inheritDoc}
     */
    @Override public abstract int size();

    /**
     * {@inheritDoc}<p/>
     *
     * This implementation compares the {@link #size} to 0.
     */
    @Override public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override public abstract MapIterator<K, V> iterator();

    /**
     * {@inheritDoc}<p/>
     *
     * This implementation does a linear search over the keys returned by {@link MapIterator#next}. Most
     * programmers will override this method for maps whose implementations can provide sub-linear performance.
     *
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    @Override public boolean containsKey(Object key) {
        for (MapIterator<K, V> iter = iterator(); iter.hasNext();) {
            if (Objects.equals(key, iter.next())) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}<p/>
     *
     * This implementation does a linear search over the values returned by {@link MapIterator#value}.
     *
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    @Override public boolean containsValue(Object value) {
        for (MapIterator<K, V> iter = iterator(); iter.hasNext();) {
            iter.next();
            if (Objects.equals(value, iter.value())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if this map contains an association E where {@code Objects.equals(E.key, key) &&
     * Objects.equals(E.value, value)}.<p/>
     *
     * The default implementation does a linear search over the items returned by {@link MapIterator#next}
     * and {@link MapIterator#value}. This method is used primarily for the implementation of the
     * {@link #entrySet} view.
     *
     * @throws ClassCastException if the key or value is of an inappropriate type for this map
     * @throws NullPointerException if the specified key or value is null and this map does not permit nulls
     */
    protected boolean containsEntry(Object key, Object value) {
        for (MapIterator<K, V> iter = iterator(); iter.hasNext();) {
            if (Objects.equals(key, iter.next()) && Objects.equals(value, iter.value())) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}<p/>
     *
     * This implementation does a linear search over the keys returned by {@link MapIterator#next} and
     * returns the result of {@link MapIterator#value} if a match is found. Most programmers will override
     * this method for maps whose implementations can provide sub-linear performance.
     *
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    @Override public V get(Object key) {
        for (MapIterator<K, V> iter = iterator(); iter.hasNext();) {
            if (Objects.equals(key, iter.next())) {
                return iter.value();
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}<p/>
     *
     * This implementation always throws {@link UnsupportedOperationException}. Mutable map implementations
     * must override this method.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override public V put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}<p/>
     *
     * This implementation iterates over the associations of the specified map (using a {@link MapIterator}
     * when possible), successively calling {@link #put} on each association.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override public void putAll(Map<? extends K, ? extends V> map) {
        if (map instanceof IterableMap) {
            for (MapIterator<? extends K, ? extends V> iter = iteratorFor(map); iter.hasNext();) {
                put(iter.next(), iter.value());
            }
        }
        else {
            for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Removes the association E where {@code Objects.equals(E.key, key)}, returning true if such an
     * association is found.<p/>
     *
     * The default implementation does a linear search over the keys returned by {@link MapIterator#next},
     * and calls {@link MapIterator#remove} if a match is found. This method is used primarily for the
     * implementation of the {@link #keySet} view.
     *
     * @param key the key to remove.
     * @return if this map contained the specified key.
     * @throws UnsupportedOperationException if the remove operation is not supported by this map
     * @throws ClassCastException if the key is of an inappropriate type for this map
     * @throws NullPointerException if the specified key is null and this map does not permit nulls
     */
    protected boolean removeKey(Object key) {
        for (MapIterator<K, V> iter = iterator(); iter.hasNext();) {
            if (Objects.equals(key, iter.next())) {
                iter.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the first association E where {@code Objects.equals(E.value, value)}, returning true if
     * such an association is found.<p/>
     *
     * The default implementation does a linear search over the values returned by {@link MapIterator#value},
     * and calls {@link MapIterator#remove} if a match is found. This method is used primarily for the
     * implementation of the {@link #values} view.
     *
     * @param value the value to remove.
     * @return if this map contained the specified value.
     * @throws UnsupportedOperationException if the remove operation is not supported by this map
     * @throws ClassCastException if the value is of an inappropriate type for this map
     * @throws NullPointerException if the specified value is null and this map does not permit nulls
     */
    protected boolean removeValue(Object value) {
        for (MapIterator<K, V> iter = iterator(); iter.hasNext();) {
            iter.next();
            if (Objects.equals(value, iter.value())) {
                iter.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the association E where {@code Objects.equals(E.key, key) && Objects.equals(E.value, value)},
     * returning {@code true} if such an association is found.<p/>
     *
     * The default implementation does a linear search over the items returned by {@link MapIterator#next}
     * and {@link MapIterator#value}, and calls {@link MapIterator#remove} if a match is found. This method
     * is used primarily for the implementation of the {@link #entrySet} view.
     *
     * @param key the key to remove.
     * @param value the associated value to remove.
     * @return if this map contained the specified entry.
     * @throws UnsupportedOperationException if the remove operation is not supported by this map
     * @throws ClassCastException if the key or value is of an inappropriate type for this map
     * @throws NullPointerException if the specified key or value is null and this map does not permit nulls
     */
    protected boolean removeEntry(Object key, Object value) {
        for (MapIterator<K, V> iter = iterator(); iter.hasNext();) {
            if (Objects.equals(key, iter.next()) && Objects.equals(value, iter.value())) {
                iter.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}<p/>
     *
     * This implementation does a linear search over the keys returned by {@link MapIterator#next} and
     * calls {@link MapIterator#remove} if a match is found. Note that this method will throw
     * {@link UnsupportedOperationException} if the iterator does not support the remove operation. Before
     * the remove, the {@link MapIterator#value} is retrieved and returned as the result of this method.
     * Most programmers will override this method for mutable maps whose implementations can provide
     * sub-linear performance.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    @Override public V remove(Object key) {
        for (MapIterator<K, V> iter = iterator(); iter.hasNext();) {
            if (Objects.equals(key, iter.next())) {
                V value = iter.value();
                iter.remove();
                return value;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}<p/>
     *
     * This implementation successively calls {@link MapIterator#remove} on each item returned by this
     * map's iterator.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     */
    @Override public void clear() {
        for (MapIterator<K, V> iter = iterator(); iter.hasNext();) {
            iter.next();
            iter.remove();
        }
    }

    /**
     * Tests for map equality according to the rules required by {@link Map#equals}.
     *
     * @param that the map to test for equality.
     * @return true if the specified map is equal to this map.
     */
    private boolean equals(Map<?, ?> that) {
        if (this.size() != that.size()) {
            return false;
        }
        try {
            for (MapIterator<K, V> iter = iterator(); iter.hasNext();) {
                K thisKey = iter.next();
                V thisValue = iter.value();
                Object thatValue = that.get(thisKey);
                if (thatValue == null) {
                    if (thisValue != null || !that.containsKey(thisKey)) {
                        return false;
                    }
                }
                else if (!thisValue.equals(thatValue)) {
                    return false;
                }
            }
        }
        catch (ClassCastException | NullPointerException ignored) {
        }
        return true;
    }

    /**
     * {@inheritDoc}<p/>
     *
     * This implementation checks if the specified object is a {@link Map}, and if so, iterates
     * over the results returned by {@link MapIterator#next} and {@link MapIterator#value}, checking
     * if each association is contained within the specified map.
     */
    @Override public boolean equals(Object that) {
        return this == that || that instanceof Map && equals((Map<?, ?>)that);
    }

    /**
     * {@inheritDoc}<p/>
     *
     * This implementation iterates over the results returned by {@link MapIterator#next} and
     * {@link MapIterator#value} to calculate the hash code.
     */
    @Override public int hashCode() {
        int result = 0;
        for (MapIterator<K, V> iter = iterator(); iter.hasNext();) {
            result += AbstractEntry.hashCode(iter.next(), iter.value());
        }
        return result;
    }

    /**
     * Append a String representation of this map's entries to the specified StringBuilder.<p/>
     *
     * The default implementation of this method iterates over the results returned by {@link MapIterator#next}
     * and {@link MapIterator#value} and appends using the format: {@code "k1=v1, k2=v2, ..., kn=vn"}
     *
     * @param sb the string builder to append to
     */
    protected void printEntriesTo(StringBuilder sb) {
        MapIterator<K, V> iter = iterator();
        if (iter.hasNext()) {
            do {
                K key = iter.next();
                V value = iter.value();
                sb.append(key == this ? "(this Map)" : key);
                sb.append('=');
                sb.append(value == this ? "(this Map)" : value);
                if (!iter.hasNext()) {
                    break;
                }
                sb.append(',').append(' ');
            } while (true);
        }
    }

    /**
     * Returns a String representation of this map. This representation encases the results of calling
     * {@link #printEntriesTo} within curly braces '{' and '}'.
     */
    @Override public String toString() {
        int size = size();
        if (size == 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(8 + size * 8).append('{');
        printEntriesTo(sb);
        return sb.append('}').toString();
    }

    // =================================================================================================================
    // KeySet View

    /**
     * A simple view of this map's keys. This view delegates any operation to the owning map if the map
     * has an analogous operation, otherwise it uses the implementation inherited from {@link AbstractSet}.
     * For example, {@link KeysView#contains} simply delegates to {@link AbstractIterableMap#containsKey}.
     * All mutation operations, except {@link KeysView#add} and {@link KeysView#addAll}, are supported if
     * the underlying map supports them.
     */
    protected class KeysView extends AbstractSet<K> {

        /**
         * {@inheritDoc}<p/>
         *
         * This implementation delegates to {@link AbstractIterableMap#size}.
         */
        @Override public int size() {
            return AbstractIterableMap.this.size();
        }

        /**
         * {@inheritDoc}<p/>
         *
         * This implementation returns an iterator over the underlying map's keys by wrapping an
         * instance of {@link MapIterator} and returning the results of {@link MapIterator#next}.
         */
        @Override public Iterator<K> iterator() {
            final MapIterator<K, V> inner = AbstractIterableMap.this.iterator();
            return new Iterator<K>() {
                @Override public boolean hasNext() { return inner.hasNext(); }
                @Override public K next() { return inner.next(); }
                @Override public void remove() { inner.remove(); }
            };
        }

        /**
         * {@inheritDoc}<p/>
         *
         * This implementation delegates to {@link AbstractIterableMap#containsKey}.
         *
         * @throws ClassCastException {@inheritDoc}
         * @throws NullPointerException {@inheritDoc}
         */
        @Override public boolean contains(Object o) {
            return AbstractIterableMap.this.containsKey(o);
        }

        /**
         * {@inheritDoc}<p/>
         *
         * This implementation delegates to {@link AbstractIterableMap#removeKey}.
         *
         * @throws ClassCastException {@inheritDoc}
         * @throws NullPointerException {@inheritDoc}
         * @throws UnsupportedOperationException {@inheritDoc}
         */
        @Override public boolean remove(Object o) {
            return AbstractIterableMap.this.removeKey(o);
        }

        /**
         * {@inheritDoc}<p/>
         *
         * This implementation delegates to {@link AbstractIterableMap#clear}.
         *
         * @throws UnsupportedOperationException {@inheritDoc}
         */
        @Override public void clear() {
            AbstractIterableMap.this.clear();
        }
    }

    /**
     * {@inheritDoc}<p/>
     *
     * This implementation returns a new view on each invocation.
     */
    @Override public Set<K> keySet() {
        return new KeysView();
    }

    // =================================================================================================================
    // Values View

    /**
     * A simple view of this map's values. This view delegates any operation to the owning map if the map
     * has an analogous operation, otherwise it uses the implementation inherited from {@link AbstractCollection}.
     * For example, {@link ValuesView#contains} simply delegates to {@link AbstractIterableMap#containsValue}.
     * All mutation operations, except {@link ValuesView#add} and {@link ValuesView#addAll}, are supported if
     * the underlying map supports them.
     */
    protected class ValuesView extends AbstractCollection<V> {

        /**
         * {@inheritDoc}<p/>
         *
         * This implementation delegates to {@link AbstractIterableMap#size}.
         */
        @Override public int size() {
            return AbstractIterableMap.this.size();
        }

        /**
         * {@inheritDoc}<p/>
         *
         * This implementation returns an iterator over the underlying map's values by wrapping an
         * instance of {@link MapIterator} and returning the results of {@link MapIterator#value}.
         */
        @Override public Iterator<V> iterator() {
            final MapIterator<K, V> inner = AbstractIterableMap.this.iterator();
            return new Iterator<V>() {
                @Override public boolean hasNext() { return inner.hasNext(); }
                @Override public V next() {
                    inner.next();
                    return inner.value();
                }
                @Override public void remove() { inner.remove(); }
            };
        }

        /**
         * {@inheritDoc}<p/>
         *
         * This implementation delegates to {@link AbstractIterableMap#containsValue}.
         *
         * @throws ClassCastException {@inheritDoc}
         * @throws NullPointerException {@inheritDoc}
         */
        @Override public boolean contains(Object o) {
            return AbstractIterableMap.this.containsValue(o);
        }

        /**
         * {@inheritDoc}<p/>
         *
         * This implementation delegates to {@link AbstractIterableMap#removeValue}.
         *
         * @throws ClassCastException {@inheritDoc}
         * @throws NullPointerException {@inheritDoc}
         * @throws UnsupportedOperationException {@inheritDoc}
         */
        @Override public boolean remove(Object o) {
            return AbstractIterableMap.this.removeValue(o);
        }

        /**
         * {@inheritDoc}<p/>
         *
         * This implementation delegates to {@link AbstractIterableMap#clear}.
         *
         * @throws UnsupportedOperationException {@inheritDoc}
         */
        @Override public void clear() {
            AbstractIterableMap.this.clear();
        }
    }

    /**
     * {@inheritDoc}<p/>
     *
     * This implementation returns a new view on each invocation.
     */
    @Override public Collection<V> values() {
        return new ValuesView();
    }

    // =================================================================================================================
    // EntrySet View

    /**
     * A simple view of this map's entries. This view delegates any operation to the owning map if the map
     * has an analogous operation, otherwise it uses the implementation inherited from {@link AbstractSet}.
     * For example, {@link EntriesView#contains} simply delegates to {@link AbstractIterableMap#containsEntry}.
     * All mutation operations, except {@link EntriesView#add} and {@link EntriesView#addAll}, are supported if
     * the underlying map supports them.
     */
    protected class EntriesView extends AbstractSet<Entry<K, V>> {

        /**
         * {@inheritDoc}<p/>
         *
         * This implementation delegates to {@link AbstractIterableMap#size}.
         */
        @Override public int size() {
            return AbstractIterableMap.this.size();
        }

        /**
         * {@inheritDoc}<p/>
         *
         * This implementation returns an iterator over the underlying map's entries by wrapping an
         * instance of {@link MapIterator} and returning the results of {@link MapIterator#entry}.
         */
        @Override public Iterator<Entry<K, V>> iterator() {
            final MapIterator<K, V> inner = AbstractIterableMap.this.iterator();
            return new Iterator<Entry<K, V>>() {
                @Override public boolean hasNext() { return inner.hasNext(); }
                @Override public Entry<K, V> next() {
                    inner.next();
                    return inner.entry();
                }
                @Override public void remove() { inner.remove(); }
            };
        }

        /**
         * {@inheritDoc}<p/>
         *
         * This implementation delegates to {@link AbstractIterableMap#containsEntry}.
         *
         * @throws ClassCastException {@inheritDoc}
         * @throws NullPointerException {@inheritDoc}
         */
        @Override public boolean contains(Object o) {
            return o instanceof Entry &&
                AbstractIterableMap.this.containsEntry(((Entry<?, ?>)o).getKey(), ((Entry<?, ?>)o).getValue());
        }

        /**
         * {@inheritDoc}<p/>
         *
         * This implementation delegates to {@link AbstractIterableMap#removeEntry}.
         *
         * @throws ClassCastException {@inheritDoc}
         * @throws NullPointerException {@inheritDoc}
         * @throws UnsupportedOperationException {@inheritDoc}
         */
        @Override public boolean remove(Object o) {
            return o instanceof Entry &&
                AbstractIterableMap.this.removeEntry(((Entry<?, ?>)o).getKey(), ((Entry<?, ?>)o).getValue());
        }

        /**
         * {@inheritDoc}<p/>
         *
         * This implementation delegates to {@link AbstractIterableMap#clear}.
         *
         * @throws UnsupportedOperationException {@inheritDoc}
         */
        @Override public void clear() {
            AbstractIterableMap.this.clear();
        }

        /**
         * {@inheritDoc}<p/>
         *
         * Interestingly, by contract, {@code map.entrySet().hashCode() == map.hashCode()}, so this
         * implementation delegates to {@link AbstractIterableMap#hashCode}.
         */
        @Override public int hashCode() {
            return AbstractIterableMap.this.hashCode();
        }

        /**
         * Returns a String representation of this set of entries. This representation encases the results
         * of calling {@link #printEntriesTo} within square brackets '[' and ']'.
         */
        @Override public String toString() {
            int size = size();
            if (size == 0) {
                return "[]";
            }
            StringBuilder sb = new StringBuilder(8 + size * 8).append('[');
            printEntriesTo(sb);
            return sb.append(']').toString();
        }
    }

    /**
     * {@inheritDoc}<p/>
     *
     * This implementation returns a new view on each invocation.
     */
    @Override public Set<Entry<K, V>> entrySet() {
        return new EntriesView();
    }

    /**
     * Utility function that returns a MapIterator for the specified map. Hides wildcard noise.
     */
    private static <K, V> MapIterator<K, V> iteratorFor(Map<K, V> map) {
        return ((IterableMap<K, V>)map).iterator();
    }
}
