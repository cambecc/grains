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

import java.util.Collection;
import java.util.Objects;

import static net.nullschool.collect.basic.BasicTools.unionInto;

/**
 * 2013-03-16<p/>
 *
 * @author Cameron Beccario
 */
final class BasicSet1<E> extends BasicConstSet<E> {

    private final E e0;

    @SuppressWarnings("unchecked")
    BasicSet1(Object e0) {
        this.e0 = (E)e0;
    }

    @Override public int size() {
        return 1;
    }

    @Override public boolean isEmpty() {
        return false;
    }

    @Override public boolean contains(Object o) {
        return Objects.equals(o, e0);
    }

    @Override E get(int index) {
        if (index == 0) {
            return e0;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override public Object[] toArray() {
        return new Object[] {e0};
    }

    @Override public ConstSet<E> with(E e) {
        return contains(e) ? this : new BasicSetN<E>(new Object[] {e0, e});
    }

    @Override public ConstSet<E> withAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return this;
        }
        Object[] expanded = unionInto(new Object[] {e0}, c.toArray());
        return expanded.length == size() ? this : BasicConstSet.<E>condense(expanded);
    }

    @Override public ConstSet<E> without(Object o) {
        return !contains(o) ? this : BasicConstSet.<E>emptySet();
    }

    @Override public ConstSet<E> withoutAll(Collection<?> c) {
        return !c.contains(e0) ? this : BasicConstSet.<E>emptySet();
    }

    @Override public int hashCode() {
        return Objects.hashCode(e0);
    }
}
