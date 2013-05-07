package net.nullschool.grains;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;
import static net.nullschool.grains.GrainTools.*;


/**
 * 2013-05-07<p/>
 *
 * @author Cameron Beccario
 */
public class GrainToolsTest {

    private enum MyFactory implements GrainFactory {
        FACTORY;
        @Override public Map<String, GrainProperty> getBasisProperties() { throw new UnsupportedOperationException(); }
        @Override public Grain getDefault() { throw new UnsupportedOperationException(); }
        @Override public GrainBuilder newBuilder() { throw new UnsupportedOperationException(); }
    }

    @Test
    public void test_factoryFor() {
        assertSame(MockGrainFactory.INSTANCE, factoryFor(MockGrain.class));
        assertSame(MockGrainFactory.INSTANCE, factoryFor(MockGrainFactory.class));
        assertSame(MyFactory.FACTORY, factoryFor(MyFactory.class));
    }
}
