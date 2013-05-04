package net.nullschool.reflect;

import java.lang.reflect.*;


/**
 * 2013-04-03<p/>
 *
 * A partial implementation of TypeOperator that dispatches invocation of {@link #invoke(Type)} to the appropriate
 * narrower overload.
 *
 * @author Cameron Beccario
 */
public abstract class AbstractTypeOperator<T> implements TypeOperator<T> {

    /**
     * Invokes this operator on the specified type.<p/>
     *
     * This implementation inspects the runtime type of the specified type and invokes the appropriate narrower
     * overload. If type is null, the null is returned.
     *
     * @param type the type.
     * @return the result.
     * @throws IllegalArgumentException if type refers to an unknown and unsupported implementation of {@link Type}.
     */
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
        throw new IllegalArgumentException("Unsupported Type implementation: " + type.getClass());
    }
}
