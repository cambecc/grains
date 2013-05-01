package net.nullschool.collect.basic;

import net.nullschool.collect.ConstSortedMap;
import org.junit.Test;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;

import static java.lang.String.CASE_INSENSITIVE_ORDER;
import static java.util.Collections.reverseOrder;
import static net.nullschool.collect.CollectionTestingTools.*;
import static net.nullschool.collect.basic.BasicConstSortedMap.*;
import static org.junit.Assert.*;


/**
 * 2013-05-01<p/>
 *
 * @author Cameron Beccario
 */
public class BasicConstSortedMapTest {

    private static void compare(SortedMap<?, ?> expected, SortedMap<?, ?> actual) {
        assertEquals(expected, actual);
        assertArrayEquals(expected.keySet().toArray(), actual.keySet().toArray());
    }

    @Test
    public void test_emptySortedMap() {
        assertSame(BasicSortedMap0.instance(null), emptySortedMap(null));

        Comparator<Object> reverse = reverseOrder();
        assertSame(BasicSortedSet0.instance(reverse).comparator(), emptySortedMap(reverse).comparator());
    }

    @Test
    public void test_construction_permutations() {
        for (int a = 0; a < 6; a++) {
            compare(newSortedMap(null, a, a+1), sortedMapOf(null, a, a+1));
            for (int b = 0; b < 6; b++) {
                compare(newSortedMap(null, a, a, b, b), sortedMapOf(null, a, a, b, b));
                for (int c = 0; c < 6; c++) {
                    compare(newSortedMap(null, a, a, b, b, c, c), sortedMapOf(null, a, a, b, b, c, c));
                    for (int d = 0; d < 6; d++) {
                        compare(
                            newSortedMap(null, a, a, b, b, c, c, d, d),
                            sortedMapOf(null, a, a, b, b, c, c, d, d));
                        for (int e = 0; e < 6; e++) {
                            compare(
                                newSortedMap(null, a, a, b, b, c, c, d, d, e, e),
                                sortedMapOf(null, a, a, b, b, c, c, d, d, e, e));
                            for (int f = 0; f < 6; f++) {
                                Integer[] ary = new Integer[] {a, b, c, d, e, f};
                                SortedMap<Integer, Integer> expected =
                                    newSortedMap(null, a, a, b, b, c, c, d, d, e, e, f, f);
                                Map<Integer, Integer> plainMap =
                                    newMap(a, a, b, b, c, c, d, d, e, e, f, f);

                                // asMap(comparator, array, array)
                                compare(expected, asSortedMap(null, ary, ary));
                                // asMap(sortedMap)
                                compare(expected, asSortedMap(expected));
                                // asMap(comparator, map)
                                compare(expected, asSortedMap(null, plainMap));
                            }
                        }
                    }
                }
            }
        }
    }

    @Test
    public void test_construction_permutations_reverse() {
        Comparator<Object> reverse = reverseOrder();
        for (int a = 0; a < 6; a++) {
            compare(newSortedMap(reverse, a, a+1), sortedMapOf(reverse, a, a+1));
            for (int b = 0; b < 6; b++) {
                compare(newSortedMap(reverse, a, a, b, b), sortedMapOf(reverse, a, a, b, b));
                for (int c = 0; c < 6; c++) {
                    compare(newSortedMap(reverse, a, a, b, b, c, c), sortedMapOf(reverse, a, a, b, b, c, c));
                    for (int d = 0; d < 6; d++) {
                        compare(
                            newSortedMap(reverse, a, a, b, b, c, c, d, d),
                            sortedMapOf(reverse, a, a, b, b, c, c, d, d));
                        for (int e = 0; e < 6; e++) {
                            compare(
                                newSortedMap(reverse, a, a, b, b, c, c, d, d, e, e),
                                sortedMapOf(reverse, a, a, b, b, c, c, d, d, e, e));
                            for (int f = 0; f < 6; f++) {
                                Integer[] ary = new Integer[] {a, b, c, d, e, f};
                                SortedMap<Integer, Integer> expected =
                                    newSortedMap(reverse, a, a, b, b, c, c, d, d, e, e, f, f);
                                Map<Integer, Integer> plainMap =
                                    newMap(a, a, b, b, c, c, d, d, e, e, f, f);

                                // asMap(comparator, array, array)
                                compare(expected, asSortedMap(reverse, ary, ary));
                                // asMap(sortedMap)
                                compare(expected, asSortedMap(expected));
                                // asMap(comparator, map)
                                compare(expected, asSortedMap(reverse, plainMap));
                            }
                        }
                    }
                }
            }
        }
    }

