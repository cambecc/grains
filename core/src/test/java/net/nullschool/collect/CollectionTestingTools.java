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

import java.util.*;

import static java.util.Collections.reverseOrder;
import static org.junit.Assert.*;


/**
 * 2013-03-13<p/>
 *
 * A set of utility methods that perform generic tests on collections.<p/>
 *
 * Many of these utility methods perform a side-by-side comparison of two collections. The first {@code expected}
 * collection is known to have proper behavior. The second {@code actual} collection is checked to ensure it
 * exhibits the same behavior as the {@code expected} collection when a series of operations are applied to both.
 *
 * @author Cameron Beccario
 */
public enum CollectionTestingTools {;

    public static final class NullSafeReverseComparator<T> implements Comparator<T> {
        @Override public int compare(T left, T right) {
            if (left == null) {
                return right == null ? 0 : 1;
            }
            return right == null ? -1 : reverseOrder().compare(left, right);
        }
        @Override public boolean equals(Object obj) { return obj instanceof NullSafeReverseComparator; }
        @Override public int hashCode() { return 1; }
    }

    private static <E> E nth(Collection<E> c, int n) {
        Iterator<E> iter = c.iterator();
        int i = 0;
        while (i++ < n) {
            iter.next();
        }
        return iter.next();
    }

    private static <K> K nth(Map<K, ?> m, int n) {
        MapIterator<K, ?> iter = IteratorTools.newMapIterator(m);
        int i = 0;
        while (i++ < n) {
            iter.next();
        }
        return iter.next();
    }

    @SafeVarargs
    public static <T> Set<T> newSet(T... a) {
        return new LinkedHashSet<>(Arrays.asList(a));
    }

