package net.nullschool.grains.generate;

import net.nullschool.reflect.*;

import java.lang.reflect.*;


/**
 * 2013-03-29<p/>
 *
 * A type operator that removes wildcards as it deeply traverses the structure of a type. Any "?" or "? super"
 * wildcards are replaced with {@code Object}, and "? extends" wildcards are replaced with the leftmost bound. For
 * example, {@code List&lt;? extends Map&lt;? super Comparator, ? extends Number&gt;&gt;} after wildcards removal
 * is {@code List&lt;Map&lt;Object, Number&gt;&gt;}.
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
        return new LateGenericArrayType(apply(gat.getGenericComponentType()));
    }

    @Override public Type apply(WildcardType wt) {
        return wt.getLowerBounds().length > 0 ? Object.class : apply(wt.getUpperBounds()[0]);
    }

    @Override public Type apply(TypeVariable<?> tv) {
        return tv;
    }
}
