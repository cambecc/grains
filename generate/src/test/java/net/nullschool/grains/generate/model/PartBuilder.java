package net.nullschool.grains.generate.model;

import javax.annotation.Generated;
import net.nullschool.grains.GrainBuilder;
import net.nullschool.grains.GrainFactoryRef;

@Generated("net.nullschool.grains.generate.GrainGenerator")
@GrainFactoryRef(PartFactory.class)
public interface PartBuilder extends Compound.Part, GrainBuilder {

    int getMake();

    PartBuilder setMake(int make);

    int getModel();

    PartBuilder setModel(int model);

    PartGrain build();
}
