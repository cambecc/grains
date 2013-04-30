package net.nullschool.collect.basic;

import net.nullschool.collect.ConstCollection;
import net.nullschool.collect.ConstSet;
import net.nullschool.collect.ConstSortedMap;
import net.nullschool.collect.ConstSortedSet;
import net.nullschool.util.ObjectTools;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.Comparator;


/**
 * 2013-04-29<p/>
 *
 * @author Cameron Beccario
 */
abstract class AbstractBasicConstSortedMap<K, V> extends AbstractBasicConstMap<K, V> implements ConstSortedMap<K, V> {

    final Comparator<? super K> comparator;

    AbstractBasicConstSortedMap(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }

    @Override public Comparator<? super K> comparator() {
        return comparator;
    }

    int compare(K left, K right) {
        return ObjectTools.compare(left, right, comparator);
    }

    @Override public abstract ConstSortedSet<K> keySet();

    @Override public abstract ConstCollection<V> values();

    @Override public abstract ConstSet<Entry<K, V>> entrySet();

    // -------------------------------------------------------------------------
    // Java serialization support

    Object writeReplace() {
        return new SortedMapProxy(this);
    }

    private void readObject(ObjectInputStream in) throws InvalidObjectException {
        throw new InvalidObjectException("proxy expected");
    }

    private static final long serialVersionUID = 1;
}
