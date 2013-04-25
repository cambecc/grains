package net.nullschool.util;

import org.junit.Test;

import static org.junit.Assert.*;
import static net.nullschool.util.StringTools.*;

/**
 * 2013-04-05<p/>
 *
 * @author Cameron Beccario
 */
public class StringToolsTest {

    @Test
    public void test_capitalize() {
        assertEquals("HellO", capitalize("hellO"));
        assertEquals("HellO", capitalize("HellO"));
        assertEquals("A", capitalize("a"));
        assertEquals("", capitalize(""));
        assertNull(capitalize(null));
    }
}
