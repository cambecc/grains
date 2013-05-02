package net.nullschool.reflect;

import java.lang.reflect.*;

/**
 * 2013-03-24<p/>
 *
 * Utility class used for testing. Allows access to type parameter T as a Type instance as produced by Java
 * reflection rather than the late type implementations LateGenericArrayType, LateParameterizedType, LateTypeVariable,
 * or LateWildcardType as would be produced by {@link LateToken}. Use this class by inheriting it with an anonymous
 * class, providing for T the desired type.
 *
 * @author Cameron Beccario
 */
@SuppressWarnings("UnusedDeclaration")
abstract class JavaToken<T> {

    Type asType() {
        return ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    ParameterizedType asParameterizedType() {
        return (ParameterizedType)asType();
    }

    WildcardType asWildcardType() {
        return (WildcardType)asParameterizedType().getActualTypeArguments()[0];
    }

    GenericArrayType asGenericArrayType() {
        return (GenericArrayType)asType();
    }

    TypeVariable<?> asTypeVariable() {
        return (TypeVariable<?>)asType();
    }
}
