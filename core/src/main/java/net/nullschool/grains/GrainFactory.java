package net.nullschool.grains;

import net.nullschool.collect.ConstMap;
import net.nullschool.reflect.Property;


/**
 * 2013-03-20<p/>
 *
 * @author Cameron Beccario
 */
public interface GrainFactory {

    ConstMap<String, Property> getProperties();  // Should be GrainProperty?? Only fits. Perhaps GrainProperty
                                                 // should be the interface and in this package, and then have
                                                 // a simple implementation.

    Grain getBasis();

    GrainBuilder newBuilder();
}
