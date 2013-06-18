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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 2013-06-18<p/>
 *
 * @author Cameron Beccario
 */
public class ComposedTest {

    @Test
    public void test_composed() {
        ComposedGrain composed = ComposedFactory.defaultValue();

        SquidGrain squid = composed;
        squid = squid.withGiant(true);

        CephalopodGrain cephalopod = squid;
        cephalopod = cephalopod.withId("abc").withLegCount(5);

        HydraGrain hydra = (HydraGrain)cephalopod;
        hydra = hydra.withAge(200);

        composed = (ComposedGrain)hydra;
        composed = composed.withName("barney");

        assertEquals("{id=abc, age=200, giant=true, legCount=5, name=barney}", composed.toString());
    }
}
