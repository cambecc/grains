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

package net.nullschool.grains;

import net.nullschool.collect.IteratorTools;
import net.nullschool.collect.MapIterator;
import net.nullschool.collect.basic.BasicConstSortedMap;

import java.util.SortedMap;
import java.util.TreeMap;


/**
 * 2013-05-04<p/>
 *
 * @author Cameron Beccario
 */
@SuppressWarnings("SuspiciousMethodCalls")
final class MockGrainBuilder extends AbstractGrainBuilder {

    private final SortedMap<String, Object> basis;
    private final SortedMap<String, Object> extensions;

    MockGrainBuilder(String... basisKeys) {
        this(new TreeMap<String, Object>(), new TreeMap<String, Object>());
        for (String key : basisKeys) {
            basis.put(key, null);
        }
    }

    MockGrainBuilder(SortedMap<String, Object> basis, SortedMap<String, Object> extensions) {
        this.basis = basis;
        this.extensions = extensions;
    }

    @Override public int size() {
        return basis.size() + extensions.size();
    }

    @Override public MapIterator<String, Object> iterator() {
        return IteratorTools.chainMapIterators(
            new BasisIter(basis.keySet().toArray(new String[basis.size()])),
            IteratorTools.newMapIterator(extensions));
    }

    @Override public Object get(Object key) {
        return basis.containsKey(key) ? basis.get(key) : extensions.get(key);
    }

    @Override public Object put(String key, Object value) {
        return basis.containsKey(key) ? basis.put(key, value) : extensions.put(key, value);
    }

    @Override public Object remove(Object key) {
        return basis.containsKey(key) ? basis.put((String)key, null) : extensions.remove(key);
    }

    @Override public Grain build() {
        return new MockGrain(BasicConstSortedMap.asSortedMap(basis), BasicConstSortedMap.asSortedMap(extensions));
    }
}
