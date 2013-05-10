package net.nullschool.grains.generate;

import net.nullschool.grains.GrainTools;

import java.util.EnumMap;
import java.util.Map;


/**
 * 2013-05-09<p/>
 *
 * @author Cameron Beccario
 */
final class NamingPolicy {

    enum Name {
        grain,
        builder,
        factory,
        grainImpl,
        grainProxy,
        builderImpl,
    }

    String getName(Class<?> schema, Name name) {
        String prefix = GrainTools.targetPackageOf(schema) + '.' + schema.getSimpleName();
        switch (name) {
            case grain:       return prefix + "GrainX";
            case builder:     return prefix + "BuilderX";
            case factory:     return prefix + "FactoryX";
            case grainImpl:   return prefix + "FactoryX$" + schema.getSimpleName() + "GrainImplX";
            case grainProxy:  return prefix + "FactoryX$" + schema.getSimpleName() + "GrainProxyX";
            case builderImpl: return prefix + "FactoryX$" + schema.getSimpleName() + "BuilderImplX";
            default:
                throw new IllegalStateException(String.valueOf(name));
        }
    }

    String getSimpleName(Class<?> schema, Name name) {
        String prefix = schema.getSimpleName();
        switch (name) {
            case grain:       return prefix + "GrainX";
            case builder:     return prefix + "BuilderX";
            case factory:     return prefix + "FactoryX";
            case grainImpl:   return prefix + "GrainImplX";
            case grainProxy:  return prefix + "GrainProxyX";
            case builderImpl: return prefix + "BuilderImplX";
            default:
                throw new IllegalStateException(String.valueOf(name));
        }
    }

    Map<Name, String> getNames(Class<?> schema) {
        Map<Name, String> result = new EnumMap<>(Name.class);
        for (Name name : Name.values()) {
            result.put(name, getName(schema, name));
        }
        return result;
    }

    Map<Name, String> getSimpleNames(Class<?> schema) {
        Map<Name, String> result = new EnumMap<>(Name.class);
        for (Name name : Name.values()) {
            result.put(name, getSimpleName(schema, name));
        }
        return result;
    }
}
