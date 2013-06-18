package net.nullschool.grains.generate.model;

import java.util.Collection;
import java.util.Map;
import javax.annotation.Generated;
import net.nullschool.collect.ConstMap;
import net.nullschool.grains.Grain;
import net.nullschool.grains.GrainFactoryRef;

/**
 * Composition of Composed and Grain. See {@link ComposedFactory}.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
@GrainFactoryRef(ComposedFactory.class)
public interface ComposedGrain
    extends Composed, Grain, SquidGrain, HydraGrain {

    //
    // Composed Accessors
    //

    String getId();

    ComposedGrain withId(String id);

    int getAge();

    ComposedGrain withAge(int age);

    boolean isGiant();

    ComposedGrain withGiant(boolean giant);

    int getLegCount();

    ComposedGrain withLegCount(int legCount);

    String getName();

    ComposedGrain withName(String name);


    //
    // Grain Methods
    //

    ConstMap<String, Object> extensions();

    ComposedGrain with(String key, Object value);

    ComposedGrain withAll(Map<? extends String, ?> map);

    ComposedGrain without(Object key);

    ComposedGrain withoutAll(Collection<?> keys);

    ComposedBuilder newBuilder();
}
