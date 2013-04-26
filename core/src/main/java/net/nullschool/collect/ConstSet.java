package net.nullschool.collect;

import java.util.Collection;
import java.util.Set;


/**
 * 2013-02-15<p/>
 *
 * @author Cameron Beccario
 */
public interface ConstSet<E> extends Set<E>, ConstCollection<E> {

    ConstSet<E> with(E e);

    ConstSet<E> withAll(Collection<? extends E> c);

    ConstSet<E> without(Object o);

    ConstSet<E> withoutAll(Collection<?> c);

    // =================================================================================================================
    // Mutation methods marked @Deprecated to signify they should not be invoked.

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #with}
     */
    @Deprecated @Override boolean add(E e);

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #withAll}
     */
    @Deprecated @Override boolean addAll(Collection<? extends E> c);

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #without}
     */
    @Deprecated @Override boolean remove(Object o);

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #withoutAll}
     */
    @Deprecated @Override boolean removeAll(Collection<?> c);

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #withoutAll}
     */
    @Deprecated @Override boolean retainAll(Collection<?> c);

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #without}
     */
    @Deprecated @Override void clear();
}
