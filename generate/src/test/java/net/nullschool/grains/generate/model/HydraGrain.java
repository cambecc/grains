package net.nullschool.grains.generate.model;

import java.util.Collection;
import java.util.Map;
import javax.annotation.Generated;
import net.nullschool.collect.ConstMap;
import net.nullschool.grains.Grain;
import net.nullschool.grains.GrainFactoryRef;

/**
 * Composition of Hydra and Grain. See {@link HydraFactory}.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
@GrainFactoryRef(HydraFactory.class)
public interface HydraGrain
    extends Animal.Hydra, Grain {

    //
    // Animal.Hydra Accessors
    //

    String getId();

    HydraGrain withId(String id);

    int getAge();

    HydraGrain withAge(int age);

    int getLegCount();

    HydraGrain withLegCount(int legCount);


    //
    // Grain Methods
    //

    ConstMap<String, Object> extensions();

    HydraGrain with(String key, Object value);

    HydraGrain withAll(Map<? extends String, ?> map);

    HydraGrain without(Object key);

    HydraGrain withoutAll(Collection<?> keys);

    HydraBuilder newBuilder();
}
