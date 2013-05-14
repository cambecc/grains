package net.nullschool.grains.generate.model;

import java.util.Collection;
import java.util.Map;
import javax.annotation.Generated;
import net.nullschool.collect.ConstMap;
import net.nullschool.grains.Grain;
import net.nullschool.grains.GrainFactoryRef;

/**
 * Composition of Part and Grain. See {@link PartFactory}.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
@GrainFactoryRef(PartFactory.class)
public interface PartGrain extends Compound.Part, Grain {

    //
    // Compound.Part Accessors
    //

    int getMake();

    PartGrain withMake(int make);

    int getModel();

    PartGrain withModel(int model);


    //
    // Grain Methods
    //

    ConstMap<String, Object> extensions();

    PartGrain with(String key, Object value);

    PartGrain withAll(Map<? extends String, ?> map);

    PartGrain without(Object key);

    PartGrain withoutAll(Collection<?> keys);

    PartBuilder newBuilder();
}
