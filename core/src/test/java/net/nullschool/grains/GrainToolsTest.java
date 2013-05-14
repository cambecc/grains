/*
 * Copyright 2013 Cameron Beccario
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        @Override public Grain getDefaultValue() { throw new UnsupportedOperationException(); }
        @Override public GrainBuilder getNewBuilder() { throw new UnsupportedOperationException(); }
    }

    @Test
    public void test_factoryFor() {
        assertSame(MockGrainFactory.INSTANCE, factoryFor(MockGrain.class));
        assertSame(MockGrainFactory.INSTANCE, factoryFor(MockGrainFactory.class));
        assertSame(MyFactory.FACTORY, factoryFor(MyFactory.class));
    }
}
