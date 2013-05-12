package net.nullschool.grains;

import java.util.Map;


/**
 * 2013-03-20<p/>
 *
 * A factory for constructing grains and grain builders.
 *
 * @author Cameron Beccario
 */
public interface GrainFactory {

    /**
     * Returns the default instance of a grain, defined as the set of basis keys and their default values.
     */
    Grain getDefault();

    /**
     * Constructs and returns a new builder containing the same values as the default grain instance.
     */
    GrainBuilder newBuilder();

    /**
     * Returns a map of basis keys to property descriptors.
     */
    Map<String, GrainProperty> getBasisProperties();
}
