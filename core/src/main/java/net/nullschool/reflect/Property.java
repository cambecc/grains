package net.nullschool.reflect;

import java.lang.reflect.Type;
import java.util.Set;


/**
 * 2013-04-08<p/>
 *
 * @author Cameron Beccario
 */
public interface Property {

    enum Flag {
        IS_PROPERTY,
    }

    String getName();

    Type getType();

    Set<Flag> getFlags();
}
