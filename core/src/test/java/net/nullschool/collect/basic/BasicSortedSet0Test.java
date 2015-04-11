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

import net.nullschool.collect.CollectionTestingTools;
import net.nullschool.collect.ConstSortedSet;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static net.nullschool.collect.CollectionTestingTools.*;
import static java.util.Collections.*;

/**
 * 2013-04-24<p/>
 *
 * @author Cameron Beccario
 */
public class BasicSortedSet0Test {

    @Test
    public void test_comparison() {
        compare_sorted_sets(CollectionTestingTools.<Integer>newSortedSet(null), BasicSortedSet0.<Integer>instance(null), -1, 0, 1, 0);
        compare_sorted_sets(newSortedSet(reverseOrder()), BasicSortedSet0.instance(reverseOrder()), 1, 0, -1, 0);
    }

    @Test
    public void test_immutable() {
        assert_sorted_set_immutable(BasicSortedSet0.instance(null));
    }

    @Test
    public void test_with() {
        compare_sorted_sets(newSortedSet(null, 1), BasicSortedSet0.<Integer>instance(null).with(1));
        compare_sorted_sets(newSortedSet(reverseOrder(), 1), BasicSortedSet0.<Integer>instance(reverseOrder()).with(1));
    }

    @Test
    public void test_withAll() {
        ConstSortedSet<Integer> empty = BasicSortedSet0.instance(null);
        compare_sorted_sets(newSortedSet(null, 1, 2, 3), empty.withAll(Arrays.asList(1, 2, 3)));
        assertSame(empty, empty.withAll(Collections.<Integer>emptySet()));

        ConstSortedSet<Integer> reverseEmpty = BasicSortedSet0.instance(reverseOrder());
        compare_sorted_sets(newSortedSet(reverseOrder(), 1, 2, 3), reverseEmpty.withAll(Arrays.asList(1, 2, 3)));
        assertSame(reverseEmpty, reverseEmpty.withAll(Collections.<Integer>emptySet()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withAll_throws() {
        BasicSortedSet0.instance(null).withAll(null);
    }

    @Test
    public void test_without() {
        ConstSortedSet<Integer> empty = BasicSortedSet0.instance(null);
        assertSame(empty, empty.without(1));

        ConstSortedSet<Integer> reverseEmpty = BasicSortedSet0.instance(reverseOrder());
        assertSame(reverseEmpty, reverseEmpty.without(1));
    }

    @Test
    public void test_withoutAll() {
        ConstSortedSet<Integer> empty = BasicSortedSet0.instance(null);
        assertSame(empty, empty.withoutAll(Arrays.asList(1)));
        assertSame(empty, empty.withoutAll(Arrays.asList()));

        ConstSortedSet<Integer> reverseEmpty = BasicSortedSet0.instance(reverseOrder());
        assertSame(reverseEmpty, reverseEmpty.withoutAll(Arrays.asList(1)));
        assertSame(reverseEmpty, reverseEmpty.withoutAll(Arrays.asList()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withoutAll_throws() {
        BasicSortedSet0.instance(null).withoutAll(null);
    }

    @Test
    public void test_serialization() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);

        ConstSortedSet<Object> set = BasicSortedSet0.instance(null);

        out.writeObject(set);
        byte[] data = baos.toByteArray();
        assertEquals(
            "aced05sr0+net.nullschool.collect.basic.SortedSetProxy00000001300xppw40000x",
            BasicToolsTest.asReadableString(data));

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));
        assertSame(set, in.readObject());
    }

    @Test
    public void test_serialization_with_comparator() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);

        ConstSortedSet<Object> set = BasicSortedSet0.instance(reverseOrder());

        out.writeObject(set);
        byte[] data = baos.toByteArray();
        assertEquals(
            "aced05sr0+net.nullschool.collect.basic.SortedSetProxy00000001300xpsr0'" +
                "java.util.Collections$ReverseComparatord48af0SNJd0200xpw40000x",
            BasicToolsTest.asReadableString(data));

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));

        ConstSortedSet<Object> read = (ConstSortedSet)in.readObject();
        compare_sorted_sets(set, read);
        assertSame(set.getClass(), read.getClass());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_out_of_bounds_get() {
        BasicSortedSet0.instance(null).get(0);
    }

    @Test
    public void test_non_equality() {
        assertFalse(BasicSortedSet0.instance(null).equals(newSortedSet(null, 1)));
        assertFalse(newSortedSet(null, 1).equals(BasicSortedSet0.instance(null)));
    }

    @Test
    public void test_different_comparators_still_equal() {
        assertTrue(BasicSortedSet0.instance(null).equals(BasicSortedSet0.instance(reverseOrder())));
    }
}
