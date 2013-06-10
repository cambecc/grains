package net.nullschool.grains.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import net.nullschool.collect.*;

import java.util.Comparator;
import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-06-03<p/>
 *
 * Kryo serializer for BasicConstSortedMap using the form {comparator, int_size, k0, v0, k1, v1, ..., kN, vN}.
 *
 * @author Cameron Beccario
 */
public class BasicConstSortedMapSerializer extends Serializer<ConstSortedMap> {

    @Override public void write(Kryo kryo, Output output, ConstSortedMap map) {
        kryo.writeClassAndObject(output, map.comparator());
        output.writeInt(map.size(), true);
        for (MapIterator<?, ?> iter = map.iterator(); iter.hasNext();) {
            kryo.writeClassAndObject(output, iter.next());
            kryo.writeClassAndObject(output, iter.value());
        }
    }

    @Override public ConstSortedMap<?, ?> read(Kryo kryo, Input input, Class<ConstSortedMap> type) {
        @SuppressWarnings("unchecked")
        Comparator<Object> comparator = (Comparator<Object>)kryo.readClassAndObject(input);
        final int size = input.readInt(true);
        switch (size) {
            case 0: return emptySortedMap(comparator);
            case 1:
                Object key = kryo.readClassAndObject(input);
                Object value = kryo.readClassAndObject(input);
                return sortedMapOf(comparator, key, value);
            default:
                Object[] keys = new Object[size];
                Object[] values = new Object[size];
                for (int i = 0; i < size; i++) {
                    keys[i] = kryo.readClassAndObject(input);
                    values[i] = kryo.readClassAndObject(input);
                }
                return asSortedMap(comparator, keys, values);
        }
    }
}
