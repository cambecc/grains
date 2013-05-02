package net.nullschool.reflect;

import org.junit.Test;

import java.lang.reflect.GenericArrayType;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 2013-03-24<p/>
 *
 * @author Cameron Beccario
 */
public final class LateGenericArrayTypeTest {

    private static void compare(GenericArrayType expected, GenericArrayType actual) {
        // Compare actual against expected, and then a copy of expected against expected.
        for (GenericArrayType gat : Arrays.asList(actual, LateGenericArrayType.copyOf(expected))) {
            assertEquals(expected.getGenericComponentType(), gat.getGenericComponentType());
            assertTrue(expected.equals(gat));
            assertTrue(gat.equals(expected));
            assertEquals(expected.hashCode(), gat.hashCode());
            assertEquals(expected.toString(), gat.toString());
        }
    }


    @Test
    public void test_concrete_array_comparison_with_java_7_reflection() {
        assertSame(Object[].class, new JavaToken<Object[]>(){}.asType());
        assertSame(Object[][].class, new JavaToken<Object[][]>(){}.asType());
        assertSame(int[].class, new JavaToken<int[]>(){}.asType());
        assertSame(int[][].class, new JavaToken<int[][]>(){}.asType());
    }

    @Test
    public void test_comparison_with_java_reflection() {
        compare(
            new JavaToken<List<Byte>[]>(){}.asGenericArrayType(),
            new LateGenericArrayType(new JavaToken<List<Byte>>(){}.asParameterizedType()));
        compare(
            new JavaToken<List<Byte>[][]>(){}.asGenericArrayType(),
            new LateGenericArrayType(new JavaToken<List<Byte>[]>(){}.asGenericArrayType()));
        compare(
            new JavaToken<List<Byte>[][]>(){}.asGenericArrayType(),
            new LateGenericArrayType(new JavaToken<List<Byte>[]>(){}.asGenericArrayType()));
    }

    @Test(expected = NullPointerException.class)
    public void test_bad_construction() {
        new LateGenericArrayType(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_bad_class_component() {
        new LateGenericArrayType(String.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_bad_wildcard_component() {
        new LateGenericArrayType(new JavaToken<Class<? extends Number>>(){}.asWildcardType());
    }
}
