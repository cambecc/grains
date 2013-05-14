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

package net.nullschool.util;

import org.junit.Test;

import static org.junit.Assert.*;
import static net.nullschool.util.StringTools.*;

/**
 * 2013-04-05<p/>
 *
 * @author Cameron Beccario
 */
public class StringToolsTest {

    @Test
    public void test_capitalize() {
        assertEquals("HellO", capitalize("hellO"));
        assertEquals("HellO", capitalize("HellO"));
        assertEquals("A", capitalize("a"));
        assertEquals("", capitalize(""));
        assertNull(capitalize(null));
    }
}
