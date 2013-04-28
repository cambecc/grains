package net.nullschool.collect.basic;

import net.nullschool.collect.ConstSortedSet;
import net.nullschool.util.ArrayTools;

import java.util.*;


/**
 * 2013-03-18<p/>
 *
 * @author Cameron Beccario
 */
final class BasicSortedSet0<E> extends AbstractBasicConstSortedSet<E> {

    private static final BasicSortedSet0 NATURAL_INSTANCE = new BasicSortedSet0<Object>(null);

    @SuppressWarnings("unchecked")
    static <E> BasicSortedSet0<E> instance(Comparator<? super E> comparator) {
        return comparator == null ? NATURAL_INSTANCE : new BasicSortedSet0<>(comparator);
    }

    private BasicSortedSet0(Comparator<? super E> comparator) {
        super(comparator);
    }

    @Override public int size() {
        return 0;
    }

    @Override public boolean isEmpty() {
        return true;
    }

    @Override public Iterator<E> iterator() {
        return Collections.emptyIterator();
    }

    @Override public boolean contains(Object o) {
        return false;
    }

    @Override public boolean containsAll(Collection<?> c) {
        return c.isEmpty();
    }

    @Override E get(int index) {
        throw new IndexOutOfBoundsException();
    }

    @Override public E first() {
        throw new NoSuchElementException();
    }

    @Override public E last() {
        throw new NoSuchElementException();
    }

    @Override public Object[] toArray() {
        return ArrayTools.EMPTY_OBJECT_ARRAY;
    }

    @Override public <T> T[] toArray(T[] a) {
        if (a.length > 0) {
            a[0] = null;
        }
        return a;
    }

    @Override public ConstSortedSet<E> with(E e) {
        return BasicConstSortedSet.of(comparator, e);
    }

    @Override public ConstSortedSet<E> withAll(Collection<? extends E> c) {
        return c.isEmpty() ? this : BasicConstSortedSet.build(comparator, c);
    }

    @Override public ConstSortedSet<E> without(Object o) {
        return this;
    }

    @Override public ConstSortedSet<E> withoutAll(Collection<?> c) {
        Objects.requireNonNull(c);
        return this;
    }

    @Override public ConstSortedSet<E> headSet(E toElement) {
        compare(toElement, toElement);  // type check
        return this;
    }

    @Override public ConstSortedSet<E> tailSet(E fromElement) {
        compare(fromElement, fromElement);  // type check
        return this;
    }

    @Override public ConstSortedSet<E> subSet(E fromElement, E toElement) {
        if (compare(fromElement, toElement) > 0) {
            throw new IllegalArgumentException("fromElement cannot be greater than toElement");
        }
        return this;
    }

    @Override public boolean equals(Object that) {
        return this == that || that instanceof Set && ((Set<?>)that).isEmpty();
    }

    @Override public int hashCode() {
        return 0;
    }

    @Override public String toString() {
        return "[]";
    }
}
