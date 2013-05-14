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

package net.nullschool.util;

import java.util.Comparator;


/**
 * 2013-02-06<p/>
 *
 * Utility methods for operating on objects.
 *
 * @author Cameron Beccario
 */
public enum ObjectTools {;

    /**
     * Returns the first non-null argument, or null if all arguments are null.
     */
    public static <T> T coalesce(T x, T y) {
        return x != null ? x : y;
    }

    /**
     * Compares two objects using the specified comparator or {@link Comparable natural ordering} if the comparator
     * is null. Note this differs subtly from {@link java.util.Objects#compare}. That method returns 0 when both
     * arguments are null whereas this method throws {@link NullPointerException}.
     *
     * @param left the left-hand value.
     * @param right the right-hand value.
     * @param comparator the comparison operator, or null if natural ordering is to be used.
     * @return a negative, 0, or positive value if {@code left} is less than, equal to, or greater than {@code right}.
     * @throws NullPointerException if either argument is null and the comparator is either null or does not permit
     *                              null arguments.
     * @throws ClassCastException if the arguments' types are not compatible for comparison.
     */
    public static <T> int compare(T left, T right, Comparator<? super T> comparator) {
        if (comparator == null) {
            // Cast to Comparable<T> is safe because compareTo method will do type checking.
            @SuppressWarnings("unchecked") Comparable<T> leftComparable = (Comparable<T>)left;
            return leftComparable.compareTo(right);
        }
        return comparator.compare(left, right);
    }
}
