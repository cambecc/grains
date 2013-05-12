package net.nullschool.grains.generate;

import net.nullschool.grains.GrainProperty;

import java.util.Comparator;


/**
 * 2013-05-09<p/>
 *
 * A comparator for sorting grain properties alphabetically by name, case-insensitive. Special precedence is given to
 * properties named "id" which, for convenience, should appear first.
 *
 * @author Cameron Beccario
 */
enum GrainPropertyComparator implements Comparator<GrainProperty> {
    INSTANCE;

    private boolean isId(GrainProperty prop) {
        return NamingPolicy.ID_PROPERTY_NAME.equalsIgnoreCase(prop.getName());
    }

    private int compareNames(String left, String right) {
        int cmp = left.compareToIgnoreCase(right);      // sort alphabetically, case-insensitive
        return cmp != 0 ? cmp : left.compareTo(right);  // when only case differs, sort by case
    }

    @Override public int compare(GrainProperty left, GrainProperty right) {
        if (isId(left)) {
            if (!isId(right)) {
                return -1;
            }
        }
        else if (isId(right)) {
            return 1;
        }
        return compareNames(left.getName(), right.getName());
    }
}
