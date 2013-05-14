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

import net.nullschool.collect.ConstCollection;
import net.nullschool.collect.ConstList;
import net.nullschool.collect.ConstMap;
import net.nullschool.collect.ConstSet;
import net.nullschool.grains.*;
import net.nullschool.grains.generate.model.Compound;
import net.nullschool.grains.generate.model.PartGrain;
import net.nullschool.reflect.*;
import org.junit.Test;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static org.junit.Assert.*;


/**
* 2013-03-28<p/>
*
* @author Cameron Beccario
*/
public class ImmutifyTest {

    private TypeTable typeTable = new TypeTable(new NamingPolicy(), DefaultTypePolicy.INSTANCE);

    private Type immutify(Type type) {
        return new Immutify(typeTable).apply(new Cook().apply(type));
    }

    enum Color {red, green}

    @Test
    public void test_immutify_known_immutable_types() {
        assertSame(boolean.class, immutify(boolean.class));
        assertSame(byte.class, immutify(byte.class));
        assertSame(short.class, immutify(short.class));
        assertSame(int.class, immutify(int.class));
        assertSame(long.class, immutify(long.class));
        assertSame(float.class, immutify(float.class));
        assertSame(double.class, immutify(double.class));
        assertSame(char.class, immutify(char.class));

        assertSame(Boolean.class, immutify(Boolean.class));
        assertSame(Byte.class, immutify(Byte.class));
        assertSame(Short.class, immutify(Short.class));
        assertSame(Integer.class, immutify(Integer.class));
        assertSame(Long.class, immutify(Long.class));
        assertSame(BigInteger.class, immutify(BigInteger.class));
        assertSame(BigDecimal.class, immutify(BigDecimal.class));
        assertSame(Float.class, immutify(Float.class));
        assertSame(Double.class, immutify(Double.class));
        assertSame(Character.class, immutify(Character.class));
        assertSame(String.class, immutify(String.class));
    }

    @Test
    public void test_arrays_not_immutable() {
        try { immutify(char[].class); fail(); } catch (IllegalArgumentException expected) {}
        try { immutify(String[].class); fail(); } catch (IllegalArgumentException expected) {}
    }

    @Test
    public void test_immutify_enum() {
        assertEquals(new TypeToken<Enum<? extends Enum>>(){}.asType(), immutify(Enum.class));  // kinda weird.
        assertSame(Color.class, immutify(Color.class));
    }

    @GrainSchema
    interface Foo {
    }

    @Test
    public void test_immutify_grain() {
        assertSame(Grain.class, immutify(Grain.class));
        assertSame(PartGrain.class, immutify(Compound.Part.class));
        assertSame(PartGrain.class, immutify(PartGrain.class));
        assertEquals("class net.nullschool.grains.generate.FooGrain", immutify(Foo.class).toString());
    }

    @Test
    public void test_grain_array_not_immutable() {
        try { immutify(PartGrain[].class); fail(); } catch (IllegalArgumentException expected) {}
    }

    interface NestedMap<K, V> extends ConstMap<K, V> {
    }

    interface ComplexMap<K extends Grain, V extends K> extends ConstMap<K, V> {
    }

