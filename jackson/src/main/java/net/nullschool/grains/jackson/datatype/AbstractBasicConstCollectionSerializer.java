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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 2013-06-11<p/>
 *
 * @author Cameron Beccario
 */
abstract class AbstractBasicConstCollectionSerializer<T> extends StdDeserializer<T> implements ContextualDeserializer {

    private static final long serialVersionUID = 1;


    final CollectionType collectionType;
    final JsonDeserializer<?> elementDeserializer;
    final TypeDeserializer elementTypeDeserializer;

    AbstractBasicConstCollectionSerializer(
        CollectionType collectionType,
        JsonDeserializer<?> elementDeserializer,
        TypeDeserializer elementTypeDeserializer) {

        super(collectionType.getRawClass());
        this.collectionType = collectionType;
        this.elementDeserializer = elementDeserializer;
        this.elementTypeDeserializer = elementTypeDeserializer;
    }

    abstract JsonDeserializer<?> withDeserializers(
        JsonDeserializer<?> elementDeserializer,
        TypeDeserializer elementTypeDeserializer);

    @Override public JsonDeserializer<?> createContextual(
        DeserializationContext ctxt,
        BeanProperty property) throws JsonMappingException {

        JsonDeserializer<?> ed = elementDeserializer != null ?
            elementDeserializer :
            ctxt.findContextualValueDeserializer(collectionType.getContentType(), property);
        TypeDeserializer etd = elementTypeDeserializer != null ?
            elementTypeDeserializer.forProperty(property) :
            null;

        return withDeserializers(ed, etd);
    }

    @Override public Object deserializeWithType(
        JsonParser jp,
        DeserializationContext ctxt,
        TypeDeserializer typeDeserializer) throws IOException {

        return typeDeserializer.deserializeTypedFromArray(jp, ctxt);
    }

    abstract T emptyResult();

    abstract T resultOf(Object element);

    abstract T asResult(List<Object> elements);

    @Override public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken token = jp.getCurrentToken();
        if (token != JsonToken.START_ARRAY) {
            throw ctxt.mappingException(collectionType.getRawClass());
        }

        if ((token = jp.nextToken()) == JsonToken.END_ARRAY) {
            return emptyResult();
        }

        JsonDeserializer<?> ed = elementDeserializer;
        TypeDeserializer etd = elementTypeDeserializer;

        List<Object> elements = null;
        Object element;
        do {
            element = token != JsonToken.VALUE_NULL ?
                etd == null ?
                    ed.deserialize(jp, ctxt) :
                    ed.deserializeWithType(jp, ctxt, etd) :
                null;

            token = jp.nextToken();
            if (elements == null) {
                if (token == JsonToken.END_ARRAY) {
                    return resultOf(element);
                }
                elements = new ArrayList<>();
            }
            elements.add(element);
        } while (token != JsonToken.END_ARRAY);

        return asResult(elements);
    }
}
