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

package net.nullschool.grains;

import net.nullschool.collect.IterableMap;

import java.util.*;


/**
 * 2013-03-24<p/>
 *
 * A GrainBuilder is a mutable {@link IterableMap iterable map} of String to Object that contains permanent keys,
 * i.e., keys that cannot be disassociated from the map. GrainBuilders permit {@code null} values but disallow
 * {@code null} keys. A GrainBuilder is the mutable analog to a {@link Grain} and makes no guarantees for thread
 * safety.<p/>
 *
 * The set of permanent keys, called the "basis", differs depending on the builder implementation, and each basis key
 * may restrict the type of values it can be associated with. This is roughly analogous to how instances of a class
 * share the same set of fields, and each field stores values only of a particular type. Attempts to disassociate a
 * basis key or associate it with {@code null} results in the key becoming associated with its <i>default value</i>.
 * Generally, a key's default value is {@code null} if its type restriction is a reference type, otherwise {@code 0}
 * for primitive types. However, some builder implementations may (optionally) specify a non-null/non-zero default
 * value for a key.<p/>
 *
 * A builder can associate and disassociate non-basis keys, just like a normal map. To differentiate them from the
 * basis, these keys are called "extensions". Extension keys may also (optionally) restrict their associated
 * values.<p/>
 *
 * The {@link #keySet}, {@link #values}, {@link #entrySet}, and {@link #iterator} methods present the <i>union</i>
 * of the basis and extensions. There is no guaranteed iteration order.<p/>
 *
 * Builders are intended to simplify the construction of immutable grains. Instances of a builder can be created
 * by calling the {@link Grain#newBuilder} or {@link GrainFactory#getNewBuilder} methods. The {@link #build} method
 * constructs a new grain from the builder's current set of entries.<p/>
 *
 * @author Cameron Beccario
 */
public interface GrainBuilder extends IterableMap<String, Object> {

    /**
     * {@inheritDoc}<p/>
     *
     * This method interprets a {@code null} value to represent the <i>default value</i> of the key. Generally, a
     * key's default value is {@code null} if its type restriction is a reference type, otherwise {@code 0} for
     * primitive types. However, some keys may have a non-null/non-zero default value. For example, if a key's
     * default value is <i>42</i>, then invoking this method with a {@code null} value will associate that key with
     * <i>42</i>.
     *
     * @param key the key to associate.
     * @param value the value to be associated with the key.
     * @throws NullPointerException if the key is null.
     * @throws ClassCastException if the value is of a type not suitable for this builder.
     * @throws IllegalArgumentException if some property of the value is not suitable for this builder.
     */
    Object put(String key, Object value);

    /**
     * {@inheritDoc}<p/>
     *
     * @param map mappings to be stored in this builder.
     * @throws NullPointerException if the map is null, or any key is null.
     * @throws ClassCastException if any value is of a type not suitable for this builder.
     * @throws IllegalArgumentException if some property of any value in the map is not suitable for this builder.
     */
    void putAll(Map<? extends String, ?> map);

    /**
     * Removes the mapping for a key if it exists.<p/>
     *
     * NOTE: This method violates the Map contract if invoked with a basis key. Rather than remove the key, this
     * method associates it with its <i>default value</i>.
     *
     * @param key the key to de-associate.
     * @throws NullPointerException if the key is null.
     * @throws ClassCastException if the key is not a String.
     */
    Object remove(Object key);

    /**
     * Removes all extension mappings from this builder and resets all basis keys to their <i>default values</i>.<p/>
     *
     * NOTE: This method violates the Map contract if this builder contains basis keys. Basis keys are permanent,
     * so the builder will not be empty after this method returns.
     */
    void clear();

    /**
     * {@inheritDoc}<p/>
     *
     * NOTE: This method violates the Map contract if basis keys are removed from the resulting set. Rather than
     * remove the key, the key will become associated with its <i>default value</i>.
     */
    Set<String> keySet();

    /**
     * {@inheritDoc}<p/>
     *
     * NOTE: This method violates the Map contract if a value associated with a basis key is removed from the
     * resulting collection. Rather than remove the association, the key will become associated with its <i>default
     * value</i>.
     */
    Collection<Object> values();

    /**
     * {@inheritDoc}<p/>
     *
     * The entries in the resulting set can be used to assign values in this builder using the {@link Entry#setValue}
     * method. However, {@code null} is interpreted to represent the <i>default value</i> of the key. See
     * {@link #put}.<p/>
     *
     * NOTE: This method violates the Map contract if an entry for a basis key is removed from the resulting set.
     * Rather than remove the association, the key will become associated with its <i>default value</i>.
     */
    Set<Entry<String, Object>> entrySet();

    /**
     * Constructs a Grain instance from this builder's current state. This method may be invoked multiple times.
     *
     * @return a grain instance.
     * @throws IllegalArgumentException if some property of this builder is not suitable for the grain being
     *                                  constructed.
     */
    Grain build();

    /**
     * {@inheritDoc}
     */
    boolean equals(Object obj);

    /**
     * {@inheritDoc}
     */
    int hashCode();
}