    @SafeVarargs
    public static <T> SortedSet<T> newSortedSet(Comparator<? super T> comparator, T... a) {
        TreeSet<T> set = new TreeSet<>(comparator);
        Collections.addAll(set, a);
        return set;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> newMap(Object... keysAndValues) {
        Map<K, V> map = new LinkedHashMap<>();
        for (int i = 0; i < keysAndValues.length; i += 2) {
            map.put((K)keysAndValues[i], (V)keysAndValues[i + 1]);
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> SortedMap<K, V> newSortedMap(Comparator<? super K> comparator, Object... keysAndValues) {
        TreeMap<K, V> map = new TreeMap<>(comparator);
        for (int i = 0; i < keysAndValues.length; i += 2) {
            map.put((K)keysAndValues[i], (V)keysAndValues[i + 1]);
        }
        return map;

    }

    public static <K, V> Map.Entry<K, V> newEntry(K key, V value) {
        return new AbstractMap.SimpleImmutableEntry<>(key, value);
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public static void assert_collection_immutable(Collection c) {
        try { c.add(1);                      fail(); } catch (UnsupportedOperationException | ClassCastException ignored) {}
        try { c.add(null);                   fail(); } catch (UnsupportedOperationException ignored) {}
        try { c.addAll(Arrays.asList(1));    fail(); } catch (UnsupportedOperationException ignored) {}
        try { c.addAll(Arrays.asList());     fail(); } catch (UnsupportedOperationException ignored) {}
        try { c.addAll(null);                fail(); } catch (UnsupportedOperationException ignored) {}
        try { c.remove(1);                   fail(); } catch (UnsupportedOperationException ignored) {}
        try { c.remove(null);                fail(); } catch (UnsupportedOperationException ignored) {}
        try { c.removeAll(Arrays.asList(1)); fail(); } catch (UnsupportedOperationException ignored) {}
        try { c.removeAll(Arrays.asList());  fail(); } catch (UnsupportedOperationException ignored) {}
        try { c.removeAll(null);             fail(); } catch (UnsupportedOperationException ignored) {}
        try { c.retainAll(Arrays.asList(1)); fail(); } catch (UnsupportedOperationException ignored) {}
        try { c.retainAll(Arrays.asList());  fail(); } catch (UnsupportedOperationException ignored) {}
        try { c.retainAll(null);             fail(); } catch (UnsupportedOperationException ignored) {}
        try { c.clear();                     fail(); } catch (UnsupportedOperationException ignored) {}

        for (Iterator iter = c.iterator(); iter.hasNext(); iter.next()) {
            try { iter.remove(); fail(); } catch (UnsupportedOperationException ignored) {}
        }
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public static void assert_list_immutable(List l) {
        assert_collection_immutable(l);

        try { l.add(0, 1);                   fail(); } catch (UnsupportedOperationException ignored) {}
        try { l.addAll(0, Arrays.asList(1)); fail(); } catch (UnsupportedOperationException ignored) {}
        try { l.addAll(0, Arrays.asList());  fail(); } catch (UnsupportedOperationException ignored) {}
        try { l.addAll(0, null);             fail(); } catch (UnsupportedOperationException ignored) {}
        try { l.set(0, 1);                   fail(); } catch (UnsupportedOperationException ignored) {}
        try { l.set(0, null);                fail(); } catch (UnsupportedOperationException ignored) {}
        try { l.remove(0);                   fail(); } catch (UnsupportedOperationException ignored) {}

        for (ListIterator iter = l.listIterator(); iter.hasNext(); iter.next()) {
            try { iter.add(1);    fail(); } catch (UnsupportedOperationException ignored) {}
            try { iter.add(null); fail(); } catch (UnsupportedOperationException ignored) {}
            try { iter.set(1);    fail(); } catch (UnsupportedOperationException ignored) {}
            try { iter.set(null); fail(); } catch (UnsupportedOperationException ignored) {}
            try { iter.remove();  fail(); } catch (UnsupportedOperationException ignored) {}
        }

        if (l.size() > 1) {
            assert_list_immutable(l.subList(0, l.size() / 2));
        }
    }

    public static void assert_set_immutable(Set s) {
        assert_collection_immutable(s);
    }

    public static <E> void assert_sorted_set_immutable(SortedSet<E> s) {
        assert_set_immutable(s);
        if (!s.isEmpty()) {
            assert_sorted_set_immutable(s.headSet(s.last()));
        }
        if (s.size() > 1) {
            E from = nth(s, 1);
            assert_sorted_set_immutable(s.tailSet(from));
            assert_sorted_set_immutable(s.subSet(from, s.last()));
        }
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public static void assert_map_immutable(Map m) {

        try { m.put("a", 1);                    fail(); } catch (UnsupportedOperationException ignored) {}
        try { m.put(null, null);                fail(); } catch (UnsupportedOperationException ignored) {}
        try { m.putAll(Collections.emptyMap()); fail(); } catch (UnsupportedOperationException ignored) {}
        try { m.putAll(null);                   fail(); } catch (UnsupportedOperationException ignored) {}
        try { m.remove(1);                      fail(); } catch (UnsupportedOperationException ignored) {}
        try { m.remove(null);                   fail(); } catch (UnsupportedOperationException ignored) {}
        try { m.clear();                        fail(); } catch (UnsupportedOperationException ignored) {}

        if (m instanceof IterableMap) {
            for (MapIterator iter = ((IterableMap)m).iterator(); iter.hasNext(); iter.next()) {
                try { iter.remove();  fail(); } catch (UnsupportedOperationException ignored) {}
            }
        }

        assert_set_immutable(m.keySet());
        Collection values = m.values();
        if (values instanceof List) {
            assert_list_immutable((List)values);
        }
        else {
            assert_collection_immutable(values);
        }
        assert_set_immutable(m.entrySet());
    }

    public static <K, V> void assert_sorted_map_immutable(SortedMap<K, V> m) {
        assert_map_immutable(m);
        if (!m.isEmpty()) {
            assert_sorted_map_immutable(m.headMap(m.lastKey()));
        }
        if (m.size() > 1) {
            K from = nth(m, 1);
            assert_sorted_map_immutable(m.tailMap(from));
            assert_sorted_map_immutable(m.subMap(from, m.lastKey()));
        }
    }

    // =================================================================================================================
    // collection comparisons

    public static void compare_iterators(Iterator<?> expected, Iterator<?> actual) {
        try { expected.remove(); fail(); } catch (IllegalStateException | UnsupportedOperationException ignored) {}
        try { actual.remove();   fail(); } catch (IllegalStateException | UnsupportedOperationException ignored) {}

        while (expected.hasNext()) {
            assertTrue(actual.hasNext());
            assertEquals(expected.next(), actual.next());
        }
        assertFalse(actual.hasNext());

        try { expected.next(); fail(); } catch (NoSuchElementException ignored) {}
        try { actual.next();   fail(); } catch (NoSuchElementException ignored) {}
    }

    public static void compare_list_iterators(ListIterator<?> expected, ListIterator<?> actual) {
        try { expected.remove(); fail(); } catch (IllegalStateException | UnsupportedOperationException ignored) {}
        try { actual.remove();   fail(); } catch (IllegalStateException | UnsupportedOperationException ignored) {}

        // walk to the end
        while (expected.hasNext()) {
            assertTrue(actual.hasNext());
            assertEquals(expected.nextIndex(), actual.nextIndex());
            assertEquals(expected.hasPrevious(), actual.hasPrevious());
            assertEquals(expected.previousIndex(), actual.previousIndex());

            assertEquals(expected.next(), actual.next());
        }
        assertFalse(actual.hasNext());
        assertEquals(expected.nextIndex(), actual.nextIndex());
        assertEquals(expected.hasPrevious(), actual.hasPrevious());
        assertEquals(expected.previousIndex(), actual.previousIndex());

        try { expected.next(); fail(); } catch (NoSuchElementException ignored) {}
        try { actual.next();   fail(); } catch (NoSuchElementException ignored) {}

        // walk back to the beginning
        while (expected.hasPrevious()) {
            assertTrue(actual.hasPrevious());
            assertEquals(expected.previousIndex(), actual.previousIndex());
            assertEquals(expected.hasNext(), actual.hasNext());
            assertEquals(expected.nextIndex(), actual.nextIndex());

            assertEquals(expected.previous(), actual.previous());
        }
        assertFalse(actual.hasPrevious());
        assertEquals(expected.previousIndex(), actual.previousIndex());
        assertEquals(expected.hasNext(), actual.hasNext());
        assertEquals(expected.nextIndex(), actual.nextIndex());

        try { expected.previous(); fail(); } catch (NoSuchElementException ignored) {}
        try { actual.previous();   fail(); } catch (NoSuchElementException ignored) {}
    }

    public static void compare_collections(Collection<?> expected, Collection<?> actual) {
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.isEmpty(), actual.isEmpty());
        assertEquals(expected.toString(), actual.toString());
        assertArrayEquals(expected.toArray(), actual.toArray());
        assertEquals(expected.equals(new Object()), actual.equals(new Object()));
        assertEquals(expected.equals(expected), actual.equals(actual));

        Object[] expectedArray;
        Object[] actualArray;

        // Arrays correct size
        expectedArray = new Object[expected.size()];
        actualArray = new Object[actual.size()];
        assertArrayEquals(
            expected.toArray(expectedArray),
            actual.toArray(actualArray));

        // Arrays too big
        expectedArray = new Object[expected.size() + 3];
        actualArray = new Object[actual.size() + 3];
        Arrays.fill(expectedArray, "foo");
        Arrays.fill(actualArray, "foo");
        assertArrayEquals(
            expected.toArray(expectedArray),
            actual.toArray(actualArray));

        // Arrays too small
        expectedArray = new Object[Math.max(0, expected.size() - 1)];
        actualArray = new Object[Math.max(0, expected.size() - 1)];
        assertArrayEquals(
            expected.toArray(expectedArray),
            actual.toArray(actualArray));

        assertTrue(expected.containsAll(actual));
        assertTrue(actual.containsAll(expected));

        for (Object item : expected) {
            assertTrue(actual.contains(item));
        }

        Object item = new Object();
        try { assertFalse(expected.contains(item)); } catch (ClassCastException ignored) {}
        try { assertFalse(actual.contains(item));   } catch (ClassCastException ignored) {}

        compare_iterators(expected.iterator(), actual.iterator());
    }

    public static void compare_lists(List<?> expected, List<?> actual) {
        compare_collections(expected, actual);
        assertEquals(expected, actual);
        assertEquals(actual, expected);
        assertEquals(expected.hashCode(), actual.hashCode());

        for (int i = 0; i < expected.size(); i++) {
            Object expectedItem = expected.get(i);
            Object actualItem = actual.get(i);
            assertEquals(expectedItem, actualItem);
            assertEquals(expected.indexOf(expectedItem), actual.indexOf(actualItem));
            assertEquals(expected.lastIndexOf(expectedItem), actual.lastIndexOf(actualItem));
        }

        try { expected.get(-1); fail(); } catch (IndexOutOfBoundsException ignored) {}
        try { actual.get(-1);   fail(); } catch (IndexOutOfBoundsException ignored) {}
        try { expected.get(expected.size()); fail(); } catch (IndexOutOfBoundsException ignored) {}
        try { actual.get(actual.size());     fail(); } catch (IndexOutOfBoundsException ignored) {}

        compare_list_iterators(expected.listIterator(), actual.listIterator());
        compare_list_iterators(expected.listIterator(expected.size()), actual.listIterator(actual.size()));

        try { expected.listIterator(-1); fail(); } catch (IndexOutOfBoundsException ignored) {}
        try { actual.listIterator(-1);   fail(); } catch (IndexOutOfBoundsException ignored) {}
        try { expected.listIterator(expected.size() + 1); fail(); } catch (IndexOutOfBoundsException ignored) {}
        try { actual.listIterator(actual.size() + 1);     fail(); } catch (IndexOutOfBoundsException ignored) {}

        if (expected.size() > 1) {
            compare_list_iterators(expected.listIterator(expected.size() / 2), actual.listIterator(actual.size() / 2));
            compare_lists(expected.subList(1, expected.size() / 2), actual.subList(1, expected.size() / 2));
        }

        Object item = new Object();
        try { assertEquals(-1, expected.indexOf(item)); } catch (ClassCastException ignored) {}
        try { assertEquals(-1, actual.indexOf(item));   } catch (ClassCastException ignored) {}
        try { assertEquals(-1, expected.lastIndexOf(item)); } catch (ClassCastException ignored) {}
        try { assertEquals(-1, actual.lastIndexOf(item));   } catch (ClassCastException ignored) {}
    }

    public static void compare_sets(Set<?> expected, Set<?> actual) {
        compare_collections(expected, actual);
        assertEquals(expected, actual);
        assertEquals(actual, expected);
        assertEquals(expected.hashCode(), actual.hashCode());
    }

    @SafeVarargs
    private static <E> void compare_ranges(SortedSet<E> expected, SortedSet<E> actual, E... rangePoints) {
        // Iterate over points [P0, ..., Pn]. Compare all sub sets described by each pair [Pi-1, Pi].
        // For good measure, also compare head and tail sets for each point Pi.

        for (int i = 0; i < rangePoints.length; i++) {
            E point = rangePoints[i];
            compare_sorted_sets(expected.headSet(point), actual.headSet(point));
            compare_sorted_sets(expected.tailSet(point), actual.tailSet(point));

            if (i > 0) {
                E previous = rangePoints[i - 1];

                SortedSet<E> expectedSubSet = null;
                SortedSet<E> actualSubSet = null;
                Exception expectedException = null;
                Exception actualException = null;

                // Construct subsets from the current point and the previous point. These points might be
                // inverted as part of the test. In this case, we expect construction of the subsets to both
                // throw the same exception.

                try {
                    expectedSubSet = expected.subSet(previous, point);
                }
                catch (IllegalArgumentException e) {
                    expectedException = e;
                }
                try {
                    actualSubSet = actual.subSet(previous, point);
                }
                catch (IllegalArgumentException e) {
                    actualException = e;
                }

                if (expectedException != null) {
                    assertNotNull(actualException);
                }
                else {
                    compare_sorted_sets(expectedSubSet, actualSubSet);
                }
            }
        }
    }

    @SafeVarargs
    public static <X, E> void compare_sorted_sets(SortedSet<X> expectedSet, SortedSet<E> actual, E... rangePoints) {
        @SuppressWarnings("unchecked") SortedSet<E> expected = (SortedSet<E>)expectedSet;
        compare_sets(expected, actual);
        assertEquals(expected.comparator(), actual.comparator());

        if (expected.isEmpty()) {
            try { expected.first(); fail(); } catch (NoSuchElementException ignored) {}
            try { actual.first();   fail(); } catch (NoSuchElementException ignored) {}
            try { expected.last();  fail(); } catch (NoSuchElementException ignored) {}
            try { actual.last();    fail(); } catch (NoSuchElementException ignored) {}
        }
        else {
            assertEquals(expected.first(), actual.first());
            assertEquals(expected.last(), actual.last());

            // Compare the head sets of everything up to but not including the last item.
            compare_sorted_sets(expected.headSet(expected.last()), actual.headSet(actual.last()));

            if (expected.size() > 1) {
                // Compare the tail sets of everything from the second item onwards.
                E from = nth(expected, 1);
                compare_sorted_sets(expected.tailSet(from), actual.tailSet(from));
            }
        }

        compare_ranges(expected, actual, rangePoints);
    }

    public static void compare_entries(Map.Entry<?, ?> expected, Map.Entry<?, ?> actual) {
        assertEquals(expected.getKey(), actual.getKey());
        assertEquals("values unequal for key '" + expected.getKey() + "'.", expected.getValue(), actual.getValue());
        assertEquals(expected, actual);
        assertEquals(actual, expected);
        assertEquals(expected.hashCode(), actual.hashCode());
        assertEquals(expected.toString(), actual.toString());
    }

    public static void compare_maps(Map<?, ?> expected, Map<?, ?> actual) {
        Iterator<? extends Map.Entry<?, ?>> actualIterator = actual.entrySet().iterator();
        for (Map.Entry<?, ?> expectedEntry : expected.entrySet()) {
            assertTrue(actualIterator.hasNext());
            compare_entries(expectedEntry, actualIterator.next());

            assertTrue(actual.containsKey(expectedEntry.getKey()));
            assertTrue(actual.containsValue(expectedEntry.getValue()));
            assertEquals(expectedEntry.getValue(), actual.get(expectedEntry.getKey()));
        }
        assertFalse(actualIterator.hasNext());

        if (actual instanceof IterableMap) {
            MapIterator<?, ?> iter = ((IterableMap)actual).iterator();

            try { iter.value();  fail(); } catch (IllegalStateException ignored) {}
            try { iter.entry();  fail(); } catch (IllegalStateException ignored) {}
            try { iter.remove(); fail(); } catch (IllegalStateException | UnsupportedOperationException ignored) {}

            Object lastValue = null;
            Map.Entry<?, ?> lastEntry = null;

            for (Map.Entry<?, ?> expectedEntry : expected.entrySet()) {
                assertTrue(iter.hasNext());
                assertEquals(expectedEntry.getKey(), iter.next());
                assertEquals(expectedEntry.getValue(), lastValue = iter.value());
                compare_entries(expectedEntry, lastEntry = iter.entry());
            }
            assertFalse(iter.hasNext());

            try { iter.next(); fail(); } catch (NoSuchElementException ignored) {}
            if (!actual.isEmpty()) {
                // make sure the iterator still returns the last value and entry after the failed call to next.
                assertEquals(lastValue, iter.value());
                assertEquals(lastEntry, iter.entry());
            }
        }

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.isEmpty(), actual.isEmpty());
        assertEquals(expected, actual);
        assertEquals(actual, expected);
        assertEquals(expected.hashCode(), actual.hashCode());
        assertEquals(expected.toString(), actual.toString());
        assertEquals(expected.equals(new Object()), actual.equals(new Object()));
        assertEquals(expected.equals(expected), actual.equals(actual));

        Object item = new Object();
        try { assertFalse(expected.containsKey(item)); } catch (ClassCastException ignored) {}
        try { assertFalse(actual.containsKey(item));   } catch (ClassCastException ignored) {}
        try { assertFalse(expected.containsValue(item)); } catch (ClassCastException ignored) {}
        try { assertFalse(actual.containsValue(item));   } catch (ClassCastException ignored) {}
        try { assertNull(expected.get(item)); } catch (ClassCastException ignored) {}
        try { assertNull(actual.get(item));   } catch (ClassCastException ignored) {}

        compare_sets(expected.keySet(), actual.keySet());
        compare_collections(expected.values(), actual.values());
        compare_sets(expected.entrySet(), actual.entrySet());

        Map.Entry entry = newEntry(new Object(), new Object());
        try { assertFalse(expected.entrySet().contains(entry)); } catch (ClassCastException ignored) {}
        try { assertFalse(actual.entrySet().contains(entry));   } catch (ClassCastException ignored) {}
    }

    @SafeVarargs
    private static <K, V> void compare_ranges(SortedMap<K, V> expected, SortedMap<K, V> actual, K... rangePoints) {
        // Iterate over points [P0, ..., Pn]. Compare all sub maps described by each pair [Pi-1, Pi].
        // For good measure, also compare head and tail maps for each point Pi.

        for (int i = 0; i < rangePoints.length; i++) {
            K point = rangePoints[i];
            compare_sorted_maps(expected.headMap(point), actual.headMap(point));
            compare_sorted_maps(expected.tailMap(point), actual.tailMap(point));

            if (i > 0) {
                K previous = rangePoints[i - 1];

                SortedMap<K, V> expectedSubMap = null;
                SortedMap<K, V> actualSubMap = null;
                Exception expectedException = null;
                Exception actualException = null;

                // Construct sub maps from the current point and the previous point. These points might be
                // inverted as part of the test. In this case, we expect construction of the sub maps to both
                // throw the same exception.

                try {
                    expectedSubMap = expected.subMap(previous, point);
                }
                catch (IllegalArgumentException e) {
                    expectedException = e;
                }
                try {
                    actualSubMap = actual.subMap(previous, point);
                }
                catch (IllegalArgumentException e) {
                    actualException = e;
                }

                if (expectedException != null) {
                    assertNotNull(actualException);
                }
                else {
                    compare_sorted_maps(expectedSubMap, actualSubMap);
                }
            }
        }
    }

    @SafeVarargs
    public static <XK, XV, K, V> void compare_sorted_maps(
        SortedMap<XK, XV> expectedMap,
        SortedMap<K, V> actual,
        K... rangePoints) {

        @SuppressWarnings("unchecked") SortedMap<K, V> expected = (SortedMap<K, V>)expectedMap;
        compare_maps(expected, actual);
        assertEquals(expected.comparator(), actual.comparator());

        if (expected.isEmpty()) {
            try { expected.firstKey(); fail(); } catch (NoSuchElementException ignored) {}
            try { actual.firstKey();   fail(); } catch (NoSuchElementException ignored) {}
            try { expected.lastKey();  fail(); } catch (NoSuchElementException ignored) {}
            try { actual.lastKey();    fail(); } catch (NoSuchElementException ignored) {}
        }
        else {
            assertEquals(expected.firstKey(), actual.firstKey());
            assertEquals(expected.lastKey(), actual.lastKey());

            // Compare the head maps of everything up to but not including the last item.
            compare_sorted_maps(expected.headMap(expected.lastKey()), actual.headMap(actual.lastKey()));

            if (expected.size() > 1) {
                // Compare the tail maps of everything from the second item onwards.
                K from = nth(expected, 1);
                compare_sorted_maps(expected.tailMap(from), actual.tailMap(from));
            }
        }

        compare_ranges(expected, actual, rangePoints);
    }

    // =================================================================================================================
    // collection mutations

    @SafeVarargs
    public static <E> void add(Collection<E> expected, Collection<E> actual, E... elements) {
        for (E e : elements) {
            boolean expectedResult = expected.add(e);
            boolean actualResult = actual.add(e);
            assertEquals(expectedResult, actualResult);
        }
    }

    @SafeVarargs
    public static <E> void addAll(Collection<E> expected, Collection<E> actual, E... elements) {
        Collection<E> c = Collections.unmodifiableCollection(Arrays.asList(elements));
        boolean expectedResult = expected.addAll(c);
        boolean actualResult = actual.addAll(c);
        assertEquals(expectedResult, actualResult);
    }

    @SafeVarargs
    public static <E> void remove(Collection<E> expected, Collection<E> actual, E... elements) {
        for (E e : elements) {
            boolean expectedResult = expected.remove(e);
            boolean actualResult = actual.remove(e);
            assertEquals(expectedResult, actualResult);
        }
    }

    @SafeVarargs
    public static <E> void removeAll(Collection<E> expected, Collection<E> actual, E... elements) {
        Collection<E> c = Collections.unmodifiableCollection(Arrays.asList(elements));
        boolean expectedResult = expected.removeAll(c);
        boolean actualResult = actual.removeAll(c);
        assertEquals(expectedResult, actualResult);
    }

    @SafeVarargs
    public static <E> void retainAll(Collection<E> expected, Collection<E> actual, E... elements) {
        Collection<E> c = Collections.unmodifiableCollection(Arrays.asList(elements));
        boolean expectedResult = expected.retainAll(c);
        boolean actualResult = actual.retainAll(c);
        assertEquals(expectedResult, actualResult);
    }

    @SafeVarargs
    public static <E> void remove_by_iterator(Collection<E> expected, Collection<E> actual, E... elements) {
        List<E> elementsToRemove = Arrays.asList(elements);
        Iterator<E> expectedIterator = expected.iterator();
        Iterator<E> actualIterator = actual.iterator();

        try { expectedIterator.remove(); fail(); } catch (IllegalStateException ignored) {}
        try { actualIterator.remove(); fail(); } catch (IllegalStateException ignored) {}

        while (expectedIterator.hasNext()) {
            assertTrue(actualIterator.hasNext());

            E e = expectedIterator.next();
            assertEquals(e, actualIterator.next());

            if (elementsToRemove.contains(e)) {
                expectedIterator.remove();
                actualIterator.remove();

                try { expectedIterator.remove(); fail(); } catch (IllegalStateException ignored) {}
                try { actualIterator.remove(); fail(); } catch (IllegalStateException ignored) {}
            }
        }

        assertFalse(actualIterator.hasNext());
    }

    public static <K, V> void put(Map<K, V> expected, Map<K, V> actual, K key, V value) {
        V expectedExisting = expected.put(key, value);
        V actualExisting = actual.put(key, value);
        assertEquals(expectedExisting, actualExisting);
    }

    public static <K, V> void putAll(Map<K, V> expected, Map<K, V> actual, K k0, V v0, K k1, V v1) {
        Map<K, V> map = Collections.unmodifiableMap(CollectionTestingTools.<K, V>newMap(k0, v0, k1, v1));
        expected.putAll(map);
        actual.putAll(map);
    }

    public static <K, V> void remove(Map<K, V> expected, Map<K, V> actual, K key) {
        V expectedExisting = expected.remove(key);
        V actualExisting = actual.remove(key);
        assertEquals(expectedExisting, actualExisting);
    }

    @SafeVarargs
    public static <K, V> void remove_by_map_iterator(Map<K, V> expected, IterableMap<K, V> actual, K... keys) {
        Set<K> keysToRemove = new HashSet<>(Arrays.asList(keys));
        Iterator<Map.Entry<K, V>> expectedIterator = expected.entrySet().iterator();
        MapIterator<K, V> actualIterator = actual.iterator();

        try { expectedIterator.remove(); fail(); } catch (IllegalStateException ignored) {}
        try { actualIterator.remove(); fail(); } catch (IllegalStateException ignored) {}

        while (expectedIterator.hasNext()) {
            assertTrue(actualIterator.hasNext());

            Map.Entry<K, V> e = expectedIterator.next();
            K actualKey = actualIterator.next();
            V actualValue = actualIterator.value();
            assertEquals(e.getKey(), actualKey);
            assertEquals(e.getValue(), actualValue);
            assertEquals(e, actualIterator.entry());

            if (keysToRemove.contains(e.getKey())) {
                expectedIterator.remove();
                actualIterator.remove();

                try { expectedIterator.remove(); fail(); } catch (IllegalStateException ignored) {}
                try { actualIterator.remove(); fail(); } catch (IllegalStateException ignored) {}
            }
        }

        assertFalse(actualIterator.hasNext());
    }
}
