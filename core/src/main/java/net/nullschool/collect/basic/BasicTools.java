package net.nullschool.collect.basic;


import net.nullschool.collect.IteratorTools;
import net.nullschool.collect.MapIterator;
import net.nullschool.util.ArrayTools;
import net.nullschool.util.ObjectTools;

import java.util.*;

import static java.lang.Math.min;
import static java.util.Objects.requireNonNull;

/**
 * 2013-03-15<p/>
 *
 * @author Cameron Beccario
 */

enum BasicTools {;

    /**
     * A utility class to hold the keys and values of a columnized map. Map entries are represented as
     * {@code {keys[i], values[i]}} for every index i.
     */
    static class MapColumns {
        final Object[] keys;
        final Object[] values;

        MapColumns(Object[] keys, Object[] values) {
            this.keys = keys;
            this.values = values;
        }
    }

    /**
     * Copies the specified array into a new array having component type Object.
     *
     * @param original the array to copy.
     * @return all the elements of the original array in a new Object[] instance.
     * @throws NullPointerException if original is null.
     */
    static Object[] copy(Object[] original) {
        return Arrays.copyOf(original, original.length, Object[].class);
    }

    /**
     * Copies the specified array into a new array of the specified length and having component type Object.
     * The resulting array is either truncated or padded with nulls if newLength is less than or greater than
     * the original array's length.
     *
     * @param original the array to copy.
     * @return all the elements of the original array in a new Object[] instance.
     * @throws NullPointerException if original is null.
     * @throws NegativeArraySizeException if newLength is negative.
     */
    static Object[] copy(Object[] original, int newLength) {
        return Arrays.copyOf(original, newLength, Object[].class);
    }

    /**
     * Copies the specified collection into a new array having component type Object.
     *
     * @param original the collection to copy.
     * @return all the elements of the original collection in a new Object[] instance.
     * @throws NullPointerException if the collection is null.
     */
    static Object[] copy(Collection<?> original) {
        // We must rely on the collection to allocate a correctly sized array for us because its size may
        // be changing concurrently. Note the contract for toArray() lets us avoid a wasteful defensive
        // copy of the result.
        // noinspection ToArrayCallWithZeroLengthArrayArgument
        return original.toArray(ArrayTools.EMPTY_OBJECT_ARRAY);
    }

    /**
     * Copies the elements of the specified iterator into a new array having component type Object.
     *
     * @param original the iterator to iterate.
     * @return all the elements of the specified iterator in a new Object[] instance.
     * @throws NullPointerException if the iterator is null.
     */
    static Object[] copy(Iterator<?> original) {
        List<Object> elements = new ArrayList<>();
        while (original.hasNext()) {
            elements.add(original.next());
        }
        return elements.toArray();
    }

    /**
     * Copies the specified map into an array of keys and an array of values, represented as a MapColumns
     * object. The ith index of the arrays represents entry {@code {ki, vi}} from the original map, and the
     * array component types are both Object.
     *
     * @param original the map to copy.
     * @return all the entries of the original array as a MapColumns object.
     * @throws NullPointerException if original is null.
     */
    static MapColumns copy(Map<?, ?> original) {
        final int expectedSize = original.size();
        Object[] keys = new Object[expectedSize];
        Object[] values = new Object[expectedSize];

        MapIterator<?, ?> iter = IteratorTools.newMapIterator(original);

        int i;
        for (i = 0; i < expectedSize && iter.hasNext(); i++) {
            keys[i] = iter.next();
            values[i] = iter.value();
        }

        if (!iter.hasNext()) {
            // Check if the iterator had fewer iterations than expected. If so, truncate the resulting arrays.
            return new MapColumns(
                i < expectedSize ? copy(keys, i) : keys,
                i < expectedSize ? copy(values, i) : values);
        }

        // The iterator has more iterations than expected. Just dump everything into lists and call toArray.
        List<Object> allKeys = new ArrayList<>(expectedSize + 8);
        List<Object> allValues = new ArrayList<>(expectedSize + 8);
        Collections.addAll(allKeys, keys);
        Collections.addAll(allValues, values);
        do {
            allKeys.add(iter.next());
            allValues.add(iter.value());
        } while (iter.hasNext());
        return new MapColumns(allKeys.toArray(), allValues.toArray());
    }

    /**
     * Returns a new array, having component type Object, that is the result of inserting an element into
     * an existing array at the specified index. All elements occurring after {@code index} are shifted down
     * to make room for the new element.
     *
     * @param original the array.
     * @param index the index where the new element should be inserted.
     * @param e the new element.
     * @return a new Object[] instance having all the elements of the original array and with the new element
     *         inserted at the specified index.
     * @throws NullPointerException if original is null.
     * @throws IndexOutOfBoundsException if index is less than zero or greater than the length of the original.
     */
    static Object[] insert(Object[] original, int index, Object e) {
        Object[] result = new Object[original.length + 1];
        System.arraycopy(original, 0, result, 0, index);
        result[index] = e;
        System.arraycopy(original, index, result, index + 1, original.length - index);
        return result;
    }

