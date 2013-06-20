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

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.UUID;

import static net.nullschool.collect.basic.BasicCollections.*;
import static org.junit.Assert.*;


/**
 * 2013-06-06<p/>
 *
 * @author Cameron Beccario
 */
public class CompleteTest {

    public static NodeGrain newNode(int id) {
        NodeBuilder builder = NodeFactory.newBuilder();
        builder.setId(id);
        return builder.build();
    }

    public static CompleteBuilder newCompleteBuilderWithSampleValues() {
        CompleteBuilder builder = CompleteFactory.newBuilder();

        builder.setA(true);
        builder.setB((byte)1);
        builder.setC((short)2);
        builder.setD(2);
        builder.setE(3);
        builder.setF(BigInteger.ONE);
        builder.setG(1.0f);
        builder.setH(-1.0f);
        builder.setI(BigDecimal.TEN);
        builder.setJ('a');
        builder.setK("hello");
        builder.setL(UUID.fromString("1bd31d66-eda2-4395-a2a7-510bd581e3ab"));
        builder.setM(URI.create("http://nullschool.net"));
        builder.setN(null);
        builder.setO(Complete.Color.green);
        builder.setP(newNode(1));
        builder.setQ(listOf(1, 2));
        builder.setR(listOf(newNode(1), newNode(2)));
        builder.setS(listOf(2, 3));
        builder.setT(listOf(newNode(2), newNode(3)));
        builder.setU(setOf("a", "b"));
        builder.setV(setOf(newNode(4), newNode(5)));
        builder.setW(mapOf("a", 1, "b", 2));
        builder.setX(mapOf("a", newNode(6), "b", newNode(7)));
        builder.setY(sortedSetOf(null, "x", "y"));
        builder.setZ(sortedMapOf(null, 1, newNode(8), 2, newNode(9)));
        builder.setZa(mapOf("a", setOf(listOf(newNode(1), newNode(2)))));
        builder.setZb(listOf(listOf("a", "b"), listOf("c", "d")));

        return builder;
    }

    @Test
    public void test_size() {
        assertEquals(28, newCompleteBuilderWithSampleValues().size());
    }
}
