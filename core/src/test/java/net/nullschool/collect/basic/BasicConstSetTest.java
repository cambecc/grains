package net.nullschool.collect.basic;

import net.nullschool.collect.ConstSet;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import static net.nullschool.collect.basic.BasicConstSet.*;
import static org.junit.Assert.*;
import static net.nullschool.collect.CollectionTestingTools.*;


/**
 * 2013-04-30<p/>
 *
 * @author Cameron Beccario
 */
public class BasicConstSetTest {

    private static void compare_order(Set<?> expected, Set<?> actual) {
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void test_emptySet() {
        assertSame(BasicSet0.instance(), emptySet());
    }

    @Test
    public void test_construction_permutations() {
        for (int a = 0; a < 6; a++) {
            compare_order(newSet(a), setOf(a));
            for (int b = 0; b < 6; b++) {
                compare_order(newSet(a, b), setOf(a, b));
                for (int c = 0; c < 6; c++) {
                    compare_order(newSet(a, b, c), setOf(a, b, c));
                    for (int d = 0; d < 6; d++) {
                        compare_order(newSet(a, b, c, d), setOf(a, b, c, d));
                        for (int e = 0; e < 6; e++) {
                            compare_order(newSet(a, b, c, d, e), setOf(a, b, c, d, e));
                            for (int f = 0; f < 6; f++) {
                                Integer[] elements = new Integer[] {a, b, c, d, e, f};
                                Set<Integer> expected = newSet(elements);
                                Collection<Integer> collection = Arrays.asList(elements);

                                // setOf
                                compare_order(expected, setOf(a, b, c, d, e, f));
                                // asSet(array)
                                compare_order(expected, asSet(elements));
                                // asSet(collection)
                                compare_order(expected, asSet(collection));
                                // asSet(iterator)
                                compare_order(expected, asSet(collection.iterator()));
                            }
                        }
                    }
                }
            }
        }
    }

    @Test
    public void test_setOf() {
        compare_sets(newSet(1), setOf(1));
        compare_sets(newSet(1, 2), setOf(1, 2));
        compare_sets(newSet(1, 2, 3), setOf(1, 2, 3));
        compare_sets(newSet(1, 2, 3, 4), setOf(1, 2, 3, 4));
        compare_sets(newSet(1, 2, 3, 4, 5), setOf(1, 2, 3, 4, 5));
        compare_sets(newSet(1, 2, 3, 4, 5, 6), setOf(1, 2, 3, 4, 5, 6));
        compare_sets(newSet(1, 2, 3, 4, 5, 6, 7), setOf(1, 2, 3, 4, 5, 6, 7));
        compare_sets(newSet(1, 2, 3, 4, 5, 6, 7, 8), setOf(1, 2, 3, 4, 5, 6, 7, 8));

        // noinspection RedundantArrayCreation
        compare_sets(newSet(1, 2, 3), setOf(1, 1, 1, 1, 1, 1, new Integer[] {1, 2, 3}));

        compare_sets(newSet((Object)null), setOf(null));
        compare_sets(newSet((Object)null), setOf(null, null));
        compare_sets(newSet((Object)null), setOf(null, null, null));
        compare_sets(newSet((Object)null), setOf(null, null, null, null));
        compare_sets(newSet((Object)null), setOf(null, null, null, null, null));
        compare_sets(newSet((Object)null), setOf(null, null, null, null, null, null));
        compare_sets(newSet((Object)null), setOf(null, null, null, null, null, null, (Object)null));
        compare_sets(newSet((Object)null), setOf(null, null, null, null, null, null, null, null));
    }

    @Test
    public void test_setOf_types() {
        assertEquals(BasicSet1.class, setOf(1).getClass());
        assertEquals(BasicSetN.class, setOf(1, 2).getClass());
        assertEquals(BasicSetN.class, setOf(1, 2, 3).getClass());
        assertEquals(BasicSetN.class, setOf(1, 2, 3, 4).getClass());
        assertEquals(BasicSetN.class, setOf(1, 2, 3, 4, 5).getClass());
        assertEquals(BasicSetN.class, setOf(1, 2, 3, 4, 5, 6).getClass());
        assertEquals(BasicSetN.class, setOf(1, 2, 3, 4, 5, 6, 7).getClass());
        assertEquals(BasicSetN.class, setOf(1, 2, 3, 4, 5, 6, 7, 8).getClass());
    }

    @Test
    public void test_asSet_array() {
        assertSame(emptySet(), asSet(new Integer[] {}));
        compare_sets(newSet(1), asSet(new Integer[] {1}));
        compare_sets(newSet(1, 2), asSet(new Integer[] {1, 2}));
        compare_sets(newSet(1, 2, 3), asSet(new Integer[] {1, 2, 3}));
        compare_sets(newSet(1, 2, 3, 4), asSet(new Integer[] {1, 2, 3, 4}));
        compare_sets(newSet(1, 2, 3, 4, 5), asSet(new Integer[] {1, 2, 3, 4, 5}));
        compare_sets(newSet(1, 2, 3, 4, 5, 6), asSet(new Integer[] {1, 2, 3, 4, 5, 6}));
    }

    @Test
    public void test_asSet_array_types() {
        assertEquals(BasicSet1.class, asSet(new Integer[] {1}).getClass());
        assertEquals(BasicSetN.class, asSet(new Integer[] {1, 2}).getClass());
        assertEquals(BasicSetN.class, asSet(new Integer[] {1, 2, 3}).getClass());
        assertEquals(BasicSetN.class, asSet(new Integer[] {1, 2, 3, 4}).getClass());
        assertEquals(BasicSetN.class, asSet(new Integer[] {1, 2, 3, 4, 5}).getClass());
        assertEquals(BasicSetN.class, asSet(new Integer[] {1, 2, 3, 4, 5, 6}).getClass());
        assertEquals(BasicSetN.class, asSet(new Integer[] {1, 2, 3, 4, 5, 6, 7}).getClass());
        assertEquals(BasicSetN.class, asSet(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8}).getClass());
    }

