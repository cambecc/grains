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

import java.lang.reflect.*;
import java.util.Set;

import static org.junit.Assert.*;


/**
 * 2013-05-02<p/>
 *
 * @author Cameron Beccario
 */
public class TypeTokenTest {

    @Test
    public void test_class() {
        TypeToken<String> token = new TypeToken<String>(){};

        assertSame(String.class, token.asType());
        assertNull(token.asNull());
        assertEquals("TypeToken<java.lang.String>", token.toString());
    }

    @Test
    public void test_parameterized_type() {
        TypeToken<Set<String>> token = new TypeToken<Set<String>>(){};
        ParameterizedType expected = new JavaToken<Set<String>>(){}.asParameterizedType();

        assertEquals(expected, token.asType());
        assertNull(token.asNull());
        assertEquals("TypeToken<java.util.Set<java.lang.String>>", token.toString());
    }

    @Test
    public void test_generic_array() {
        TypeToken<Set<? extends Long>[]> token = new TypeToken<Set<? extends Long>[]>(){};
        GenericArrayType expected = new JavaToken<Set<? extends Long>[]>(){}.asGenericArrayType();

        assertEquals(expected, token.asType());
        assertNull(token.asNull());
        assertEquals("TypeToken<java.util.Set<? extends java.lang.Long>[]>", token.toString());
    }

    private static class Foo<T extends Number> {
        final TypeToken<T> token = new TypeToken<T>(){};
    }

    @Test
    public void test_type_variable() {
        TypeToken<?> token = new Foo().token;
        TypeVariable<?> expected = new LateTypeVariable<Class>("T", Foo.class, Number.class);

        assertEquals(expected, token.asType());
        assertEquals("TypeToken<T>", token.toString());
    }

    @Test
    public void test_equality() {
        TypeToken<Set<String>> setOfString1 = new TypeToken<Set<String>>(){};
        TypeToken<Set<String>> setOfString2 = new TypeToken<Set<String>>(){};
        TypeToken<Set<Long>> setOfLong = new TypeToken<Set<Long>>(){};

        assertEquals(setOfString1, setOfString2);
        assertEquals(setOfString1.hashCode(), setOfString2.hashCode());

        assertNotEquals(setOfString1, setOfLong);
        assertNotEquals(setOfString1.hashCode(), setOfLong.hashCode());
        assertNotEquals(setOfString1, new Object());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_no_type_argument() {
        new TypeToken(){};
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_indirect_extension() {
        class FooToken<T> extends TypeToken<T> {}
        new FooToken<Integer>(){};
    }

    static int invoke(Integer i) { fail(); return i; }
    static long invoke(Long l) { return l == null ? 42 : 42; }

    @Test
    public void test_typed_null() {
        TypeToken<Long> token = new TypeToken<Long>(){};
        assertEquals(42, invoke(token.asNull()));
    }
}
