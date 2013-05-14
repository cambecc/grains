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
import java.util.Comparator;


/**
 * 2013-04-29<p/>
 *
 * @author Cameron Beccario
 */
abstract class AbstractBasicConstSortedMap<K, V> extends AbstractBasicConstMap<K, V> implements ConstSortedMap<K, V> {

    final Comparator<? super K> comparator;

    AbstractBasicConstSortedMap(Comparator<? super K> comparator) {
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

    @Override public abstract ConstSet<Entry<K, V>> entrySet();

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
