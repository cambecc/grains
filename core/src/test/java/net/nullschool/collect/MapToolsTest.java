package net.nullschool.collect;

import org.junit.Test;

import static net.nullschool.collect.MapTools.*;
import static org.junit.Assert.*;

/**
 * 2013-02-06<p/>
 *
 * @author Cameron Beccario
 */
public final class MapToolsTest {

    @Test
    public void test_interleave() {
        // one entry
        assertEquals("{a=1}", interleave(new String[] {"a"}, new Integer[] {1}).toString());
        // two entries
        assertEquals("{a=1, b=2}", interleave(new String[] {"a", "b"}, new Integer[] {1, 2}).toString());
        // duplicate key
        assertEquals("{a=2}", interleave(new String[] {"a", "a"}, new Integer[] {1, 2}).toString());
        // extra keys
        assertEquals("{a=1}", interleave(new String[] {"a", "b"}, new Integer[] {1}).toString());
        // extra values
        assertEquals("{a=1}", interleave(new String[] {"a"}, new Integer[] {1, 2}).toString());
        // empty map
        assertEquals("{}", interleave(new String[] {}, new Integer[] {}).toString());
    }

    @Test(expected = NullPointerException.class)
    public void test_bad_interleave() {
        interleave(new String[] {}, null);
    }

    @Test(expected = NullPointerException.class)
    public void test_bad_interleave_2() {
        interleave(null, new Integer[] {});
    }
}
