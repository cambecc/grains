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

import net.nullschool.collect.basic.BasicCollections;
import net.nullschool.grains.GrainProperty;
import net.nullschool.grains.GrainProperty.Flag;
import net.nullschool.grains.SimpleGrainProperty;
import net.nullschool.reflect.LateParameterizedType;

import java.beans.*;
import java.lang.reflect.*;
import java.util.*;

import static net.nullschool.grains.generate.GenerateTools.*;
import static net.nullschool.collect.basic.BasicCollections.*;
import static net.nullschool.reflect.TypeTools.*;


/**
 * 2013-05-09<p/>
 *
 * Organizes and constructs the {@link Symbol} instances used for code generation.
 *
 * @author Cameron Beccario
 */
final class SymbolTable {

    private final Class<?> schema;
    private final TypeTable typeTable;
    private final TypePrinterFactory printerFactory;
    private final Member typePolicyMember;

    SymbolTable(Class<?> schema, TypeTable typeTable, TypePrinterFactory printerFactory, Member typePolicyMember) {
        this.schema = schema;
        this.typeTable = typeTable;
        this.printerFactory = printerFactory;
        this.typePolicyMember = typePolicyMember;
    }

    private static Type cook(Type type) {
        return new Cook().apply(type);
    }

    private Type immutify(Type type) {
        return new Immutify(typeTable).apply(type);
    }

    private static Type dewildcard(Type type) {
        return new DeWildcard().apply(type);
    }

    private static Set<Flag> flagsFor(PropertyDescriptor pd) {
        return pd.getReadMethod().getName().startsWith("is") ?
            setOf(Flag.IS_PROPERTY) :
            BasicCollections.<Flag>emptySet();
    }

    /**
     * Returns true if the left type is wider than the right type, i.e., the right type is more specific than the
     * left type.
     */
    private static boolean isWider(Type left, Type right) {
        // UNDONE: use proper widening conversion check that follows rules in JLS7 §4.10 and §5.1.5.
        return erase(left).isAssignableFrom(erase(right));
    }

    /**
     * Collects all grain properties explicitly defined on the specified type. This method uses the JavaBean
     * introspector to identify the properties. Type variables are replaced with their appropriate type arguments,
     * if any.
     *
     * @param type the type to introspect.
     * @return a list of declared grain properties.
     * @throws IntrospectionException if an exception occurs during introspection.
     */
    static List<GrainProperty> collectDeclaredProperties(Type type) throws IntrospectionException {
        List<GrainProperty> results = new ArrayList<>();
        LateParameterizedType lpt = asLateParameterizedType(type);
        Class<?> clazz = erase(type);
        BeanInfo bi = Introspector.getBeanInfo(clazz);

        for (PropertyDescriptor pd : bi.getPropertyDescriptors()) {
            if (pd instanceof IndexedPropertyDescriptor) {
                // Ignore index properties.
                continue;
            }
            Method getter = pd.getReadMethod();
            if (getter == null || getter.getDeclaringClass() != clazz) {
                // Ignore properties not declared on the current type or not readable.
                continue;
            }

            // If the type we are processing has type variable bindings, then replace the type variables, if any.
            // For example, "T Foo#getId()" becomes "String Foo#getId()" if current type is Foo<String>.
            Type returnType = getter.getGenericReturnType();
            if (lpt != null) {
                returnType = lpt.resolve(returnType);
            }
            results.add(new SimpleGrainProperty(pd.getName(), returnType, flagsFor(pd)));
        }
        return results;
    }

