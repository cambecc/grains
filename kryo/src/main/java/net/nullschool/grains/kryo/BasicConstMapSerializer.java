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
import net.nullschool.collect.ConstMap;
import net.nullschool.collect.MapIterator;

import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-06-02<p/>
 *
 * Kryo serializer for BasicConstMap using the form {int_size, k0, v0, k1, v1, ..., kN, vN}.
 *
 * @author Cameron Beccario
 */
public class BasicConstMapSerializer extends Serializer<ConstMap> {

    @Override public void write(Kryo kryo, Output output, ConstMap map) {
        output.writeInt(map.size(), true);
        for (MapIterator<?, ?> iter = map.iterator(); iter.hasNext();) {
            kryo.writeClassAndObject(output, iter.next());
            kryo.writeClassAndObject(output, iter.value());
        }
    }

    @Override public ConstMap<?, ?> read(Kryo kryo, Input input, Class<ConstMap> type) {
        final int size = input.readInt(true);
        switch (size) {
            case 0: return emptyMap();
            case 1:
                Object key = kryo.readClassAndObject(input);
                Object value = kryo.readClassAndObject(input);
                return mapOf(key, value);
            default:
                Object[] keys = new Object[size];
                Object[] values = new Object[size];
                for (int i = 0; i < size; i++) {
                    keys[i] = kryo.readClassAndObject(input);
                    values[i] = kryo.readClassAndObject(input);
                }
                return asMap(keys, values);
        }
    }
}
