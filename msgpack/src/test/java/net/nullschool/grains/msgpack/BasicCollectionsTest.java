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

import net.nullschool.collect.*;
import org.junit.Test;
import org.msgpack.MessagePack;

import java.io.IOException;

import static net.nullschool.collect.basic.BasicCollections.*;
import static net.nullschool.collect.basic.BasicCollections.emptySortedMap;
import static net.nullschool.grains.msgpack.MessagePackTools.newGrainsMessagePack;


/**
 * 2013-06-06<p/>
 *
 * @author Cameron Beccario
 */
public class BasicCollectionsTest {

    @Test
    public void test_basicConstList() throws IOException {
        ConstList<Integer> list = emptyList();
        for (int i = 0; i < 10; i++) {
            MessagePack msgpack = newGrainsMessagePack();
            byte[] data = msgpack.write(list);
            ConstList<?> actual = msgpack.read(data, ConstList.class);
            CollectionTestingTools.compare_lists(list, actual);
            list = list.with(i);
        }
    }

    @Test
    public void test_basicConstSet() throws IOException {
        ConstSet<Integer> set = emptySet();
        for (int i = 0; i < 10; i++) {
            MessagePack msgpack = newGrainsMessagePack();
            byte[] data = msgpack.write(set);
            ConstSet<?> actual = msgpack.read(data, ConstSet.class);
            CollectionTestingTools.compare_sets(set, actual);
            set = set.with(i);
        }
    }

    @Test
    public void test_basicConstSortedSet() throws IOException {
        ConstSortedSet<Integer> set = emptySortedSet(null);
        for (int i = 0; i < 10; i++) {
            MessagePack msgpack = newGrainsMessagePack();
            byte[] data = msgpack.write(set);
            ConstSortedSet<?> actual = msgpack.read(data, ConstSortedSet.class);
            CollectionTestingTools.compare_sorted_sets(set, actual);
            set = set.with(i);
        }
    }

    @Test
    public void test_basicConstMap() throws IOException {
        ConstMap<Integer, Integer> map = emptyMap();
        for (int i = 0; i < 10; i++) {
            MessagePack msgpack = newGrainsMessagePack();
            byte[] data = msgpack.write(map);
            ConstMap<?, ?> actual = msgpack.read(data, ConstMap.class);
            CollectionTestingTools.compare_maps(map, actual);
            map = map.with(i, i);
        }
    }

    @Test
    public void test_basicConstSortedMap() throws IOException {
        ConstSortedMap<Integer, Integer> map = emptySortedMap(null);
        for (int i = 0; i < 5; i++) {
            MessagePack msgpack = newGrainsMessagePack();
            byte[] data = msgpack.write(map);
            ConstSortedMap<?, ?> actual = msgpack.read(data, ConstSortedMap.class);
            CollectionTestingTools.compare_sorted_maps(map, actual);
            map = map.with(i, i);
        }
    }
}
