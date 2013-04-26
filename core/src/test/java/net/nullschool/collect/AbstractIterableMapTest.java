package net.nullschool.collect;

import org.junit.Test;

import java.util.*;

import static java.util.Collections.*;
import static net.nullschool.collect.CollectionTestingTools.*;
import static net.nullschool.collect.IteratorTools.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

/**
 * 2013-02-12<p/>
 *
 * @author Cameron Beccario
 */
public class AbstractIterableMapTest {

    private static final class MockIterableMap<K, V> extends AbstractIterableMap<K, V> {
        private final LinkedHashMap<K, V> inner = new LinkedHashMap<>();

        MockIterableMap() {}
        MockIterableMap(Map<? extends K, ? extends V> map) { inner.putAll(map); }

        @Override public int size() { return inner.size(); }
        @Override public V put(K key, V value) { return inner.put(key, value); }
        @Override public MapIterator<K, V> iterator() { return newMapIterator(inner); }
    }

    // =================================================================================================================
    // Map comparison tests

    @Test
    public void test_empty_map() {
        compare_maps(emptyMap(), new MockIterableMap<>(emptyMap()));
    }

    @Test
    public void test_singleton_map() {
        Map<String, Integer> expected = singletonMap("a", 1);
        compare_maps(expected, new MockIterableMap<>(expected));
    }

    @Test
    public void test_map_with_several_values() {
        Map<String, Integer> expected = asMap("a", 1, "b", 2, "c", 3, "d", 4, "e", 5);
        compare_maps(expected, new MockIterableMap<>(expected));
    }

    @Test
    public void test_map_with_nulls() {
        Map<String, Integer> expected;

        expected = asMap("a", null);
        compare_maps(expected, new MockIterableMap<>(expected));

        expected = asMap(null, 5);
        compare_maps(expected, new MockIterableMap<>(expected));

        expected = asMap(null, null);
        compare_maps(expected, new MockIterableMap<>(expected));
    }

    @Test
    public void test_inequality() {
        Map<String, Integer> a;
        IterableMap<String, Integer> b;

        // differ by size
        a = asMap("a", 1, "b", 2);
        b = new MockIterableMap<>(singletonMap("a", 1));
        assertNotEquals(a, b);
        assertNotEquals(b, a);

        // differ by keys
        a = singletonMap("a", 1);
        b = new MockIterableMap<>(singletonMap("b", 1));
        assertNotEquals(a, b);
        assertNotEquals(b, a);

        // differ by values
        a = singletonMap("a", 1);
        b = new MockIterableMap<>(singletonMap("a", 2));
        assertNotEquals(a, b);
        assertNotEquals(b, a);

        // differ by values of null keys
        a = singletonMap(null, 1);
        b = new MockIterableMap<>(singletonMap((String)null, 2));
        assertNotEquals(a, b);
        assertNotEquals(b, a);

        // differ by keys with null values.
        a = singletonMap("b", null);
        b = new MockIterableMap<>(singletonMap("a", (Integer)null));
        assertNotEquals(a, b);
        assertNotEquals(b, a);
    }

    @Test
    public void test_map_containing_self() {
        Map<Object, Object> expected = new LinkedHashMap<>();
        expected.put(expected, expected);

        Map<Object, Object> actual = new MockIterableMap<>();
        actual.put(actual, actual);

        assertEquals(expected.toString(), actual.toString());
    }

    // =================================================================================================================
    // Map mutation tests

    @Test
    public void test_map_put() {
        Map<String, Integer> expected = asMap("a", 1, "b", 2, "c", 3);
        Map<String, Integer> actual = new MockIterableMap<>(expected);

        // put new
        put(expected, actual, "d", 4);
        compare_maps(expected, actual);

        // replace value
        put(expected, actual, "a", -1);
        compare_maps(expected, actual);

        // put null key
        put(expected, actual, null, 10);
        compare_maps(expected, actual);

        // replace
        put(expected, actual, null, 11);
        compare_maps(expected, actual);

        // put null value
        put(expected, actual, "e", null);
        compare_maps(expected, actual);

        // replace
        put(expected, actual, "e", 5);
        compare_maps(expected, actual);
    }

    @Test
    public void test_map_put_null_entry() {
        Map<String, Integer> expected = asMap("a", 1, "b", 2, "c", 3);
        Map<String, Integer> actual = new MockIterableMap<>(expected);

        // put null entry
        put(expected, actual, null, null);
        compare_maps(expected, actual);
    }

    @Test
    public void test_map_putAll() {
        Map<String, Integer> expected = asMap("a", 1, "b", 2, "c", 3);
        Map<String, Integer> actual = new MockIterableMap<>(expected);

        // put existing and new
        putAll(expected, actual, "a", -1, "d", 4);
        compare_maps(expected, actual);

        // put null keys and values
        putAll(expected, actual, "e", null, null, 10);
        compare_maps(expected, actual);

        // putAll empty
        expected.putAll(Collections.<String, Integer>emptyMap());
        actual.putAll(Collections.<String, Integer>emptyMap());
        compare_maps(expected, actual);
    }