    /**
     * Collects all grain properties defined on the specified type and all super classes and super interfaces. This
     * method uses the JavaBean introspector to identify the properties. Type variables are replaced with their
     * appropriate type arguments, if any. Classes are traversed breadth-first, with the super class traversed before
     * any super interfaces.
     *
     * @param type the type to introspect.
     * @return a list of grain properties.
     * @throws IntrospectionException if an exception occurs during introspection.
     */
    static List<GrainProperty> collectProperties(Type type) throws IntrospectionException {
        List<GrainProperty> results = new ArrayList<>();
        Set<Type> visited = new HashSet<>();
        visited.add(null);          // ensures we don't visit null superclass
        visited.add(Object.class);  // ensures we don't visit Object and its confusing getClass() accessor.
        Deque<Type> workList = new LinkedList<>();
        workList.add(type);

        while (!workList.isEmpty()) {

            Type current = workList.removeFirst();
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);

            results.addAll(collectDeclaredProperties(current));

            workList.add(genericSuperclassOf(current));
            Collections.addAll(workList, genericInterfacesOf(current));
        }
        return results;
    }

    /**
     * Returns all unique properties by name from the specified collection. For any two properties with the same
     * name, the property having the more specific type is preferred.
     *
     * @param properties the properties to scan for duplicates.
     * @return the most specific, unique properties by name.
     */
    static List<GrainProperty> resolveProperties(Collection<? extends GrainProperty> properties) {
        Map<String, GrainProperty> bestFit = new LinkedHashMap<>();

        for (GrainProperty candidate : properties) {
            String name = candidate.getName();
            GrainProperty previous = bestFit.put(name, candidate);
            if (previous != null) {
                // We have a collision/duplicate. If the candidate is wider than the previous, put the previous back.
                if (isWider(candidate.getType(), previous.getType())) {
                    bestFit.put(name, previous);
                }
            }
        }

        return new ArrayList<>(bestFit.values());
    }

    GrainSymbol buildGrainSymbol() throws IntrospectionException {
        int typeTokenIndex = 0;

        Map<Type, TypeTokenSymbol> typeTokens = new LinkedHashMap<>();
        List<GrainProperty> properties = resolveProperties(collectProperties(schema));
        Collections.sort(properties, GrainPropertyComparator.INSTANCE);

        List<PropertySymbol> symbols = new ArrayList<>();

        for (GrainProperty prop : properties) {
            Type immutableType = dewildcard(immutify(cook(prop.getType())));
            GrainProperty immutableProp = new SimpleGrainProperty(prop.getName(), immutableType, prop.getFlags());
            TypeSymbol immutableTypeSymbol = new TypeSymbol(immutableType, printerFactory);

            TypeTokenSymbol typeTokenSymbol = null;
            if (immutableType instanceof ParameterizedType) {
                typeTokenSymbol = typeTokens.get(immutableType);
                if (typeTokenSymbol == null) {
                    int index = typeTokenIndex++;
                    typeTokens.put(
                        immutableType,
                        typeTokenSymbol = new TypeTokenSymbol(
                            "$token" + index,
                            immutableTypeSymbol,
                            new FieldSymbol("$transform" + index, immutableTypeSymbol)));
                }
            }
            symbols.add(new PropertySymbol(immutableProp, printerFactory, typeTokenSymbol));
        }

        Symbol typePolicyLoadExpression = null;
        if (!typeTokens.isEmpty()) {
            if (typePolicyMember instanceof Method) {
                typePolicyLoadExpression =
                    new StaticMethodInvocationExpression((Method)typePolicyMember, printerFactory);
            }
            else if (typePolicyMember instanceof Field) {
                typePolicyLoadExpression = new StaticFieldLoadExpression((Field)typePolicyMember, printerFactory);
            }
        }
        return new GrainSymbol(symbols, typeTokens.values(), typePolicyLoadExpression);
    }

    Map<String, Symbol> buildTypeSymbols() {
        Map<String, Symbol> map = new HashMap<>();
        for (Map.Entry<String, Type> entry : typeTable.wellKnownTypes().entrySet()) {
            map.put(entry.getKey(), new TypeSymbol(entry.getValue(), printerFactory));
        }
        for (Map.Entry<String, Type> entry : typeTable.schemaTypes(schema).entrySet()) {
            map.put(entry.getKey(), new TypeSymbol(entry.getValue(), printerFactory));
        }
        return map;
    }
}
