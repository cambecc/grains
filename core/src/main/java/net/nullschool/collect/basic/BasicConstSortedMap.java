package net.nullschool.collect.basic;

import net.nullschool.collect.ConstSortedMap;
import net.nullschool.collect.MapIterator;
import net.nullschool.util.ObjectTools;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;

import static java.lang.Math.min;
import static net.nullschool.collect.basic.BasicTools.*;
import static net.nullschool.util.ArrayTools.*;

/**
 * 2013-04-29<p/>
 *
 * @author Cameron Beccario
 */
public enum BasicConstSortedMap {;

    public static <K, V> ConstSortedMap<K, V> of(Comparator<? super K> comparator) {
        return BasicSortedMap0.instance(comparator);
    }

    public static <K, V> ConstSortedMap<K, V> of(Comparator<? super K> comparator, K k0, V v0) {
        ObjectTools.compare(k0, k0, comparator);  // type check
        return new BasicSortedMap1<>(comparator, k0, v0);
    }

    public static <K, V> ConstSortedMap<K, V> of(Comparator<? super K> comparator, K k0, V v0, K k1, V v1) {
        int cmp = ObjectTools.compare(k0, k1, comparator);
        return cmp == 0 ?
            new BasicSortedMap1<K, V>(comparator, k0, v1) :
            cmp < 0 ?
                new BasicSortedMapN<K, V>(comparator, new Object[] {k0, k1}, new Object[] {v0, v1}) :
                new BasicSortedMapN<K, V>(comparator, new Object[] {k1, k0}, new Object[] {v1, v0});
    }

    @SuppressWarnings("unchecked")
    public static <K, V> ConstSortedMap<K, V> of(Comparator<? super K> comparator, K k0, V v0, K k1, V v1, K k2, V v2) {
        return of(comparator, (K[])new Object[] {k0, k1, k2}, (V[])new Object[] {v0, v1, v2});
    }

    public static <K, V> ConstSortedMap<K, V> of(Comparator<? super K> comparator, K[] keys, V[] values) { // untrusted
        // UNDONE: null propagation?
        int length = min(keys.length, values.length);
        switch (length) {
            case 0: return BasicSortedMap0.instance(comparator);
            case 1: return of(comparator, keys[0], values[0]);
            default:
                return condense(
                    comparator,
                    unionInto(EMPTY_OBJECT_ARRAY, EMPTY_OBJECT_ARRAY, keys, values, comparator));
        }
    }

    static <K, V> ConstSortedMap<K, V> condense(Comparator<? super K> comparator, BasicTools.MapColumns columns) {  // trusted arrays
        return condense(comparator, columns.keys, columns.values);
    }

    static <K, V> ConstSortedMap<K, V> condense(Comparator<? super K> comparator, Object[] keys, Object[] values) {  // trusted arrays
        assert keys.getClass() == Object[].class;
        assert values.getClass() == Object[].class;
        assert keys.length == values.length;
        switch (keys.length) {
            case 0: return BasicSortedMap0.instance(comparator);
            case 1: return new BasicSortedMap1<>(comparator, keys[0], values[0]);
            default: return new BasicSortedMapN<>(comparator, keys, values);
        }
    }

    @SuppressWarnings("unchecked")
    public static <K, V> ConstSortedMap<K, V> build(Comparator<? super K> comparator, Map<? extends K, ? extends V> map) {  // buildFrom? copy? create? convert? newInstance?
        // UNDONE: zero size map
        // UNDONE: null propagation?
        // UNDONE: correctness of this method. also see ConstSortedSet build.
        if (map instanceof AbstractBasicConstSortedMap &&
                Objects.equals(comparator, ((AbstractBasicConstSortedMap)map).comparator)) {
            // Safely covariant with AbstractBasicConstSortedMap<? extends K, ? extends V> because immutable.
            return (ConstSortedMap<K, V>)map;
        }
        if (map instanceof SortedMap && Objects.equals(comparator, ((SortedMap)map).comparator())) {
            return condense(comparator, copy(map));
        }
        // UNDONE: need to special case zero size, because building ConstImpl calls this.
        MapColumns mc = copy(map);
        return of(comparator, (K[])mc.keys, (V[])mc.values);
    }

    public static <K, V> ConstSortedMap<K, V> build(SortedMap<K, V> map) {
        // UNDONE: zero size map
        if (map instanceof AbstractBasicConstSortedMap) {
            return (ConstSortedMap<K, V>)map;
        }
        return condense(map.comparator(), copy(map));
    }

    static void write(AbstractBasicConstSortedMap<?, ?> map, ObjectOutputStream out) throws IOException {
        out.writeObject(map.comparator);
        // CONSIDER: write boxed native types as raw types to save on space ??
        final int size = map.size();
        out.writeInt(size);
        for (MapIterator<?, ?> iter = map.iterator(); iter.hasNext();) {
            out.writeObject(iter.next());
            out.writeObject(iter.value());
        }
    }

    static AbstractBasicConstSortedMap<?, ?> read(ObjectInputStream in) throws IOException, ClassNotFoundException {
        @SuppressWarnings("unchecked") Comparator<Object> comparator = (Comparator<Object>)in.readObject();
        final int size = in.readInt();
        switch (size) {
            case 0: return BasicSortedMap0.instance(comparator);
            case 1: return new BasicSortedMap1<>(comparator, in.readObject(), in.readObject()); // UNDONE: type check
            default:
                Object[] keys = new Object[size];
                Object[] values = new Object[size];
                for (int i = 0; i < size; i++) {
                    keys[i] = in.readObject();
                    values[i] = in.readObject();
                }
                // UNDONE: what if this re-sort means we now have duplicate elements?
                // I guess the same could be said of ConstMap, if element.equals() method
                // changed between serialization and deserialization.
                // UNDONE: Arrays.sort(keys, comparator);  use unionInto here?
                return new BasicSortedMapN<>(comparator, keys, values); // UNDONE: check how TreeMap verifies all elements are unique
        }
    }

}
