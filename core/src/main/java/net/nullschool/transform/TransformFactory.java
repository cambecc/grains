package net.nullschool.transform;


/**
 * 2013-05-13<p/>
 *
 * @author Cameron Beccario
 */
interface TransformFactory {

    interface Builder<T> {

        Class<? super T> getType();

        Builder<T> setArgumentCasts(Transform<?>[] argCasts);

        Transform<T> build();
    }

    <T> Builder<T> newBuilder(Class<T> clazz);
}
