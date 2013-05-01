package net.nullschool.collect.basic;

import org.junit.Test;

import java.util.Map;

import static net.nullschool.collect.basic.BasicConstMap.*;
import static org.junit.Assert.*;
import static net.nullschool.collect.CollectionTestingTools.*;


/**
 * 2013-05-01<p/>
 *
 * @author Cameron Beccario
 */
public class BasicConstMapTest {

    private static void compare(Map<?, ?> expected, Map<?, ?> actual) {
        assertEquals(expected, actual);
    }

    @Test
    public void test_emptyMap() {
        assertSame(BasicMap0.instance(), emptyMap());
    }

    @Test
    public void test_construction_permutations() {
        for (int a = 0; a < 6; a++) {
            compare(newMap(a, a+1), mapOf(a, a+1));
            for (int b = 0; b < 6; b++) {
                compare(newMap(a, a, b, b), mapOf(a, a, b, b));
                for (int c = 0; c < 6; c++) {
                    compare(newMap(a, a, b, b, c, c), mapOf(a, a, b, b, c, c));
                    for (int d = 0; d < 6; d++) {
                        compare(newMap(a, a, b, b, c, c, d, d), mapOf(a, a, b, b, c, c, d, d));
                        for (int e = 0; e < 6; e++) {
                            compare(newMap(a, a, b, b, c, c, d, d, e, e), mapOf(a, a, b, b, c, c, d, d, e, e));
                            for (int f = 0; f < 6; f++) {
                                Integer[] ary = new Integer[] {a, b, c, d, e, f};
                                Map<Integer, Integer> expected = newMap(a, a, b, b, c, c, d, d, e, e, f, f);

                                // asMap(array, array)
                                compare(expected, asMap(ary, ary));
                                // asMap(map)
                                compare(expected, asMap(expected));
                            }
                        }
                    }
                }
            }
        }
    }

    @Test
    public void test_mapOf() {
        compare_maps(newMap("a", 1), mapOf("a", 1));
        compare_maps(newMap("a", 1, "b", 2), mapOf("a", 1, "b", 2));
        compare_maps(newMap("a", 1, "b", 2, "c", 3), mapOf("a", 1, "b", 2, "c", 3));
        compare_maps(newMap("a", 1, "b", 2, "c", 3, "d", 4), mapOf("a", 1, "b", 2, "c", 3, "d", 4));
        compare_maps(newMap("a", 1, "b", 2, "c", 3, "d", 4, "e", 5), mapOf("a", 1, "b", 2, "c", 3, "d", 4, "e", 5));

        compare_maps(newMap(null, null), mapOf(null, null));
        compare_maps(newMap(null, null, null, null), mapOf(null, null, null, null));
        compare_maps(newMap(null, null, null, null, null, null), mapOf(null, null, null, null, null, null));
        compare_maps(
            newMap(null, null, null, null, null, null, null, null),
            mapOf(null, null, null, null, null, null, null, null));
        compare_maps(
            newMap(null, null, null, null, null, null, null, null, null, null),
            mapOf(null, null, null, null, null, null, null, null, null, null));
    }

    @Test
    public void test_mapOf_types() {
        assertEquals(BasicMap1.class, mapOf("a", 1).getClass());
        assertEquals(BasicMapN.class, mapOf("a", 1, "b", 2).getClass());
        assertEquals(BasicMapN.class, mapOf("a", 1, "b", 2, "c", 3).getClass());
        assertEquals(BasicMapN.class, mapOf("a", 1, "b", 2, "c", 3, "d", 4).getClass());
        assertEquals(BasicMapN.class, mapOf("a", 1, "b", 2, "c", 3, "d", 4, "e", 5).getClass());
    }

