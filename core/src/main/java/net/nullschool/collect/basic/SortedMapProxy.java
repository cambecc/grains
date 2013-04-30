package net.nullschool.collect.basic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * 2013-04-29<p/>
 *
 * @author Cameron Beccario
 */
final class SortedMapProxy implements Serializable {

    private static final long serialVersionUID = 1;
    private transient AbstractBasicConstSortedMap<?, ?> map;

    SortedMapProxy(AbstractBasicConstSortedMap<?, ?> map) {
        this.map = map;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        BasicConstSortedMap.write(map, out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        map = BasicConstSortedMap.read(in);
    }

    Object readResolve() {
        return map;
    }
}
