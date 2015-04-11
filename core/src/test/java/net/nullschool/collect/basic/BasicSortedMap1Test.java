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

import net.nullschool.collect.ConstSortedMap;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import static net.nullschool.collect.CollectionTestingTools.*;
import static org.junit.Assert.*;
import static java.util.Collections.*;

/**
 * 2013-04-29<p/>
 *
 * @author Cameron Beccario
 */
public class BasicSortedMap1Test {

    @Test
    public void test_comparison() {
        compare_sorted_maps(newSortedMap(null, "a", 1), new BasicSortedMap1<>(null, "a", 1), "+", "a", "a", "b", "+");
        compare_sorted_maps(
            newSortedMap(reverseOrder(), "a", 1),
            new BasicSortedMap1<>(reverseOrder(), "a", 1), "b", "a", "a", "+", "b");
    }

    @Test
    public void test_immutable() {
        assert_sorted_map_immutable(new BasicSortedMap1<>(null, "a", 1));
    }

    @Test
    public void test_with() {
        ConstSortedMap<Object, Object> map;

        map = new BasicSortedMap1<>(null, "a", 1);
        compare_sorted_maps(newSortedMap(null, "a", 1, "b", 2), map.with("b", 2));
        compare_sorted_maps(newSortedMap(null, "a", 2), map.with("a", 2));
        assertSame(map, map.with("a", 1));

        map = new BasicSortedMap1<>(reverseOrder(), "a", 1);
        compare_sorted_maps(newSortedMap(reverseOrder(), "a", 1, "b", 2), map.with("b", 2));
        compare_sorted_maps(newSortedMap(reverseOrder(), "a", 2), map.with("a", 2));
        assertSame(map, map.with("a", 1));
    }

    @Test
    public void test_withAll() {
        ConstSortedMap<Object, Object> map;

        map = new BasicSortedMap1<>(null, "a", 1);
        compare_sorted_maps(
            newSortedMap(null, "a", 2, "b", 2, "c", 3),
            map.withAll(newMap("c", 3, "b", 2, "a", 2)));
        compare_sorted_maps(map, map.withAll(newMap("a", 1)));
        assertSame(map, map.withAll(newMap()));

        map = new BasicSortedMap1<>(reverseOrder(), "a", 1);
        compare_sorted_maps(
            newSortedMap(reverseOrder(), "a", 2, "b", 2, "c", 3),
            map.withAll(newMap("b", 2, "c", 3, "a", 2)));
        compare_sorted_maps(map, map.withAll(newMap("a", 1)));
        assertSame(map, map.withAll(newMap()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withAll_throws() {
        new BasicSortedMap1<>(null, "a", 1).withAll(null);
    }

    @Test
    public void test_without() {
        ConstSortedMap<Object, Object> map;

        map = new BasicSortedMap1<>(null, "a", 1);
        compare_sorted_maps(BasicSortedMap0.instance(null), map.without("a"));
        assertSame(map, map.without("b"));

        map = new BasicSortedMap1<>(reverseOrder(), "a", 1);
        compare_sorted_maps(BasicSortedMap0.instance(reverseOrder()), map.without("a"));
        assertSame(map, map.without("b"));
    }

    @Test
    public void test_withoutAll() {
        ConstSortedMap<Object, Object> map;

        map = new BasicSortedMap1<>(null, "a", 1);
        compare_sorted_maps(BasicSortedMap0.instance(null), map.withoutAll(Arrays.asList("a")));
        compare_sorted_maps(BasicSortedMap0.instance(null), map.withoutAll(Arrays.asList("a", "b", "a")));
        assertSame(map, map.withoutAll(Arrays.asList("b")));
        assertSame(map, map.withoutAll(Arrays.asList()));

        map = new BasicSortedMap1<>(reverseOrder(), "a", 1);
        compare_sorted_maps(BasicSortedMap0.instance(reverseOrder()), map.withoutAll(Arrays.asList("a")));
        compare_sorted_maps(BasicSortedMap0.instance(reverseOrder()), map.withoutAll(Arrays.asList("a", "b", "a")));
        assertSame(map, map.withoutAll(Arrays.asList("b")));
        assertSame(map, map.withoutAll(Arrays.asList()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withoutAll_throws() {
        new BasicSortedMap1<>(null, "a", 1).withoutAll(null);
    }

    @Test
    public void test_serialization() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);

        ConstSortedMap<Object, Object> map = new BasicSortedMap1<>(null, "a", "1");

        out.writeObject(map);
        byte[] data = baos.toByteArray();
        assertEquals(
            "aced05sr0+net.nullschool.collect.basic.SortedMapProxy00000001300xppw40001t01at011x",
            BasicToolsTest.asReadableString(data));

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));

        ConstSortedMap<Object, Object> read = (ConstSortedMap)in.readObject();
        compare_sorted_maps(map, read);
        assertSame(map.getClass(), read.getClass());
    }

    @Test
    public void test_serialization_with_comparator() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);

        ConstSortedMap<Object, Object> map = new BasicSortedMap1<>(reverseOrder(), "a", "1");

        out.writeObject(map);
        byte[] data = baos.toByteArray();
        assertEquals(
            "aced05sr0+net.nullschool.collect.basic.SortedMapProxy00000001300xpsr0'" +
                "java.util.Collections$ReverseComparatord48af0SNJd0200xpw40001t01at011x",
            BasicToolsTest.asReadableString(data));

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));

        ConstSortedMap<Object, Object> read = (ConstSortedMap)in.readObject();
        compare_sorted_maps(map, read);
        assertSame(map.getClass(), read.getClass());
    }

    @Test
    public void test_get_key() {
        assertEquals("a", new BasicSortedMap1<>(null, "a", 1).getKey(0));
    }

    @Test
    public void test_get_value() {
        assertEquals(1, new BasicSortedMap1<>(null, "a", 1).getValue(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_out_of_bounds_get_key() {
        new BasicSortedMap1<>(null, "a", 1).getKey(1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_out_of_bounds_get_value() {
        new BasicSortedMap1<>(null, "a", 1).getValue(1);
    }

    @Test
    public void test_non_equality() {
        assertFalse(new BasicSortedMap1<>(null, "a", 1).equals(newSortedMap(null, "a", 2)));
        assertFalse(new BasicSortedMap1<>(null, "a", 1).equals(newSortedMap(null, "b", 1)));
        assertFalse(newSortedMap(null, "a", 2).equals(new BasicSortedMap1<>(null, "a", 1)));
        assertFalse(newSortedMap(null, "b", 1).equals(new BasicSortedMap1<>(null, "a", 1)));
    }

    @Test
    public void test_different_comparators_still_equal() {
        assertTrue(new BasicSortedMap1<>(null, "a", 1).equals(new BasicSortedMap1<>(reverseOrder(), "a", 1)));
    }
}
