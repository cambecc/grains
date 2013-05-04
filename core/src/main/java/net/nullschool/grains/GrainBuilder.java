package net.nullschool.grains;

import net.nullschool.collect.IterableMap;


/**
 * 2013-03-24<p/>
 *
 * @author Cameron Beccario
 */
public interface GrainBuilder extends IterableMap<String, Object> {

    Grain build();
}