    /**
     * Returns a new array, having component type Object, that is the result of inserting all the elements
     * of the specified collection into an existing array starting at the specified index. All original
     * elements occurring after {@code index} are shifted down to make room for the new elements.
     *
     * @param original the array.
     * @param index the starting index where the new elements should be inserted.
     * @param c the collection of elements to insert.
     * @return a new Object[] instance having all the elements of the original array and with the new elements
     *         inserted at the specified index.
     * @throws NullPointerException if original or c is null.
     * @throws IndexOutOfBoundsException if index is less than zero or greater than the length of the original.
     */
    static Object[] insertAll(Object[] original, int index, Collection<?> c) {
        final Object[] elements = c.toArray();
        Object[] result = new Object[original.length + elements.length];
        System.arraycopy(original, 0, result, 0, index);
        System.arraycopy(elements, 0, result, index, elements.length);
        System.arraycopy(original, index, result, index + elements.length, original.length - index);
        return result;
    }

    /**
     * Returns a new array, having component type Object, that is the result of replacing the element at
     * the {@code index} with the specified element.
     *
     * @param original the array.
     * @param index the index of the element to replace.
     * @param e the new element to store in the array at the specified index.
     * @return a new Object[] containing all elements of the original, but with the specified element replaced.
     * @throws NullPointerException if original is null.
     * @throws IndexOutOfBoundsException if index is less than 0 or greater than or equal to the length of the original.
     */
    static Object[] replace(Object[] original, int index, Object e) {
        Object[] result = copy(original);
        result[index] = e;
        return result;
    }

    /**
     * Returns a new array, having component type Object, that is the result of removing the element at
     * the specified index. All elements occurring after {@code index} are shifted up to fill the gap left
     * by thd deleted element.
     *
     * @param original the array.
     * @param index the index of the element to delete.
     * @return a new Object[] containing all elements of the original, but with the specified element removed.
     * @throws NullPointerException if original is null.
     * @throws IndexOutOfBoundsException if index is less than 0 or greater than or equal to the length of the original.
     * @throws NegativeArraySizeException if original is zero length.
     */
    static Object[] delete(Object[] original, int index) {
        final int resultLength = original.length - 1;
        Object[] result = new Object[resultLength];
        System.arraycopy(original, 0, result, 0, index);
        System.arraycopy(original, index + 1, result, index, resultLength - index);
        return result;
    }

    /**
     * Returns a new array, having component type Object, that is the result of removing from the original array
     * all elements contained in the specified collection. The collection's {@link Collection#contains contains}
     * method is used to determine if the collection contains elements from {@code original}.
     *
     * @param original the array.
     * @param c the collection of elements to delete.
     * @return a new Object[] containing all elements of the original except those in c.
     * @throws NullPointerException if original or c is null.
     * @throws ClassCastException if any element of {@code original} is incompatible with the collection.
     */
    static Object[] deleteAll(Object[] original, Collection<?> c) {
        Object[] result = new Object[original.length];
        requireNonNull(c);
        int cursor = 0;
        for (Object o : original) {
            if (!c.contains(o)) {
                result[cursor++] = o;
            }
        }
        return cursor == result.length ? result : copy(result, cursor);
    }

    /**
     * Returns new map columns, having component type Object, that is the result of removing from the original
     * columns all keys contained in the specified collection. The collection's {@link Collection#contains contains}
     * method is used to determine if the collection contains the elements in {@code keys}.
     *
     * @param keys the key column of the map.
     * @param values the value column of the map.
     * @param c the collection of keys to delete.
     * @return a new MapColumns object containing all entries of the original columns except those in c.
     * @throws NullPointerException if keys, values, or c is null.
     * @throws ClassCastException if any element of {@code keys} is incompatible with the collection.
     * @throws IndexOutOfBoundsException if {@code values} is smaller than {@code keys}.
     */
    static MapColumns deleteAll(Object[] keys, Object[] values, Collection<?> c) {
        final int length = min(keys.length, values.length);
        Object[] resultKeys = new Object[length];
        Object[] resultValues = new Object[length];
        requireNonNull(c);
        int cursor = 0;
        for (int i = 0; i < length; i++) {
            Object key = keys[i];
            if (!c.contains(key)) {
                resultKeys[cursor] = key;
                resultValues[cursor++] = values[i];
            }
        }
        return new MapColumns(
            cursor < length ? copy(resultKeys, cursor) : resultKeys,
            cursor < length ? copy(resultValues, cursor) : resultValues);
    }

