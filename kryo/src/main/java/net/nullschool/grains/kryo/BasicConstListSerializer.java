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
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import net.nullschool.collect.basic.BasicConstList;

import static net.nullschool.collect.basic.BasicConstList.*;


/**
 * 2013-06-02<p/>
 *
 * Kryo serializer for BasicConstList using the form {int_size, E0, E1, ..., En}.
 *
 * @author Cameron Beccario
 */
public class BasicConstListSerializer extends Serializer<BasicConstList> {

    @Override public void write(Kryo kryo, Output output, BasicConstList list) {
        final int size = list.size();
        output.writeInt(size, true);
        // noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < size; i++) {
            kryo.writeClassAndObject(output, list.get(i));
        }
    }

    @Override public BasicConstList<?> read(Kryo kryo, Input input, Class<BasicConstList> type) {
        final int size = input.readInt(true);
        switch (size) {
            case 0: return emptyList();
            case 1: return listOf(kryo.readClassAndObject(input));
            default:
                Object[] elements = new Object[size];
                for (int i = 0; i < size; i++) {
                    elements[i] = kryo.readClassAndObject(input);
                }
                return asList(elements);
        }
    }
}
