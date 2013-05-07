package net.nullschool.grains;

import java.util.Map;


/**
 * 2013-03-20<p/>
 *
 * @author Cameron Beccario
 */
public interface GrainFactory {

    Grain getDefault();

    GrainBuilder newBuilder();

    Map<String, GrainProperty> getBasisProperties();
}
