package net.nullschool.transform;

/**
 * 2013-02-25<p/>
 *
 * A function that transforms an object to an instance of statically known type T.
 *
 * @author Cameron Beccario
 */
public interface Transform<T> {

    T apply(Object o);
}
