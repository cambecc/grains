package net.nullschool.grains.generate;

import net.nullschool.reflect.*;

import java.lang.reflect.*;


/**
 * 2013-03-29<p/>
 *
 * @author Cameron Beccario
 */
class DeWildcard extends AbstractTypeOperator<Type> {

    @Override public Type apply(Class<?> clazz) {
        return clazz;
    }

    @Override public Type apply(ParameterizedType pt) {
        return new LateParameterizedType(
            pt.getRawType(),
            apply(pt.getOwnerType()),  // remove wildcards in owner
            TypeTools.apply(this, pt.getActualTypeArguments()));  // remove wildcards in type arguments
    }

    @Override public Type apply(GenericArrayType gat) {
        return gat;
    }

    @Override public Type apply(WildcardType wt) {
        Type[] upperBounds = wt.getUpperBounds();
        return upperBounds.length == 0 || wt.getLowerBounds().length > 0 ?
            Object.class :
            apply(upperBounds[0]); // UNDONE: research if this is correct
                                   // wouldn't it be the least common type of all bounds?
    }

    @Override public Type apply(TypeVariable<?> tv) {
        return tv;
    }
}
