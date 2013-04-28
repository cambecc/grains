package net.nullschool.collect.basic;

import net.nullschool.collect.ConstList;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;

import static net.nullschool.collect.CollectionTestingTools.*;
import static org.junit.Assert.*;

/**
 * 2013-03-16<p/>
 *
 * @author Cameron Beccario
 */
public class BasicListNTest {

    @Test
    public void test_comparison() {
        compare_lists(
            Arrays.asList(1, 2, 3, 4, 5, 6),
            new BasicListN<>(new Object[] {1, 2, 3, 4, 5, 6}));
    }

    @Test
    public void test_immutable() {
        assert_list_immutable(new BasicListN<>(new Object[] {1, 2, 3, 4, 5, 6}));
    }

    @Test
    public void test_with() {
        compare_lists(
            Arrays.asList(1, 2, 3, 4, 5, 6, 7),
            new BasicListN<Integer>(new Object[] {1, 2, 3, 4, 5, 6}).with(7));
        compare_lists(
            Arrays.asList(1, 2, 3, 4, 5, 6, null),
            new BasicListN<Integer>(new Object[] {1, 2, 3, 4, 5, 6}).with(null));
    }

    @Test
    public void test_with_index() {
        compare_lists(
            Arrays.asList(7, 1, 2, 3, 4, 5, 6),
            new BasicListN<Integer>(new Object[] {1, 2, 3, 4, 5, 6}).with(0, 7));
        compare_lists(
            Arrays.asList(1, 2, 3, 7, 4, 5, 6),
            new BasicListN<Integer>(new Object[] {1, 2, 3, 4, 5, 6}).with(3, 7));
        compare_lists(
            Arrays.asList(1, 2, 3, 4, 5, 6, 7),
            new BasicListN<Integer>(new Object[] {1, 2, 3, 4, 5, 6}).with(6, 7));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_with_index_out_of_bounds() {
        new BasicListN<Integer>(new Object[] {1, 2, 3, 4, 5, 6}).with(7, 7);
    }

    @Test
    public void test_withAll() {
        ConstList<Integer> list = new BasicListN<>(new Object[] {1, 2, 3, 4, 5, 6});
        compare_lists(Arrays.asList(1, 2, 3, 4, 5, 6, 1, 2, 3), list.withAll(Arrays.asList(1, 2, 3)));
        assertSame(list, list.withAll(Collections.<Integer>emptyList()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withAll_throws() {
        new BasicListN<>(new Object[] {1, 2, 3, 4, 5, 6}).withAll(null);
    }

    @Test
    public void test_withAll_index() {
        ConstList<Integer> list = new BasicListN<>(new Object[] {1, 2, 3, 4, 5, 6});
        compare_lists(Arrays.asList(1, 2, 3, 1, 2, 3, 4, 5, 6), list.withAll(0, Arrays.asList(1, 2, 3)));
        compare_lists(Arrays.asList(1, 2, 3, 4, 1, 2, 3, 5, 6), list.withAll(4, Arrays.asList(1, 2, 3)));
        compare_lists(Arrays.asList(1, 2, 3, 4, 5, 6, 1, 2, 3), list.withAll(6, Arrays.asList(1, 2, 3)));
        assertSame(list, list.withAll(0, Collections.<Integer>emptyList()));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_withAll_index_out_of_bounds() {
        new BasicListN<>(new Object[] {1, 2, 3, 4, 5, 6}).withAll(7, Collections.<Integer>emptyList());
    }

    @Test(expected = NullPointerException.class)
    public void test_withAll_index_throws() {
        new BasicListN<>(new Object[] {1, 2, 3, 4, 5, 6}).withAll(0, null);
    }

    @Test
    public void test_replace() {
        ConstList<Integer> list = new BasicListN<>(new Object[] {1, 2, 3, 4, 5, 6});
        compare_lists(Arrays.asList(9, 2, 3, 4, 5, 6), list.replace(0, 9));
        compare_lists(Arrays.asList(1, 2, 3, 9, 5, 6), list.replace(3, 9));
        compare_lists(Arrays.asList(1, 2, 3, 4, 5, 9), list.replace(5, 9));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_replace_out_of_bounds() {
        new BasicListN<>(new Object[] {1, 2, 3, 4, 5, 6}).replace(6, 9);
    }

    @Test
    public void test_without() {
        ConstList<Integer> list = new BasicListN<>(new Object[] {1, 2, 3, 4, 5, 6, 1});
        compare_lists(Arrays.asList(2, 3, 4, 5, 6, 1), list.without(1));
        compare_lists(Arrays.asList(1, 3, 4, 5, 6, 1), list.without(2));
        assertSame(list, list.without(7));
        assertSame(list, list.without(null));
    }

    @Test
    public void test_delete() {
        ConstList<Integer> list = new BasicListN<>(new Object[] {1, 2, 3, 4, 5, 6});
        compare_lists(Arrays.asList(2, 3, 4, 5, 6), list.delete(0));
        compare_lists(Arrays.asList(1, 2, 3, 5, 6), list.delete(3));
        compare_lists(Arrays.asList(1, 2, 3, 4, 5), list.delete(5));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_delete_out_of_bounds() {
        new BasicListN<>(new Object[] {1, 2, 3, 4, 5, 6}).delete(6);
    }

    @Test
    public void test_withoutAll() {
        ConstList<Integer> list = new BasicListN<>(new Object[] {1, 2, 3, 4, 5, 6, 1});
        compare_lists(Arrays.asList(3, 4, 5, 6), list.withoutAll(Arrays.asList(1, 2)));
        compare_lists(Arrays.asList(3, 4, 5, 6), list.withoutAll(Arrays.asList(1, 2, 9)));
        assertSame(list, list.withoutAll(Arrays.asList(7)));
        assertSame(list, list.withoutAll(Arrays.asList()));
        assertSame(BasicList0.instance(), list.withoutAll(list));
    }

    @Test(expected = NullPointerException.class)
    public void test_withoutAll_throws() {
        new BasicListN<>(new Object[] {1, 2, 3, 4, 5, 6}).withoutAll(null);
    }

    @Test
    public void test_subList() {
        ConstList<Integer> list = new BasicListN<>(new Object[] {1, 2, 3, 4, 5, 6});
        assertSame(BasicList0.instance(), list.subList(0, 0));
        assertSame(BasicList0.instance(), list.subList(6, 6));
    }

    @Test
    public void test_serialization() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);

        ConstList<Integer> list = new BasicListN<>(new Object[] {"a", "b", "c", "d", "a", "b"});

        out.writeObject(list);
        byte[] data = baos.toByteArray();
        assertEquals(
            "aced05sr0&net.nullschool.collect.basic.ListProxy00000001300xpw40006t01at01bt01ct01dq0~02q0~03x",
            BasicToolsTest.asReadableString(data));

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));

        ConstList<?> read = (ConstList)in.readObject();
        compare_lists(list, read);
        assertSame(list.getClass(), read.getClass());
    }

    @Test
    public void test_non_equality() {
        assertFalse(new BasicListN<>(new Object[] {1, 2, 3}).equals(Arrays.asList(3, 2, 1)));
        assertFalse(Arrays.asList(3, 2, 1).equals(new BasicListN<>(new Object[] {1, 2, 3})));
    }
}
