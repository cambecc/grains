package net.nullschool.reflect;

import org.junit.Test;

import java.util.*;

import static net.nullschool.reflect.TypeTools.*;
import static org.junit.Assert.*;


/**
 * 2013-03-24<p/>
 *
 * @author Cameron Beccario
 */
public class TypeToolsTest {

    @Test
    public void test_compare() {
        assertEquals(-1, (Object)compare(List.class, Collection.class));
        assertEquals(-1, (Object)compare(ArrayList.class, List.class));
        assertEquals(0, (Object)compare(List.class, List.class));
        assertEquals(0, (Object)compare(ArrayList.class, ArrayList.class));
        assertEquals(1, (Object)compare(Collection.class, List.class));
        assertEquals(1, (Object)compare(List.class, ArrayList.class));
        assertEquals(null, compare(List.class, Set.class));
    }

    @Test
    public void test_primitive_compare() {
        assertEquals(0, (Object)compare(int.class, int.class));
        assertEquals(null, compare(int.class, double.class));
        assertEquals(null, compare(int.class, Integer.class));
        assertEquals(null, compare(int.class, Number.class));
    }

    @Test
    public void test_buildArrayType() {
        assertSame(int[].class, buildArrayType(int.class));
        assertSame(int[][].class, buildArrayType(int[].class));
        assertSame(Integer[].class, buildArrayType(Integer.class));
        assertSame(Map[].class, buildArrayType(Map.class));
    }

    @Test
    public void test_erasure() {
        assertSame(int.class, erase(int.class));
        assertSame(Integer.class, erase(Integer.class));
        assertSame(List.class, erase(new TypeToken<List<?>>(){}.asType()));
        assertSame(List.class, erase(new TypeToken<List<? extends Integer>>(){}.asType()));
        assertSame(Integer.class, erase(new TypeToken<List<? extends Integer>>(){}.asWildcardType()));
        assertSame(Object.class, erase(new TypeToken<List<? super Number>>(){}.asWildcardType()));
        assertNull(erase(null));
    }

    @Test
    public void test_print() {
        assertEquals("java.util.List", print(List.class));
        assertEquals("java.util.List[]", print(List[].class));
        assertEquals(
            "java.util.List<? extends java.lang.Integer>",
            print(new TypeToken<List<? extends Integer>>(){}.asType()));
        assertEquals(
            "java.util.List<? extends java.lang.Integer>[][]",
            print(new TypeToken<List<? extends Integer>[][]>(){}.asType()));
        assertEquals("java.util.Map.Entry", print(Map.Entry.class));
        assertEquals("int", print(int.class));
        assertEquals("int[]", print(int[].class));
    }

    private static class SimpleNamePrinter implements TypePrinter {
        private final StringBuilder sb = new StringBuilder();
        @Override public TypePrinter print(char c) { sb.append(c); return this; }
        @Override public TypePrinter print(String s) { sb.append(s); return this; }
        @Override public TypePrinter print(Class<?> clazz) { sb.append(clazz.getSimpleName()); return this; }
        @Override public String toString() { return sb.toString(); }
    }

    @Test
    public void test_print_with_custom_printer() {
        assertEquals("List", print(List.class, new SimpleNamePrinter()));
        assertEquals("List[]", print(List[].class, new SimpleNamePrinter()));
        assertEquals(
            "List<? extends Integer>",
            print(new TypeToken<List<? extends Integer>>(){}.asType(), new SimpleNamePrinter()));
        assertEquals(
            "List<? extends Integer>[][]",
            print(new TypeToken<List<? extends Integer>[][]>(){}.asType(), new SimpleNamePrinter()));
        assertEquals("Map.Entry", print(Map.Entry.class, new SimpleNamePrinter()));
        assertEquals("int", print(int.class, new SimpleNamePrinter()));
        assertEquals("int[]", print(int[].class, new SimpleNamePrinter()));
    }
}
