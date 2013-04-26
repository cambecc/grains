package net.nullschool.collect;

import java.util.Iterator;


/**
 * 2013-03-01<p/>
 *
 * An Iterator that does not support the remove method.
 *
 * @author Cameron Beccario
 */
public abstract class AbstractUnmodifiableIterator<E> implements Iterator<E> {

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated this iterator cannot remove elements.
     */
    @Deprecated public final void remove() {
        throw new UnsupportedOperationException();
    }
}
