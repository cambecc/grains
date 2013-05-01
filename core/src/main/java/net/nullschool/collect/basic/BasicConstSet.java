package net.nullschool.collect.basic;

import net.nullschool.collect.ConstSet;

import java.util.*;

import static net.nullschool.collect.basic.BasicTools.copy;
import static net.nullschool.collect.basic.BasicTools.unionInto;
import static net.nullschool.util.ArrayTools.EMPTY_OBJECT_ARRAY;

/**
 * 2013-03-16<p/>
 *
 * Utility methods for constructing instances of {@link ConstSet} that use arrays to store their elements, providing
 * a memory efficient implementation of ConstSet but with O(N) complexity for most set query operations, and O(N^2)
 * complexity for most set construction operations. These sets allow {@code null} elements and use {@link Object#equals}
 * and {@link Object#hashCode} to test for set membership.<p/>
 *
 * Note that these sets are not sorted. See {@link BasicConstSortedSet} to construct instances of ConstSortedSet.
 *
 * @author Cameron Beccario
 */
public enum BasicConstSet {;

    /**
     * Returns an empty ConstSet.
     *
     * @return a persistent empty set.
     */
    public static <E> ConstSet<E> emptySet() {
        return BasicSet0.instance();
    }

    /**
     * Returns a ConstSet with a single element.
     *
     * @param e0 the element.
     * @return a persistent set containing the specified element.
     */
    public static <E> ConstSet<E> setOf(E e0) {
        return new BasicSet1<>(e0);
    }

    /**
     * Returns a ConstSet of up to two elements. If the second argument is equal to the first, then a ConstSet
     * containing just the first element is returned. {@link Object#equals} and {@link Object#hashCode} are used
     * to test for uniqueness.
     *
     * @param e0 the first element.
     * @param e1 the (potential) second element.
     * @return a persistent set containing the unique elements from the provided arguments in the order they appear.
     */
    public static <E> ConstSet<E> setOf(E e0, E e1) {
        return Objects.equals(e1, e0) ?
            new BasicSet1<E>(e0) :
            new BasicSetN<E>(new Object[] {e0, e1});
    }

    /**
     * Returns a ConstSet comprised of the unique elements from the provided arguments. {@link Object#equals} and
     * {@link Object#hashCode} are used to test for uniqueness.
     *
     * @param e0 the first element.
     * @param e1 the (potential) second element.
     * @param e2 the (potential) third element.
     * @return a persistent set containing the unique elements from the provided arguments in the order they appear.
     */
    public static <E> ConstSet<E> setOf(E e0, E e1, E e2) {
        return condense(unionInto(EMPTY_OBJECT_ARRAY, new Object[] {e0, e1, e2}));
    }

    /**
     * Returns a ConstSet comprised of the unique elements from the provided arguments. {@link Object#equals} and
     * {@link Object#hashCode} are used to test for uniqueness.
     *
     * @return a persistent set containing the unique elements from the provided arguments in the order they appear.
     */
    public static <E> ConstSet<E> setOf(E e0, E e1, E e2, E e3) {
        return condense(unionInto(EMPTY_OBJECT_ARRAY, new Object[] {e0, e1, e2, e3}));
    }

    /**
     * Returns a ConstSet comprised of the unique elements from the provided arguments. {@link Object#equals} and
     * {@link Object#hashCode} are used to test for uniqueness.
     *
     * @return a persistent set containing the unique elements from the provided arguments in the order they appear.
     */
    public static <E> ConstSet<E> setOf(E e0, E e1, E e2, E e3, E e4) {
        return condense(unionInto(EMPTY_OBJECT_ARRAY, new Object[] {e0, e1, e2, e3, e4}));
    }

    /**
     * Returns a ConstSet comprised of the unique elements from the provided arguments. {@link Object#equals} and
     * {@link Object#hashCode} are used to test for uniqueness.
     *
     * @param additional all additional elements past the sixth element.
     * @return a persistent set containing the unique elements from the provided arguments in the order they appear.
     */
    @SafeVarargs
    public static <E> ConstSet<E> setOf(E e0, E e1, E e2, E e3, E e4, E e5, E... additional) {
        Object[] elements = new Object[6 + additional.length];
        elements[0] = e0;
        elements[1] = e1;
        elements[2] = e2;
        elements[3] = e3;
        elements[4] = e4;
        elements[5] = e5;
        System.arraycopy(additional, 0, elements, 6, additional.length);
        return condense(unionInto(EMPTY_OBJECT_ARRAY, elements));
    }

    /**
     * Converts the specified array of elements into a ConstSet comprised of the unique elements from the array.
     * {@link Object#equals} and {@link Object#hashCode} are used to test for uniqueness.
     *
     * @param elements the elements from which to build the set.
     * @return a persistent set containing the unique elements from the array in the order they appear.
     * @throws NullPointerException if {@code elements} is null.
     */
    public static <E> ConstSet<E> asSet(E[] elements) {
        return condense(unionInto(EMPTY_OBJECT_ARRAY, elements));
    }

    /**
     * Converts the specified collection into a ConstSet comprised of the unique elements encountered while iterating
     * over the collection. {@link Object#equals} and {@link Object#hashCode} are used to test for uniqueness.
     *
     * @param collection the collection.
     * @return a persistent set containing the unique elements from the collection in the order they appear.
     * @throws NullPointerException if {@code collection} is null.
     */
    public static <E> ConstSet<E> asSet(Collection<? extends E> collection) {
        if (collection instanceof AbstractBasicConstSet && !(collection instanceof AbstractBasicConstSortedSet)) {
            @SuppressWarnings("unchecked") ConstSet<E> covariant = (ConstSet<E>)collection;
            return covariant;  // The collection is already a non-sorted ConstSet.
        }
        return condense(unionInto(EMPTY_OBJECT_ARRAY, collection.toArray()));
    }

    /**
     * Constructs a ConstSet from the unique elements encountered while iterating with the specified iterator.
     * {@link Object#equals} and {@link Object#hashCode} are used to test for uniqueness.
     *
     * @param iterator the iterator.
     * @return a persistent set containing the unique elements of the iteration in the order they appear.
     * @throws NullPointerException if {@code iterator} is null.
     */
    public static <E> ConstSet<E> asSet(Iterator<? extends E> iterator) {
        return condense(unionInto(EMPTY_OBJECT_ARRAY, copy(iterator)));
    }

    /**
     * Instantiates the appropriate AbstractBasicConstSet implementation from the specified array of elements. The
     * array reference <b>must be trusted</b>:
     * <ol>
     *     <li>the array was defensively copied or is guaranteed to be invisible to external clients</li>
     *     <li>the component type is Object instead of a narrower type such as String or Integer</li>
     *     <li><i>the array contains only unique elements</i></li>
     * </ol>
     *
     * @param trustedElements the Object array of elements.
     * @return a size-appropriate implementation of AbstractBasicConstSet.
     */
    static <E> AbstractBasicConstSet<E> condense(Object[] trustedElements) {
        assert trustedElements.getClass() == Object[].class;
        switch (trustedElements.length) {
            case 0: return BasicSet0.instance();
            case 1: return new BasicSet1<>(trustedElements[0]);
            default: return new BasicSetN<>(trustedElements);
        }
    }
}