    @Test
    public void test_sortedMapOf() {
        compare_sorted_maps(newSortedMap(null, "a", 1), sortedMapOf(null, "a", 1));
        compare_sorted_maps(newSortedMap(null, "a", 1, "b", 2), sortedMapOf(null, "a", 1, "b", 2));
        compare_sorted_maps(newSortedMap(null, "a", 1, "b", 2, "c", 3), sortedMapOf(null, "a", 1, "b", 2, "c", 3));
        compare_sorted_maps(
            newSortedMap(null, "a", 1, "b", 2, "c", 3, "d", 4),
            sortedMapOf(null, "a", 1, "b", 2, "c", 3, "d", 4));
        compare_sorted_maps(
            newSortedMap(null, "a", 1, "b", 2, "c", 3, "d", 4, "e", 5),
            sortedMapOf(null, "a", 1, "b", 2, "c", 3, "d", 4, "e", 5));

        Comparator<Object> reverse = new NullSafeReverseComparator<>();

        compare_sorted_maps(newSortedMap(reverse, "a", 1), sortedMapOf(reverse, "a", 1));
        compare_sorted_maps(newSortedMap(reverse, "a", 1, "b", 2), sortedMapOf(reverse, "a", 1, "b", 2));
        compare_sorted_maps(
            newSortedMap(reverse, "a", 1, "b", 2, "c", 3),
            sortedMapOf(reverse, "a", 1, "b", 2, "c", 3));
        compare_sorted_maps(
            newSortedMap(reverse, "a", 1, "b", 2, "c", 3, "d", 4),
            sortedMapOf(reverse, "a", 1, "b", 2, "c", 3, "d", 4));
        compare_sorted_maps(
            newSortedMap(reverse, "a", 1, "b", 2, "c", 3, "d", 4, "e", 5),
            sortedMapOf(reverse, "a", 1, "b", 2, "c", 3, "d", 4, "e", 5));

        compare_sorted_maps(newSortedMap(reverse, null, null), sortedMapOf(reverse, null, null));
        compare_sorted_maps(
            newSortedMap(reverse, null, null, null, null),
            sortedMapOf(reverse, null, null, null, null));
        compare_sorted_maps(
            newSortedMap(reverse, null, null, null, null, null, null),
            sortedMapOf(reverse, null, null, null, null, null, null));
        compare_sorted_maps(
            newSortedMap(reverse, null, null, null, null, null, null, null, null),
            sortedMapOf(reverse, null, null, null, null, null, null, null, null));
        compare_sorted_maps(
            newSortedMap(reverse, null, null, null, null, null, null, null, null, null, null),
            sortedMapOf(reverse, null, null, null, null, null, null, null, null, null, null));
    }

    @Test
    public void test_sortedMapOf_types() {
        assertEquals(BasicSortedMap1.class, sortedMapOf(null, "a", 1).getClass());
        assertEquals(BasicSortedMapN.class, sortedMapOf(null, "a", 1, "b", 2).getClass());
        assertEquals(BasicSortedMapN.class, sortedMapOf(null, "a", 1, "b", 2, "c", 3).getClass());
        assertEquals(BasicSortedMapN.class, sortedMapOf(null, "a", 1, "b", 2, "c", 3, "d", 4).getClass());
        assertEquals(BasicSortedMapN.class, sortedMapOf(null, "a", 1, "b", 2, "c", 3, "d", 4, "e", 5).getClass());
    }

