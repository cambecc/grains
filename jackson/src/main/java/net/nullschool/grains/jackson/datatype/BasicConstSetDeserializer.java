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
import com.fasterxml.jackson.databind.type.CollectionType;
import net.nullschool.collect.ConstSet;
import net.nullschool.collect.basic.BasicCollections;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 2013-06-07<p/>
 *
 * @author Cameron Beccario
 */
final class BasicConstSetDeserializer extends StdDeserializer<ConstSet> implements ContextualDeserializer {

    private static final long serialVersionUID = 1;


    private final CollectionType setType;
    private final JsonDeserializer<?> elementDeserializer;
    private final TypeDeserializer elementTypeDeserializer;

    public BasicConstSetDeserializer(
        CollectionType setType,
        JsonDeserializer<?> elementDeserializer,
        TypeDeserializer elementTypeDeserializer) {

        super(setType.getRawClass());
        this.setType = setType;
        this.elementDeserializer = elementDeserializer;
        this.elementTypeDeserializer = elementTypeDeserializer;
    }

    @Override public JsonDeserializer<?> createContextual(
        DeserializationContext ctxt,
        BeanProperty property) throws JsonMappingException {

        JsonDeserializer<?> ed = elementDeserializer != null ?
            elementDeserializer :
            ctxt.findContextualValueDeserializer(setType.getContentType(), property);
        TypeDeserializer etd = elementTypeDeserializer != null ?
            elementTypeDeserializer.forProperty(property) :
            null;

        return ed == elementDeserializer && etd == elementTypeDeserializer ?
            this :
            new BasicConstSetDeserializer(setType, ed, etd);
    }

    @Override public Object deserializeWithType(
        JsonParser jp,
        DeserializationContext ctxt,
        TypeDeserializer typeDeserializer) throws IOException {

        return typeDeserializer.deserializeTypedFromArray(jp, ctxt);
    }

    @Override public ConstSet<?> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (jp.getCurrentToken() != JsonToken.START_ARRAY) {
            throw ctxt.mappingException(setType.getRawClass());
        }

        JsonDeserializer<?> ed = elementDeserializer;
        TypeDeserializer etd = elementTypeDeserializer;
        List<Object> elements = new ArrayList<>();
        JsonToken token;

        while ((token = jp.nextToken()) != JsonToken.END_ARRAY) {
            Object element;
            if (token == JsonToken.VALUE_NULL) {
                element = null;
            }
            else if (etd == null) {
                element = ed.deserialize(jp, ctxt);
            }
            else {
                element = ed.deserializeWithType(jp, ctxt, etd);
            }
            elements.add(element);
        }
        return BasicCollections.asSet(elements);
    }
}
