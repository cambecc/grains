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
    Grain getDefaultValue();

    /**
     * Constructs and returns a new builder containing the same values as the default grain instance.
     */
    GrainBuilder getNewBuilder();

    /**
     * Returns a map of basis keys to property descriptors.
     */
    Map<String, GrainProperty> getBasisProperties();
}