    @Test
    public void test_immutify_generic_types() {
        assertEquals(
            new TypeToken<ConstCollection<Integer>>(){}.asType(),
            immutify(new TypeToken<Collection<Integer>>(){}.asType()));
        assertEquals(
            new TypeToken<ConstList<Integer>>(){}.asType(),
            immutify(new TypeToken<List<Integer>>(){}.asType()));
        assertEquals(
            new TypeToken<ConstSet<Integer>>(){}.asType(),
            immutify(new TypeToken<Set<Integer>>(){}.asType()));
        assertEquals(
            new TypeToken<ConstMap<String, UUID>>(){}.asType(),
            immutify(new TypeToken<Map<String, UUID>>(){}.asType()));

        assertEquals(
            new TypeToken<ConstList<ConstList<Integer>>>(){}.asType(),
            immutify(new TypeToken<List<List<Integer>>>(){}.asType()));
        assertEquals(
            new TypeToken<ConstMap<ConstList<UUID>, ConstSet<PartGrain>>>(){}.asType(),
            immutify(new TypeToken<Map<List<UUID>, Set<Compound.Part>>>(){}.asType()));

        assertEquals(
            new TypeToken<ConstList<? extends Integer>>(){}.asType(),
            immutify(new TypeToken<List<? extends Integer>>(){}.asType()));
        assertEquals(
            new TypeToken<ConstList<? extends ConstList<? extends Integer>>>(){}.asType(),
            immutify(new TypeToken<List<? extends List<? extends Integer>>>(){}.asType()));
        assertEquals(
            new TypeToken<ConstMap<? extends ConstList<? extends UUID>, ConstSet<? extends PartGrain>>>(){}.asType(),
            immutify(new TypeToken<Map<? extends List<? extends UUID>, Set<? extends Compound.Part>>>(){}.asType()));

        assertEquals(
            new TypeToken<ConstList<Integer>>(){}.asType(),
            immutify(new TypeToken<ConstList<Integer>>(){}.asType()));
        assertEquals(
            new TypeToken<ConstList<ConstList<Integer>>>(){}.asType(),
            immutify(new TypeToken<ConstList<ConstList<Integer>>>(){}.asType()));

        assertEquals(
            new TypeToken<NestedMap<String, Integer>>(){}.asType(),
            immutify(new TypeToken<NestedMap<String, Integer>>(){}.asType()));
        assertEquals(
            new TypeToken<ComplexMap<? extends Grain, ? extends Grain>>(){}.asType(),
            immutify(ComplexMap.class));  // this raw type when cooked is immutable
    }

    @Test
    public void test_immutify_fails_on_non_immutable_types() {

        try { immutify(Object.class);     fail(); } catch (IllegalArgumentException expected) {}
        try { immutify(Number.class);     fail(); } catch (IllegalArgumentException expected) {}
        try { immutify(String[].class);   fail(); } catch (IllegalArgumentException expected) {}
        try { immutify(List.class);       fail(); } catch (IllegalArgumentException expected) {}
        try { immutify(Date.class);       fail(); } catch (IllegalArgumentException expected) {}

        // raw const collections are not immutable because their type arguments are implicitly Object
        try { immutify(ConstCollection.class);    fail(); } catch (IllegalArgumentException expected) {}
        try { immutify(ConstList.class);          fail(); } catch (IllegalArgumentException expected) {}
        try { immutify(ConstSet.class);           fail(); } catch (IllegalArgumentException expected) {}
        try { immutify(ConstMap.class);           fail(); } catch (IllegalArgumentException expected) {}

        try { immutify(new TypeToken<List<Object>>(){}.asType()); fail(); }
        catch (IllegalArgumentException expected) {}

        try { immutify(new TypeToken<List<List>>(){}.asType()); fail(); }
        catch (IllegalArgumentException expected) {}

        try { immutify(new TypeToken<List<ConstList>>(){}.asType()); fail(); }
        catch (IllegalArgumentException expected) {}

        try { immutify(new TypeToken<ConstList<ConstList>>(){}.asType()); fail(); }
        catch (IllegalArgumentException expected) {}

        try { immutify(new TypeToken<List<?>>(){}.asType()); fail(); }
        catch (IllegalArgumentException expected) {}

        try { immutify(new TypeToken<List<? extends Number>>(){}.asType()); fail(); }
        catch (IllegalArgumentException expected) {}

        try { immutify(new TypeToken<List<? extends ConstList>>(){}.asType()); fail(); }
        catch (IllegalArgumentException expected) {}

        try { immutify(new TypeToken<List<? super Integer>>(){}.asType()); fail(); }
        catch (IllegalArgumentException expected) {}

        try { immutify(new TypeToken<List<? extends List<? super Integer>>>(){}.asType()); fail(); }
        catch (IllegalArgumentException expected) {}

        try { immutify(new TypeToken<List<Integer>[]>(){}.asType()); fail(); }
        catch (IllegalArgumentException expected) {}
    }
}
