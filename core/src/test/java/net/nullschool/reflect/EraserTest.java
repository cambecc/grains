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

import static org.junit.Assert.*;


/**
 * 2013-05-02<p/>
 *
 * @author Cameron Beccario
 */
public class EraserTest {

    @Test
    public void test_eraser() {
        Eraser eraser = Eraser.INSTANCE;

        assertSame(Object.class, eraser.apply(Object.class));
        assertSame(Object[].class, eraser.apply(Object[].class));
        assertSame(Set[].class, eraser.apply(new JavaToken<Set<?>[]>(){}.asGenericArrayType()));
        assertSame(Object.class, eraser.apply(new LateWildcardType("?")));
        assertSame(Object.class, eraser.apply(new LateWildcardType("? super", Comparable.class)));
        assertSame(Comparable.class, eraser.apply(new LateWildcardType("? extends", Comparable.class)));
        assertSame(Map.class, eraser.apply(new LateWildcardType("? extends", Map.class, HashMap.class)));
        assertSame(Set.class, eraser.apply(new JavaToken<Set<Integer>>(){}.asParameterizedType()));
        assertSame(Object.class, eraser.apply(new LateTypeVariable<Class>("E", Set.class)));
        assertSame(Map.class, eraser.apply(new LateTypeVariable<Class>("E", Set.class, Map.class)));
        assertSame(Map.class, eraser.apply(new LateTypeVariable<Class>("E", Set.class, Map.class, HashMap.class)));

        assertSame(Object.class, eraser.apply((Type)Object.class));
        assertSame(Object[].class, eraser.apply((Type)Object[].class));
        assertSame(Set[].class, eraser.apply(new JavaToken<Set<?>[]>(){}.asType()));
        assertSame(Object.class, eraser.apply((Type)new LateWildcardType("?")));
        assertSame(Object.class, eraser.apply((Type)new LateWildcardType("? super", Comparable.class)));
        assertSame(Comparable.class, eraser.apply((Type)new LateWildcardType("? extends", Comparable.class)));
        assertSame(Map.class, eraser.apply((Type)new LateWildcardType("? extends", Map.class, HashMap.class)));
        assertSame(Set.class, eraser.apply(new JavaToken<Set<Integer>>(){}.asType()));
        assertSame(Object.class, eraser.apply((Type)new LateTypeVariable<Class>("E", Set.class)));
        assertSame(Map.class, eraser.apply((Type)new LateTypeVariable<Class>("E", Set.class, Map.class)));
        assertSame(Map.class, eraser.apply((Type)new LateTypeVariable<Class>("E", Set.class, Map.class, HashMap.class)));

        assertNull(eraser.apply((Type)null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_eraser_unknown() {
        Eraser.INSTANCE.apply(new Type(){});
    }
}
