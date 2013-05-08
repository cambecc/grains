package net.nullschool.reflect;

import net.nullschool.collect.IterableMap;
import net.nullschool.collect.MapIterator;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.*;


/**
 * 2013-02-25<p/>
 *
 * @author Cameron Beccario
 */
public final class Casts {

    private Casts() {
        throw new AssertionError();
    }

    private static boolean iterate(Collection<?> c, Cast<?> elementCast) {
        for (Object o : c) {
            try {
                elementCast.cast(o);
            }
            catch (Exception e) {
                throw new AssertionError("iteration failed: " + o, e);
            }
        }
        return true;
    }

    private static boolean iterate(Map<?, ?> m, Cast<?> keyCast, Cast<?> valueCast) {
        if (m instanceof IterableMap) {
            for (MapIterator iter = ((IterableMap)m).iterator(); iter.hasNext();) {
                keyCast.cast(iter.next());
                valueCast.cast(iter.value());
            }
        }
        else {
            for (Map.Entry entry : m.entrySet()) {
                keyCast.cast(entry.getKey());
                valueCast.cast(entry.getValue());
            }
        }
        return true;
    }

    private static <T> Cast<T> checkType(final Class<T> clazz) {
        Objects.requireNonNull(clazz);
        return new Cast<T>() {
            @Override
            public T cast(Object o) {
                return clazz.cast(o);
            }

            @Override
            public String toString() {
                return "CAST:" + clazz.getSimpleName();
            }
        };
    }

    // UNDONE: reduce check* to just checkCollection

    private static <C> Cast<C> checkCollection(
        final Class<? extends Collection> collectionClass,
        final Cast<?> elementCheck) {

        Objects.requireNonNull(collectionClass);
        Objects.requireNonNull(elementCheck);

        return new Cast<C>() {
            @Override
            @SuppressWarnings("unchecked")
            public C cast(Object o) {
                Collection c = collectionClass.cast(o);
                assert c == null || iterate(c, elementCheck);
                return (C)c;
            }

            @Override
            public String toString() {
                return "COLCAST:" + collectionClass.getSimpleName() + "<" + elementCheck + ">";
            }
        };
    }

    private static <S> Cast<S> checkSet(
        final Class<? extends Set> setClass,
        final Cast<?> elementCheck) {

        Objects.requireNonNull(setClass);
        Objects.requireNonNull(elementCheck);

        return new Cast<S>() {
            @Override
            @SuppressWarnings("unchecked")
            public S cast(Object o) {
                Set set = setClass.cast(o);
                assert set == null || iterate(set, elementCheck);
                return (S)set;
            }

            @Override
            public String toString() {
                return "SETCAST:" + setClass.getSimpleName() + "<" + elementCheck + ">";
            }
        };
    }

    private static <L> Cast<L> checkList(
        final Class<? extends List> listClass,
        final Cast<?> elementCheck) {

        Objects.requireNonNull(listClass);
        Objects.requireNonNull(elementCheck);

        return new Cast<L>() {
            @Override
            @SuppressWarnings("unchecked")
            public L cast(Object o) {
                List list = listClass.cast(o);
                assert o == null || iterate(list, elementCheck);
                return (L)list;
            }

            @Override
            public String toString() {
                return "LISTCAST:" + listClass.getSimpleName() + "<" + elementCheck + ">";
            }
        };
    }

    private static <M> Cast<M> checkMap(
        final Class<? extends Map> mapClass,
        final Cast<?> keyCheck,
        final Cast<?> valueCheck) {

        Objects.requireNonNull(mapClass);
        Objects.requireNonNull(keyCheck);
        Objects.requireNonNull(valueCheck);

        return new Cast<M>() {
            @Override
            @SuppressWarnings("unchecked")
            public M cast(Object o) {
                Map map = mapClass.cast(o);
                assert o == null || iterate(map, keyCheck, valueCheck);
                return (M)map;
            }

            @Override
            public String toString() {
                return "MAPCAST:" + mapClass.getSimpleName() + "<" + keyCheck + ", " + valueCheck + ">";
            }
        };
    }

    private static Cast<?> buildChecker(Type type) {
        if (type instanceof Class) {
            return checkType(TypeTools.erase(type));
        }
        else if (type instanceof ParameterizedType) {
            Class<?> rawType = (Class<?>)((ParameterizedType)type).getRawType();
            Type[] typeArguments = ((ParameterizedType)type).getActualTypeArguments();
            if (Set.class.isAssignableFrom(rawType)) {
                // UNDONE: need to dig upwards to find actual type argument for Set interface
                return checkSet(
                    rawType.asSubclass(Set.class),
                    buildChecker(typeArguments[0]));
            }
            else if (List.class.isAssignableFrom(rawType)) {
                // UNDONE: need to dig upwards to find actual type argument for List interface
                return checkList(
                    rawType.asSubclass(List.class),
                    buildChecker(typeArguments[0]));
            }
            else if (Map.class.isAssignableFrom(rawType)) {
                // UNDONE: need to dig upwards to find actual type arguments for Map interface
                return checkMap(
                    rawType.asSubclass(Map.class),
                    buildChecker(typeArguments[0]),
                    buildChecker(typeArguments[1]));
            }
            else if (Collection.class.isAssignableFrom(rawType)) {
                return checkCollection(
                    rawType.asSubclass(Collection.class),
                    buildChecker(typeArguments[0]));
            }
            else {
                // UNDONE ??? what to do here?
                return checkType(TypeTools.erase(type));
            }
        }
        else if (type instanceof WildcardType) {
            // UNDONE: correct bounds handling
            return buildChecker(((WildcardType)type).getUpperBounds()[0]);
        }
        else {
            throw new IllegalArgumentException("don't know how to handle " + type);
        }
    }

    // UNDONE: rename "checker". should be "caster" or something like that.
    @SuppressWarnings("unchecked")
    public static <T> Cast<T> buildChecker(TypeToken<T> token) {
        return (Cast<T>)buildChecker(token.asType());
    }
}