    /**
     * Returns a new array, having component type Object, that is the result of merging the original array's elements
     * with all unique elements from the {@code additional} array not already contained in the {@code original} array.
     * Elements are compared using {@link Object#equals}. Merged elements are appended to the end of the resulting
     * array in the order they are encountered. Note that duplicate elements from the original array, if any, are
     * <i>retained</i> in the result but duplicate elements from the additional array are <i>excluded</i> from the
     * result. For example: {@code unionInto([1, 1], [1, 0, 0])} returns {@code [1, 1, 0]}.
     *
     * @param original the existing elements.
     * @param additional the elements to union with the existing elements.
     * @return a new Object[] instance containing all elements of {@code original} appended with unique elements
     *         from {@code additional}.
     * @throws NullPointerException if either {@code original} or {@code additional} arrays are null.
     */
    static Object[] unionInto(Object[] original, Object[] additional) {
        // CONSIDER: this method is O(N^2). Switch to a different algorithm above a certain size?
        Object[] result = copy(original, original.length + additional.length);
        int cursor = original.length;
        for (Object element : additional) {
            // Do a linear search of the resulting elements found so far.
            if (ArrayTools.indexOf(element, result, 0, cursor) < 0) {
                result[cursor++] = element;
            }
        }
        // Truncate if array was not completely filled.
        return cursor == result.length ? result : copy(result, cursor);
    }

    /**
     * Returns a new array, having component type Object, that is the result of merging the original array's elements
     * with all unique elements from the {@code additional} array not already contained in the {@code original} array.
     * Elements are compared using {@link Comparator#compare}, or {@link Comparable natural ordering} if the comparator
     * is null. The resulting array is sorted according to this ordering. Note that duplicate elements from the
     * original array, if any, are <i>retained</i> in the result but duplicate elements from the additional array
     * are <i>excluded</i> from the result. For example: {@code unionInto([1, 1], [1, 0, 0])} returns
     * {@code [1, 1, 0]}.<p/>
     *
     * <em>NOTE:</em> If the ordering of the {@code original} array's elements is not consistent with the specified
     * comparator (or natural ordering if the comparator is null), then the behavior of this method is undefined.
     *
     * @param original the existing elements, sorted with the ordering specified by comparator.
     * @param additional the elements to union with the existing elements.
     * @param comparator the comparison operator, or null if natural ordering is to be used.
     * @return a new Object[] instance containing all elements of {@code original} appended with unique elements
     *         from {@code additional}.
     * @throws NullPointerException if either {@code original} or {@code additional} arrays are null, or any
     *                              two elements are null and the comparator is either null or does not permit
     *                              null arguments.
     * @throws ClassCastException if any two elements' types are not compatible for comparison.
     */
    static Object[] unionInto(Object[] original, Object[] additional, Comparator<?> comparator) {
        // CONSIDER: this method is O(N^2). Switch to a different algorithm above a certain size?
        Object[] result = copy(original, original.length + additional.length);
        // Cast to comparator of object is safe here because each comparator will do its own type checking when
        // the compare method is invoked.
        @SuppressWarnings("unchecked") Comparator<Object> c = (Comparator<Object>)comparator;
        int cursor = original.length;
        for (Object element : additional) {
            // Do a binary search of the original sorted elements.
            if (Arrays.binarySearch(result, 0, original.length, element, c) < 0) {
                // Do a linear search of new unique elements found so far.
                if (ArrayTools.indexOf(element, result, original.length, cursor, c) < 0) {
                    result[cursor++] = element;
                }
            }
        }
        // Truncate if array was not completely filled. Sort the result.
        return ArrayTools.sort(cursor == result.length ? result : copy(result, cursor), c);
    }

