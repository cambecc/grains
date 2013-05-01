package net.nullschool.collect.basic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * 2013-03-16<p/>
 *
 * A serialization proxy for AbstractBasicConstList. This class handles serialization and deserialization for all
 * AbstractBasicConstList implementations using the Java Serialization Proxy pattern.
 *
 * @author Cameron Beccario
 */
final class ListProxy implements Serializable {

    private static final long serialVersionUID = 1;
    private transient AbstractBasicConstList<?> list;

    ListProxy(AbstractBasicConstList<?> list) {
        this.list = list;
    }

    /**
     * Writes the list in the form {int_size, E0, E1, ..., En}.
     *
     * @param out the output stream.
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        final int size = list.size();
        out.writeInt(size);
        for (int i = 0; i < size; i++) {
            out.writeObject(list.get(i));
        }
    }

    /**
     * Instantiates the appropriate AbstractBasicConstList implementation from the serialized form described
     * by {@link #writeObject}.
     *
     * @param in the stream containing the serialized form.
     * @return a size-appropriate implementation of AbstractBasicConstList.
     */
    private AbstractBasicConstList<?> readList(ObjectInputStream in) throws IOException, ClassNotFoundException {
        final int size = in.readInt();
        switch (size) {
            case 0: return BasicList0.instance();
            case 1: return new BasicList1<>(in.readObject());
            default:
                Object[] elements = new Object[size];
                for (int i = 0; i < size; i++) {
                    elements[i] = in.readObject();
                }
                return new BasicListN<>(elements);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        list = readList(in);
    }

    Object readResolve() {
        return list;
    }
}