    @Test
    public void test_map_putAll_iterableMap() {
        Map<String, Integer> expected = asMap("a", 1, "b", 2, "c", 3);
        Map<String, Integer> actual = new MockIterableMap<>(expected);

        // invoke putAll with an IterableMap argument
        IterableMap<String, Integer> im = new MockIterableMap<>(singletonMap("x", 0));
        expected.putAll(im);
        actual.putAll(im);
        compare_maps(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void test_map_null_putAll() {
        new MockIterableMap<>().putAll(null);
    }

    @Test
    public void test_map_remove() {
        Map<String, Integer> expected = asMap("a", 1, "b", 2, "c", 3, null, 4);
        Map<String, Integer> actual = new MockIterableMap<>(expected);

        // remove existing
        remove(expected, actual, "c");
        compare_maps(expected, actual);

        // remove non-existing
        remove(expected, actual, "x");
        compare_maps(expected, actual);

        // remove null key
        remove(expected, actual, null);
        compare_maps(expected, actual);
    }

    @Test
    public void test_map_clear() {
        Map<String, Integer> expected = asMap("a", 1, "b", 2, "c", 3);
        Map<String, Integer> actual = new MockIterableMap<>(expected);

        expected.clear();
        actual.clear();
        compare_maps(expected, actual);
    }

    @Test
    public void test_map_iterator_remove() {
        Map<String, Integer> expected = asMap("a", 1, "b", 2, "c", 3, null, 4);
        IterableMap<String, Integer> actual = new MockIterableMap<>(expected);

        // remove existing
        remove_by_map_iterator(expected, actual, "b");
        compare_maps(expected, actual);

        // remove non-existing
        remove_by_map_iterator(expected, actual, "x");
        compare_maps(expected, actual);

        // remove null key
        remove_by_map_iterator(expected, actual, new String[] {null});
        compare_maps(expected, actual);
    }

    @Test
    public void test_map_iterator_remove_multiple() {
        Map<String, Integer> expected = asMap("a", 1, "b", 2, "c", 3);
        IterableMap<String, Integer> actual = new MockIterableMap<>(expected);

        remove_by_map_iterator(expected, actual, "c", "b");
        compare_maps(expected, actual);
    }

    @Test
    public void test_map_iterator_remove_all() {
        Map<String, Integer> expected = asMap("a", 1, "b", 2, "c", 3);
        IterableMap<String, Integer> actual = new MockIterableMap<>(expected);

        remove_by_map_iterator(expected, actual, "c", "a", "b");
        compare_maps(expected, actual);
    }

    @Test
    public void test_map_entry_setValue() {
        IterableMap<String, Integer> map = new MockIterableMap<>(singletonMap("a", 1));
        for (MapIterator<String, Integer> iter = map.iterator(); iter.hasNext();) {
            iter.next();
            iter.entry().setValue(9);
        }
        compare_maps(singletonMap("a", 9), map);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_map_put_throws() {
        @SuppressWarnings("unchecked") IterableMap<Object, Object> map = mock(AbstractIterableMap.class);
        given(map.put(any(), any())).willCallRealMethod();
        map.put(new Object(), new Object());
    }

    // =================================================================================================================
    // KeySet mutation tests

    @Test
    public void test_keySet_remove() {
        Map<String, Integer> expected = asMap("a", 1, "b", 2, "c", 3, null, 4);
        IterableMap<String, Integer> actual = new MockIterableMap<>(expected);

        remove(expected.keySet(), actual.keySet(), "a", "c", null, "d");
        compare_maps(expected, actual);
    }

    @Test
    public void test_keySet_removeAll() {
        Map<String, Integer> expected = asMap("a", 1, "b", 2, "c", 3, null, 4);
        IterableMap<String, Integer> actual = new MockIterableMap<>(expected);

        removeAll(expected.keySet(), actual.keySet(), "a", "c", null, "d");
        compare_maps(expected, actual);

        // noinspection RedundantArrayCreation
        removeAll(expected.keySet(), actual.keySet(), new String[0]);
        compare_maps(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void test_keySet_removeAll_throws() {
        new MockIterableMap<>(singletonMap("a", 1)).keySet().removeAll(null);
    }

    @Test
    public void test_keySet_retainAll() {
        Map<String, Integer> expected = asMap("a", 1, "b", 2, "c", 3, null, 4);
        IterableMap<String, Integer> actual = new MockIterableMap<>(expected);
        retainAll(expected.keySet(), actual.keySet(), "a", "c", null, "d");
        compare_maps(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void test_keySet_retainAll_throws() {
        new MockIterableMap<>(singletonMap("a", 1)).keySet().retainAll(null);
    }

    @Test
    public void test_keySet_clear() {
        Map<String, Integer> expected = asMap("a", 1, "b", 2, "c", 3, null, 4);
        IterableMap<String, Integer> actual = new MockIterableMap<>(expected);
        expected.keySet().clear();
        actual.keySet().clear();
        compare_maps(expected, actual);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_keySet_add_throws() {
        new MockIterableMap<>().keySet().add("a");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_keySet_addAll_throws() {
        new MockIterableMap<>().keySet().addAll(Arrays.asList("a"));
    }

    // =================================================================================================================
    // Values collection mutation tests

    @Test
    public void test_values_remove() {
        Map<String, Integer> expected = asMap("a", 1, "b", 2, "c", 3, "d", null, "x", 1);
        IterableMap<String, Integer> actual = new MockIterableMap<>(expected);

        remove(expected.values(), actual.values(), 1, 3, null, 5);
        compare_maps(expected, actual);
    }

    @Test
    public void test_values_removeAll() {
        Map<String, Integer> expected = asMap("a", 1, "b", 2, "c", 3, "d", null, "x", 1);
        IterableMap<String, Integer> actual = new MockIterableMap<>(expected);

        removeAll(expected.values(), actual.values(), 1, 3, null, 5);
        compare_maps(expected, actual);

        // noinspection RedundantArrayCreation
        removeAll(expected.values(), actual.values(), new Integer[0]);
        compare_maps(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void test_values_removeAll_throws() {
        // noinspection ConstantConditions
        new MockIterableMap<>(singletonMap("a", 1)).values().removeAll(null);
    }

    @Test
    public void test_values_retainAll() {
        Map<String, Integer> expected = asMap("a", 1, "b", 2, "c", 3, "d", null, "x", 1);
        IterableMap<String, Integer> actual = new MockIterableMap<>(expected);
        retainAll(expected.values(), actual.values(), 1, 3, null, 5);
        compare_maps(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void test_values_retainAll_throws() {
        // noinspection ConstantConditions
        new MockIterableMap<>(singletonMap("a", 1)).values().retainAll(null);
    }

    @Test
    public void test_values_clear() {
        Map<String, Integer> expected = asMap("a", 1, "b", 2, "c", 3, "d", null, "x", 1);
        IterableMap<String, Integer> actual = new MockIterableMap<>(expected);
        expected.values().clear();
        actual.values().clear();
        compare_maps(expected, actual);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_values_add_throws() {
        new MockIterableMap<>().values().add(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_values_addAll_throws() {
        new MockIterableMap<>().values().addAll(Arrays.asList(1));
    }

    // =================================================================================================================
    // EntrySet mutation tests

    @Test
    public void test_entrySet_remove() {
        Map<String, Integer> expected = asMap("a", 1, "b", 2, "c", 3, "d", null, null, 5);
        IterableMap<String, Integer> actual = new MockIterableMap<>(expected);

        remove(
            expected.entrySet(),
            actual.entrySet(),
            asEntry("a", 1),
            asEntry("d", (Integer)null),
            asEntry((String)null, 5),
            asEntry("c", 4),
            asEntry("x", 2),
            asEntry("z", 9));
        compare_maps(expected, actual);
    }

    @Test
    public void test_entrySet_removeAll() {
        Map<String, Integer> expected = asMap("a", 1, "b", 2, "c", 3, "d", null, null, 5);
        IterableMap<String, Integer> actual = new MockIterableMap<>(expected);

        removeAll(
            expected.entrySet(),
            actual.entrySet(),
            asEntry("a", 1),
            asEntry("d", (Integer)null),
            asEntry((String)null, 5),
            asEntry("c", 4),
            asEntry("x", 2),
            asEntry("z", 9));
        compare_maps(expected, actual);

        removeAll(expected.entrySet(), actual.entrySet());
        compare_maps(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void test_entrySet_removeAll_throws() {
        // noinspection ConstantConditions
        new MockIterableMap<>(singletonMap("a", 1)).entrySet().removeAll(null);
    }

    @Test
    public void test_entrySet_retainAll() {
        Map<String, Integer> expected = asMap("a", 1, "b", 2, "c", 3, "d", null, null, 5);
        IterableMap<String, Integer> actual = new MockIterableMap<>(expected);
        retainAll(
            expected.entrySet(),
            actual.entrySet(),
            asEntry("a", 1),
            asEntry("d", (Integer)null),
            asEntry((String)null, 5),
            asEntry("c", 4),
            asEntry("x", 2),
            asEntry("z", 9));
        compare_maps(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void test_entrySet_retainAll_throws() {
        // noinspection ConstantConditions
        new MockIterableMap<>(singletonMap("a", 1)).entrySet().retainAll(null);
    }

    @Test
    public void test_entrySet_clear() {
        Map<String, Integer> expected = asMap("a", 1, "b", 2, "c", 3, "d", null, null, 5);
        IterableMap<String, Integer> actual = new MockIterableMap<>(expected);
        expected.entrySet().clear();
        actual.entrySet().clear();
        compare_maps(expected, actual);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_entrySet_add_throws() {
        new MockIterableMap<String, Integer>().entrySet().add(asEntry("a", 1));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_entrySet_addAll_throws() {
        new MockIterableMap<String, Integer>().entrySet().addAll(Arrays.asList(asEntry("a", 1)));
    }
}
