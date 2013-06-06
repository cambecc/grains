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

package net.nullschool.grains.msgpack;

import net.nullschool.collect.basic.BasicToolsTest;
import net.nullschool.grains.generate.model.*;
import org.junit.Test;
import org.msgpack.MessagePack;

import java.io.IOException;

import static net.nullschool.collect.basic.BasicCollections.*;
import static org.junit.Assert.*;


/**
 * 2013-06-06<p/>
 *
 * @author Cameron Beccario
 */
public class MessagePackTest {

    @Test
    public void test_complete_serialization() throws IOException {
        CompleteGrain expected = CompleteTest.newCompleteBuilderWithSampleValues().build();

        MessagePack msgpack = MessagePackTools.newGrainsMessagePack();
        byte[] data = msgpack.write(expected);
        CompleteGrain actual = msgpack.read(data, CompleteGrain.class);

        assertEquals(
            "de01ba1ac3a1b1a1c2a1d2a1e3a1f1a1gca?8000a1hcbbff0000000a1ia210a1jaa1ka5helloa1l92cf1bd31dfeda2C9" +
                "5d3a2a7Qbd581e3aba1mb5http://nullschool.neta1na3USDa1o1a1p81a2id1a1q9212a1r9281a2id181a2id2a" +
                "1s9223a1t9281a2id281a2id3a1u92a1aa1ba1v9281a2id481a2id5a1w82a1a1a1b2a1x82a1a81a2id6a1b81a2id" +
                "7a1y92a1xa1ya1z82181a2id8281a2id9a2za81a1a919281a2id181a2id2",
            BasicToolsTest.asReadableString(data));
        assertEquals(expected, actual);
    }

    @Test
    public void test_sparse_serialization() throws IOException {
        CompleteGrain expected = CompleteFactory.defaultValue();

        MessagePack msgpack = MessagePackTools.newGrainsMessagePack();
        byte[] data = msgpack.write(expected);
        CompleteGrain actual = msgpack.read(data, CompleteGrain.class);

        assertEquals("80", BasicToolsTest.asReadableString(data));
        assertEquals(expected, actual);
    }

    @Test
    public void test_extension_serialization() throws IOException {
        NodeGrain expected = NodeFactory.newBuilder().setId(10).build();

        expected = expected.with("x", 1);
        expected = expected.with("y", 2);
        expected = expected.with("z", null);  // expected that this extension key will be dropped
        expected = expected.with("extra", mapOf("a", 1, "b", 2));

        MessagePack msgpack = MessagePackTools.newGrainsMessagePack();
        byte[] data = msgpack.write(expected);
        NodeGrain actual = msgpack.read(data, NodeGrain.class);

        assertEquals("84a2idaa5extra82a1a1a1b2a1x1a1y2", BasicToolsTest.asReadableString(data));
        assertEquals(expected.without("z"), actual);
    }
}
