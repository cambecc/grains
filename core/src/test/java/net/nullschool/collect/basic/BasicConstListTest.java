package net.nullschool.collect.basic;

import net.nullschool.collect.ConstList;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import static net.nullschool.collect.basic.BasicConstList.*;
import static org.junit.Assert.*;
import static net.nullschool.collect.CollectionTestingTools.*;

/**
 * 2013-04-30<p/>
 *
 * @author Cameron Beccario
 */
public class BasicConstListTest {

    @Test
    public void test_emptyList() {
        assertSame(BasicList0.instance(), emptyList());
    }

    @Test
    public void test_listOf() {
        compare_lists(Arrays.asList(1), listOf(1));
        compare_lists(Arrays.asList(1, 2), listOf(1, 2));
        compare_lists(Arrays.asList(1, 2, 3), listOf(1, 2, 3));
        compare_lists(Arrays.asList(1, 2, 3, 4), listOf(1, 2, 3, 4));
        compare_lists(Arrays.asList(1, 2, 3, 4, 5), listOf(1, 2, 3, 4, 5));
        compare_lists(Arrays.asList(1, 2, 3, 4, 5, 6), listOf(1, 2, 3, 4, 5, 6));
        compare_lists(Arrays.asList(1, 2, 3, 4, 5, 6, 7), listOf(1, 2, 3, 4, 5, 6, 7));
        compare_lists(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8), listOf(1, 2, 3, 4, 5, 6, 7, 8));

        // noinspection RedundantArrayCreation
        compare_lists(Arrays.asList(9, 3, 2, 1, 1, 3, 2, 3, 2), listOf(9, 3, 2, 1, 1, 3, new Integer[] {2, 3, 2}));

