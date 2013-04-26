package net.nullschool.collect;

import java.util.Iterator;


/**
 * 2013-03-01<p/>
 *
 * @author Cameron Beccario
 */
public abstract class AbstractUnmodifiableIterator<E> implements Iterator<E> {

    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
