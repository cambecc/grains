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

package net.nullschool.grains.generate.model;

import net.nullschool.grains.GrainFactory;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import static net.nullschool.collect.CollectionTestingTools.*;



/**
* 2013-02-14<p/>
*
* @author Cameron Beccario
*/
public class IntrinsicsTest {

    private static LinkedHashMap<String, Object> newBasisAsPlainMap() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("id", null);
        map.put("$float", 0.0F);
        map.put("bigDecimal", null);
        map.put("bigInteger", null);
        map.put("boolean", false);
        map.put("boxedBoolean", null);
        map.put("boxedDouble", null);
        map.put("boxedFloat", null);
        map.put("boxedLong", null);
        map.put("boxedShort", null);
        map.put("byte", (byte)0);
        map.put("char", (char)0);
        map.put("char_", (char)0);
        map.put("character", null);
        map.put("double", 0.0D);
        map.put("enum", null);
        map.put("float", 0.0F);
        map.put("int", 0);
        map.put("integer", null);
        map.put("long", 0L);
        map.put("short", (short)0);
        map.put("string", null);
        map.put("URI", null);
        map.put("UUID", null);
        map.put("ボックス化バイト", null);
        return map;
    }

    private static LinkedHashMap<String, Object> newSampleValuesAsPlainMap() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("id", "ABC123");
        map.put("$float", 2.0F);
        map.put("bigDecimal", BigDecimal.ONE);
        map.put("bigInteger", BigInteger.ONE);
        map.put("boolean", true);
        map.put("boxedBoolean", true);
        map.put("boxedDouble", Double.NaN);
        map.put("boxedFloat", Float.MAX_VALUE);
        map.put("boxedLong", 1L);
        map.put("boxedShort", (short)2);
        map.put("byte", (byte)3);
        map.put("char", 'a');
        map.put("char_", 'b');
        map.put("character", 'b');
        map.put("double", Double.POSITIVE_INFINITY);
        map.put("enum", Intrinsics.Color$.red);
        map.put("float", Float.MIN_VALUE);
        map.put("int", 4);
        map.put("integer", 5);
        map.put("long", 6L);
        map.put("short", (short)7);
        map.put("string", "hello");
        map.put("URI", URI.create("http://nullschool.net"));
        map.put("UUID", UUID.fromString("1bd31d66-eda2-4395-a2a7-510bd581e3ab"));
        map.put("ボックス化バイト", (byte)1);
        return map;
    }

    private static IntrinsicsGrain newConstWithSampleValues() {
        IntrinsicsGrain grain = IntrinsicsFactory.defaultValue();
        grain = grain.withId("ABC123");
        grain = grain.with$float(2.0F);
        grain = grain.withBigDecimal(BigDecimal.ONE);
        grain = grain.withBigInteger(BigInteger.ONE);
        grain = grain.withBoolean(true);
        grain = grain.withBoxedBoolean(true);
        grain = grain.withボックス化バイト((byte)1);
        grain = grain.withBoxedDouble(Double.NaN);
        grain = grain.withBoxedFloat(Float.MAX_VALUE);
        grain = grain.withBoxedLong(1L);
        grain = grain.withBoxedShort((short)2);
        grain = grain.withByte((byte)3);
        grain = grain.withChar('a');
        grain = grain.withChar_('b');
        grain = grain.withCharacter('b');
        grain = grain.withDouble(Double.POSITIVE_INFINITY);
        grain = grain.withEnum(Intrinsics.Color$.red);
        grain = grain.withFloat(Float.MIN_VALUE);
        grain = grain.withInt(4);
        grain = grain.withInteger(5);
        grain = grain.withLong(6);
        grain = grain.withShort((short)7);
        grain = grain.withString("hello");
        grain = grain.withURI(URI.create("http://nullschool.net"));
        grain = grain.withUUID(UUID.fromString("1bd31d66-eda2-4395-a2a7-510bd581e3ab"));
        return grain;
    }

    private static IntrinsicsBuilder newBuilderWithSampleValues() {
        IntrinsicsBuilder builder = IntrinsicsFactory.newBuilder();
        builder.setId("ABC123");
        builder.set$float(2.0F);
        builder.setBigDecimal(BigDecimal.ONE);
        builder.setBigInteger(BigInteger.ONE);
        builder.setBoolean(true);
        builder.setBoxedBoolean(true);
        builder.setボックス化バイト((byte)1);
        builder.setBoxedDouble(Double.NaN);
        builder.setBoxedFloat(Float.MAX_VALUE);
        builder.setBoxedLong(1L);
        builder.setBoxedShort((short)2);
        builder.setByte((byte)3);
        builder.setChar('a');
        builder.setChar_('b');
        builder.setCharacter('b');
        builder.setDouble(Double.POSITIVE_INFINITY);
        builder.setEnum(Intrinsics.Color$.red);
        builder.setFloat(Float.MIN_VALUE);
        builder.setInt(4);
        builder.setInteger(5);
        builder.setLong(6);
        builder.setShort((short)7);
        builder.setString("hello");
        builder.setURI(URI.create("http://nullschool.net"));
        builder.setUUID(UUID.fromString("1bd31d66-eda2-4395-a2a7-510bd581e3ab"));
        return builder;
    }

    private static void compare_getters_with_expected(Intrinsics intrinsics) {
        assertEquals("ABC123", intrinsics.getId());
        assertEquals(2.0F, intrinsics.get$float(), 0.0F);
        assertEquals(BigDecimal.ONE, intrinsics.getBigDecimal());
        assertEquals(BigInteger.ONE, intrinsics.getBigInteger());
        assertEquals(true, intrinsics.isBoolean());
        assertEquals(true, intrinsics.getBoxedBoolean());
        assertEquals((Byte)(byte)1, intrinsics.getボックス化バイト());
        assertEquals((Double)Double.NaN, intrinsics.getBoxedDouble());
        assertEquals((Float)Float.MAX_VALUE, intrinsics.getBoxedFloat());
        assertEquals((Long)1L, intrinsics.getBoxedLong());
        assertEquals((Short)(short)2, intrinsics.getBoxedShort());
        assertEquals((byte)3, intrinsics.getByte());
        assertEquals('a', intrinsics.getChar());
        assertEquals('b', intrinsics.getChar_());
        assertEquals((Character)'b', intrinsics.getCharacter());
        assertEquals(Double.POSITIVE_INFINITY, intrinsics.getDouble(), 0.0D);
        assertEquals(Intrinsics.Color$.red, intrinsics.getEnum());
        assertEquals(Float.MIN_VALUE, intrinsics.getFloat(), 0.0F);
        assertEquals(4, intrinsics.getInt());
        assertEquals((Integer)5, intrinsics.getInteger());
        assertEquals(6L, intrinsics.getLong());
        assertEquals((short)7, intrinsics.getShort());
        assertEquals("hello", intrinsics.getString());
        assertEquals(URI.create("http://nullschool.net"), intrinsics.getURI());
        assertEquals(UUID.fromString("1bd31d66-eda2-4395-a2a7-510bd581e3ab"), intrinsics.getUUID());
    }

    @Test
    public void test_intrinsics_size_changed() {
        assertEquals(25, IntrinsicsFactory.defaultValue().size());
    }

    @Test
    public void test_factory_instance() {
        assertTrue(IntrinsicsFactory.class.isEnum());
        assertEquals(1, IntrinsicsFactory.class.getEnumConstants().length);
        IntrinsicsFactory factory = IntrinsicsFactory.class.getEnumConstants()[0];
        assertSame(factory, Enum.valueOf(IntrinsicsFactory.class, "INSTANCE"));
        assertEquals(IntrinsicsFactory.class.getName(), factory.toString());
        assertTrue(GrainFactory.class.isAssignableFrom(IntrinsicsFactory.class));

        assertSame(IntrinsicsFactory.defaultValue(), factory.getDefaultValue());
        assertNotNull(factory.getNewBuilder());
    }

    @Test
    public void test_unique_builders() {
        assertNotSame(IntrinsicsFactory.newBuilder(), IntrinsicsFactory.newBuilder());
    }

    @Test
    public void test_new_builder_as_map() {
        compare_maps(newBasisAsPlainMap(), IntrinsicsFactory.newBuilder());
        compare_maps(newBasisAsPlainMap(), IntrinsicsFactory.class.getEnumConstants()[0].getNewBuilder());
    }

    @Test
    public void test_default_as_map() {
        compare_maps(newBasisAsPlainMap(), IntrinsicsFactory.defaultValue());
        compare_maps(newBasisAsPlainMap(), IntrinsicsFactory.class.getEnumConstants()[0].getDefaultValue());
    }

    @Test
    public void test_getters_and_setters() {
        compare_getters_with_expected(newBuilderWithSampleValues());
        compare_maps(newSampleValuesAsPlainMap(), newBuilderWithSampleValues());
    }

    @Test
    public void test_getters_and_withs() {
        compare_getters_with_expected(newConstWithSampleValues());
        compare_maps(newSampleValuesAsPlainMap(), newConstWithSampleValues());
    }

    @Test
    public void test_build() {
        compare_maps(newSampleValuesAsPlainMap(), newBuilderWithSampleValues().build());
    }

    @Test
    public void test_clear() {
        IntrinsicsBuilder builder = newBuilderWithSampleValues();
        builder.clear();
        compare_maps(newBasisAsPlainMap(), builder);
    }

    @Test
    public void test_keySet_clear() {
        IntrinsicsBuilder builder = newBuilderWithSampleValues();
        builder.keySet().clear();
        compare_maps(newBasisAsPlainMap(), builder);
    }

    @Test
    public void test_values_clear() {
        IntrinsicsBuilder builder = newBuilderWithSampleValues();
        builder.values().clear();
        compare_maps(newBasisAsPlainMap(), builder);
    }

    @Test
    public void test_entrySet_clear() {
        IntrinsicsBuilder builder = newBuilderWithSampleValues();
        builder.entrySet().clear();
        compare_maps(newBasisAsPlainMap(), builder);
    }

    @Test
    public void test_builder_set_and_put() {
        IntrinsicsBuilder builder = IntrinsicsFactory.newBuilder();
        builder.setString("hello");
        assertEquals("hello", builder.getString());
        assertSame(builder.getString(), builder.get("string"));
        builder.put("string", "world");
        assertEquals("world", builder.getString());
        assertSame(builder.getString(), builder.get("string"));
    }

    @Test
    public void test_builder_put_null_of_static_key() {
        IntrinsicsBuilder builder = IntrinsicsFactory.newBuilder();

        builder.put("string", "hello");
        assertEquals("hello", builder.getString());
        builder.put("string", null);
        assertEquals(null, builder.getString());

        builder.put("int", 5);
        assertEquals(5, builder.getInt());
        builder.put("int", null);
        assertEquals(0, builder.getInt());

        builder.put("short", (short)5);
        assertEquals(5, builder.getShort());
        builder.put("short", null);
        assertEquals(0, builder.getShort());

        builder.put("long", (long)5);
        assertEquals(5, builder.getLong());
        builder.put("long", null);
        assertEquals(0, builder.getLong());

        builder.put("boolean", true);
        assertEquals(true, builder.isBoolean());
        builder.put("boolean", null);
        assertEquals(false, builder.isBoolean());
    }

    @Test
    public void test_builder_get_nonexistent() {
        IntrinsicsBuilder builder = IntrinsicsFactory.newBuilder();
        assertNull(builder.get("barney"));
    }

    @Test(expected = ClassCastException.class)
    public void test_put_wrong_type() {
        IntrinsicsFactory.newBuilder().put("long", 1);
    }

    @Test(expected = ClassCastException.class)
    public void test_with_wrong_type() {
        IntrinsicsFactory.defaultValue().with("long", 1);
    }

    @Test(expected = ClassCastException.class)
    public void test_put_not_boolean() {
        IntrinsicsFactory.newBuilder().put("boolean", "true");
    }

    @Test(expected = ClassCastException.class)
    public void test_with_not_boolean() {
        IntrinsicsFactory.defaultValue().with("boolean", "true");
    }

    @Test
    public void test_immutable() {
        assert_map_immutable(IntrinsicsFactory.defaultValue());
    }
}
