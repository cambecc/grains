package net.nullschool.grains.generate.model;

import javax.annotation.Generated;
import net.nullschool.grains.GrainBuilder;
import net.nullschool.grains.GrainFactoryRef;

/**
 * Composition of Node and GrainBuilder. See {@link NodeFactory}.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
@GrainFactoryRef(NodeFactory.class)
public interface NodeBuilder
    extends Complete.Node, GrainBuilder {

    //
    // Complete.Node Accessors
    //

    int getId();

    NodeBuilder setId(int id);

    CompleteGrain getComplete();

    NodeBuilder setComplete(CompleteGrain complete);


    //
    // GrainBuilder Methods
    //

    NodeGrain build();
}
