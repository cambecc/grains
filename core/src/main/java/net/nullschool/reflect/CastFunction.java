package net.nullschool.reflect;

/**
 * 2013-02-25<p/>
 *
 * Casts an object to a statically known type T.
 *
 * @author Cameron Beccario
 */
public interface CastFunction<T> {

    T apply(Object o);
}
