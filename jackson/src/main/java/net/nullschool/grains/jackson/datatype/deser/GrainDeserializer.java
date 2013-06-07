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

package net.nullschool.grains.jackson.datatype.deser;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import net.nullschool.collect.ConstCollection;
import net.nullschool.collect.ConstMap;
import net.nullschool.grains.*;
import net.nullschool.grains.jackson.datatype.GrainAsBeanProperty;

import java.io.IOException;
import java.util.*;


/**
 * 2013-06-07<p/>
 *
 * @author Cameron Beccario
 */
public class GrainDeserializer extends StdDeserializer<Grain> implements /*ContextualDeserializer,*/ ResolvableDeserializer {

    private static final long serialVersionUID = 1;


    private final JavaType grainType;
    private final ObjectIdReader objectIdReader;
    private final GrainFactory factory;
    private final Map<String, PropertyReader> readers;
    private volatile JsonDeserializer<?> extensionValueDeserializer;
    private volatile JsonDeserializer<?> extensionArrayDeserializer;
    private volatile JsonDeserializer<?> extensionObjectDeserializer;

    private static class PropertyReader {
        private final JsonDeserializer<?> deserializer;
        private final TypeDeserializer typeDeserializer;

        private PropertyReader(JsonDeserializer<?> deserializer, TypeDeserializer typeDeserializer) {
            this.deserializer = Objects.requireNonNull(deserializer);
            this.typeDeserializer = typeDeserializer;
        }
    }

    public GrainDeserializer(JavaType grainType, ObjectIdReader objectIdReader) {
        super(grainType);
        this.grainType = grainType;
        this.objectIdReader = objectIdReader;
        this.factory = GrainTools.factoryFor(grainType.getRawClass());
        this.readers = new HashMap<>();
    }

    @Override public void resolve(DeserializationContext ctxt) throws JsonMappingException {
        for (Map.Entry<String, GrainProperty> entry : factory.getBasisProperties().entrySet()) {
            String name = entry.getKey();
            GrainProperty property = entry.getValue();
            JavaType type = ctxt.constructType(property.getType());
            GrainAsBeanProperty gabp = new GrainAsBeanProperty(property, type, grainType.getClass());  // UNDONE: grain type
            JsonDeserializer<?> deserializer = ctxt.findContextualValueDeserializer(type, gabp);

            readers.put(name, new PropertyReader(deserializer, null /*UNDONE*/));
        }

        extensionValueDeserializer = ctxt.findContextualValueDeserializer(ctxt.constructType(Object.class), null);
        extensionArrayDeserializer = ctxt.findContextualValueDeserializer(ctxt.constructType(ConstCollection.class), null);
        extensionObjectDeserializer = ctxt.findContextualValueDeserializer(ctxt.constructType(ConstMap.class), null);
    }

    @Override public Grain deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken token = jp.getCurrentToken();
        if (token == JsonToken.START_OBJECT) {
            token = jp.nextToken();
            if (token != JsonToken.FIELD_NAME && token != JsonToken.END_OBJECT) {
                throw ctxt.mappingException(grainType.getRawClass());
            }
        }
        else if (token != JsonToken.FIELD_NAME) {
            throw ctxt.mappingException(grainType.getRawClass());
        }

        GrainBuilder builder = factory.getNewBuilder();

        while (token == JsonToken.FIELD_NAME) {
            String key = jp.getCurrentName();

            Object value;
            if (jp.nextToken() == JsonToken.VALUE_NULL) {
                value = null;
            }
            else {
                PropertyReader reader = readers.get(key);
                if (reader != null) {
                    value = reader.typeDeserializer != null ?
                        reader.deserializer.deserializeWithType(jp, ctxt, reader.typeDeserializer) :
                        reader.deserializer.deserialize(jp, ctxt);
                }
                else {
                    switch (jp.getCurrentToken()) {
                        case START_ARRAY:
                            value = extensionArrayDeserializer.deserialize(jp, ctxt);
                            break;
                        case START_OBJECT:
                            value = extensionObjectDeserializer.deserialize(jp, ctxt);
                            break;
                        default:
                            value = extensionValueDeserializer.deserialize(jp, ctxt);
                    }
                }
            }

            builder.put(key, value);
            token = jp.nextToken();
        }
        return builder.build();
    }
}
