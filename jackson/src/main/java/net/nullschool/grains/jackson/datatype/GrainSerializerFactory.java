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

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.ser.*;
import net.nullschool.grains.Grain;


/**
 * 2013-06-07<p/>
 *
 * @author Cameron Beccario
 */
public class GrainSerializerFactory extends BeanSerializerFactory {

    private static final long serialVersionUID = 1;


    public GrainSerializerFactory(SerializerFactoryConfig config) {
        super(config);
    }

    @Override public SerializerFactory withConfig(SerializerFactoryConfig config) {
        return new GrainSerializerFactory(config);
    }

    @Override public JsonSerializer<Object> createSerializer(
        SerializerProvider provider,
        JavaType type) throws JsonMappingException {

        Class<?> clazz = type.getRawClass();

        if (Grain.class.isAssignableFrom(clazz)) {
            @SuppressWarnings("unchecked") JsonSerializer<Object> serializer =
                (JsonSerializer)new GrainSerializer(clazz.asSubclass(Grain.class));
            return serializer;
        }

        return super.createSerializer(provider, type);
    }
}
