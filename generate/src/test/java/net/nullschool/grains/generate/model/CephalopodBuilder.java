package net.nullschool.grains.generate.model;

import javax.annotation.Generated;
import net.nullschool.grains.GrainBuilder;
import net.nullschool.grains.GrainFactoryRef;

/**
 * Composition of Cephalopod and GrainBuilder. See {@link CephalopodFactory}.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
@GrainFactoryRef(CephalopodFactory.class)
public interface CephalopodBuilder
    extends Animal.Cephalopod, GrainBuilder {

    //
    // Animal.Cephalopod Accessors
    //

    String getId();

    CephalopodBuilder setId(String id);

    int getLegCount();

    CephalopodBuilder setLegCount(int legCount);


    //
    // GrainBuilder Methods
    //

    CephalopodGrain build();
}
