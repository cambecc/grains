package net.nullschool.reflect;

import java.lang.reflect.*;


/**
 * 2013-03-24<p/>
 *
 * Utility methods for operating on types.
 *
 * @author Cameron Beccario
 */
public enum TypeTools {;

    /**
     * Compares the inheritance relationship between two classes. This method returns:
     * <ul>
     * <li>-1 : when left is encompassed by right, i.e., right is a superclass or superinterface of left.</li>
     * <li>0 : when left is exactly the same type as right.</li>
     * <li>1 : when right is encompassed by left, i.e., left is a superclass or superinterface of right.</li>
     * <li>null : when left and right have no inheritance relationship.</li>
     * </ul>
     *
     * Note that primitive types, such as int.class, have no inheritance relationship with any other type,
     * and are equal only to themselves.
     *
     * @param left the class to compare.
     * @param right the class to compare.
     * @return -1, 0, 1, or null depending on the inheritance relationship between left and right.
     * @throws NullPointerException if just one of the arguments is null.
     */
    public static Integer compare(Class<?> left, Class<?> right) {
        if (left == right) {
            return 0;
        }
        if (right.isAssignableFrom(left)) {
            return -1;
        }
        if (left.isAssignableFrom(right)) {
            return 1;
        }
        return null;
    }

    /**
     * Returns the class object that represents an array of the specified component type. For example,
     * this method will return {@code Foo[].class} if invoked with {@code Foo.class}.
     *
     * @param componentType the element type of the array.
     * @return the type object representing an array of the component type.
     * @throws NullPointerException if component type is null.
     */
    public static Class<?> buildArrayType(Class<?> componentType) {
        return Array.newInstance(componentType, 0).getClass();
    }

    /**
     * Returns the erasure of the specified type.
     *
     * @param type the type to erase
     * @return the type's erasure, or null if {@code type} is null.
     */
    public static Class<?> erase(Type type) {
        return Eraser.INSTANCE.invoke(type);
    }

    /**
     * Returns a string representation of the provided type. All component classes are printed using their fully
     * qualified names, and nested types are delimited with '.' (rather than '$' as Class.toString() does). Type
     * arguments are enclosed in angle brackets {@code &lt;&gt;}, bounds are joined with ampersand {@code &}, and
     * each array dimension is indicated with square brackets {@code []}.<p/>
     *
     * Examples:
     * <pre>
     *     List                     - java.util.List
     *     List&lt;? extends Integer&gt;  - java.util.List&lt;? extends java.lang.Integer&gt;
     *     List[]                   - java.util.List[]
     *     Map.Entry                - java.util.Map.Entry
     *     int                      - int
     *     int[][]                  - int[][]
     * </pre>
     *
     * @param type the type to print as a String.
     * @return the String representation.
     * @throws NullPointerException if type is null.
     */
    public static String print(Type type) {
        return print(type, new StringTypePrinter());
    }

    /**
     * Returns a string representation of the provided type using the specified {@link TypePrinter} to build the
     * resulting string. All component classes are printed according to the implementation of
     * {@link TypePrinter#print(Class)}. Nested types are delimited with '.' (rather than '$' as Class.toString()
     * does). Type arguments are enclosed in angle brackets {@code &lt;&gt;}, bounds are joined with ampersand
     * {@code &}, and each array dimension is indicated with square brackets {@code []}.
     *
     * @param type the type to print as a string.
     * @param printer the printer used to build the resulting String.
     * @return the String representation.
     * @throws NullPointerException if type is null.
     */
    public static String print(Type type, TypePrinter printer) {
        return new TypeWriter(printer).invoke(type).toString();
    }

    /**
     * Invokes the specified operator on each type in the provided array and packages the results in a new array.
     *
     * @param operator the operator to invoke.
     * @param types the types to invoke the operator on.
     * @return a new array containing the results of the operation invocations.
     * @throws NullPointerException if types is null or operator is null.
     */
    public static Type[] apply(TypeOperator<? extends Type> operator, Type[] types) {
        Type[] result = types.length > 0 ? new Type[types.length] : types;
        for (int i = 0; i < types.length; i++) {
            result[i] = operator.invoke(types[i]);
        }
        return result;
    }

    private static final class LateTypeConverter extends AbstractTypeOperator<Type> {
        private static final LateTypeConverter INSTANCE = new LateTypeConverter();

        @Override public Type invoke(Class<?> clazz) { return clazz; }
        @Override public Type invoke(ParameterizedType pt) { return LateParameterizedType.copyOf(pt); }
        @Override public Type invoke(GenericArrayType gat) { return LateGenericArrayType.copyOf(gat); }
        @Override public Type invoke(WildcardType wt) { return LateWildcardType.copyOf(wt); }
        @Override public Type invoke(TypeVariable<?> tv) { return LateTypeVariable.copyOf(tv); }
    }

    /**
     * Transforms the provided Type instance into a late type implementation (i.e., one of LateParameterizedType,
     * LateGenericArrayType, LateWildcardType, or LateTypeVariable), except for instances of Class, which remain
     * unchanged.
     */
    static Type copyOf(Type type) {
        return LateTypeConverter.INSTANCE.invoke(type);
    }
}
