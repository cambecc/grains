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
import net.nullschool.collect.MapIterator;
import net.nullschool.grains.*;

import java.util.Objects;


/**
 * 2013-05-31<p/>
 *
 * Kryo serializer for {@link Grain} instances using the form {k0, v0, ..., kN, vN, NULL}.
 * <ul>
 *     <li>All keys are written as String references rather than simple Strings due to the high probability of
 *         repetition when writing multiple grains at once.</li>
 *     <li>An encoded NULL key signals the end of serialized entries.</li>
 *     <li>Each value is written in combination with its type.</li>
 *     <li>Default values are ignored, i.e., entries whose values are equivalent to the default value for that key are
 *         not serialized. For example, if a grain differs from its {@link GrainFactory#getDefaultValue() default
 *         instance} by only one entry, then only that one entry is serialized. A consequence of this rule is
 *         {@link Grain#extensions extension entries} having a null value are ignored; the key "disappears" after
 *         deserialization.</li>
 * </ul>
 *
 * @author Cameron Beccario
 */
public class VerboseGrainSerializer extends Serializer<Grain> {

    private final GrainFactory factory;
    private final Grain defaultValue;
    private final Serializer<?> keySerializer;

    public VerboseGrainSerializer(Kryo kryo, Class<?> type) {
        this.factory = GrainTools.factoryFor(type);
        this.defaultValue = factory.getDefaultValue();
        this.keySerializer = Objects.requireNonNull(kryo.getSerializer(String.class));
    }

    @Override public void write(Kryo kryo, Output output, Grain grain) {
        for (MapIterator<String, Object> iter = grain.iterator(); iter.hasNext();) {
            String key = iter.next();
            Object value = iter.value();
            if (!Objects.equals(defaultValue.get(key), value)) {
                kryo.writeObject(output, key, keySerializer);
                kryo.writeClassAndObject(output, value);
            }
        }
        kryo.writeObjectOrNull(output, null, keySerializer);
    }

    @Override public Grain read(Kryo kryo, Input input, Class<Grain> type) {
        GrainBuilder builder = factory.getNewBuilder();
        do {
            String key = kryo.readObjectOrNull(input, String.class, keySerializer);
            if (key == null) {
                break;
            }
            Object value = kryo.readClassAndObject(input);
            builder.put(key, value);
        } while (true);
        return builder.build();
    }
}
