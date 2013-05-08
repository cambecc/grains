package com.acme.model;

import net.nullschool.grains.GrainSchema;

import java.util.List;


/**
 * 2013-02-10<p/>
 *
 * @author Cameron Beccario
 */
@GrainSchema
public interface AddressBook {

    List<? extends Person> getPeople();  // 1
}
