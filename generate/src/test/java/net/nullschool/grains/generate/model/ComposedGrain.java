package net.nullschool.grains.generate.model;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Generated;
import net.nullschool.collect.ConstList;
import net.nullschool.collect.ConstMap;
import net.nullschool.grains.Grain;
import net.nullschool.grains.GrainFactoryRef;

/**
 * Composition of Composed and Grain. See {@link ComposedFactory}.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
@GrainFactoryRef(ComposedFactory.class)
public interface ComposedGrain extends Composed, Grain {

    //
    // Composed Accessors
    //

    UUID getId();

    ComposedGrain withId(UUID id);

    String getLeft();

    ComposedGrain withLeft(String left);

    ConstList<UUID> getLeftIds();

    ComposedGrain withLeftIds(ConstList<UUID> leftIds);

    String getName();

    ComposedGrain withName(String name);

    String getRight();

    ComposedGrain withRight(String right);

    ConstList<UUID> getRightIds();

    ComposedGrain withRightIds(ConstList<UUID> rightIds);

    String getTop();

    ComposedGrain withTop(String top);


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
