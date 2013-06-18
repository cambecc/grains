package net.nullschool.grains.generate.model;

import javax.annotation.Generated;
import net.nullschool.grains.GrainBuilder;
import net.nullschool.grains.GrainFactoryRef;

/**
 * Composition of Hydra and GrainBuilder. See {@link HydraFactory}.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
@GrainFactoryRef(HydraFactory.class)
public interface HydraBuilder
    extends Animal.Hydra, GrainBuilder {

    //
    // Animal.Hydra Accessors
    //

    String getId();

    HydraBuilder setId(String id);

    int getAge();

    HydraBuilder setAge(int age);

    int getLegCount();

    HydraBuilder setLegCount(int legCount);


    //
    // GrainBuilder Methods
    //

    HydraGrain build();
}
