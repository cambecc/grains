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
import net.nullschool.collect.IteratorTools;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;

import static java.util.Collections.reverseOrder;
import static net.nullschool.collect.CollectionTestingTools.*;
import static org.junit.Assert.*;

/**
 * 2013-04-29<p/>
 *
 * @author Cameron Beccario
 */
public class BasicSortedMap0Test {

    @Test
    public void test_comparison() {
        compare_sorted_maps(newSortedMap(null), BasicSortedMap0.instance(null), "a", "b");
        compare_sorted_maps(newSortedMap(reverseOrder()), BasicSortedMap0.instance(reverseOrder()), "a", "b");
    }

    @Test
    public void test_immutable() {
        assert_sorted_map_immutable(BasicSortedMap0.instance(null));
    }

    @Test
    public void test_with() {
        compare_maps(newSortedMap(null, "a", 1), BasicSortedMap0.instance(null).with("a", 1));
        compare_maps(newSortedMap(reverseOrder(), "a", 1), BasicSortedMap0.instance(reverseOrder()).with("a", 1));
    }

    @Test
    public void test_withAll() {
        ConstSortedMap<Object, Object> empty = BasicSortedMap0.instance(null);
        compare_sorted_maps(newSortedMap(null, "a", 1, "b", 2), empty.withAll(newMap("a", 1, "b", 2)));
        assertSame(empty, empty.withAll(Collections.emptyMap()));

        ConstSortedMap<Object, Object> reverseEmpty = BasicSortedMap0.instance(reverseOrder());
        compare_sorted_maps(newSortedMap(reverseOrder(), "b", 2, "a", 1), reverseEmpty.withAll(newMap("a", 1, "b", 2)));
        assertSame(reverseEmpty, reverseEmpty.withAll(Collections.emptyMap()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withAll_throws() {
        BasicSortedMap0.instance(null).withAll(null);
    }

    @Test
    public void test_without() {
        ConstSortedMap<Object, Object> empty = BasicSortedMap0.instance(null);
        assertSame(empty, empty.without("a"));

        ConstSortedMap<Object, Object> reverseEmpty = BasicSortedMap0.instance(reverseOrder());
        assertSame(reverseEmpty, reverseEmpty.without("a"));
    }

    @Test
    public void test_withoutAll() {
        ConstSortedMap<String, Integer> empty = BasicSortedMap0.instance(null);
        assertSame(empty, empty.withoutAll(Arrays.asList("a")));
        assertSame(empty, empty.withoutAll(Arrays.asList()));

        ConstSortedMap<String, Integer> reverseEmpty = BasicSortedMap0.instance(reverseOrder());
        assertSame(reverseEmpty, reverseEmpty.withoutAll(Arrays.asList("a")));
        assertSame(reverseEmpty, reverseEmpty.withoutAll(Arrays.asList()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withoutAll_throws() {
        BasicSortedMap0.instance(null).withoutAll(null);
    }

    @Test
    public void test_serialization() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);

        ConstSortedMap<Object, Object> map = BasicSortedMap0.instance(null);

        out.writeObject(map);
        byte[] data = baos.toByteArray();
        assertEquals(
            "aced05sr0+net.nullschool.collect.basic.SortedMapProxy00000001300xppw40000x",
            BasicToolsTest.asReadableString(data));

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));
        assertSame(map, in.readObject());
    }

    @Test
    public void test_serialization_with_comparator() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);

        ConstSortedMap<Object, Object> map = BasicSortedMap0.instance(reverseOrder());

        out.writeObject(map);
        byte[] data = baos.toByteArray();
        assertEquals(
            "aced05sr0+net.nullschool.collect.basic.SortedMapProxy00000001300xpsr0'" +
                "java.util.Collections$ReverseComparatord48af0SNJd0200xpw40000x",
            BasicToolsTest.asReadableString(data));

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));

        ConstSortedMap<Object, Object> read = (ConstSortedMap)in.readObject();
        compare_sorted_maps(map, read);
        assertSame(map.getClass(), read.getClass());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_out_of_bounds_get_key() {
        BasicSortedMap0.instance(null).getKey(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_out_of_bounds_get_value() {
        BasicSortedMap0.instance(null).getValue(0);
    }

    @Test
    public void test_empty_iterator() {
        assertSame(IteratorTools.emptyMapIterator(), BasicSortedMap0.instance(null).iterator());
    }

    @Test
    public void test_non_equality() {
        assertFalse(BasicSortedMap0.instance(null).equals(newSortedMap(null, "a", 1)));
        assertFalse(newSortedMap(null, "a", 1).equals(BasicSortedMap0.instance(null)));
    }

    @Test
    public void test_different_comparators_still_equal() {
        assertTrue(BasicSortedMap0.instance(null).equals(BasicSortedMap0.instance(reverseOrder())));
    }
}
