package net.nullschool.util;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;


/**
 * 2013-02-11<p/>
 *
 * Utility methods for operating on arrays.
 *
 * @author Cameron Beccario
 */
public enum ArrayTools {;

    /**
     * Immutable Object[] of length 0.
     */
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    /**
     * Immutable Class[] of length 0.
     */
    public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
    /**
     * Immutable Type[] of length 0.
     */
    public static final Type[] EMPTY_TYPE_ARRAY = new Type[0];

    /**
     * Returns the index of the specified element's first occurrence in the provided array. If the element
     * cannot be found, -1 is returned. Element comparisons are made exactly as specified by {@link Objects#equals}.
     *
     * @param element the element to find
     * @param array the array to search
     * @param <T> the element type
     * @return the index of the first occurrence of the element, or -1 if it cannot be found.
     * @throws NullPointerException if the array is null
     */
    public static <T> int indexOf(T element, T[] array) {
        if (element == null) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        }
        else {
            for (int i = 0; i < array.length; i++) {
                if (element.equals(array[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Returns the index of the specified element's first occurrence in the provided array, searching from
     * {@code fromIndex} (inclusive) up to {@code toIndex} (exclusive). If the element cannot be found, -1
     * is returned. Element comparisons are made exactly as specified by {@link Objects#equals}.
     *
     * @param element the element to find
     * @param array the array to search
     * @param fromIndex the index at which to start searching
     * @param toIndex the index at which to stop searching
     * @param <T> the element type
     * @return the index of the first occurrence of the element in the search range, or -1 if it cannot be found.
     * @throws IllegalArgumentException if {@code fromIndex > toIndex}
     * @throws IndexOutOfBoundsException if {@code fromIndex < 0 || toIndex > array.length}
     * @throws NullPointerException if the array is null
     */
    public static <T> int indexOf(T element, T[] array, int fromIndex, int toIndex) {
        checkRange(fromIndex, toIndex, array.length);
        if (element == null) {
            for (int i = fromIndex; i < toIndex; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        }
        else {
            for (int i = fromIndex; i < toIndex; i++) {
                if (element.equals(array[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Returns the index of the specified element's first occurrence in the provided array. If the element
     * cannot be found, -1 is returned. Element comparisons are made using either the given comparator exactly
     * as specified by {@link Comparator#compare}, or the element's {@link Comparable natural ordering} if the
     * comparator is null. Note the array is traversed linearly. If the array is sorted, {@link
     * java.util.Arrays#binarySearch(Object[], Object, Comparator)} will offer better performance.
     *
     * @param element the element to find
     * @param array the array to search
     * @param comparator the comparator to use for equality testing, or null if natural ordering should be used.
     * @param <T> the element type
     * @return the index of the first occurrence of the element, or -1 if it cannot be found.
     * @throws NullPointerException if the array is null or the comparison does not allow nulls.
     * @throws ClassCastException if the element is not comparable to the elements of the array.
     */
    public static <T> int indexOf(T element, T[] array, Comparator<? super T> comparator) {
        if (comparator == null) {
            for (int i = 0; i < array.length; i++) {
                // Cast to Comparable<T> is safe because compareTo method will do type checking.
                @SuppressWarnings("unchecked") Comparable<T> comparable = (Comparable<T>)array[i];
                if (comparable.compareTo(element) == 0) {
                    return i;
                }
            }
        }
        else {
            for (int i = 0; i < array.length; i++) {
                if (comparator.compare(array[i], element) == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Returns the index of the specified element's first occurrence in the provided array, searching from
     * {@code fromIndex} (inclusive) up to {@code toIndex} (exclusive). If the element
     * cannot be found, -1 is returned. Element comparisons are made using either the given comparator exactly
     * as specified by {@link Comparator#compare}, or the element's {@link Comparable natural ordering} if the
     * comparator is null. Note the array is traversed linearly. If the array is sorted, {@link
     * java.util.Arrays#binarySearch(Object[], int, int, Object, Comparator)} will offer better performance.
     *
     * @param element the element to find
     * @param array the array to search
     * @param fromIndex the index at which to start searching
     * @param toIndex the index at which to stop searching
     * @param comparator the comparator to use for equality testing, or null if natural ordering should be used.
     * @param <T> the element type
     * @return the index of the first occurrence of the element, or -1 if it cannot be found.
     * @throws IllegalArgumentException if {@code fromIndex > toIndex}
     * @throws IndexOutOfBoundsException if {@code fromIndex < 0 || toIndex > array.length}
     * @throws NullPointerException if the array is null or the comparison does not allow nulls.
     * @throws ClassCastException if the element is not comparable to the elements of the array.
     */
    public static <T> int indexOf(T element, T[] array, int fromIndex, int toIndex, Comparator<? super T> comparator) {
        checkRange(fromIndex, toIndex, array.length);
        if (comparator == null) {
            for (int i = fromIndex; i < toIndex; i++) {
                // Cast to Comparable<T> is safe because compareTo method will do type checking.
                @SuppressWarnings("unchecked") Comparable<T> comparable = (Comparable<T>)array[i];
                if (comparable.compareTo(element) == 0) {
                    return i;
                }
            }
        }
        else {
            for (int i = fromIndex; i < toIndex; i++) {
                if (comparator.compare(array[i], element) == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Returns the index of the specified element's last occurrence in the provided array. If the element
     * cannot be found, -1 is returned. Element comparisons are made exactly as specified by {@link Objects#equals}.
     *
     * @param element the element to find
     * @param array the array to search
     * @param <T> the element type
     * @return the index of the last occurrence of the element, or -1 if it cannot be found.
     * @throws NullPointerException if the array is null
     */
    public static <T> int lastIndexOf(T element, T[] array) {
        if (element == null) {
            for (int i = array.length - 1; i >= 0; i--) {
                if (array[i] == null) {
                    return i;
                }
            }
        }
        else {
            for (int i = array.length - 1; i >= 0; i--) {
                if (element.equals(array[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Checks that a range fits within an array of the specified length.
     *
     * @param fromIndex the starting index of the range, inclusive.
     * @param toIndex the ending index of the range, exclusive.
     * @param length the length of the array.
     * @throws IndexOutOfBoundsException if fromIndex is less than zero or toIndex is greater than length.
     * @throws IllegalArgumentException if fromIndex is greater than toIndex.
     */
    public static void checkRange(int fromIndex, int toIndex, int length) {
        if (fromIndex < 0 || toIndex > length) {
            throw new IndexOutOfBoundsException(
                String.format("range [%d, %d) is outside bounds [%d, %d)", fromIndex, toIndex, 0, length));
        }
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException(
                String.format("fromIndex %d cannot be greater than toIndex %d", fromIndex, toIndex));
        }
    }

    /**
     * Sorts the specified array exactly as described by {@link Arrays#sort(Object[])}, returning the same array
     * instance.
     *
     * @param a the array to sort.
     * @throws ClassCastException if some elements of the array are not mutually comparable.
     * @throws IllegalArgumentException if the elements' natural ordering violates the {@link Comparable} contract.
     * @return the exact array instance passed to this method, sorted.
     */
    public static <T> T[] sort(T[] a) {
        Arrays.sort(a);
        return a;
    }

    /**
     * Sorts the specified array exactly as described by {@link Arrays#sort(Object[], Comparator)}, returning
     * the same array instance.
     *
     * @param a the array to sort.
     * @param c the comparator to use for sorting, or null if natural ordering should be used.
     * @throws ClassCastException if some elements of the array are not mutually comparable.
     * @throws IllegalArgumentException if the comparator violates the {@link Comparator} contract.
     * @return the exact array instance passed to this method, sorted.
     */
    public static <T> T[] sort(T[] a, Comparator<? super T> c) {
        Arrays.sort(a, c);
        return a;
    }
}
