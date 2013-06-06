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

package net.nullschool.grains.msgpack;

import net.nullschool.grains.Grain;
import net.nullschool.reflect.TypeTools;
import org.msgpack.template.Template;
import org.msgpack.template.TemplateRegistry;

import java.lang.reflect.Type;


/**
 * 2013-06-06<p/>
 *
 * @author Cameron Beccario
 */
public class VerboseGrainTemplateBuilder extends AbstractTemplateBuilder {

    private final TemplateRegistry registry;

    public VerboseGrainTemplateBuilder(TemplateRegistry registry) {
        this.registry = registry;
    }

    @Override public boolean matchType(Type targetType, boolean hasAnnotation) {
        return Grain.class.isAssignableFrom(TypeTools.erase(targetType));
    }

    @Override public <T> Template<T> buildTemplate(Type targetType) {
        Class<? extends Grain> grainType = TypeTools.erase(targetType).asSubclass(Grain.class);
        @SuppressWarnings("unchecked") Template<T> result =
            (Template<T>)new VerboseGrainTemplate<>(grainType, registry);
        return result;
    }
}
