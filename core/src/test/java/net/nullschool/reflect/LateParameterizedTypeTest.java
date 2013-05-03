package net.nullschool.reflect;

import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.*;
import java.nio.file.FileVisitResult;
import java.util.*;

import static org.junit.Assert.*;


/**
 * 2013-03-24<p/>
 *
 * @author Cameron Beccario
 */
public final class LateParameterizedTypeTest {

    enum IgnoreFlag {
        ToString,
    }

    private static void compare(ParameterizedType expected, ParameterizedType actual, IgnoreFlag... flags) {
        // Compare actual against expected, and then a copy of expected against expected.
        for (ParameterizedType lpc : Arrays.asList(actual, LateParameterizedType.copyOf(actual))) {
            assertEquals(expected.getRawType(), lpc.getRawType());
            assertEquals(expected.getOwnerType(), lpc.getOwnerType());
            assertArrayEquals(expected.getActualTypeArguments(), lpc.getActualTypeArguments());
            assertTrue(expected.equals(lpc));
            assertTrue(lpc.equals(expected));
            assertEquals(expected.hashCode(), lpc.hashCode());
            if (!Arrays.asList(flags).contains(IgnoreFlag.ToString)) {
                assertEquals(expected.toString(), lpc.toString());
            }
        }
    }


    @Test
    public void test_trivial_comparison_with_standard_reflection() {
        compare(
            new JavaToken<List<Object>>(){}.asParameterizedType(),
            new LateParameterizedType(List.class, null, Object.class));
        compare(
            new JavaToken<List<List<Object>>>(){}.asParameterizedType(),
            new LateParameterizedType(List.class, null, new LateParameterizedType(List.class, null, Object.class)));
        compare(
            new JavaToken<Map<String, Object>>(){}.asParameterizedType(),
            new LateParameterizedType(Map.class, null, String.class, Object.class));
    }

    @Test
    public void test_nested_type_comparison_with_standard_reflection() {
        // Java BUG: Oracle's implementation returns the wrong toString value in this scenario:
        //     java.util.Map.java.util.Map$Entry<java.lang.Long, java.lang.Byte>
        compare(
            new JavaToken<Map.Entry<Long, Byte>>(){}.asParameterizedType(),
            new LateParameterizedType(Map.Entry.class, Map.class, Long.class, Byte.class),
            IgnoreFlag.ToString);
    }

    @Test
    public void test_inner_class_comparison_with_standard_reflection() {

        compare(
            new JavaToken<Outer<Byte>.Inner0>(){}.asParameterizedType(),
            new LateParameterizedType(
                Outer.Inner0.class,
                new LateParameterizedType(Outer.class, null, Byte.class)));

        compare(
            new JavaToken<Outer<Byte>.Inner1<Long>>(){}.asParameterizedType(),
            new LateParameterizedType(
                Outer.Inner1.class,
                new LateParameterizedType(Outer.class, null, Byte.class),
                Long.class));
    }

    @Test
    public void test_local_class_comparison_with_standard_reflection() {
        @SuppressWarnings("UnusedDeclaration")
        class Local<T> {
        }
        compare(
            new JavaToken<Local<Byte>>(){}.asParameterizedType(),
            new LateParameterizedType(Local.class, null, Byte.class));
    }

