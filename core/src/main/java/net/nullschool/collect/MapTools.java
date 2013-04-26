package net.nullschool.collect;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.Math.*;

/**
 * 2013-02-06<p/>
 *
 * @author Cameron Beccario
 */
public enum MapTools {;

    private static int goodInitialCapacity(int size) {
        return max((int)(size / 0.75d) + 1, 16);
    }

    /**
     * Returns a map containing entries of the first key, first value, then second key, second value,
     * and so on. Extra keys or values are ignored. More formally: {@code {k[0]:v[0], k[1]:v[1], ...,
     * k[n-1]:v[n-1]}} where {@code n} is {@code min(k.length, v.length)}.
     *
     * @param keys the map keys
     * @param values the map values
     * @param <K> the key type
     * @param <V> the value type
     * @return a map of keys to values, index by index.
     * @throws NullPointerException if either {@code keys} or {@code values} is null
     */
    public static <K, V> Map<K, V> interleave(K[] keys, V[] values) {
        int length = min(keys.length, values.length);
        LinkedHashMap<K, V> map = new LinkedHashMap<>(goodInitialCapacity(length));
        for (int i = 0; i < length; i++) {
            map.put(keys[i], values[i]);
        }
        return map;
    }

    private static <K, V, M extends Map<K, V>> M put(M map, K key, V value) {
        map.put(key, value);
        return map;
    }

    private static <K, V, M extends Map<K, V>> M put(M map, K k0, V v0, K k1, V v1) {
        map.put(k0, v0);
        map.put(k1, v1);
        return map;
    }

    private static <K, V, M extends Map<K, V>> M put(M map, K k0, V v0, K k1, V v1, K k2, V v2) {
        map.put(k0, v0);
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }

    private static <K, V, M extends Map<K, V>> M put(M map, K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3) {
        map.put(k0, v0);
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return map;
    }

    private static <K, V, M extends Map<K, V>> M put(M map, K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        map.put(k0, v0);
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        return map;
    }

//    public static <K, V> HashMap<K, V> newHashMap(K key, V value) {
//        return put(new HashMap<K, V>(), key, value);
//    }
//
//    public static <K, V> HashMap<K, V> newHashMap(K k0, V v0, K k1, V v1) {
//        return put(new HashMap<K, V>(), k0, v0, k1, v1);
//    }
//
//    public static <K, V> HashMap<K, V> newHashMap(K k0, V v0, K k1, V v1, K k2, V v2) {
//        return put(new HashMap<K, V>(), k0, v0, k1, v1, k2, v2);
//    }

    public static <K, V> LinkedHashMap<K, V> linkedHashMapOf(K key, V value) {
        return put(new LinkedHashMap<K, V>(), key, value);
    }

    public static <K, V> LinkedHashMap<K, V> linkedHashMapOf(K k0, V v0, K k1, V v1) {
        return put(new LinkedHashMap<K, V>(), k0, v0, k1, v1);
    }

    public static <K, V> LinkedHashMap<K, V> linkedHashMapOf(K k0, V v0, K k1, V v1, K k2, V v2) {
        return put(new LinkedHashMap<K, V>(), k0, v0, k1, v1, k2, v2);
    }

    public static <K, V> LinkedHashMap<K, V> linkedHashMapOf(K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3) {
        return put(new LinkedHashMap<K, V>(), k0, v0, k1, v1, k2, v2, k3, v3);
    }

    public static <K, V> LinkedHashMap<K, V> linkedHashMapOf(K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return put(new LinkedHashMap<K, V>(), k0, v0, k1, v1, k2, v2, k3, v3, k4, v4);
    }

    public static <K, V, M extends Map<K, V>> M putAll(M dest, Map<? extends K, ? extends V> map) {
        dest.putAll(map);
        return dest;
    }

    public static <K, V, M extends Map<K, V>> M remove(M dest, K key) {
        dest.remove(key);
        return dest;
    }

    public static <K, V, M extends Map<K, V>> M removeAll(M dest, Collection<?> keys) {
        dest.keySet().removeAll(keys);
        return dest;
    }
}
