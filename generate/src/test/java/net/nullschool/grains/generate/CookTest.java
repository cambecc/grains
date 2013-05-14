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

package net.nullschool.grains.generate;

import net.nullschool.reflect.TypeToken;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


/**
 * 2013-05-11<p/>
 *
 * @author Cameron Beccario
 */
public class CookTest {

    interface ComparatorMap<K extends Comparator<K>, V extends Comparator<V>> extends Map<K, V> {
    }

    interface NumberMap<K extends Number, V extends K> extends Map<K, V> {
    }

    interface NumbersMap<K extends Number, V extends List<K>> extends Map<K, V> {
    }

    interface ComplexList1<E extends ComplexList1> extends List<E> {
    }

    interface ComplexList2<E extends List<? extends E>> extends List<E> {
    }

    interface ComplexMap1<K extends List<V>, V extends List<K>> extends Map<K, V> {
    }

    interface ComplexMap2<K extends List<? extends V>, V extends List<? extends K>> extends Map<K, V> {
    }

    interface ComplexMap5<K extends Number, V extends Map<? extends K, ? extends List<K>>> extends Map<K, V> {
    }

    interface ComplexMap6<K extends Number, V extends Map<? super K, ? super List<K>>> extends Map<K, V> {
    }

    private class Outer<T extends Number> {
        private class Inner0 {}
        private abstract class Inner1<U extends T> implements List<U> {}
    }

    @Test
    public void test_cook() {
        Cook cook = new Cook();

        assertEquals(
            new TypeToken<Integer>(){}.asType(),
            cook.apply(new TypeToken<Integer>(){}.asType()));
        assertEquals(
            new TypeToken<Integer[]>(){}.asType(),
            cook.apply(new TypeToken<Integer[]>(){}.asType()));
        assertEquals(
            new TypeToken<int[]>(){}.asType(),
            cook.apply(new TypeToken<int[]>(){}.asType()));
        assertEquals(
            new TypeToken<List<?>>(){}.asType(),
            cook.apply(new TypeToken<List>(){}.asType()));
        assertEquals(
            new TypeToken<List<?>[]>(){}.asType(),
            cook.apply(new TypeToken<List[]>(){}.asType()));
        assertEquals(
            new TypeToken<Enum<?>[]>(){}.asType(),
            cook.apply(new TypeToken<Enum<?>[]>(){}.asType()));
        assertEquals(
            new TypeToken<List<?>[][]>(){}.asType(),
            cook.apply(new TypeToken<List[][]>(){}.asType()));
        assertEquals(
            new TypeToken<Map<?, ?>>(){}.asType(),
            cook.apply(new TypeToken<Map>(){}.asType()));
        assertEquals(
            new TypeToken<Enum<? extends Enum>>(){}.asType(),
            cook.apply(new TypeToken<Enum>(){}.asType()));
        assertEquals(
            new TypeToken<EnumSet<? extends Enum>>(){}.asType(),
            cook.apply(new TypeToken<EnumSet>(){}.asType()));
        assertEquals(
            new TypeToken<ComparatorMap<? extends Comparator, ? extends Comparator>>(){}.asType(),
            cook.apply(new TypeToken<ComparatorMap>(){}.asType()));
        assertEquals(
            new TypeToken<NumberMap<? extends Number, ? extends Number>>(){}.asType(),
            cook.apply(new TypeToken<NumberMap>(){}.asType()));
        assertEquals(
            new TypeToken<NumbersMap<? extends Number, ? extends List<? extends Number>>>(){}.asType(),
            cook.apply(new TypeToken<NumbersMap>(){}.asType()));
        assertEquals(
            new TypeToken<ComplexList1<? extends ComplexList1>>(){}.asType(),
            cook.apply(new TypeToken<ComplexList1>(){}.asType()));
        assertEquals(
            new TypeToken<ComplexList2<? extends List<? extends List>>>(){}.asType(),
            cook.apply(new TypeToken<ComplexList2>(){}.asType()));
        assertEquals(
            new TypeToken<ComplexMap1<? extends List<? extends List>, ? extends List<? extends List>>>(){}.asType(),
            cook.apply(new TypeToken<ComplexMap1>(){}.asType()));
        assertEquals(
            new TypeToken<ComplexMap2<? extends List<? extends List>, ? extends List<? extends List>>>(){}.asType(),
            cook.apply(new TypeToken<ComplexMap2>(){}.asType()));
        assertEquals(
            new TypeToken<ComplexMap5<? extends Number, ? extends Map<? extends Number, ? extends List<? extends Number>>>>(){}.asType(),
            cook.apply(new TypeToken<ComplexMap5>(){}.asType()));
        assertEquals(
            new TypeToken<ComplexMap6<? extends Number, ? extends Map<? super Number, ? super List<? extends Number>>>>(){}.asType(),
            cook.apply(new TypeToken<ComplexMap6>(){}.asType()));
        assertEquals(
            new TypeToken<Outer<? extends Number>.Inner0>(){}.asType(),
            cook.apply(new TypeToken<Outer.Inner0>(){}.asType()));
        assertEquals(
            new TypeToken<Outer<? extends Number>.Inner1<? extends Number>>(){}.asType(),
            cook.apply(new TypeToken<Outer.Inner1>(){}.asType()));
    }
}
