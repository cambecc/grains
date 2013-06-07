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

package net.nullschool.grains.jackson.datatype.ser;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.*;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import net.nullschool.collect.MapIterator;
import net.nullschool.grains.*;
import net.nullschool.grains.jackson.datatype.GrainAsBeanProperty;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;


/**
 * 2013-06-07<p/>
 *
 * @author Cameron Beccario
 */
public class GrainSerializer extends StdSerializer<Grain> implements ResolvableSerializer {

    private final GrainFactory factory;
    private final ObjectIdWriter objectIdWriter;
    private final Grain defaultValue;
    private final Map<String, PropertyWriter> writers;
    private volatile PropertySerializerMap dynamicSerializers = PropertySerializerMap.emptyMap();

    private static class PropertyWriter {
        private final JsonSerializer<Object> serializer;
        private final TypeSerializer typeSerializer;

        private PropertyWriter(JsonSerializer<Object> serializer, TypeSerializer typeSerializer) {
            this.serializer = Objects.requireNonNull(serializer);
            this.typeSerializer = typeSerializer;
        }
    }

    public GrainSerializer(JavaType type, ObjectIdWriter objectIdWriter) {
        super(type);
        this.objectIdWriter = objectIdWriter;

        this.factory = GrainTools.factoryFor(type.getRawClass());
        this.defaultValue = factory.getDefaultValue();
        this.writers = new HashMap<>();
    }

    @Override public boolean usesObjectId() {
        return objectIdWriter != null;
    }

    @Override public void resolve(SerializerProvider provider) throws JsonMappingException {
        for (Map.Entry<String, GrainProperty> entry : factory.getBasisProperties().entrySet()) {
            String name = entry.getKey();
            GrainProperty property = entry.getValue();
            JavaType type = provider.constructType(property.getType());
            GrainAsBeanProperty gabp = new GrainAsBeanProperty(property, type, defaultValue.getClass()); // UNDONE: grain type
            JsonSerializer<Object> serializer = provider.findValueSerializer(type, gabp);

            if (type.isContainerType() && serializer instanceof ContainerSerializer) {
                ContainerSerializer<Object> cs = (ContainerSerializer<Object>)serializer;
                TypeSerializer ts = type.getContentType().getTypeHandler();
                if (ts != null) {
                    // UNDONE
                    @SuppressWarnings("unchecked") JsonSerializer<Object> s =
                        (JsonSerializer<Object>)cs.withValueTypeSerializer(ts);
                    serializer = s;
                }
            }

            writers.put(name, new PropertyWriter(serializer, null /*UNDONE*/));
        }
    }

    @SuppressWarnings("deprecation")  // UNDONE
    @Override public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        ObjectNode node = createSchemaNode("object", true);
        ObjectNode propNode = node.objectNode();
        for (Map.Entry<String, GrainProperty> entry : factory.getBasisProperties().entrySet()) {
            String name = entry.getKey();
            Type type = entry.getValue().getType();
            JsonSerializer<Object> serializer = writers.get(name).serializer;
            JsonNode schemaNode;
            if (serializer instanceof SchemaAware) {
                schemaNode = ((SchemaAware)serializer).getSchema(provider, type);
            }
            else {
                schemaNode = com.fasterxml.jackson.databind.jsonschema.JsonSchema.getDefaultSchemaNode();
            }
            propNode.put(name, schemaNode);
        }
        node.put("properties", propNode);
        return node;
    }

    @Override public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
        throw new UnsupportedOperationException();
//        JsonObjectFormatVisitor grainVisitor = (visitor == null) ? null : visitor.expectObjectFormat(typeHint);
//        if (grainVisitor != null) {
//            for (Map.Entry<String, GrainProperty> entry : factory.getBasisProperties().entrySet()) {
//            }
//        }
    }

    private JsonSerializer<Object> deriveSerializer(Class<?> clazz, SerializerProvider provider) throws JsonMappingException {
        // UNDONE: race condition by callers
        PropertySerializerMap.SerializerAndMapResult result =
            dynamicSerializers.findAndAddSerializer(clazz, provider, null);
        dynamicSerializers = result.map;
        return result.serializer;
    }

    private void serializeEntries(Grain grain, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        for (MapIterator<String, Object> iter = grain.iterator(); iter.hasNext();) {
            String key = iter.next();
            Object value = iter.value();
            if (value != null && !value.equals(defaultValue.get(key))) {

                // Write key.
                jgen.writeFieldName(key);

                try {
                    // Write value.
                    PropertyWriter writer = writers.get(key);
                    if (writer != null) {
                        if (writer.typeSerializer == null) {
                            writer.serializer.serialize(value, jgen, provider);
                        }
                        else {
                            writer.serializer.serializeWithType(value, jgen, provider, writer.typeSerializer);
                        }
                    }
                    else {
                        Class<?> valueClass = value.getClass();
                        JsonSerializer<Object> serializer = dynamicSerializers.serializerFor(valueClass);
                        if (serializer == null) {
                            serializer = deriveSerializer(valueClass, provider);
                        }
                        serializer.serialize(value, jgen, provider);
                    }
                }
                catch (Exception e) {
                    wrapAndThrow(provider, e, grain, key);
                }
            }
        }
    }

    @Override public void serialize(Grain grain, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (usesObjectId()) {
            serializeWithObjectId(grain, jgen, provider);
            return;
        }
        jgen.writeStartObject();
        serializeEntries(grain, jgen, provider);
        jgen.writeEndObject();
    }

    @Override public void serializeWithType(
        Grain grain,
        JsonGenerator jgen,
        SerializerProvider provider,
        TypeSerializer typeSer) throws IOException {

        if (usesObjectId()) {
            serializeWithObjectIdAndType(grain, jgen, provider, typeSer);
            return;
        }
        typeSer.writeTypePrefixForObject(grain, jgen);
        serializeEntries(grain, jgen, provider);
        typeSer.writeTypeSuffixForObject(grain, jgen);
    }

    private void serializeWithObjectId(
        Grain value,
        JsonGenerator jgen,
        SerializerProvider provider) throws IOException {

        throw new UnsupportedOperationException();
    }

    private void serializeWithObjectIdAndType(
        Grain value,
        JsonGenerator jgen,
        SerializerProvider provider,
        TypeSerializer typeSer) throws IOException {

        throw new UnsupportedOperationException();
    }

    @Override public String toString() {
        return "GrainSerializer(" + handledType().getName() + ")";
    }
}
