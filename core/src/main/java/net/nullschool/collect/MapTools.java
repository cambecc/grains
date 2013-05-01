package net.nullschool.collect;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * 2013-02-06<p/>
 *
 * @author Cameron Beccario
 */
public enum MapTools {;

    private static int goodInitialCapacity(int size) {
        // Just like what the HashMap(Map) constructor does...
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
     * @throws NullPointerException if either {@code keys} or {@code values} is null.
     */
    public static <K, V> Map<K, V> interleave(K[] keys, V[] values) {
        int length = min(keys.length, values.length);
        LinkedHashMap<K, V> map = new LinkedHashMap<>(goodInitialCapacity(length));
        for (int i = 0; i < length; i++) {
            map.put(keys[i], values[i]);
        }
        return map;
    }
}
