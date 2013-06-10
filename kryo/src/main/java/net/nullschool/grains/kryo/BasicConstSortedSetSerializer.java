package net.nullschool.grains.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import net.nullschool.collect.ConstSortedSet;

import java.util.Comparator;
import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-06-03<p/>
 *
 * Kryo serializer for BasicConstSortedSet using the form {comparator, int_size, E0, E1, ..., En}.
 *
 * @author Cameron Beccario
 */
public class BasicConstSortedSetSerializer extends Serializer<ConstSortedSet> {

    @Override public void write(Kryo kryo, Output output, ConstSortedSet set) {
        kryo.writeClassAndObject(output, set.comparator());
        output.writeInt(set.size(), true);
        for (Object o : set) {
            kryo.writeClassAndObject(output, o);
        }
    }

    @Override public ConstSortedSet<?> read(Kryo kryo, Input input, Class<ConstSortedSet> type) {
        @SuppressWarnings("unchecked")
        Comparator<Object> comparator = (Comparator<Object>)kryo.readClassAndObject(input);
        final int size = input.readInt(true);
        switch (size) {
            case 0: return emptySortedSet(comparator);
            case 1: return sortedSetOf(comparator, kryo.readClassAndObject(input));
            default:
                Object[] elements = new Object[size];
                for (int i = 0; i < size; i++) {
                    elements[i] = kryo.readClassAndObject(input);
                }
                return asSortedSet(comparator, elements);
        }
    }
}
