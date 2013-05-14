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
public class BasicSetNTest {

    @Test
    public void test_comparison() {
        compare_sets(
            newSet(1, 2, 3, 4, 5, 6),
            new BasicSetN<>(new Object[] {1, 2, 3, 4, 5, 6}));
    }

    @Test
    public void test_immutable() {
        assert_set_immutable(new BasicSetN<>(new Object[] {1, 2, 3, 4, 5, 6}));
    }

    @Test
    public void test_with() {
        ConstSet<Integer> set;

        set = new BasicSetN<>(new Object[] {1, 2, 3, 4, 5, 6});
        compare_sets(newSet(1, 2, 3, 4, 5, 6, 7), set.with(7));
        compare_sets(newSet(1, 2, 3, 4, 5, 6, null), set.with(null));
        assertSame(set, set.with(6));

        set = new BasicSetN<>(new Object[] {1, 2, 3, 4, 5, null});
        assertSame(set, set.with(null));
    }

    @Test
    public void test_withAll() {
        ConstSet<Integer> set = new BasicSetN<>(new Object[] {1, 2, 3, 4, 5, 6});
        compare_sets(newSet(1, 2, 3, 4, 5, 6, 7), set.withAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 1)));

        assertSame(set, set.withAll(Arrays.asList(1, 1, 1, 2, 2, 2, 3, 3)));
        assertSame(set, set.withAll(Collections.<Integer>emptyList()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withAll_throws() {
        new BasicSetN<>(new Object[] {1, 2, 3, 4, 5, 6}).withAll(null);
    }

    @Test
    public void test_without() {
        ConstSet<Integer> set = new BasicSetN<>(new Object[] {1, 2, 3, 4, 5, 6});
        compare_sets(newSet(2, 3, 4, 5, 6), set.without(1));
        compare_sets(newSet(1, 3, 4, 5, 6), set.without(2));
        compare_sets(newSet(1, 2, 3, 4, 5), set.without(6));
        assertSame(set, set.without(7));
        assertSame(set, set.without(null));
    }

    @Test
    public void test_withoutAll() {
        ConstSet<Integer> set = new BasicSetN<>(new Object[] {1, 2, 3, 4, 5, 6});
        compare_sets(newSet(3, 4, 5, 6), set.withoutAll(Arrays.asList(1, 2)));
        compare_sets(newSet(3, 4, 5, 6), set.withoutAll(Arrays.asList(1, 2, 9)));
        assertSame(set, set.withoutAll(Arrays.asList(7)));
        assertSame(set, set.withoutAll(Arrays.asList()));
        assertSame(BasicSet0.instance(), set.withoutAll(set));
    }

    @Test(expected = NullPointerException.class)
    public void test_withoutAll_throws() {
        new BasicSetN<>(new Object[] {1, 2, 3, 4, 5, 6}).withoutAll(null);
    }

    @Test
    public void test_serialization() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);

        ConstSet<String> set = new BasicSetN<>(new Object[] {"a", "b", "c", "d"});

        out.writeObject(set);
        byte[] data = baos.toByteArray();
        assertEquals(
            "aced05sr0%net.nullschool.collect.basic.SetProxy00000001300xpw40004t01at01bt01ct01dx",
            BasicToolsTest.asReadableString(data));

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));

        ConstSet<?> read = (ConstSet)in.readObject();
        compare_sets(set, read);
        assertSame(set.getClass(), read.getClass());
    }

    @Test
    public void test_get() {
        BasicSetN<Integer> set = new BasicSetN<>(new Object[] {1, 2, 3, 4, 5, 6});
        for (int i = 0; i < set.size(); i++) {
            assertEquals(i + 1, (int)set.get(i));
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_out_of_bounds_get() {
        new BasicSetN<>(new Object[] {1, 2, 3, 4, 5, 6}).get(7);
    }

    @Test
    public void test_non_equality() {
        assertFalse(new BasicSetN<>(new Object[] {1, 2, 3}).equals(newSet(1, 2, 4)));
        assertFalse(newSet(1, 2, 4).equals(new BasicSetN<>(new Object[] {1, 2, 3})));
    }
}
