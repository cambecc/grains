package net.nullschool.grains.generate.model;


import java.util.List;


/**
 * 2013-03-05<p/>
 *
 * @author Cameron Beccario
 */
public interface Top<T> {

    interface Left<T> extends Top<T> {

        String getLeft();

        List<T> getLeftIds();
    }

    interface Right<T> extends Top<T> {

        String getRight();

        List<T> getRightIds();
    }

    String getTop();

    T getId();
}
