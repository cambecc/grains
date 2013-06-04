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

import net.nullschool.collect.*;
import net.nullschool.collect.basic.BasicCollections;

import java.io.Serializable;
import java.util.*;

import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-05-04<p/>
 *
 * @author Cameron Beccario
 */
@SuppressWarnings("SuspiciousMethodCalls")
@GrainFactoryRef(MockGrainFactory.class)
final class MockGrain extends AbstractGrain implements Serializable {

    private final ConstSortedMap<String, Object> basis;
    private final ConstSortedMap<String, Object> extensions;

    MockGrain(String... basisKeys) {
        this(
            asSortedMap(null, basisKeys, new Object[basisKeys.length]),
            BasicCollections.<String, Object>emptySortedMap(null));
    }

    MockGrain(ConstSortedMap<String, Object> basis, ConstSortedMap<String, Object> extensions) {
        this.basis = basis;
        this.extensions = extensions;
    }

    @Override public int size() {
        return basis.size() + extensions.size();
    }

    @Override public MapIterator<String, Object> iterator() {
        return IteratorTools.chainMapIterators(
            new BasisIter(basis.keySet().toArray(new String[basis.size()])),
            extensions.iterator());
    }

    @Override public Object get(Object key) {
        return basis.containsKey(key) ? basis.get(key) : extensions.get(key);
    }

    @Override public ConstMap<String, Object> extensions() {
        return extensions;
    }

    @Override public MockGrain with(String key, Object value) {
        return basis.containsKey(key) ?
            new MockGrain(basis.with(key, value), extensions) :
            new MockGrain(basis, extensions.with(key, value));
    }

    @Override public MockGrain withAll(Map<? extends String, ?> map) {
        MockGrain result = this;
        for (Entry<? extends String, ?> entry : map.entrySet()) {
            result = result.with(entry.getKey(), entry.getValue());
        }
        return result;
    }

    @Override public MockGrain without(Object key) {
        return basis.containsKey(key) ?
            new MockGrain(basis.with((String)key, null), extensions) :
            new MockGrain(basis, extensions.without(key));
    }

    @Override public MockGrain withoutAll(Collection<?> c) {
        MockGrain result = this;
        for (Object key : c) {
            result = result.without(key);
        }
        return result;
    }

    @Override public MockGrainBuilder newBuilder() {
        return new MockGrainBuilder(new TreeMap<>(basis), new TreeMap<>(extensions));
    }

    private Object writeReplace() {
        return new Proxy().setPayload(this);
    }

    private static final class Proxy extends AbstractGrainProxy {
        private static final long serialVersionUID = 1;
        @Override protected GrainBuilder newBuilder() { return MockGrainFactory.INSTANCE.getNewBuilder(); }
    }
}
