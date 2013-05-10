package net.nullschool.grains.generate.model;

import java.util.UUID;
import javax.annotation.Generated;
import net.nullschool.collect.ConstList;
import net.nullschool.grains.GrainBuilder;
import net.nullschool.grains.GrainFactoryRef;

/**
 * Composition of Composed and GrainBuilder. See {@link ComposedFactory}.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
@GrainFactoryRef(ComposedFactory.class)
public interface ComposedBuilder extends Composed, GrainBuilder {

    UUID getId();

    ComposedBuilder setId(UUID id);

    String getLeft();

    ComposedBuilder setLeft(String left);

    ConstList<UUID> getLeftIds();

    ComposedBuilder setLeftIds(ConstList<UUID> leftIds);

    String getName();

    ComposedBuilder setName(String name);

    String getRight();

    ComposedBuilder setRight(String right);

    ConstList<UUID> getRightIds();

    ComposedBuilder setRightIds(ConstList<UUID> rightIds);

    String getTop();

    ComposedBuilder setTop(String top);

    ComposedGrain build();
}
