package net.nullschool.transform;

import net.nullschool.reflect.*;

import java.lang.reflect.*;


/**
 * 2013-05-13<p/>
 *
 * @author Cameron Beccario
 */
class RecursiveConstruct extends AbstractTypeOperator<Transform<?>> {

    private static final Transform[] EMPTY = new Transform[0];

    private Transform[] apply(Type[] types) {
        Transform[] result = types.length > 0 ? new Transform[types.length] : EMPTY;
        for (int i = 0; i < types.length; i++) {
            result[i] = apply(types[i]);
        }
        return result;
    }

    private final TransformFactory factory;

    public RecursiveConstruct(TransformFactory factory) {
        this.factory = factory;
    }

    @Override public Transform<?> apply(Class<?> clazz) {
        return factory.newBuilder(clazz).build();
    }

    @Override public Transform<?> apply(ParameterizedType pt) {
        Class<?> rawType = TypeTools.erase(pt.getRawType());
        TransformFactory.Builder<?> builder = factory.newBuilder(rawType);

        // UNDONE: translation of type arguments to match parameters of target type: builder.getType().
        //         for example, MultiSet<Integer> extends Set<List<Integer>> should set cast function
        //         for List<Integer> if target type of the builder is Set.class rather than MultiSet.class.
        Transform[] argCasts = apply(pt.getActualTypeArguments());
        builder.setArgumentCasts(argCasts);

        return builder.build();
    }

    @Override public Transform<?> apply(GenericArrayType gat) {
        return factory
            .newBuilder(Object[].class)
            .setArgumentCasts(new Transform[] {apply(gat.getGenericComponentType())})
            .build();
    }

    @Override public Transform<?> apply(WildcardType wt) {
        return wt.getLowerBounds().length > 0 ? apply(Object.class) : apply(wt.getUpperBounds()[0]);
    }

    @Override public Transform<?> apply(TypeVariable<?> tv) {
        throw new IllegalArgumentException();
    }

    public <T> Transform<T> apply(TypeToken<T> token) {
        @SuppressWarnings("unchecked") Transform<T> result = (Transform<T>)apply(token.asType());
        return result;
    }
}
