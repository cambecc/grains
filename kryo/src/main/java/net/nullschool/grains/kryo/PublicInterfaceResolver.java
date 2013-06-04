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

package net.nullschool.grains.kryo;

import com.esotericsoftware.kryo.ClassResolver;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.DefaultClassResolver;
import com.esotericsoftware.kryo.util.ObjectMap;
import net.nullschool.reflect.PublicInterfaceRef;

import java.lang.reflect.Modifier;


/**
 * 2013-06-02<p/>
 *
 * A Kryo {@link ClassResolver} that processes classes using their {@link PublicInterfaceRef public interface},
 * if defined. This ensures Kryo encodes objects using their publicly exported types rather than their private internal
 * implementations that may evolve over time. Generated grain implementations and const collections are particularly
 * susceptible to this condition. For example, {@code OrderGrainImpl} should be written as {@code OrderGrain},
 * {@code BasicListN} should be written as {@code BasicConstList}, etc. Using the public interface also makes it
 * possible to register these classes with Kryo.<p/>
 *
 * Note: instances of this resolver are not thread safe.
 *
 * @author Cameron Beccario
 */
public class PublicInterfaceResolver extends DefaultClassResolver implements ClassResolver {

    private final ObjectMap<Class<?>, Class<?>> memos = new ObjectMap<>();

    /**
     * Derives the type that represents the public interface of the specified type. If no public interface can be
     * found, then the specified type is returned as-is.<p/>
     *
     * This implementation inspects the {@link PublicInterfaceRef} annotation declared directly on the specified type,
     * if any.
     *
     * @param type the type.
     * @return the type's publicly exported interface.
     */
    protected Class<?> derivePublicInterface(Class<?> type) {
        PublicInterfaceRef pi = type.getAnnotation(PublicInterfaceRef.class);
        return pi != null ? pi.value() : type;
    }

    protected Class<?> publicInterfaceOf(Class<?> type) {
        if (type == null || Modifier.isPublic(type.getModifiers())) {
            // A public type is its own public interface, so just return it.
            return type;
        }
        Class<?> memo = memos.get(type);
        if (memo == null) {
            memos.put(type, memo = derivePublicInterface(type));
        }
        return memo;
    }

    @Override public Registration registerImplicit(Class type) {
        return super.registerImplicit(publicInterfaceOf(type));
    }

    @Override public Registration getRegistration(Class type) {
        return super.getRegistration(publicInterfaceOf(type));
    }

    @Override public Registration writeClass(Output output, Class type) {
        return super.writeClass(output, publicInterfaceOf(type));
    }
}
