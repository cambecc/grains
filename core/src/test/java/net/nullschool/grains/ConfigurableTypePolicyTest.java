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

package net.nullschool.grains;

import net.nullschool.collect.*;
import net.nullschool.collect.basic.*;
import net.nullschool.reflect.TypeToken;
import net.nullschool.transform.Transform;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static net.nullschool.collect.CollectionTestingTools.*;
import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-06-16<p/>
 *
 * @author Cameron Beccario
 */
public class ConfigurableTypePolicyTest {

    @Test
    public void test_empty() {
        ConfigurableTypePolicy policy = ConfigurableTypePolicy.EMPTY;
        assertTrue(policy.getImmutableTypes().isEmpty());
        assertTrue(policy.getImmutableMappings().isEmpty());
        assertFalse(policy.isImmutableType(int.class));
        assertFalse(policy.isImmutableType(String.class));
        assertTrue(policy.withImmutableTypes(int.class).isImmutableType(int.class));
        assertEquals(
            String.class,
            policy.withImmutableMapping(Object.class, String.class).asImmutableType(Object.class));
    }

    @Test
    public void test_mapping_with_wider_and_narrower_types() {
        ConfigurableTypePolicy policy = ConfigurableTypePolicy.EMPTY
            .withImmutableMapping(Set.class, ConstSet.class);

        assertEquals(ConstSet.class, policy.asImmutableType(Set.class));
        assertEquals(ConstSet.class, policy.asImmutableType(ConstSet.class));
        assertTrue(policy.isImmutableType(ConstSet.class));
        assertNull(policy.asImmutableType(Collection.class));
        assertNull(policy.asImmutableType(SortedSet.class));
    }

    @Test
    public void test_mapping_from_immutable_type() {
        ConfigurableTypePolicy policy = ConfigurableTypePolicy.EMPTY
            .withImmutableTypes(ConstSet.class)
            .withImmutableMapping(Set.class, ConstSet.class)
            .withImmutableMapping(ConstSet.class, BasicConstSet.class);

        assertEquals(ConstSet.class, policy.asImmutableType(Set.class));
        assertEquals(BasicConstSet.class, policy.asImmutableType(ConstSet.class));
        assertEquals(BasicConstSet.class, policy.asImmutableType(BasicConstSet.class));
    }

    @Test
    public void test_re_mapping() {
        ConfigurableTypePolicy policy = ConfigurableTypePolicy.EMPTY
            .withImmutableMapping(Set.class, ConstSet.class)
            .withImmutableMapping(Set.class, ConstSortedSet.class);

        assertEquals(ConstSortedSet.class, policy.asImmutableType(Set.class));
        assertTrue(policy.isImmutableType(ConstSortedSet.class));
        assertTrue(policy.isImmutableType(ConstSet.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_bad_mapping_not_related() {
        ConfigurableTypePolicy.EMPTY.withImmutableMapping(Set.class, ConstMap.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_bad_mapping_not_narrowing() {
        ConfigurableTypePolicy.EMPTY.withImmutableMapping(ConstSet.class, Set.class);
    }

    @Test
    public void test_registration() {
        ConfigurableTypePolicy policy = ConfigurableTypePolicy.EMPTY
            .withImmutableTypes(ConstSet.class, ConstSet.class, ConstSet.class)
            .withImmutableMapping(Set.class, ConstSet.class)
            .withImmutableMapping(Collection.class, ConstSet.class);

        compare_sets(setOf(ConstSet.class), policy.getImmutableTypes());
        compare_maps(
            mapOf(Set.class, ConstSet.class, Collection.class, ConstSet.class),
            policy.getImmutableMappings());
    }

    @Test
    public void test_subtypes_are_immutable() {
        ConfigurableTypePolicy policy = ConfigurableTypePolicy.EMPTY
            .withImmutableTypes(ConstCollection.class);

        assertTrue(policy.isImmutableType(ConstSet.class));
        assertTrue(policy.isImmutableType(ConstSortedSet.class));
        assertTrue(policy.isImmutableType(BasicConstSet.class));
        assertFalse(policy.isImmutableType(Collection.class));
    }

    @Test
    public void test_class_transform() {
        ConfigurableTypePolicy policy = ConfigurableTypePolicy.EMPTY;
        Transform<Long> transform = policy.newTransform(new TypeToken<Long>(){});

        Object o = transform.apply(1L);
        assertEquals(1L, o);
        try { transform.apply(1); fail(); } catch (ClassCastException expected) {}
    }

    @Test
    public void test_list_transform() {
        ConfigurableTypePolicy policy = ConfigurableTypePolicy.EMPTY;
        Transform<List<String>> transform = policy.newTransform(new TypeToken<List<String>>(){});

        Object o = transform.apply(listOf("a"));
        assertEquals(listOf("a"), o);

        try { transform.apply(setOf("a")); fail(); } catch (ClassCastException expected) {}

        List<String> list = transform.apply(listOf(1));  // We really want to get an exception HERE.
        try { System.out.println(list.get(0)); fail(); } catch (ClassCastException darn) {}  // heap pollution
    }
}
