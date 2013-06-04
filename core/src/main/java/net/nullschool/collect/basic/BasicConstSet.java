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

import net.nullschool.collect.AbstractUnmodifiableIterator;
import net.nullschool.collect.ConstSet;

import java.io.*;
import java.util.*;


/**
 * 2013-03-16<p/>
 *
 * A {@link ConstSet} that uses an array to store its elements, providing a memory efficient implementation of ConstSet
 * but with O(N) complexity for most set query operations, and O(N^2) complexity for most set construction operations.
 * This set allows {@code null} elements and uses {@link Object#equals} and {@link Object#hashCode} to test for set
 * membership.<p/>
 *
 * Note that this set is not sorted. See {@link BasicConstSortedSet} for a ConstSortedSet implementation.<p/>
 *
 * See {@link BasicCollections} for utility methods that construct instances of this set.
 *
 * @see BasicCollections#emptySet
 * @see BasicCollections#setOf
 *
 * @author Cameron Beccario
 */
public abstract class BasicConstSet<E> extends AbstractSet<E> implements ConstSet<E>, Serializable {

    BasicConstSet() {
    }

    abstract E get(int index);

    private class Iter extends AbstractUnmodifiableIterator<E> {

        private final int size = size();
        private int i = 0;

        @Override public boolean hasNext() {
            return i < size;
        }

        @Override public E next() {
            if (i < size) {
                return get(i++);
            }
            throw new NoSuchElementException();
        }
    }

    @Override public Iterator<E> iterator() {
        return new Iter();
    }

    // -------------------------------------------------------------------------
    // Mutation methods marked final, always throw UnsupportedOperationException

    @Deprecated @Override public final boolean add(E e)                          { throw unsupported(); }
    @Deprecated @Override public final boolean addAll(Collection<? extends E> c) { throw unsupported(); }
    @Deprecated @Override public final boolean remove(Object o)                  { throw unsupported(); }
    @Deprecated @Override public final boolean removeAll(Collection<?> c)        { throw unsupported(); }
    @Deprecated @Override public final boolean retainAll(Collection<?> c)        { throw unsupported(); }
    @Deprecated @Override public final void clear()                              { throw unsupported(); }

    private static UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException();
    }

    // -------------------------------------------------------------------------
    // Java serialization support

    Object writeReplace() {
        return new SetProxy(this);
    }

    private void readObject(ObjectInputStream in) throws InvalidObjectException {
        throw new InvalidObjectException("proxy expected");
    }

    private static final long serialVersionUID = 1;
}
