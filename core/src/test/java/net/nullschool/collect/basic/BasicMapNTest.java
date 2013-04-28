package net.nullschool.collect.basic;

import net.nullschool.collect.ConstMap;
import net.nullschool.collect.ConstSet;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Map;

import static net.nullschool.collect.CollectionTestingTools.*;
import static org.junit.Assert.*;

/**
 * 2013-03-18<p/>
 *
 * @author Cameron Beccario
 */
public class BasicMapNTest {

    @Test
    public void test_comparison() {
        compare_maps(
            asMap("a", 1, "b", 2, "c", 3, "d", 4),
            new BasicMapN<>(new Object[] {"a", "b", "c", "d"}, new Object[] {1, 2, 3, 4}));
    }

    @Test
    public void test_immutable() {
        assert_map_immutable(new BasicMapN<>(new Object[] {"a", "b", "c", "d"}, new Object[] {1, 2, 3, 4}));
    }

    @Test
    public void test_with() {
        ConstMap<Object, Object> map;

        map = new BasicMapN<>(new Object[] {"a", "b", "c", "d"}, new Object[] {1, 2, 3, 4});
        compare_maps(asMap("a", 1, "b", 2, "c", 3, "d", 4, "e", 5), map.with("e", 5));
        compare_maps(asMap("a", 1, "b", 9, "c", 3, "d", 4), map.with("b", 9));
        compare_maps(asMap("a", 1, "b", 2, "c", 3, "d", 4, "e", null), map.with("e", null));
        compare_maps(asMap("a", 1, "b", 2, "c", 3, "d", 4, null, 5), map.with(null, 5));
        assertSame(map, map.with("b", 2));

        map = new BasicMapN<>(new Object[] {"a", "b", "c", null}, new Object[] {1, 2, 3, null});
        assertSame(map, map.with(null, null));
    }

