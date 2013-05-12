package net.nullschool.grains;

import net.nullschool.collect.ConstMap;
import net.nullschool.collect.basic.BasicConstMap;


/**
 * 2013-03-25<p/>
 *
 * Utility methods for operating on grains.
 *
 * @author Cameron Beccario
 */
public enum GrainTools {;

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
        return BasicConstMap.asMap(keys, properties);
    }
}
