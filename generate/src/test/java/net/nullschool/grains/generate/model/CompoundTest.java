package net.nullschool.grains.generate.model;

import net.nullschool.collect.basic.BasicConstList;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static net.nullschool.collect.CollectionTestingTools.*;


/**
 * 2013-02-26<p/>
 *
 * @author Cameron Beccario
 */
public final class CompoundTest {

    private static PartGrain newPart(int make, int model) {
        return PartFactory.DEFAULT().withMake(make).withModel(model);
    }

    private static LinkedHashMap<String, Object> newBasisAsPlainMap() {
        return new LinkedHashMap<>(CompoundFactory.newBuilder());
    }

    @Test
    public void test_basis() {
        compare_maps(newBasisAsPlainMap(), CompoundFactory.newBuilder());
    }

    @Test
    public void test_nested_parts() {
        CompoundBuilder actual = CompoundFactory.newBuilder();
        actual.setFirstPart(newPart(42, 99));
        actual.setSecondPart(newPart(43, 99));

        Map<String, Object> expected = newBasisAsPlainMap();
        expected.put("firstPart", newPart(42, 99));
        expected.put("secondPart", newPart(43, 99));

        compare_maps(expected, actual);
        compare_maps(expected, actual.build());

        expected.put("firstPart", newMap("make", 42, "model", 99));
        expected.put("secondPart", newMap("make", 43, "model", 99));

        compare_maps(expected, actual);
        compare_maps(expected, actual.build());

        actual.put("firstPart", newPart(10, 99));
        actual.put("secondPart", newPart(11, 99));

        expected.put("firstPart", newPart(10, 99));
        expected.put("secondPart", newPart(11, 99));

        compare_maps(expected, actual);
        compare_maps(expected, actual.build());
    }

    @Test
    public void test_list() {
        CompoundBuilder actual = CompoundFactory.newBuilder();
        actual.setRemainingParts(BasicConstList.listOf(newPart(42, 99), newPart(42, 99), newPart(43, 99)));
        actual.setUnusedParts(BasicConstList.listOf(newPart(1, 99), newPart(2, 99), newPart(3, 99)));

        Map<String, Object> expected = newBasisAsPlainMap();
        expected.put("remainingParts", Arrays.asList(newPart(42, 99), newPart(42, 99), newPart(43, 99)));
        expected.put("unusedParts", Arrays.asList(newPart(1, 99), newPart(2, 99), newPart(3, 99)));

        compare_maps(expected, actual);
        compare_maps(expected, actual.build());

        actual.put("remainingParts", BasicConstList.listOf(newPart(42, 99), newPart(42, 99), newPart(43, 99)));
        actual.put("unusedParts", BasicConstList.listOf(newPart(1, 99), newPart(2, 99), newPart(3, 99)));

        compare_maps(expected, actual);
        compare_maps(expected, actual.build());
    }

}
