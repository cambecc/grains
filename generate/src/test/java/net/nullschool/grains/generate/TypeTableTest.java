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

import net.nullschool.grains.ConfigurableTypePolicy;
import net.nullschool.grains.GrainSchema;
import org.junit.Ignore;
import org.junit.Test;


/**
 * 2013-05-10<p/>
 *
 * @author Cameron Beccario
 */
public class TypeTableTest {

    @GrainSchema(targetPackage = "com.acme")
    interface Foo {
    }

    @Test @Ignore
    public void test() {
        TypeTable table = new TypeTable(new NamingPolicy(), ConfigurableTypePolicy.STANDARD);
        System.out.println(table.schemaTypes(Foo.class));
    }
}
