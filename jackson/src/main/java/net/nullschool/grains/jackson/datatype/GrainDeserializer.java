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
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import net.nullschool.collect.ConstCollection;
import net.nullschool.collect.ConstMap;
import net.nullschool.grains.*;

import java.io.IOException;
import java.util.*;

import static net.nullschool.reflect.TypeTools.publicInterfaceOf;


/**
 * 2013-06-07<p/>
 *
 * @author Cameron Beccario
 */
class GrainDeserializer extends StdDeserializer<Grain> implements ResolvableDeserializer {
    // UNDONE: serialization proxy
    private static final long serialVersionUID = 1;

    private static class PropertyReader {
        private final JsonDeserializer<?> deserializer;

        private PropertyReader(JsonDeserializer<?> deserializer) {
            this.deserializer = Objects.requireNonNull(deserializer);
        }
    }


    private final GrainFactory factory;
    private final Map<String, PropertyReader> readers = new HashMap<>();
    private volatile JsonDeserializer<?> extValueDeserializer;
    private volatile JsonDeserializer<?> extArrayDeserializer;
    private volatile JsonDeserializer<?> extObjectDeserializer;

    public GrainDeserializer(Class<? extends Grain> clazz) {
        this(GrainTools.factoryFor(clazz));
    }

    private GrainDeserializer(GrainFactory factory) {
        super(publicInterfaceOf(factory.getDefaultValue().getClass()));
        this.factory = factory;
    }

    @Override public void resolve(DeserializationContext ctxt) throws JsonMappingException {
        for (GrainProperty gp : factory.getBasisProperties().values()) {
            JacksonGrainProperty prop = new JacksonGrainProperty(gp, ctxt.getTypeFactory(), getValueClass());
            JsonDeserializer<?> deserializer = ctxt.findContextualValueDeserializer(prop.getType(), prop);

            readers.put(prop.getName(), new PropertyReader(deserializer));
        }

        extValueDeserializer = ctxt.findContextualValueDeserializer(ctxt.constructType(Object.class), null);
        extArrayDeserializer = ctxt.findContextualValueDeserializer(ctxt.constructType(ConstCollection.class), null);
        extObjectDeserializer = ctxt.findContextualValueDeserializer(ctxt.constructType(ConstMap.class), null);
    }

    @Override public Grain deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken token = jp.getCurrentToken();
        if (token == JsonToken.START_OBJECT) {
            token = jp.nextToken();
        }
        else if (token != JsonToken.FIELD_NAME) {
            throw ctxt.mappingException(getValueClass());
        }

        if (token == JsonToken.END_OBJECT) {
            return factory.getDefaultValue();
        }

        GrainBuilder builder = factory.getNewBuilder();

        do {
            String key = jp.getCurrentName();
            PropertyReader reader = readers.get(key);
            token = jp.nextToken();
            Object value;
            if (token == JsonToken.VALUE_NULL) {
                value = null;
            }
            else if (reader != null) {
                value = reader.deserializer.deserialize(jp, ctxt);
            }
            else {
                switch (token) {
                    case START_ARRAY:
                        value = extArrayDeserializer.deserialize(jp, ctxt);
                        break;
                    case START_OBJECT:
                        value = extObjectDeserializer.deserialize(jp, ctxt);
                        break;
                    default:
                        value = extValueDeserializer.deserialize(jp, ctxt);
                }
            }

            builder.put(key, value);
        } while (jp.nextToken() == JsonToken.FIELD_NAME);

        return builder.build();
    }

    @Override public String toString() {
        return "GrainDeserializer(" + getValueClass().getName() + ")";
    }
}
