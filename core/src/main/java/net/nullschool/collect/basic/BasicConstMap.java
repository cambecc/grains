package net.nullschool.collect.basic;

import net.nullschool.collect.ConstMap;
import net.nullschool.collect.MapIterator;
import net.nullschool.util.ArrayTools;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Objects;

import static java.lang.Math.min;
import static net.nullschool.collect.basic.BasicTools.*;

/**
 * 2013-03-17<p/>
 *
 * @author Cameron Beccario
 */
public final class BasicConstMap {

    private BasicConstMap() {
        throw new AssertionError();
    }

    public static <K, V> ConstMap<K, V> of() {
        return BasicMap0.instance();
    }

    public static <K, V> ConstMap<K, V> of(K k0, V v0) {
        return new BasicMap1<>(k0, v0);
    }

    public static <K, V> ConstMap<K, V> of(K k0, V v0, K k1, V v1) {
        return Objects.equals(k1, k0) ?
            new BasicMap1<K, V>(k0, v1) :
            new BasicMapN<K, V>(new Object[] {k0, k1}, new Object[] {v0, v1});
    }

    public static <K, V> ConstMap<K, V> of(K k0, V v0, K k1, V v1, K k2, V v2) {
        if (Objects.equals(k1, k0)) {
            return of(k0, v1, k2, v2);
        }
        if (Objects.equals(k2, k0)) {
            return new BasicMapN<>(new Object[] {k0, k1}, new Object[] {v2, v1});
        }
        if (Objects.equals(k2, k1)) {
            return new BasicMapN<>(new Object[] {k0, k1}, new Object[] {v0, v2});
        }
        return new BasicMapN<>(new Object[] {k0, k1, k2}, new Object[] {v0, v1, v2});
    }

    public static <K, V> ConstMap<K, V> of(K[] keys, V[] values) {  // untrusted arrays
        // UNDONE: null propagation?
        int length = min(keys.length, values.length);
        switch (length) {
            case 0: return BasicMap0.instance();
            case 1: return new BasicMap1<>(keys[0], values[0]);
            default: return condense(unionInto(ArrayTools.EMPTY_OBJECT_ARRAY, ArrayTools.EMPTY_OBJECT_ARRAY, keys, values));
        }
    }

    static <K, V> ConstMap<K, V> condense(MapColumns columns) {  // trusted arrays
        return condense(columns.keys, columns.values);
    }

    static <K, V> ConstMap<K, V> condense(Object[] keys, Object[] values) {  // trusted arrays
        assert keys.getClass() == Object[].class;
        assert values.getClass() == Object[].class;
        assert keys.length == values.length;
        switch (keys.length) {
            case 0: return BasicMap0.instance();
            case 1: return new BasicMap1<>(keys[0], values[0]);
            default: return new BasicMapN<>(keys, values);
        }
    }

    @SuppressWarnings("unchecked")
    public static <K, V> ConstMap<K, V> build(Map<? extends K, ? extends V> map) {  // buildFrom? copy? create? convert? newInstance?
        // UNDONE: null propagation?
        if (map instanceof AbstractBasicConstMap) {
            // Safely covariant with AbstractBasicConstMap<? extends K, ? extends V> because immutable.
            return (ConstMap<K, V>)map;
        }
        // UNDONE: need to special case zero size, because building ConstImpl calls this.
        return condense(copy(map));
    }

    static void write(AbstractBasicConstMap<?, ?> map, ObjectOutputStream out) throws IOException {
        // CONSIDER: write boxed native types as raw types to save on space ??
        final int size = map.size();
        out.writeInt(size);
        for (MapIterator<?, ?> iter = map.iterator(); iter.hasNext();) {
            out.writeObject(iter.next());
            out.writeObject(iter.value());
        }
    }

    static AbstractBasicConstMap<?, ?> read(ObjectInputStream in) throws IOException, ClassNotFoundException {
        final int size = in.readInt();
        switch (size) {
            case 0: return BasicMap0.instance();
            case 1: return new BasicMap1<>(in.readObject(), in.readObject());
            default:
                Object[] keys = new Object[size];
                Object[] values = new Object[size];
                for (int i = 0; i < size; i++) {
                    keys[i] = in.readObject();
                    values[i] = in.readObject();
                }
                return new BasicMapN<>(keys, values); // UNDONE: check how TreeMap verifies all elements are unique
        }
    }
}
