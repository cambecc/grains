package net.nullschool.grains;

import net.nullschool.collect.*;
import net.nullschool.collect.basic.BasicCollections;
import net.nullschool.reflect.TypeToken;
import net.nullschool.reflect.TypeTools;
import net.nullschool.transform.Transform;

import java.lang.reflect.*;
import java.util.*;


/**
 * 2013-06-15<p/>
 *
 * An immutable type policy that is configurable by registering Class objects. Common usage is to create a new type
 * policy from one of the predefined configurations. For example:
 * <pre>
 * public static final TypePolicy MY_POLICY =
 *     ConfigurableTypePolicy.STANDARD
 *         .withImmutableTypes(ImmutableFoo.class, ImmutableBar.class)
 *         .withImmutableMapping(MyImmutableList.class, List.class);
 * </pre>
 *
 * @see #withImmutableTypes
 * @see #withImmutableMapping
 * @author Cameron Beccario
 */
public final class ConfigurableTypePolicy implements TypePolicy {

    /**
     * A type policy that contains no types.
     */
    public static final ConfigurableTypePolicy EMPTY = new ConfigurableTypePolicy();
    /**
     * A type policy pre-configured with all immutable types supported by the Grains framework.
     */
    public static final ConfigurableTypePolicy STANDARD = EMPTY
        .withImmutableTypes(PRIMITIVE_TYPES)
        .withImmutableTypes(BOXED_PRIMITIVE_TYPES)
        .withImmutableTypes(ANCILLARY_TYPES)
        .withImmutableTypes(Enum.class)   // For convenience, we assume all enums are immutable.
        .withImmutableTypes(Grain.class)  // All implementations/subtypes of Grain are immutable by contract.
        .withImmutableMapping(List.class, ConstList.class)  // Define ConstList to be the immutable version of List.
        .withImmutableMapping(Set.class, ConstSet.class)    // and so on...
        .withImmutableMapping(Map.class, ConstMap.class)
        .withImmutableMapping(Collection.class, ConstCollection.class)
        .withImmutableMapping(SortedSet.class, ConstSortedSet.class)
        .withImmutableMapping(SortedMap.class, ConstSortedMap.class)
        ;


    private final Set<Class<?>> types;  // registry of all immutable types
    private final Set<Class<?>> bases;  // registry of immutable types not marked final
    private final Map<Class<?>, Class<?>> mappings;  // mappings of non-immutable types to immutable types.

    private ConfigurableTypePolicy() {
        this.types = BasicCollections.emptySet();
        this.bases = BasicCollections.emptySet();
        this.mappings = BasicCollections.emptyMap();
    }

    /**
     * Creates a copy of the specified policy.
     */
    private ConfigurableTypePolicy(ConfigurableTypePolicy source) {
        this.types = new LinkedHashSet<>(source.types);
        this.bases = new LinkedHashSet<>(source.bases);
        this.mappings = new LinkedHashMap<>(source.mappings);
    }

    /**
     * Returns all immutable types registered with this policy.
     */
    public Set<Class<?>> getImmutableTypes() {
        return Collections.unmodifiableSet(types);
    }

    /**
     * Returns all immutable type mappings registered with this policy.
     */
    public Map<Class<?>, Class<?>> getImmutableMappings() {
        return Collections.unmodifiableMap(mappings);
    }

    private ConfigurableTypePolicy registerTypes(Collection<? extends Class<?>> newTypes) {
        // Add all types to the registry and then check each one for the final modifier.
        types.addAll(newTypes);
        for (Class<?> newType : newTypes) {
            if (!Modifier.isFinal(newType.getModifiers())) {
                // Not final means it can (usually) be extended.
                bases.add(newType);
            }
        }
        return this;
    }

    /**
     * Defines a set of types as being immutable, allowing invocations of {@link #isImmutableType} to return true.
     * For any type not marked final, all of its subtypes will also be considered immutable. This method returns a
     * new policy containing all the registrations of this policy plus the specified immutable types, leaving this
     * policy unchanged.
     *
     * @param types the immutable types to register.
     * @return a new policy that unions this policy's types with the specified types.
     * @throws NullPointerException if the collection is null or any element is null.
     */
    public ConfigurableTypePolicy withImmutableTypes(Collection<? extends Class<?>> types) {
        ConfigurableTypePolicy result = new ConfigurableTypePolicy(this);
        return result.registerTypes(types);
    }

