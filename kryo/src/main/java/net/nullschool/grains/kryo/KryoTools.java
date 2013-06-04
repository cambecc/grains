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
import com.esotericsoftware.kryo.util.MapReferenceResolver;
import net.nullschool.collect.basic.*;
import net.nullschool.grains.Grain;

import java.net.URI;
import java.util.UUID;


/**
 * 2013-05-31<p/>
 *
 * Utility methods to support {@link Kryo} serialization of Grains.
 *
 * @author Cameron Beccario
 */
public class KryoTools {

    private KryoTools() {
        throw new AssertionError();
    }

    /**
     * Constructs a new Kryo instance configured for Grain serialization and deserialization.
     */
    public static Kryo newGrainKryo() {
        return configureGrainSerializers(new Kryo(new PublicInterfaceResolver(), new MapReferenceResolver()));
    }

    /**
     * Adds default serializers for all supported Grain types to the specified Kryo instance.
     *
     * @param kryo the kryo instance to configure.
     * @return the same kryo instance.
     * @throws NullPointerException if kryo is null.
     */
    public static Kryo configureGrainSerializers(Kryo kryo) {
        kryo.addDefaultSerializer(Grain.class, VerboseGrainSerializer.class);
        kryo.addDefaultSerializer(UUID.class, new UUIDSerializer());
        kryo.addDefaultSerializer(URI.class, new URISerializer());
        kryo.addDefaultSerializer(BasicConstList.class, new BasicConstListSerializer());
        kryo.addDefaultSerializer(BasicConstSortedSet.class, new BasicConstSortedSetSerializer());
        kryo.addDefaultSerializer(BasicConstSet.class, new BasicConstSetSerializer());
        kryo.addDefaultSerializer(BasicConstSortedMap.class, new BasicConstSortedMapSerializer());
        kryo.addDefaultSerializer(BasicConstMap.class, new BasicConstMapSerializer());
        return kryo;
    }
}
