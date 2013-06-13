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
import com.fasterxml.jackson.databind.type.MapType;
import net.nullschool.collect.ConstMap;

import java.util.*;

import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-06-07<p/>
 *
 * @author Cameron Beccario
 */
final class BasicConstMapDeserializer extends AbstractBasicConstMapDeserializer<ConstMap> {

    private static final long serialVersionUID = 1;


    BasicConstMapDeserializer(
        MapType mapType,
        KeyDeserializer keyDeserializer,
        JsonDeserializer<?> valueDeserializer,
        TypeDeserializer valueTypeDeserializer) {

        super(mapType, keyDeserializer, valueDeserializer, valueTypeDeserializer);
    }

    @Override JsonDeserializer<?> withDeserializers(KeyDeserializer kd, JsonDeserializer<?> vd, TypeDeserializer vtd) {
        return kd == keyDeserializer && vd == valueDeserializer && vtd == valueTypeDeserializer ?
            this :
            new BasicConstMapDeserializer(mapType, kd, vd, vtd);
    }

    @Override ConstMap emptyResult() {
        return emptyMap();
    }

    @Override ConstMap resultOf(Object key, Object value) {
        return mapOf(key, value);
    }

    @Override ConstMap asResult(List<Object> keys, List<Object> values) {
        return asMap(keys.toArray(), values.toArray());
    }
}
