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

import net.nullschool.collect.ConstMap;
import net.nullschool.collect.IteratorTools;
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
 * 2013-03-18<p/>
 *
 * @author Cameron Beccario
 */
public class BasicMap0Test {

    @Test
    public void test_comparison() {
        compare_maps(Collections.emptyMap(), BasicMap0.instance());
    }

    @Test
    public void test_immutable() {
        assert_map_immutable(BasicMap0.instance());
    }

    @Test
    public void test_with() {
        compare_maps(Collections.singletonMap("a", 1), BasicMap0.instance().with("a", 1));
        compare_maps(Collections.singletonMap(null, null), BasicMap0.instance().with(null, null));
    }

    @Test
    public void test_withAll() {
        ConstMap<Object, Object> empty = BasicMap0.instance();
        compare_maps(newMap("a", 1, "b", 2), empty.withAll(newMap("a", 1, "b", 2)));
        assertSame(empty, empty.withAll(Collections.emptyMap()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withAll_throws() {
        BasicMap0.instance().withAll(null);
    }

    @Test
    public void test_without() {
        assertSame(BasicMap0.instance(), BasicMap0.instance().without("a"));
        assertSame(BasicMap0.instance(), BasicMap0.instance().without(null));
    }

    @Test
    public void test_withoutAll() {
        assertSame(BasicMap0.instance(), BasicMap0.instance().withoutAll(Arrays.asList("a")));
        assertSame(BasicMap0.instance(), BasicMap0.instance().withoutAll(Arrays.asList()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withoutAll_throws() {
        BasicMap0.instance().withoutAll(null);
    }

    @Test
    public void test_serialization() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);

        out.writeObject(BasicMap0.instance());
        byte[] data = baos.toByteArray();
        assertEquals(
            "aced05sr0%net.nullschool.collect.basic.MapProxy00000001300xpw40000x",
            BasicToolsTest.asReadableString(data));

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));
        assertSame(BasicMap0.instance(), in.readObject());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_out_of_bounds_get_key() {
        BasicMap0.instance().getKey(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_out_of_bounds_get_value() {
        BasicMap0.instance().getValue(0);
    }

    @Test
    public void test_empty_iterator() {
        assertSame(IteratorTools.emptyMapIterator(), BasicMap0.instance().iterator());
    }

    @Test
    public void test_non_equality() {
        assertFalse(BasicMap0.instance().equals(newMap("a", 1)));
        assertFalse(newMap("a", 1).equals(BasicMap0.instance()));
    }
}
