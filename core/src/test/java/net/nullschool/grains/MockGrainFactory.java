package net.nullschool.grains;

import java.util.Map;


/**
 * 2013-05-04<p/>
 *
 * @author Cameron Beccario
 */
enum MockGrainFactory implements GrainFactory {
    INSTANCE;

    @Override public Map<String, GrainProperty> getBasisProperties() {
        throw new UnsupportedOperationException("NYI");
    }

    @Override public Grain getDefault() {
        return new MockGrain("a", "x");
    }

    @Override public GrainBuilder getNewBuilder() {
        return new MockGrainBuilder("a", "x");
    }
}
