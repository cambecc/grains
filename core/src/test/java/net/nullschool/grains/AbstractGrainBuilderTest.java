package net.nullschool.grains;

import net.nullschool.collect.CollectionTestingTools;
import net.nullschool.collect.MapIterator;
import org.junit.Test;

import java.util.*;

import static net.nullschool.collect.CollectionTestingTools.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;


/**
 * 2013-05-04<p/>
 *
 * @author Cameron Beccario
 */
public class AbstractGrainBuilderTest {

    @Test
    public void test_compare() {
        Map<String, Object> expected = newMap("a", 1, "b", 2);
        GrainBuilder builder = new MockGrainBuilder(
            CollectionTestingTools.<String, Object>newSortedMap(null, "a", 1),
            CollectionTestingTools.<String, Object>newSortedMap(null, "b", 2));

        compare_maps(expected, builder);
    }

    @Test
    public void test_put() {
        Map<String, Object> expected = newMap("a", 1, "+", 3, "b", 2);
        GrainBuilder builder = new MockGrainBuilder("a");

        builder.put("b", 2);
        builder.put("a", 1);
        builder.put("+", 3);
        compare_maps(expected, builder);
    }

    @Test
    public void test_putAll() {
        Map<String, Object> expected = newMap("a", 1, "b", 2);
        GrainBuilder builder = new MockGrainBuilder("a");

        builder.putAll(expected);
        compare_maps(expected, builder);
    }

    @Test
    public void test_iteration_without_calling_hasNext() {
        Set<String> keys = new LinkedHashSet<>();
        GrainBuilder builder = new MockGrainBuilder("a");
        builder.put("a", 1);
        builder.put("b", 2);
        MapIterator<String, Object> iter = builder.iterator();
        for (int i = 0; i < builder.size(); i++) {
            keys.add(iter.next());
        }
        try { iter.next(); fail(); } catch (NoSuchElementException expected) {}
        assertArrayEquals(builder.keySet().toArray(), keys.toArray());

    }

    @Test
    public void test_remove() {
        GrainBuilder builder = new MockGrainBuilder("a");
        builder.put("a", 1);
        builder.put("b", 2);
        compare_maps(newMap("a", 1, "b", 2), builder);
        builder.remove("a");
        compare_maps(newMap("a", null, "b", 2), builder);
        builder.remove("b");
        compare_maps(newMap("a", null), builder);
    }

    @Test
    public void test_clear() {
        GrainBuilder builder = new MockGrainBuilder("a", "x", "y");
        builder.put("x", 8);
        builder.put("y", 9);
        builder.put("a", 1);
        builder.put("c", 3);
        builder.put("b", 2);
        compare_maps(newMap("a", 1, "x", 8, "y", 9, "b", 2, "c", 3), builder);
        builder.clear();
        compare_maps(newMap("a", null, "x", null, "y", null), builder);
    }

    @Test
    public void test_iterator_remove() {
        GrainBuilder builder = new MockGrainBuilder("a", "x", "y");
        builder.put("x", 8);
        builder.put("y", 9);
        builder.put("a", 1);
        builder.put("c", 3);
        builder.put("b", 2);
        compare_maps(newMap("a", 1, "x", 8, "y", 9, "b", 2, "c", 3), builder);
        for (MapIterator<String, Object> iter = builder.iterator(); iter.hasNext();) {
            iter.next();
            iter.remove();
        }
        compare_maps(newMap("a", null, "x", null, "y", null), builder);
    }

    @Test
    public void test_entry_setValue() {
        GrainBuilder builder = new MockGrainBuilder("a", "x", "y");
        builder.put("x", 8);
        builder.put("y", 9);
        builder.put("a", 1);
        builder.put("c", 3);
        builder.put("b", 2);
        compare_maps(newMap("a", 1, "x", 8, "y", 9, "b", 2, "c", 3), builder);
        int i = 1;
        for (MapIterator<String, Object> iter = builder.iterator(); iter.hasNext();) {
            iter.next();
            iter.entry().setValue(i++);
        }
        compare_maps(newMap("a", 1, "x", 2, "y", 3, "b", 4, "c", 5), builder);
    }
}
