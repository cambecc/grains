package net.nullschool.reflect;

import java.lang.reflect.TypeVariable;

/**
 * 2013-03-24<p/>
 *
 * @author Cameron Beccario
 */
final class TestUtils {

    private static class Item<E> {

        private TypeVariable<?> theE() {
            return new JavaToken<E>(){}.asTypeVariable();
        }
    }

    static final TypeVariable<?> someTypeVariable = new Item().theE();

    private TestUtils() {
    }
}
