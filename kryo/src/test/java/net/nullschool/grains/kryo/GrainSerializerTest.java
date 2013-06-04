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

package net.nullschool.grains.kryo;

import com.esotericsoftware.kryo.Kryo;
import net.nullschool.collect.basic.BasicConstList;
import net.nullschool.collect.basic.BasicToolsTest;
import net.nullschool.grains.generate.model.*;
import net.nullschool.grains.kryo.model.*;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.UUID;

import static org.junit.Assert.*;
import static net.nullschool.grains.kryo.KryoTestTools.*;

/**
 * 2013-06-04<p/>
 *
 * @author Cameron Beccario
 */
public class GrainSerializerTest {

    private static IntrinsicsBuilder newIntrinsicsBuilderWithSampleValues() {
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

    @Test
    public void test_intrinsic_serialization() {
        IntrinsicsGrain grain = newIntrinsicsBuilderWithSampleValues().build();
        assertEquals(25, grain.size());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Object obj = roundTrip(grain, baos);

        assertEquals(
            "10net.nullschool.grains.generate.model.IntrinsicsGraiee11ie431ABC12b31$floaf44@0001bigDecimaec11" +
                "java.math.BigDecimaec12101bigIntegef212java.math.BigIntegef21211booleaee511boxedBooleaee511b" +
                "oxedDouble5a7ff80000001boxedFloaf447f7fffff1boxedLone7921boxedShorf48021byte5631chaf270a1cha" +
                "rdf70b1charactef270b1double5a7ff00000001enued13net.nullschool.grains.generate.model.Intrinsi" +
                "cs$Colora4111floaf4400011inf4281integef22a1lone79c1shorf48071strine731hellef1URc914java.net." +
                "URc91http://nullschool.nef41UUIc415java.util.UUIc411bd31dfeda2C95a2a7Qbd581e3ab189e3839ce383" +
                "83e382afe382b9e58c96e38390e382a4e38388610",
            BasicToolsTest.asReadableString(baos.toByteArray()));

        assertTrue(obj instanceof IntrinsicsGrain);
        assertEquals(grain, obj);
    }

    @Test
    public void test_sparse_serialization() {
        IntrinsicsGrain grain = IntrinsicsFactory.defaultValue();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Object obj = roundTrip(grain, baos);

        assertEquals(
            "10net.nullschool.grains.generate.model.IntrinsicsGraiee10",
            BasicToolsTest.asReadableString(baos.toByteArray()));

        assertTrue(obj instanceof IntrinsicsGrain);
        assertEquals(grain, obj);
    }

    @Test
    public void test_extension_serialization() {
        OrderGrain order = OrderFactory.newBuilder()
            .setId(UUID.fromString("1bd31d66-eda2-4395-a2a7-510bd581e3ab"))
            .setDescription("test")
            .setSide(Order.Side.buy)
            .build();

        order = order.with("x", 1);
        order = order.with("y", 2);
        order = order.with("z", null);  // expected that this extension key will be dropped

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Object obj = roundTrip(order, baos);

        assertEquals(
            "10net.nullschool.grains.kryo.model.OrderGraiee11ie411java.util.UUIc411bd31dfeda2C95a2a7Qbd581e3a" +
                "b1descriptioee31tesf41side512net.nullschool.grains.kryo.model.Order$Side511182x22182y240",
            BasicToolsTest.asReadableString(baos.toByteArray()));

        assertTrue(obj instanceof OrderGrain);
        assertEquals(order.without("z"), obj);
    }

    @Test
    public void test_nested_grains() {
        ProductGrain product = ProductFactory.defaultValue();
        OrderGrain order = OrderFactory.newBuilder()
            .setId(UUID.fromString("1bd31d66-eda2-4395-a2a7-510bd581e3ab"))
            .setDescription("test")
            .setSide(Order.Side.buy)
            .setMainProduct(product.withDescription("test1"))
            .setProducts(BasicConstList.listOf(
                product.withDescription("test2"),
                product.withDescription("test3")))
            .build();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Object obj = roundTrip(order, baos);

        assertEquals(
            "10net.nullschool.grains.kryo.model.OrderGraiee11ie411java.util.UUIc411bd31dfeda2C95a2a7Qbd581e3ab1" +
                "descriptioee31tesf41mainProducf412net.nullschool.grains.kryo.model.ProductGraiee1531testb101pr" +
                "oductf313net.nullschool.collect.basic.BasicConstLisf412121531testb20121531testb301side514net.n" +
                "ullschool.grains.kryo.model.Order$Side5110",
            BasicToolsTest.asReadableString(baos.toByteArray()));

        assertTrue(obj instanceof OrderGrain);
        assertEquals(order, obj);
    }

    @Test
    public void test_nested_grains_with_registration() {
        ProductGrain product = ProductFactory.defaultValue();
        OrderGrain order = OrderFactory.newBuilder()
            .setId(UUID.fromString("1bd31d66-eda2-4395-a2a7-510bd581e3ab"))
            .setDescription("test")
            .setSide(Order.Side.buy)
            .setMainProduct(product.withDescription("test1"))
            .setProducts(BasicConstList.listOf(product.withDescription("test2")))
            .build();

        Kryo kryo = newTestKryo();
        kryo.setRegistrationRequired(true);
        kryo.register(UUID.class, 40);
        kryo.register(BasicConstList.class, 41);
        kryo.register(OrderGrain.class, 42);
        kryo.register(Order.Side.class, 43);
        kryo.register(ProductGrain.class, 44);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Object obj = roundTrip(order, baos, kryo, kryo);

        assertEquals(
            ",11ie4*11bd31dfeda2C95a2a7Qbd581e3ab1descriptioee31tesf41mainProducf4.1531testb101productf3+11.1" +
                "531testb201side5-110",
            BasicToolsTest.asReadableString(baos.toByteArray()));

        assertTrue(obj instanceof OrderGrain);
        assertEquals(order, obj);
    }
}
