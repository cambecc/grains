package net.nullschool.collect;

import java.util.Collection;


/**
 * 2013-02-15<p/>
 *
 * @author Cameron Beccario
 */
public interface ConstCollection<E> extends Collection<E> {

    ConstCollection<E> with(E e);

    ConstCollection<E> withAll(Collection<? extends E> c);

    ConstCollection<E> without(Object o);

    ConstCollection<E> withoutAll(Collection<?> c);

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
