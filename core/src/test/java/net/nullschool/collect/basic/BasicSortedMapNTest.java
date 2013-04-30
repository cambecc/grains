package net.nullschool.collect.basic;

import net.nullschool.collect.ConstSet;
import net.nullschool.collect.ConstSortedMap;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.*;
import static net.nullschool.collect.CollectionTestingTools.*;
import static java.util.Collections.*;

/**
 * 2013-04-29<p/>
 *
 * @author Cameron Beccario
 */
public class BasicSortedMapNTest {

    @Test
    public void test_comparison() {

        compare_sorted_maps(
            asSortedMap(null, "a", 1, "b", 2, "c", 3, "e", 5, "f", 6, "g", 7),
            new BasicSortedMapN<>(
                null,
                new Object[] {"a", "b", "c", "e", "f", "g"},
                new Object[] {1,   2,   3,   5,   6,   7}),
            "+", "b", "d", "f", "h", "a", "g");
        compare_sorted_maps(
            asSortedMap(reverseOrder(), "g", 7, "f", 6, "e", 5, "c", 3, "b", 2, "a", 1),
            new BasicSortedMapN<>(
                reverseOrder(),
                new Object[] {"g", "f", "e", "c", "b", "a"},
                new Object[] {7, 6, 5, 3, 2, 1}),
            "h", "f", "d", "b", "+", "g", "a");
    }

    @Test
    public void test_immutable() {
        assert_sorted_map_immutable(
            new BasicSortedMapN<>(null, new Object[] {"a", "b", "c", "d"}, new Object[] {1, 2, 3, 4}));
    }

    @Test
    public void test_with() {
        ConstSortedMap<Object, Object> map;

        map = new BasicSortedMapN<>(null, new Object[] {"a", "b", "c", "e"}, new Object[] {1, 2, 3, 5});
        compare_sorted_maps(asSortedMap(null, "a", 1, "b", 2, "c", 3, "d", 4, "e", 5), map.with("d", 4));
        compare_sorted_maps(asSortedMap(null, "+", 0, "a", 1, "b", 2, "c", 3, "e", 5), map.with("+", 0));
        compare_sorted_maps(asSortedMap(null, "a", 1, "b", 2, "c", 3, "e", 5, "f", 6), map.with("f", 6));
        compare_sorted_maps(asSortedMap(null, "a", 1, "b", 9, "c", 3, "e", 5), map.with("b", 9));
        assertSame(map, map.with("b", 2));

        map = new BasicSortedMapN<>(reverseOrder(), new Object[] {"e", "c", "b", "a"}, new Object[] {5, 3, 2, 1});
        compare_sorted_maps(asSortedMap(reverseOrder(), "e", 5, "d", 4, "c", 3, "b", 2, "a", 1), map.with("d", 4));
        compare_sorted_maps(asSortedMap(reverseOrder(), "e", 5, "c", 3, "b", 2, "a", 1, "+", 0), map.with("+", 0));
        compare_sorted_maps(asSortedMap(reverseOrder(), "f", 6, "e", 5, "c", 3, "b", 2, "a", 1), map.with("f", 6));
        compare_sorted_maps(asSortedMap(reverseOrder(), "e", 5, "c", 3, "b", 9, "a", 1), map.with("b", 9));
        assertSame(map, map.with("b", 2));
    }

