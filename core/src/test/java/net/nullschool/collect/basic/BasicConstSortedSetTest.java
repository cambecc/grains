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

import net.nullschool.collect.ConstSortedSet;
import net.nullschool.reflect.PublicInterfaceRef;
import org.junit.Test;

import java.util.*;

import static net.nullschool.collect.CollectionTestingTools.*;
import static net.nullschool.collect.basic.BasicConstSortedSet.*;
import static org.junit.Assert.*;
import static java.util.Collections.*;
import static java.lang.String.CASE_INSENSITIVE_ORDER;
import static java.util.Arrays.asList;

/**
 * 2013-04-30<p/>
 *
 * @author Cameron Beccario
 */
public class BasicConstSortedSetTest {

    private static void compare_order(Set<?> expected, Set<?> actual) {
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void test_emptySortedSet() {
        assertSame(BasicSortedSet0.instance(null), emptySortedSet(null));

        Comparator<Object> reverse = reverseOrder();
        assertSame(BasicSortedSet0.instance(reverse).comparator(), emptySortedSet(reverse).comparator());
    }

    @Test
    public void test_construction_permutations() {
        for (int a = 0; a < 6; a++) {
            compare_order(newSortedSet(null, a), sortedSetOf(null, a));
            for (int b = 0; b < 6; b++) {
                compare_order(newSortedSet(null, a, b), sortedSetOf(null, a, b));
                for (int c = 0; c < 6; c++) {
                    compare_order(newSortedSet(null, a, b, c), sortedSetOf(null, a, b, c));
                    for (int d = 0; d < 6; d++) {
                        compare_order(newSortedSet(null, a, b, c, d), sortedSetOf(null, a, b, c, d));
                        for (int e = 0; e < 6; e++) {
                            compare_order(newSortedSet(null, a, b, c, d, e), sortedSetOf(null, a, b, c, d, e));
                            for (int f = 0; f < 6; f++) {
                                Integer[] elements = new Integer[] {a, b, c, d, e, f};
                                SortedSet<Integer> expected = newSortedSet(null, elements);
                                Collection<Integer> collection = asList(elements);

                                // sortedSetOf
                                compare_order(expected, sortedSetOf(null, a, b, c, d, e, f));
                                // asSortedSet(comparator, array)
                                compare_order(expected, asSortedSet(null, elements));
                                // asSortedSet(sortedSet)
                                compare_order(expected, asSortedSet(expected));
                                // asSortedSet(comparator, collection)
                                compare_order(expected, asSortedSet(null, collection));
                                // asSortedSet(comparator, iterator)
                                compare_order(expected, asSortedSet(null, collection.iterator()));
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
            compare_order(newSortedSet(reverse, a), sortedSetOf(reverse, a));
            for (int b = 0; b < 6; b++) {
                compare_order(newSortedSet(reverse, a, b), sortedSetOf(reverse, a, b));
                for (int c = 0; c < 6; c++) {
                    compare_order(newSortedSet(reverse, a, b, c), sortedSetOf(reverse, a, b, c));
                    for (int d = 0; d < 6; d++) {
                        compare_order(newSortedSet(reverse, a, b, c, d), sortedSetOf(reverse, a, b, c, d));
                        for (int e = 0; e < 6; e++) {
                            compare_order(newSortedSet(reverse, a, b, c, d, e), sortedSetOf(reverse, a, b, c, d, e));
                            for (int f = 0; f < 6; f++) {
                                Integer[] elements = new Integer[] {a, b, c, d, e, f};
                                SortedSet<Integer> expected = newSortedSet(reverse, elements);
                                Collection<Integer> collection = asList(elements);

                                // sortedSetOf
                                compare_order(expected, sortedSetOf(reverse, a, b, c, d, e, f));
                                // asSortedSet(array)
                                compare_order(expected, asSortedSet(reverse, elements));
                                // asSortedSet(collection)
                                compare_order(expected, asSortedSet(reverse, collection));
                                // asSortedSet(iterator)
                                compare_order(expected, asSortedSet(reverse, collection.iterator()));
                            }
                        }
                    }
                }
            }
        }
    }

    @Test
    public void test_sortedSetOf() {

        compare_sorted_sets(newSortedSet(null, 1), sortedSetOf(null, 1));
        compare_sorted_sets(newSortedSet(null, 1, 2), sortedSetOf(null, 1, 2));
        compare_sorted_sets(newSortedSet(null, 1, 2, 3), sortedSetOf(null, 1, 2, 3));
        compare_sorted_sets(newSortedSet(null, 1, 2, 3, 4), sortedSetOf(null, 1, 2, 3, 4));
        compare_sorted_sets(newSortedSet(null, 1, 2, 3, 4, 5), sortedSetOf(null, 1, 2, 3, 4, 5));
        compare_sorted_sets(newSortedSet(null, 1, 2, 3, 4, 5, 6), sortedSetOf(null, 1, 2, 3, 4, 5, 6));
        compare_sorted_sets(newSortedSet(null, 1, 2, 3, 4, 5, 6, 7), sortedSetOf(null, 1, 2, 3, 4, 5, 6, 7));
        compare_sorted_sets(newSortedSet(null, 1, 2, 3, 4, 5, 6, 7, 8), sortedSetOf(null, 1, 2, 3, 4, 5, 6, 7, 8));

        // noinspection RedundantArrayCreation
        compare_sorted_sets(newSortedSet(null, 1, 2, 3), sortedSetOf(null, 1, 1, 1, 1, 1, 1, new Integer[] {3, 2, 1}));

        Comparator<Object> reverse = new NullSafeReverseComparator<>();

        compare_sorted_sets(newSortedSet(reverse, 1), sortedSetOf(reverse, 1));
        compare_sorted_sets(newSortedSet(reverse, 1, 2), sortedSetOf(reverse, 1, 2));
        compare_sorted_sets(newSortedSet(reverse, 1, 2, 3), sortedSetOf(reverse, 1, 2, 3));
        compare_sorted_sets(newSortedSet(reverse, 1, 2, 3, 4), sortedSetOf(reverse, 1, 2, 3, 4));
        compare_sorted_sets(newSortedSet(reverse, 1, 2, 3, 4, 5), sortedSetOf(reverse, 1, 2, 3, 4, 5));
        compare_sorted_sets(newSortedSet(reverse, 1, 2, 3, 4, 5, 6), sortedSetOf(reverse, 1, 2, 3, 4, 5, 6));
        compare_sorted_sets(newSortedSet(reverse, 1, 2, 3, 4, 5, 6, 7), sortedSetOf(reverse, 1, 2, 3, 4, 5, 6, 7));
        compare_sorted_sets(
            newSortedSet(reverse, 1, 2, 3, 4, 5, 6, 7, 8),
            sortedSetOf(reverse, 1, 2, 3, 4, 5, 6, 7, 8));

        compare_sorted_sets(newSortedSet(reverse, (Object)null), sortedSetOf(reverse, null));
        compare_sorted_sets(newSortedSet(reverse, (Object)null), sortedSetOf(reverse, null, null));
        compare_sorted_sets(newSortedSet(reverse, (Object)null), sortedSetOf(reverse, null, null, null));
        compare_sorted_sets(newSortedSet(reverse, (Object)null), sortedSetOf(reverse, null, null, null, null));
        compare_sorted_sets(newSortedSet(reverse, (Object)null), sortedSetOf(reverse, null, null, null, null, null));
        compare_sorted_sets(
            newSortedSet(reverse, (Object)null),
            sortedSetOf(reverse, null, null, null, null, null, null));
        compare_sorted_sets(
            newSortedSet(reverse, (Object)null),
            sortedSetOf(reverse, null, null, null, null, null, null, (Object)null));
        compare_sorted_sets(
            newSortedSet(reverse, (Object)null),
            sortedSetOf(reverse, null, null, null, null, null, null, null, null));
    }

    @Test
    public void test_sortedSetOf_types() {
        assertEquals(BasicSortedSet1.class, sortedSetOf(null, 1).getClass());
        assertEquals(BasicSortedSetN.class, sortedSetOf(null, 1, 2).getClass());
        assertEquals(BasicSortedSetN.class, sortedSetOf(null, 1, 2, 3).getClass());
        assertEquals(BasicSortedSetN.class, sortedSetOf(null, 1, 2, 3, 4).getClass());
        assertEquals(BasicSortedSetN.class, sortedSetOf(null, 1, 2, 3, 4, 5).getClass());
        assertEquals(BasicSortedSetN.class, sortedSetOf(null, 1, 2, 3, 4, 5, 6).getClass());
        assertEquals(BasicSortedSetN.class, sortedSetOf(null, 1, 2, 3, 4, 5, 6, 7).getClass());
        assertEquals(BasicSortedSetN.class, sortedSetOf(null, 1, 2, 3, 4, 5, 6, 7, 8).getClass());
    }

    @Test
    public void test_asSortedSet_array() {
        assertSame(emptySortedSet(null), asSortedSet(null, new Integer[] {}));
        compare_sorted_sets(newSortedSet(null, 1), asSortedSet(null, new Integer[] {1}));
        compare_sorted_sets(newSortedSet(null, 1, 2), asSortedSet(null, new Integer[] {1, 2}));
        compare_sorted_sets(newSortedSet(null, 1, 2, 3), asSortedSet(null, new Integer[] {1, 2, 3}));
        compare_sorted_sets(newSortedSet(null, 1, 2, 3, 4), asSortedSet(null, new Integer[] {1, 2, 3, 4}));
        compare_sorted_sets(newSortedSet(null, 1, 2, 3, 4, 5), asSortedSet(null, new Integer[] {1, 2, 3, 4, 5}));
        compare_sorted_sets(newSortedSet(null, 1, 2, 3, 4, 5, 6), asSortedSet(null, new Integer[] {1, 2, 3, 4, 5, 6}));

        Comparator<Object> reverse = reverseOrder();

        compare_sorted_sets(emptySortedSet(reverse), asSortedSet(reverse, new Integer[] {}));
        compare_sorted_sets(newSortedSet(reverse, 1), asSortedSet(reverse, new Integer[] {1}));
        compare_sorted_sets(newSortedSet(reverse, 1, 2), asSortedSet(reverse, new Integer[] {1, 2}));
        compare_sorted_sets(newSortedSet(reverse, 1, 2, 3), asSortedSet(reverse, new Integer[] {1, 2, 3}));
        compare_sorted_sets(newSortedSet(reverse, 1, 2, 3, 4), asSortedSet(reverse, new Integer[] {1, 2, 3, 4}));
        compare_sorted_sets(newSortedSet(reverse, 1, 2, 3, 4, 5), asSortedSet(reverse, new Integer[] {1, 2, 3, 4, 5}));
        compare_sorted_sets(
            newSortedSet(reverse, 1, 2, 3, 4, 5, 6),
            asSortedSet(reverse, new Integer[] {1, 2, 3, 4, 5, 6}));
    }

    @Test
    public void test_asSortedSet_array_types() {
        assertEquals(BasicSortedSet1.class, asSortedSet(null, new Integer[] {1}).getClass());
        assertEquals(BasicSortedSetN.class, asSortedSet(null, new Integer[] {1, 2}).getClass());
        assertEquals(BasicSortedSetN.class, asSortedSet(null, new Integer[] {1, 2, 3}).getClass());
        assertEquals(BasicSortedSetN.class, asSortedSet(null, new Integer[] {1, 2, 3, 4}).getClass());
        assertEquals(BasicSortedSetN.class, asSortedSet(null, new Integer[] {1, 2, 3, 4, 5}).getClass());
        assertEquals(BasicSortedSetN.class, asSortedSet(null, new Integer[] {1, 2, 3, 4, 5, 6}).getClass());
        assertEquals(BasicSortedSetN.class, asSortedSet(null, new Integer[] {1, 2, 3, 4, 5, 6, 7}).getClass());
        assertEquals(BasicSortedSetN.class, asSortedSet(null, new Integer[] {1, 2, 3, 4, 5, 6, 7, 8}).getClass());
    }

    @Test(expected = NullPointerException.class)
    public void test_asSortedSet_array_null() {
        asSortedSet(null, (Integer[])null);
    }

    @Test
    public void test_asSortedSet_sortedSet() {
        assertSame(emptySortedSet(null), asSortedSet(newSortedSet(null)));
        compare_sorted_sets(newSortedSet(null, 1), asSortedSet(newSortedSet(null, 1)));
        compare_sorted_sets(newSortedSet(null, 1, 2), asSortedSet(newSortedSet(null, 1, 2)));
        compare_sorted_sets(newSortedSet(null, 1, 2, 3), asSortedSet(newSortedSet(null, 1, 2, 3)));
        compare_sorted_sets(newSortedSet(null, 1, 2, 3, 4), asSortedSet(newSortedSet(null, 1, 2, 3, 4)));
        compare_sorted_sets(newSortedSet(null, 1, 2, 3, 4, 5), asSortedSet(newSortedSet(null, 1, 2, 3, 4, 5)));
        compare_sorted_sets(newSortedSet(null, 1, 2, 3, 4, 5, 6), asSortedSet(newSortedSet(null, 1, 2, 3, 4, 5, 6)));

        Comparator<Object> reverse = reverseOrder();
        compare_sorted_sets(emptySortedSet(reverse), asSortedSet(newSortedSet(reverse)));
        compare_sorted_sets(newSortedSet(reverse, 1), asSortedSet(newSortedSet(reverse, 1)));
        compare_sorted_sets(newSortedSet(reverse, 1, 2), asSortedSet(newSortedSet(reverse, 1, 2)));
        compare_sorted_sets(newSortedSet(reverse, 1, 2, 3), asSortedSet(newSortedSet(reverse, 1, 2, 3)));
        compare_sorted_sets(newSortedSet(reverse, 1, 2, 3, 4), asSortedSet(newSortedSet(reverse, 1, 2, 3, 4)));
        compare_sorted_sets(newSortedSet(reverse, 1, 2, 3, 4, 5), asSortedSet(newSortedSet(reverse, 1, 2, 3, 4, 5)));
        compare_sorted_sets(
            newSortedSet(reverse, 1, 2, 3, 4, 5, 6),
            asSortedSet(newSortedSet(reverse, 1, 2, 3, 4, 5, 6)));

        // ConstSortedSet should just be returned as-is.
        ConstSortedSet<Integer> set;
        set = sortedSetOf(null, 1);
        assertSame(set, asSortedSet(set));
        set = sortedSetOf(null, 1, 2);
        assertSame(set, asSortedSet(set));
        set = sortedSetOf(null, 1, 2, 3);
        assertSame(set, asSortedSet(set));
        set = sortedSetOf(null, 1, 2, 3, 4, 5, 6, 7, 8);
        assertSame(set, asSortedSet(set));
    }

    @Test(expected = NullPointerException.class)
    public void test_asSortedSet_sortedSet_null() {
        asSortedSet(null);
    }

    @Test
    public void test_asSortedSet_collection() {
        assertSame(emptySortedSet(null), asSortedSet(null, asList()));
        compare_sorted_sets(newSortedSet(null, 1), asSortedSet(null, asList(1)));
        compare_sorted_sets(newSortedSet(null, 1, 2), asSortedSet(null, asList(1, 2)));
        compare_sorted_sets(newSortedSet(null, 1, 2, 3), asSortedSet(null, asList(1, 2, 3)));
        compare_sorted_sets(newSortedSet(null, 1, 2, 3, 4), asSortedSet(null, asList(1, 2, 3, 4)));
        compare_sorted_sets(newSortedSet(null, 1, 2, 3, 4, 5), asSortedSet(null, asList(1, 2, 3, 4, 5)));
        compare_sorted_sets(newSortedSet(null, 1, 2, 3, 4, 5, 6), asSortedSet(null, asList(1, 2, 3, 4, 5, 6)));

        Comparator<Object> reverse = reverseOrder();

        compare_sorted_sets(emptySortedSet(reverse), asSortedSet(reverse, asList()));
        compare_sorted_sets(newSortedSet(reverse, 1), asSortedSet(reverse, asList(1)));
        compare_sorted_sets(newSortedSet(reverse, 1, 2), asSortedSet(reverse, asList(1, 2)));
        compare_sorted_sets(newSortedSet(reverse, 1, 2, 3), asSortedSet(reverse, asList(1, 2, 3)));
        compare_sorted_sets(newSortedSet(reverse, 1, 2, 3, 4), asSortedSet(reverse, asList(1, 2, 3, 4)));
        compare_sorted_sets(newSortedSet(reverse, 1, 2, 3, 4, 5), asSortedSet(reverse, asList(1, 2, 3, 4, 5)));
        compare_sorted_sets(
            newSortedSet(reverse, 1, 2, 3, 4, 5, 6),
            asSortedSet(reverse, asList(1, 2, 3, 4, 5, 6)));

        // ConstSortedSet should just be returned as-is, if the comparators are equal.
        ConstSortedSet<Integer> set;
        set = sortedSetOf(null, 1, 2, 3);
        assertSame(set, asSortedSet(null, set));
        set = sortedSetOf(reverseOrder(), 1, 2, 3);
        assertSame(set, asSortedSet(reverseOrder(), set));
        set = sortedSetOf(new NullSafeReverseComparator<>(), 1, 2, 3);
        assertSame(set, asSortedSet(new NullSafeReverseComparator<>(), set));

        // Calling asSortedSet with a sorted set but different comparator should change the order, and even the size.
        ConstSortedSet<String> names = sortedSetOf(reverse, "a", "B", "b", "c", "C");
        compare_sorted_sets(
            newSortedSet(reverse, "c", "b", "a", "C", "B"),  // before
            names);
        compare_sorted_sets(
            newSortedSet(CASE_INSENSITIVE_ORDER, "a", "b", "c"), // after
            asSortedSet(CASE_INSENSITIVE_ORDER, names));
    }

    @Test
    public void test_asSortedSet_collection_types() {
        assertEquals(BasicSortedSet1.class, asSortedSet(null, asList(1)).getClass());
        assertEquals(BasicSortedSetN.class, asSortedSet(null, asList(1, 2)).getClass());
        assertEquals(BasicSortedSetN.class, asSortedSet(null, asList(1, 2, 3)).getClass());
        assertEquals(BasicSortedSetN.class, asSortedSet(null, asList(1, 2, 3, 4)).getClass());
        assertEquals(BasicSortedSetN.class, asSortedSet(null, asList(1, 2, 3, 4, 5)).getClass());
        assertEquals(BasicSortedSetN.class, asSortedSet(null, asList(1, 2, 3, 4, 5, 6)).getClass());
        assertEquals(BasicSortedSetN.class, asSortedSet(null, asList(1, 2, 3, 4, 5, 6, 7)).getClass());
        assertEquals(BasicSortedSetN.class, asSortedSet(null, asList(1, 2, 3, 4, 5, 6, 7, 8)).getClass());
    }

    @Test(expected = NullPointerException.class)
    public void test_asSortedSet_collection_null() {
        asSortedSet(null, (Collection<?>)null);
    }

    @Test
    public void test_asSortedSet_iterator() {
        assertSame(emptySortedSet(null), asSortedSet(null, asList().iterator()));
        compare_sorted_sets(newSortedSet(null, 1), asSortedSet(null, asList(1).iterator()));
        compare_sorted_sets(newSortedSet(null, 1, 2), asSortedSet(null, asList(1, 2).iterator()));
        compare_sorted_sets(newSortedSet(null, 1, 2, 3), asSortedSet(null, asList(1, 2, 3).iterator()));
        compare_sorted_sets(newSortedSet(null, 1, 2, 3, 4), asSortedSet(null, asList(1, 2, 3, 4).iterator()));
        compare_sorted_sets(newSortedSet(null, 1, 2, 3, 4, 5), asSortedSet(null, asList(1, 2, 3, 4, 5).iterator()));
        compare_sorted_sets(
            newSortedSet(null, 1, 2, 3, 4, 5, 6),
            asSortedSet(null, asList(1, 2, 3, 4, 5, 6).iterator()));

        Comparator<Object> reverse = reverseOrder();
        compare_sorted_sets(emptySortedSet(reverse), asSortedSet(reverse, asList().iterator()));
        compare_sorted_sets(newSortedSet(reverse, 1), asSortedSet(reverse, asList(1).iterator()));
        compare_sorted_sets(newSortedSet(reverse, 1, 2), asSortedSet(reverse, asList(1, 2).iterator()));
        compare_sorted_sets(newSortedSet(reverse, 1, 2, 3), asSortedSet(reverse, asList(1, 2, 3).iterator()));
        compare_sorted_sets(newSortedSet(reverse, 1, 2, 3, 4), asSortedSet(reverse, asList(1, 2, 3, 4).iterator()));
        compare_sorted_sets(
            newSortedSet(reverse, 1, 2, 3, 4, 5),
            asSortedSet(reverse, asList(1, 2, 3, 4, 5).iterator()));
        compare_sorted_sets(
            newSortedSet(reverse, 1, 2, 3, 4, 5, 6),
            asSortedSet(reverse, asList(1, 2, 3, 4, 5, 6).iterator()));
    }

    @Test
    public void test_asSortedSet_iterator_types() {
        assertEquals(BasicSortedSet1.class, asSortedSet(null, asList(1).iterator()).getClass());
        assertEquals(BasicSortedSetN.class, asSortedSet(null, asList(1, 2).iterator()).getClass());
        assertEquals(BasicSortedSetN.class, asSortedSet(null, asList(1, 2, 3).iterator()).getClass());
        assertEquals(BasicSortedSetN.class, asSortedSet(null, asList(1, 2, 3, 4).iterator()).getClass());
        assertEquals(BasicSortedSetN.class, asSortedSet(null, asList(1, 2, 3, 4, 5).iterator()).getClass());
        assertEquals(BasicSortedSetN.class, asSortedSet(null, asList(1, 2, 3, 4, 5, 6).iterator()).getClass());
        assertEquals(BasicSortedSetN.class, asSortedSet(null, asList(1, 2, 3, 4, 5, 6, 7).iterator()).getClass());
        assertEquals(BasicSortedSetN.class, asSortedSet(null, asList(1, 2, 3, 4, 5, 6, 7, 8).iterator()).getClass());
    }

    @Test(expected = NullPointerException.class)
    public void test_asSortedSet_iterator_null() {
        asSortedSet(null, (Iterator<?>)null);
    }

    @Test
    public void test_condense() {
        assertSame(emptySortedSet(null), condense(null, new Object[] {}));
        assertEquals(BasicSortedSet1.class, condense(null, new Object[] {1}).getClass());
        assertEquals(BasicSortedSetN.class, condense(null, new Object[] {1, 2}).getClass());
        assertEquals(BasicSortedSetN.class, condense(null, new Object[] {1, 2, 3}).getClass());
        assertEquals(BasicSortedSetN.class, condense(null, new Object[] {1, 2, 3, 4}).getClass());
        assertEquals(BasicSortedSetN.class, condense(null, new Object[] {1, 2, 3, 4, 5}).getClass());
        assertEquals(BasicSortedSetN.class, condense(null, new Object[] {1, 2, 3, 4, 5, 6}).getClass());
        assertEquals(BasicSortedSetN.class, condense(null, new Object[] {1, 2, 3, 4, 5, 6, 7}).getClass());
        assertEquals(BasicSortedSetN.class, condense(null, new Object[] {1, 2, 3, 4, 5, 6, 7, 8}).getClass());
    }

    @Test
    public void test_sortedSetOf_bad_elements() {
        @SuppressWarnings("unchecked") Comparator<Object> comparator = (Comparator)String.CASE_INSENSITIVE_ORDER;

        // Comparator and element types don't match.
        try { sortedSetOf(comparator, 1);             fail(); } catch (ClassCastException ignored) {}
        try { sortedSetOf(comparator, 1, 2);          fail(); } catch (ClassCastException ignored) {}
        try { sortedSetOf(comparator, 1, 2, 3);       fail(); } catch (ClassCastException ignored) {}
        try { sortedSetOf(comparator, 1, 2, 3, 4);    fail(); } catch (ClassCastException ignored) {}
        try { sortedSetOf(comparator, 1, 2, 3, 4, 5); fail(); } catch (ClassCastException ignored) {}

        try { sortedSetOf(comparator, null);                         fail(); } catch (NullPointerException ignored) {}
        try { sortedSetOf(comparator, null, null);                   fail(); } catch (NullPointerException ignored) {}
        try { sortedSetOf(comparator, null, null, null);             fail(); } catch (NullPointerException ignored) {}
        try { sortedSetOf(comparator, null, null, null, null);       fail(); } catch (NullPointerException ignored) {}
        try { sortedSetOf(comparator, null, null, null, null, null); fail(); } catch (NullPointerException ignored) {}

        // With method passed element having mismatched type.
        try { emptySortedSet(comparator).with(1);                     fail(); } catch (ClassCastException ignored) {}
        try { sortedSetOf(comparator, (Object)"a").with(1);           fail(); } catch (ClassCastException ignored) {}
        try { sortedSetOf(comparator, (Object)"a", "b").with(1);      fail(); } catch (ClassCastException ignored) {}
        try { sortedSetOf(comparator, (Object)"a", "b", "c").with(1); fail(); } catch (ClassCastException ignored) {}

        try { emptySortedSet(comparator).with(null);                     fail(); } catch (NullPointerException ignored) {}
        try { sortedSetOf(comparator, (Object)"a").with(null);           fail(); } catch (NullPointerException ignored) {}
        try { sortedSetOf(comparator, (Object)"a", "b").with(null);      fail(); } catch (NullPointerException ignored) {}
        try { sortedSetOf(comparator, (Object)"a", "b", "c").with(null); fail(); } catch (NullPointerException ignored) {}

        // WithAll method passed elements having mismatched type.
        try { emptySortedSet(comparator).withAll(asList(1));                     fail(); } catch (ClassCastException ignored) {}
        try { sortedSetOf(comparator, (Object)"a").withAll(asList(1));           fail(); } catch (ClassCastException ignored) {}
        try { sortedSetOf(comparator, (Object)"a", "b").withAll(asList(1));      fail(); } catch (ClassCastException ignored) {}
        try { sortedSetOf(comparator, (Object)"a", "b", "c").withAll(asList(1)); fail(); } catch (ClassCastException ignored) {}

        Object[] oneNull = new Object[] {null};
        try { emptySortedSet(comparator).withAll(asList(oneNull));                     fail(); } catch (NullPointerException ignored) {}
        try { sortedSetOf(comparator, (Object)"a").withAll(asList(oneNull));           fail(); } catch (NullPointerException ignored) {}
        try { sortedSetOf(comparator, (Object)"a", "b").withAll(asList(oneNull));      fail(); } catch (NullPointerException ignored) {}
        try { sortedSetOf(comparator, (Object)"a", "b", "c").withAll(asList(oneNull)); fail(); } catch (NullPointerException ignored) {}

        // Natural ordering but element types are not Comparable
        Object o = new Object();
        try { sortedSetOf(null, o);          fail(); } catch (ClassCastException ignored) {}
        try { sortedSetOf(null, o, o);       fail(); } catch (ClassCastException ignored) {}
        try { sortedSetOf(null, o, o, o);    fail(); } catch (ClassCastException ignored) {}
        try { sortedSetOf(null, o, o, o, o); fail(); } catch (ClassCastException ignored) {}

        // Natural ordering does not allow nulls.
        try { sortedSetOf(null, null);                   fail(); } catch (NullPointerException ignored) {}
        try { sortedSetOf(null, null, null);             fail(); } catch (NullPointerException ignored) {}
        try { sortedSetOf(null, null, null, null);       fail(); } catch (NullPointerException ignored) {}
        try { sortedSetOf(null, null, null, null, null); fail(); } catch (NullPointerException ignored) {}
    }

    @Test
    public void test_publicInterfaceRef_annotation_present() {
        Collection<Integer> elements = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            assertSame(
                BasicConstSortedSet.class,
                asSortedSet(null, elements).getClass().getAnnotation(PublicInterfaceRef.class).value());
            elements.add(i);
        }
    }
}
