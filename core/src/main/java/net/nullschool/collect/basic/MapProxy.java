package net.nullschool.collect.basic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * 2013-03-17<p/>
 *
 * @author Cameron Beccario
 */
final class MapProxy implements Serializable {

    private static final long serialVersionUID = 1;
    private transient AbstractBasicConstMap<?, ?> map;

    MapProxy(AbstractBasicConstMap<?, ?> map) {
        this.map = map;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        BasicConstMap.write(map, out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        map = BasicConstMap.read(in);
    }

    Object readResolve() {
        return map;
    }
}
