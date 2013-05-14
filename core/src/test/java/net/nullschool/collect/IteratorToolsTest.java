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

import static net.nullschool.collect.IteratorTools.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static net.nullschool.collect.CollectionTestingTools.*;

/**
 * 2013-04-25<p/>
 *
 * @author Cameron Beccario
 */
public class IteratorToolsTest {

    @Test
    public void test_empty_map_iterator() {
        assertFalse(emptyMapIterator().hasNext());
        try { emptyMapIterator().next();   fail(); } catch (NoSuchElementException ignored) {}
        try { emptyMapIterator().value();  fail(); } catch (IllegalStateException ignored) {}
        try { emptyMapIterator().entry();  fail(); } catch (IllegalStateException ignored) {}
        try { emptyMapIterator().remove(); fail(); } catch (IllegalStateException ignored) {}
        assertSame(emptyMapIterator(), emptyMapIterator());
    }

    @Test
    public void test_newMapIterator_adapter() {
        // Create a mock map with two entries.
        Map map = mock(Map.class);
        Set entrySet = mock(Set.class);
        Iterator entrySetIter = mock(Iterator.class);
        Map.Entry entry1 = new AbstractMap.SimpleImmutableEntry<>("a", 1);
        Map.Entry entry2 = new AbstractMap.SimpleImmutableEntry<>("b", 2);
        when(map.isEmpty()).thenReturn(false);
        when(map.entrySet()).thenReturn(entrySet);
        when(entrySet.iterator()).thenReturn(entrySetIter);
        when(entrySetIter.hasNext()).thenReturn(true, true, false);
        when(entrySetIter.next()).thenReturn(entry1, entry2);

        // Now create the map iterator to test.
        MapIterator iter = newMapIterator((Map<?, ?>)map);

        // Before calling next, these methods should throw.
        try { iter.value(); fail(); } catch (IllegalStateException ignored) {}
        try { iter.entry(); fail(); } catch (IllegalStateException ignored) {}

        // Advance to the first entry.
        assertTrue(iter.hasNext());
        assertEquals(entry1.getKey(), iter.next());
        assertEquals(entry1.getValue(), iter.value());
        assertSame(entry1, iter.entry());

        // Remove the first entry.
        iter.remove();
        try { iter.value(); fail(); } catch (IllegalStateException ignored) {}
        try { iter.entry(); fail(); } catch (IllegalStateException ignored) {}

        // Advance to the second entry.
        assertTrue(iter.hasNext());
        assertEquals(entry2.getKey(), iter.next());
        assertEquals(entry2.getValue(), iter.value());
        assertSame(entry2, iter.entry());

        // Remove the second entry.
        iter.remove();
        try { iter.value(); fail(); } catch (IllegalStateException ignored) {}
        try { iter.entry(); fail(); } catch (IllegalStateException ignored) {}

        verify(entrySetIter, times(2)).hasNext();
        verify(entrySetIter, times(2)).next();
        verify(entrySetIter, times(2)).remove();

        assertFalse(iter.hasNext());
    }

    @Test
    public void test_newMapIterator_empty() {
        assertSame(emptyMapIterator(), newMapIterator(Collections.emptyMap()));

        IterableMap<?, ?> map = mock(AbstractIterableMap.class);
        when(map.isEmpty()).thenReturn(true);
        assertSame(emptyMapIterator(), newMapIterator(map));
    }

    @Test
    public void test_newMapIterator_on_iterableMap() {
        IterableMap map = mock(IterableMap.class);
        MapIterator iter = mock(MapIterator.class);
        when(map.isEmpty()).thenReturn(false);
        when(map.iterator()).thenReturn(iter);

        assertSame(iter, newMapIterator((Map<?, ?>)map));
    }

