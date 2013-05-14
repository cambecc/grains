/*
 * Copyright 2013 Cameron Beccario
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
