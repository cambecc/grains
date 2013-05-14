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

import java.util.*;

import static net.nullschool.collect.basic.BasicTools.*;

/**
 * 2013-03-18<p/>
 *
 * @author Cameron Beccario
 */
final class BasicSortedSetN<E> extends AbstractBasicConstSortedSet<E> {

    private final E[] elements;

    @SuppressWarnings("unchecked")
    BasicSortedSetN(Comparator<? super E> comparator, Object[] elements) {
        super(comparator);
        assert elements.getClass() == Object[].class;
        assert elements.length > 1;
        this.elements = (E[])elements;
    }

    @Override public int size() {
        return elements.length;
    }

    private int indexOf(Object o) {
        // Cast to E is safe here because the comparator will do type checking when the compare method is invoked.
        @SuppressWarnings("unchecked") E e = (E)o;
        return Arrays.binarySearch(elements, e, comparator);
    }

    @Override public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override E get(int index) {
        return elements[index];
    }

    @Override public E first() {
        return elements[0];
    }

    @Override public E last() {
        return elements[elements.length - 1];
    }

    @Override public Object[] toArray() {
        return elements.clone();
    }

    @Override public ConstSortedSet<E> with(E e) {
        int index = indexOf(e);
        return index >= 0 ? this : new BasicSortedSetN<>(comparator, insert(elements, flip(index), e));
    }

    @Override public ConstSortedSet<E> withAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return this;
        }
        Object[] expanded = unionInto(elements, c.toArray(), comparator);
        return expanded.length == size() ? this : new BasicSortedSetN<>(comparator, expanded);
    }

    @Override public ConstSortedSet<E> without(Object o) {
        int index = indexOf(o);
        return index < 0 ? this : BasicConstSortedSet.condense(comparator, delete(elements, index));
    }

    @Override public ConstSortedSet<E> withoutAll(Collection<?> c) {
        if (c.isEmpty()) {
            return this;
        }
        // deleteAll uses the collection's contains method to test for equality, just as
        // AbstractCollection.removeAll does.
        Object[] shrunk = deleteAll(elements, c);
        return shrunk.length == size() ? this : BasicConstSortedSet.condense(comparator, shrunk);
    }

    private ConstSortedSet<E> subSet(int fromIndex, int toIndex) {
        if (fromIndex < 0) {
            fromIndex = flip(fromIndex);
        }
        if (toIndex < 0) {
            toIndex = flip(toIndex);
        }
        if (fromIndex == 0 && toIndex == elements.length) {
            return this;
        }
        return BasicConstSortedSet.condense(comparator, Arrays.copyOfRange(elements, fromIndex, toIndex));
    }

    @Override public ConstSortedSet<E> headSet(E toElement) {
        return subSet(0, indexOf(toElement));
    }

    @Override public ConstSortedSet<E> tailSet(E fromElement) {
        return subSet(indexOf(fromElement), elements.length);
    }

    @Override public ConstSortedSet<E> subSet(E fromElement, E toElement) {
        if (compare(fromElement, toElement) > 0) {
            throw new IllegalArgumentException("fromElement cannot be greater than toElement");
        }
        return subSet(indexOf(fromElement), indexOf(toElement));
    }

    @Override public int hashCode() {
        int hash = 0;
        for (Object o : elements) {
            hash += Objects.hashCode(o);
        }
        return hash;
    }
}
