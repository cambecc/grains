package net.nullschool.grains.kryo;

import com.esotericsoftware.kryo.Kryo;
import net.nullschool.collect.*;
import net.nullschool.collect.basic.*;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Comparator;

import static net.nullschool.grains.kryo.KryoTestTools.*;
import static org.junit.Assert.*;


/**
 * 2013-06-04<p/>
 *
 * @author Cameron Beccario
 */
public class BasicCollectionsTest {

    @Test
    public void test_basicConstList() {
        ConstList<Integer> list = BasicConstList.emptyList();
        for (int i = 0; i < 10; i++) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Object obj = roundTrip(list, baos);
            assertTrue(obj instanceof ConstList);
            CollectionTestingTools.compare_lists(list, (ConstList<?>)obj);
            list = list.with(i);
        }
    }

    @Test
    public void test_basicConstSet() {
        ConstSet<Integer> set = BasicConstSet.emptySet();
        for (int i = 0; i < 10; i++) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Object obj = roundTrip(set, baos);
            assertTrue(obj instanceof ConstSet);
            CollectionTestingTools.compare_sets(set, (ConstSet<?>)obj);
            set = set.with(i);
        }
    }

    @Test
    public void test_basicConstSortedSet() {
        ConstSortedSet<Integer> set = BasicConstSortedSet.emptySortedSet(Collections.reverseOrder());
        for (int i = 0; i < 10; i++) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Kryo kryo = newTestKryo();
            kryo.addDefaultSerializer(Comparator.class, new ComparatorSerializer());
            Object obj = roundTrip(set, baos, kryo, kryo);
            assertTrue(obj instanceof ConstSortedSet);
            CollectionTestingTools.compare_sorted_sets(set, (ConstSortedSet<?>)obj);
            set = set.with(i);
        }

        set = BasicConstSortedSet.emptySortedSet(null);
        for (int i = 0; i < 10; i++) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Kryo kryo = newTestKryo();
            kryo.addDefaultSerializer(Comparator.class, new ComparatorSerializer());
            Object obj = roundTrip(set, baos, kryo, kryo);
            assertTrue(obj instanceof ConstSortedSet);
            CollectionTestingTools.compare_sorted_sets(set, (ConstSortedSet<?>)obj);
            set = set.with(i);
        }
    }

    @Test
    public void test_basicConstMap() {
        ConstMap<Integer, Integer> map = BasicConstMap.emptyMap();
        for (int i = 0; i < 10; i++) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Object obj = roundTrip(map, baos);
            assertTrue(obj instanceof ConstMap);
            CollectionTestingTools.compare_maps(map, (ConstMap<?, ?>)obj);
            map = map.with(i, i);
        }
    }

    @Test
    public void test_basicConstSortedMap() {
        ConstSortedMap<Integer, Integer> map = BasicConstSortedMap.emptySortedMap(Collections.reverseOrder());
        for (int i = 0; i < 5; i++) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Kryo kryo = newTestKryo();
            kryo.addDefaultSerializer(Comparator.class, new ComparatorSerializer());
            Object obj = roundTrip(map, baos, kryo, kryo);
            assertTrue(obj instanceof ConstSortedMap);
            CollectionTestingTools.compare_sorted_maps(map, (ConstSortedMap<?, ?>)obj);
            map = map.with(i, i);
        }

        map = BasicConstSortedMap.emptySortedMap(null);
        for (int i = 0; i < 5; i++) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Kryo kryo = newTestKryo();
            kryo.addDefaultSerializer(Comparator.class, new ComparatorSerializer());
            Object obj = roundTrip(map, baos, kryo, kryo);
            assertTrue(obj instanceof ConstSortedMap);
            CollectionTestingTools.compare_sorted_maps(map, (ConstSortedMap<?, ?>)obj);
            map = map.with(i, i);
        }
    }
}
