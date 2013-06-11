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

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.CollectionType;
import net.nullschool.collect.ConstList;
import net.nullschool.collect.basic.BasicCollections;

import java.io.IOException;
import java.util.*;


/**
 * 2013-06-07<p/>
 *
 * @author Cameron Beccario
 */
final class BasicConstListDeserializer extends StdDeserializer<ConstList> implements ContextualDeserializer {

    private static final long serialVersionUID = 1;


    private final CollectionType listType;
    private final JsonDeserializer<?> elementDeserializer;
    private final TypeDeserializer elementTypeDeserializer;

    public BasicConstListDeserializer(
        CollectionType listType,
        JsonDeserializer<?> elementDeserializer,
        TypeDeserializer elementTypeDeserializer) {

        super(listType.getRawClass());
        this.listType = listType;
        this.elementDeserializer = elementDeserializer;
        this.elementTypeDeserializer = elementTypeDeserializer;
    }

    @Override public JsonDeserializer<?> createContextual(
        DeserializationContext ctxt,
        BeanProperty property) throws JsonMappingException {

        JsonDeserializer<?> ed = elementDeserializer != null ?
            elementDeserializer :
            ctxt.findContextualValueDeserializer(listType.getContentType(), property);
        TypeDeserializer etd = elementTypeDeserializer != null ?
            elementTypeDeserializer.forProperty(property) :
            null;

        return ed == elementDeserializer && etd == elementTypeDeserializer ?
            this :
            new BasicConstListDeserializer(listType, ed, etd);
    }

    @Override public Object deserializeWithType(
        JsonParser jp,
        DeserializationContext ctxt,
        TypeDeserializer typeDeserializer) throws IOException {

        return typeDeserializer.deserializeTypedFromArray(jp, ctxt);
    }

    @Override public ConstList<?> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (jp.getCurrentToken() != JsonToken.START_ARRAY) {
            throw ctxt.mappingException(listType.getRawClass());
        }

        JsonDeserializer<?> ed = elementDeserializer;
        TypeDeserializer etd = elementTypeDeserializer;
        List<Object> elements = new ArrayList<>();
        JsonToken token;

        if (etd != null) {
            // UNDONE: make same as other collection deserializers
            while ((token = jp.nextToken()) != JsonToken.END_ARRAY) {
                elements.add(token == JsonToken.VALUE_NULL ? null : ed.deserializeWithType(jp, ctxt, etd));
            }
        }
        else {
            while ((token = jp.nextToken()) != JsonToken.END_ARRAY) {
                elements.add(token == JsonToken.VALUE_NULL ? null : ed.deserialize(jp, ctxt));
            }
        }
        return BasicCollections.asList(elements);
    }
}
