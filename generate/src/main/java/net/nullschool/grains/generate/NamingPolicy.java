package net.nullschool.grains.generate;

import net.nullschool.grains.GrainTools;


/**
 * 2013-05-09<p/>
 *
 * @author Cameron Beccario
 */
final class NamingPolicy {

    enum Kind {
        grain,
        grainImpl,
        builder,
        builderImpl,
        factory,
        proxy,
    }

    private String prefix(Class<?> schema) {
        return GrainTools.targetPackageOf(schema) + '.' + schema.getSimpleName();
    }

    public String name(Class<?> schema, Kind kind) {
        switch (kind) {
            case grain:       return prefix(schema) + "Grain";
            case builder:     return prefix(schema) + "Builder";
            case factory:     return prefix(schema) + "Factory";
            case grainImpl:   return prefix(schema) + "Factory$" + schema.getSimpleName() + "GrainImpl";
            case builderImpl: return prefix(schema) + "Factory$" + schema.getSimpleName() + "BuilderImpl";
            case proxy:       return prefix(schema) + "Factory$" + schema.getSimpleName() + "GrainProxy";
            default:
                throw new IllegalStateException(String.valueOf(kind));
        }
    }
}
