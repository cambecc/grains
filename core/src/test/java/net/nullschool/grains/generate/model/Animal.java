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


/**
 * 2013-03-05<p/>
 *
 *        Animal
 *       /      \
 *  Cephalopod   \
 *     /          \
 *  Squid        Hydra
 *     \          /
 *      \        /
 *       Composed
 *
 * @author Cameron Beccario
 */
public interface Animal<T> {

    T getId();

    @GrainSchema
    interface Cephalopod extends Animal<String> {

        int getLegCount();
    }

    @GrainSchema
    interface Squid extends Cephalopod {

        boolean isGiant();
    }

    @GrainSchema
    interface Hydra extends Animal<String> {

        int getAge();

        int getLegCount();
    }
}
