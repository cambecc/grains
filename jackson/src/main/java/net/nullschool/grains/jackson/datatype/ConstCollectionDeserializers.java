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

package net.nullschool.grains.jackson.datatype;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import net.nullschool.collect.*;
import net.nullschool.grains.jackson.datatype.deser.*;


/**
 * 2013-06-07<p/>
 *
 * @author Cameron Beccario
 */
final class ConstCollectionDeserializers extends Deserializers.Base {

    @Override public JsonDeserializer<?> findCollectionDeserializer(
        CollectionType type,
        DeserializationConfig config,
        BeanDescription beanDesc,
        TypeDeserializer elementTypeDeserializer,
        JsonDeserializer<?> elementDeserializer) throws JsonMappingException {

        Class<?> clazz = type.getRawClass();

        if (ConstCollection.class.isAssignableFrom(clazz)) {
            if (ConstSet.class.isAssignableFrom(clazz)) {
                if (ConstSortedSet.class.isAssignableFrom(clazz)) {
                    return new BasicConstSortedSetDeserializer(type, elementDeserializer, elementTypeDeserializer);
                }
                return new BasicConstSetDeserializer(type, elementDeserializer, elementTypeDeserializer);
            }
            return new BasicConstListDeserializer(type, elementDeserializer, elementTypeDeserializer);
        }
        return null;
    }

    @Override
    public JsonDeserializer<?> findMapDeserializer(
        MapType type,
        DeserializationConfig config,
        BeanDescription beanDesc,
        KeyDeserializer keyDeserializer,
        TypeDeserializer valueTypeDeserializer,
        JsonDeserializer<?> valueDeserializer) throws JsonMappingException {

        Class<?> clazz = type.getRawClass();

        if (ConstMap.class.isAssignableFrom(clazz)) {
            if (ConstSortedMap.class.isAssignableFrom(clazz)) {
                return new BasicConstSortedMapDeserializer(
                    type,
                    keyDeserializer,
                    valueDeserializer,
                    valueTypeDeserializer);
            }
            return new BasicConstMapDeserializer(type, keyDeserializer, valueDeserializer, valueTypeDeserializer);
        }

        return null;
    }
}
