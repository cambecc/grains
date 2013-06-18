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

import net.nullschool.collect.ConstMap;

import java.util.Collection;
import java.util.Map;


/**
 * 2013-03-24<p/>
 *
 * A Grain is an {@link ConstMap immutable map} of String to Object that contains permanent keys, i.e., keys that
 * cannot be disassociated from the map. Grains permit {@code null} values but disallow {@code null} keys.<p/>
 *
 * The set of permanent keys, called the "basis", differs depending on the grain implementation, and each basis key
 * may restrict the type of values it can be associated with. This is roughly analogous to how instances of a class
 * share the same set of fields, and each field stores values only of a particular type. Attempts to disassociate a
 * basis key or associate it with {@code null} results in the key becoming associated with its <i>default value</i>.
 * Generally, a key's default value is {@code null} if its type restriction is a reference type, otherwise {@code 0}
 * for primitive types. However, some grain implementations may (optionally) specify a non-null/non-zero default
 * value for a key.<p/>
 *
 * A grain can associate and disassociate non-basis keys, just like a normal map. To differentiate them from the
 * basis, these keys are called "extensions". Extension keys may also (optionally) restrict their associated
 * values.<p/>
 *
 * The {@link #keySet}, {@link #values}, {@link #entrySet}, and {@link #iterator} methods present the <i>union</i>
 * of the basis and extensions. There is no guaranteed iteration order. When desired, the {@link #extensions} method
 * provides a view of just the extension associations.<p/>
 *
 * Entries may be "modified" using the {@link #with} and {@link #without} methods inherited from {@link ConstMap}.
 * These methods return a new instance, leaving the original instance unmodified. As it can be expensive to construct
 * grain instances from a large sequence of {@code with} method invocations, the builder pattern is provided to
 * simplify construction. A mutable {@link GrainBuilder} instance containing the grain's current associations can be
 * retrieved using the {@link #newBuilder} method. After the builder is modified as desired, the {@link
 * GrainBuilder#build} method can be invoked to construct a new grain instance.<p/>
 *
 * @author Cameron Beccario
 */
public interface Grain extends ConstMap<String, Object> {

    /**
     * Returns a grain containing the entries of this grain plus the specified key-value association. No visible
     * change to this grain occurs.<p/>
     *
     * This method may throw ClassCastException if the value does not meet the typing requirements of the key, or
     * IllegalArgumentException if some property of the value is not suitable for the key. This method interprets
     * a {@code null} value to represent the <i>default value</i> of the key. Generally, a key's default value is
     * {@code null} if its type restriction is a reference type, otherwise {@code 0} for primitive types. However,
     * some keys may have a non-null/non-zero default value. For example, if a key's default value is <i>42</i>,
     * then invoking this method with a {@code null} value will associate that key with <i>42</i>.<p/>
     *
     * If the specified key is not a basis key, then the key-value association becomes an extension on this grain.
     *
     * @param key the key to associate.
     * @param value the value to be associated with the key.
     * @return a grain containing this grain's associations conjoined with the specified association.
     * @throws NullPointerException if the key is null.
     * @throws ClassCastException if the value is of a type not suitable for this grain.
     * @throws IllegalArgumentException if some property of the value is not suitable for this grain.
     */
    @Override Grain with(String key, Object value);

    /**
     * Returns a grain containing the entries of this grain plus all the entries of the specified map. No visible
     * change to this grain occurs.<p/>
     *
     * The effect of this call is equivalent to invoking {@link #with} for each entry in the specified map.
     *
     * @param map mappings to conjoin with the associations in this grain.
     * @return a grain containing this grain's associations conjoined with the associations in the provided map.
     * @throws NullPointerException if the map is null, or any key is null.
     * @throws ClassCastException if any value is of a type not suitable for this grain.
     * @throws IllegalArgumentException if some property of any value in the map is not suitable for this grain.
     */
    @Override Grain withAll(Map<? extends String, ?> map);

    /**
     * Returns a grain containing the associations of this grain minus the mapping for the specified key. No visible
     * change to this grain occurs.<p/>
     *
     * NOTE: This method violates the ConstMap contract if invoked with a basis key. Rather than remove the key, this
     * method associates it with its <i>default value</i>.
     *
     * @param key the key to de-associate.
     * @return a grain containing this grain's associations disjoined from the specified association.
     * @throws NullPointerException if the key is null.
     * @throws ClassCastException if the key is not a String.
     */
    @Override Grain without(Object key);

    /**
     * Returns a grain containing the associations of this grain minus all the mappings for the specified keys. No
     * visible change to this grain occurs.<p/>
     *
     * The effect of this call is equivalent to invoking {@link #without} for each key in the specified collection.<p/>
     *
     * NOTE: This method violates the ConstMap contract if any key in the specified collection is a basis key. Rather
     * than remove the key, this method associates it with its <i>default value</i>.
     *
     * @param keys the keys to de-associate from this grain.
     * @return a grain containing this grain's associations disjoined from the keys in the provided collection.
     * @throws NullPointerException if the specified collection is null, or any key in the collection is null.
     * @throws ClassCastException if any key in the specified collection is not a String.
     */
    @Override Grain withoutAll(Collection<?> keys);

    /**
     * Returns a new builder initialized with the same entries as this grain.
     *
     * @return a new builder instance copied from this grain.
     */
    GrainBuilder newBuilder();

    /**
     * Returns a ConstMap of this grain's extensions, if any. An extension is an extra entry on this grain instance
     * whose key is not a basis key, i.e., is not a permanent member of this grain.
     *
     * @return a map of all extensions on this grain, or an empty map if there are none. Never returns {@code null}.
     */
    ConstMap<String, Object> extensions();

    /**
     * {@inheritDoc}
     */
    boolean equals(Object obj);

    /**
     * {@inheritDoc}
     */
    int hashCode();
}
