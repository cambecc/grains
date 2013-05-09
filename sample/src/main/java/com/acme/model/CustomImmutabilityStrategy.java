package com.acme.model;

import net.nullschool.reflect.DefaultImmutabilityStrategy;
import org.joda.time.DateTime;


/**
 * 2013-05-09<p/>
 *
 * @author Cameron Beccario
 */
public class CustomImmutabilityStrategy extends DefaultImmutabilityStrategy {

    public static final CustomImmutabilityStrategy INSTANCE = new CustomImmutabilityStrategy();


    public CustomImmutabilityStrategy() {
        registerType(DateTime.class);
    }
}
