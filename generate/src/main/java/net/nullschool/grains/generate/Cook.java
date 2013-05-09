package net.nullschool.grains.generate;

import net.nullschool.reflect.*;
import net.nullschool.util.ArrayTools;

import java.lang.reflect.*;

import static net.nullschool.reflect.TypeTools.*;


/**
 * 2013-03-29<p/>
 *
 * An operator that changes all raw types into equivalent generic instances. For example, the raw type
 * {@code List} should be considered {@code List&lt;Object&gt;} for certain purposes, such as verifying
 * the type represents an immutable type. Other examples:
 * <pre>
 * Map      ->  Map&lt;Object, Object&gt;
 * EnumSet  ->  EnumSet&lt;Enum&gt;
 * </pre>
 *
 * @author Cameron Beccario
 */
class Cook extends AbstractTypeOperator<Type> {

    /**
     * Check the provided array of type variables for self-referential cycles, such as
     * Enum&lt;E extends Enum&lt;E&gt;&gt;. The only way this can occur is for a parameterized type to have a
     * type variable argument whose bounds contain the very same parameterized type.
     *
     * @param tvs the type variables to check for cycles
     * @return true if a cycle is detected.
     */
    private static boolean hasCycle(TypeVariable<?>[] tvs) {
        // Given an array of type variables (1), each potentially having several bounds (2), then for any bound
        // that is parameterized type P (3), inspect each argument of P (4) that is a type variable T (5), and
        // check if the bounds of T contains P (6). If so, we have a cycle.

        for (TypeVariable<?> tv : tvs) {  // (1)
            for (Type bound : tv.getBounds()) {  // (2)
                if (bound instanceof ParameterizedType) {  // (3)
                    ParameterizedType pt = (ParameterizedType)bound;
                    for (Type arg : pt.getActualTypeArguments()) {  // (4)
                        if (arg instanceof TypeVariable) {  // (5)
                            TypeVariable<?> tv2 = (TypeVariable<?>)arg;
                            if (ArrayTools.indexOf(pt, tv2.getBounds()) >= 0) {  // (6)
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override public Type invoke(Class<?> clazz) {
        if (clazz.isArray()) {
            // Cook arrays that might have generic component types, like List[].
            Type componentType = invoke(clazz.getComponentType());
            if (!(componentType instanceof Class)) {
                return new LateGenericArrayType(componentType);
            }
        }
        else {
            // Cook generic classes (i.e., any class that has type parameters). However, be careful because
            // attempting to instantiate Enum would create Enum<Enum<Enum<...>>>, which is rather verbose.
            TypeVariable<?>[] typeParameters = clazz.getTypeParameters();
            if (typeParameters.length > 0 && !hasCycle(typeParameters)) {
                return new LateParameterizedType(
                    clazz,
                    clazz.isMemberClass() ? invoke(clazz.getEnclosingClass()) : clazz.getEnclosingClass(),
                    apply(this, typeParameters));
            }
        }
        return clazz;
    }

    @Override public Type invoke(ParameterizedType pt) {
        Class<?> rawType = erase(pt.getRawType());
        return new LateParameterizedType(
            rawType,
            rawType.isMemberClass() ? invoke(pt.getOwnerType()) : pt.getOwnerType(),
            apply(this, pt.getActualTypeArguments()));
    }

    @Override public Type invoke(GenericArrayType gat) {
        return new LateGenericArrayType(invoke(gat.getGenericComponentType()));
    }

    @Override public Type invoke(WildcardType wt) {
        return wt.getLowerBounds().length > 0 ?
            new LateWildcardType("? super", apply(this, wt.getLowerBounds())) :
            new LateWildcardType("? extends", apply(this, wt.getUpperBounds()));
    }

    @Override public Type invoke(TypeVariable<?> tv) {
        return invoke(erase(tv));
    }
}
