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

import org.junit.Test;

import java.util.*;

import static net.nullschool.collect.MapTools.*;
import static org.junit.Assert.*;


/**
 * 2013-02-06<p/>
 *
 * @author Cameron Beccario
 */
public final class MapToolsTest {

    @Test
    public void test_interleave() {
        // one entry
        assertEquals("{a=1}", interleave(new String[] {"a"}, new Integer[] {1}).toString());
        // two entries
        assertEquals("{a=1, b=2}", interleave(new String[] {"a", "b"}, new Integer[] {1, 2}).toString());
        // duplicate key
        assertEquals("{a=2}", interleave(new String[] {"a", "a"}, new Integer[] {1, 2}).toString());
        // extra keys
        assertEquals("{a=1}", interleave(new String[] {"a", "b"}, new Integer[] {1}).toString());
        // extra values
        assertEquals("{a=1}", interleave(new String[] {"a"}, new Integer[] {1, 2}).toString());
        // empty map
        assertEquals("{}", interleave(new String[] {}, new Integer[] {}).toString());
    }

    @Test(expected = NullPointerException.class)
    public void test_bad_interleave() {
        interleave(new String[] {}, null);
    }

    @Test(expected = NullPointerException.class)
    public void test_bad_interleave_2() {
        interleave(null, new Integer[] {});
    }

    @Test
    public void test_putAll() {
        Map<String, Integer> map = new HashMap<>();
        Map<String, Integer> source = new HashMap<>();
        source.put("a", 1);
        source.put("b", 2);
        assertSame(map, putAll(map, source));
        assertEquals(source, map);
        try { putAll(map, null);    fail(); } catch (NullPointerException ignored) {}
        try { putAll(null, source); fail(); } catch (NullPointerException ignored) {}
    }

    @Test
    public void test_removeAll() {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        Collection<String> keys = Arrays.asList("a", "b");
        assertSame(map, removeAll(map, keys));
        Map<String, Integer> expected = new HashMap<>();
        expected.put("c", 3);
        assertEquals(expected, map);
        try { removeAll(map, null);  fail(); } catch (NullPointerException ignored) {}
        try { removeAll(null, keys); fail(); } catch (NullPointerException ignored) {}
    }
}
