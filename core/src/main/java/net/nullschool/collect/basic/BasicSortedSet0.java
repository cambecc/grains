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

import net.nullschool.collect.ConstSortedSet;
import net.nullschool.reflect.PublicInterfaceRef;
import net.nullschool.util.ArrayTools;

import java.util.*;


/**
 * 2013-03-18<p/>
 *
 * @author Cameron Beccario
 */
@PublicInterfaceRef(BasicConstSortedSet.class)
final class BasicSortedSet0<E> extends BasicConstSortedSet<E> {

    private static final BasicSortedSet0 NATURAL_INSTANCE = new BasicSortedSet0<Object>(null);

    @SuppressWarnings("unchecked")
    static <E> BasicSortedSet0<E> instance(Comparator<? super E> comparator) {
        return comparator == null ? NATURAL_INSTANCE : new BasicSortedSet0<>(comparator);
    }

    private BasicSortedSet0(Comparator<? super E> comparator) {
        super(comparator);
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

    @Override public boolean contains(Object o) {
        return false;
    }

    @Override public boolean containsAll(Collection<?> c) {
        return c.isEmpty();
    }

    @Override E get(int index) {
        throw new IndexOutOfBoundsException();
    }

    @Override public E first() {
        throw new NoSuchElementException();
    }

    @Override public E last() {
        throw new NoSuchElementException();
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

    @Override public ConstSortedSet<E> with(E e) {
        return BasicConstSortedSet.sortedSetOf(comparator, e);
    }

    @Override public ConstSortedSet<E> withAll(Collection<? extends E> c) {
        return c.isEmpty() ? this : BasicConstSortedSet.asSortedSet(comparator, c);
    }

    @Override public ConstSortedSet<E> without(Object o) {
        return this;
    }

    @Override public ConstSortedSet<E> withoutAll(Collection<?> c) {
        Objects.requireNonNull(c);
        return this;
    }

    @Override public ConstSortedSet<E> headSet(E toElement) {
        BasicTools.checkType(comparator, toElement);
        return this;
    }

    @Override public ConstSortedSet<E> tailSet(E fromElement) {
        BasicTools.checkType(comparator, fromElement);
        return this;
    }

    @Override public ConstSortedSet<E> subSet(E fromElement, E toElement) {
        if (compare(fromElement, toElement) > 0) {
            throw new IllegalArgumentException("fromElement cannot be greater than toElement");
        }
        return this;
    }

    @Override public boolean equals(Object that) {
        return this == that || that instanceof Set && ((Set<?>)that).isEmpty();
    }

    @Override public int hashCode() {
        return 0;
    }

    @Override public String toString() {
        return "[]";
    }
}
