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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 2013-06-11<p/>
 *
 * @author Cameron Beccario
 */
abstract class AbstractBasicConstMapSerializer<T> extends StdDeserializer<T> implements ContextualDeserializer {

    private static final long serialVersionUID = 1;


    final MapType mapType;
    final KeyDeserializer keyDeserializer;
    final JsonDeserializer<?> valueDeserializer;
    final TypeDeserializer valueTypeDeserializer;

    AbstractBasicConstMapSerializer(
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

    abstract JsonDeserializer<?> withDeserializers(
        KeyDeserializer keyDeserializer,
        JsonDeserializer<?> valueDeserializer,
        TypeDeserializer valueTypeDeserializer);

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

        return withDeserializers(kd, vd, vtd);
    }

    @Override public Object deserializeWithType(
        JsonParser jp,
        DeserializationContext ctxt,
        TypeDeserializer typeDeserializer) throws IOException {

        return typeDeserializer.deserializeTypedFromObject(jp, ctxt);
    }

    abstract T emptyResult();

    abstract T resultOf(Object key, Object value);

    abstract T asResult(List<Object> keys, List<Object> values);

    @Override public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken token = jp.getCurrentToken();
        if (token == JsonToken.START_OBJECT) {
            token = jp.nextToken();
        }
        else if (token != JsonToken.FIELD_NAME) {
            throw ctxt.mappingException(mapType.getRawClass());
        }

        if (token == JsonToken.END_OBJECT) {
            return emptyResult();
        }

        KeyDeserializer kd = keyDeserializer;
        JsonDeserializer<?> vd = valueDeserializer;
        TypeDeserializer vtd = valueTypeDeserializer;

        List<Object> keys = null;
        List<Object> values = null;
        Object key;
        Object value;
        do {
            String name = jp.getCurrentName();
            key = kd == null ? name : kd.deserializeKey(name, ctxt);
            value = jp.nextToken() != JsonToken.VALUE_NULL ?
                vtd == null ?
                    vd.deserialize(jp, ctxt) :
                    vd.deserializeWithType(jp, ctxt, vtd) :
                null;

            token = jp.nextToken();
            if (keys == null) {
                if (token == JsonToken.END_OBJECT) {
                    return resultOf(key, value);
                }
                keys = new ArrayList<>();
                values = new ArrayList<>();
            }
            keys.add(key);
            values.add(value);
        } while (token != JsonToken.END_OBJECT);

        return asResult(keys, values);
    }
}
