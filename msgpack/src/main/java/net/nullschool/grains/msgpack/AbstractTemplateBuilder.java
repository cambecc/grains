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

import org.msgpack.template.FieldList;
import org.msgpack.template.Template;
import org.msgpack.template.builder.TemplateBuildException;
import org.msgpack.template.builder.TemplateBuilder;

import java.lang.reflect.Type;


/**
 * 2013-06-06<p/>
 *
 * A simple TemplateBuilder implementation that does not support writing or loading of templates.
 *
 * @author Cameron Beccario
 */
public abstract class AbstractTemplateBuilder implements TemplateBuilder {

    @Override public abstract boolean matchType(Type targetType, boolean hasAnnotation);

    @Override public abstract <T> Template<T> buildTemplate(Type targetType) throws TemplateBuildException;

    @Override public <T> Template<T> buildTemplate(
        Class<T> targetClass,
        FieldList fieldList) throws TemplateBuildException {

        throw new UnsupportedOperationException();
    }

    @Override public void writeTemplate(Type targetType, String directoryName) {
        throw new UnsupportedOperationException();
    }

    @Override public <T> Template<T> loadTemplate(Type targetType) {
        throw new UnsupportedOperationException();
    }
}
