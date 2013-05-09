package net.nullschool.grains.generate;

import net.nullschool.reflect.*;

import java.lang.reflect.*;


/**
 * 2013-05-09<p/>
 *
 * @author Cameron Beccario
 */
final class Immutify extends AbstractTypeOperator<Type> {

    private final TypeTable typeTable;

    private Immutify(TypeTable typeTable) {
        this.typeTable = typeTable;
    }

    @Override public Class<?> invoke(Class<?> clazz) {
        return typeTable.immutify(clazz);
    }

    @Override public Type invoke(ParameterizedType pt) {
        Class<?> immutableRawType = invoke(TypeTools.erase(pt.getRawType()));
        return new LateParameterizedType(
            immutableRawType,
            immutableRawType.isMemberClass() ? apply(pt.getOwnerType(), typeTable) : pt.getOwnerType(),
            TypeTools.apply(this, pt.getActualTypeArguments()));
    }

    @Override public Type invoke(GenericArrayType gat) {
        return new LateGenericArrayType(invoke(gat.getGenericComponentType()));
    }

    @Override public Type invoke(WildcardType wt) {
        return wt.getLowerBounds().length > 0 ?
            new LateWildcardType("? super", TypeTools.apply(this, wt.getLowerBounds())) :
            new LateWildcardType("? extends", TypeTools.apply(this, wt.getUpperBounds()));
    }

    @Override public Type invoke(TypeVariable<?> tv) {
        return invoke(TypeTools.erase(tv));  // not sure if this makes sense
    }

    static Type apply(Type type, TypeTable table) {
        Type instantiated = new Cook().invoke(new DeWildcard().invoke(type));
        try {
            return new Immutify(table).invoke(instantiated);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                String.format("Failed to immutify: %s (interpreted as: %s)", type, instantiated), e);
        }
    }
}
