package net.nullschool.collect.basic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * 2013-03-18<p/>
 *
 * @author Cameron Beccario
 */
final class SortedSetProxy implements Serializable {

    private static final long serialVersionUID = 1;
    private transient AbstractBasicConstSortedSet<?> set;

    SortedSetProxy(AbstractBasicConstSortedSet<?> set) {
        this.set = set;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        BasicConstSortedSet.write(set, out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        set = BasicConstSortedSet.read(in);
    }

    Object readResolve() {
        return set;
    }
}
