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

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;

import static org.junit.Assert.*;

/**
 * 2013-04-25<p/>
 *
 * @author Cameron Beccario
 */
public class AbstractEntryTest {

    private static <K, V> Entry<K, V> mockEntry(final K key, final V value) {
        return new AbstractEntry<K, V>() {
            @Override public K getKey() { return key; }
            @Override public V getValue() { return value; }
            @Override public V setValue(Object value) { throw new UnsupportedOperationException(); }
        };
    }

    @Test
    public void test_equals() {
        assertEquals(new SimpleImmutableEntry<>("a", 1), mockEntry("a", 1));
        assertEquals(new SimpleImmutableEntry<>(null, null), mockEntry(null, null));
        assertEquals(mockEntry("a", 1), new SimpleImmutableEntry<>("a", 1));
        assertEquals(mockEntry(null, null), new SimpleImmutableEntry<>(null, null));
        assertNotEquals(mockEntry("a", 1), new Object());
    }

    @Test
    public void test_hashCode() {
        assertEquals(new SimpleImmutableEntry<>("a", 1).hashCode(), AbstractEntry.hashCode("a", 1));
        assertEquals(new SimpleImmutableEntry<>("a", 1).hashCode(), mockEntry("a", 1).hashCode());
        assertEquals(new SimpleImmutableEntry<>(null, null).hashCode(), AbstractEntry.hashCode(null, null));
        assertEquals(new SimpleImmutableEntry<>(null, null).hashCode(), mockEntry(null, null).hashCode());
    }

    @Test
    public void test_toString() {
        assertEquals(new SimpleImmutableEntry<>("a", 1).toString(), mockEntry("a", 1).toString());
        assertEquals(new SimpleImmutableEntry<>(null, null).toString(), mockEntry(null, null).toString());
    }
}
