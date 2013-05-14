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

import net.nullschool.collect.IterableMap;


/**
 * 2013-03-24<p/>
 *
 *
 *
 * @author Cameron Beccario
 */
public interface GrainBuilder extends IterableMap<String, Object> {

    /**
     * Constructs a Grain instance from this builder's current state.
     *
     * @return the grain instance.
     */
    Grain build();
}
