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

import net.nullschool.collect.ConstSet;
import net.nullschool.util.ArrayTools;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Objects;

import static net.nullschool.collect.basic.BasicTools.*;

/**
 * 2013-03-16<p/>
 *
 * @author Cameron Beccario
 */
final class BasicSetN<E> extends AbstractBasicConstSet<E> {

    private final E[] elements;

    @SuppressWarnings("unchecked")
    BasicSetN(Object[] elements) {
        assert elements.getClass() == Object[].class;
        assert elements.length > 1;
        this.elements = (E[])elements;
    }

    @Override public int size() {
        return elements.length;
    }

    @Override public boolean contains(Object o) {
        return ArrayTools.indexOf(o, elements) >= 0;
    }

    @Override E get(int index) {
        return elements[index];
    }

    @Override public Object[] toArray() {
        return elements.clone();
    }

    @Override public <T> T[] toArray(T[] a) {
        int size = elements.length;
        // Cast is safe because Array.newInstance will return an array of type T[].
        @SuppressWarnings("unchecked") T[] result = a.length < size ?
            (T[])Array.newInstance(a.getClass().getComponentType(), size) :
            a;
        // noinspection SuspiciousSystemArraycopy
        System.arraycopy(elements, 0, result, 0, size);
        if (result.length > size) {
            result[size] = null;
        }
        return result;
    }

    @Override public ConstSet<E> with(E e) {
        return contains(e) ? this : new BasicSetN<E>(insert(elements, elements.length, e));
    }

    @Override public ConstSet<E> withAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return this;
        }
        Object[] expanded = unionInto(elements, c.toArray());
        return expanded.length == size() ? this : new BasicSetN<E>(expanded);
    }

    @Override public ConstSet<E> without(Object o) {
        int index = ArrayTools.indexOf(o, elements);
        return index < 0 ? this : BasicConstSet.<E>condense(delete(elements, index));
    }

    @Override public ConstSet<E> withoutAll(Collection<?> c) {
        if (c.isEmpty()) {
            return this;
        }
        Object[] shrunk = deleteAll(elements, c);
        return shrunk.length == size() ? this : BasicConstSet.<E>condense(shrunk);
    }

    @Override public int hashCode() {
        int hash = 0;
        for (Object o : elements) {
            hash += Objects.hashCode(o);
        }
        return hash;
    }
}
