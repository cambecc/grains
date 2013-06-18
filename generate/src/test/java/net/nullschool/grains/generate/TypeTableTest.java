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

import net.nullschool.grains.*;
import org.junit.Test;
import net.nullschool.grains.generate.TypeTable.ClassHandle;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


/**
 * 2013-05-10<p/>
 *
 * @author Cameron Beccario
 */
public class TypeTableTest {

    @Test
    public void test_simple_class_creation() {
        TypeTable table = new TypeTable(new NamingPolicy(), ConfigurableTypePolicy.STANDARD);
        ClassHandle testHandle = table.createClass("com.test.Test", Object.class);
        assertFalse(testHandle.isLoaded());
        assertTrue(testHandle.isDynamicallyCreated());

        Class<?> testClass = testHandle.toClass();
        assertEquals("com.test.Test", testClass.getName());
        assertSame(Object.class, testClass.getSuperclass());
        assertTrue(Modifier.isPublic(testClass.getModifiers()));
        assertTrue(testHandle.isLoaded());
        assertTrue(testHandle.isDynamicallyCreated());
    }

    @Test
    public void test_generic_class_creation() {
        TypeTable table = new TypeTable(new NamingPolicy(), ConfigurableTypePolicy.STANDARD);
        ClassHandle mapHandle = table.createClass("com.test.Map", AbstractMap.class);
        Class<?> mapClass = mapHandle.toClass();

        assertEquals("com.test.Map", mapClass.getName());
        assertSame(AbstractMap.class, mapClass.getSuperclass());
        assertEquals("[K, V]", Arrays.toString(mapClass.getTypeParameters()));
        assertEquals("java.util.AbstractMap<K, V>", mapClass.getGenericSuperclass().toString());
    }

    @Test
    public void test_creation_of_instantiated_generic() {
        TypeTable table = new TypeTable(new NamingPolicy(), ConfigurableTypePolicy.STANDARD);
        ClassHandle grainHandle = table.createClass("com.test.TestGrain", AbstractGrain.class);
        Class<?> grainClass = grainHandle.toClass();

        assertEquals("com.test.TestGrain", grainClass.getName());
        assertSame(AbstractGrain.class, grainClass.getSuperclass());
        assertEquals(0, grainClass.getTypeParameters().length);
        assertSame(AbstractGrain.class, grainClass.getGenericSuperclass());
    }

    @Test
    public void test_interface_creation() {
        TypeTable table = new TypeTable(new NamingPolicy(), ConfigurableTypePolicy.STANDARD);
        ClassHandle testableHandle = table.createClass("com.test.Testable", null);
        assertFalse(testableHandle.isLoaded());
        assertTrue(testableHandle.isDynamicallyCreated());

        Class<?> testableClass = testableHandle.toClass();
        assertTrue(testableClass.isInterface());
        assertEquals("com.test.Testable", testableClass.getName());
        assertNull(testableClass.getSuperclass());
        assertEquals(0, testableClass.getInterfaces().length);
        assertTrue(Modifier.isPublic(testableClass.getModifiers()));
        assertTrue(testableHandle.isLoaded());
        assertTrue(testableHandle.isDynamicallyCreated());
    }

    @Test
    public void test_sub_interface_creation() {
        TypeTable table = new TypeTable(new NamingPolicy(), ConfigurableTypePolicy.STANDARD);
        ClassHandle raHandle = table.createClass("com.test.RandomAccess", RandomAccess.class);
        Class<?> raClass = raHandle.toClass();

        assertTrue(raClass.isInterface());
        assertEquals("com.test.RandomAccess", raClass.getName());
        assertNull(raClass.getSuperclass());
        assertSame(RandomAccess.class, raClass.getInterfaces()[0]);
    }

    @Test
    public void test_generic_interface_creation() {
        TypeTable table = new TypeTable(new NamingPolicy(), ConfigurableTypePolicy.STANDARD);
        ClassHandle iMapHandle = table.createClass("com.test.IMap", Map.class);
        Class<?> iMapClass = iMapHandle.toClass();

        assertEquals("com.test.IMap", iMapClass.getName());
        assertEquals("[K, V]", Arrays.toString(iMapClass.getTypeParameters()));
        assertSame(Map.class, iMapClass.getInterfaces()[0]);
        assertEquals("java.util.Map<K, V>", iMapClass.getGenericInterfaces()[0].toString());
        assertNull(iMapClass.getSuperclass());
        assertNull(iMapClass.getGenericSuperclass());
    }

    @Test
    public void test_enum_creation() {
        TypeTable table = new TypeTable(new NamingPolicy(), ConfigurableTypePolicy.STANDARD);
        ClassHandle enumHandle = table.createClass("com.test.TestEnum", Enum.class);
        Class<?> enumClass = enumHandle.toClass();

        assertTrue(enumClass.isEnum());
        assertEquals("com.test.TestEnum", enumClass.getName());
        assertSame(Enum.class, enumClass.getSuperclass());
        assertEquals("java.lang.Enum<E>", enumClass.getGenericSuperclass().toString());
        assertTrue(Modifier.isPublic(enumClass.getModifiers()));
    }

    @GrainSchema(targetPackage = "com.test")
    public interface Foo {
    }

    @Test
    public void test_schema_types() {
        TypeTable table = new TypeTable(new NamingPolicy(), ConfigurableTypePolicy.STANDARD);
        Map<String, Type> schemaTypes = table.schemaTypes(Foo.class);

        assertSame(Foo.class, schemaTypes.get("targetSchema"));
        assertEquals("interface com.test.FooGrain", schemaTypes.get("targetGrain").toString());
        assertEquals("interface com.test.FooBuilder", schemaTypes.get("targetBuilder").toString());
        assertEquals("class com.test.FooFactory", schemaTypes.get("targetFactory").toString());
        assertEquals("class com.test.FooFactory$FooGrainImpl", schemaTypes.get("targetGrainImpl").toString());
        assertEquals("class com.test.FooFactory$FooGrainProxy", schemaTypes.get("targetGrainProxy").toString());
        assertEquals("class com.test.FooFactory$FooBuilderImpl", schemaTypes.get("targetBuilderImpl").toString());
    }
}
