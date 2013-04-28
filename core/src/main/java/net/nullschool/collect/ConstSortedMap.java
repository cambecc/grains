package net.nullschool.collect;

import java.util.Collection;
import java.util.Map;
import java.util.SortedMap;


/**
 * 2013-04-04<p/>
 *
 * A persistent (i.e., immutable) {@link SortedMap}. See a description of persistent data structures on
 * <a href="http://en.wikipedia.org/wiki/Persistent_data_structures">Wikipedia</a>.<p/> A ConstMap instance
 * is guaranteed to never visibly change. New associations can be created or removed using the {@link #with},
 * {@link #without}, and related methods, which allocate a new instance if necessary while leaving the original
 * instance unchanged.<p/>
 *
 * Because a ConstSortedMap is effectively immutable, the {@link #put}, {@link #putAll}, {@link #remove}, and
 * {@link #clear} methods inherited from {@link SortedMap} always throw {@link UnsupportedOperationException}. They
 * are marked <i>deprecated</i> to signify to the developer that they should not be invoked. Furthermore, the
 * {@link #keySet}, {@link #values}, and {@link #entrySet} views are also persistent and cannot be used to mutate
 * the underlying map, either via their iterators' {@link java.util.Iterator#remove} method or the
 * {@link Entry#setValue} method.
 *
 * @param <K> the key type
 * @param <V> the value type
 *
 * @author Cameron Beccario
 */
public interface ConstSortedMap<K, V> extends SortedMap<K, V>, ConstMap<K, V> {

    /**
     * See {@link ConstMap#with}.
     *
     * @param key the key to associate
     * @param value the value to be associated with the key
     * @return a ConstSortedMap containing this map's associations conjoined with the specified association.
     * @throws ClassCastException if the key or value is of a type not suitable for this or the resulting map.
     * @throws NullPointerException if the key or value is null and this or the resulting map does not allow nulls.
     * @throws IllegalArgumentException if some property of the key or value is not suitable for this or the
     *                                  resulting map.
     */
    @Override ConstSortedMap<K, V> with(K key, V value);

    /**
     * See {@link ConstMap#with}.
     *
     * @param map mappings to conjoin with the associations in this map
     * @return a ConstSortedMap containing this map's associations conjoined with the associations in the provided map.
     * @throws ClassCastException if a key or value in the specified map is of a type not suitable for this or
     *                            the resulting map.
     * @throws NullPointerException if the specified map is null, or if a key or value in the specified map is
     *                              null and either this or the resulting map does not allow nulls.
     * @throws IllegalArgumentException if some property of a key or value in the specified map is not suitable
     *                                  for this or the resulting map.
     */
    @Override ConstSortedMap<K, V> withAll(Map<? extends K, ? extends V> map);

    /**
     * See {@link ConstMap#without}.
     *
     * @param key the key to de-associate.
     * @return a ConstSortedMap containing this map's associations disjoined from the specified association.
     * @throws ClassCastException if the key is of a type not suitable for this map.
     * @throws NullPointerException if the key is null and this map does not allow nulls.
     */
    @Override ConstSortedMap<K, V> without(Object key);

    /**
     * See {@link ConstMap#withoutAll}.
     *
     * @param keys the keys to de-associate from this map.
     * @return a ConstMap containing this map's associations disjoined from the keys in the provided collection.
     * @throws ClassCastException if a key in the specified collection is of a type not suitable for this or
     *                            the resulting map.
     * @throws NullPointerException if the specified collection is null, or if a key in the specified collection
     *                              is null and either this or the resulting map does not allow nulls.
     */
    @Override ConstSortedMap<K, V> withoutAll(Collection<?> keys);

    /**
     * Returns a new ConstSortedMap containing all the entries of this map whose key is less than {@code toKey}.
     *
     * @param toKey the end point (exclusive) of the resulting map.
     * @return a ConstSortedMap containing this map's entries up to but not including the specified key.
     * @throws ClassCastException if the key is of a type not suitable for this map.
     * @throws NullPointerException if the key is null and this map does not allow nulls.
     */
    @Override ConstSortedMap<K, V> headMap(K toKey);

    /**
     * Returns a new ConstSortedMap containing all the entries of this map whose key is greater than or equal to
     * {@code fromKey}.
     *
     * @param fromKey the start point (inclusive) of the resulting map.
     * @return a ConstSortedMap containing this map's entries from the specified key.
     * @throws ClassCastException if the key is of a type not suitable for this map.
     * @throws NullPointerException if the key is null and this map does not allow nulls.
     */
    @Override ConstSortedMap<K, V> tailMap(K fromKey);

    /**
     * Returns a new ConstSortedMap containing all the entries of this map whose key is greater than or equal to
     * {@code fromKey} and less than {@code toKey}.
     *
     * @param fromKey the start point (inclusive) of the resulting map.
     * @param toKey the end point (exclusive) of the resulting map.
     * @return a ConstSortedMap containing this map's entries between fromKey (inclusive) and toKey (exclusive).
     * @throws ClassCastException if either key is of a type not suitable for this map.
     * @throws NullPointerException if either key is null and this map does not allow nulls.
     * @throws IllegalArgumentException if fromKey is greater than toKey.
     */
    @Override ConstSortedMap<K, V> subMap(K fromKey, K toKey);

    /**
     * Returns a {@link ConstSortedSet} containing this map's keys. Because it is persistent, the key set should not
     * be considered a "view" of this map. For example, invocations of this set's {@link ConstSortedSet#with} method
     * have no effect on this map and result in a {@link ConstSortedSet} instance that is effectively independent.
     *
     * @return this map's keys as a persistent sorted set.
     */
    @Override ConstSortedSet<K> keySet();

    /**
     * Returns a {@link ConstCollection} containing this map's values. Because it is persistent, the value
     * collection should not be considered a "view" of this map. For example, invocations of this collection's
     * {@link ConstCollection#with} method have no effect on this map and result in a {@link ConstCollection}
     * instance that is effectively independent.
     *
     * @return this map's values as a persistent collection.
     */
    @Override ConstCollection<V> values();

    /**
     * Returns a {@link ConstSortedSet} containing this map's entries. Because it is persistent, the entry set should
     * not be considered a "view" of this map. For example, invocations of this set's {@link ConstSortedSet#with}
     * method have no effect on this map and result in a {@link ConstSortedSet} instance that is effectively
     * independent.
     *
     * @return this map's entries as a persistent sorted set.
     */
    @Override ConstSortedSet<Entry<K, V>> entrySet();

    // =================================================================================================================
    // Mutation methods marked @Deprecated to signify they should not be invoked.

    /**
     * This method always throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #with}
     */
    @Deprecated @Override V put(K key, V value);

    /**
     * This method always throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #withAll}
     */
    @Deprecated @Override void putAll(Map<? extends K, ? extends V> map);

    /**
     * This method always throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #without}
     */
    @Deprecated @Override V remove(Object key);

    /**
     * This method always throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #withoutAll}
     */
    @Deprecated @Override void clear();
}
