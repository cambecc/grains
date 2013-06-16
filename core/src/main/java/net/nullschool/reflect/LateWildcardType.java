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

package net.nullschool.reflect;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;

import static net.nullschool.util.ArrayTools.EMPTY_TYPE_ARRAY;
import static net.nullschool.reflect.TypeTools.*;


/**
 * 2013-03-24<p/>
 *
 * An immutable {@link WildcardType} that represents a wildcard with bounds. For example:
 * <pre>
 *     ?
 *     ? extends Number
 *     ? super E
 * </pre>
 *
 * @author Cameron Beccario
 */
public final class LateWildcardType implements WildcardType {

    private static final Type[] OBJECT_TYPE_ARRAY = new Type[] {Object.class};


    private final Type[] upperBounds;  // ? extends T & U
    private final Type[] lowerBounds;  // ? super T & U

    private LateWildcardType(Type[] upperBounds, Type[] lowerBounds) {
        this.upperBounds = upperBounds;
        this.lowerBounds = lowerBounds;
    }

    /**
     * Constructs a wildcard type having the given bounds. The keyword specifies what kind of bounds the wildcard
     * type should have. Valid values are the exact strings {@code "?"}, {@code "? extends"}, and {@code "? super"}.
     * The default upper bound (used when no upper bounds are explicitly specified) is Object.class.
     *
     * Examples:
     * <pre>
     * new LateWildcardType("?")
     *     - a wildcard with an implicit upper bound of Object
     *
     * new LateWildcardType("? extends", Number.class)
     *     - a wildcard with an upper bound of Number
     *
     * new LateWildcardType("? super", Comparable.class)
     *     - a wildcard with a lower bound of Comparable
     * </pre>
     *
     * @param keyword the kind of wildcard to construct.
     * @param bounds the wildcard bounds.
     * @throws NullPointerException if bounds is null or keyword is null.
     * @throws IllegalArgumentException if keyword is not one of {@code "?"}, {@code "? extends"}, and
     *                                  {@code "? super"}, or if bounds are provided when using keyword {@code "?"},
     *                                  or if any bound is not suitable for use as a wildcard bound.
     */
    public LateWildcardType(String keyword, Type... bounds) {
        for (Type bound : bounds) {
            if (bound == null || bound instanceof WildcardType) {
                throw new IllegalArgumentException("not suitable as wildcard bound: " + bound);
            }
        }
        switch (keyword) {
            case "?":
                if (bounds.length != 0) {
                    throw new IllegalArgumentException("bounds not expected");
                }
                upperBounds = OBJECT_TYPE_ARRAY;
                lowerBounds = EMPTY_TYPE_ARRAY;
                break;
            case "? extends":
                if (bounds.length == 0) {
                    throw new IllegalArgumentException("upper bounds expected");
                }
                upperBounds = bounds.clone();
                lowerBounds = EMPTY_TYPE_ARRAY;
                break;
            case "? super":
                if (bounds.length == 0) {
                    throw new IllegalArgumentException("lower bounds expected");
                }
                upperBounds = OBJECT_TYPE_ARRAY;
                lowerBounds = bounds.clone();
                break;
            default:
                throw new IllegalArgumentException("unknown keyword: " + keyword);
        }
    }

    /**
     * Builds a LateWildcardType representation of the specified WildcardType.
     *
     * @param wt the wildcard type to copy.
     * @return a LateWildcardType copy of the provided type.
     * @throws NullPointerException if the argument is null or its bounds are null.
     * @throws IllegalArgumentException if any bound is not suitable for use as a wildcard bound.
     */
    public static LateWildcardType copyOf(WildcardType wt) {
        return new LateWildcardType(wt.getUpperBounds().clone(), wt.getLowerBounds().clone());
    }

    /**
     * Returns this wildcard's upper bound(s). If no upper bound is explicitly defined, the upper bound
     * is {@code Object}.
     *
     * @return this wildcard's upper bound(s).
     */
    @Override public Type[] getUpperBounds() {
        return upperBounds.clone();
    }

    /**
     * Returns this wildcard type's lower bound(s). If no lower bound is explicitly defined, this method returns
     * an empty array.
     *
     * @return this wildcard's lower bound(s).
     */
    @Override public Type[] getLowerBounds() {
        return lowerBounds.clone();
    }

    private boolean equals(WildcardType that) {
        return
            Arrays.equals(this.upperBounds, that.getUpperBounds()) &&
            Arrays.equals(this.lowerBounds, that.getLowerBounds());
    }

    /**
     * Returns true if the specific object is a WildcardType and its bounds equal this wildcard's bounds.
     */
    @Override public boolean equals(Object that) {
        return this == that || that instanceof WildcardType && equals((WildcardType)that);
    }

    /**
     * The hash code for a wildcard type is defined to be the XOR'd hash codes of its bound arrays, as per
     * Oracle's implementation.
     */
    @Override public int hashCode() {
        return Arrays.hashCode(upperBounds) ^ Arrays.hashCode(lowerBounds);
    }

    /**
     * Returns a string representation of this wildcard type having the format "? extends" + upper bounds,
     * "? super" + lower bounds, or "?" as appropriate.
     */
    @Override public String toString() {
        return print(this);
    }
}
