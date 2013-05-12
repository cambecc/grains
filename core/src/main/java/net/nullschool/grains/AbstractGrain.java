package net.nullschool.grains;

import net.nullschool.collect.*;
import net.nullschool.collect.basic.BasicConstList;
import net.nullschool.collect.basic.BasicConstSet;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.*;


/**
 * 2013-03-11<p/>
 *
 * A partial implementation of Grain that provides a MapIterator implementation and const behavior for the
 * {@link #keySet}, {@link #values}, and {@link #entrySet} views built on {@link BasicConstList} and
 * {@link BasicConstSet}.
 *
 * @author Cameron Beccario
 */
public abstract class AbstractGrain extends AbstractIterableMap<String, Object> implements Grain {

    /**
     * {@inheritDoc}
     *
     * <p/>This method must be overridden by AbstractGrain implementers as it is used for basis iteration.
     *
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    @Override public abstract Object get(Object key);

    protected class BasisIter implements MapIterator<String, Object> {

        private static final int BAD = -1;

        private final String[] keys;  // the keys to iterator over
        private final int maxIndex;  // the index of the last key
        private int cursor = BAD;  // the index of the key that was most recently returned by next(), or BAD if none

        public BasisIter(String[] keys) {
            this.keys = keys;
            this.maxIndex = keys.length - 1;
        }

        @Override public boolean hasNext() {
            return cursor != maxIndex;
        }

        @Override public String next() {
            final int i = cursor;
            if (i != maxIndex) {              // if we are not currently sitting at the last key
                return keys[cursor = i + 1];  // advance to the next key
            }
            throw new NoSuchElementException();
        }

        @Override public Object value() {
            final int i = cursor;
            if (i != BAD) {
                return get(keys[i]);
            }
            throw new IllegalStateException();
        }

        @Override public Entry<String, Object> entry() {
            final int i = cursor;
            if (i != BAD) {
                final String key = keys[i];
                return new SimpleImmutableEntry<>(key, get(key));
            }
            throw new IllegalStateException();
        }

        @Override public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // -------------------------------------------------------------------------
    // Mutation methods marked final, always throw UnsupportedOperationException

    @Deprecated @Override public final Object put(String key, Object value)             { throw unsupported(); }
    @Deprecated @Override public final void putAll(Map<? extends String, ?> map)        { throw unsupported(); }
    @Deprecated @Override protected final boolean removeKey(Object key)                 { throw unsupported(); }
    @Deprecated @Override protected final boolean removeValue(Object value)             { throw unsupported(); }
    @Deprecated @Override protected final boolean removeEntry(Object key, Object value) { throw unsupported(); }
    @Deprecated @Override public final Object remove(Object key)                        { throw unsupported(); }
    @Deprecated @Override public final void clear()                                     { throw unsupported(); }

    // =================================================================================================================
    // KeySet View

    protected class ConstKeysView extends KeysView implements ConstSet<String> {

        @Override public ConstSet<String> with(String key) {
            return contains(key) ? this : BasicConstSet.asSet(this).with(key);
        }

        @Override public ConstSet<String> withAll(Collection<? extends String> c) {
            return c.isEmpty() ? this : BasicConstSet.asSet(this).withAll(c);
        }

        @Override public ConstSet<String> without(Object key) {
            return !contains(key) ? this : BasicConstSet.asSet(this).without(key);
        }

        @Override public ConstSet<String> withoutAll(Collection<?> c) {
            return c.isEmpty() ? this : BasicConstSet.asSet(this).withoutAll(c);
        }

        // -------------------------------------------------------------------------
        // Mutation methods marked final, always throw UnsupportedOperationException

        @Deprecated @Override public final boolean add(String s)                          { throw unsupported(); }
        @Deprecated @Override public final boolean addAll(Collection<? extends String> c) { throw unsupported(); }
        @Deprecated @Override public final boolean remove(Object o)                       { throw unsupported(); }
        @Deprecated @Override public final boolean removeAll(Collection<?> c)             { throw unsupported(); }
        @Deprecated @Override public final boolean retainAll(Collection<?> c)             { throw unsupported(); }
        @Deprecated @Override public final void clear()                                   { throw unsupported(); }
    }

    @Override public ConstSet<String> keySet() {
        return new ConstKeysView();
    }

    // =================================================================================================================
    // Values View

    protected class ConstValuesView extends ValuesView implements ConstCollection<Object> {

        @Override public ConstCollection<Object> with(Object value) {
            return contains(value) ? this : BasicConstList.asList(this).with(value);
        }

        @Override public ConstCollection<Object> withAll(Collection<?> c) {
            return c.isEmpty() ? this : BasicConstList.asList(this).withAll(c);
        }

        @Override public ConstCollection<Object> without(Object value) {
            return !contains(value) ? this : BasicConstList.asList(this).without(value);
        }

        @Override public ConstCollection<Object> withoutAll(Collection<?> c) {
            return c.isEmpty() ? this : BasicConstList.asList(this).withoutAll(c);
        }

        // -------------------------------------------------------------------------
        // Mutation methods marked final, always throw UnsupportedOperationException

        @Deprecated @Override public final boolean add(Object s)              { throw unsupported(); }
        @Deprecated @Override public final boolean addAll(Collection<?> c)    { throw unsupported(); }
        @Deprecated @Override public final boolean remove(Object o)           { throw unsupported(); }
        @Deprecated @Override public final boolean removeAll(Collection<?> c) { throw unsupported(); }
        @Deprecated @Override public final boolean retainAll(Collection<?> c) { throw unsupported(); }
        @Deprecated @Override public final void clear()                       { throw unsupported(); }
    }

    @Override public ConstCollection<Object> values() {
        return new ConstValuesView();
    }

    // =================================================================================================================
    // EntrySet View

    protected class ConstEntriesView extends EntriesView implements ConstSet<Entry<String, Object>> {

        @Override public ConstSet<Entry<String, Object>> with(Entry<String, Object> entry) {
            return contains(entry) ? this : BasicConstSet.asSet(this).with(entry);
        }

        @Override public ConstSet<Entry<String, Object>> withAll(Collection<? extends Entry<String, Object>> c) {
            return c.isEmpty() ? this : BasicConstSet.asSet(this).withAll(c);
        }

        @Override public ConstSet<Entry<String, Object>> without(Object entry) {
            return !contains(entry) ? this : BasicConstSet.asSet(this).without(entry);
        }

        @Override public ConstSet<Entry<String, Object>> withoutAll(Collection<?> c) {
            return c.isEmpty() ? this : BasicConstSet.asSet(this).withoutAll(c);
        }

        // -------------------------------------------------------------------------
        // Mutation methods marked final, always throw UnsupportedOperationException

        @Deprecated
        @Override public final boolean addAll(Collection<? extends Entry<String, Object>> c) { throw unsupported(); }
        @Deprecated @Override public final boolean add(Entry<String, Object> entry)          { throw unsupported(); }
        @Deprecated @Override public final boolean remove(Object o)                          { throw unsupported(); }
        @Deprecated @Override public final boolean removeAll(Collection<?> c)                { throw unsupported(); }
        @Deprecated @Override public final boolean retainAll(Collection<?> c)                { throw unsupported(); }
        @Deprecated @Override public final void clear()                                      { throw unsupported(); }
    }

    @Override public ConstSet<Entry<String, Object>> entrySet() {
        return new ConstEntriesView();
    }


    private static UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException();
    }
}
