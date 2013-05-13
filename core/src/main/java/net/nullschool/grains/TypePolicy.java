package net.nullschool.grains;

import net.nullschool.transform.Transform;
import net.nullschool.reflect.TypeToken;


/**
 * 2013-05-08<p/>
 *
 * @author Cameron Beccario
 */
public interface TypePolicy {

    boolean isConstType(Class<?> clazz);  // test for immutability

    Class<?> asConstType(Class<?> clazz);  // translate to immutable type

    <T> T requireConst(T t);  // throw if t not immutable

    <T> Transform<T> newTransform(TypeToken<T> token);  // create cast function for type token (not related to
                                                        // immutability)
}
