package net.nullschool.collect.basic;

import net.nullschool.collect.ConstSortedSet;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static net.nullschool.collect.CollectionTestingTools.*;
import static java.util.Collections.*;


/**
 * 2013-04-25<p/>
 *
 * @author Cameron Beccario
 */
public class BasicSortedSetNTest {

    @Test
    public void test_comparison() {
        compare_sorted_sets(
            asSortedSet(null, 1, 2, 3, 5, 6, 7),
            new BasicSortedSetN<>(null, new Object[] {1, 2, 3, 5, 6, 7}),
            0, 2, 4, 6, 8, 1, 7);
        compare_sorted_sets(
            asSortedSet(reverseOrder(), 7, 6, 5, 3, 2, 1),
            new BasicSortedSetN<>(reverseOrder(), new Object[] {7, 6, 5, 3, 2, 1}),
            8, 6, 4, 2, 0, 7, 1);
    }

    @Test
    public void test_immutable() {
        assert_sorted_set_immutable(new BasicSortedSetN<>(null, new Object[] {1, 2, 3, 4, 5, 6}));
    }

    @Test
    public void test_with() {
        ConstSortedSet<Integer> set;

        set = new BasicSortedSetN<>(null, new Object[] {1, 2, 3, 4, 5, 6});
        compare_sorted_sets(asSortedSet(null, 1, 2, 3, 4, 5, 6, 7), set.with(7));
        compare_sorted_sets(asSortedSet(null, 0, 1, 2, 3, 4, 5, 6), set.with(0));
        compare_sorted_sets(asSortedSet(null, 1, 2, 3, 4, 5, 6, 7, 8), set.with(8).with(7));
        assertSame(set, set.with(6));

        set = new BasicSortedSetN<>(reverseOrder(), new Object[] {6, 5, 4, 3, 2, 1});
        compare_sorted_sets(asSortedSet(reverseOrder(), 7, 6, 5, 4, 3, 2, 1), set.with(7));
        compare_sorted_sets(asSortedSet(reverseOrder(), 6, 5, 4, 3, 2, 1, 0), set.with(0));
        compare_sorted_sets(asSortedSet(reverseOrder(), 8, 7, 6, 5, 4, 3, 2, 1), set.with(8).with(7));
        assertSame(set, set.with(6));
    }

