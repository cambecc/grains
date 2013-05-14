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

package net.nullschool.collect;

import java.util.*;

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

    /**
     * Invokes {@link Map#putAll} on {@code dest} with the specified map and returns {@code dest} so this operation
     * can be chained.
     *
     * @param dest the map on which to invoke putAll.
     * @param map the map of entries to put into dest.
     * @return the same reference as {@code dest}.
     * @throws UnsupportedOperationException if {@code dest} does not support putAll.
     * @throws ClassCastException if any key or value type in the source map is not compatible with {@code dest}.
     * @throws NullPointerException if {@code dest} or {@code map} is null or {@code map} contains a null key or value
     *                              and {@code dest} does not support nulls.
     * @throws IllegalArgumentException if any key or value in the source map has some property which is not suitable
     *                                  for the {@code dest} map.
     */
    public static <K, V, T extends Map<K, V>> T putAll(T dest, Map<? extends K, ? extends V> map) {
        dest.putAll(map);
        return dest;
    }

    /**
     * Removes all specified keys from the map and returns the same map reference so this operation can be chained.
     *
     * @param map the map to remove keys from.
     * @param keys the keys to remove.
     * @return the same map.
     * @throws UnsupportedOperationException if the map's entrySet does not support {@link Set#removeAll}.
     * @throws ClassCastException if any key's type is not compatible with this map.
     * @throws NullPointerException if {@code map} or {@code keys} is null, or {@code keys} contains null and the
     *                              map does not support null keys.
     */
    public static <T extends Map<?, ?>> T removeAll(T map, Collection<?> keys) {
        map.keySet().removeAll(keys);
        return map;
    }
}
