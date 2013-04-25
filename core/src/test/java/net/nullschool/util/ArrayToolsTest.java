package net.nullschool.util;

import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;
import static net.nullschool.util.ArrayTools.*;


/**
 * 2013-04-05<p/>
 *
 * @author Cameron Beccario
 */
public class ArrayToolsTest {

    @Test
    public void test_indexOf() {
        assertEquals(0, indexOf(10, new Integer[] {10}));
        assertEquals(-1, indexOf(9, new Integer[] {10}));
        assertEquals(-1, indexOf(null, new Integer[] {10}));
        assertEquals(1, indexOf(null, new Integer[] {10, null, null}));
        assertEquals(3, indexOf(13, new Integer[] {10, null, null, 13}));
    }

    @Test(expected = NullPointerException.class)
    public void test_indexOf_illegal_arg() {
        indexOf(10, null);
    }

    @Test
    public void test_bounded_indexOf() {
        Integer[] ary = new Integer[] {10, 11, 12, 13, null, 10, 11, 12, 13, null};

        assertEquals(0, indexOf(10, ary, 0, 10));
        assertEquals(5, indexOf(10, ary, 1, 10));
        assertEquals(-1, indexOf(10, ary, 1, 5));

        assertEquals(4, indexOf(null, ary, 0, 10));
        assertEquals(4, indexOf(null, ary, 4, 10));
        assertEquals(9, indexOf(null, ary, 5, 10));
        assertEquals(-1, indexOf(null, ary, 5, 9));

        assertEquals(-1, indexOf(10, ary, 0, 0));
        assertEquals(-1, indexOf(10, ary, 10, 10));
    }