    @Test
    public void test_asSortedMap_array() {
        Integer[] a;
        assertSame(emptySortedMap(null), asSortedMap(null, a = new Integer[] {}, a));
        compare_sorted_maps(newSortedMap(null, 1, 1), asSortedMap(null, a = new Integer[] {1}, a));
        compare_sorted_maps(newSortedMap(null, 1, 1, 2, 2), asSortedMap(null, a = new Integer[] {1, 2}, a));
        compare_sorted_maps(newSortedMap(null, 1, 1, 2, 2, 3, 3), asSortedMap(null, a = new Integer[] {1, 2, 3}, a));
        compare_sorted_maps(
            newSortedMap(null, 1, 1, 2, 2, 3, 3, 4, 4),
            asSortedMap(null, a = new Integer[] {1, 2, 3, 4}, a));
        compare_sorted_maps(
            newSortedMap(null, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5),
            asSortedMap(null, a = new Integer[] {1, 2, 3, 4, 5}, a));
        compare_sorted_maps(
            newSortedMap(null, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6),
            asSortedMap(null, a = new Integer[] {1, 2, 3, 4, 5, 6}, a));

        compare_sorted_maps(
            newSortedMap(null, 1, 1, 2, 2),
            asSortedMap(null, new Integer[] {1, 2}, new Integer[] {1, 2, 3}));
        compare_sorted_maps(
            newSortedMap(null, 1, 1, 2, 2),
            asSortedMap(null, new Integer[] {1, 2, 3}, new Integer[] {1, 2}));

        Comparator<Object> reverse = reverseOrder();

        compare_sorted_maps(emptySortedMap(reverse), asSortedMap(reverse, a = new Integer[] {}, a));
        compare_sorted_maps(newSortedMap(reverse, 1, 1), asSortedMap(reverse, a = new Integer[] {1}, a));
        compare_sorted_maps(newSortedMap(reverse, 1, 1, 2, 2), asSortedMap(reverse, a = new Integer[] {1, 2}, a));
        compare_sorted_maps(
            newSortedMap(reverse, 1, 1, 2, 2, 3, 3),
            asSortedMap(reverse, a = new Integer[] {1, 2, 3}, a));
        compare_sorted_maps(
            newSortedMap(reverse, 1, 1, 2, 2, 3, 3, 4, 4),
            asSortedMap(reverse, a = new Integer[] {1, 2, 3, 4}, a));
        compare_sorted_maps(
            newSortedMap(reverse, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5),
            asSortedMap(reverse, a = new Integer[] {1, 2, 3, 4, 5}, a));
        compare_sorted_maps(
            newSortedMap(reverse, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6),
            asSortedMap(reverse, a = new Integer[] {1, 2, 3, 4, 5, 6}, a));

        compare_sorted_maps(
            newSortedMap(reverse, 1, 1, 2, 2),
            asSortedMap(reverse, new Integer[] {1, 2}, new Integer[] {1, 2, 3}));
        compare_sorted_maps(
            newSortedMap(reverse, 1, 1, 2, 2),
            asSortedMap(reverse, new Integer[] {1, 2, 3}, new Integer[] {1, 2}));
    }

    @Test
    public void test_asSortedMap_array_types() {
        Integer[] a;
        assertEquals(BasicSortedMap1.class, asSortedMap(null, a = new Integer[] {1}, a).getClass());
        assertEquals(BasicSortedMapN.class, asSortedMap(null, a = new Integer[] {1, 2}, a).getClass());
        assertEquals(BasicSortedMapN.class, asSortedMap(null, a = new Integer[] {1, 2, 3}, a).getClass());
        assertEquals(BasicSortedMapN.class, asSortedMap(null, a = new Integer[] {1, 2, 3, 4}, a).getClass());
        assertEquals(BasicSortedMapN.class, asSortedMap(null, a = new Integer[] {1, 2, 3, 4, 5}, a).getClass());
        assertEquals(BasicSortedMapN.class, asSortedMap(null, a = new Integer[] {1, 2, 3, 4, 5, 6}, a).getClass());
    }

