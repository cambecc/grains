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

/**
 * 2013-02-19<p/>
 *
 * @author Cameron Beccario
 */
final class TemplateHandles {

    private TemplateHandles() {
        throw new AssertionError();
    }

    /**
     * Builds a template handle for FooGrain.java.
     */
    static TemplateHandle newGrainInterfaceTemplate(Configuration config) {
        return new TemplateHandleDecl(config, "grain_interface", "grain_interface.stg");
    }

    /**
     * Builds a template handle for FooBuilder.java.
     */
    static TemplateHandle newBuilderInterfaceTemplate(Configuration config) {
        return new TemplateHandleDecl(config, "builder_interface", "builder_interface.stg");
    }

    /**
     * Builds a template handle for FooFactory.java.
     */
    static TemplateHandle newFactoryEnumTemplate(Configuration config) {
        return new TemplateHandleDecl(
            config,
            "factory_enum",
            "factory_enum.stg",
            "grain_impl.stg",
            "builder_impl.stg",
            "serialization_proxy_impl.stg");
    }

    /**
     * Builds a template handle for generating the imports block at the top of each file.
     */
    static TemplateHandle newImportsBlockTemplate(Configuration config) {
        return new TemplateHandleDecl(config, "imports_block", "imports_block.stg");
    }
}
