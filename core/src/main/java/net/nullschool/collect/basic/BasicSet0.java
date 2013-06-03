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

import java.util.*;


/**
 * 2013-03-16<p/>
 *
 * @author Cameron Beccario
 */
final class BasicSet0<E> extends BasicConstSet<E> {

    private static final BasicSet0 INSTANCE = new BasicSet0();

    @SuppressWarnings("unchecked")
    static <E> BasicSet0<E> instance() {
        return INSTANCE;
    }

    private BasicSet0() {
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

    @Override public Object[] toArray() {
        return ArrayTools.EMPTY_OBJECT_ARRAY;
    }

    @Override public <T> T[] toArray(T[] a) {
        if (a.length > 0) {
            a[0] = null;
        }
        return a;
    }

    @Override public ConstSet<E> with(E e) {
        return BasicConstSet.setOf(e);
    }

    @Override public ConstSet<E> withAll(Collection<? extends E> c) {
        return BasicConstSet.asSet(c);
    }

    @Override public ConstSet<E> without(Object o) {
        return this;
    }

    @Override public ConstSet<E> withoutAll(Collection<?> c) {
        Objects.requireNonNull(c);
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
