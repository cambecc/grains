package net.nullschool.reflect;

import java.lang.reflect.*;
import java.util.Objects;


/**
 * 2013-03-24<p/>
 *
 * An immutable {@link GenericArrayType} that represents an array having a component type of
 * {@link ParameterizedType}, {@link TypeVariable}, or {@link GenericArrayType}. For example:
 * <pre>
 *     List&lt;String&gt;[]
 *     E[]
 *     E[][]
 * </pre>
 *
 * @author Cameron Beccario
 */
public final class LateGenericArrayType implements GenericArrayType {

    private final Type genericComponentType;

    /**
     * Constructs a generic array type 'T[]' given the specified component type 'T'. The component type must
     * itself be generic. For example, a generic array of {@code String.class} is not generic but simply
     * {@code String[].class}.<p/>
     *
     * Note that multi-dimensional arrays can be constructed by passing a generic array type as the component
     * type for another generic array type.
     *
     * @param genericComponentType the component type of the array.
     * @throws NullPointerException if the component type is null.
     * @throws IllegalArgumentException if the component type is either Class or a wildcard.
     */
    public LateGenericArrayType(Type genericComponentType) {
        if (genericComponentType instanceof Class || genericComponentType instanceof WildcardType) {
            throw new IllegalArgumentException("genericComponentType cannot be a Class or WildcardType");
        }
        this.genericComponentType = Objects.requireNonNull(genericComponentType, "null genericComponentType");
    }

    /**
     * Builds a LateGenericArrayType representation of the specified GenericArrayType.
     *
     * @param gat the generic array type to copy.
     * @return a LateGenericArrayType copy of the provided type.
     * @throws NullPointerException if the argument is null or the argument's component type is null.
     * @throws IllegalArgumentException if the argument's component type is either Class or a wildcard.
     */
    public static LateGenericArrayType copyOf(GenericArrayType gat) {
        return new LateGenericArrayType(gat.getGenericComponentType());
    }

    /**
     * Returns the component type of this generic array.
     *
     * @return a {@code Type} object representing the component type of this array
     */
    @Override public Type getGenericComponentType() {
        return genericComponentType;
    }

    private boolean equals(GenericArrayType that) {
        return this.genericComponentType.equals(that.getGenericComponentType());
    }

    /**
     * Returns true if the specific object is a GenericArrayType and the component type equals this array's
     * component type.
     */
    @Override public boolean equals(Object that) {
        return this == that || that instanceof GenericArrayType && this.equals((GenericArrayType)that);
    }

    /**
     * The hash code for a generic array type is defined to be the hash code of its component type, as per
     * Oracle's implementation.
     */
    @Override public int hashCode() {
        return genericComponentType.hashCode();
    }

    /**
     * Returns a string representation of this generic array type having the format E + "[]", where E is the
     * component type.
     */
    @Override public String toString() {
        return TypeTools.print(this);
    }
}
