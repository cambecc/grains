package net.nullschool.collect.basic;

import net.nullschool.collect.AbstractUnmodifiableIterator;
import net.nullschool.collect.ConstSet;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * 2013-02-26<p/>
 *
 * @author Cameron Beccario
 */
abstract class AbstractBasicConstSet<E> extends AbstractSet<E> implements ConstSet<E>, Serializable {

    abstract E get(int index);

    private class Iter extends AbstractUnmodifiableIterator<E> {

        private final int size = size();
        private int i = 0;

        @Override public boolean hasNext() {
            return i < size;
        }

        @Override public E next() {
            if (i < size) {
                return get(i++);
            }
            throw new NoSuchElementException();
        }
    }

    @Override public Iterator<E> iterator() {
        return new Iter();
    }

    // -------------------------------------------------------------------------
    // Mutation methods marked final, always throw UnsupportedOperationException

    @Deprecated @Override public final boolean add(E e)                          { throw unsupported(); }
    @Deprecated @Override public final boolean addAll(Collection<? extends E> c) { throw unsupported(); }
    @Deprecated @Override public final boolean remove(Object o)                  { throw unsupported(); }
    @Deprecated @Override public final boolean removeAll(Collection<?> c)        { throw unsupported(); }
    @Deprecated @Override public final boolean retainAll(Collection<?> c)        { throw unsupported(); }
    @Deprecated @Override public final void clear()                              { throw unsupported(); }

    private static UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException();
    }

    // -------------------------------------------------------------------------
    // Java serialization support

    Object writeReplace() {
        return new SetProxy(this);
    }

    private void readObject(ObjectInputStream in) throws InvalidObjectException {
        throw new InvalidObjectException("proxy expected");
    }

    private static final long serialVersionUID = 1;
}
