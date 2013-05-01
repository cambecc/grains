package net.nullschool.collect.basic;

import net.nullschool.collect.MapIterator;

import java.io.*;
import java.util.Comparator;

import static net.nullschool.collect.basic.BasicConstSortedMap.condense;
import static net.nullschool.collect.basic.BasicTools.checkType;
import static net.nullschool.collect.basic.BasicTools.unionInto;
import static net.nullschool.util.ArrayTools.EMPTY_OBJECT_ARRAY;


/**
 * 2013-04-29<p/>
 *
 * A serialization proxy for AbstractBasicConstSortedMap. This class handles serialization and deserialization for all
 * AbstractBasicConstSortedMap implementations using the Java Serialization Proxy pattern.
 *
 * @author Cameron Beccario
 */
final class SortedMapProxy implements Serializable {

    private static final long serialVersionUID = 1;
    private transient AbstractBasicConstSortedMap<?, ?> map;

    SortedMapProxy(AbstractBasicConstSortedMap<?, ?> map) {
        this.map = map;
    }

    /**
     * Writes the map in the form {comparator, int_size, k0, v0, k1, v1, ..., kN, vN}.
     *
     * @param out the output stream.
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(map.comparator);
        final int size = map.size();
        out.writeInt(size);
        for (MapIterator<?, ?> iter = map.iterator(); iter.hasNext();) {
            out.writeObject(iter.next());
            out.writeObject(iter.value());
        }
    }

    /**
     * Instantiates the appropriate AbstractBasicConstSortedMap implementation from the serialized form described
     * by {@link #writeObject}.
     *
     * @param in the stream containing the serialized form.
     * @return a size-appropriate implementation of AbstractBasicConstSortedMap.
     */
    private AbstractBasicConstSortedMap<?, ?> readMap(ObjectInputStream in) throws IOException, ClassNotFoundException {
        @SuppressWarnings("unchecked") Comparator<Object> comparator = (Comparator<Object>)in.readObject();
        final int size = in.readInt();
        switch (size) {
            case 0: return BasicSortedMap0.instance(comparator);
            case 1: return new BasicSortedMap1<>(comparator, checkType(comparator, in.readObject()), in.readObject());
            default:
                Object[] keys = new Object[size];
                Object[] values = new Object[size];
                for (int i = 0; i < size; i++) {
                    keys[i] = in.readObject();
                    values[i] = in.readObject();
                }
                return condense(comparator, unionInto(EMPTY_OBJECT_ARRAY, EMPTY_OBJECT_ARRAY, keys, values, comparator));
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        map = readMap(in);
    }

    Object readResolve() {
        return map;
    }
}
