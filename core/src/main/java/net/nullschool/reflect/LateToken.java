package net.nullschool.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
* 2013-02-19<p/>
*
* @author Cameron Beccario
*/
@SuppressWarnings("UnusedDeclaration")
public abstract class LateToken<T> {

    private final Type type;

    protected LateToken() {
        Class<?> clazz = this.getClass();
        Type parent = clazz.getGenericSuperclass();
        if (parent instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType)parent;
            if (pt.getRawType() == LateToken.class) {
                // UNDONE: Hrm. What's the point of this Token if it doesn't return LateType instances?
                type = pt.getActualTypeArguments()[0];
                // type = TypeTools.asLateType(pt.getActualTypeArguments()[0]);
                return;
            }
        }
        throw new IllegalArgumentException();
    }

    public Type type() {
        return type;
    }
}
