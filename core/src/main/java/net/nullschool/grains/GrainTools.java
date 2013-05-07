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

    private static GrainFactory factoryInstance(Class<? extends GrainFactory> clazz) {
        if (clazz.isEnum()) {
            // @SuppressWarnings("unchecked") Object instance = Enum.valueOf((Class<? extends Enum>)clazz, "INSTANCE");
            Object instance = clazz.getEnumConstants()[0];
            return (GrainFactory)instance;
        }
        throw new IllegalArgumentException();
    }

    public static GrainFactory factoryFor(Class<?> clazz) {
        if (GrainFactory.class.isAssignableFrom(clazz)) {
            return factoryInstance(clazz.asSubclass(GrainFactory.class));
        }
//        Class<?> enclosing = clazz.getEnclosingClass();
//        if (enclosing != null && GrainFactory.class.isAssignableFrom(enclosing)) {
//            return factoryInstance(enclosing.asSubclass(GrainFactory.class));
//        }
        GrainFactoryRef ref = clazz.getAnnotation(GrainFactoryRef.class);
        if (ref != null) {
            return factoryInstance(ref.value());
        }
        // UNDONE: clazz has GrainSchema annotation. need to load class with ClassLoader as optional parameter.
        throw new IllegalArgumentException("cannot find factory for " + clazz);
    }

    public static ConstMap<String, GrainProperty> propertyMap(GrainProperty... properties) {
        String[] keys = new String[properties.length];
        for (int i = 0; i < properties.length; i++) {
            keys[i] = properties[i].getName();
        }
        return BasicConstMap.asMap(keys, properties);
    }
}