    @Test(expected = NullPointerException.class)
    public void test_asSet_array_null() {
        asSet((Integer[])null);
    }

    @Test
    public void test_asSet_collection() {
        assertSame(emptySet(), asSet(Arrays.asList()));
        compare_sets(newSet(1), asSet(Arrays.asList(1)));
        compare_sets(newSet(1, 2), asSet(Arrays.asList(1, 2)));
        compare_sets(newSet(1, 2, 3), asSet(Arrays.asList(1, 2, 3)));
        compare_sets(newSet(1, 2, 3, 4), asSet(Arrays.asList(1, 2, 3, 4)));
        compare_sets(newSet(1, 2, 3, 4, 5), asSet(Arrays.asList(1, 2, 3, 4, 5)));
        compare_sets(newSet(1, 2, 3, 4, 5, 6), asSet(Arrays.asList(1, 2, 3, 4, 5, 6)));

        // ConstSet should just be returned as-is.
        ConstSet<Integer> set;
        set = setOf(1);
        assertSame(set, asSet(set));
        set = setOf(1, 2);
        assertSame(set, asSet(set));
        set = setOf(1, 2, 3);
        assertSame(set, asSet(set));
        set = setOf(1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertSame(set, asSet(set));

        // ConstSortedSet should be converted to a regular ConstSet.
        set = BasicConstSortedSet.sortedSetOf(null, 1);
        assertNotSame(set, asSet(set));
    }

    @Test
    public void test_asSet_collection_types() {
        assertEquals(BasicSet1.class, asSet(Arrays.asList(1)).getClass());
        assertEquals(BasicSetN.class, asSet(Arrays.asList(1, 2)).getClass());
        assertEquals(BasicSetN.class, asSet(Arrays.asList(1, 2, 3)).getClass());
        assertEquals(BasicSetN.class, asSet(Arrays.asList(1, 2, 3, 4)).getClass());
        assertEquals(BasicSetN.class, asSet(Arrays.asList(1, 2, 3, 4, 5)).getClass());
        assertEquals(BasicSetN.class, asSet(Arrays.asList(1, 2, 3, 4, 5, 6)).getClass());
        assertEquals(BasicSetN.class, asSet(Arrays.asList(1, 2, 3, 4, 5, 6, 7)).getClass());
        assertEquals(BasicSetN.class, asSet(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)).getClass());
    }

    @Test(expected = NullPointerException.class)
    public void test_asSet_collection_null() {
        asSet((Collection<?>)null);
    }

    @Test
    public void test_asSet_iterator() {
        assertSame(emptySet(), asSet(Arrays.asList().iterator()));
        compare_sets(newSet(1), asSet(Arrays.asList(1).iterator()));
        compare_sets(newSet(1, 2), asSet(Arrays.asList(1, 2).iterator()));
        compare_sets(newSet(1, 2, 3), asSet(Arrays.asList(1, 2, 3).iterator()));
        compare_sets(newSet(1, 2, 3, 4), asSet(Arrays.asList(1, 2, 3, 4).iterator()));
        compare_sets(newSet(1, 2, 3, 4, 5), asSet(Arrays.asList(1, 2, 3, 4, 5).iterator()));
        compare_sets(newSet(1, 2, 3, 4, 5, 6), asSet(Arrays.asList(1, 2, 3, 4, 5, 6).iterator()));
    }

    @Test
    public void test_asSet_iterator_types() {
        assertEquals(BasicSet1.class, asSet(Arrays.asList(1).iterator()).getClass());
        assertEquals(BasicSetN.class, asSet(Arrays.asList(1, 2).iterator()).getClass());
        assertEquals(BasicSetN.class, asSet(Arrays.asList(1, 2, 3).iterator()).getClass());
        assertEquals(BasicSetN.class, asSet(Arrays.asList(1, 2, 3, 4).iterator()).getClass());
        assertEquals(BasicSetN.class, asSet(Arrays.asList(1, 2, 3, 4, 5).iterator()).getClass());
        assertEquals(BasicSetN.class, asSet(Arrays.asList(1, 2, 3, 4, 5, 6).iterator()).getClass());
        assertEquals(BasicSetN.class, asSet(Arrays.asList(1, 2, 3, 4, 5, 6, 7).iterator()).getClass());
        assertEquals(BasicSetN.class, asSet(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8).iterator()).getClass());
    }

    @Test(expected = NullPointerException.class)
    public void test_asSet_iterator_null() {
        asSet((Iterator<?>)null);
    }

    @Test
    public void test_condense() {
        assertSame(emptySet(), condense(new Object[] {}));
        assertEquals(BasicSet1.class, condense(new Object[] {1}).getClass());
        assertEquals(BasicSetN.class, condense(new Object[] {1, 2}).getClass());
        assertEquals(BasicSetN.class, condense(new Object[] {1, 2, 3}).getClass());
        assertEquals(BasicSetN.class, condense(new Object[] {1, 2, 3, 4}).getClass());
        assertEquals(BasicSetN.class, condense(new Object[] {1, 2, 3, 4, 5}).getClass());
        assertEquals(BasicSetN.class, condense(new Object[] {1, 2, 3, 4, 5, 6}).getClass());
        assertEquals(BasicSetN.class, condense(new Object[] {1, 2, 3, 4, 5, 6, 7}).getClass());
        assertEquals(BasicSetN.class, condense(new Object[] {1, 2, 3, 4, 5, 6, 7, 8}).getClass());
    }
}
