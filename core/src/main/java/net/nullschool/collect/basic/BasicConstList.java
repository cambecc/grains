package net.nullschool.collect.basic;

import net.nullschool.collect.ConstList;

import java.util.Collection;
import java.util.Iterator;

import static net.nullschool.collect.basic.BasicTools.*;

/**
 * 2013-03-15<p/>
 *
 * Utility methods for constructing instances of {@link ConstList} that use arrays to store their elements,
 * providing a memory efficient implementation of ConstList but with linear complexity for most list operations.
 *
 * @author Cameron Beccario
 */
public enum BasicConstList {;

    /**
     * Returns an empty ConstList.
     *
     * @return an immutable empty list.
     */
    public static <E> ConstList<E> emptyList() {
        return BasicList0.instance();
    }

    /**
     * Returns a ConstList with a single element.
     *
     * @param e0 the element.
     * @return an immutable list containing the specified element.
     */
    public static <E> ConstList<E> listOf(E e0) {
        return new BasicList1<>(e0);
    }

    /**
     * Returns a ConstList of two elements.
     *
     * @param e0 the first element.
     * @param e1 the second element.
     * @return an immutable list containing the specified elements.
     */
    public static <E> ConstList<E> listOf(E e0, E e1) {
        return new BasicListN<>(new Object[] {e0, e1});
    }

    /**
     * Returns a ConstList of three elements.
     *
     * @param e0 the first element.
     * @param e1 the second element.
     * @param e2 the third element.
     * @return an immutable list containing the specified elements.
     */
    public static <E> ConstList<E> listOf(E e0, E e1, E e2) {
        return new BasicListN<>(new Object[] {e0, e1, e2});
    }

    /**
     * Returns a ConstList of four elements.
     *
     * @return an immutable list containing the specified elements.
     */
    public static <E> ConstList<E> listOf(E e0, E e1, E e2, E e3) {
        return new BasicListN<>(new Object[] {e0, e1, e2, e3});
    }

    /**
     * Returns a ConstList of five elements.
     *
     * @return an immutable list containing the specified elements.
     */
    public static <E> ConstList<E> listOf(E e0, E e1, E e2, E e3, E e4) {
        return new BasicListN<>(new Object[] {e0, e1, e2, e3, e4});
    }

    /**
     * Returns a ConstList of many elements.
     *
     * @param additional all additional elements past the sixth element.
     * @return an immutable list containing the specified elements.
     */
    @SafeVarargs
    public static <E> ConstList<E> listOf(E e0, E e1, E e2, E e3, E e4, E e5, E... additional) {
        Object[] elements = new Object[6 + additional.length];
        elements[0] = e0;
        elements[1] = e1;
        elements[2] = e2;
        elements[3] = e3;
        elements[4] = e4;
        elements[5] = e5;
        System.arraycopy(additional, 0, elements, 6, additional.length);
        return new BasicListN<>(elements);
    }

    /**
     * Converts the specified array of elements into a ConstList.
     *
     * @param elements the elements of the list.
     * @return an immutable list containing the specified elements.
     * @throws NullPointerException if {@code elements} is null.
     */
    public static <E> ConstList<E> asList(E[] elements) {
        return condense(copy(elements));
    }

    /**
     * Converts the specified collection into a ConstList. The list is constructed from the sequence of elements
     * encountered while iterating over the collection.
     *
     * @param collection the collection.
     * @return an immutable list containing the elements of the specified collection.
     * @throws NullPointerException if {@code collection} is null.
     */
    public static <E> ConstList<E> asList(Collection<? extends E> collection) {
        if (collection.isEmpty()) {
            return emptyList();
        }
        if (collection instanceof AbstractBasicConstList) {
            @SuppressWarnings("unchecked") ConstList<E> covariant = (ConstList<E>)collection;
            return covariant;
        }
        return condense(copy(collection));
    }

    /**
     * Constructs a ConstList from the sequence of elements encountered while iterating with the specified iterator.
     *
     * @param iterator the iterator.
     * @return an immutable list containing the elements of the iteration.
     * @throws NullPointerException if {@code iterator} is null.
     */
    public static <E> ConstList<E> asList(Iterator<? extends E> iterator) {
        if (!iterator.hasNext()) {
            return emptyList();
        }
        return condense(copy(iterator));
    }

    /**
     * Instantiates the appropriate AbstractBasicConstList implementation from the specified array of elements. The
     * array reference must be trusted, i.e., the array was defensively copied or is guaranteed to be invisible
     * to external clients, and the component type is Object instead of a narrower type such as String or Integer.
     *
     * @param trustedElements the Object array of elements.
     * @return a size-appropriate implementation of AbstractBasicConstList.
     */
    static <E> AbstractBasicConstList<E> condense(Object[] trustedElements) {
        assert trustedElements.getClass() == Object[].class;
        switch (trustedElements.length) {
            case 0: return BasicList0.instance();
            case 1: return new BasicList1<>(trustedElements[0]);
            default: return new BasicListN<>(trustedElements);
        }
    }
}
