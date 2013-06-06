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

package net.nullschool.grains.msgpack;

import net.nullschool.collect.MapIterator;
import net.nullschool.grains.*;
import org.msgpack.MessageTypeException;
import org.msgpack.packer.Packer;
import org.msgpack.template.Template;
import org.msgpack.template.TemplateRegistry;
import org.msgpack.unpacker.Unpacker;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;


/**
 * 2013-06-04<p/>
 *
 * @author Cameron Beccario
 */
public class VerboseGrainTemplate<T extends Grain> extends AbstractNullableTemplate<T> {

    private final GrainFactory factory;
    private final Grain defaultValue;
    private final Map<String, Type> propertyTypes = new HashMap<>();

    private volatile Map<String, Template<?>> propertyTemplates;
    private volatile Template<?> extensionTemplate;

    public VerboseGrainTemplate(Class<T> clazz, TemplateRegistry registry) {
        this.factory = GrainTools.factoryFor(clazz);
        this.defaultValue = factory.getDefaultValue();

        // Aggressively register this template so that recursive template construction will succeed when we encounter
        // a type declaration cycle. For example:
        //
        //     class A { B getB(); }
        //     class B { A getA(); }
        //
        // During the construction of A's template, we construct B's template, which in turn tries to construct
        // another template for A, and so on. Aggressive registration will avoid infinite recursion.
        //
        registry.register(clazz, this);

        for (Map.Entry<String, GrainProperty> entry : factory.getBasisProperties().entrySet()) {
            propertyTypes.put(entry.getKey(), entry.getValue().getType());
        }

        this.propertyTemplates = new HashMap<>();
        for (Map.Entry<String, Type> entry : propertyTypes.entrySet()) {
            propertyTemplates.put(entry.getKey(), registry.lookup(entry.getValue()));
        }
        this.extensionTemplate = registry.lookup(Object.class);
    }

    @Override public void writeValue(Packer packer, T grain) throws IOException {

        // Identify the entries to serialize.
        Object[] entriesToWrite = new Object[grain.size() * 2];  // keys and values interleaved: k0, v0, k1, v1, ...
        int count = 0;
        for (MapIterator<String, Object> iter = grain.iterator(); iter.hasNext();) {
            String key = iter.next();
            Object value = iter.value();

            if (!Objects.equals(defaultValue.get(key), value)) {
                // This entry differs from the default value, so we will write this to the stream.
                entriesToWrite[count++] = key;
                entriesToWrite[count++] = value;
            }
        }

        // Write the entries to the MessagePack stream.
        packer.writeMapBegin(count >> 1);
        for (int i = 0; i < count; i++) {
            packer.write((String)entriesToWrite[i]);
            packer.write(entriesToWrite[++i]);
        }
        packer.writeMapEnd();
    }

    @Override public T readValue(Unpacker unpacker, T to) throws IOException {
        GrainBuilder builder = factory.getNewBuilder();
        final int size = unpacker.readMapBegin();
        for (int i = 0; i < size; i++) {
            String key = unpacker.readString();
            Object value;
            Template<?> propertyTemplate = propertyTemplates.get(key);
            if (propertyTemplate != null) {
                try {
                    value = propertyTemplate.read(unpacker, null, false);
                }
                catch (MessageTypeException e) {
                    throw new MessageTypeException(
                        String.format("Exception reading value for key '%s', type %s, for %s",
                            key,
                            propertyTypes.get(key),
                            factory),
                        e);
                }
            }
            else {
                value = unpacker.read(extensionTemplate);
            }
            builder.put(key, value);
        }
        unpacker.readMapEnd();
        @SuppressWarnings("unchecked") T result = (T)builder.build();
        return result;
    }
}
