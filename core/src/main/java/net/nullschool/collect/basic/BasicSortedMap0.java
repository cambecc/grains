package net.nullschool.collect.basic;

import net.nullschool.collect.*;

import java.util.*;


/**
 * 2013-04-29<p/>
 *
 * @author Cameron Beccario
 */
final class BasicSortedMap0<K, V> extends AbstractBasicConstSortedMap<K, V> {

    private static final BasicSortedMap0 NATURAL_INSTANCE = new BasicSortedMap0<Object, Object>(null);

    @SuppressWarnings("unchecked")
    static <K, V> BasicSortedMap0<K, V> instance(Comparator<? super K> comparator) {
        return comparator == null ? NATURAL_INSTANCE : new BasicSortedMap0<>(comparator);
    }

    private BasicSortedMap0(Comparator<? super K> comparator) {
        super(comparator);
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

    @Override public K firstKey() {
        throw new NoSuchElementException();
    }

    @Override public K lastKey() {
        throw new NoSuchElementException();
    }

    @Override public ConstSortedSet<K> keySet() {
        return BasicConstSortedSet.of(comparator);
    }

    @Override public ConstCollection<V> values() {
        return BasicConstList.of();
    }

    @Override public ConstSet<Entry<K, V>> entrySet() {
        return BasicConstSet.of();
    }

    @Override public ConstSortedMap<K, V> with(K key, V value) {
        return BasicConstSortedMap.of(comparator, key, value);
    }

    @Override public ConstSortedMap<K, V> withAll(Map<? extends K, ? extends V> map) {
        return map.isEmpty() ? this : BasicConstSortedMap.build(comparator, map);
    }

    @Override public ConstSortedMap<K, V> without(Object key) {
        return this;
    }

    @Override public ConstSortedMap<K, V> withoutAll(Collection<?> keys) {
        Objects.requireNonNull(keys);
        return this;
    }

    @Override public ConstSortedMap<K, V> headMap(K toKey) {
        compare(toKey, toKey);  // type check
        return this;
    }

    @Override public ConstSortedMap<K, V> tailMap(K fromKey) {
        compare(fromKey, fromKey);  // type check
        return this;
    }

    @Override public ConstSortedMap<K, V> subMap(K fromKey, K toKey) {
        if (compare(fromKey, toKey) > 0) {
            throw new IllegalArgumentException("fromKey cannot be greater than toKey");
        }
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
