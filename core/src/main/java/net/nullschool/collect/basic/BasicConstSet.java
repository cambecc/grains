package net.nullschool.collect.basic;

import net.nullschool.collect.ConstSet;
import net.nullschool.util.ArrayTools;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import static net.nullschool.collect.basic.BasicTools.*;

/**
 * 2013-03-16<p/>
 *
 * @author Cameron Beccario
 */
public enum BasicConstSet {;

    public static <E> ConstSet<E> of() {
        return BasicSet0.instance();
    }

    public static <E> ConstSet<E> of(E e0) {
        return new BasicSet1<>(e0);
    }

    public static <E> ConstSet<E> of(E e0, E e1) {
        return Objects.equals(e1, e0) ?
            new BasicSet1<E>(e0) :
            new BasicSetN<E>(new Object[] {e0, e1});
    }

    public static <E> ConstSet<E> of(E e0, E e1, E e2) {
        if (Objects.equals(e1, e0)) {
            return of(e0, e2);
        }
        if (Objects.equals(e2, e0) || Objects.equals(e2, e1)) {
            return new BasicSetN<>(new Object[] {e0, e1});
        }
        return new BasicSetN<>(new Object[] {e0, e1, e2});
    }

    @SafeVarargs
    public static <E> ConstSet<E> of(E... elements) {  // untrusted array
        // UNDONE: null propagation?
        int length = elements.length;
        switch (length) {
            case 0: return BasicSet0.instance();
            case 1: return new BasicSet1<>(elements[0]);
            default: return condense(unionInto(ArrayTools.EMPTY_OBJECT_ARRAY, elements));
        }
    }

    static <E> ConstSet<E> condense(Object[] elements) {  // trusted array
        assert elements.getClass() == Object[].class;
        switch (elements.length) {
            case 0: return BasicSet0.instance();
            case 1: return new BasicSet1<>(elements[0]);
            default: return new BasicSetN<>(elements);
        }
    }

    @SuppressWarnings("unchecked")
    public static <E> ConstSet<E> build(Collection<? extends E> c) {  // buildFrom? copy? create? convert? newInstance?
        // UNDONE: null propagation?
        if (c instanceof AbstractBasicConstSet) {
            // Safely covariant with AbstractBasicConstSet<? extends E> because immutable.
            return (ConstSet<E>)c;
        }
        if (c instanceof Set) {
            return condense(copy(c));  // UNDONE: note that c's concept of equality might be based on something
                                       //         other than equals and hashCode, like a comparator, meaning we
                                       //         might get duplicates here.
        }
        return (ConstSet<E>)of(c.toArray());
    }

    static void write(AbstractBasicConstSet<?> set, ObjectOutputStream out) throws IOException {
        // CONSIDER: write boxed native types as raw types to save on space ??
        final int size = set.size();
        out.writeInt(size);
        for (int i = 0; i < size; i++) {
            out.writeObject(set.get(i));
        }
    }

    static AbstractBasicConstSet<?> read(ObjectInputStream in) throws IOException, ClassNotFoundException {
        final int size = in.readInt();
        switch (size) {
            case 0: return BasicSet0.instance();
            case 1: return new BasicSet1<>(in.readObject());
            default:
                Object[] elements = new Object[size];
                for (int i = 0; i < size; i++) {
                    elements[i] = in.readObject();
                }
                return new BasicSetN<>(elements); // UNDONE: check how TreeSet verifies all elements are unique
        }
    }
}
