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

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.CollectionType;
import net.nullschool.collect.ConstSet;

import java.util.List;

import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-06-07<p/>
 *
 * @author Cameron Beccario
 */
final class BasicConstSetDeserializer extends AbstractBasicConstCollectionSerializer<ConstSet> {

    private static final long serialVersionUID = 1;


    BasicConstSetDeserializer(
        CollectionType setType,
        JsonDeserializer<?> elementDeserializer,
        TypeDeserializer elementTypeDeserializer) {

        super(setType, elementDeserializer, elementTypeDeserializer);
    }

    @Override JsonDeserializer<?> withDeserializers(JsonDeserializer<?> ed, TypeDeserializer etd) {
        return ed == elementDeserializer && etd == elementTypeDeserializer ?
            this :
            new BasicConstSetDeserializer(collectionType, ed, etd);
    }

    @Override ConstSet emptyResult() {
        return emptySet();
    }

    @Override ConstSet resultOf(Object element) {
        return setOf(element);
    }

    @Override ConstSet asResult(List<Object> elements) {
        return asSet(elements);
    }
}
