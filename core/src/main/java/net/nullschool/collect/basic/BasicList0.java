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

import net.nullschool.collect.ConstList;
import net.nullschool.reflect.PublicInterfaceRef;
import net.nullschool.util.ArrayTools;

import java.util.*;
import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-03-15<p/>
 *
 * @author Cameron Beccario
 */
@PublicInterfaceRef(BasicConstList.class)
final class BasicList0<E> extends BasicConstList<E> {

    private static final BasicList0 INSTANCE = new BasicList0();

    @SuppressWarnings("unchecked")
    static <E> BasicList0<E> instance() {
        return INSTANCE;
    }

    private BasicList0() {
    }

    @Override public int size() {
        return 0;
    }

    @Override public boolean isEmpty() {
        return true;
    }

    @Override public Iterator<E> iterator() {
        return Collections.emptyIterator();
    }

    @Override public ListIterator<E> listIterator() {
        return Collections.emptyListIterator();
    }

    @Override public ListIterator<E> listIterator(int index) {
        if (index != 0) {
            throw new IndexOutOfBoundsException();
        }
        return Collections.emptyListIterator();
    }

    @Override public boolean contains(Object o) {
        return false;
    }

    @Override public boolean containsAll(Collection<?> c) {
        return c.isEmpty();
    }

    @Override public int indexOf(Object o) {
        return -1;
    }

    @Override public int lastIndexOf(Object o) {
        return -1;
    }

    @Override public E get(int index) {
        throw new IndexOutOfBoundsException();
    }

    @Override public Object[] toArray() {
        return ArrayTools.EMPTY_OBJECT_ARRAY;
    }

    @Override public <T> T[] toArray(T[] a) {
        if (a.length > 0) {
            a[0] = null;
        }
        return a;
    }

    @Override public ConstList<E> with(E e) {
        return listOf(e);
    }

    @Override public ConstList<E> with(int index, E e) {
        if (index == 0) {
            return with(e);
        }
        throw new IndexOutOfBoundsException();
    }

    @Override public ConstList<E> withAll(Collection<? extends E> c) {
        return asList(c);
    }

    @Override public ConstList<E> withAll(int index, Collection<? extends E> c) {
        if (index == 0) {
            return withAll(c);
        }
        throw new IndexOutOfBoundsException();
    }

    @Override public ConstList<E> replace(int index, E e) {
        throw new IndexOutOfBoundsException();
    }

    @Override public ConstList<E> without(Object o) {
        return this;
    }

    @Override public ConstList<E> delete(int index) {
        throw new IndexOutOfBoundsException();
    }

    @Override public ConstList<E> withoutAll(Collection<?> c) {
        Objects.requireNonNull(c);
        return this;
    }

    @Override public ConstList<E> subList(int fromIndex, int toIndex) {
        ArrayTools.checkRange(fromIndex, toIndex, 0);
        return this;
    }

    @Override public boolean equals(Object that) {
        return this == that || that instanceof List && ((List<?>)that).isEmpty();
    }

    @Override public int hashCode() {
        return 1;
    }

    @Override public String toString() {
        return "[]";
    }
}
