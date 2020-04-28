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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.nullschool.collect.*;
import org.junit.Test;

import java.io.IOException;

import static net.nullschool.collect.basic.BasicCollections.*;
import static net.nullschool.grains.jackson.JacksonTools.*;


/**
 * 2013-06-11<p/>
 *
 * @author Cameron Beccario
 */
public class BasicCollectionsTest {

    @Test
    public void test_basicConstList() throws IOException {
        ConstList<Integer> list = emptyList();
        for (int i = 0; i < 10; i++) {
            ObjectMapper mapper = newGrainsObjectMapper();
            byte[] data = mapper.writeValueAsBytes(list);
            ConstList<?> actual = mapper.readValue(data, ConstList.class);
            CollectionTestingTools.compare_lists(list, actual);
            list = list.with(i);
        }
    }

    @Test
    public void test_basicConstSet() throws IOException {
        ConstSet<Integer> set = emptySet();
        for (int i = 0; i < 10; i++) {
            ObjectMapper mapper = newGrainsObjectMapper();
            byte[] data = mapper.writeValueAsBytes(set);
            ConstSet<?> actual = mapper.readValue(data, ConstSet.class);
            CollectionTestingTools.compare_sets(set, actual);
            set = set.with(i);
        }
    }

    @Test
    public void test_basicConstSortedSet() throws IOException {
        ConstSortedSet<Integer> set = emptySortedSet(null);
        for (int i = 0; i < 10; i++) {
            ObjectMapper mapper = newGrainsObjectMapper();
            byte[] data = mapper.writeValueAsBytes(set);
            ConstSortedSet<Integer> actual = mapper.readValue(data, new TypeReference<ConstSortedSet<Integer>>(){});
            CollectionTestingTools.compare_sorted_sets(set, actual);
            set = set.with(i);
        }
    }

    @Test
    public void test_basicConstMap() throws IOException {
        ConstMap<String, Integer> map = emptyMap();
        for (int i = 0; i < 10; i++) {
            ObjectMapper mapper = newGrainsObjectMapper();
            byte[] data = mapper.writeValueAsBytes(map);
            ConstMap<?, ?> actual = mapper.readValue(data, ConstMap.class);
            CollectionTestingTools.compare_maps(map, actual);
            map = map.with(String.valueOf(i), i);
        }
    }

    @Test
    public void test_basicConstSortedMap() throws IOException {
        ConstSortedMap<String, Integer> map = emptySortedMap(null);
        for (int i = 0; i < 5; i++) {
            ObjectMapper mapper = newGrainsObjectMapper();
            byte[] data = mapper.writeValueAsBytes(map);
            ConstSortedMap<String, Integer> actual =
                mapper.readValue(data, new TypeReference<ConstSortedMap<String, Integer>>(){});
            CollectionTestingTools.compare_sorted_maps(map, actual);
            map = map.with(String.valueOf(i), i);
        }
    }
}
