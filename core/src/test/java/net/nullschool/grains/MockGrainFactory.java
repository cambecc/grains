package net.nullschool.grains;

import net.nullschool.collect.ConstMap;
import net.nullschool.reflect.Property;


/**
 * 2013-05-04<p/>
 *
 * @author Cameron Beccario
 */
enum MockGrainFactory implements GrainFactory {
    INSTANCE;

    @Override public ConstMap<String, Property> getProperties() {
        throw new UnsupportedOperationException("NYI");
    }

    @Override public Grain getBasis() {
        return new MockGrain("a", "x");
    }

    @Override public GrainBuilder newBuilder() {
        return new MockGrainBuilder("a", "x");
    }
}
