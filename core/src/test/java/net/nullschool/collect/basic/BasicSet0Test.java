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
import java.util.*;

import static net.nullschool.collect.CollectionTestingTools.*;
import static org.junit.Assert.*;

/**
 * 2013-03-17<p/>
 *
 * @author Cameron Beccario
 */
public class BasicSet0Test {

    @Test
    public void test_comparison() {
        compare_sets(Collections.emptySet(), BasicSet0.instance());
    }

    @Test
    public void test_immutable() {
        assert_set_immutable(BasicSet0.instance());
    }

    @Test
    public void test_with() {
        compare_sets(Collections.singleton(1), BasicSet0.instance().with(1));
        compare_sets(Collections.singleton(null), BasicSet0.instance().with(null));
    }

    @Test
    public void test_withAll() {
        ConstSet<Integer> empty = BasicSet0.instance();
        compare_sets(newSet(1, 2, 3), empty.withAll(Arrays.asList(1, 2, 3)));
        assertSame(empty, empty.withAll(Collections.<Integer>emptySet()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withAll_throws() {
        BasicSet0.instance().withAll(null);
    }

    @Test
    public void test_without() {
        assertSame(BasicSet0.instance(), BasicSet0.instance().without(1));
        assertSame(BasicSet0.instance(), BasicSet0.instance().without(null));
    }

    @Test
    public void test_withoutAll() {
        assertSame(BasicSet0.instance(), BasicSet0.instance().withoutAll(Arrays.asList(1)));
        assertSame(BasicSet0.instance(), BasicSet0.instance().withoutAll(Arrays.asList()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withoutAll_throws() {
        BasicList0.instance().withoutAll(null);
    }

    @Test
    public void test_serialization() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);

        out.writeObject(BasicSet0.instance());
        byte[] data = baos.toByteArray();
        assertEquals(
            "aced05sr0%net.nullschool.collect.basic.SetProxy00000001300xpw40000x",
            BasicToolsTest.asReadableString(data));

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));
        assertSame(BasicSet0.instance(), in.readObject());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_out_of_bounds_get() {
        BasicSet0.instance().get(0);
    }

    @Test
    public void test_non_equality() {
        assertFalse(BasicSet0.instance().equals(newSet(1)));
        assertFalse(newSet(1).equals(BasicSet0.instance()));
    }
}
