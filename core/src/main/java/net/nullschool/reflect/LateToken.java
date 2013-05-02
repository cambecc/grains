package net.nullschool.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static net.nullschool.reflect.TypeTools.*;

/**
 * 2013-02-19<p/>
 *
 *
 *
 * @author Cameron Beccario
 */
public abstract class LateToken<T> {

    private final Type type;

    protected LateToken() {
        Class<?> clazz = this.getClass();
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            throw new IllegalArgumentException(
                String.format("%s must define a type argument to its super class.", clazz));
        }
        ParameterizedType pt = (ParameterizedType)genericSuperclass;
        if (pt.getRawType() != LateToken.class) {
            throw new IllegalArgumentException(
                String.format("%s does not directly extend %s", clazz, LateToken.class));
        }
        type = copyOf(pt.getActualTypeArguments()[0]);  // Extract the T.
    }

    /**
     * Returns the type this token represents.
     */
    public final Type type() {
        return type;
    }

    /**
     * Returns the value {@code null} typed as the type this token represents.
     */
    public final T Null() {
        return null;
    }

    /**
     * Two type tokens are equal if the types they represent are equal.
     */
    @Override public final boolean equals(Object that) {
        return this == that || that instanceof LateToken && this.type.equals(((LateToken)that).type());
    }

    /**
     * The hash code of a type token is the hash code of the type it represents.
     */
    @Override public final int hashCode() {
        return type.hashCode();
    }

    /**
     *
     */
    @Override public final String toString() {
        return "LateToken<" + type + '>';
    }
}
