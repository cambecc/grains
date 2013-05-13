package com.acme;


import com.acme.model.*;
import net.nullschool.collect.basic.BasicConstList;


/**
 * 2013-02-14<p/>
 *
 * @author Cameron Beccario
 */
public class Driver {

    public static void main(String[] args) {
        PersonGrain person = PersonFactory.newBuilder()
            .setId(12345)
            .setName("Bob")
            .setEmail("bob@bob.com")
            .setPhones(
                BasicConstList.listOf(
                    PhoneNumberFactory.newBuilder()
                        .setNumber("1234-5678")
                        .setType(PhoneNumber.PhoneType.MOBILE)
                        .build()))
            .build();

        System.out.println(person);
    }
}
