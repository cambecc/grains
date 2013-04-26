package net.nullschool.collect;

import java.util.*;


/**
 * 2013-02-15<p/>
 *
 * A persistent (i.e., immutable) {@link IterableMap}. See a description of persistent data structures on
 * <a href="http://en.wikipedia.org/wiki/Persistent_data_structures">Wikipedia</a>.<p/> A ConstMap instance
 * is guaranteed to never visibly change. New associations can be created or removed using the {@link #with},
 * {@link #without}, and related methods, which allocate a new instance if necessary while leaving the original
 * instance unchanged.
 *
 * Because a ConstMap is effectively immutable, the {@link #put}, {@link #putAll}, {@link #remove}, and
 * {@link #clear} methods inherited from {@link Map} always throw {@link UnsupportedOperationException}. They
 * are marked <i>deprecated</i> to signify to the developer that they should not be invoked. Furthermore, the
 * {@link #keySet}, {@link #values}, and {@link #entrySet} views are also persistent and cannot be used to mutate
 * the underlying map, either via their iterators' {@link Iterator#remove} method or the {@link Map.Entry#setValue}
 * method.
 *
 * @param <K> the key type
 * @param <V> the value type
 *
 * @author Cameron Beccario
 */
public interface ConstMap<K, V> extends IterableMap<K, V> {

    /**
     * Returns a ConstMap containing the associations of this map plus the specified key-value association.
     * No visible change to this map occurs. In this way, the <i>with</i> method may be considered a factory
     * method for creating new instances of ConstMap.<p/>
     *
     * If this map already contains the desired association, i.e., if the resulting map R would otherwise
     * equal this map, {@code R.equals(this)}, then the method may simply return this map, unchanged.
     *
     * @param key the key to associate
     * @param value the value to be associated with the key
     * @return a ConstMap containing this map's associations conjoined with the specified association.
     * @throws ClassCastException if the key or value is of a type not suitable for this or the resulting map.
     * @throws NullPointerException if the key or value is null and this or the resulting map does not allow nulls.
     * @throws IllegalArgumentException if some property of the key or value is not suitable for this or the
     *                                  resulting map.
     */
    ConstMap<K, V> with(K key, V value);

    /**
     * Returns a ConstMap containing the associations of this map plus all the associations of the specified
     * map. No visible change in this map occurs. In this way, the <i>withAll</i> method may be considered a
     * factory method for creating new instances of ConstMap.<p/>
     *
     * The effect of this call is equivalent to invoking {@link #with} for each entry in the specified map. If
     * this map already contains the desired associations, i.e., if the resulting map R would otherwise
     * equal this map, {@code R.equals(this)}, then the method may simply return this map, unchanged.
     *
     * @param map mappings to conjoin with the associations in this map
     * @return a ConstMap containing this map's associations conjoined with the associations in the provided map.
     * @throws ClassCastException if a key or value in the specified map is of a type not suitable for this or
     *                            the resulting map.
     * @throws NullPointerException if the specified map is null, or if a key or value in the specified map is
     *                              null and either this or the resulting map does not allow nulls.
     * @throws IllegalArgumentException if some property of a key or value in the specified map is not suitable
     *                                  for this or the resulting map.
     */
    ConstMap<K, V> withAll(Map<? extends K, ? extends V> map);

    /**
     * Returns a ConstMap containing the associations of this map minus the mapping for the specified key.
     * No visible change to this map occurs. In this way, the <i>without</i> method may be considered a factory
     * method for creating new instances of ConstMap.<p/>
     *
     * If this map does not contain the specified key, i.e., if the resulting map R would otherwise
     * equal this map, {@code R.equals(this)}, then the method may simply return this map, unchanged.
     *
     * @param key the key to de-associate.
     * @return a ConstMap containing this map's associations disjoined from the specified association.
     * @throws ClassCastException if the key is of a type not suitable for this map.
     * @throws NullPointerException if the key is null and this map does not allow nulls.
     */
    ConstMap<K, V> without(Object key);

    /**
     * Returns a ConstMap containing the associations of this map minus all the mappings for the specified
     * keys. No visible change in this map occurs. In this way, the <i>withoutAll</i> method may be considered a
     * factory method for creating new instances of ConstMap.<p/>
     *
     * The effect of this call is equivalent to invoking {@link #without} for each key in the specified
     * collection. If this map does not contain any of the keys, i.e., if the resulting map R would otherwise
     * equal this map, {@code R.equals(this)}, then the method may simply return this map, unchanged.
     *
     * @param keys the keys to de-associate from this map
     * @return a ConstMap containing this map's associations disjoined from the keys in the provided collection.
     * @throws ClassCastException if a key in the specified collection is of a type not suitable for this or
     *                            the resulting map.
     * @throws NullPointerException if the specified collection is null, or if a key in the specified collection
     *                              is null and either this or the resulting map does not allow nulls.
     */
    ConstMap<K, V> withoutAll(Collection<?> keys);

    /**
     * Returns a {@link ConstSet} containing this map's keys. Because it is persistent, the key set should not be
     * considered a "view" of this map. For example, invocations of this set's {@link ConstSet#with} method have
     * no effect on this map and result in a {@link ConstSet} instance that is effectively independent.
     *
     * @return this map's keys as a persistent set.
     */
    ConstSet<K> keySet();

    /**
     * Returns a {@link ConstCollection} containing this map's values. Because it is persistent, the value
     * collection should not be considered a "view" of this map. For example, invocations of this collection's
     * {@link ConstCollection#with} method have no effect on this map and result in a {@link ConstCollection}
     * instance that is effectively independent.
     *
     * @return this map's values as a persistent collection.
     */
    ConstCollection<V> values();

    /**
     * Returns a {@link ConstSet} containing this map's entries. Because it is persistent, the entry set should
     * not be considered a "view" of this map. For example, invocations of this set's {@link ConstSet#with} method
     * have no effect on this map and result in a {@link ConstSet} instance that is effectively independent.
     *
     * @return this map's entries as a persistent set.
     */
    ConstSet<Entry<K, V>> entrySet();

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
