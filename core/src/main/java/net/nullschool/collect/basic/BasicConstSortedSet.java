package net.nullschool.collect.basic;

import net.nullschool.collect.ConstSortedSet;
import net.nullschool.util.ArrayTools;
import net.nullschool.util.ObjectTools;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import static net.nullschool.collect.basic.BasicTools.*;

/**
 * 2013-03-18<p/>
 *
 * @author Cameron Beccario
 */
public enum BasicConstSortedSet {;

    public static <E> ConstSortedSet<E> of(Comparator<? super E> comparator) {
        return BasicSortedSet0.instance(comparator);
    }

    public static <E> ConstSortedSet<E> of(Comparator<? super E> comparator, E e0) {
        ObjectTools.compare(e0, e0, comparator);  // type check
        return new BasicSortedSet1<>(comparator, e0);
    }

    public static <E> ConstSortedSet<E> of(Comparator<? super E> comparator, E e0, E e1) {
        int cmp = ObjectTools.compare(e0, e1, comparator);
        return cmp == 0 ?
            new BasicSortedSet1<>(comparator, e0) :
            cmp < 0 ?
                new BasicSortedSetN<>(comparator, new Object[] {e0, e1}) :
                new BasicSortedSetN<>(comparator, new Object[] {e1, e0});
    }

    @SuppressWarnings("unchecked")
    public static <E> ConstSortedSet<E> of(Comparator<? super E> comparator, E e0, E e1, E e2) {
        return of(comparator, (E[])new Object[] {e0, e1, e2});
    }

    @SafeVarargs
    public static <E> ConstSortedSet<E> of(Comparator<? super E> comparator, E... elements) {  // untrusted array
        // UNDONE: null propagation?
        int length = elements.length;
        switch (length) {
            case 0: return BasicSortedSet0.instance(comparator);
            case 1: return of(comparator, elements[0]);
            // UNDONE: copyUnique must use comparator for equality
            default: return condense(comparator, unionInto(ArrayTools.EMPTY_OBJECT_ARRAY, elements, comparator));
        }
    }

    static <E> ConstSortedSet<E> condense(Comparator<? super E> comparator, Object[] elements) {  // trusted array
        assert elements.getClass() == Object[].class;
        switch (elements.length) {
            case 0: return BasicSortedSet0.instance(comparator);
            case 1: return new BasicSortedSet1<>(comparator, elements[0]);
            default: return new BasicSortedSetN<>(comparator, elements);
        }
    }

    // construct const sorted set from elements in c with ordering of specified comparator
    @SuppressWarnings("unchecked")
    public static <E> ConstSortedSet<E> build(Comparator<? super E> comparator, Collection<? extends E> c) {  // buildFrom? copy? create? convert? newInstance?
        // UNDONE: zero size set c
        if (c instanceof AbstractBasicConstSortedSet &&
                Objects.equals(comparator, ((AbstractBasicConstSortedSet)c).comparator)) {
            // Safely covariant with AbstractBasicConstSortedSet<? extends E> because immutable.
            return (ConstSortedSet<E>)c;
        }
        if (c instanceof SortedSet && Objects.equals(comparator, ((SortedSet)c).comparator())) {
            return condense(comparator, copy(c));
        }
        if (c instanceof Set && comparator == null) {
            return condense(null, ArrayTools.sort(copy(c)));
        }
        return of(comparator, (E[])c.toArray());
    }

    // construct const sorted set from elements in s with same ordering as S
    public static <E> ConstSortedSet<E> build(SortedSet<E> s) {
        // UNDONE: zero size set s
        if (s instanceof AbstractBasicConstSortedSet) {
            return (ConstSortedSet<E>)s;
        }
        return condense(s.comparator(), copy(s));
    }

    static void write(AbstractBasicConstSortedSet<?> set, ObjectOutputStream out) throws IOException {
        out.writeObject(set.comparator);
        // CONSIDER: write boxed native types as raw types to save on space ??
        final int size = set.size();
        out.writeInt(size);
        for (int i = 0; i < size; i++) {
            out.writeObject(set.get(i));
        }
    }

    static AbstractBasicConstSortedSet<?> read(ObjectInputStream in) throws IOException, ClassNotFoundException {
        @SuppressWarnings("unchecked") Comparator<Object> comparator = (Comparator<Object>)in.readObject();
        final int size = in.readInt();
        switch (size) {
            case 0: return BasicSortedSet0.instance(comparator);
            case 1: return new BasicSortedSet1<>(comparator, in.readObject());  // UNDONE: element may no longer be compatible with comparator
            default:
                Object[] elements = new Object[size];
                for (int i = 0; i < size; i++) {
                    elements[i] = in.readObject();
                }
                // UNDONE: what if this re-sort means we now have duplicate elements?
                // I guess the same could be said of ConstSet, if element.equals() method
                // changed between serialization and deserialization.
                Arrays.sort(elements, comparator);
                return new BasicSortedSetN<>(comparator, elements); // UNDONE: check how TreeSet verifies all elements are unique
        }
    }

}
