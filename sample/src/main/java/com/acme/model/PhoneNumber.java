package com.acme.model;

import net.nullschool.grains.GrainSchema;


/**
 * 2013-02-10<p/>
 *
 * An example phone number object containing an enum.
 *
 * @author Cameron Beccario
 */
@GrainSchema
public interface PhoneNumber {

    enum PhoneType {WORK, HOME, MOBILE}

    String getNumber();

    PhoneType getType();
}
