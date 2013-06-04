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
import net.nullschool.collect.ConstList;

import java.io.*;
import java.util.*;


/**
 * 2013-03-15<p/>
 *
 * Utility methods for constructing instances of {@link ConstList} that use arrays to store their elements,
 * providing a memory efficient implementation of ConstList but with O(N) complexity for most list operations. These
 * lists allow {@code null} elements.
 *
 * @author Cameron Beccario
 */
public abstract class BasicConstList<E> extends AbstractList<E> implements ConstList<E>, RandomAccess, Serializable {

    BasicConstList() {
    }

    private class Iter extends AbstractUnmodifiableIterator<E> {

        final int size = size();
        int i = 0;

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

    private class ListIter extends Iter implements ListIterator<E> {

        ListIter(int start) {
            if (start < 0 || size < start) {
                throw new IndexOutOfBoundsException();
            }
            this.i = start;
        }

        @Override public int nextIndex() {
            return i;
        }

        @Override public boolean hasPrevious() {
            return i > 0;
        }

        @Override public E previous() {
            if (i > 0) {
                return get(--i);
            }
            throw new NoSuchElementException();
        }

        @Override public int previousIndex() {
            return i - 1;
        }

        @Override public void set(E e) { throw new UnsupportedOperationException(); }
        @Override public void add(E e) { throw new UnsupportedOperationException(); }
    }

    @Override public ListIterator<E> listIterator(int start) {
        return new ListIter(start);
    }

    @Override public abstract ConstList<E> subList(int fromIndex, int toIndex);

    // -------------------------------------------------------------------------
    // Mutation methods marked final, always throw UnsupportedOperationException

    @Deprecated @Override public final boolean add(E e)                                     { throw unsupported(); }
    @Deprecated @Override public final void add(int index, E element)                       { throw unsupported(); }
    @Deprecated @Override public final boolean addAll(Collection<? extends E> c)            { throw unsupported(); }
    @Deprecated @Override public final boolean addAll(int index, Collection<? extends E> c) { throw unsupported(); }
    @Deprecated @Override public final E set(int index, E element)                          { throw unsupported(); }
    @Deprecated @Override public final boolean retainAll(Collection<?> c)                   { throw unsupported(); }
    @Deprecated @Override public final boolean remove(Object o)                             { throw unsupported(); }
    @Deprecated @Override public final E remove(int index)                                  { throw unsupported(); }
    @Deprecated @Override protected final void removeRange(int fromIndex, int toIndex)      { throw unsupported(); }
    @Deprecated @Override public final boolean removeAll(Collection<?> c)                   { throw unsupported(); }
    @Deprecated @Override public final void clear()                                         { throw unsupported(); }

    private static UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException();
    }

    // -------------------------------------------------------------------------
    // Java serialization support

    Object writeReplace() {
        return new ListProxy(this);
    }

    private void readObject(ObjectInputStream in) throws InvalidObjectException {
        throw new InvalidObjectException("proxy expected");
    }

    private static final long serialVersionUID = 1;
}
