package net.nullschool.grains.generate.model;

import javax.annotation.Generated;
import net.nullschool.grains.GrainBuilder;
import net.nullschool.grains.GrainFactoryRef;

/**
 * Composition of Composed and GrainBuilder. See {@link ComposedFactory}.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
@GrainFactoryRef(ComposedFactory.class)
public interface ComposedBuilder
    extends Composed, GrainBuilder, SquidBuilder, HydraBuilder {

    //
    // Composed Accessors
    //

    String getId();

    ComposedBuilder setId(String id);

    int getAge();

    ComposedBuilder setAge(int age);

    boolean isGiant();

    ComposedBuilder setGiant(boolean giant);

    int getLegCount();

    ComposedBuilder setLegCount(int legCount);

    String getName();

    ComposedBuilder setName(String name);


    //
    // GrainBuilder Methods
    //

    ComposedGrain build();
}
