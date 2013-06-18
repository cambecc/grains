package net.nullschool.grains.generate.model;

import java.util.Collection;
import java.util.Map;
import javax.annotation.Generated;
import net.nullschool.collect.ConstMap;
import net.nullschool.grains.Grain;
import net.nullschool.grains.GrainFactoryRef;

/**
 * Composition of Cephalopod and Grain. See {@link CephalopodFactory}.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
@GrainFactoryRef(CephalopodFactory.class)
public interface CephalopodGrain
    extends Animal.Cephalopod, Grain {

    //
    // Animal.Cephalopod Accessors
    //

    String getId();

    CephalopodGrain withId(String id);

    int getLegCount();

    CephalopodGrain withLegCount(int legCount);


    //
    // Grain Methods
    //

    ConstMap<String, Object> extensions();

    CephalopodGrain with(String key, Object value);

    CephalopodGrain withAll(Map<? extends String, ?> map);

    CephalopodGrain without(Object key);

    CephalopodGrain withoutAll(Collection<?> keys);

    CephalopodBuilder newBuilder();
}
