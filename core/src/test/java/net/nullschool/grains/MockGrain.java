package net.nullschool.grains;

import net.nullschool.collect.*;
import net.nullschool.collect.basic.BasicConstSortedMap;

import java.io.Serializable;
import java.util.*;

import static net.nullschool.collect.basic.BasicConstSortedMap.*;


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
            BasicConstSortedMap.<String, Object>emptySortedMap(null));
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

    @Override public MockGrainBuilder builder() {
        return new MockGrainBuilder(new TreeMap<>(basis), new TreeMap<>(extensions));
    }

    private Object writeReplace() {
        return new Proxy().setPayload(this);
    }

    private static final class Proxy extends AbstractGrainProxy {
        private static final long serialVersionUID = 1;
        @Override protected GrainBuilder newBuilder() { return MockGrainFactory.INSTANCE.newBuilder(); }
    }
}
