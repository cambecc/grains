package net.nullschool.collect.basic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * 2013-03-16<p/>
 *
 * @author Cameron Beccario
 */
final class ListProxy implements Serializable {

    private static final long serialVersionUID = 1;
    private transient AbstractBasicConstList<?> list;

    ListProxy(AbstractBasicConstList<?> list) {
        this.list = list;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        BasicConstList.write(list, out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        list = BasicConstList.read(in);
    }

    Object readResolve() {
        return list;
    }
}
