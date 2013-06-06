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
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

import static org.junit.Assert.*;
import static net.nullschool.grains.kryo.KryoTestTools.*;
import static net.nullschool.collect.basic.BasicCollections.*;
import static net.nullschool.grains.generate.model.CompleteTest.*;


/**
 * 2013-06-04<p/>
 *
 * @author Cameron Beccario
 */
public class GrainSerializerTest {

    @Test
    public void test_intrinsic_serialization() {
        CompleteGrain expected = newCompleteBuilderWithSampleValues().build();
        assertEquals(27, expected.size());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Object actual = roundTrip(expected, baos);

        assertEquals(
            "10net.nullschool.grains.generate.model.CompleteGraiee1182a51182b61182c802182d24182e96182f11java." +
                "math.BigIntegef2121182g4?8000182habff0000000182i12java.math.BigDecimaec12a0182j70a182k31hell" +
                "ef182l13java.util.UUIc411bd31dfeda2C95a2a7Qbd581e3ab182m14java.net.URc91http://nullschool.ne" +
                "f4182n15java.util.Currencf91USc4182o16net.nullschool.grains.generate.model.Complete$Colof212" +
                "182p17net.nullschool.grains.generate.model.NodeGraiee11ie4220182q18net.nullschool.collect.ba" +
                "sic.BasicConstLisf4122224182r18121711b2201711b240182s18122426182t18121711b2401711b260182u19n" +
                "et.nullschool.collect.basic.BasicConstSef4123334182v19121711b2801711b2a0182w1anet.nullschool" +
                ".collect.basic.BasicConstMaf01233223424182x1a12331711b2c0341711b2e0182y1bnet.nullschool.coll" +
                "ect.basic.BasicConstSortedSef41023034182z1cnet.nullschool.collect.basic.BasicConstSortedMaf0" +
                "102221711b2100241711b21201ze11a1133191118121711b2201711b2400",
            BasicToolsTest.asReadableString(baos.toByteArray()));

        assertTrue(actual instanceof CompleteGrain);
        assertEquals(expected, actual);
    }

    @Test
    public void test_sparse_serialization() {
        CompleteGrain expected = CompleteFactory.defaultValue();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Object actual = roundTrip(expected, baos);

        assertEquals(
            "10net.nullschool.grains.generate.model.CompleteGraiee10",
            BasicToolsTest.asReadableString(baos.toByteArray()));

        assertTrue(actual instanceof CompleteGrain);
        assertEquals(expected, actual);
    }

    @Test
    public void test_extension_serialization() {
        NodeGrain expected = NodeFactory.newBuilder().setId(10).build();

        expected = expected.with("x", 1);
        expected = expected.with("y", 2);
        expected = expected.with("z", null);  // expected that this extension key will be dropped
        expected = expected.with("extra", mapOf("a", 1, "b", 2));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Object actual = roundTrip(expected, baos);

        assertEquals(
            "10net.nullschool.grains.generate.model.NodeGraiee11ie42141extre111net.nullschool.collect.basic.B" +
                "asicConstMaf0123182a223182b24182x22182y240",
            BasicToolsTest.asReadableString(baos.toByteArray()));

        assertTrue(actual instanceof NodeGrain);
        assertEquals(expected.without("z"), actual);
    }

    @Test
    public void test_kryo_registration() {
        CompleteGrain expected = CompleteFactory.defaultValue()
            .withT(listOf(newNode(1), newNode(2)))
            .withI(BigDecimal.ONE)
            .with("extra", Complete.Color.green);

        Kryo kryo = newTestKryo();
        kryo.setRegistrationRequired(true);
        kryo.register(BasicConstList.class, 40);
        kryo.register(BigDecimal.class, 41);
        kryo.register(CompleteGrain.class, 42);
        kryo.register(NodeGrain.class, 43);
        kryo.register(Complete.Color.class, 44);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Object actual = roundTrip(expected, baos, kryo, kryo);

        assertEquals(
            ",1182i+1210182t*12-11ie4220-182401extre1.120",
            BasicToolsTest.asReadableString(baos.toByteArray()));

        assertTrue(actual instanceof CompleteGrain);
        assertEquals(expected, actual);
    }
}
