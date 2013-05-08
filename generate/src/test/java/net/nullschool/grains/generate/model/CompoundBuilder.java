package net.nullschool.grains.generate.model;

import javax.annotation.Generated;
import net.nullschool.collect.ConstList;
import net.nullschool.collect.ConstMap;
import net.nullschool.collect.ConstSet;
import net.nullschool.grains.GrainBuilder;
import net.nullschool.grains.GrainFactoryRef;

@Generated("net.nullschool.grains.generate.GrainGenerator")
@GrainFactoryRef(CompoundFactory.class)
public interface CompoundBuilder extends Compound, GrainBuilder {

    PartGrain getFirstPart();

    CompoundBuilder setFirstPart(PartGrain firstPart);

    ConstMap<String, ConstSet<PartGrain>> getPartGroups();

    CompoundBuilder setPartGroups(ConstMap<String, ConstSet<PartGrain>> partGroups);

    ConstList<PartGrain> getRemainingParts();

    CompoundBuilder setRemainingParts(ConstList<PartGrain> remainingParts);

    PartGrain getSecondPart();

    CompoundBuilder setSecondPart(PartGrain secondPart);

    ConstSet<PartGrain> getUniqueParts();

    CompoundBuilder setUniqueParts(ConstSet<PartGrain> uniqueParts);

    ConstList<PartGrain> getUnusedParts();

    CompoundBuilder setUnusedParts(ConstList<PartGrain> unusedParts);

    CompoundGrain build();
}
