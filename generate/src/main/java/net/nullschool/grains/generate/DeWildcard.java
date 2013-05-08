package net.nullschool.grains.generate;

import net.nullschool.reflect.*;

import java.lang.reflect.*;


/**
 * 2013-03-29<p/>
 *
 * @author Cameron Beccario
 */
class DeWildcard extends AbstractTypeOperator<Type> {

    @Override public Type invoke(Class<?> clazz) {
        return clazz;
    }

    @Override public Type invoke(ParameterizedType pt) {
        return new LateParameterizedType(
            pt.getRawType(),
            invoke(pt.getOwnerType()),  // remove wildcards in owner
            TypeTools.apply(this, pt.getActualTypeArguments()));  // remove wildcards in type arguments
    }

    @Override public Type invoke(GenericArrayType gat) {
        return gat;
    }

    @Override public Type invoke(WildcardType wt) {
        Type[] upperBounds = wt.getUpperBounds();
        return upperBounds.length == 0 || wt.getLowerBounds().length > 0 ?
            Object.class :
            invoke(upperBounds[0]); // UNDONE: research if this is correct
                                   // wouldn't it be the least common type of all bounds?
    }

    @Override public Type invoke(TypeVariable<?> tv) {
        return tv;
    }
}