    @Test(expected = NullPointerException.class)
    public void test_asSortedMap_key_array_null() {
        asSortedMap(null, null, new Integer[0]);
    }

    @Test(expected = NullPointerException.class)
    public void test_asSortedMap_value_array_null() {
        asSortedMap(null, new Integer[0], null);
    }

    @Test
    public void test_asSortedMap_sortedMap() {
        SortedMap<Integer, Integer> expected;
        assertSame(emptySortedMap(null), asSortedMap(newSortedMap(null)));
        compare_sorted_maps(expected = newSortedMap(null, 1, 1), asSortedMap(expected));
        compare_sorted_maps(expected = newSortedMap(null, 1, 1, 2, 2), asSortedMap(expected));
        compare_sorted_maps(expected = newSortedMap(null, 1, 1, 2, 2, 3, 3), asSortedMap(expected));
        compare_sorted_maps(expected = newSortedMap(null, 1, 1, 2, 2, 3, 3, 4, 4), asSortedMap(expected));
        compare_sorted_maps(expected = newSortedMap(null, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5), asSortedMap(expected));
        compare_sorted_maps(expected = newSortedMap(null, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6), asSortedMap(expected));

        Comparator<Object> reverse = reverseOrder();

        compare_sorted_maps(emptySortedMap(reverse), asSortedMap(newSortedMap(reverse)));
        compare_sorted_maps(expected = newSortedMap(reverse, 1, 1), asSortedMap(expected));
        compare_sorted_maps(expected = newSortedMap(reverse, 1, 1, 2, 2), asSortedMap(expected));
        compare_sorted_maps(expected = newSortedMap(reverse, 1, 1, 2, 2, 3, 3), asSortedMap(expected));
        compare_sorted_maps(expected = newSortedMap(reverse, 1, 1, 2, 2, 3, 3, 4, 4), asSortedMap(expected));
        compare_sorted_maps(expected = newSortedMap(reverse, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5), asSortedMap(expected));
        compare_sorted_maps(expected = newSortedMap(reverse, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6), asSortedMap(expected));

        // ConstSortedMap should just be returned as-is.
        ConstSortedMap<Integer, Integer> map;
        map = sortedMapOf(null, 1, 1);
        assertSame(map, asSortedMap(map));
        map = sortedMapOf(null, 1, 1, 2, 2);
        assertSame(map, asSortedMap(map));
        map = sortedMapOf(null, 1, 1, 2, 2, 3, 3);
        assertSame(map, asSortedMap(map));
        map = asSortedMap(null, new Integer[] {1, 2, 3, 4, 5, 6, 7, 8}, new Integer[] {1, 2, 3, 4, 5, 6, 7, 8});
        assertSame(map, asSortedMap(map));
    }

    @Test(expected = NullPointerException.class)
    public void test_asSortedMap_sortedMap_null() {
        asSortedMap(null);
    }