    @Test
    public void test_withAll() {
        ConstSortedMap<Object, Object> map;

        map = new BasicSortedMapN<>(null, new Object[] {"a", "b", "c", "e"}, new Object[] {1, 2, 3, 5});
        compare_sorted_maps(
            asSortedMap(null, "+", 0, "a", 1, "b", 9, "c", 3, "d", 4, "e", 5, "f", 6),
            map.withAll(asMap("+", 0, "f", 6, "b", 9, "d", 4)));
        compare_sorted_maps(map, map.withAll(asMap("a", 1, "b", 2)));
        assertSame(map, map.withAll(asMap()));

        map = new BasicSortedMapN<>(reverseOrder(), new Object[] {"e", "c", "b", "a"}, new Object[] {5, 3, 2, 1});
        compare_sorted_maps(
            asSortedMap(reverseOrder(), "f", 6, "e", 5, "d", 4, "c", 3, "b", 9, "a", 1, "+", 0),
            map.withAll(asMap("+", 0, "f", 6, "b", 9, "d", 4)));
        compare_sorted_maps(map, map.withAll(asMap("a", 1, "b", 2)));
        assertSame(map, map.withAll(asMap()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withAll_throws() {
        new BasicSortedMapN<>(null, new Object[] {"a", "b", "c", "d"}, new Object[] {1, 2, 3, 4}).withAll(null);
    }

    @Test
    public void test_without() {
        ConstSortedMap<Object, Object> map;

        map = new BasicSortedMapN<>(null, new Object[] {"a", "b", "c", "e"}, new Object[] {1, 2, 3, 5});
        compare_sorted_maps(asSortedMap(null, "b", 2, "c", 3, "e", 5), map.without("a"));
        compare_sorted_maps(asSortedMap(null, "a", 1, "b", 2, "e", 5), map.without("c"));
        compare_sorted_maps(asSortedMap(null, "a", 1, "b", 2, "c", 3), map.without("e"));
        assertSame(map, map.without("+"));
        assertSame(map, map.without("d"));
        assertSame(map, map.without("f"));

        map = new BasicSortedMapN<>(reverseOrder(), new Object[] {"e", "c", "b", "a"}, new Object[] {5, 3, 2, 1});
        compare_sorted_maps(asSortedMap(reverseOrder(), "e", 5, "c", 3, "b", 2), map.without("a"));
        compare_sorted_maps(asSortedMap(reverseOrder(), "e", 5, "b", 2, "a", 1), map.without("c"));
        compare_sorted_maps(asSortedMap(reverseOrder(), "c", 3, "b", 2, "a", 1), map.without("e"));
        assertSame(map, map.without("+"));
        assertSame(map, map.without("d"));
        assertSame(map, map.without("f"));
    }

    @Test
    public void test_withoutAll() {
        ConstSortedMap<Object, Object> map;

        map = new BasicSortedMapN<>(null, new Object[] {"a", "b", "c", "e"}, new Object[] {1, 2, 3, 5});
        compare_sorted_maps(asSortedMap(null, "b", 2), map.withoutAll(Arrays.asList("a", "e", "c")));
        compare_sorted_maps(asSortedMap(null, "b", 2), map.withoutAll(Arrays.asList("a", "+", "f", "e", "c", "a")));
        compare_sorted_maps(map, map.withoutAll(Arrays.asList("+")));
        assertSame(map, map.withoutAll(Arrays.asList()));
        assertEquals(BasicSortedMap0.<Object, Object>instance(null), map.withoutAll(map.keySet()));

        map = new BasicSortedMapN<>(reverseOrder(), new Object[] {"e", "c", "b", "a"}, new Object[] {5, 3, 2, 1});
        compare_sorted_maps(asSortedMap(reverseOrder(), "b", 2), map.withoutAll(Arrays.asList("a", "e", "c")));
        compare_sorted_maps(asSortedMap(reverseOrder(), "b", 2), map.withoutAll(Arrays.asList("a", "+", "f", "e", "c", "a")));
        compare_sorted_maps(map, map.withoutAll(Arrays.asList("+")));
        assertSame(map, map.withoutAll(Arrays.asList()));
        assertEquals(BasicSortedMap0.<Object, Object>instance(reverseOrder()), map.withoutAll(map.keySet()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withoutAll_throws() {
        new BasicSortedMapN<>(null, new Object[] {"a", "b", "c", "d"}, new Object[] {1, 2, 3, 4}).withoutAll(null);
    }

    @Test
    public void test_serialization() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);

        ConstSortedMap<Object, Object> map =
            new BasicSortedMapN<>(null, new Object[] {"a", "b"}, new Object[] {"1", "1"});

        out.writeObject(map);
        byte[] data = baos.toByteArray();
        assertEquals(
            "aced05sr0+net.nullschool.collect.basic.SortedMapProxy00000001300xppw40002t01at011t01bq0~03x",
            BasicToolsTest.asReadableString(data));

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));

        ConstSortedMap<?, ?> read = (ConstSortedMap)in.readObject();
        compare_sorted_maps(map, read);
        assertSame(map.getClass(), read.getClass());
    }

    @Test
    public void test_serialization_with_comparator() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);

        ConstSortedMap<Object, Object> map =
            new BasicSortedMapN<>(reverseOrder(), new Object[] {"b", "a"}, new Object[] {"1", "1"});

        out.writeObject(map);
        byte[] data = baos.toByteArray();
        assertEquals(
            "aced05sr0+net.nullschool.collect.basic.SortedMapProxy00000001300xpsr0'" +
                "java.util.Collections$ReverseComparatord48af0SNJd0200xpw40002t01bt011t01aq0~05x",
            BasicToolsTest.asReadableString(data));

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));

        ConstSortedMap<?, ?> read = (ConstSortedMap)in.readObject();
        compare_sorted_maps(map, read);
        assertSame(map.getClass(), read.getClass());
    }

    @Test
    public void test_get_key_and_value() {
        BasicSortedMapN<Object, Object> map =
            new BasicSortedMapN<>(null, new Object[] {"1", "2", "3", "4"}, new Object[] {1, 2, 3, 4});
        for (int i = 0; i < map.size(); i++) {
            assertEquals(String.valueOf(i + 1), String.valueOf(map.getKey(i)));
            assertEquals(String.valueOf(i + 1), String.valueOf(map.getValue(i)));
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_out_of_bounds_get_key() {
        new BasicSortedMapN<>(null, new Object[] {"1", "2", "3", "4"}, new Object[] {1, 2, 3, 4}).getKey(5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_out_of_bounds_get_value() {
        new BasicSortedMapN<>(null, new Object[] {"1", "2", "3", "4"}, new Object[] {1, 2, 3, 4}).getValue(5);
    }

    @Test
    public void test_non_equality() {
        assertFalse(
            new BasicSortedMapN<>(null, new Object[] {"a", "b", "c"}, new Object[] {1, 2, 3})
                .equals(asSortedMap(null, "a", 1, "b", 2, "c", 4)));
        assertFalse(
            new BasicSortedMapN<>(null, new Object[] {"a", "b", "x"}, new Object[] {1, 2, 3})
                .equals(asSortedMap(null, "a", 1, "b", 2, "c", 3)));
        assertFalse(
            asSortedMap(null, "a", 1, "b", 2, "c", 4)
                .equals(new BasicSortedMapN<>(null, new Object[] {"a", "b", "c"}, new Object[] {1, 2, 3})));
        assertFalse(
            asSortedMap(null, "a", 1, "b", 2, "c", 3)
                .equals(new BasicSortedMapN<>(null, new Object[] {"a", "b", "x"}, new Object[] {1, 2, 3})));
    }

    @Test
    public void test_different_comparators_still_equal() {
        assertTrue(
            new BasicSortedMapN<>(null, new Object[] {"a", "b", "c"}, new Object[] {1, 2, 3})
                .equals(new BasicSortedMapN<>(reverseOrder(), new Object[] {"c", "b", "a"}, new Object[] {3, 2, 1})));
    }

    @Test
    public void test_entrySet_with() {
        ConstSortedMap<String, Integer> map = new BasicSortedMapN<>(null, new Object[] {"a", "b"}, new Object[] {1, 2});
        ConstSet<Map.Entry<String, Integer>> entrySet = map.entrySet();

        compare_sets(asSet(asEntry("a", 1), asEntry("b", 2), asEntry("a", 0)), entrySet.with(asEntry("a", 0)));
        compare_sets(asSet(asEntry("a", 1), asEntry("b", 2), asEntry("c", 3)), entrySet.with(asEntry("c", 3)));
        assertSame(entrySet, entrySet.with(asEntry("a", 1)));
    }

    @Test
    public void test_entrySet_withAll() {
        ConstSortedMap<String, Integer> map = new BasicSortedMapN<>(null, new Object[] {"a", "b"}, new Object[] {1, 2});
        ConstSet<Map.Entry<String, Integer>> entrySet = map.entrySet();

        compare_sets(
            asSet(asEntry("a", 1), asEntry("b", 2), asEntry("c", 3)),
            entrySet.withAll(Arrays.asList(asEntry("a", 1), asEntry("c", 3))));
        compare_sets(entrySet, entrySet.withAll(Arrays.asList(asEntry("a", 1), asEntry("b", 2))));
        assertSame(entrySet, entrySet.withAll(Arrays.<Map.Entry<String, Integer>>asList()));
    }

    @Test
    public void test_entrySet_without() {
        ConstSortedMap<String, Integer> map = new BasicSortedMapN<>(null, new Object[] {"a", "b"}, new Object[] {1, 2});
        ConstSet<Map.Entry<String, Integer>> entrySet = map.entrySet();

        compare_sets(asSet(asEntry("a", 1)), entrySet.without(asEntry("b", 2)));
        assertSame(entrySet, entrySet.without(asEntry("a", 0)));
    }

    @Test
    public void test_entrySet_withoutAll() {
        ConstSortedMap<String, Integer> map = new BasicSortedMapN<>(null, new Object[] {"a", "b"}, new Object[] {1, 2});
        ConstSet<Map.Entry<String, Integer>> entrySet = map.entrySet();

        compare_sets(asSet(asEntry("a", 1)), entrySet.withoutAll(Arrays.asList(asEntry("b", 2), asEntry("c", 3))));
        assertSame(entrySet, entrySet.withoutAll(Arrays.asList()));
    }

}
