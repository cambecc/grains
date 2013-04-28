package net.nullschool.collect.basic;

import net.nullschool.collect.ConstSortedSet;
import net.nullschool.util.ObjectTools;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.Comparator;


/**
 * 2013-03-18<p/>
 *
 * @author Cameron Beccario
 */
abstract class AbstractBasicConstSortedSet<E> extends AbstractBasicConstSet<E> implements ConstSortedSet<E> {

    final Comparator<? super E> comparator;

    AbstractBasicConstSortedSet(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    @Override public Comparator<? super E> comparator() {
        return comparator;
    }

    int compare(E left, E right) {
        return ObjectTools.compare(left, right, comparator);
    }

    // -------------------------------------------------------------------------
    // Java serialization support

    Object writeReplace() {
        return new SortedSetProxy(this);
    }

    private void readObject(ObjectInputStream in) throws InvalidObjectException {
        throw new InvalidObjectException("proxy expected");
    }

    private static final long serialVersionUID = 1;
}
