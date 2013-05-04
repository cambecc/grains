package net.nullschool.collect.basic;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static net.nullschool.collect.basic.BasicTools.*;
import static net.nullschool.util.ArrayTools.*;
import static net.nullschool.collect.CollectionTestingTools.*;

/**
 * 2013-03-15<p/>
 *
 * @author Cameron Beccario
 */
public class BasicToolsTest {

    public static String asReadableString(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length);
        for (byte b : data) {
            if (b < 32) {
                sb/*.append('»')*/.append(String.format("%x", b & 0xff));
            }
            else {
                sb.append((char)b);
            }
        }
        return sb.toString();
    }

    @Test
    public void test_copy_array() {
        Object[] src = new Object[] {1, 2, 3};
        assertArrayEquals(src, copy(src));
        assertNotSame(src, copy(src));
        assertArrayEquals(EMPTY_OBJECT_ARRAY, copy(EMPTY_OBJECT_ARRAY));
        assertArrayEquals(src, copy(new Integer[] {1, 2, 3}));
        assertEquals(Object[].class, copy(new Integer[] {1, 2, 3}).getClass());
    }

    @Test(expected = NullPointerException.class)
    public void test_copy_array_null() {
        copy((Object[])null);
    }

    @Test
    public void test_copy_array_length() {
        Object[] src = new Object[] {1, 2, 3};
        assertArrayEquals(src, copy(src, 3));
        assertNotSame(src, copy(src, 3));
        assertArrayEquals(src, copy(new Integer[] {1, 2, 3}, 3));
        assertEquals(Object[].class, copy(new Integer[] {1, 2, 3}, 3).getClass());
        assertArrayEquals(new Object[] {}, copy(src, 0));
        assertArrayEquals(new Object[] {1, 2}, copy(src, 2));
        assertArrayEquals(new Object[] {1, 2, 3, null}, copy(src, 4));
        assertArrayEquals(new Object[] {null, null, null}, copy(EMPTY_OBJECT_ARRAY, 3));
    }

    @Test(expected = NullPointerException.class)
    public void test_copy_array_length_null() {
        copy(null, 0);
    }

    @Test(expected = NegativeArraySizeException.class)
    public void test_copy_array_length_negative_size() {
        copy(EMPTY_OBJECT_ARRAY, -1);
    }

    @Test
    public void test_copy_collection() {
        List<Integer> src = Arrays.asList(1, 2, 3);
        assertArrayEquals(new Object[] {1, 2, 3}, copy(src));
        assertEquals(Object[].class, copy(src).getClass());
    }

    @Test(expected = NullPointerException.class)
    public void test_copy_collection_null() {
        copy((Collection<?>)null);
    }

    @Test
    public void test_copy_iterator() {
        final int[] items = new int[] {1, 2, 3};
        Iterator<Integer> iter = new Iterator<Integer>() {
            int i = 0;
            @Override public boolean hasNext() { return i < items.length; }
            @Override public Integer next() { return items[i++]; }
            @Override public void remove() { throw new UnsupportedOperationException(); }
        };
        Object[] result = copy(iter);
        assertArrayEquals(new Object[] {1, 2, 3}, result);
        assertEquals(Object[].class, result.getClass());
    }

    @Test(expected = NullPointerException.class)
    public void test_copy_iterator_null() {
        copy((Iterator<?>)null);
    }

    @Test
    public void test_copy_map() {
        Map<?, ?> map = newMap("a", 1, "b", 2, "c", 3);
        MapColumns mc;

        mc = copy(map);
        assertArrayEquals(new Object[] {"a", "b", "c"}, mc.keys);
        assertArrayEquals(new Object[] {1,   2,   3}, mc.values);
        assertEquals(Object[].class, mc.keys.getClass());
        assertEquals(Object[].class, mc.values.getClass());

        mc = copy(Collections.emptyMap());
        assertArrayEquals(EMPTY_OBJECT_ARRAY, mc.keys);
        assertArrayEquals(EMPTY_OBJECT_ARRAY, mc.values);
    }

    private class LyingMap<K, V> extends TreeMap<K, V> {
        @Override public int size() { return 2; }  // return a bogus size
        private static final long serialVersionUID = 1;
    }

    @Test
    public void test_copy_map_lying_about_its_size() {
        Map<String, Integer> map = new LyingMap<>();
        MapColumns mc;

        // Map is smaller than it says.
        map.put("a", 1);
        assertEquals(2, map.size());
        assertEquals("{a=1}", map.toString());

        mc = copy(map);
        assertArrayEquals(new Object[] {"a"}, mc.keys);
        assertArrayEquals(new Object[] {1}, mc.values);
        assertEquals(Object[].class, mc.keys.getClass());
        assertEquals(Object[].class, mc.values.getClass());

        // Map is larger than it says.
        map.put("b", 2);
        map.put("c", 3);
        assertEquals(2, map.size());
        assertEquals("{a=1, b=2, c=3}", map.toString());

        mc = copy(map);
        assertArrayEquals(new Object[] {"a", "b", "c"}, mc.keys);
        assertArrayEquals(new Object[] {1, 2, 3}, mc.values);
        assertEquals(Object[].class, mc.keys.getClass());
        assertEquals(Object[].class, mc.values.getClass());
    }

    @Test(expected = NullPointerException.class)
    public void test_copy_map_null() {
        copy((Map)null);
    }

    @Test
    public void test_insert() {
        Object[] src = new Object[] {1, 2, 3};
        assertArrayEquals(new Object[] {4, 1, 2, 3}, insert(src, 0, 4));
        assertArrayEquals(new Object[] {1, 4, 2, 3}, insert(src, 1, 4));
        assertArrayEquals(new Object[] {1, 2, 4, 3}, insert(src, 2, 4));
        assertArrayEquals(new Object[] {1, 2, 3, 4}, insert(src, 3, 4));

        assertArrayEquals(new Object[] {"a"}, insert(EMPTY_OBJECT_ARRAY, 0, "a"));
        assertArrayEquals(new Object[] {null}, insert(EMPTY_OBJECT_ARRAY, 0, null));
    }

    @Test
    public void test_insert_result_type_is_object_array() {
        assertSame(Object[].class, insert(new Integer[] {1, 2, 3}, 3, 4).getClass());
    }

    @Test(expected = NullPointerException.class)
    public void test_insert_null() {
        insert(null, 0, null);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void test_insert_out_of_bounds_low() {
        insert(EMPTY_OBJECT_ARRAY, -1, "a");
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void test_insert_out_of_bounds_high() {
        insert(EMPTY_OBJECT_ARRAY, 1, "a");
    }

    @Test
    public void test_insertAll() {
        Object[] src = new Object[] {1, 2, 3};
        List<Integer> extra = Arrays.asList(8, 9);

        assertArrayEquals(new Object[] {8, 9, 1, 2, 3}, insertAll(src, 0, extra));
        assertArrayEquals(new Object[] {1, 8, 9, 2, 3}, insertAll(src, 1, extra));
        assertArrayEquals(new Object[] {1, 2, 8, 9, 3}, insertAll(src, 2, extra));
        assertArrayEquals(new Object[] {1, 2, 3, 8, 9}, insertAll(src, 3, extra));

        assertArrayEquals(new Object[] {1, 2, 3}, insertAll(src, 0, Collections.emptyList()));
        assertArrayEquals(new Object[] {1, 2, 3}, insertAll(src, 1, Collections.emptyList()));
        assertArrayEquals(new Object[] {1, 2, 3}, insertAll(src, 2, Collections.emptyList()));
        assertArrayEquals(new Object[] {1, 2, 3}, insertAll(src, 3, Collections.emptyList()));

        assertArrayEquals(new Object[] {"a", "b"}, insertAll(EMPTY_OBJECT_ARRAY, 0, Arrays.asList("a", "b")));
        assertArrayEquals(new Object[] {null, null}, insertAll(EMPTY_OBJECT_ARRAY, 0, Arrays.asList(null, null)));
    }

    @Test
    public void test_insertAll_result_type_is_object_array() {
        assertSame(Object[].class, insertAll(new Integer[] {1, 2, 3}, 3, Arrays.asList(4, 5)).getClass());
    }

    @Test
    public void test_insertAll_allocates_new() {
        Object[] src = new Object[] {1, 2, 3};
        assertNotSame(src, insertAll(src, 0, Collections.emptyList()));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_insertAll_out_of_bounds_low() {
        insertAll(EMPTY_OBJECT_ARRAY, -1, Arrays.asList("a"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_insertAll_out_of_bounds_high() {
        insertAll(EMPTY_OBJECT_ARRAY, 1, Arrays.asList("a"));
    }

    @Test(expected = NullPointerException.class)
    public void test_insertAll_null() {
        insertAll(null, 0, Collections.emptyList());
    }

    @Test(expected = NullPointerException.class)
    public void test_insertAll_null_collection() {
        insertAll(EMPTY_OBJECT_ARRAY, 0, null);
    }

    @Test
    public void test_replace() {
        Integer[] src = new Integer[] {1, 2, 3};
        assertArrayEquals(new Object[] {9, 2, 3}, replace(src, 0, 9));
        assertArrayEquals(new Object[] {1, 9, 3}, replace(src, 1, 9));
        assertArrayEquals(new Object[] {1, 2, 9}, replace(src, 2, 9));
        assertEquals(Object[].class, replace(src, 0, 1).getClass());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_replace_out_of_bounds_low() {
        replace(EMPTY_OBJECT_ARRAY, -1, null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_replace_out_of_bounds_high() {
        replace(EMPTY_OBJECT_ARRAY, 0, null);
    }

    @Test(expected = NullPointerException.class)
    public void test_replace_null() {
        replace(null, 0, null);
    }

    @Test
    public void test_delete() {
        Integer[] src = new Integer[] {1, 2, 3, 4};
        assertArrayEquals(new Object[] {2, 3, 4}, delete(src, 0));
        assertArrayEquals(new Object[] {1, 3, 4}, delete(src, 1));
        assertArrayEquals(new Object[] {1, 2, 4}, delete(src, 2));
        assertArrayEquals(new Object[] {1, 2, 3}, delete(src, 3));

        assertArrayEquals(EMPTY_OBJECT_ARRAY, delete(new Object[] {"a"}, 0));
        assertArrayEquals(EMPTY_OBJECT_ARRAY, delete(new Object[] {null}, 0));
    }

    @Test
    public void test_delete_result_type_is_object_array() {
        assertSame(Object[].class, delete(new Integer[] {1, 2, 3}, 1).getClass());
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void test_delete_out_of_bounds_low() {
        delete(new Object[] {"a"}, -1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void test_delete_out_of_bounds_high() {
        delete(new Object[] {"a"}, 1);
    }

    @Test(expected = NegativeArraySizeException.class)
    public void test_delete_out_of_bounds() {
        delete(EMPTY_OBJECT_ARRAY, 0);
    }

    @Test(expected = NullPointerException.class)
    public void test_delete_null() {
        delete(null, 0);
    }

    @Test
    public void test_deleteAll() {
        String[] src = new String[] {"a", "b", "c"};
        assertArrayEquals(new Object[] {"b", "c"}, deleteAll(src, Arrays.asList("a")));
        assertArrayEquals(new Object[] {"a", "c"}, deleteAll(src, Arrays.asList("b")));
        assertArrayEquals(new Object[] {"a", "b"}, deleteAll(src, Arrays.asList("c")));
        assertArrayEquals(new Object[] {"a", "b", "c"}, deleteAll(src, Arrays.asList("d")));
        assertArrayEquals(new Object[] {"a", "b", "c"}, deleteAll(src, Collections.emptyList()));

        TreeSet<String> filter = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        filter.add("A");
        filter.add("B");
        assertArrayEquals(new Object[] {"c"}, deleteAll(src, filter));
        filter.add("C");
        filter.add("D");
        assertArrayEquals(new Object[] {}, deleteAll(src, filter));
    }

    @Test
    public void test_deleteAll_result_type_is_object_array() {
        assertSame(Object[].class, deleteAll(new Integer[] {1, 2, 3}, Collections.emptySet()).getClass());
    }

    @Test(expected = NullPointerException.class)
    public void test_deleteAll_null_array() {
        deleteAll(null, Collections.emptySet());
    }

    @Test(expected = NullPointerException.class)
    public void test_deleteAll_null_collection() {
        deleteAll(EMPTY_OBJECT_ARRAY, null);
    }

    @Test
    public void test_deleteAll_columns() {
        String[] keys = new String[] {"a", "b", "c"};
        Integer[] values = new Integer[] {1, 2, 3};
        MapColumns mc;

        mc = deleteAll(keys, values, Arrays.asList("a"));
        assertArrayEquals(new Object[] {"b", "c"}, mc.keys);
        assertArrayEquals(new Object[] {2,   3}, mc.values);

        mc = deleteAll(keys, values, Arrays.asList("b"));
        assertArrayEquals(new Object[] {"a", "c"}, mc.keys);
        assertArrayEquals(new Object[] {1,   3}, mc.values);

        mc = deleteAll(keys, values, Arrays.asList("c"));
        assertArrayEquals(new Object[] {"a", "b"}, mc.keys);
        assertArrayEquals(new Object[] {1,   2}, mc.values);

        mc = deleteAll(keys, values, Arrays.asList("d"));
        assertArrayEquals(new Object[] {"a", "b", "c"}, mc.keys);
        assertArrayEquals(new Object[] {1,   2,   3}, mc.values);

        mc = deleteAll(keys, values, Collections.emptyList());
        assertArrayEquals(new Object[] {"a", "b", "c"}, mc.keys);
        assertArrayEquals(new Object[] {1,   2,   3}, mc.values);

        TreeSet<String> filter = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        filter.add("A");
        filter.add("B");

        mc = deleteAll(keys, values, filter);
        assertArrayEquals(new Object[] {"c"}, mc.keys);
        assertArrayEquals(new Object[] {3}, mc.values);

        filter.add("C");
        filter.add("D");

        mc = deleteAll(keys, values, filter);
        assertArrayEquals(new Object[] {}, mc.keys);
        assertArrayEquals(new Object[] {}, mc.values);
    }

    @Test
    public void test_deleteAll_columns_result_type_is_object_array() {
        String[] keys = new String[] {"a", "b", "c"};
        Integer[] values = new Integer[] {1, 2, 3};
        MapColumns mc;

        mc = deleteAll(keys, values, Collections.emptyList());
        assertSame(Object[].class, mc.keys.getClass());
        assertSame(Object[].class, mc.values.getClass());
    }

    @Test(expected = NullPointerException.class)
    public void test_deleteAll_columns_null_array() {
        deleteAll(null, EMPTY_OBJECT_ARRAY, Collections.emptySet());
    }

    @Test(expected = NullPointerException.class)
    public void test_deleteAll_columns_null_value_array() {
        deleteAll(EMPTY_OBJECT_ARRAY, null, Collections.emptySet());
    }

    @Test(expected = NullPointerException.class)
    public void test_deleteAll_columns_null_collection() {
        deleteAll(EMPTY_OBJECT_ARRAY, EMPTY_OBJECT_ARRAY, null);
    }

    @Test
    public void test_union_into() {
        assertArrayEquals(new Object[] {1, 2, 3, 0}, unionInto(new Object[] {1, 2, 3}, new Object[] {0}));
        assertArrayEquals(new Object[] {1, 3, 2, 0}, unionInto(new Object[] {1, 3, 2}, new Object[] {0, 1}));
        assertArrayEquals(new Object[] {1, 2, 3, null}, unionInto(new Object[] {1, 2, 3}, new Object[] {null}));
        assertArrayEquals(new Object[] {1, 2, 3, "a"}, unionInto(new Object[] {1, 2, 3}, new Object[] {"a"}));
        assertArrayEquals(new Object[] {1, 1, 0}, unionInto(new Object[] {1, 1}, new Object[] {1, 0, 0}));
        assertArrayEquals(new Object[] {1, 0, 2}, unionInto(new Object[] {}, new Object[] {1, 0, 1, 2, 0, 2}));
        assertArrayEquals(new Object[] {1, 1}, unionInto(new Object[] {1, 1}, new Object[] {}));

        assertEquals(Object[].class, unionInto(new Integer[] {1}, new Integer[] {2}).getClass());
    }

    @Test
    public void test_union_into_null_arrays() {
        try { unionInto(null, new Object[0]); fail(); } catch (NullPointerException ignored) {}
        try { unionInto(new Object[0], null); fail(); } catch (NullPointerException ignored) {}
    }

    @Test
    public void test_union_into_comparator() {
        // natural ordering
        assertArrayEquals(new Object[] {0, 1, 2, 3}, unionInto(new Object[] {1, 2, 3}, new Object[] {0}, null));
        assertArrayEquals(new Object[] {0, 1, 2, 3}, unionInto(new Object[] {1, 2, 3}, new Object[] {0, 1}, null));
        assertArrayEquals(new Object[] {0, 1, 1}, unionInto(new Object[] {1, 1}, new Object[] {1, 0, 0}, null));
        assertArrayEquals(new Object[] {0, 1, 2}, unionInto(new Object[] {}, new Object[] {1, 0, 1, 2, 0, 2}, null));
        assertArrayEquals(new Object[] {1, 1}, unionInto(new Object[] {1, 1}, new Object[] {}, null));
        assertEquals(Object[].class, unionInto(new Integer[] {1}, new Integer[] {2}, null).getClass());

        // reverse ordering
        Comparator<Object> c = Collections.reverseOrder();
        assertArrayEquals(new Object[] {3, 2, 1, 0}, unionInto(new Object[] {3, 2, 1}, new Object[] {0}, c));
        assertArrayEquals(new Object[] {3, 2, 1, 0}, unionInto(new Object[] {3, 2, 1}, new Object[] {0, 1}, c));
        assertArrayEquals(new Object[] {1, 1, 0}, unionInto(new Object[] {1, 1}, new Object[] {1, 0, 0}, c));
        assertArrayEquals(new Object[] {2, 1, 0}, unionInto(new Object[] {}, new Object[] {1, 0, 1, 2, 0, 2}, c));
        assertArrayEquals(new Object[] {1, 1}, unionInto(new Object[] {1, 1}, new Object[] {}, c));
        assertEquals(Object[].class, unionInto(new Integer[] {1}, new Integer[] {2}, c).getClass());
    }

    @Test
    public void test_union_into_comparator_bad_types() {
        try { unionInto(new Object[] {1}, new Object[] {"a"}, null); } catch (ClassCastException ignored) {}
        try { unionInto(new Object[] {1}, new Object[] {null}, null); } catch (NullPointerException ignored) {}

        Comparator<Object> c = Collections.reverseOrder();
        try { unionInto(new Object[] {1}, new Object[] {"a"}, c); } catch (ClassCastException ignored) {}
        try { unionInto(new Object[] {1}, new Object[] {null}, c); } catch (NullPointerException ignored) {}
    }

    @Test
    public void test_union_into_comparator_null_arrays() {
        try { unionInto(null, new Object[0], null); fail(); } catch (NullPointerException ignored) {}
        try { unionInto(new Object[0], null, null); fail(); } catch (NullPointerException ignored) {}

        Comparator<Object> c = Collections.reverseOrder();
        try { unionInto(null, new Object[0], c); fail(); } catch (NullPointerException ignored) {}
        try { unionInto(new Object[0], null, c); fail(); } catch (NullPointerException ignored) {}
    }

    @Test
    public void test_union_into_columns() {
        String[] keys = new String[] {"a", "b", "a"};
        Integer[] values = new Integer[] {1, 2, 3};
        MapColumns mc;

        mc = unionInto(keys, values, new String[] {"+"}, new Integer[] {1});
        assertArrayEquals(new Object[] {"a", "b", "a", "+"}, mc.keys);
        assertArrayEquals(new Object[] {1,   2,   3,   1  }, mc.values);

        mc = unionInto(keys, values, new String[] {"+", "a"}, new Integer[] {1, 2});
        assertArrayEquals(new Object[] {"a", "b", "a", "+"}, mc.keys);
        assertArrayEquals(new Object[] {2,   2,   3,   1  }, mc.values);

        mc = unionInto(keys, values, new Object[] {"+", "a", "+", "a", 4}, new Object[] {1, 2, 0, 4, "d"});
        assertArrayEquals(new Object[] {"a", "b", "a", "+", 4}, mc.keys);
        assertArrayEquals(new Object[] {4,   2,   3,   0  , "d"}, mc.values);

        assertEquals(Object[].class, mc.keys.getClass());
        assertEquals(Object[].class, mc.values.getClass());
    }

    @Test
    public void test_union_into_columns_null_arrays() {
        try { unionInto(new Object[0], null, null, null); fail(); } catch (NullPointerException ignored) {}
        try { unionInto(null, new Object[0], null, null); fail(); } catch (NullPointerException ignored) {}
        try { unionInto(null, null, new Object[0], null); fail(); } catch (NullPointerException ignored) {}
        try { unionInto(null, null, null, new Object[0]); fail(); } catch (NullPointerException ignored) {}
    }

    @Test
    public void test_union_into_columns_comparator_natural_ordering() {
        String[] keys = new String[] {"a", "a", "d"};
        Integer[] values = new Integer[] {1, 1, 4};
        MapColumns mc;

        mc = unionInto(keys, values, new String[] {"+"}, new Integer[] {1}, null);
        assertArrayEquals(new Object[] {"+", "a", "a", "d"}, mc.keys);
        assertArrayEquals(new Object[] {1,   1,   1,   4}, mc.values);

        mc = unionInto(keys, values, new String[] {"a", "+", "e", "c"}, new Integer[] {1, 0, 5, 3}, null);
        assertArrayEquals(new Object[] {"+", "a", "a", "c", "d", "e"}, mc.keys);
        assertArrayEquals(new Object[] {0,   1,   1,   3,   4,   5}, mc.values);

        assertEquals(Object[].class, mc.keys.getClass());
        assertEquals(Object[].class, mc.values.getClass());
    }

    @Test
    public void test_union_into_columns_comparator_reverse_ordering() {
        String[] keys = new String[] {"d", "a", "a"};
        Integer[] values = new Integer[] {4, 1, 1};
        Comparator<Object> c = Collections.reverseOrder();
        MapColumns mc;

        mc = unionInto(keys, values, new String[] {"+"}, new Integer[] {1}, c);
        assertArrayEquals(new Object[] {"d", "a", "a", "+"}, mc.keys);
        assertArrayEquals(new Object[] {4,   1,   1,   1}, mc.values);

        mc = unionInto(keys, values, new String[] {"a", "+", "e", "c"}, new Integer[] {1, 0, 5, 3}, c);
        assertArrayEquals(new Object[] {"e", "d", "c", "a", "a", "+"}, mc.keys);
        assertArrayEquals(new Object[] {5,   4,   3,   1,   1,   0}, mc.values);

        assertEquals(Object[].class, mc.keys.getClass());
        assertEquals(Object[].class, mc.values.getClass());
    }

    @Test
    public void test_union_into_columns_comparator_null_arrays() {
        try { unionInto(new Object[0], null, null, null, null); fail(); } catch (NullPointerException ignored) {}
        try { unionInto(null, new Object[0], null, null, null); fail(); } catch (NullPointerException ignored) {}
        try { unionInto(null, null, new Object[0], null, null); fail(); } catch (NullPointerException ignored) {}
        try { unionInto(null, null, null, new Object[0], null); fail(); } catch (NullPointerException ignored) {}

        Comparator<Object> c = Collections.reverseOrder();
        try { unionInto(new Object[0], null, null, null, c); fail(); } catch (NullPointerException ignored) {}
        try { unionInto(null, new Object[0], null, null, c); fail(); } catch (NullPointerException ignored) {}
        try { unionInto(null, null, new Object[0], null, c); fail(); } catch (NullPointerException ignored) {}
        try { unionInto(null, null, null, new Object[0], c); fail(); } catch (NullPointerException ignored) {}
    }

    @Test
    public void test_unionInto_bad_types() {
        final Object[] EMPTY = new Object[0];
        final Object[] items = new Object[] {"a"};
        final Object[] oneObj = new Object[] {new Object()};
        final Object[] oneNull = new Object[] {null};
        @SuppressWarnings("unchecked") Comparator<Object> c = (Comparator)String.CASE_INSENSITIVE_ORDER;

        try { unionInto(EMPTY, oneObj,  null); fail(); } catch (ClassCastException ignored) {}
        try { unionInto(EMPTY, oneObj,  c);    fail(); } catch (ClassCastException ignored) {}
        try { unionInto(EMPTY, oneNull, null); fail(); } catch (NullPointerException ignored) {}
        try { unionInto(EMPTY, oneNull, c);    fail(); } catch (NullPointerException ignored) {}

        try { unionInto(items, oneObj,  null); fail(); } catch (ClassCastException ignored) {}
        try { unionInto(items, oneObj,  c);    fail(); } catch (ClassCastException ignored) {}
        try { unionInto(items, oneNull, null); fail(); } catch (NullPointerException ignored) {}
        try { unionInto(items, oneNull, c);    fail(); } catch (NullPointerException ignored) {}
    }

    @Test
    public void test_unionInto_columns_bad_types() {
        final Object[] EMPTY = new Object[0];
        final Object[] items = new Object[] {"a"};
        final Object[] oneObj = new Object[] {new Object()};
        final Object[] oneNull = new Object[] {null};
        @SuppressWarnings("unchecked") Comparator<Object> c = (Comparator)String.CASE_INSENSITIVE_ORDER;

        try { unionInto(EMPTY, EMPTY, oneObj,  oneNull, null); fail(); } catch (ClassCastException ignored) {}
        try { unionInto(EMPTY, EMPTY, oneObj,  oneNull, c);    fail(); } catch (ClassCastException ignored) {}
        try { unionInto(EMPTY, EMPTY, oneNull, oneNull, null); fail(); } catch (NullPointerException ignored) {}
        try { unionInto(EMPTY, EMPTY, oneNull, oneNull, c);    fail(); } catch (NullPointerException ignored) {}

        try { unionInto(items, EMPTY, oneObj,  oneNull, null); fail(); } catch (ClassCastException ignored) {}
        try { unionInto(items, EMPTY, oneObj,  oneNull, c);    fail(); } catch (ClassCastException ignored) {}
        try { unionInto(items, EMPTY, oneNull, oneNull, null); fail(); } catch (NullPointerException ignored) {}
        try { unionInto(items, EMPTY, oneNull, oneNull, c);    fail(); } catch (NullPointerException ignored) {}
    }
}