    @Test
    public void test_asSortedMap_comparator_map() {
        assertSame(emptySortedMap(null), asSortedMap(null, newMap()));
        compare_sorted_maps(newSortedMap(null, 1, 1), asSortedMap(null, newMap(1, 1)));
        compare_sorted_maps(newSortedMap(null, 1, 1, 2, 2), asSortedMap(null, newMap(1, 1, 2, 2)));
        compare_sorted_maps(newSortedMap(null, 1, 1, 2, 2, 3, 3), asSortedMap(null, newMap(1, 1, 2, 2, 3, 3)));
        compare_sorted_maps(
            newSortedMap(null, 1, 1, 2, 2, 3, 3, 4, 4),
            asSortedMap(null, newMap(1, 1, 2, 2, 3, 3, 4, 4)));
        compare_sorted_maps(
            newSortedMap(null, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5),
            asSortedMap(null, newMap(1, 1, 2, 2, 3, 3, 4, 4, 5, 5)));
        compare_sorted_maps(
            newSortedMap(null, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6),
            asSortedMap(null, newMap(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6)));

        Comparator<Object> reverse = reverseOrder();

        compare_sorted_maps(emptySortedMap(reverse), asSortedMap(reverse, newMap()));
        compare_sorted_maps(newSortedMap(reverse, 1, 1), asSortedMap(reverse, newMap(1, 1)));
        compare_sorted_maps(newSortedMap(reverse, 1, 1, 2, 2), asSortedMap(reverse, newMap(1, 1, 2, 2)));
        compare_sorted_maps(newSortedMap(reverse, 1, 1, 2, 2, 3, 3), asSortedMap(reverse, newMap(1, 1, 2, 2, 3, 3)));
        compare_sorted_maps(
            newSortedMap(reverse, 1, 1, 2, 2, 3, 3, 4, 4),
            asSortedMap(reverse, newMap(1, 1, 2, 2, 3, 3, 4, 4)));
        compare_sorted_maps(
            newSortedMap(reverse, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5),
            asSortedMap(reverse, newMap(1, 1, 2, 2, 3, 3, 4, 4, 5, 5)));
        compare_sorted_maps(
            newSortedMap(reverse, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6),
            asSortedMap(reverse, newMap(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6)));

        // ConstSortedSet should just be returned as-is, if the comparators are equal.
        ConstSortedMap<Integer, Integer> map;
        map = sortedMapOf(null, 1, 1, 2, 2, 3, 3);
        assertSame(map, asSortedMap(null, map));
        map = sortedMapOf(reverseOrder(), 1, 1, 2, 2, 3, 3);
        assertSame(map, asSortedMap(reverseOrder(), map));
        map = sortedMapOf(new NullSafeReverseComparator<>(), 1, 1, 2, 2, 3, 3);
        assertSame(map, asSortedMap(new NullSafeReverseComparator<>(), map));

        // Calling asSortedMap with a sorted map but different comparator should change the order, and even the size.
        ConstSortedMap<String, Integer> names = sortedMapOf(reverse, "a", 1, "B", 2, "b", 3, "c", 4, "C", 5);
        compare_sorted_maps(
            newSortedMap(reverse, "c", 4, "b", 3, "a", 1, "C", 5, "B", 2),  // before
            names);
        compare_sorted_maps(
            newSortedMap(CASE_INSENSITIVE_ORDER, "a", 1, "b", 2, "c", 5), // after
            asSortedMap(CASE_INSENSITIVE_ORDER, names));
    }

    @Test(expected = NullPointerException.class)
    public void test_asSortedMap_comparator_map_null() {
        asSortedMap(null, null);
    }

    @Test
    public void test_condense() {
        Object[] a;
        assertEquals(BasicSortedMap1.class, condense(null, a = new Object[] {1}, a).getClass());
        assertEquals(BasicSortedMapN.class, condense(null, a = new Object[] {1, 2}, a).getClass());
        assertEquals(BasicSortedMapN.class, condense(null, a = new Object[] {1, 2, 3}, a).getClass());
        assertEquals(BasicSortedMapN.class, condense(null, a = new Object[] {1, 2, 3, 4}, a).getClass());
        assertEquals(BasicSortedMapN.class, condense(null, a = new Object[] {1, 2, 3, 4, 5}, a).getClass());
        assertEquals(BasicSortedMapN.class, condense(null, a = new Object[] {1, 2, 3, 4, 5, 6}, a).getClass());
    }
}