    @Test(expected = NullPointerException.class)
    public void test_bad_construction_1() {
        new LateParameterizedType(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void test_bad_construction_2() {
        new LateParameterizedType(List.class, null, (Type[])null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_bad_construction_3() {
        new LateParameterizedType(List.class, null, Integer.class, String.class);
    }

    @Test
    public void test_nested_print() {
        assertEquals(
            "net.nullschool.reflect.Outer<java.lang.Short>.Inner1<java.lang.Integer>.Inner2<java.lang.Long>",
            new TypeToken<Outer<Short>.Inner1<Integer>.Inner2<Long>>(){}.asType().toString());
        assertEquals(
            "net.nullschool.reflect.Outer.$Inner3<java.lang.Integer>",
            new TypeToken<Outer.$Inner3<Integer>>(){}.asType().toString());
        assertEquals(
            "net.nullschool.reflect.Outer.$Inner3<java.lang.Integer>.$Inner4<java.lang.Long>",
            new TypeToken<Outer.$Inner3<Integer>.$Inner4<Long>>(){}.asType().toString());
    }

    @Test
    public void test_resolve() throws Exception {
        LateParameterizedType lpc = new LateParameterizedType(Map.class, null, String.class, Integer.class);

        Method putMethod = Map.class.getMethod("put", Object.class, Object.class);
        assertEquals(Integer.class, lpc.resolve(putMethod.getGenericReturnType()));
        assertArrayEquals(new Type[] {String.class, Integer.class}, lpc.resolve(putMethod.getGenericParameterTypes()));
        assertArrayEquals(new Type[] {String.class, Integer.class}, lpc.resolve(Map.class.getTypeParameters()));
        assertEquals(Long.class, lpc.resolve(Long.class));

        Method putAllMethod = Map.class.getMethod("putAll", Map.class);
        Type mapParam = putAllMethod.getGenericParameterTypes()[0];
        assertEquals(new TypeToken<Map<? extends String, ? extends Integer>>(){}.asType(), lpc.resolve(mapParam));

        assertEquals(
            new LateWildcardType("? super", String.class),
            lpc.resolve(new LateWildcardType("? super", new LateTypeVariable<Class>("K", Map.class))));
    }

    @Test
    public void test_recursive_resolve() throws Exception {
        LateParameterizedType lpc = new LateParameterizedType(Map.class, null, String.class, Integer.class);
        assertEquals(lpc, lpc.resolve(new LateParameterizedType(Map.class, null, Map.class.getTypeParameters())));
    }

    @Test
    public void test_resolve_uninstantiated_type() throws Exception {
        TypeVariable<?>[] typeVars = Map.class.getTypeParameters();
        LateParameterizedType lpc = new LateParameterizedType(Map.class, null, Map.class.getTypeParameters());
        Method m = Map.class.getMethod("put", Object.class, Object.class);
        assertEquals(typeVars[1], lpc.resolve(m.getGenericReturnType()));
        assertArrayEquals(typeVars, lpc.resolve(m.getGenericParameterTypes()));
    }

    @Test
    public void test_resolve_partially_instantiated_type() throws Exception {
        TypeVariable<?>[] typeVars = Map.class.getTypeParameters();
        LateParameterizedType lpc = new LateParameterizedType(Map.class, null, String.class, typeVars[1]);
        Method m = Map.class.getMethod("put", Object.class, Object.class);
        assertEquals(typeVars[1], lpc.resolve(m.getGenericReturnType()));
        assertArrayEquals(new Type[] {String.class, typeVars[1]}, lpc.resolve(m.getGenericParameterTypes()));
    }

    @Test
    public void test_resolve_causes_concrete_array_instantiation() throws Exception {
        // First create a context where E -> Integer
        TypeVariable<?> E = List.class.getTypeParameters()[0];
        LateParameterizedType lpc = new LateParameterizedType(List.class, null, Integer.class);

        // Now create a bunch of array types based on E
        LateGenericArrayType arrayOfE = new LateGenericArrayType(E);
        LateGenericArrayType arrayOfArrayOfE = new LateGenericArrayType(arrayOfE);
        LateParameterizedType setOfArrayOfE = new LateParameterizedType(Set.class, null, arrayOfE);
        LateParameterizedType setOfArrayOfArrayOfE = new LateParameterizedType(Set.class, null, arrayOfArrayOfE);
        LateGenericArrayType arrayOfSetOfArrayOfE = new LateGenericArrayType(setOfArrayOfE);

        // Ensure that when E resolves to Integer, it instantiates concrete array types when possible.
        assertEquals(Integer[].class, lpc.resolve(arrayOfE));
        assertEquals(Integer[][].class, lpc.resolve(arrayOfArrayOfE));
        assertEquals(
            new LateParameterizedType(Set.class, null, Integer[].class),
            lpc.resolve(setOfArrayOfE));
        assertEquals(
            new LateParameterizedType(Set.class, null, Integer[][].class),
            lpc.resolve(setOfArrayOfArrayOfE));
        assertEquals(
            new LateGenericArrayType(new LateParameterizedType(Set.class, null, Integer[].class)),
            lpc.resolve(arrayOfSetOfArrayOfE));
    }

    @SuppressWarnings("UnusedDeclaration")
    private static class Foo<T> {
        public <T> T bar(T t) { return t; }
    }

    @Test
    public void test_generic_method_shadows_enclosing_type_parameter() throws Exception {
        LateParameterizedType lpc = new LateParameterizedType(Foo.class, null, Integer.class);
        Method m = Foo.class.getMethod("bar", Object.class);
        assertEquals(m.getGenericReturnType(), lpc.resolve(m.getGenericReturnType()));
        assertArrayEquals(m.getGenericParameterTypes(), lpc.resolve(m.getGenericParameterTypes()));
    }

    @Test
    public void test_superclass() {
        LateParameterizedType lpc = new LateParameterizedType(ArrayList.class, null, Long.class);
        compare(
            new JavaToken<AbstractList<Long>>(){}.asParameterizedType(),
            lpc = (LateParameterizedType)lpc.getSuperclass());
        compare(
            new JavaToken<AbstractCollection<Long>>(){}.asParameterizedType(),
            lpc = (LateParameterizedType)lpc.getSuperclass());
        assertSame(Object.class, lpc.getSuperclass());

        LateParameterizedType listOfLong = new LateParameterizedType(List.class, null, Long.class);
        assertNull(listOfLong.getSuperclass());
    }

    @Test
    public void test_interfaces() {
        LateParameterizedType lpc = new LateParameterizedType(ArrayList.class, null, Long.class);
        Type[] result = lpc.getInterfaces();
        assertEquals(4, result.length);
        compare(new JavaToken<List<Long>>(){}.asParameterizedType(), lpc = (LateParameterizedType)result[0]);
        assertSame(RandomAccess.class, result[1]);
        assertSame(Cloneable.class, result[2]);
        assertSame(Serializable.class, result[3]);

        result = lpc.getInterfaces();
        assertEquals(1, result.length);
        compare(new JavaToken<Collection<Long>>(){}.asParameterizedType(), lpc = (LateParameterizedType)result[0]);

        result = lpc.getInterfaces();
        assertEquals(1, result.length);
        compare(new JavaToken<Iterable<Long>>(){}.asParameterizedType(), lpc = (LateParameterizedType)result[0]);

        result = lpc.getInterfaces();
        assertEquals(0, result.length);
    }

    @Test
    public void test_enum_super() throws Exception {
        LateParameterizedType lpc =
            LateParameterizedType.copyOf((ParameterizedType)FileVisitResult.class.getGenericSuperclass());
        Method m = lpc.getRawType().getMethod("getDeclaringClass");
        assertEquals(
            new LateParameterizedType(Class.class, null, FileVisitResult.class),
            lpc.resolve(m.getGenericReturnType()));
    }

    @Test(expected = NullPointerException.class)
    public void test_null_type_argument() {
        new LateParameterizedType(Map.class, null, String.class, null);
    }
}
