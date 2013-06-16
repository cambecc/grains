package com.acme.model;

import net.nullschool.grains.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;


/**
 * 2013-05-09<p/>
 *
 * @author Cameron Beccario
 */
public class CustomTypePolicy {

    public static final TypePolicy INSTANCE = ConfigurableTypePolicy.STANDARD
        .withImmutableTypes(DateTime.class, DateTimeZone.class)
//        .withImmutableMapping(Set.class, ImmutableSet.class)
//        .withImmutableMapping(Map.class, ImmutableMap.class)
//        .withImmutableMapping(List.class, ImmutableList.class)
//        .withImmutableMapping(SortedSet.class, ImmutableSortedSet.class)
//        .withImmutableMapping(SortedMap.class, ImmutableSortedMap.class)
//        .withImmutableMapping(Collection.class, ImmutableCollection.class)
//        .withImmutableMapping(Multimap.class, ImmutableMultimap.class)
//        .withImmutableMapping(SetMultimap.class, ImmutableSetMultimap.class)
//        .withImmutableMapping(ListMultimap.class, ImmutableListMultimap.class)
        ;
}
