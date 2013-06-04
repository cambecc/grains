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
import org.junit.Test;
import java.util.Map.Entry;
import java.util.*;

import static org.junit.Assert.*;
import static net.nullschool.collect.CollectionTestingTools.*;
import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-05-04<p/>
 *
 * @author Cameron Beccario
 */
public class AbstractGrainTest {

    @Test
    public void test_compare() {
        Map<String, Object> expected = newMap("a", 1, "b", 2);
        Grain grain = new MockGrain(sortedMapOf(null, "a", (Object)1), sortedMapOf(null, "b", (Object)2));

        compare_maps(expected, grain);
    }

    @Test
    public void test_with() {
        Grain grain = new MockGrain("a", "x");
        compare_maps(newMap("a", null, "x", null), grain);
        grain = grain.with("b", 2).with("a", 1).with("x", 8);
        compare_maps(newMap("a", 1, "x", 8, "b", 2), grain);
        compare_maps(newMap("b", 2), grain.extensions());
    }

    @Test
    public void test_iteration_without_calling_hasNext() {
        Set<String> keys = new LinkedHashSet<>();
        Grain grain = new MockGrain(sortedMapOf(null, "a", (Object)1), sortedMapOf(null, "b", (Object)2));
        MapIterator<String, Object> iter = grain.iterator();
        for (int i = 0; i < grain.size(); i++) {
            keys.add(iter.next());
        }
        try { iter.next(); fail(); } catch (NoSuchElementException expected) {}
        assertArrayEquals(grain.keySet().toArray(), keys.toArray());
    }

    @Test
    public void test_immutable() {
        Grain grain = new MockGrain(sortedMapOf(null, "a", (Object)1), sortedMapOf(null, "b", (Object)2));
        assert_map_immutable(grain);
        assert_map_immutable(grain.extensions());
    }

    @Test
    public void test_keySet_modify() {
        Grain grain = new MockGrain(sortedMapOf(null, "a", (Object)1), sortedMapOf(null, "b", (Object)2));
        ConstSet<String> keySet = grain.keySet();

        // with/withAll
        assertSame(keySet, keySet.with("a"));
        assertSame(keySet, keySet.with("b"));
        compare_sets(newSet("a", "b", "+"), keySet.with("+"));
        compare_sets(newSet("a", "b", "+", "c"), keySet.withAll(Arrays.asList("+", "c")));
        assertSame(keySet, keySet.withAll(Arrays.<String>asList()));

        // without/withoutAll
        assertSame(keySet, keySet.without("+"));
        compare_sets(newSet("b"), keySet.without("a"));
        compare_sets(newSet("a"), keySet.without("b"));
        compare_sets(newSet("a"), keySet.withoutAll(Arrays.asList("b", "c")));
        compare_sets(newSet(), keySet.withoutAll(keySet));
        assertSame(keySet, keySet.withoutAll(Arrays.asList()));
    }

    @Test
    public void test_values_modify() {
        Grain grain = new MockGrain(sortedMapOf(null, "a", (Object)1), sortedMapOf(null, "b", (Object)2));
        ConstCollection<Object> values = grain.values();

        // with/withAll
        assertSame(values, values.with(1));
        assertSame(values, values.with(2));
        compare_collections(Arrays.asList(1, 2, 3), values.with(3));
        compare_collections(Arrays.asList(1, 2, 0, 3), values.withAll(Arrays.asList(0, 3)));
        assertSame(values, values.withAll(Arrays.asList()));

        // without/withoutAll
        assertSame(values, values.without(0));
        compare_collections(Arrays.asList(2), values.without(1));
        compare_collections(Arrays.asList(1), values.without(2));
        compare_collections(Arrays.asList(1), values.withoutAll(Arrays.asList(2, 3)));
        compare_collections(Arrays.asList(), values.withoutAll(values));
        assertSame(values, values.withoutAll(Arrays.asList()));
    }

    @Test
    public void test_entrySet_modify() {
        Grain grain = new MockGrain(sortedMapOf(null, "a", (Object)1), sortedMapOf(null, "b", (Object)2));
        ConstSet<Entry<String, Object>> entrySet = grain.entrySet();

        // with/withAll
        assertSame(entrySet, entrySet.with(newEntry("a", (Object)1)));
        assertSame(entrySet, entrySet.with(newEntry("b", (Object)2)));
        compare_sets(
            newSet(newEntry("a", 1), newEntry("b", 2), newEntry("a", 3)),
            entrySet.with(newEntry("a", (Object)3)));
        compare_sets(
            newSet(newEntry("a", 1), newEntry("b", 2), newEntry("a", 3), newEntry("c", 3)),
            entrySet.withAll(Arrays.asList(newEntry("a", (Object)3), newEntry("c", (Object)3))));
        assertSame(entrySet, entrySet.withAll(Arrays.<Entry<String, Object>>asList()));

        // without/withoutAll
        assertSame(entrySet, entrySet.without(newEntry("a", 2)));
        compare_sets(newSet(newEntry("b", 2)), entrySet.without(newEntry("a", 1)));
        compare_sets(newSet(newEntry("a", 1)), entrySet.without(newEntry("b", 2)));
        compare_sets(
            newSet(newEntry("a", 1)),
            entrySet.withoutAll(Arrays.asList(newEntry("b", (Object)2), newEntry("c", (Object)3))));
        compare_sets(newSet(), entrySet.withoutAll(entrySet));
        assertSame(entrySet, entrySet.withoutAll(Arrays.asList()));
    }
}
