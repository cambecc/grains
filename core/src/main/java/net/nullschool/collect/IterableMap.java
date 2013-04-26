package net.nullschool.collect;

import java.util.Map;


/**
 * 2013-02-12<p/>
 *
 * A {@link Map} that provides iterations over its key-value associations via a {@link MapIterator}. Iterations
 * using a {@link MapIterator} can avoid costly iterations over the more conventional {@link #entrySet} when the
 * map implementation does not natively use instances of {@link Entry} for representing key-value associations.
 *
 * @author Cameron Beccario
 */
public interface IterableMap<K, V> extends Map<K, V> {

    /**
     * Returns an iterator over this map's key-value associations.
     *
     * @return a new iteration of this map's entries.
     */
    MapIterator<K, V> iterator();
}
