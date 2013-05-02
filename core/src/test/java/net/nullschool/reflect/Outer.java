package net.nullschool.reflect;

/**
 * 2013-03-24<p/>
 *
 * Some classes used for testing scenarios.
 *
 * @author Cameron Beccario
 */
@SuppressWarnings("UnusedDeclaration")
class Outer<T> {

    class Inner0 {
    }

    class Inner1<T> {
        class Inner2<V> {
        }
    }

    static class $Inner3<V> {
        class $Inner4<X> {
        }
    }
}
