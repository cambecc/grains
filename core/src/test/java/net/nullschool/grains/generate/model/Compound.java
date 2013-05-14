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
