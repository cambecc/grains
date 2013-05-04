package net.nullschool.grains;

import net.nullschool.collect.ConstMap;

import java.util.Collection;
import java.util.Map;


/**
 * 2013-03-24<p/>
 *
 * A Grain is an immutable map of Strings to Objects with the special property that some keys are permanent and
 * restrict the kinds of values they are associated with.<p/>
 *
 * The set of permanent keys and their default values defines the "basis" of the grain. All instances of a
 * particular Grain implementation share the same set of basis keys, and each basis key restricts the values it
 * can be associated with to a specific type or even instance. This is analogous to how all instances of a class
 * share the same set of fields, and each field can store values only of a particular type.<p/>
 *
 * Grains can also hold keys beyond those defined by their basis. These "extensions" appear as normal associations
 * when iterating over the Grain's keySet, values, or entrySet, but can also be viewed independently via the
 * {@link #extensions} method. Extension keys may also (optionally) restrict their associated values.<p/>
 *
 * Grain entries can be "modified" using the {@link #with} and {@link #without} methods inherited from
 * {@link ConstMap}. These methods return a new Grain instance, leaving the original instance unmodified. Invoking
 * {@code without} on an <i>extension</i> key will produce a Grain with the extension key removed. However, because
 * <i>basis</i> keys are permanent, invoking {@code without} on a basis key produces a Grain with the key's associated
 * value reset back to its default value.<p/>
 *
 * As it can be expensive to construct Grain instances from a large sequence of {@code with} method invocations, the
 * builder pattern is utilized to simplify Grain construction. A mutable {@link GrainBuilder} instance containing the
 * current associations of a Grain can be retrieved using the {@link #builder} method. After the builder is modified
 * as desired, the {@link GrainBuilder#build} method is invoked to construct a new immutable Grain instance.<p/>
 *
 * A Grain has no defined iteration order. Whether or not {@code null} keys are allowed is implementation
 * dependent. UNDONE: be squishy on nulls?<p/>
 *
 * UNDONE: a discussion on the equivalence of null and a basis key's default value. Null is always taken to mean
 *         the default value of a basis key. Thus, if a basis key has a non-null default value, then the literal
 *         value _null_ can never be associated with the key, as any attempt to make such an association would
 *         reset the value back to the non-null default value.
 *
 * UNDONE: how strong of a requirement to make for extension values being immutable? There is no way to enforce this
 *         at runtime except to check that the type is an instance of any of the allowable basis key types. DEEP
 *         IMMUTABILITY.
 *
 * @author Cameron Beccario
 */
public interface Grain extends ConstMap<String, Object> {

    /**
     * Returns a grain containing the entries of this grain plus the specified key-value association. No visible
     * change to this grain occurs. If the specified key is not a basis key, then the key-value association becomes
     * an extension on this grain.<p/>
     *
     * This method may throw ClassCastException if the value does not meet the typing requirements of the key, or
     * IllegalArgumentException if some property of the value is not suitable for the key. Furthermore, the value
     * {@code null} is taken to represent the <i>default value</i> of the key.
     *
     * @param key the key to associate.
     * @param value the value to be associated with the key.
     * @return a grain containing this grain's associations conjoined with the specified association.
     * @throws ClassCastException if the value is of a type not suitable for this grain.
     * @throws NullPointerException if the key is null and this grain does not allow null keys. UNDONE: squishy on nulls?
     * @throws IllegalArgumentException if some property of the value is not suitable for this grain.
     */
    @Override Grain with(String key, Object value);

    /**
     * Returns a grain containing the entries of this grain plus all the entries of the specified map. No visible
     * change to this grain occurs. For any entry in the specified map whose key is not a basis key of this grain,
     * the entry becomes an extension on this grain.<p/>
     *
     * The effect of this call is equivalent to invoking {@link #with} for each entry in the specified map. Therefore,
     * this method may throw ClassCastException if any value does not meet the typing requirements or is not suitable
     * for its associated key. Furthermore, the value {@code null} is taken to represent the <i>default value</i> of
     * its associated key.
     *
     * @param map mappings to conjoin with the associations in this grain.
     * @return a grain containing this grain's associations conjoined with the associations in the provided map.
     * @throws ClassCastException if any value is of a type not suitable for this grain.
     * @throws NullPointerException if the map is null, or any key is null and this grain does not allow null keys. UNDONE: squishy on nulls?
     * @throws IllegalArgumentException if some property of any value in the map is not suitable for this grain.
     */
    @Override Grain withAll(Map<? extends String, ?> map);

    /**
     * UNDONE
     */
    @Override Grain without(Object key);

    /**
     * UNDONE
     */
    @Override Grain withoutAll(Collection<?> c);

    /**
     * Returns a new builder initialized with the same entries as this grain.
     *
     * @return a new builder instance copied from this grain.
     */
    GrainBuilder builder();

    /**
     * Returns a ConstMap of this grain's extensions, if any. An extension is an extra entry on this grain instance
     * whose key is not a basis key, i.e., is not a permanent member of this grain.
     *
     * @return a map of all extensions on this grain, or an empty map if there are none. Never returns {@code null}.
     */
    ConstMap<String, Object> extensions();
}
