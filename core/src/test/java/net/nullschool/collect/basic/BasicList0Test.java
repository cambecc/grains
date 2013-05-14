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

import net.nullschool.collect.ConstList;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;

import static net.nullschool.collect.CollectionTestingTools.*;
import static org.junit.Assert.*;


/**
 * 2013-03-16<p/>
 *
 * @author Cameron Beccario
 */
public class BasicList0Test {

    @Test
    public void test_comparison() {
        compare_lists(Collections.emptyList(), BasicList0.instance());
    }

    @Test
    public void test_immutable() {
        assert_list_immutable(BasicList0.instance());
    }

    @Test
    public void test_with() {
        compare_lists(Collections.singletonList(1), BasicList0.instance().with(1));
        compare_lists(Collections.singletonList(null), BasicList0.instance().with(null));
    }

    @Test
    public void test_with_index() {
        compare_lists(Collections.singletonList(1), BasicList0.instance().with(0, 1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_with_index_out_of_bounds() {
        BasicList0.instance().with(1, 1);
    }

    @Test
    public void test_withAll() {
        ConstList<Integer> empty = BasicList0.instance();
        compare_lists(Arrays.asList(1, 2, 3), empty.withAll(Arrays.asList(1, 2, 3)));
        assertSame(empty, empty.withAll(Collections.<Integer>emptyList()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withAll_throws() {
        BasicList0.instance().withAll(null);
    }

    @Test
    public void test_withAll_index() {
        ConstList<Integer> empty = BasicList0.instance();
        compare_lists(Arrays.asList(1, 2, 3), empty.withAll(0, Arrays.asList(1, 2, 3)));
        assertSame(empty, empty.withAll(0, Collections.<Integer>emptyList()));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_withAll_index_out_of_bounds() {
        BasicList0.instance().withAll(1, Collections.<Integer>emptyList());
    }

    @Test(expected = NullPointerException.class)
    public void test_withAll_index_throws() {
        BasicList0.instance().withAll(0, null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_replace_out_of_bounds() {
        BasicList0.instance().replace(0, 1);
    }

    @Test
    public void test_without() {
        assertSame(BasicList0.instance(), BasicList0.instance().without(1));
        assertSame(BasicList0.instance(), BasicList0.instance().without(null));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_delete_out_of_bounds() {
        BasicList0.instance().delete(0);
    }

    @Test
    public void test_withoutAll() {
        assertSame(BasicList0.instance(), BasicList0.instance().withoutAll(Arrays.asList(1)));
        assertSame(BasicList0.instance(), BasicList0.instance().withoutAll(Arrays.asList()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withoutAll_throws() {
        BasicList0.instance().withoutAll(null);
    }

    @Test
    public void test_subList() {
        assertSame(BasicList0.instance(), BasicList0.instance().subList(0, 0));
    }

    @Test
    public void test_serialization() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);

        out.writeObject(BasicList0.instance());
        byte[] data = baos.toByteArray();
        assertEquals(
            "aced05sr0&net.nullschool.collect.basic.ListProxy00000001300xpw40000x",
            BasicToolsTest.asReadableString(data));

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));
        assertSame(BasicList0.instance(), in.readObject());
    }

    @Test
    public void test_non_equality() {
        assertFalse(BasicList0.instance().equals(Arrays.asList(1)));
        assertFalse(Arrays.asList(1).equals(BasicList0.instance()));
    }
}
