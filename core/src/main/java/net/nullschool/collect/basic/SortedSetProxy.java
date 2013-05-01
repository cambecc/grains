package net.nullschool.collect.basic;

import java.io.*;
import java.util.Comparator;

import static net.nullschool.collect.basic.BasicConstSortedSet.condense;
import static net.nullschool.collect.basic.BasicTools.checkType;
import static net.nullschool.collect.basic.BasicTools.unionInto;
import static net.nullschool.util.ArrayTools.EMPTY_OBJECT_ARRAY;


/**
 * 2013-03-18<p/>
 *
 * A serialization proxy for AbstractBasicConstSet. This class handles serialization and deserialization for all
 * AbstractBasicConstSortedSet implementations using the Java Serialization Proxy pattern.
 *
 * @author Cameron Beccario
 */
final class SortedSetProxy implements Serializable {

    private static final long serialVersionUID = 1;
    private transient AbstractBasicConstSortedSet<?> set;

    SortedSetProxy(AbstractBasicConstSortedSet<?> set) {
        this.set = set;
    }

    /**
     * Writes the set in the form {comparator, int_size, E0, E1, ..., En}.
     *
     * @param out the output stream.
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(set.comparator);
        final int size = set.size();
        out.writeInt(size);
        for (int i = 0; i < size; i++) {
            out.writeObject(set.get(i));
        }
    }

    /**
     * Instantiates the appropriate AbstractBasicConstSortedSet implementation from the serialized form described
     * by {@link #writeObject}.
     *
     * @param in the stream containing the serialized form.
     * @return a size-appropriate implementation of AbstractBasicConstSortedSet.
     */
    private AbstractBasicConstSortedSet<?> readSet(ObjectInputStream in) throws IOException, ClassNotFoundException {
        @SuppressWarnings("unchecked") Comparator<Object> comparator = (Comparator<Object>)in.readObject();
        final int size = in.readInt();
        switch (size) {
            case 0: return BasicSortedSet0.instance(comparator);
            case 1: return new BasicSortedSet1<>(comparator, checkType(comparator, in.readObject()));
            default:
                Object[] elements = new Object[size];
                for (int i = 0; i < size; i++) {
                    elements[i] = in.readObject();
                }
                return condense(comparator, unionInto(EMPTY_OBJECT_ARRAY, elements, comparator));
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        set = readSet(in);
    }

    Object readResolve() {
        return set;
    }
}