    @Test(expected = NullPointerException.class)
    public void test_newMapIterator_throws() {
        newMapIterator(null);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void test_chainMapIterators() {
        // Create two mock iterators that both have two entries.
        MapIterator first = mock(MapIterator.class);
        MapIterator second = mock(MapIterator.class);
        Map.Entry entry1 = newEntry("a", 1);
        Map.Entry entry2 = newEntry("b", 2);
        Map.Entry entry3 = newEntry("c", 3);
        Map.Entry entry4 = newEntry("d", 4);
        when(first.hasNext()).thenReturn(true, true, false);
        when(first.next()).thenReturn(entry1.getKey(), entry2.getKey());
        when(first.value()).thenReturn(entry1.getValue(), entry2.getValue());
        when(first.entry()).thenReturn(entry1, entry2);
        when(second.hasNext()).thenReturn(true, true, false);
        when(second.next()).thenReturn(entry3.getKey(), entry4.getKey()).thenThrow(NoSuchElementException.class);
        when(second.value()).thenReturn(entry3.getValue(), entry4.getValue());
        when(second.entry()).thenReturn(entry3, entry4);

        MapIterator iter = chainMapIterators(first, second);

        assertTrue(iter.hasNext());
        assertEquals(entry1.getKey(), iter.next());
        assertEquals(entry1.getValue(), iter.value());
        assertSame(entry1, iter.entry());

        assertTrue(iter.hasNext());
        assertEquals(entry2.getKey(), iter.next());
        assertEquals(entry2.getValue(), iter.value());
        assertSame(entry2, iter.entry());

        iter.remove();

        assertTrue(iter.hasNext());
        assertEquals(entry3.getKey(), iter.next());
        assertEquals(entry3.getValue(), iter.value());
        assertSame(entry3, iter.entry());

        assertTrue(iter.hasNext());
        assertEquals(entry4.getKey(), iter.next());
        assertEquals(entry4.getValue(), iter.value());
        assertSame(entry4, iter.entry());

        assertFalse(iter.hasNext());
        try { iter.next(); fail(); } catch (NoSuchElementException ignored) {}

        iter.remove();

        verify(first, times(3)).hasNext();
        verify(first, times(2)).next();
        verify(first, times(2)).value();
        verify(first, times(2)).entry();
        verify(first, times(1)).remove();

        verify(second, times(3)).hasNext();
        verify(second, times(3)).next();
        verify(second, times(2)).value();
        verify(second, times(2)).entry();
        verify(second, times(1)).remove();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void test_chainMapIterators_without_calling_hasNext() {
        // Create two mock iterators that both have one entry.
        MapIterator first = mock(MapIterator.class);
        MapIterator second = mock(MapIterator.class);
        Map.Entry entry1 = newEntry("a", 1);
        Map.Entry entry2 = newEntry("b", 2);
        when(first.next()).thenReturn(entry1.getKey()).thenThrow(NoSuchElementException.class);
        when(first.value()).thenReturn(entry1.getValue());
        when(first.entry()).thenReturn(entry1);
        when(second.next()).thenReturn(entry2.getKey()).thenThrow(NoSuchElementException.class);
        when(second.value()).thenReturn(entry2.getValue());
        when(second.entry()).thenReturn(entry2);

        MapIterator iter = chainMapIterators(first, second);
        assertEquals(entry1.getKey(), iter.next());
        assertEquals(entry1.getValue(), iter.value());
        assertSame(entry1, iter.entry());

        // This call to next should swallow the first iterator's NoSuchElementException and move to the second.
        assertEquals(entry2.getKey(), iter.next());
        assertEquals(entry2.getValue(), iter.value());
        assertSame(entry2, iter.entry());

        try { iter.next(); fail(); } catch (NoSuchElementException ignored) {}

        verify(first, times(0)).hasNext();
        verify(first, times(2)).next();
        verify(first, times(1)).value();
        verify(first, times(1)).entry();

        verify(second, times(0)).hasNext();
        verify(second, times(2)).next();
        verify(second, times(1)).value();
        verify(second, times(1)).entry();
    }

    @Test
    public void test_chainMapIterators_throws() {
        try { chainMapIterators(emptyMapIterator(), null); fail(); } catch (NullPointerException ignored) {}
        try { chainMapIterators(null, emptyMapIterator()); fail(); } catch (NullPointerException ignored) {}
        try { chainMapIterators(null, null);               fail(); } catch (NullPointerException ignored) {}
    }

    @Test
    @SuppressWarnings("unchecked")
    public void test_chainMapIterators_short_circuits_empty_iterator() {
        MapIterator iter = mock(MapIterator.class);
        assertSame(iter, chainMapIterators(iter, emptyMapIterator()));
        assertSame(iter, chainMapIterators(emptyMapIterator(), iter));
    }
}
