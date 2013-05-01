package net.nullschool.collect.basic;

import net.nullschool.collect.ConstSet;

import java.util.Collection;
import java.util.Objects;

import static net.nullschool.collect.basic.BasicTools.unionInto;

/**
 * 2013-03-16<p/>
 *
 * @author Cameron Beccario
 */
final class BasicSet1<E> extends AbstractBasicConstSet<E> {

    private final E e0;

    @SuppressWarnings("unchecked")
    BasicSet1(Object e0) {
        this.e0 = (E)e0;
    }

    @Override public int size() {
        return 1;
    }

    @Override public boolean isEmpty() {
        return false;
    }

    @Override public boolean contains(Object o) {
        return Objects.equals(o, e0);
    }

    @Override E get(int index) {
        if (index == 0) {
            return e0;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override public Object[] toArray() {
        return new Object[] {e0};
    }

    @Override public ConstSet<E> with(E e) {
        return contains(e) ? this : new BasicSetN<E>(new Object[] {e0, e});
    }

    @Override public ConstSet<E> withAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return this;
        }
        Object[] expanded = unionInto(new Object[] {e0}, c.toArray());
        return expanded.length == size() ? this : BasicConstSet.<E>condense(expanded);
    }

    @Override public ConstSet<E> without(Object o) {
        return !contains(o) ? this : BasicConstSet.<E>emptySet();
    }

    @Override public ConstSet<E> withoutAll(Collection<?> c) {
        return !c.contains(e0) ? this : BasicConstSet.<E>emptySet();
    }

    @Override public int hashCode() {
        return Objects.hashCode(e0);
    }
}
