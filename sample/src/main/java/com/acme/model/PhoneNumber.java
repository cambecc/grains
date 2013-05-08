package com.acme.model;

import net.nullschool.grains.GrainSchema;


/**
 * 2013-02-10<p/>
 *
 * @author Cameron Beccario
 */
@GrainSchema
public interface PhoneNumber {

    enum PhoneType {
        MOBILE,  // 0
        HOME,    // 1
        WORK,    // 2
    }

    String getNumber();  // 1

    PhoneType getType(); // 2, default = home
}