        compare_lists(Arrays.asList((Object)null), listOf(null));
        compare_lists(Arrays.asList(null, null), listOf(null, null));
        compare_lists(Arrays.asList(null, null, null), listOf(null, null, null));
        compare_lists(Arrays.asList(null, null, null, null), listOf(null, null, null, null));
        compare_lists(Arrays.asList(null, null, null, null, null), listOf(null, null, null, null, null));
        compare_lists(Arrays.asList(null, null, null, null, null, null), listOf(null, null, null, null, null, null));
        compare_lists(
            Arrays.asList(null, null, null, null, null, null, null),
            listOf(null, null, null, null, null, null, (Object)null));
        compare_lists(
            Arrays.asList(null, null, null, null, null, null, null, null),
            listOf(null, null, null, null, null, null, null, null));
    }

    @Test
    public void test_listOf_types() {
        assertEquals(BasicList1.class, listOf(1).getClass());
        assertEquals(BasicListN.class, listOf(1, 2).getClass());
        assertEquals(BasicListN.class, listOf(1, 2, 3).getClass());
        assertEquals(BasicListN.class, listOf(1, 2, 3, 4).getClass());
        assertEquals(BasicListN.class, listOf(1, 2, 3, 4, 5).getClass());
        assertEquals(BasicListN.class, listOf(1, 2, 3, 4, 5, 6).getClass());
        assertEquals(BasicListN.class, listOf(1, 2, 3, 4, 5, 6, 7).getClass());
        assertEquals(BasicListN.class, listOf(1, 2, 3, 4, 5, 6, 7, 8).getClass());
    }

    @Test
    public void test_asList_array() {
        assertSame(emptyList(), asList(new Integer[] {}));
        compare_lists(Arrays.asList(1), asList(new Integer[] {1}));
        compare_lists(Arrays.asList(1, 2), asList(new Integer[] {1, 2}));
        compare_lists(Arrays.asList(1, 2, 3), asList(new Integer[] {1, 2, 3}));
        compare_lists(Arrays.asList(1, 2, 3, 4), asList(new Integer[] {1, 2, 3, 4}));
        compare_lists(Arrays.asList(1, 2, 3, 4, 5), asList(new Integer[] {1, 2, 3, 4, 5}));
        compare_lists(Arrays.asList(1, 2, 3, 4, 5, 6), asList(new Integer[] {1, 2, 3, 4, 5, 6}));
    }

    @Test
    public void test_asList_array_types() {
        assertEquals(BasicList1.class, asList(new Integer[] {1}).getClass());
        assertEquals(BasicListN.class, asList(new Integer[] {1, 2}).getClass());
        assertEquals(BasicListN.class, asList(new Integer[] {1, 2, 3}).getClass());
        assertEquals(BasicListN.class, asList(new Integer[] {1, 2, 3, 4}).getClass());
        assertEquals(BasicListN.class, asList(new Integer[] {1, 2, 3, 4, 5}).getClass());
        assertEquals(BasicListN.class, asList(new Integer[] {1, 2, 3, 4, 5, 6}).getClass());
        assertEquals(BasicListN.class, asList(new Integer[] {1, 2, 3, 4, 5, 6, 7}).getClass());
        assertEquals(BasicListN.class, asList(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8}).getClass());
    }

    @Test(expected = NullPointerException.class)
    public void test_asList_array_null() {
        asList((Integer[])null);
    }

    @Test
    public void test_asList_collection() {
        assertSame(emptyList(), asList(Arrays.asList()));
        compare_lists(Arrays.asList(1), asList(Arrays.asList(1)));
        compare_lists(Arrays.asList(1, 2), asList(Arrays.asList(1, 2)));
        compare_lists(Arrays.asList(1, 2, 3), asList(Arrays.asList(1, 2, 3)));
        compare_lists(Arrays.asList(1, 2, 3, 4), asList(Arrays.asList(1, 2, 3, 4)));
        compare_lists(Arrays.asList(1, 2, 3, 4, 5), asList(Arrays.asList(1, 2, 3, 4, 5)));
        compare_lists(Arrays.asList(1, 2, 3, 4, 5, 6), asList(Arrays.asList(1, 2, 3, 4, 5, 6)));

        ConstList<Integer> list;
        list = listOf(1);
        assertSame(list, asList(list));
        list = listOf(1, 2);
        assertSame(list, asList(list));
        list = listOf(1, 2, 3);
        assertSame(list, asList(list));
        list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertSame(list, asList(list));
    }

    @Test
    public void test_asList_collection_types() {
        assertEquals(BasicList1.class, asList(Arrays.asList(1)).getClass());
        assertEquals(BasicListN.class, asList(Arrays.asList(1, 2)).getClass());
        assertEquals(BasicListN.class, asList(Arrays.asList(1, 2, 3)).getClass());
        assertEquals(BasicListN.class, asList(Arrays.asList(1, 2, 3, 4)).getClass());
        assertEquals(BasicListN.class, asList(Arrays.asList(1, 2, 3, 4, 5)).getClass());
        assertEquals(BasicListN.class, asList(Arrays.asList(1, 2, 3, 4, 5, 6)).getClass());
        assertEquals(BasicListN.class, asList(Arrays.asList(1, 2, 3, 4, 5, 6, 7)).getClass());
        assertEquals(BasicListN.class, asList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)).getClass());
    }

    @Test(expected = NullPointerException.class)
    public void test_asList_collection_null() {
        asList((Collection<?>)null);
    }

    @Test
    public void test_asList_iterator() {
        assertSame(emptyList(), asList(Arrays.asList().iterator()));
        compare_lists(Arrays.asList(1), asList(Arrays.asList(1).iterator()));
        compare_lists(Arrays.asList(1, 2), asList(Arrays.asList(1, 2).iterator()));
        compare_lists(Arrays.asList(1, 2, 3), asList(Arrays.asList(1, 2, 3).iterator()));
        compare_lists(Arrays.asList(1, 2, 3, 4), asList(Arrays.asList(1, 2, 3, 4).iterator()));
        compare_lists(Arrays.asList(1, 2, 3, 4, 5), asList(Arrays.asList(1, 2, 3, 4, 5).iterator()));
        compare_lists(Arrays.asList(1, 2, 3, 4, 5, 6), asList(Arrays.asList(1, 2, 3, 4, 5, 6).iterator()));
    }

    @Test
    public void test_asList_iterator_types() {
        assertEquals(BasicList1.class, asList(Arrays.asList(1).iterator()).getClass());
        assertEquals(BasicListN.class, asList(Arrays.asList(1, 2).iterator()).getClass());
        assertEquals(BasicListN.class, asList(Arrays.asList(1, 2, 3).iterator()).getClass());
        assertEquals(BasicListN.class, asList(Arrays.asList(1, 2, 3, 4).iterator()).getClass());
        assertEquals(BasicListN.class, asList(Arrays.asList(1, 2, 3, 4, 5).iterator()).getClass());
        assertEquals(BasicListN.class, asList(Arrays.asList(1, 2, 3, 4, 5, 6).iterator()).getClass());
        assertEquals(BasicListN.class, asList(Arrays.asList(1, 2, 3, 4, 5, 6, 7).iterator()).getClass());
        assertEquals(BasicListN.class, asList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8).iterator()).getClass());
    }

    @Test(expected = NullPointerException.class)
    public void test_asList_iterator_null() {
        asList((Iterator<?>)null);
    }

    @Test
    public void test_condense() {
        assertSame(emptyList(), condense(new Object[] {}));
        assertEquals(BasicList1.class, condense(new Object[] {1}).getClass());
        assertEquals(BasicListN.class, condense(new Object[] {1, 2}).getClass());
        assertEquals(BasicListN.class, condense(new Object[] {1, 2, 3}).getClass());
        assertEquals(BasicListN.class, condense(new Object[] {1, 2, 3, 4}).getClass());
        assertEquals(BasicListN.class, condense(new Object[] {1, 2, 3, 4, 5}).getClass());
        assertEquals(BasicListN.class, condense(new Object[] {1, 2, 3, 4, 5, 6}).getClass());
        assertEquals(BasicListN.class, condense(new Object[] {1, 2, 3, 4, 5, 6, 7}).getClass());
        assertEquals(BasicListN.class, condense(new Object[] {1, 2, 3, 4, 5, 6, 7, 8}).getClass());
    }
}
