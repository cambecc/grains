package net.nullschool.reflect;

import java.lang.reflect.*;

/**
 * 2013-04-03<p/>
 *
 * @author Cameron Beccario
 */
final class Eraser extends AbstractTypeOperator<Class<?>> {

    static final Eraser INSTANCE = new Eraser();


    @Override public Class<?> invoke(Class<?> clazz) {
        return clazz;
    }

    @Override public Class<?> invoke(ParameterizedType pt) {
        return invoke(pt.getRawType());
    }

    @Override public Class<?> invoke(GenericArrayType gat) {
        return TypeTools.buildArrayType(invoke(gat.getGenericComponentType()));
    }

    @Override public Class<?> invoke(WildcardType wt) {
        if (wt.getLowerBounds().length != 0) {
            return Object.class;  // UNDONE: research if this is correct
        }
        else {
            return invoke(wt.getUpperBounds()[0]);  // UNDONE: research if this is correct
                                                    // wouldn't it be the least common type of all bounds?
        }
    }

    @Override public Class<?> invoke(TypeVariable<?> tv) {
        return invoke(tv.getBounds()[0]);  // UNDONE: research the actual erasure for a multi-bounded type variable
                                           // wouldn't it be the least common type of all bounds?
    }
}
