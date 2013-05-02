package net.nullschool.reflect;

import net.nullschool.collect.ConstMap;

import java.lang.reflect.*;

import static net.nullschool.reflect.TypeTools.apply;
import static net.nullschool.reflect.TypeTools.buildArrayType;
import static net.nullschool.util.ObjectTools.coalesce;

/**
 * 2013-03-29<p/>
 *
 * @author Cameron Beccario
 */
final class Resolver extends AbstractTypeOperator<Type> {

    private final ConstMap<TypeVariable<?>, Type> variableBindings;

    Resolver(ConstMap<TypeVariable<?>, Type> variableBindings) {
        this.variableBindings = variableBindings;
    }

    @Override public Type invoke(Class<?> clazz) {
        return clazz;
    }

    @Override public Type invoke(ParameterizedType pt) {
        return new LateParameterizedType(
            pt.getRawType(),
            invoke(pt.getOwnerType()),
            apply(this, pt.getActualTypeArguments()));
    }

    @Override public Type invoke(GenericArrayType gat) {
        Type resolvedComponentType = invoke(gat.getGenericComponentType());
        return resolvedComponentType instanceof Class ?
            buildArrayType((Class<?>)resolvedComponentType) :
            new LateGenericArrayType(resolvedComponentType);
    }

    @Override public Type invoke(WildcardType wt) {
        return wt.getLowerBounds().length > 0 ?
            new LateWildcardType("? super", apply(this, wt.getLowerBounds())) :
            new LateWildcardType("? extends", apply(this, wt.getUpperBounds()));
    }

    @Override public Type invoke(TypeVariable<?> tv) {
        return coalesce(variableBindings.get(tv), tv);
    }
}
