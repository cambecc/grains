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

import net.nullschool.collect.ConstMap;
import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-03-25<p/>
 *
 * Utility methods for operating on grains.
 *
 * @author Cameron Beccario
 */
public class GrainTools {

    private GrainTools() {
        throw new AssertionError();
    }

    /**
     * Returns the target package for the specified schema. The target package is the package where generated grain
     * classes are located by convention. If the {@link GrainSchema#targetPackage targetPackage} attribute has been
     * defined, then that value is returned. Otherwise, the package of the schema itself is returned.<p/>
     *
     * If {@code schema} has not been annotated with {@link GrainSchema}, this method returns null.
     *
     * @param schema the schema to get the target package for.
     * @return the package as a dot-delimited string, or null if the class is not a grain schema.
     * @throws NullPointerException if schema is null.
     */
    public static String targetPackageOf(Class<?> schema) {
        GrainSchema annotation = schema.getAnnotation(GrainSchema.class);
        if (annotation == null) {
            return null;
        }
        return !annotation.targetPackage().isEmpty() ? annotation.targetPackage() : schema.getPackage().getName();
    }

    private static GrainFactory factoryInstance(Class<? extends GrainFactory> clazz) {
        if (clazz.isEnum()) {
            // @SuppressWarnings("unchecked") Object instance = Enum.valueOf((Class<? extends Enum>)clazz, "INSTANCE");
            Object instance = clazz.getEnumConstants()[0];
            return (GrainFactory)instance;
        }
        throw new IllegalArgumentException(
            "expected factory implementation to follow enum singleton pattern: " + clazz);
    }

    public static GrainFactory factoryFor(Class<?> clazz) {
        // UNDONE: this throws if factory cannot be found, but targetPackageOf returns null if package cannot be found.
        // CONSIDER: also possible to check if the enclosing class is a GrainFactory, like would be for *GrainImpl.
        GrainFactoryRef ref = clazz.getAnnotation(GrainFactoryRef.class);
        if (ref != null) {
            return factoryInstance(ref.value());
        }
        if (GrainFactory.class.isAssignableFrom(clazz)) {
            return factoryInstance(clazz.asSubclass(GrainFactory.class));
        }
        // CONSIDER: also map GrainSchemas to their factories? would need a ClassLoader as optional parameter.
        throw new IllegalArgumentException("cannot find factory for " + clazz);
    }

    /**
     * Returns a map of property names to property descriptors built from the specified array of property descriptors.
     * Each property's name becomes its associated key in the resulting map.
     *
     * @param properties the properties to build the map from.
     * @return an immutable map of names to property descriptors.
     * @throws NullPointerException if the array is null or contains null.
     */
    public static ConstMap<String, GrainProperty> asPropertyMap(GrainProperty... properties) {
        String[] keys = new String[properties.length];
        for (int i = 0; i < properties.length; i++) {
            keys[i] = properties[i].getName();
        }
        return asMap(keys, properties);
    }
}
