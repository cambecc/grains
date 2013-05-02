package net.nullschool.reflect;

import org.junit.Test;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


/**
 * 2013-03-24<p/>
 *
 * @author Cameron Beccario
 */
public final class LateTypeVariableTest {

    private static class Item<T, U extends Number & Comparable> {

        public TypeVariable<?> getT() {
            return new JavaToken<T>(){}.asTypeVariable();
        }

        public TypeVariable<?> getU() {
            return new JavaToken<U>(){}.asTypeVariable();
        }

        public <V> TypeVariable<?> getV() {
            return new JavaToken<V>(){}.asTypeVariable();
        }

        public <W extends Number & Comparable> TypeVariable<?> getW() {
            return new JavaToken<W>(){}.asTypeVariable();
        }
    }

    @SuppressWarnings("unchecked") private static final Item ITEM = new Item();

    private static class MyEnum<E extends MyEnum<E>> {

        public TypeVariable<?> getE() {
            return new JavaToken<E>(){}.asTypeVariable();
        }
    }

    @SuppressWarnings("unchecked") private static final MyEnum MY_ENUM = new MyEnum();

    @SuppressWarnings("unchecked")
    private static void compare(TypeVariable<?> expected, TypeVariable<?> actual) {
        // Compare actual against expected, and then a copy of expected against expected.
        for (TypeVariable<?> tv : Arrays.asList(actual, LateTypeVariable.copyOf(actual))) {
            assertEquals(expected.getName(), tv.getName());
            assertEquals(expected.getGenericDeclaration(), tv.getGenericDeclaration());
            assertArrayEquals(expected.getBounds(), tv.getBounds());
            assertTrue(expected.equals(tv));
            assertTrue(tv.equals(expected));
            assertEquals(expected.hashCode(), tv.hashCode());
            assertEquals(expected.toString(), tv.toString());
        }
    }


    @Test
    public void test_trivial_comparison_with_java_reflection() throws Exception {

        // Normal class type parameter.
        compare(
            ITEM.getT(),
            new LateTypeVariable<>("T", Item.class));

        // Class type parameter with multiple bounds.
        compare(
            ITEM.getU(),
            new LateTypeVariable<>("U", Item.class, Number.class, Comparable.class));

        // Generic method type parameter.
        compare(
            ITEM.getV(),
            new LateTypeVariable<>("V", Item.class.getMethod("getV")));

        // Generic method type parameter with multiple bounds.
        compare(
            ITEM.getW(),
            new LateTypeVariable<>("W", Item.class.getMethod("getW"), Number.class, Comparable.class));
    }

    @Test
    public void test_recursive_type_variable() {
        compare(MY_ENUM.getE(), new LateTypeVariable<Class>("E", MyEnum.class, MY_ENUM.getE().getBounds()[0]));
    }

    @Test(expected = NullPointerException.class)
    public void test_bad_construction_1() {
        new LateTypeVariable<Class>(null, List.class);
    }

    @Test(expected = NullPointerException.class)
    public void test_bad_construction_2() {
        new LateTypeVariable<Class>("T", null);
    }

    @Test
    public void test_late_bounds() {
        LateTypeVariable<Class> e = new LateTypeVariable<Class>("E", Enum.class, (Type[])null);
        try { e.getBounds();                fail(); } catch (IllegalStateException expected) {}
        try { LateTypeVariable.copyOf(e);   fail(); } catch (IllegalStateException expected) {}
        try { e.assignBounds((Type[])null); fail(); } catch (NullPointerException expected) {}

        // Set one bound and ensure it is picked up. Can only set them once.
        e.assignBounds(Number.class);
        try {
            e.assignBounds(Number.class);
            fail();
        }
        catch (IllegalArgumentException expected) {
        }
        assertArrayEquals(new Type[] {Number.class}, e.getBounds());

        // Set no bounds. Should default to Object.class.
        e = new LateTypeVariable<Class>("E", Enum.class, (Type[])null);
        e.assignBounds();
        assertArrayEquals(new Type[] {Object.class}, e.getBounds());
    }
}
