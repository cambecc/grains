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
public class BasicList1Test {

    @Test
    public void test_comparison() {
        compare_lists(Collections.singletonList(1), new BasicList1<>(1));
    }

    @Test
    public void test_immutable() {
        assert_list_immutable(new BasicList1<>(1));
    }

    @Test
    public void test_with() {
        compare_lists(Arrays.asList(1, 1), new BasicList1<>(1).with(1));
        compare_lists(Arrays.asList(1, null), new BasicList1<>(1).with(null));
    }

    @Test
    public void test_withAll() {
        ConstList<Integer> list = new BasicList1<>(1);
        compare_lists(Arrays.asList(1, 1, 2, 3), list.withAll(Arrays.asList(1, 2, 3)));
        assertSame(list, list.withAll(Collections.<Integer>emptyList()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withAll_throws() {
        new BasicList1<>(1).withAll(null);
    }

    @Test
    public void test_without() {
        ConstList<Integer> list = new BasicList1<>(1);
        assertSame(BasicList0.instance(), list.without(1));
        assertSame(list, list.without(2));
        assertSame(list, list.without(null));
    }

    @Test
    public void test_withoutAll() {
        ConstList<Integer> list = new BasicList1<>(1);
        assertSame(BasicList0.instance(), list.withoutAll(Arrays.asList(1)));
        assertSame(BasicList0.instance(), list.withoutAll(Arrays.asList(2, 1)));
        assertSame(list, list.withoutAll(Arrays.asList(2)));
        assertSame(list, list.withoutAll(Arrays.asList()));
    }

    @Test(expected = NullPointerException.class)
    public void test_withoutAll_throws() {
        new BasicList1<>(1).withoutAll(null);
    }

    @Test
    public void test_subList() {
        ConstList<Integer> list = new BasicList1<>(1);
        assertSame(BasicList0.instance(), list.subList(0, 0));
        assertSame(list, list.subList(0, 1));
        assertSame(BasicList0.instance(), list.subList(1, 1));
    }

    @Test
    public void test_serialization() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);

        ConstList<Integer> list = new BasicList1<>("a");

        out.writeObject(list);
        byte[] data = baos.toByteArray();
        assertEquals(
            "aced05sr0&net.nullschool.collect.basic.ListProxy00000001300xpw40001t01ax",
            BasicToolsTest.asReadableString(data));

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));

        ConstList<?> read = (ConstList)in.readObject();
        compare_lists(list, read);
        assertSame(list.getClass(), read.getClass());
    }

    @Test
    public void test_non_equality() {
        assertFalse(new BasicList1<>(1).equals(Arrays.asList(2)));
        assertFalse(Arrays.asList(2).equals(new BasicList1<>(1)));
        ConstList<Integer> empty = BasicList0.instance();
        assertFalse(new BasicList1<>(1).equals(empty));
    }
}
