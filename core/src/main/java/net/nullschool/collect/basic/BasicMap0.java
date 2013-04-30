package net.nullschool.collect.basic;

import net.nullschool.collect.*;

import java.util.*;


/**
 * 2013-03-17<p/>
 *
 * @author Cameron Beccario
 */
final class BasicMap0<K, V> extends AbstractBasicConstMap<K, V> {

    private static final BasicMap0 INSTANCE = new BasicMap0();

    @SuppressWarnings("unchecked")
    static <K, V> BasicMap0<K, V> instance() {
        return INSTANCE;
    }

    private BasicMap0() {
    }

    @Override public int size() {
        return 0;
    }

    @Override public boolean isEmpty() {
        return true;
    }

    @Override public MapIterator<K, V> iterator() {
        return IteratorTools.emptyMapIterator();
    }

    @Override public boolean containsKey(Object key) {
        return false;
    }

    @Override public boolean containsValue(Object value) {
        return false;
    }

    @Override K getKey(int index) {
        throw new IndexOutOfBoundsException();
    }

    @Override V getValue(int index) {
        throw new IndexOutOfBoundsException();
    }

    @Override public V get(Object key) {
        return null;
    }

    @Override public ConstSet<K> keySet() {
        return BasicConstSet.of();
    }

    @Override public ConstCollection<V> values() {
        return BasicConstList.emptyList();
    }

    @Override public ConstSet<Entry<K, V>> entrySet() {
        return BasicConstSet.of();
    }

    @Override public ConstMap<K, V> with(K key, V value) {
        return BasicConstMap.of(key, value);
    }

    @Override public ConstMap<K, V> withAll(Map<? extends K, ? extends V> map) {
        return BasicConstMap.build(map);
    }

    @Override public ConstMap<K, V> without(Object key) {
        return this;
    }

    @Override public ConstMap<K, V> withoutAll(Collection<?> keys) {
        Objects.requireNonNull(keys);
        return this;
    }

    @Override public boolean equals(Object that) {
        return this == that || that instanceof Map && ((Map<?, ?>)that).isEmpty();
    }

    @Override public int hashCode() {
        return 0;
    }

    @Override public String toString() {
        return "{}";
    }
}
