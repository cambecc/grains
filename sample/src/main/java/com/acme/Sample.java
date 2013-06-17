package com.acme;


import com.acme.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import net.nullschool.grains.jackson.JacksonTools;
import com.acme.model.PhoneNumber.PhoneType;
import org.joda.time.LocalDate;

import java.io.IOException;
import java.util.UUID;

import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-02-14<p/>
 *
 * This sample shows how to generate grain implementations for a simple object model comprised of interfaces. The
 * interfaces are located in the {@code com.acme.model} package and makes use of immutable types available from
 * common open source libraries. The {@link CustomTypePolicies} class shows how to register these immutable types
 * so they can be used by the Maven <i>grains-plugin</i> during code generation.<p/>
 *
 * The POM file for this sample shows how to configure the project for code generation. To generate code, simply
 * invoke the maven 'compile' goal. After making changes to the object model, re-run the 'compile' goal for the
 * generator to pick up the changes and regenerate new code.<p/>
 *
 * See the {@link CustomTypePolicies} class for a description of how to configure the generator to use Guava's
 * immutable collections framework rather than the const collections framework natively available from Grains.<p/>
 *
 * This class' {@code main} method demonstrates how to create a sample {@link Person} grain, serialize it to JSON
 * using Jackson, and then deserialize it back to a grain.
 *
 * @author Cameron Beccario
 */
public class Sample {

    static PhoneNumberGrain samplePhone() {
        PhoneNumberBuilder builder = PhoneNumberFactory.newBuilder();
        builder.setNumber("+81 3-5555-1212");
        builder.setType(PhoneType.WORK);
        return builder.build();
    }

    static PersonGrain samplePerson() {
        PersonBuilder builder = PersonFactory.newBuilder();
        builder.setId(UUID.randomUUID());
        builder.setName("Foo");
        builder.setEmail("foo@foo.com");
        builder.setBirthday(new LocalDate(1969, 7, 20));

        // Uncomment the second line if using Guava's immutable collections (see javadoc above):
        builder.setPhones(listOf(samplePhone()));
        // builder.setPhones(ImmutableList.of(samplePhone()));

        return builder.build();
    }

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = JacksonTools.newGrainsObjectMapper()
            .registerModule(new JodaModule())    // add support for joda time objects
            .registerModule(new GuavaModule());  // add support for guava collections

        PersonGrain person = samplePerson();
        System.out.println("before: " + person);

        String json = mapper.writeValueAsString(person);
        System.out.println("json:   " + json);

        person = mapper.readValue(json, PersonGrain.class);
        System.out.println("after:  " + person);
    }
}
