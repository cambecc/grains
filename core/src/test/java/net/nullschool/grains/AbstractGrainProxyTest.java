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

import net.nullschool.collect.basic.BasicToolsTest;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;
import static net.nullschool.collect.CollectionTestingTools.*;


/**
 * 2013-05-04<p/>
 *
 * @author Cameron Beccario
 */
public class AbstractGrainProxyTest {

    @Test
    public void test_serialization() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);

        Grain grain = new MockGrain("a", "x");
        grain = grain.with("a", "1").with("x", "8").with("b", "2");

        out.writeObject(grain);
        byte[] data = baos.toByteArray();
        assertEquals(
            "aced05sr0%net.nullschool.grains.MockGrain$Proxy00000001200xr0" +
                "(net.nullschool.grains.AbstractGrainProxy00000001300xpw40003t01at011t01xt018t01bt012x",
            BasicToolsTest.asReadableString(data));

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));

        Grain read = (Grain)in.readObject();
        compare_maps(grain, read);
    }
}
