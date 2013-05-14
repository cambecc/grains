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

import java.io.Serializable;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.*;


/**
 * 2013-03-24<p/>
 *
 * @author Cameron Beccario
 */
public final class LateWildcardTypeTest {

    private static void compare(WildcardType expected, WildcardType actual) {
        // Compare actual against expected, and then a copy of expected against expected.
        for (WildcardType wt : Arrays.asList(actual, LateWildcardType.copyOf(expected))) {
            assertArrayEquals(expected.getUpperBounds(), wt.getUpperBounds());
            assertArrayEquals(expected.getLowerBounds(), wt.getLowerBounds());
            assertTrue(expected.equals(wt));
            assertTrue(wt.equals(expected));
            assertEquals(expected.hashCode(), wt.hashCode());
            assertEquals(expected.toString(), wt.toString());
        }
    }


    @Test
    public void test_trivial_comparison_with_java_reflection() {

        compare(
            new JavaToken<Class<?>>(){}.asWildcardType(),
            new LateWildcardType("?"));

        compare(
            new JavaToken<Class<?>>(){}.asWildcardType(),
            new LateWildcardType("? extends", Object.class));

        compare(
            new JavaToken<Class<? super Object>>(){}.asWildcardType(),
            new LateWildcardType("? super", Object.class));
    }

    @Test
    public void test_upper_bounds_comparison_with_java_reflection() {

        compare(
            new JavaToken<Class<? extends Number>>(){}.asWildcardType(),
            new LateWildcardType("? extends", Number.class));

        compare(
            new JavaToken<Class<? extends Set>>(){}.asWildcardType(),
            new LateWildcardType("? extends", Set.class));

        compare(
            new JavaToken<Class<? extends Set<?>>>(){}.asWildcardType(),
            new LateWildcardType("? extends", new JavaToken<Set<?>>(){}.asParameterizedType()));
    }

    @Test
    public void test_lower_bounds_comparison_with_java_reflection() {

        compare(
            new JavaToken<Class<? super Number>>(){}.asWildcardType(),
            new LateWildcardType("? super", Number.class));

        compare(
            new JavaToken<Class<? super Set>>(){}.asWildcardType(),
            new LateWildcardType("? super", Set.class));

        compare(
            new JavaToken<Class<? super Set<?>>>(){}.asWildcardType(),
            new LateWildcardType("? super", new JavaToken<Set<?>>(){}.asParameterizedType()));
    }

    @Test
    public void test_extra_linguistic_wildcard_bounds() {
        // Though these wildcard types are not expressible in Java, their behavior should be unsurprising.
        assertEquals(
            "? extends java.io.Serializable & java.lang.Comparable",
            new LateWildcardType("? extends", Serializable.class, Comparable.class).toString());
        assertEquals(
            "? extends java.lang.Class & java.lang.Class",
            new LateWildcardType("? extends", Class.class, Class.class).toString());
        assertEquals(
            "? super java.io.Serializable & java.lang.Comparable",
            new LateWildcardType("? super", Serializable.class, Comparable.class).toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_bad_wildcard_bound() {
        new LateWildcardType("? extends", new LateWildcardType("?"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_bad_null_bound() {
        new LateWildcardType("? extends", Object.class, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_no_lower_bounds() {
        new LateWildcardType("? extends");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_no_upper_bounds() {
        new LateWildcardType("? super");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_unexpected_bounds() {
        new LateWildcardType("?", Number.class);
    }

    @Test
    public void test_bad_construction() {
        try { new LateWildcardType(null); fail(); } catch (NullPointerException expected) {}
        try { new LateWildcardType("foo"); fail(); } catch (IllegalArgumentException expected) {}
        try { new LateWildcardType("? super", (Type[])null); fail(); } catch (NullPointerException expected) {}
        try { new LateWildcardType("? extends", (Type[])null); fail(); } catch (NullPointerException expected) {}
        try { new LateWildcardType("?", (Type[])null); fail(); } catch (NullPointerException expected) {}
    }
}