    @Test
    public void test_asMap_array() {
        Integer[] a;
        assertSame(emptyMap(), asMap(a = new Integer[] {}, a));
        compare_maps(newMap(1, 1), asMap(a = new Integer[] {1}, a));
        compare_maps(newMap(1, 1, 2, 2), asMap(a = new Integer[] {1, 2}, a));
        compare_maps(newMap(1, 1, 2, 2, 3, 3), asMap(a = new Integer[] {1, 2, 3}, a));
        compare_maps(newMap(1, 1, 2, 2, 3, 3, 4, 4), asMap(a = new Integer[] {1, 2, 3, 4}, a));
        compare_maps(newMap(1, 1, 2, 2, 3, 3, 4, 4, 5, 5), asMap(a = new Integer[] {1, 2, 3, 4, 5}, a));
        compare_maps(newMap(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6), asMap(a = new Integer[] {1, 2, 3, 4, 5, 6}, a));


        compare_maps(newMap(1, 1, 2, 2), asMap(new Integer[] {1, 2}, new Integer[] {1, 2, 3}));
        compare_maps(newMap(1, 1, 2, 2), asMap(new Integer[] {1, 2, 3}, new Integer[] {1, 2}));
    }

    @Test
    public void test_asMap_array_types() {
        Integer[] a;
        assertEquals(BasicMap1.class, asMap(a = new Integer[] {1}, a).getClass());
        assertEquals(BasicMapN.class, asMap(a = new Integer[] {1, 2}, a).getClass());
        assertEquals(BasicMapN.class, asMap(a = new Integer[] {1, 2, 3}, a).getClass());
        assertEquals(BasicMapN.class, asMap(a = new Integer[] {1, 2, 3, 4}, a).getClass());
        assertEquals(BasicMapN.class, asMap(a = new Integer[] {1, 2, 3, 4, 5}, a).getClass());
        assertEquals(BasicMapN.class, asMap(a = new Integer[] {1, 2, 3, 4, 5, 6}, a).getClass());
    }

    @Test(expected = NullPointerException.class)
    public void test_asMap_key_array_null() {
        asMap(null, new Integer[0]);
    }

    @Test(expected = NullPointerException.class)
    public void test_asMap_value_array_null() {
        asMap(new Integer[0], null);
    }

    @Test
    public void test_asMap_map() {
        Map<?, ?> map;
        assertSame(emptyMap(), asMap(newMap()));
        compare_maps(map = newMap(1, 1), asMap(map));
        compare_maps(map = newMap(1, 1, 2, 2), asMap(map));
        compare_maps(map = newMap(1, 1, 2, 2, 3, 3), asMap(map));
        compare_maps(map = newMap(1, 1, 2, 2, 3, 3, 4, 4), asMap(map));
        compare_maps(map = newMap(1, 1, 2, 2, 3, 3, 4, 4, 5, 5), asMap(map));
        compare_maps(map = newMap(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6), asMap(map));

        // ConstMap should just be returned as-is.
        map = mapOf(1, 1);
        assertSame(map, asMap(map));
        map = mapOf(1, 1, 2, 2);
        assertSame(map, asMap(map));
        map = mapOf(1, 1, 2, 2, 3, 3);
        assertSame(map, asMap(map));
        map = asMap(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8}, new Integer[] {1, 2, 3, 4, 5, 6, 7, 8});
        assertSame(map, asMap(map));

        // However, ConstSortedMap should be converted to a regular ConstMap.
        map = BasicConstSortedMap.sortedMapOf(null, 1, 1);
        assertNotSame(map, asMap(map));
    }

    @Test(expected = NullPointerException.class)
    public void test_asMap_map_null() {
        asMap(null);
    }

    @Test
    public void test_condense() {
        Object[] a;
        assertEquals(BasicMap1.class, condense(a = new Object[] {1}, a).getClass());
        assertEquals(BasicMapN.class, condense(a = new Object[] {1, 2}, a).getClass());
        assertEquals(BasicMapN.class, condense(a = new Object[] {1, 2, 3}, a).getClass());
        assertEquals(BasicMapN.class, condense(a = new Object[] {1, 2, 3, 4}, a).getClass());
        assertEquals(BasicMapN.class, condense(a = new Object[] {1, 2, 3, 4, 5}, a).getClass());
        assertEquals(BasicMapN.class, condense(a = new Object[] {1, 2, 3, 4, 5, 6}, a).getClass());
    }
}
