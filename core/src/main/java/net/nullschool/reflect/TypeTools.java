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

import java.lang.reflect.*;


/**
 * 2013-03-24<p/>
 *
 * Utility methods for operating on types.
 *
 * @author Cameron Beccario
 */
public final class TypeTools {

    private TypeTools() {
        throw new AssertionError();
    }

// UNDONE: Unlikely to be correct. Need to check JLS7 §5.1.5 and §5.1.6. Then this method can be used to implement the
// SymbolTable#isWider method in the grains-generator module.
//
//    /**
//     * Compares the inheritance relationship between two classes. This method returns:
//     * <ul>
//     * <li>-1 : when left is encompassed by right, i.e., right is a superclass or superinterface of left.</li>
//     * <li>0 : when left is exactly the same type as right.</li>
//     * <li>1 : when right is encompassed by left, i.e., left is a superclass or superinterface of right.</li>
//     * <li>null : when left and right have no inheritance relationship.</li>
//     * </ul>
//     *
//     * Note that primitive types, such as int.class, have no inheritance relationship with any other type,
//     * and are equal only to themselves.
//     *
//     * @param left the class to compare.
//     * @param right the class to compare.
//     * @return -1, 0, 1, or null depending on the inheritance relationship between left and right.
//     * @throws NullPointerException if just one of the arguments is null.
//     */
//    public static Integer compare(Class<?> left, Class<?> right) {
//        if (left == right) {
//            return 0;
//        }
//        if (right.isAssignableFrom(left)) {
//            return -1;
//        }
//        if (left.isAssignableFrom(right)) {
//            return 1;
//        }
//        return null;
//    }

    /**
     * Returns true if the specified class is an inner class (i.e., a non-static member class).
     *
     * @param clazz the class to test.
     * @return true if the class is an inner class.
     * @throws NullPointerException if clazz is null.
     */
    public static boolean isInnerClass(Class<?> clazz) {
        return !Modifier.isStatic(clazz.getModifiers()) && clazz.isMemberClass();
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
     * @throws IllegalArgumentException if type refers to an instance of an unknown implementation of {@link Type}.
     */
    public static Class<?> erase(Type type) {
        return Eraser.INSTANCE.apply(type);
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
        return print(type, new FullNamePrinter());
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
        return new TypeWriter(printer).apply(type).toString();
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
            result[i] = operator.apply(types[i]);
        }
        return result;
    }

    private static final class LateTypeConverter extends AbstractTypeOperator<Type> {
        private static final LateTypeConverter INSTANCE = new LateTypeConverter();

        @Override public Type apply(Class<?> clazz) { return clazz; }
        @Override public Type apply(ParameterizedType pt) { return LateParameterizedType.copyOf(pt); }
        @Override public Type apply(GenericArrayType gat) { return LateGenericArrayType.copyOf(gat); }
        @Override public Type apply(WildcardType wt) { return LateWildcardType.copyOf(wt); }
        @Override public Type apply(TypeVariable<?> tv) { return LateTypeVariable.copyOf(tv); }
    }

    /**
     * Transforms the provided Type instance into a late type implementation (i.e., one of LateParameterizedType,
     * LateGenericArrayType, LateWildcardType, or LateTypeVariable), except for instances of Class, which remain
     * unchanged.
     */
    static Type copyOf(Type type) {
        return LateTypeConverter.INSTANCE.apply(type);
    }

    /**
     * Derives the type that represents the public interface of the specified type. If no public interface can be
     * found, then the specified type is returned as-is.<p/>
     *
     * This implementation inspects the {@link PublicInterfaceRef} annotation declared directly on the specified type,
     * if any.
     *
     * @param type the type.
     * @return the type's publicly exported interface.
     * @throws NullPointerException if type is null.
     */
    public static Class<?> publicInterfaceOf(Class<?> type) {
        PublicInterfaceRef pi = type.getAnnotation(PublicInterfaceRef.class);
        return pi != null ? pi.value() : type;
    }
}
