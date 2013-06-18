package net.nullschool.grains.generate.model;

import java.util.Collection;
import java.util.Map;
import javax.annotation.Generated;
import net.nullschool.collect.ConstMap;
import net.nullschool.grains.Grain;
import net.nullschool.grains.GrainFactoryRef;

/**
 * Composition of Squid and Grain. See {@link SquidFactory}.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
@GrainFactoryRef(SquidFactory.class)
public interface SquidGrain
    extends Animal.Squid, Grain, CephalopodGrain {

    //
    // Animal.Squid Accessors
    //

    String getId();

    SquidGrain withId(String id);

    boolean isGiant();

    SquidGrain withGiant(boolean giant);

    int getLegCount();

    SquidGrain withLegCount(int legCount);


    //
    // Grain Methods
    //

    ConstMap<String, Object> extensions();

    SquidGrain with(String key, Object value);

    SquidGrain withAll(Map<? extends String, ?> map);

    SquidGrain without(Object key);

    SquidGrain withoutAll(Collection<?> keys);

    SquidBuilder newBuilder();
}
