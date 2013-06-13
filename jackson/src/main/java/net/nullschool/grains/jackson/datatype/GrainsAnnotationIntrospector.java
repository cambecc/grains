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

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.introspect.*;
import net.nullschool.grains.Grain;
import net.nullschool.reflect.PublicInterfaceRef;
import net.nullschool.reflect.TypeTools;

import java.util.HashMap;
import java.util.Map;


/**
 * 2013-06-13<p/>
 *
 * Introspector for finding grain types and serializers.
 *
 * @author Cameron Beccario
 */
public class GrainsAnnotationIntrospector extends NopAnnotationIntrospector {

    private static final long serialVersionUID = 1;

    private static Class<?> valueOf(PublicInterfaceRef ref) {
        return ref != null ? ref.value() : null;
    }

    /**
     * Returns the contents of the {@link PublicInterfaceRef} annotation on the specified annotated object, or null
     * if it does not exist.
     */
    private static Class<?> publicInterfaceOf(Annotated annotated) {
        return Grain.class.isAssignableFrom(annotated.getRawType()) ?
            valueOf(annotated.getAnnotation(PublicInterfaceRef.class)) :
            null;
    }

    /**
     * Returns the contents of the {@link PublicInterfaceRef} annotation on the specified class, or null if it does
     * not exist.
     */
    private static Class<?> publicInterfaceOf(Class<?> clazz) {
        return Grain.class.isAssignableFrom(clazz) ?
            valueOf(clazz.getAnnotation(PublicInterfaceRef.class)) :
            null;
    }


    @Override public Class<?> findSerializationType(Annotated annotated) {
        return publicInterfaceOf(annotated);
    }

    @Override public Class<?> findSerializationKeyType(Annotated annotated, JavaType keyType) {
        return keyType != null ? publicInterfaceOf(keyType.getRawClass()) : null;
    }

    @Override public Class<?> findSerializationContentType(Annotated annotated, JavaType contentType) {
        return contentType != null ? publicInterfaceOf(contentType.getRawClass()) : null;
    }

    private transient volatile Map<Class, JsonSerializer> serializerMemos = new HashMap<>();

    @Override public Object findSerializer(Annotated annotated) {
        Class<?> clazz = annotated.getRawType();

        if (Grain.class.isAssignableFrom(clazz)) {
            JsonSerializer serializer = serializerMemos.get(clazz);
            if (serializer == null) {
                // No serializer found, so try finding one using the "public interface" of the grain.
                Class<?> grainClass = TypeTools.publicInterfaceOf(clazz);
                serializer = serializerMemos.get(grainClass);
                if (serializer == null) {
                    // No serializer found for public interface either, so make a new one.
                    serializer = new GrainSerializer(grainClass.asSubclass(Grain.class));
                }
                synchronized (this) {
                    // It's okay to have a race condition where two threads create the same serializer twice. We
                    // simply want to avoid serializer construction upon every invocation of findSerializer.
                    serializerMemos = new HashMap<>(serializerMemos);
                    serializerMemos.put(grainClass, serializer);
                    serializerMemos.put(clazz, serializer);
                }
            }
            return serializer;
        }

        return null;
    }
}
