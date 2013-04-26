package net.nullschool.collect;

import java.util.*;


/**
 * 2013-02-14<p/>
 *
 * @author Cameron Beccario
 */
public enum IteratorTools {;

    private static final class EmptyMapIterator<K, V> implements MapIterator<K, V> {
        private static final EmptyMapIterator INSTANCE = new EmptyMapIterator();

        public boolean hasNext() { return false; }
        public K next() { throw new NoSuchElementException(); }
        public V value() { throw new IllegalStateException(); }
        public Map.Entry<K, V> entry() { throw new IllegalStateException(); }
        public void remove() { throw new IllegalStateException(); }
    }

    @SuppressWarnings("unchecked")
    public static <K, V> MapIterator<K, V> emptyMapIterator() {
        return EmptyMapIterator.INSTANCE;
    }

    public static <K, V> MapIterator<K, V> mapIterator(Map<K, V> map) {
        if (map.isEmpty()) {
            return emptyMapIterator();
        }
        return map instanceof IterableMap ?
            ((IterableMap<K, V>)map).iterator() :
            asMapIterator(map.entrySet().iterator());
    }

    public static <K, V, E extends Map.Entry<K, V>> MapIterator<K, V> asMapIterator(final Iterator<E> iterator) {
        // UNDONE: null propagation?
        return new MapIterator<K, V>() {
            private E current;

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public K next() {
                return (current = iterator.next()).getKey();
            }

            @Override
            public V value() {
                if (current == null) {
                    throw new IllegalStateException();
                }
                return current.getValue();  // UNDONE: should the result of value() be undefined if called before next()?
            }

            @Override
            public E entry() {
                if (current == null) {
                    throw new IllegalStateException();
                }
                return current;  // UNDONE: wrap??
                                 // UNDONE: should the result of value() be undefined if called before next()?
            }

            @Override
            public void remove() {
                if (current == null) {
                    throw new IllegalStateException();
                }
                iterator.remove();
                current = null;
            }
        };
    }

    private static final class DualMapIterator<K, V> implements MapIterator<K, V> {

        private MapIterator<K, V> current;
        private MapIterator<K, V> next;

        private DualMapIterator(MapIterator<K, V> i0, MapIterator<K, V> i1) {
            this.current = i0;
            this.next = i1;
        }

        public boolean hasNext() {
            do {
                if (current.hasNext()) {
                    return true;
                }
                if (next == null) {
                    return false;
                }
                // Advance to the next iterator and try again.
                current = next;
                next = null;
            } while (true);
        }

        public K next() {
            do {
                try {
                    return current.next();
                }
                catch (NoSuchElementException e) {
                    if (next == null) {
                        throw e;
                    }
                }
                // Advance to the next iterator and try again.
                current = next;
                next = null;
            } while (true);
        }

        public V value() {
            return current.value();
        }

        public Map.Entry<K, V> entry() {
            return current.entry();
        }

        public void remove() {
            current.remove();
        }
    }

    private static <K, V> MapIterator<K, V> chainMapIterator(MapIterator<K, V> i0, MapIterator<K, V> i1) {
        if (i1 == EmptyMapIterator.INSTANCE && i0 != null) {
            return i0;
        }
        if (i0 == EmptyMapIterator.INSTANCE && i1 != null) {
            return i1;
        }
        return new DualMapIterator<>(i0, i1);
    }

    public static <K, V> MapIterator<K, V> chainMapIterator(MapIterator<K, V> i0, Map<K, V> map) {
        return chainMapIterator(i0, mapIterator(map));
    }
}
