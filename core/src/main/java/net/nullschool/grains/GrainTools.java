package net.nullschool.grains;

import net.nullschool.collect.ConstMap;
import net.nullschool.collect.basic.BasicConstMap;
import net.nullschool.reflect.Property;


/**
 * 2013-03-25<p/>
 *
 * Utility methods for operating on grains.
 *
 * @author Cameron Beccario
 */
public enum GrainTools {;

    public static GrainFactory factoryFor(Class<?> clazz) {
        Class<?> enclosing = clazz.getEnclosingClass();
        if (enclosing != null && GrainFactory.class.isAssignableFrom(enclosing)) {
            return (GrainFactory)enclosing.getEnumConstants()[0];
        }
        GrainFactoryRef ref = clazz.getAnnotation(GrainFactoryRef.class);
        if (ref != null) {
            return (GrainFactory)ref.value().getEnumConstants()[0];
        }

//        for (Type t : clazz.getGenericInterfaces()) {
//            if (t instanceof ParameterizedType) {
//                ParameterizedType pt = (ParameterizedType)t;
//                if (pt.getRawType() == GrainFactory.Ref.class) {
//                    Class<?> factoryClass = (Class<?>)pt.getActualTypeArguments()[0];
//                    return (GrainFactory)factoryClass.getEnumConstants()[0];
//                }
//            }
//        }

        throw new IllegalArgumentException("cannot find factory for " + clazz);
    }

    public static Class<?> grainType(GrainFactory factory) {
        try {
            return factory.getClass().getMethod("getBasis").getReturnType();
        }
        catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("cannot find grain type for factory " + factory);
        }
    }

    public static ConstMap<String, Property> propertyMap(Property... properties) {
        String[] keys = new String[properties.length];
        for (int i = 0; i < properties.length; i++) {
            keys[i] = properties[i].getName();
        }
        return BasicConstMap.asMap(keys, properties);
    }
}
