package com.acme.model;

import net.nullschool.grains.DefaultTypePolicy;
import org.joda.time.DateTime;


/**
 * 2013-05-09<p/>
 *
 * @author Cameron Beccario
 */
public class CustomTypePolicy extends DefaultTypePolicy {

    public static final CustomTypePolicy INSTANCE = new CustomTypePolicy();


    public CustomTypePolicy() {
        registerType(DateTime.class);

//        registerTranslation(Collection.class, ImmutableCollection.class);
//        registerTranslation(List.class, ImmutableList.class);
//        registerTranslation(Set.class, ImmutableSet.class);
//        registerTranslation(SortedSet.class, ImmutableSortedSet.class);
//        registerTranslation(Map.class, ImmutableMap.class);
//        registerTranslation(SortedMap.class, ImmutableSortedMap.class);
//        registerTranslation(Multimap.class, ImmutableMultimap.class);
//        registerTranslation(ListMultimap.class, ImmutableListMultimap.class);
//        registerTranslation(SetMultimap.class, ImmutableSetMultimap.class);
    }
}