    @Test
    public void test_withAll() {
        ConstMap<Object, Object> map =
            new BasicMapN<>(new Object[] {"a", "b", "c", "d"}, new Object[] {1, 2, 3, 4});
        compare_maps(
            asMap("a", 1, "b", 9, "c", 3, "d", 4, "e", 5, "f", 6),
            map.withAll(asMap("e", 5, "f", 6, "b", 9)));

        compare_maps(map, map.withAll(asMap("a", 1, "b", 2)));
        assertSame(map, map.withAll(asMap()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withAll_throws() {
        new BasicMapN<>(new Object[] {"a", "b", "c", "d"}, new Object[] {1, 2, 3, 4}).withAll(null);
    }

    @Test
    public void test_without() {
        ConstMap<Object, Object> map =
            new BasicMapN<>(new Object[] {"a", "b", "c", "d"}, new Object[] {1, 2, 3, 4});
        compare_maps(asMap("a", 1, "b", 2, "c", 3), map.without("d"));
        assertSame(map, map.without("e"));
        assertSame(map, map.without(null));
    }

    @Test
    public void test_withoutAll() {
        ConstMap<Object, Object> map =
            new BasicMapN<>(new Object[] {"a", "b", "c", "d"}, new Object[] {1, 2, 3, 4});

        compare_maps(asMap("c", 3, "d", 4), map.withoutAll(Arrays.asList("a", "b", "a")));
        compare_maps(asMap("c", 3, "d", 4), map.withoutAll(Arrays.asList("a", "b", "x")));
        compare_maps(map, map.withoutAll(Arrays.asList("x")));
        assertSame(map, map.withoutAll(Arrays.asList()));
        assertSame(BasicMap0.instance(), map.withoutAll(map.keySet()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withoutAll_throws() {
        new BasicMapN<>(new Object[] {"a", "b", "c", "d"}, new Object[] {1, 2, 3, 4}).withoutAll(null);
    }

    @Test
    public void test_serialization() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);

        ConstMap<Object, Object> map =
            new BasicMapN<>(new Object[] {"a", "b", "c", "d"}, new Object[] {"1", "1", "2", "2"});

        out.writeObject(map);
        byte[] data = baos.toByteArray();
        assertEquals(
            "aced05sr0%net.nullschool.collect.basic.MapProxy00000001300xpw40004t01at011t01bq0~03t01ct012t01dq0~06x",
            BasicToolsTest.asReadableString(data));

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));

        ConstMap<?, ?> read = (ConstMap)in.readObject();
        compare_maps(map, read);
        assertSame(map.getClass(), read.getClass());
    }

    @Test
    public void test_get_key_and_value() {
        BasicMapN<Object, Object> map =
            new BasicMapN<>(new Object[] {"1", "2", "3", "4"}, new Object[] {1, 2, 3, 4});
        for (int i = 0; i < map.size(); i++) {
            assertEquals(String.valueOf(i + 1), String.valueOf(map.getKey(i)));
            assertEquals(String.valueOf(i + 1), String.valueOf(map.getValue(i)));
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_out_of_bounds_get_key() {
        new BasicMapN<>(new Object[] {"1", "2", "3", "4"}, new Object[] {1, 2, 3, 4}).getKey(5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_out_of_bounds_get_value() {
        new BasicMapN<>(new Object[] {"1", "2", "3", "4"}, new Object[] {1, 2, 3, 4}).getValue(5);
    }

    @Test
    public void test_non_equality() {
        assertFalse(
            new BasicMapN<>(new Object[] {"a", "b", "c"}, new Object[] {1, 2, 3})
                .equals(asMap("a", "b", "c", 1, 2, 4)));
        assertFalse(
            new BasicMapN<>(new Object[] {"a", "b", "x"}, new Object[] {1, 2, 3})
                .equals(asMap("a", "b", "c", 1, 2, 3)));
        assertFalse(
            asMap("a", "b", "c", 1, 2, 4)
                .equals(new BasicMapN<>(new Object[] {"a", "b", "c"}, new Object[] {1, 2, 3})));
        assertFalse(
            asMap("a", "b", "c", 1, 2, 3)
                .equals(new BasicMapN<>(new Object[] {"a", "b", "x"}, new Object[] {1, 2, 3})));
    }

    @Test
    public void test_entrySet_with() {
        ConstMap<String, Integer> map = new BasicMapN<>(new Object[] {"a", "b"}, new Object[] {1, 2});
        ConstSet<Map.Entry<String, Integer>> entrySet = map.entrySet();

        compare_sets(asSet(asEntry("a", 1), asEntry("b", 2), asEntry("a", 0)), entrySet.with(asEntry("a", 0)));
        compare_sets(asSet(asEntry("a", 1), asEntry("b", 2), asEntry("c", 3)), entrySet.with(asEntry("c", 3)));
        assertSame(entrySet, entrySet.with(asEntry("a", 1)));
    }

    @Test
    public void test_entrySet_withAll() {
        ConstMap<String, Integer> map = new BasicMapN<>(new Object[] {"a", "b"}, new Object[] {1, 2});
        ConstSet<Map.Entry<String, Integer>> entrySet = map.entrySet();

        compare_sets(
            asSet(asEntry("a", 1), asEntry("b", 2), asEntry("c", 3)),
            entrySet.withAll(Arrays.asList(asEntry("a", 1), asEntry("c", 3))));
        compare_sets(entrySet, entrySet.withAll(Arrays.asList(asEntry("a", 1), asEntry("b", 2))));
        assertSame(entrySet, entrySet.withAll(Arrays.<Map.Entry<String, Integer>>asList()));
    }

    @Test
    public void test_entrySet_without() {
        ConstMap<String, Integer> map = new BasicMapN<>(new Object[] {"a", "b"}, new Object[] {1, 2});
        ConstSet<Map.Entry<String, Integer>> entrySet = map.entrySet();

        compare_sets(asSet(asEntry("a", 1)), entrySet.without(asEntry("b", 2)));
        assertSame(entrySet, entrySet.without(asEntry("a", 0)));
    }

    @Test
    public void test_entrySet_withoutAll() {
        ConstMap<String, Integer> map = new BasicMapN<>(new Object[] {"a", "b"}, new Object[] {1, 2});
        ConstSet<Map.Entry<String, Integer>> entrySet = map.entrySet();

        compare_sets(asSet(asEntry("a", 1)), entrySet.withoutAll(Arrays.asList(asEntry("b", 2), asEntry("c", 3))));
        assertSame(entrySet, entrySet.withoutAll(Arrays.asList()));
    }
}