    @Test
    public void test_bounded_indexOf_illegal_arg() {
        Integer[] ary = new Integer[] {10, 11, 12, 13, null, 10, 11, 12, 13, null};

        try { indexOf(0, null, 2, 1);  fail(); } catch (NullPointerException ignored) {}
        try { indexOf(0, ary, -2, -1); fail(); } catch (IndexOutOfBoundsException ignored) {}
        try { indexOf(0, ary, -1, 0);  fail(); } catch (IndexOutOfBoundsException ignored) {}
        try { indexOf(0, ary, 10, 11); fail(); } catch (IndexOutOfBoundsException ignored) {}
        try { indexOf(0, ary, 2, 1);   fail(); } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void test_comparator_indexOf() {
        String[] ary = new String[] {"a", "b", "c", "d", "B"};

        assertEquals(1, indexOf("b", ary, String.CASE_INSENSITIVE_ORDER));
        assertEquals(1, indexOf("B", ary, String.CASE_INSENSITIVE_ORDER));
        assertEquals(-1, indexOf("e", ary, String.CASE_INSENSITIVE_ORDER));

        assertEquals(1, indexOf("b", ary, null));
        assertEquals(4, indexOf("B", ary, null));
        assertEquals(-1, indexOf("e", ary, null));
    }

    @Test(expected = NullPointerException.class)
    public void test_comparator_indexOf_null_arguments() {
        // CASE_INSENSITIVE_ORDER does not allow null strings.
        indexOf(null, new String[] {null}, String.CASE_INSENSITIVE_ORDER);
    }

    @Test(expected = NullPointerException.class)
    public void test_comparator_indexOf_null_arguments_null_comparator() {
        // String natural ordering does not allow nulls.
        indexOf(null, new String[] {null}, null);
    }

    @Test(expected = NullPointerException.class)
    public void test_comparator_indexOf_illegal_arg() {
        indexOf(10, null, null);
    }

    @Test
    public void test_bounded_comparator_indexOf() {
        String[] ary = new String[] {"a", "b", "c", "d", "B"};

        assertEquals(1, indexOf("B", ary, 0, 5, String.CASE_INSENSITIVE_ORDER));
        assertEquals(4, indexOf("b", ary, 2, 5, String.CASE_INSENSITIVE_ORDER));
        assertEquals(-1, indexOf("b", ary, 2, 4, String.CASE_INSENSITIVE_ORDER));
        assertEquals(-1, indexOf("b", ary, 0, 0, String.CASE_INSENSITIVE_ORDER));
        assertEquals(-1, indexOf("b", ary, 5, 5, String.CASE_INSENSITIVE_ORDER));
        assertEquals(-1, indexOf("e", ary, 0, 5, String.CASE_INSENSITIVE_ORDER));

        assertEquals(1, indexOf("b", ary, 0, 5, null));
        assertEquals(4, indexOf("B", ary, 0, 5, null));
        assertEquals(-1, indexOf("b", ary, 2, 5, null));
        assertEquals(-1, indexOf("b", ary, 2, 4, null));
        assertEquals(-1, indexOf("b", ary, 0, 0, null));
        assertEquals(-1, indexOf("b", ary, 5, 5, null));
        assertEquals(-1, indexOf("e", ary, 0, 5, null));
    }

    @Test
    public void test_bounded_comparator_indexOf_illegal_arg() {
        String[] ary = new String[] {"a", "b", "c", "d", "B"};

        try { indexOf("b", null, 2, 1, null);  fail(); } catch (NullPointerException ignored) {}
        try { indexOf("b", ary, -2, -1, null); fail(); } catch (IndexOutOfBoundsException ignored) {}
        try { indexOf("b", ary, -1, 0, null);  fail(); } catch (IndexOutOfBoundsException ignored) {}
        try { indexOf("b", ary, 5, 6, null);   fail(); } catch (IndexOutOfBoundsException ignored) {}
        try { indexOf("b", ary, 2, 1, null);   fail(); } catch (IllegalArgumentException ignored) {}
    }

    @Test(expected = NullPointerException.class)
    public void test_bounded_comparator_indexOf_null_arguments() {
        // CASE_INSENSITIVE_ORDER does not allow null strings.
        indexOf(null, new String[] {null}, 0, 1, String.CASE_INSENSITIVE_ORDER);
    }

    @Test(expected = NullPointerException.class)
    public void test_bounded_comparator_indexOf_null_arguments_null_comparator() {
        // String natural ordering does not allow nulls.
        indexOf(null, new String[] {null}, 0, 1, null);
    }

    @Test
    public void test_lastIndexOf() {
        Integer[] ary = new Integer[] {10, 11, 12, 13, null, 10, 11, 12, 13, null};

        assertEquals(9, lastIndexOf(null, ary));
        assertEquals(6, lastIndexOf(11, ary));
        assertEquals(-1, lastIndexOf(42, ary));
        assertEquals(-1, lastIndexOf(null, new Integer[] {1, 2, 3}));
    }

    @Test(expected = NullPointerException.class)
    public void test_lastIndexOf_illegal_arg() {
        lastIndexOf(10, null);
    }

    @Test
    public void test_check_range() {
        checkRange(0, 0, 2);
        checkRange(0, 2, 2);
        checkRange(2, 2, 2);
        try { checkRange(-1, 2, 2); fail(); } catch (IndexOutOfBoundsException ignored) {}
        try { checkRange( 0, 3, 2); fail(); } catch (IndexOutOfBoundsException ignored) {}
        try { checkRange( 7, 6, 2); fail(); } catch (IndexOutOfBoundsException ignored) {}
        try { checkRange( 1, 0, 2); fail(); } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void test_sort() {
        Integer[] a = new Integer[] {3, 2, 1};
        assertArrayEquals(new Integer[] {1, 2, 3}, sort(a));
        assertSame(a, sort(a));
    }

    @Test(expected = NullPointerException.class)
    public void test_sort_nulls() {
        Integer[] a = new Integer[] {null, null};
        sort(a);
    }

    @Test
    public void test_sort_comparator() {
        Integer[] a = new Integer[] {2, 1, 3};

        assertArrayEquals(new Integer[] {1, 2, 3}, sort(a, null));
        assertSame(a, sort(a, null));

        assertArrayEquals(new Integer[] {3, 2, 1}, sort(a, Collections.reverseOrder()));
        assertSame(a, sort(a, Collections.reverseOrder()));
    }

    @Test(expected = NullPointerException.class)
    public void test_sort_comparator_nulls() {
        String[] a = new String[] {null, null};
        sort(a, String.CASE_INSENSITIVE_ORDER);
    }
}
