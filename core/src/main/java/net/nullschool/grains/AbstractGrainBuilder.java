package net.nullschool.grains;

import net.nullschool.collect.AbstractEntry;
import net.nullschool.collect.AbstractIterableMap;
import net.nullschool.collect.MapIterator;

import java.util.NoSuchElementException;


/**
 * 2013-03-13<p/>
 *
 * @author Cameron Beccario
 */
public abstract class AbstractGrainBuilder extends AbstractIterableMap<String, Object> implements GrainBuilder {

    @Override public abstract Object get(Object key);  // the iterator uses this

    @Override public abstract Object remove(Object key);  // the iterator uses this

    protected class BasisIter implements MapIterator<String, Object> {

        private final String[] keys;
        private int next;
        private int cursor = -1;

        public BasisIter(String[] keys) {
            this.keys = keys;
        }

        @Override public boolean hasNext() {
            return next != keys.length;
        }

        @Override public String next() {
            final int i = next;
            if (next != keys.length) {
                next = i + 1;
                return keys[cursor = i];
            }
            throw new NoSuchElementException();
        }

        @Override public Object value() {
            final int i = cursor;
            if (i >= 0) {
                return get(keys[i]);
            }
            throw new IllegalStateException();
        }

        @Override public Entry<String, Object> entry() {
            final int i = cursor;
            if (i >= 0) {
                final String key = keys[i];
                return new AbstractEntry<String, Object>() {
                    @Override public String getKey() { return key; }
                    @Override public Object getValue() { return get(key); }
                    @Override public Object setValue(Object value) { return put(key, value); }
                };
            }
            throw new IllegalStateException();
        }

        @Override public void remove() {
            final int i = cursor;
            if (i < 0) {
                throw new IllegalStateException();
            }
            AbstractGrainBuilder.this.remove(keys[i]);
            cursor = -1;
        }
    }
}
