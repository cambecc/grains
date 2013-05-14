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

package net.nullschool.grains.generate;

import net.nullschool.reflect.TypeToken;
import net.nullschool.reflect.TypeTools;
import org.junit.Test;
import net.nullschool.grains.generate.SimpleNameWriter.SimpleNamePrinter;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;


/**
 * 2013-05-10<p/>
 *
 * @author Cameron Beccario
 */
public class SimpleNameWriterTest {

    @Test
    public void test_parameterized_type() {
        Type type = new TypeToken<Set<Map.Entry<Map.Entry, Integer>>>(){}.asType();
        assertEquals("Set<Map.Entry<Map.Entry, Integer>>", TypeTools.toString(type, new SimpleNamePrinter()));
        assertEquals("Set<Entry<Entry, Integer>>", new SimpleNameWriter().apply(type).toString());
    }
}
