package com.acme.model;

import com.google.common.collect.*;
import net.nullschool.grains.*;
import org.joda.time.*;

import java.util.*;


/**
 * 2013-05-09<p/>
 *
 * This class holds examples of TypePolicy objects customized with immutable types available from common open source
 * libraries, allowing the Maven grains-plugin to use these types during code generation. For illustrative purposes,
 * there are two customized type policies defined:
 * <ol>
 *     <li>STANDARD_JODA: adds immutable JodaTime objects</li>
 *     <li>STANDARD_JODA_GUAVA: adds immutable JodaTime objects and defines mappings for Guava immutable
 *                              collections.</li>
 * </ol>
 *
 * The custom type policy to use during code generation is defined by the grains-plugin POM configuration. For example:
 * <pre>
 * &lt;configuration&gt;
 *    &lt;typePolicy&gt;com.acme.model.CustomTypePolicies.STANDARD_JODA&lt;/typePolicy&gt;
 * &lt;/configuration&gt;
 * </pre>
 *
 * Changing the type policy configuration from STANDARD_JODA to STANDARD_JODA_GUAVA will cause the generated code to
 * use Guava's immutable collections rather than the const collections natively available from the Grains framework.
 * Make sure to update the {@link com.acme.Driver} class as well to use these different collection types.
 *
 * @author Cameron Beccario
 */
public class CustomTypePolicies {

    /**
     * A standard policy that supports JodaTime.
     */
    public static final ConfigurableTypePolicy STANDARD_JODA = ConfigurableTypePolicy.STANDARD
        .withImmutableTypes(LocalDate.class, DateTime.class);

    /**
     * A standard policy that supports JodaTime and Guava immutable collections.
     */
    public static final ConfigurableTypePolicy STANDARD_JODA_GUAVA = ConfigurableTypePolicy.STANDARD
        .withImmutableTypes(LocalDate.class, DateTime.class)
        .withImmutableMapping(Set.class, ImmutableSet.class)
        .withImmutableMapping(Map.class, ImmutableMap.class)
        .withImmutableMapping(List.class, ImmutableList.class)
        .withImmutableMapping(SortedSet.class, ImmutableSortedSet.class)
        .withImmutableMapping(SortedMap.class, ImmutableSortedMap.class)
        .withImmutableMapping(Collection.class, ImmutableCollection.class)
        .withImmutableMapping(Multimap.class, ImmutableMultimap.class)
        .withImmutableMapping(SetMultimap.class, ImmutableSetMultimap.class)
        .withImmutableMapping(ListMultimap.class, ImmutableListMultimap.class);
}
