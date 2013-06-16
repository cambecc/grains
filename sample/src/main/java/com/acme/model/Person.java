package com.acme.model;

import net.nullschool.grains.GrainSchema;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.UUID;


/**
 * 2013-02-10<p/>
 *
 * A example Person object having a UUID id, Joda LocalDate birthday, and a list of phone numbers.
 *
 * @author Cameron Beccario
 */
@GrainSchema
public interface Person extends Identifiable<UUID> {

    String getName();

    String getEmail();

    LocalDate getBirthday();

    List<? extends PhoneNumber> getPhones();
}
