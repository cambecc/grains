package net.nullschool.reflect;

/**
 * 2013-05-08<p/>
 *
 * @author Cameron Beccario
 */
public interface ConstPolicy {

    boolean test(Class<?> clazz);

    Class<?> translate(Class<?> clazz);

    <T> T requireImmutable(T t);

    <T> CastFunction<T> newCastFunction(TypeToken<T> token);
}