    /**
     * Defines a set of types as being immutable, allowing invocations of {@link #isImmutableType} to return true.
     * For any type not marked final, all of its subtypes will also be considered immutable. This method returns a
     * new policy containing all the registrations of this policy plus the specified immutable types, leaving this
     * policy unchanged.
     *
     * @param types the immutable types to register.
     * @return a new policy that unions this policy's types with the specified types.
     * @throws NullPointerException if any type is null.
     */
    public ConfigurableTypePolicy withImmutableTypes(Class<?>... types) {
        return withImmutableTypes(Arrays.asList(types));
    }

    /**
     * Returns a new policy containing all the registrations of this policy plus the specified mapping, leaving
     * this policy unchanged. A mapping defines a translation from a non-immutable type S to a <i>narrower</i>
     * immutable type T, to be used by {@link #asImmutableType(Class)}. Only the exact type S, not its subtypes or
     * supertypes, will be mapped to T. For example, a mapping from {@code Set} to {@code ConstSet} does not define
     * a mapping from either {@code SortedSet} or {@code Collection}.<p/>
     *
     * If a mapping already exists for the specified "from" type S, then that mapping is replaced with the new "to"
     * type T.<p/>
     *
     * Note: this method registers the "to" type T as an immutable type, just as {@link #withImmutableTypes} would.
     *
     * @param from the non-immutable type.
     * @param to the immutable type.
     * @return a new policy that unions this policy's mappings with the specified mapping.
     * @throws NullPointerException if any argument is null.
     * @throws IllegalArgumentException if {@code from} is not a wider type than {@code to}.
     */
    public ConfigurableTypePolicy withImmutableMapping(Class<?> from, Class<?> to) {
        if (!from.isAssignableFrom(to)) {
            throw new IllegalArgumentException(String.format("%s is not a supertype of %s", from, to));
        }
        ConfigurableTypePolicy result = new ConfigurableTypePolicy(this);
        result.mappings.put(from, to);
        return result.registerTypes(Arrays.asList(to));
    }

    /**
     * {@inheritDoc}<p/>
     *
     * This implementation returns true if the specified class is the exact type, or a subtype, of any registered
     * immutable.
     *
     * @throws NullPointerException {@inheritDoc}
     */
    @Override public boolean isImmutableType(Class<?> clazz) {
        if (types.contains(clazz)) {
            return true;
        }
        for (Class<?> base : bases) {
            if (base.isAssignableFrom(clazz)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}<p/>
     *
     * This implementation looks for a mapping having exactly the same class as {@code clazz} and returns the
     * associated immutable type. (Translations can be registered by invoking the {@link #withImmutableMapping}
     * method). If no mapping exists, then {@code clazz} is returned if it is already an immutable type, otherwise
     * null is returned.
     *
     * @throws NullPointerException {@inheritDoc}
     */
    @Override public Class<?> asImmutableType(Class<?> clazz) {
        Class<?> result = mappings.get(clazz);
        if (result == null && isImmutableType(clazz)) {
            result = clazz;
        }
        return result;
    }

    private static class CastingTransform<T> implements Transform<T> {
        private final Class<T> clazz;

        private CastingTransform(Class<T> clazz) {
            this.clazz = clazz;
        }

        @Override public T apply(Object o) {
            return clazz.cast(o);
        }
    }

    @Override public <T> Transform<T> newTransform(TypeToken<T> token) {
        // UNDONE: allow configuration of this method's behavior by plugging-in a TransformFactory or similar.
        //         For example, switch between transforms that use casts or just assertions.
        Type type = token.asType();
        if (type instanceof Class) {
            @SuppressWarnings("unchecked") final Class<T> clazz = (Class<T>)type;
            return new CastingTransform<>(clazz);
        }
        if (type instanceof ParameterizedType) {
            // UNDONE: proper transform that checks elements of collections. For now, this is unsafe.
            @SuppressWarnings("unchecked") final Class<T> erasure = (Class<T>)TypeTools.erase(type);
            return new CastingTransform<>(erasure);
        }
        throw new IllegalArgumentException("Cannot create transform for: " + TypeTools.toString(type));
    }
}
