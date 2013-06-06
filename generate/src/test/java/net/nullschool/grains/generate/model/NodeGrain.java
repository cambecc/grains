package net.nullschool.grains.generate.model;

import java.util.Collection;
import java.util.Map;
import javax.annotation.Generated;
import net.nullschool.collect.ConstMap;
import net.nullschool.grains.Grain;
import net.nullschool.grains.GrainFactoryRef;

/**
 * Composition of Node and Grain. See {@link NodeFactory}.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
@GrainFactoryRef(NodeFactory.class)
public interface NodeGrain extends Complete.Node, Grain {

    //
    // Complete.Node Accessors
    //

    int getId();

    NodeGrain withId(int id);

    CompleteGrain getComplete();

    NodeGrain withComplete(CompleteGrain complete);


    //
    // Grain Methods
    //

    ConstMap<String, Object> extensions();

    NodeGrain with(String key, Object value);

    NodeGrain withAll(Map<? extends String, ?> map);

    NodeGrain without(Object key);

    NodeGrain withoutAll(Collection<?> keys);

    NodeBuilder newBuilder();
}
