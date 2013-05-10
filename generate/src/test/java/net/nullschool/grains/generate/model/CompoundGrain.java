package net.nullschool.grains.generate.model;

import java.util.Collection;
import java.util.Map;
import javax.annotation.Generated;
import net.nullschool.collect.ConstList;
import net.nullschool.collect.ConstMap;
import net.nullschool.collect.ConstSet;
import net.nullschool.grains.Grain;
import net.nullschool.grains.GrainFactoryRef;

/**
 * Composition of Compound and Grain. See {@link CompoundFactory}.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
@GrainFactoryRef(CompoundFactory.class)
public interface CompoundGrain extends Compound, Grain {

    //
    // Compound Accessors
    //

    PartGrain getFirstPart();

    CompoundGrain withFirstPart(PartGrain firstPart);

    ConstMap<String, ConstSet<PartGrain>> getPartGroups();

    CompoundGrain withPartGroups(ConstMap<String, ConstSet<PartGrain>> partGroups);

    ConstList<PartGrain> getRemainingParts();

    CompoundGrain withRemainingParts(ConstList<PartGrain> remainingParts);

    PartGrain getSecondPart();

    CompoundGrain withSecondPart(PartGrain secondPart);

    ConstSet<PartGrain> getUniqueParts();

    CompoundGrain withUniqueParts(ConstSet<PartGrain> uniqueParts);

    ConstList<PartGrain> getUnusedParts();

    CompoundGrain withUnusedParts(ConstList<PartGrain> unusedParts);


    //
    // Grain Methods
    //

    CompoundGrain with(String key, Object value);

    CompoundGrain withAll(Map<? extends String, ?> map);

    CompoundGrain without(Object key);

    CompoundGrain withoutAll(Collection<?> keys);

    CompoundBuilder builder();

    ConstMap<String, Object> extensions();
}
