package net.nullschool.grains;

import java.lang.reflect.Type;
import java.util.Set;


/**
 * 2013-05-07<p/>
 *
 * Descriptor for a Grain property.
 *
 * @author Cameron Beccario
 */
public interface GrainProperty {

    public enum Flag {
        /**
         * Signifies the name of this property's get accessor, if one exists, starts with "is" rather than "get".
         */
        IS_PROPERTY
    }

    /**
     * This property's name.
     */
    String getName();

    /**
     * This property's generic type.
     */
    Type getType();

    /**
     * The set of flags that describe this property, or an empty set if no flags are set.
     */
    Set<Flag> getFlags();

    // UNDONE: define equals? hashCode? would doing so require an "owner" property? would equal properties require
    //         consistency when calling setValue with potentially different owner types?
}
