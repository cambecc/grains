package net.nullschool.reflect;

import java.lang.reflect.*;

import static java.util.Objects.requireNonNull;

/**
 * 2013-03-24<p/>
 *
 * A mostly immutable {@link TypeVariable} that represents a type parameter with bounds. For example:
 * <pre>
 *     T
 *     T extends Number
 * </pre>
 *
 * A LateTypeVariable's bounds can be assigned <i>once</i> after construction to support cyclic bounds
 * such as {@code Enum&lt;T extends Enum&lt;T&gt;&gt;}. The LateTypeVariable must be constructed with a
 * {@code null} varargs array, and then {@link #assignBounds} can be invoked.
 *
 * @author Cameron Beccario
 */
public final class LateTypeVariable<D extends GenericDeclaration> implements TypeVariable<D> {

    private static final Type[] OBJECT_TYPE_ARRAY = new Type[] {Object.class};


    private final String name;
    private final D genericDeclaration;
    private volatile Type[] bounds;  // null means bounds have not been assigned yet.

    /**
     * Constructs a TypeVariable having the specified properties. The assignment of bounds may be
     * deferred until after construction by passing a {@code null} for the {@code bounds} vararg array,
     * and then subsequently calling {@link #assignBounds}.
     *
     * @param name the name of the type variable.
     * @param genericDeclaration the entity that declared this type variable.
     * @param bounds the upper bounds of the type variable, or {@code null} if bounds assignment is deferred.
     * @throws NullPointerException when {@code name} or {@code genericDeclaration} is {@code null}.
     */
    public LateTypeVariable(String name, D genericDeclaration, Type... bounds) {
        this.name = requireNonNull(name, "null name");
        this.genericDeclaration = requireNonNull(genericDeclaration, "null genericDeclaration");
        if (bounds != null) {
            setBounds(bounds);
        }
    }

    /**
     * Builds a LateTypeVariable representation of the specified TypeVariable.
     *
     * @param tv the type variable to copy.
     * @param <D> the owning generic declaration type.
     * @return a LateTypeVariable copy of the provided type.
     * @throws NullPointerException if the argument is null or the argument's name or genericDeclaration is null.
     */
    public static <D extends GenericDeclaration> LateTypeVariable<D> copyOf(TypeVariable<D> tv) {
        return new LateTypeVariable<>(tv.getName(), tv.getGenericDeclaration(), tv.getBounds());
    }

    /**
     * {@inheritDoc}
     */
    @Override public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override public D getGenericDeclaration() {
        return genericDeclaration;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalStateException if bounds have not yet been assigned.
     */
    @Override public Type[] getBounds() {
        if (bounds != null) {
            return bounds.clone();
        }
        throw new IllegalStateException("bounds have not yet been assigned");
    }

    private void setBounds(Type... bounds) {
        this.bounds = bounds.length != 0 ? bounds.clone() : OBJECT_TYPE_ARRAY;
    }

    /**
     * Assigns the bounds for this type variable. Assigning bounds allows construction of cyclic bounds such as
     * {@code Enum&lt;T extends Enum&lt;T&gt;&gt;}. This method is <i>write-once</i>.
     *
     * @param bounds the bounds to assign.
     * @throws IllegalArgumentException if bounds have already been assigned for this type variable.
     * @throws NullPointerException if {@code bounds} is null.
     */
    public synchronized void assignBounds(Type... bounds) {
        if (this.bounds != null) {
            throw new IllegalArgumentException("bounds already assigned");
        }
        setBounds(bounds);
    }

    private boolean equals(TypeVariable<?> that) {
        return this.name.equals(that.getName()) && this.genericDeclaration.equals(that.getGenericDeclaration());
    }

    /**
     * Returns true if the specific object is a TypeVariable and its name and generic declaration are equal to
     * this instance's name and generic declaration.
     */
    @Override public boolean equals(Object that) {
        return this == that || that instanceof TypeVariable<?> && this.equals((TypeVariable<?>)that);
    }

    /**
     * The hash code for a type variable is defined to be the hash code of its name XOR'd with the hash code
     * of its generic declaration, as per Oracle's implementation.
     */
    @Override public int hashCode() {
        return name.hashCode() ^ genericDeclaration.hashCode();
    }

    /**
     * Returns the name of this type variable.
     */
    @Override public String toString() {
        return name;
    }
}
