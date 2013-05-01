package net.nullschool.collect.basic;

import net.nullschool.collect.ConstSortedSet;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

import static net.nullschool.collect.basic.BasicTools.unionInto;


/**
 * 2013-03-18<p/>
 *
 * @author Cameron Beccario
 */
final class BasicSortedSet1<E> extends AbstractBasicConstSortedSet<E> {

    private final E e0;

    @SuppressWarnings("unchecked")
    BasicSortedSet1(Comparator<? super E> comparator, Object e0) {
        super(comparator);
        this.e0 = (E)e0;
    }

    @Override public int size() {
        return 1;
    }

    @Override public boolean isEmpty() {
        return false;
    }

    @Override public boolean contains(Object o) {
        @SuppressWarnings("unchecked") E e = (E)o;
        return compare(e, e0) == 0;
    }

    @Override E get(int index) {
        if (index == 0) {
            return e0;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override public E first() {
        return e0;
    }

    @Override public E last() {
        return e0;
    }

    @Override public Object[] toArray() {
        return new Object[] {e0};
    }

    @Override public ConstSortedSet<E> with(E e) {
        int cmp = compare(e, e0);
        return cmp == 0 ?
            this :
            cmp < 0 ?
                new BasicSortedSetN<>(comparator, new Object[] {e, e0}) :
                new BasicSortedSetN<>(comparator, new Object[] {e0, e});
    }

    @Override public ConstSortedSet<E> withAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return this;
        }
        Object[] expanded = unionInto(new Object[] {e0}, c.toArray(), comparator);
        return expanded.length == size() ? this : BasicConstSortedSet.condense(comparator, expanded);
    }

    @Override public ConstSortedSet<E> without(Object o) {
        return !contains(o) ? this : BasicConstSortedSet.emptySortedSet(comparator);
    }

    @Override public ConstSortedSet<E> withoutAll(Collection<?> c) {
        // Just like AbstractCollection.removeAll, use the specified collection's "contains" method
        // to test for equality rather than this set's comparator.
        return !c.contains(e0) ? this : BasicConstSortedSet.emptySortedSet(comparator);
    }

    @Override public ConstSortedSet<E> headSet(E toElement) {
        return compare(e0, toElement) < 0 ? this : BasicConstSortedSet.emptySortedSet(comparator);
    }

    @Override public ConstSortedSet<E> tailSet(E fromElement) {
        return compare(fromElement, e0) <= 0 ? this : BasicConstSortedSet.emptySortedSet(comparator);
    }

    @Override public ConstSortedSet<E> subSet(E fromElement, E toElement) {
        if (compare(fromElement, toElement) > 0) {
            throw new IllegalArgumentException("fromElement cannot be greater than toElement");
        }
        int from = compare(fromElement, e0);
        int to = compare(e0, toElement);
        return from <= 0 && to < 0 ? this : BasicConstSortedSet.emptySortedSet(comparator);
    }

    @Override public int hashCode() {
        return Objects.hashCode(e0);
    }
}
