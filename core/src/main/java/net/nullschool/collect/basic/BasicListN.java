package net.nullschool.collect.basic;

import net.nullschool.collect.ConstList;
import net.nullschool.util.ArrayTools;

import java.lang.reflect.Array;
import java.util.*;

import static net.nullschool.collect.basic.BasicTools.*;

/**
 * 2013-03-15<p/>
 *
 * @author Cameron Beccario
 */
final class BasicListN<E> extends AbstractBasicConstList<E> {

    private final E[] elements;

    @SuppressWarnings("unchecked")
    BasicListN(Object[] elements) {
        assert elements.getClass() == Object[].class;
        assert elements.length > 1;
        this.elements = (E[])elements;
    }

    @Override public int size() {
        return elements.length;
    }

    @Override public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override public int indexOf(Object o) {
        return ArrayTools.indexOf(o, elements);
    }

    @Override public int lastIndexOf(Object o) {
        return ArrayTools.lastIndexOf(o, elements);
    }

    @Override public E get(int index) {
        return elements[index];
    }

    @Override public Object[] toArray() {
        return elements.clone();
    }

    @Override public <T> T[] toArray(T[] a) {
        int size = elements.length;
        // Cast is safe because Array.newInstance will return an array of type T[].
        @SuppressWarnings("unchecked") T[] result = a.length < size ?
            (T[])Array.newInstance(a.getClass().getComponentType(), size) :
            a;
        // noinspection SuspiciousSystemArraycopy
        System.arraycopy(elements, 0, result, 0, size);
        if (result.length > size) {
            result[size] = null;
        }
        return result;
    }

    @Override public ConstList<E> with(E e) {
        return with(elements.length, e);
    }

    @Override public ConstList<E> with(int index, E e) {
        return new BasicListN<>(insert(elements, index, e));
    }

    @Override public ConstList<E> withAll(Collection<? extends E> c) {
        return withAll(elements.length, c);
    }

    @Override public ConstList<E> withAll(int index, Collection<? extends E> c) {
        return c.isEmpty() ? this : new BasicListN<E>(insertAll(elements, index, c));
    }

    @Override public ConstList<E> replace(int index, E e) {
        return new BasicListN<>(BasicTools.replace(elements, index, e));
    }

    @Override public ConstList<E> without(Object o) {
        int index = ArrayTools.indexOf(o, elements);
        return index < 0 ? this : delete(index);
    }

    @Override public ConstList<E> delete(int index) {
        return BasicConstList.condense(BasicTools.delete(elements, index));
    }

    @Override public ConstList<E> withoutAll(Collection<?> c) {
        if (c.isEmpty()) {
            return this;
        }
        Object[] shrunk = deleteAll(elements, c);
        return shrunk.length == size() ? this : BasicConstList.<E>condense(shrunk);
    }

    @Override public ConstList<E> subList(int fromIndex, int toIndex) {
        return BasicConstList.condense(Arrays.copyOfRange(elements, fromIndex, toIndex));
    }

    @Override public int hashCode() {
        return Arrays.hashCode(elements);
    }
}
