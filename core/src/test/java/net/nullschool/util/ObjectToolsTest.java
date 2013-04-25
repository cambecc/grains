package net.nullschool.util;

import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;

import static org.junit.Assert.*;
import static net.nullschool.util.ObjectTools.*;

import static org.mockito.Mockito.*;

/**
 * 2013-02-07<p/>
 *
 * @author Cameron Beccario
 */
public class ObjectToolsTest {

    @Test
    public void test_coalesce() {
        Object x = new Object();
        Object y = new Object();
        assertSame(x, coalesce(x, y));
        assertSame(x, coalesce(x, null));
        assertNull(coalesce(null, null));
        assertSame(y, coalesce(null, y));
    }

    @Test
    public void test_compare() {
        assertEquals( 0, compare(1, 1, null));
        assertEquals(-1, compare(0, 1, null));
        assertEquals( 1, compare(1, 0, null));
        assertEquals( 0, compare(1, 1, Collections.reverseOrder()));
        assertEquals( 1, compare(0, 1, Collections.reverseOrder()));
        assertEquals(-1, compare(1, 0, Collections.reverseOrder()));
    }

    @Test
    public void test_compare_nulls() {
        // Ensure comparator called when both arguments null.
        @SuppressWarnings("unchecked") Comparator<Object> c = (Comparator<Object>)mock(Comparator.class);
        when(c.compare(any(), any())).thenReturn(5);
        assertEquals(5, compare(null, null, c));
    }

    @Test(expected = NullPointerException.class)
    public void test_natural_compare_nulls() {
        compare(null, 5, null);
    }

    @Test(expected = ClassCastException.class)
    public void test_compare_not_comparable() {
        compare(int.class, int.class, null);
    }
}
