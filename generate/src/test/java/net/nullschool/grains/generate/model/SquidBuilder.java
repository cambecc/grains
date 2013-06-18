package net.nullschool.grains.generate.model;

import javax.annotation.Generated;
import net.nullschool.grains.GrainBuilder;
import net.nullschool.grains.GrainFactoryRef;

/**
 * Composition of Squid and GrainBuilder. See {@link SquidFactory}.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
@GrainFactoryRef(SquidFactory.class)
public interface SquidBuilder
    extends Animal.Squid, GrainBuilder, CephalopodBuilder {

    //
    // Animal.Squid Accessors
    //

    String getId();

    SquidBuilder setId(String id);

    boolean isGiant();

    SquidBuilder setGiant(boolean giant);

    int getLegCount();

    SquidBuilder setLegCount(int legCount);


    //
    // GrainBuilder Methods
    //

    SquidGrain build();
}
