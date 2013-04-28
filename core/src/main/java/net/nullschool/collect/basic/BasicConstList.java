package net.nullschool.collect.basic;

import net.nullschool.collect.ConstList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;

import static net.nullschool.collect.basic.BasicTools.*;

/**
 * 2013-03-15<p/>
 *
 * @author Cameron Beccario
 */
public final class BasicConstList {

    private BasicConstList() {
        throw new AssertionError();
    }

    public static <E> ConstList<E> of() {
        return BasicList0.instance();
    }

    public static <E> ConstList<E> of(E e0) {
        return new BasicList1<>(e0);
    }

    public static <E> ConstList<E> of(E e0, E e1) {
        return new BasicListN<>(new Object[] {e0, e1});
    }

    public static <E> ConstList<E> of(E e0, E e1, E e2) {
        return new BasicListN<>(new Object[] {e0, e1, e2});
    }

    @SafeVarargs
    public static <E> ConstList<E> of(E... elements) {  // untrusted array
        // UNDONE: null propagation?
        switch (elements.length) {
            case 0: return BasicList0.instance();
            case 1: return new BasicList1<>(elements[0]);
            default: return new BasicListN<>(copy(elements));
        }
    }

    static <E> ConstList<E> condense(Object[] elements) {  // trusted array
        assert elements.getClass() == Object[].class;
        switch (elements.length) {
            case 0: return BasicList0.instance();
            case 1: return new BasicList1<>(elements[0]);
            default: return new BasicListN<>(elements);
        }
    }

    @SuppressWarnings("unchecked")
    public static <E> ConstList<E> build(Collection<? extends E> c) {  // buildFrom? copy? create? convert? newInstance?
        // UNDONE: null propagation? if so, has ramifications for BasicList0.withAll();
        if (c instanceof AbstractBasicConstList) {
            // Safely covariant with AbstractBasicConstList<? extends E> because immutable.
            return (ConstList<E>)c;
        }
        return condense(copy(c));
    }

    static void write(AbstractBasicConstList<?> list, ObjectOutputStream out) throws IOException {
        // CONSIDER: write boxed native types as raw types to save on space ??
        final int size = list.size();
        out.writeInt(size);
        // noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < size; i++) {
            out.writeObject(list.get(i));
        }
    }

    static AbstractBasicConstList<?> read(ObjectInputStream in) throws IOException, ClassNotFoundException {
        final int size = in.readInt();
        switch (size) {
            case 0: return BasicList0.instance();
            case 1: return new BasicList1<>(in.readObject());
            default:
                Object[] elements = new Object[size];
                for (int i = 0; i < size; i++) {
                    elements[i] = in.readObject();
                }
                return new BasicListN<>(elements);
        }
    }
}
