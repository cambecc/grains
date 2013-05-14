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

import java.lang.reflect.Type;
import java.util.Set;


/**
 * 2013-05-07<p/>
 *
 * Descriptor for a Grain property.
 *
 * @author Cameron Beccario
 */
public interface GrainProperty {

    public enum Flag {
        /**
         * Signifies the name of this property's get accessor, if one exists, starts with "is" rather than "get".
         */
        IS_PROPERTY
    }

    /**
     * This property's name.
     */
    String getName();

    /**
     * This property's generic type.
     */
    Type getType();

    /**
     * The set of flags that describe this property, or an empty set if no flags are set.
     */
    Set<Flag> getFlags();

    // UNDONE: define equals? hashCode? would doing so require an "owner" property? would equal properties require
    //         consistency when calling setValue with potentially different owner types?
}
