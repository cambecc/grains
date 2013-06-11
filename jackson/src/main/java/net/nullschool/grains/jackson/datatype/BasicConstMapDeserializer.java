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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.MapType;
import net.nullschool.collect.ConstMap;
import net.nullschool.collect.basic.BasicCollections;

import java.io.IOException;
import java.util.*;


/**
 * 2013-06-07<p/>
 *
 * @author Cameron Beccario
 */
final class BasicConstMapDeserializer extends StdDeserializer<ConstMap> implements ContextualDeserializer {

    private static final long serialVersionUID = 1;


    private final MapType mapType;
    private final KeyDeserializer keyDeserializer;
    private final JsonDeserializer<?> valueDeserializer;
    private final TypeDeserializer valueTypeDeserializer;

    public BasicConstMapDeserializer(
        MapType mapType,
        KeyDeserializer keyDeserializer,
        JsonDeserializer<?> valueDeserializer,
        TypeDeserializer valueTypeDeserializer) {

        super(mapType.getRawClass());
        this.mapType = mapType;
        this.keyDeserializer = keyDeserializer;
        this.valueDeserializer = valueDeserializer;
        this.valueTypeDeserializer = valueTypeDeserializer;
    }

    @Override public JsonDeserializer<?> createContextual(
        DeserializationContext ctxt,
        BeanProperty property) throws JsonMappingException {

        KeyDeserializer kd = keyDeserializer != null ?
            keyDeserializer :
            ctxt.findKeyDeserializer(mapType.getKeyType(), property);
        JsonDeserializer<?> vd = valueDeserializer != null ?
            valueDeserializer :
            ctxt.findContextualValueDeserializer(mapType.getContentType(), property);
        TypeDeserializer vtd = valueTypeDeserializer != null ?
            valueTypeDeserializer.forProperty(property) :
            null;

        return kd == keyDeserializer && vd == valueDeserializer && vtd == valueTypeDeserializer ?
            this :
            new BasicConstMapDeserializer(mapType, kd, vd, vtd);
    }

    @Override public Object deserializeWithType(
        JsonParser jp,
        DeserializationContext ctxt,
        TypeDeserializer typeDeserializer) throws IOException {

        return typeDeserializer.deserializeTypedFromObject(jp, ctxt);
    }

    @Override public ConstMap<?, ?> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken token = jp.getCurrentToken();
        if (token == JsonToken.START_OBJECT) {
            token = jp.nextToken();
            if (token != JsonToken.FIELD_NAME && token != JsonToken.END_OBJECT) {
                throw ctxt.mappingException(mapType.getRawClass());
            }
        }
        else if (token != JsonToken.FIELD_NAME) {
            throw ctxt.mappingException(mapType.getRawClass());
        }

        KeyDeserializer kd = keyDeserializer;
        JsonDeserializer<?> vd = valueDeserializer;
        TypeDeserializer vtd = valueTypeDeserializer;

        List<Object> keys = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        while (token == JsonToken.FIELD_NAME) {
            String key = jp.getCurrentName();
            keys.add(kd != null ? kd.deserializeKey(key, ctxt) : key);
            values.add(jp.nextToken() == JsonToken.VALUE_NULL ?
                null :
                vtd != null ?
                    vd.deserializeWithType(jp, ctxt, vtd) :
                    vd.deserialize(jp, ctxt));
            token = jp.nextToken();
        }
        return BasicCollections.asMap(keys.toArray(), values.toArray());
    }
}
