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
import net.nullschool.collect.ConstList;

import java.util.*;

import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-06-07<p/>
 *
 * @author Cameron Beccario
 */
final class BasicConstListDeserializer extends AbstractBasicConstCollectionSerializer<ConstList> {

    private static final long serialVersionUID = 1;


    BasicConstListDeserializer(
        CollectionType listType,
        JsonDeserializer<?> elementDeserializer,
        TypeDeserializer elementTypeDeserializer) {

        super(listType, elementDeserializer, elementTypeDeserializer);
    }

    @Override JsonDeserializer<?> withDeserializers(JsonDeserializer<?> ed, TypeDeserializer etd) {
        return ed == elementDeserializer && etd == elementTypeDeserializer ?
            this :
            new BasicConstListDeserializer(collectionType, ed, etd);
    }

    @Override ConstList emptyResult() {
        return emptyList();
    }

    @Override ConstList resultOf(Object element) {
        return listOf(element);
    }

    @Override ConstList asResult(List<Object> elements) {
        return asList(elements);
    }
}
