package net.nullschool.reflect;

import java.lang.reflect.*;


/**
 * 2013-04-03<p/>
 *
 * @author Cameron Beccario
 */
public abstract class AbstractTypeOperator<T> implements TypeOperator<T> {

    @Override public T invoke(Type type) {
        if (type instanceof Class) {
            return invoke((Class<?>)type);
        }
        else if (type instanceof ParameterizedType) {
            return invoke((ParameterizedType)type);
        }
        else if (type instanceof WildcardType) {
            return invoke((WildcardType)type);
        }
        else if (type instanceof GenericArrayType) {
            return invoke((GenericArrayType)type);
        }
        else if (type instanceof TypeVariable) {
            return invoke((TypeVariable<?>)type);
        }
        else if (type == null) {
            return null;
        }
        throw new IllegalArgumentException("Unsupported type: " + type);
    }
}
