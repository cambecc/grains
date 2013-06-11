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
import com.fasterxml.jackson.databind.type.MapType;
import net.nullschool.grains.Grain;


/**
 * 2013-06-07<p/>
 *
 * @author Cameron Beccario
 */
final class GrainsDeserializers extends Deserializers.Base {

//    @Override public JsonDeserializer<?> findCollectionDeserializer(
//        CollectionType type,
//        DeserializationConfig config,
//        BeanDescription beanDesc,
//        TypeDeserializer elementTypeDeserializer,
//        JsonDeserializer<?> elementDeserializer) throws JsonMappingException {
//
//    }

    @Override
    public JsonDeserializer<?> findMapDeserializer(
        MapType type,
        DeserializationConfig config,
        BeanDescription beanDesc,
        KeyDeserializer keyDeserializer,
        TypeDeserializer valueTypeDeserializer,
        JsonDeserializer<?> valueDeserializer) throws JsonMappingException {

        Class<?> clazz = type.getRawClass();

        if (Grain.class.isAssignableFrom(clazz)) {
            return new GrainDeserializer(clazz.asSubclass(Grain.class));
        }
        return null;
    }
}
