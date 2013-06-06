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

import net.nullschool.collect.ConstList;
import org.msgpack.packer.Packer;
import org.msgpack.template.Template;
import org.msgpack.unpacker.Unpacker;

import java.io.IOException;
import java.util.Objects;

import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-06-05<p/>
 *
 * @author Cameron Beccario
 */
public class BasicConstListTemplate extends AbstractNullableTemplate<ConstList> {

    private final Template<Object> elementTemplate;

    public BasicConstListTemplate(Template<?> elementTemplate) {
        @SuppressWarnings("unchecked") Template<Object> et = (Template<Object>)elementTemplate;
        this.elementTemplate = Objects.requireNonNull(et);
    }

    @Override protected void writeValue(Packer packer, ConstList list) throws IOException {
        final int size = list.size();
        packer.writeArrayBegin(size);
        for (int i = 0; i < size; i++) {
            elementTemplate.write(packer, list.get(i), false);
        }
        packer.writeArrayEnd();
    }

    @Override protected ConstList readValue(Unpacker unpacker, ConstList to) throws IOException {
        ConstList<?> result;
        final int size = unpacker.readArrayBegin();
        switch (size) {
            case 0:
                result = emptyList();
                break;
            case 1:
                result = listOf(elementTemplate.read(unpacker, null, false));
                break;
            default:
                Object[] elements = new Object[size];
                for (int i = 0; i < size; i++) {
                    elements[i] = elementTemplate.read(unpacker, null, false);
                }
                result = asList(elements);
        }
        unpacker.readArrayEnd();
        return result;
    }

    @Override public String toString() {
        return String.format("%s<%s>", getClass().getSimpleName(), elementTemplate);
    }
}
