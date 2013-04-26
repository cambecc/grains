package net.nullschool.collect;

import java.util.Collection;
import java.util.SortedSet;


/**
 * 2013-03-18<p/>
 *
 * @author Cameron Beccario
 */
public interface ConstSortedSet<E> extends SortedSet<E>, ConstSet<E> {

    @Override ConstSortedSet<E> with(E e);

    @Override ConstSortedSet<E> withAll(Collection<? extends E> c);

    @Override ConstSortedSet<E> without(Object o);

    @Override ConstSortedSet<E> withoutAll(Collection<?> c);

    @Override ConstSortedSet<E> headSet(E toElement);

    @Override ConstSortedSet<E> tailSet(E fromElement);

    @Override ConstSortedSet<E> subSet(E fromElement, E toElement);

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
