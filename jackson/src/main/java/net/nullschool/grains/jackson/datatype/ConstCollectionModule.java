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

package net.nullschool.grains.jackson.datatype;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.Module;


/**
 * 2013-06-07<p/>
 *
 * @author Cameron Beccario
 */
public class ConstCollectionModule extends Module {

    @Override public String getModuleName() {
        return "ConstCollectionModule";
    }

    @Override public Version version() {
        return PackageVersion.VERSION;
    }

    @Override public void setupModule(SetupContext context) {
        context.addDeserializers(new ConstCollectionDeserializers());
//        context.addSerializers(new ConstCollectionSerializers());
    }
}
