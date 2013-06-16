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

package net.nullschool.grains;

import net.nullschool.collect.AbstractEntry;
import net.nullschool.collect.AbstractIterableMap;
import net.nullschool.collect.MapIterator;

import java.util.NoSuchElementException;


/**
 * 2013-03-13<p/>
 *
 * A partial implementation of GrainBuilder that provides a MapIterator implementation.
 *
 * @author Cameron Beccario
 */
public abstract class AbstractGrainBuilder extends AbstractIterableMap<String, Object> implements GrainBuilder {

    /**
     * {@inheritDoc}
     *
     * <p/>When implemented, this method's return type should be changed to return the specific grain type this
     * builder builds.
     */
    @Override public abstract Grain build();

    @Override public abstract Object get(Object key);  // the iterator uses this, so must be defined by implementers

    @Override public abstract Object remove(Object key);  // the iterator uses this, so must be defined by implementers

    /**
     * An implementation of MapIterator that iterates over an array of basis keys provided by implementers of
     * AbstractGrainBuilder.
     */
    protected class BasisIter implements MapIterator<String, Object> {

        private static final int BAD = -1;

        private final String[] keys;  // the keys to iterator over
        private int next = 0;  // the index of the key that will be the next result of next()
        private int cursor = BAD;  // the index of the key that was most recently returned by next(), or BAD if none

        public BasisIter(String[] keys) {
            this.keys = keys;
        }

        @Override public boolean hasNext() {
            return next != keys.length;
        }

        @Override public String next() {
            final int i = next;
            if (next != keys.length) {    // if there is a next key
                next = i + 1;             // advance to the next key
                return keys[cursor = i];  // and remember the current position
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
            if (i == BAD) {
                throw new IllegalStateException();
            }
            AbstractGrainBuilder.this.remove(keys[i]);
            cursor = BAD;  // the current position has been removed
        }
    }
}
