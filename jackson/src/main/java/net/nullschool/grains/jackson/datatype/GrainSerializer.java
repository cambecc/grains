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
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.*;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import net.nullschool.collect.MapIterator;
import net.nullschool.grains.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static net.nullschool.reflect.TypeTools.*;

/**
 * 2013-06-07<p/>
 *
 * @author Cameron Beccario
 */
class GrainSerializer extends StdSerializer<Grain> implements ResolvableSerializer {

    private static class PropertyWriter {
        private final String name;
        private final JsonSerializer<Object> serializer;

        private PropertyWriter(String name, JsonSerializer<Object> serializer) {
            this.name = name;
            this.serializer = Objects.requireNonNull(serializer);
        }
    }


    private final GrainFactory factory;
    private final Grain defaultValue;
    private final AtomicBoolean isResolved = new AtomicBoolean();
    private volatile PropertyWriter[] writers;
    private volatile PropertySerializerMap lateSerializerMemos = PropertySerializerMap.emptyMap();

    public GrainSerializer(Class<? extends Grain> clazz) {
        this(GrainTools.factoryFor(clazz));
    }

    private GrainSerializer(GrainFactory factory) {
        super(publicInterfaceOf(factory.getDefaultValue().getClass()), false);
        this.factory = factory;
        this.defaultValue = factory.getDefaultValue();
    }

    @Override public void resolve(SerializerProvider provider) throws JsonMappingException {
        if (isResolved.getAndSet(true)) {
            // This serializer has already been resolved so don't resolve again; avoids stack overflow.
            return;
        }
        List<PropertyWriter> writers = new ArrayList<>();
        for (GrainProperty gp : factory.getBasisProperties().values()) {
            JacksonGrainProperty prop = new JacksonGrainProperty(gp, provider.getTypeFactory(), handledType());
            JsonSerializer<Object> serializer = provider.findValueSerializer(prop.getType(), prop);

            writers.add(new PropertyWriter(prop.getName(), serializer));
        }
        this.writers = writers.toArray(new PropertyWriter[writers.size()]);
    }

    private JsonSerializer<Object> findLateSerializer(
        Class<?> valueClass,
        SerializerProvider provider) throws JsonMappingException {

        JsonSerializer<Object> serializer = lateSerializerMemos.serializerFor(valueClass);
        if (serializer == null) {
            serializer = provider.findValueSerializer(valueClass, null);
            lateSerializerMemos = lateSerializerMemos.newWith(valueClass, serializer);
        }
        return serializer;
    }

    private void serializeProperty(
        PropertyWriter writer,
        Grain grain,
        JsonGenerator jgen,
        SerializerProvider provider) throws IOException {

        String key = writer.name;
        Object value = grain.get(key);
        if (value != null && !value.equals(defaultValue.get(key))) {
            // This property's value is not the default value, so write it to the stream.
            jgen.writeFieldName(key);
            writer.serializer.serialize(value, jgen, provider);
        }
    }

    private void serializeExtension(
        String key,
        Object value,
        JsonGenerator jgen,
        SerializerProvider provider) throws IOException {

        if (value != null) {
            // This extension's value is not the default value _null_, so write it to the stream.
            jgen.writeFieldName(key);
            JsonSerializer<Object> serializer = findLateSerializer(value.getClass(), provider);
            serializer.serialize(value, jgen, provider);
        }
    }

    private void serializeEntries(Grain grain, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        // Write all basis properties.
        for (PropertyWriter writer : writers) {
            try {
                serializeProperty(writer, grain, jgen, provider);
            }
            catch (Exception e) {
                wrapAndThrow(provider, e, grain, writer.name);
            }
        }

        // Write all extensions.
        for (MapIterator<String, Object> iter = grain.extensions().iterator(); iter.hasNext();) {
            String key = iter.next();
            try {
                serializeExtension(key, iter.value(), jgen, provider);
            }
            catch (Exception e) {
                wrapAndThrow(provider, e, grain, key);
            }
        }
    }

    @Override public void serialize(Grain grain, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        serializeEntries(grain, jgen, provider);
        jgen.writeEndObject();
    }

    @Override public void serializeWithType(
        Grain grain,
        JsonGenerator jgen,
        SerializerProvider provider,
        TypeSerializer typeSer) throws IOException {

        typeSer.writeTypePrefixForObject(grain, jgen);
        serializeEntries(grain, jgen, provider);
        typeSer.writeTypeSuffixForObject(grain, jgen);
    }

    @Override public String toString() {
        return "GrainSerializer(" + handledType().getName() + ")";
    }

//    @SuppressWarnings("deprecation")  // UNDONE
//    @Override public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
//        ObjectNode node = createSchemaNode("object", true);
//        ObjectNode propNode = node.objectNode();
//        for (Map.Entry<String, GrainProperty> entry : factory.getBasisProperties().entrySet()) {
//            String name = entry.getKey();
//            Type type = entry.getValue().getType();
//            JsonSerializer<Object> serializer = writers.get(name).serializer;
//            JsonNode schemaNode;
//            if (serializer instanceof SchemaAware) {
//                schemaNode = ((SchemaAware)serializer).getSchema(provider, type);
//            }
//            else {
//                schemaNode = com.fasterxml.jackson.databind.jsonschema.JsonSchema.getDefaultSchemaNode();
//            }
//            propNode.put(name, schemaNode);
//        }
//        node.put("properties", propNode);
//        return node;
//    }

//    @Override public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
//        throw new UnsupportedOperationException();
//    }
}
