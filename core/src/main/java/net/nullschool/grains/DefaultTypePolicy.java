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

package net.nullschool.grains;

import net.nullschool.transform.*;
import net.nullschool.collect.*;
import net.nullschool.reflect.*;

import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.*;


/**
 * 2013-05-08<p/>
 *
 * @author Cameron Beccario
 */
public class DefaultTypePolicy implements TypePolicy {

    public static final DefaultTypePolicy INSTANCE = new DefaultTypePolicy();


    private final Set<Class<?>> immutableTypes = new HashSet<>();
    private final Set<Class<?>> immutableBases = new HashSet<>();
    private final Map<Class<?>, Class<?>> translations = new HashMap<>();

    protected void registerType(Class<?> clazz) {
        if (clazz.isArray()) {
            throw new IllegalArgumentException("array type " + clazz + " can never be immutable");
        }
        immutableTypes.add(clazz);
        if (!Modifier.isFinal(clazz.getModifiers())) {
            immutableBases.add(clazz);
        }
    }

    protected void registerTranslation(Class<?> from, Class<?> to) {
        translations.put(from, to);
        registerType(to);
    }

    public DefaultTypePolicy() {
        registerType(boolean.class);
        registerType(byte.class);
        registerType(short.class);
        registerType(int.class);
        registerType(long.class);
        registerType(float.class);
        registerType(double.class);
        registerType(char.class);
        registerType(void.class);
        registerType(Boolean.class);
        registerType(Byte.class);
        registerType(Short.class);
        registerType(Integer.class);
        registerType(Long.class);
        registerType(BigInteger.class);
        registerType(BigDecimal.class);
        registerType(Float.class);
        registerType(Double.class);
        registerType(Character.class);
        registerType(String.class);
        registerType(Void.class);
        registerType(UUID.class);
        registerType(URI.class);
        registerType(Currency.class);
        registerType(Enum.class);
        registerType(ConstCollection.class);
        registerType(ConstMap.class);

        registerTranslation(Collection.class, ConstCollection.class);
        registerTranslation(List.class, ConstList.class);
        registerTranslation(Set.class, ConstSet.class);
        registerTranslation(SortedSet.class, ConstSortedSet.class);
        registerTranslation(Map.class, ConstMap.class);
        registerTranslation(SortedMap.class, ConstSortedMap.class);
    }

    @Override public boolean isConstType(Class<?> clazz) {
        if (immutableTypes.contains(clazz)) {
            return true;
        }
        for (Class<?> base : immutableBases) {
            if (base.isAssignableFrom(clazz)) {
                return true;
            }
        }
        return false;
    }

    @Override public Class<?> asConstType(Class<?> clazz) {
        return translations.get(clazz);
    }

    @Override public <T> T requireConst(T t) {
        if (!isConstType(t.getClass())) {
            throw new IllegalArgumentException("not immutable");
        }
        return t;
    }

    @Override public <T> Transform<T> newTransform(TypeToken<T> token) {
        // UNDONE: construct actual cast functions. For now, this is completely unsafe.
        return new Transform<T>() {
            @Override public T apply(Object o) {
                @SuppressWarnings("unchecked") T t = (T)o;
                return t;
            }
        };
    }
}