    @Test
    public void test_withAll() {
        ConstSortedSet<Integer> set;

        set = new BasicSortedSetN<>(null, new Object[] {1, 2, 3, 4, 5, 7});
        compare_sorted_sets(
            asSortedSet(null, 0, 1, 2, 3, 4, 5, 6, 7, 8),
            set.withAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 7, 6, 5, 4, 3, 2, 1, 0)));
        assertSame(set, set.withAll(Arrays.asList(1, 1, 1, 2, 2, 3, 3)));
        assertSame(set, set.withAll(Collections.<Integer>emptyList()));

        set = new BasicSortedSetN<>(reverseOrder(), new Object[] {7, 5, 4, 3, 2, 1});
        compare_sorted_sets(
            asSortedSet(reverseOrder(), 8, 7, 6, 5, 4, 3, 2, 1, 0),
            set.withAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 7, 6, 5, 4, 3, 2, 1, 0)));
        assertSame(set, set.withAll(Arrays.asList(1, 1, 1, 2, 2, 3, 3)));
        assertSame(set, set.withAll(Collections.<Integer>emptyList()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withAll_throws() {
        new BasicSortedSetN<>(null, new Object[] {1, 2, 3, 4, 5, 6}).withAll(null);
    }

    @Test
    public void test_without() {
        ConstSortedSet<Integer> set;

        set = new BasicSortedSetN<>(null, new Object[] {1, 2, 3, 4, 5, 6});
        compare_sorted_sets(asSortedSet(null, 2, 3, 4, 5, 6), set.without(1));
        compare_sorted_sets(asSortedSet(null, 1, 3, 4, 5, 6), set.without(2));
        compare_sorted_sets(asSortedSet(null, 1, 2, 3, 4, 5), set.without(6));
        assertSame(set, set.without(-1));
        assertSame(set, set.without(7));

        set = new BasicSortedSetN<>(reverseOrder(), new Object[] {6, 5, 4, 3, 2, 1});
        compare_sorted_sets(asSortedSet(reverseOrder(), 6, 5, 4, 3, 2), set.without(1));
        compare_sorted_sets(asSortedSet(reverseOrder(), 6, 5, 4, 3, 1), set.without(2));
        compare_sorted_sets(asSortedSet(reverseOrder(), 5, 4, 3, 2, 1), set.without(6));
        assertSame(set, set.without(-1));
        assertSame(set, set.without(7));
    }

    @Test
    public void test_withoutAll() {
        ConstSortedSet<Integer> set;

        set = new BasicSortedSetN<>(null, new Object[] {1, 2, 3, 4, 5, 6});
        compare_sorted_sets(asSortedSet(null, 2, 4, 5), set.withoutAll(Arrays.asList(1, 3, 6)));
        compare_sorted_sets(asSortedSet(null, 2, 4, 5), set.withoutAll(Arrays.asList(1, 3, 9, -1, 6)));
        assertSame(set, set.withoutAll(Arrays.asList(7)));
        assertSame(set, set.withoutAll(Arrays.asList()));
        assertEquals(BasicSortedSet0.<Integer>instance(null), set.withoutAll(set));

        set = new BasicSortedSetN<>(reverseOrder(), new Object[] {6, 5, 4, 3, 2, 1});
        compare_sorted_sets(asSortedSet(reverseOrder(), 5, 4, 2), set.withoutAll(Arrays.asList(1, 3, 6)));
        compare_sorted_sets(asSortedSet(reverseOrder(), 5, 4, 2), set.withoutAll(Arrays.asList(1, 3, 9, -1, 6)));
        assertSame(set, set.withoutAll(Arrays.asList(7)));
        assertSame(set, set.withoutAll(Arrays.asList()));
        assertEquals(BasicSortedSet0.<Integer>instance(reverseOrder()), set.withoutAll(set));
    }

    @Test(expected = NullPointerException.class)
    public void test_withoutAll_throws() {
        new BasicSortedSetN<>(null, new Object[] {1, 2, 3, 4, 5, 6}).withoutAll(null);
    }

    @Test
    public void test_serialization() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);

        ConstSortedSet<String> set = new BasicSortedSetN<>(null, new Object[] {"a", "b", "c", "d"});

        out.writeObject(set);
        byte[] data = baos.toByteArray();
        assertEquals(
            "aced05sr0+net.nullschool.collect.basic.SortedSetProxy00000001300xppw40004t01at01bt01ct01dx",
            BasicToolsTest.asReadableString(data));

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));

        ConstSortedSet<?> read = (ConstSortedSet)in.readObject();
        compare_sorted_sets(set, read);
        assertSame(set.getClass(), read.getClass());
    }

    @Test
    public void test_serialization_with_comparator() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);

        ConstSortedSet<String> set = new BasicSortedSetN<>(reverseOrder(), new Object[] {"d", "c", "b", "a"});

        out.writeObject(set);
        byte[] data = baos.toByteArray();
        assertEquals(
            "aced05sr0+net.nullschool.collect.basic.SortedSetProxy00000001300xpsr0'" +
                "java.util.Collections$ReverseComparatord48af0SNJd0200xpw40004t01dt01ct01bt01ax",
            BasicToolsTest.asReadableString(data));

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));

        ConstSortedSet<?> read = (ConstSortedSet)in.readObject();
        compare_sorted_sets(set, read);
        assertSame(set.getClass(), read.getClass());
    }

    @Test
    public void test_get() {
        BasicSortedSetN<Integer> set = new BasicSortedSetN<>(null, new Object[] {1, 2, 3, 4, 5, 6});
        for (int i = 0; i < set.size(); i++) {
            assertEquals(i + 1, (int)set.get(i));
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_out_of_bounds_get() {
        new BasicSortedSetN<>(null, new Object[] {1, 2, 3, 4, 5, 6}).get(7);
    }

    @Test
    public void test_non_equality() {
        assertFalse(new BasicSortedSetN<>(null, new Object[] {1, 2, 3}).equals(asSortedSet(null, 1, 2, 4)));
        assertFalse(asSortedSet(null, 1, 2, 4).equals(new BasicSortedSetN<>(null, new Object[] {1, 2, 3})));
    }

    @Test
    public void test_different_comparators_still_equal() {
        assertTrue(
            new BasicSortedSetN<>(null, new Object[] {1, 2, 3})
                .equals(new BasicSortedSetN<>(reverseOrder(), new Object[] {3, 2, 1})));
    }

}
