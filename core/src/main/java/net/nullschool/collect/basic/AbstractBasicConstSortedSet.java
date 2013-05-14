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
import net.nullschool.util.ObjectTools;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.Comparator;


/**
 * 2013-03-18<p/>
 *
 * @author Cameron Beccario
 */
abstract class AbstractBasicConstSortedSet<E> extends AbstractBasicConstSet<E> implements ConstSortedSet<E> {

    final Comparator<? super E> comparator;

    AbstractBasicConstSortedSet(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    @Override public Comparator<? super E> comparator() {
        return comparator;
    }

    int compare(E left, E right) {
        return ObjectTools.compare(left, right, comparator);
    }

    // -------------------------------------------------------------------------
    // Java serialization support

    Object writeReplace() {
        return new SortedSetProxy(this);
    }

    private void readObject(ObjectInputStream in) throws InvalidObjectException {
        throw new InvalidObjectException("proxy expected");
    }

    private static final long serialVersionUID = 1;
}
