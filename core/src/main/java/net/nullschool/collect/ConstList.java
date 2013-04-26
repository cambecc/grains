package net.nullschool.collect;

import java.util.Collection;
import java.util.List;


/**
 * 2013-02-24<p/>
 *
 * @author Cameron Beccario
 */
public interface ConstList<E> extends List<E>, ConstCollection<E> {

    ConstList<E> with(E e);

    ConstList<E> with(int index, E e);  // UNDONE: same as void add(int index, E element)

    ConstList<E> withAll(Collection<? extends E> c);

    ConstList<E> replace(int index, E e);  // UNDONE: same as E set(int index, E element)

    ConstList<E> without(Object o);

    ConstList<E> without(int index);  // UNDONE: same as E remove(int index)

    ConstList<E> withoutAll(Collection<?> c);

    ConstList<E> subList(int fromIndex, int toIndex);

    // =================================================================================================================
    // Mutation methods marked @Deprecated to signify they should not be invoked.

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #with}
     */
    @Deprecated @Override boolean add(E e);

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #with}
     */
    @Deprecated @Override void add(int index, E element);

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #withAll}
     */
    @Deprecated @Override boolean addAll(Collection<? extends E> c);

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #withAll}
     */
    @Deprecated @Override boolean addAll(int index, Collection<? extends E> c);

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #replace}
     */
    @Deprecated @Override E set(int index, E element);

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #without}
     */
    @Deprecated @Override boolean remove(Object o);

    /**
     * This method throws {@link UnsupportedOperationException}.
     * @deprecated see {@link #without}
     */
    @Deprecated @Override E remove(int index);

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
     * @deprecated see {@link #withoutAll}
     */
    @Deprecated @Override void clear();
}
