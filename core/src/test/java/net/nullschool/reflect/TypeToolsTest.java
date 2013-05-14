/*
 * Copyright 2013 Cameron Beccario
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.nullschool.reflect;

import org.junit.Test;

import java.lang.reflect.Type;
import java.util.*;

import static net.nullschool.reflect.TypeTools.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
        // classes
        assertSame(int.class, erase(int.class));
        assertSame(Object.class, erase(Object.class));
        assertSame(Object[].class, erase(Object[].class));

        // generic arrays
        assertSame(Set[].class, erase(new JavaToken<Set<?>[]>(){}.asGenericArrayType()));

        // wildcards
        assertSame(Object.class, erase(new JavaToken<Set<?>>(){}.asWildcardType()));
        assertSame(Object.class, erase(new JavaToken<Set<? super Comparable>>(){}.asWildcardType()));
        assertSame(Comparable.class, erase(new JavaToken<Set<? extends Comparable>>(){}.asWildcardType()));
        assertSame(Map.class, erase(new LateWildcardType("? extends", Map.class, HashMap.class)));

        // parameterized types
        assertSame(Set.class, erase(new JavaToken<Set<Integer>>(){}.asParameterizedType()));
        assertSame(Set.class, erase(new JavaToken<Set<?>>(){}.asParameterizedType()));

        // type variables
        assertSame(Object.class, erase(new LateTypeVariable<Class>("E", Set.class)));
        assertSame(Map.class, erase(new LateTypeVariable<Class>("E", Set.class, Map.class)));
        assertSame(Map.class, erase(new LateTypeVariable<Class>("E", Set.class, Map.class, HashMap.class)));

        // other
        assertNull(erase(null));
        try { erase(new Type(){}); fail(); } catch (IllegalArgumentException expected) {}
    }

    @Test
    public void test_print() {
        assertEquals("java.util.List", TypeTools.toString(List.class));
        assertEquals("java.util.List[]", TypeTools.toString(List[].class));
        assertEquals(
            "java.util.List<? extends java.lang.Integer>",
            TypeTools.toString(new JavaToken<List<? extends Integer>>(){}.asType()));
        assertEquals(
            "java.util.List<? extends java.lang.Integer>[][]",
            TypeTools.toString(new JavaToken<List<? extends Integer>[][]>(){}.asType()));
        assertEquals("java.util.Map.Entry", TypeTools.toString(Map.Entry.class));
        assertEquals("int", TypeTools.toString(int.class));
        assertEquals("int[]", TypeTools.toString(int[].class));
    }

    private static class PrependTildePrinter extends AbstractTypePrinter {
        @Override public TypePrinter print(Class<?> clazz) {
            sb.append('~').append(clazz.getSimpleName());
            return this;
        }
    }

    @Test
    public void test_print_with_custom_printer() {
        assertEquals("~List", TypeTools.toString(List.class, new PrependTildePrinter()));
        assertEquals("~List[]", TypeTools.toString(List[].class, new PrependTildePrinter()));
        assertEquals(
            "~List<? extends ~Integer>",
            TypeTools.toString(new JavaToken<List<? extends Integer>>(){}.asType(), new PrependTildePrinter()));
        assertEquals(
            "~List<? extends ~Integer>[][]",
            TypeTools.toString(new JavaToken<List<? extends Integer>[][]>(){}.asType(), new PrependTildePrinter()));
        assertEquals("~Map.Entry", TypeTools.toString(Map.Entry.class, new PrependTildePrinter()));
        assertEquals("~int", TypeTools.toString(int.class, new PrependTildePrinter()));
        assertEquals("~int[]", TypeTools.toString(int[].class, new PrependTildePrinter()));
    }

    @Test
    public void test_apply() {
        @SuppressWarnings("unchecked") TypeOperator<Type> operator = (TypeOperator<Type>)mock(TypeOperator.class);
        when(operator.apply((Type)String.class)).thenReturn(Character.class);
        when(operator.apply((Type)Integer.class)).thenReturn(Byte.class);

        Type[] result = apply(operator, new Type[] {String.class, Integer.class});
        assertArrayEquals(new Type[] {Character.class, Byte.class}, result);
    }

    @Test
    public void test_copyOf() {
        assertEquals(Class.class, copyOf(Object.class).getClass());
        assertEquals(LateParameterizedType.class, copyOf(new JavaToken<Set<Byte>>(){}.asParameterizedType()).getClass());
        assertEquals(LateGenericArrayType.class, copyOf(new JavaToken<Set<Byte>[]>(){}.asGenericArrayType()).getClass());
        assertEquals(LateWildcardType.class, copyOf(new JavaToken<Set<?>>(){}.asWildcardType()).getClass());
    }
}
