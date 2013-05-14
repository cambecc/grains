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

import net.nullschool.collect.ConstSet;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;

import static net.nullschool.collect.CollectionTestingTools.*;
import static org.junit.Assert.*;

/**
 * 2013-03-17<p/>
 *
 * @author Cameron Beccario
 */
public class BasicSet1Test {

    @Test
    public void test_comparison() {
        compare_sets(Collections.singleton(1), new BasicSet1<>(1));
    }

    @Test
    public void test_immutable() {
        assert_set_immutable(new BasicSet1<>(1));
    }

    @Test
    public void test_with() {
        ConstSet<Integer> set;

        set = new BasicSet1<>(1);
        compare_sets(newSet(1, 2), set.with(2));
        compare_sets(newSet(1, null), set.with(null));
        assertSame(set, set.with(1));

        set = new BasicSet1<>(null);
        assertSame(set, set.with(null));
    }

    @Test
    public void test_withAll() {
        ConstSet<Integer> set = new BasicSet1<>(1);
        compare_sets(newSet(1, 2, 3), set.withAll(Arrays.asList(1, 2, 3, 3, 2, 1)));

        assertSame(set, set.withAll(Arrays.asList(1, 1, 1, 1, 1, 1)));
        assertSame(set, set.withAll(Collections.<Integer>emptyList()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withAll_throws() {
        new BasicSet1<>(1).withAll(null);
    }

    @Test
    public void test_without() {
        ConstSet<Integer> set = new BasicSet1<>(1);
        assertSame(BasicSet0.instance(), set.without(1));
        assertSame(set, set.without(2));
        assertSame(set, set.without(null));
    }

    @Test
    public void test_withoutAll() {
        ConstSet<Integer> set = new BasicSet1<>(1);
        assertSame(BasicSet0.instance(), set.withoutAll(Arrays.asList(1)));
        assertSame(BasicSet0.instance(), set.withoutAll(Arrays.asList(2, 1)));
        assertSame(set, set.withoutAll(Arrays.asList(2)));
        assertSame(set, set.withoutAll(Arrays.asList()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withoutAll_throws() {
        new BasicSet1<>(1).withoutAll(null);
    }

    @Test
    public void test_serialization() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);

        ConstSet<Integer> set = new BasicSet1<>("a");

        out.writeObject(set);
        byte[] data = baos.toByteArray();
        assertEquals(
            "aced05sr0%net.nullschool.collect.basic.SetProxy00000001300xpw40001t01ax",
            BasicToolsTest.asReadableString(data));

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));

        ConstSet<?> read = (ConstSet)in.readObject();
        compare_sets(set, read);
        assertSame(set.getClass(), read.getClass());
    }

    @Test
    public void test_get() {
        assertEquals(1, new BasicSet1<>(1).get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_out_of_bounds_get() {
        new BasicSet1<>(1).get(1);
    }

    @Test
    public void test_non_equality() {
        assertFalse(new BasicSet1<>(1).equals(newSet(2)));
        assertFalse(newSet(2).equals(new BasicSet1<>(1)));
    }
}
