package net.nullschool.collect;

import java.util.*;


/**
 * 2013-02-12<p/>
 *
 * An iterator that iterates over the key-value associations of an {@link IterableMap} without requiring each
 * mapping to be represented as a {@link Map.Entry} object. This is suitable for {@link Map} implementations
 * which cannot efficiently represent their key-value associations as a set of {@link Map.Entry} instances (see
 * {@link Map#entrySet}).
 *
 * @param <K> the key type
 * @param <V> the value type
 *
 * @author Cameron Beccario
 */
public interface MapIterator<K, V> extends Iterator<K> {

    /**
     * Returns {@code true} if the iteration has more entries. When {@code true} is returned, then the subsequent
     * call to {@link #next} will not throw {@link NoSuchElementException}.
     *
     * @return {@code true} if the iteration has more entries.
     */
    @Override boolean hasNext();

    /**
     * Moves the iteration to the next entry and returns its key.
     *
     * @return the next entry's key
     * @throws NoSuchElementException if the iteration has no more entries.
     */
    @Override K next();

    /**
     * Returns the value of the entry last successfully moved to in this iteration. In other words, this method
     * returns the value associated with the key returned by the last successful invocation of {@link #next}.
     *
     * @return the value of this iteration's current entry.
     * @throws IllegalStateException if there is no current entry because {@link #next} has not been called or
     *                               the entry has been successfully removed (see {@link #remove}).
     */
    V value();

    /**
     * Returns the entry last successfully moved to in this iteration. In other words, this method returns the
     * entry associated with the key returned by the last successful invocation of {@link #next}.<p/>
     *
     * Implementer's note: the classes {@link AbstractEntry}, {@link AbstractMap.SimpleEntry}, and {@link
     * AbstractMap.SimpleImmutableEntry} provide implementations that satisfy the contract for {@link Map.Entry}.
     *
     * @return this iteration's current entry.
     * @throws IllegalStateException if there is no current entry because {@link #next} has not been called or
     *                               the entry has been successfully removed (see {@link #remove}).
     */
    Map.Entry<K, V> entry();

    /**
     * Removes the current entry from the map (optional operation). In other words, this method removes the
     * entry associated with the key returned by the last successful invocation of {@link #next}. The behavior
     * of this iterator is undefined if the underlying map is modified during iteration by any means other than
     * invocation of this method.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws IllegalStateException {@inheritDoc}
     */
    @Override void remove();
}