    /**
     * Returns new map columns, having component type Object, that are the result of merging the original columns'
     * entries with all unique entries from the additional columns not already contained in the original columns.
     * Keys are compared using {@link Object#equals}. Merged entries are appended to the end of the resulting
     * columns in the order they are encountered. Note that duplicate keys from the original columns, if any, are
     * <i>retained</i> in the result but duplicate keys from the additional columns are <i>excluded</i> from the
     * result. However, those duplicate keys' values are retained in the result just as would occur when calling
     * {@link Map#put} on an existing key.
     *
     * @param originalKeys the existing keys.
     * @param originalValues the corresponding existing values.
     * @param additionalKeys the keys to union with the existing entries.
     * @param additionalValues the values to union with the existing entries.
     * @return a new MapColumns instance containing all original entries appended with the unique additional entries.
     * @throws NullPointerException if any argument is null.
     */
    static MapColumns unionInto(
        Object[] originalKeys,
        Object[] originalValues,
        Object[] additionalKeys,
        Object[] additionalValues) {

        final int originalLength = min(originalKeys.length, originalValues.length);
        final int additionalLength = min(additionalKeys.length, additionalValues.length);
        final int length = originalLength + additionalLength;
        Object[] resultKeys = copy(originalKeys, length);
        Object[] resultValues = copy(originalValues, length);

        int cursor = originalLength;
        for (int i = 0; i < additionalLength; i++) {
            Object newKey = additionalKeys[i];
            Object newValue = additionalValues[i];
            int index = ArrayTools.indexOf(newKey, resultKeys, 0, cursor);
            if (index < 0) {
                // Add a new unique entry
                resultKeys[cursor] = newKey;
                resultValues[cursor++] = newValue;
            }
            else {
                // Replace value of existing entry
                resultValues[index] = newValue;
            }
        }
        return new MapColumns(
            cursor < length ? copy(resultKeys, cursor) : resultKeys,
            cursor < length ? copy(resultValues, cursor) : resultValues);
    }

    /**
     * Returns new map columns, having component type Object, that are the result of merging the original columns'
     * entries with all unique entries from the additional columns not already contained in the original columns.
     * Keys are compared using {@link Comparator#compare}, or {@link Comparable natural ordering} if the comparator
     * is null. The resulting columns are sorted according to this ordering. Note that duplicate keys from the
     * original columns, if any, are <i>retained</i> in the result but duplicate keys from the additional columns
     * are <i>excluded</i> from the result. However, those duplicate keys' values are retained in the result just as
     * would occur when calling {@link Map#put} on an existing key.<p/>
     *
     * <em>NOTE:</em> If the ordering of the original column's keys is not consistent with the specified
     * comparator (or natural ordering if the comparator is null), then the behavior of this method is undefined.
     *
     * @param originalKeys the existing keys.
     * @param originalValues the corresponding existing values.
     * @param additionalKeys the keys to union with the existing entries.
     * @param additionalValues the values to union with the existing entries.
     * @return a new MapColumns instance containing all original entries appended with the unique additional entries.
     * @throws NullPointerException if any argument is null, or any two keys are null and the comparator is either
     *                              null or does not permit null arguments.
     * @throws ClassCastException if any two keys' types are not compatible for comparison.
     */
    static MapColumns unionInto(
        Object[] originalKeys,
        Object[] originalValues,
        Object[] additionalKeys,
        Object[] additionalValues,
        Comparator<?> comparator) {

        final int originalLength = min(originalKeys.length, originalValues.length);
        final int additionalLength = min(additionalKeys.length, additionalValues.length);
        final int length = originalLength + additionalLength;
        Object[] resultKeys = copy(originalKeys, length);
        Object[] resultValues = copy(originalValues, length);

        // Cast to comparator of object is safe here because each comparator will do its own type checking when
        // the compare method is invoked.
        @SuppressWarnings("unchecked") Comparator<Object> c = (Comparator<Object>)comparator;

        int cursor = originalLength;
        for (int i = 0; i < additionalLength; i++) {
            Object key = additionalKeys[i];
            Object value = additionalValues[i];
            int index = Arrays.binarySearch(resultKeys, 0, cursor, key, c);
            if (index < 0) {
                // Need to insert, so shift everything down.
                index = flip(index);
                System.arraycopy(resultKeys, index, resultKeys, index + 1, cursor - index);
                System.arraycopy(resultValues, index, resultValues, index + 1, cursor - index);
                resultKeys[index] = key;
                resultValues[index] = value;
                cursor++;
            }
            else {
                // Replace value of existing entry.
                resultValues[index] = value;
            }
        }

        return new MapColumns(
            cursor < length ? copy(resultKeys, cursor) : resultKeys,
            cursor < length ? copy(resultValues, cursor) : resultValues);
    }

    /**
     * Flips a negative index into the corresponding proper positive index, as according to the formula described
     * by {@link Arrays#binarySearch(Object[], Object)}: {@code i := (-insertion_point - 1)}. This method returns
     * {@code insertion_point := (-1 - i)}.
     *
     * @param i the value to flip.
     * @return the flipped value.
     */
    static int flip(int i) {
        return -1 - i;
    }

    /**
     * Ensures the specified object is of a type compatible with the provided comparator.
     *
     * @param comparator the comparator.
     * @param item the item to check.
     * @return the item.
     * @throws NullPointerException if item is null and the comparator is null or does not support null values.
     * @throws ClassCastException if the item is of a type not compatible for comparison.
     */
    static <T> T checkType(Comparator<? super T> comparator, T item) {
        ObjectTools.compare(item, item, comparator);
        return item;
    }
}
