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

import net.nullschool.collect.*;
import net.nullschool.util.ObjectTools;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.*;


/**
 * 2013-04-29<p/>
 *
 * A {@link ConstSortedMap} that uses arrays to store its entries, providing a memory efficient implementation of
 * ConstSortedMap but with O(log(N)) complexity for most map query operations, and O(N) complexity for most map
 * construction operations. Map membership is determined using {@link Comparator#compare}, or {@link Comparable
 * natural ordering} if the comparator is {@code null}. This map allows {@code null} keys only if the associated
 * {@link Comparator} allows nulls.<p/>
 *
 * See {@link BasicCollections} for utility methods that construct instances of this map.
 *
 * @see BasicCollections#emptySortedMap
 * @see BasicCollections#sortedMapOf
 *
 * @author Cameron Beccario
 */
public abstract class BasicConstSortedMap<K, V> extends BasicConstMap<K, V> implements ConstSortedMap<K, V> {

    final Comparator<? super K> comparator;

    BasicConstSortedMap(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }

    @Override public Comparator<? super K> comparator() {
        return comparator;
    }

    int compare(K left, K right) {
        return ObjectTools.compare(left, right, comparator);
    }

    @Override public abstract ConstSortedSet<K> keySet();

    @Override public abstract ConstCollection<V> values();

    @Override public abstract ConstSet<Map.Entry<K, V>> entrySet();

    // -------------------------------------------------------------------------
    // Java serialization support

    Object writeReplace() {
        return new SortedMapProxy(this);
    }

    private void readObject(ObjectInputStream in) throws InvalidObjectException {
        throw new InvalidObjectException("proxy expected");
    }

    private static final long serialVersionUID = 1;
}
