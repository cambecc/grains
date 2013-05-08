package com.acme.model;

import net.nullschool.grains.GrainSchema;

import java.util.List;


/**
 * 2013-02-10<p/>
 *
 * @author Cameron Beccario
 */
@GrainSchema
public interface Person {

    int getId();  // 2

    String getName();  // 1

    String getEmail();  // 3

    List<? extends PhoneNumber> getPhones();  // 4
}
