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

    @Override public Grain getDefaultValue() {
        return new MockGrain("a", "x");
    }

    @Override public GrainBuilder getNewBuilder() {
        return new MockGrainBuilder("a", "x");
    }
}
