package net.nullschool.grains.generate.model;

import net.nullschool.grains.GrainSchema;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 2013-02-22<p/>
 *
 * A Grain schema that uses embedded grains and collection types.
 *
 * @author Cameron Beccario
 */
@GrainSchema
public interface Compound {

    @GrainSchema
    interface Part {

        int getMake();

        int getModel();
    }

    Part getFirstPart();

    Part getSecondPart();

    List<? extends Part> getRemainingParts();

    List<? extends Part> getUnusedParts();

    Set<? extends Part> getUniqueParts();

    Map<String, ? extends Set<? extends Part>> getPartGroups();
}
