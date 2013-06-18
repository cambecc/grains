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

import javassist.*;
import javassist.bytecode.*;
import javassist.bytecode.SignatureAttribute.*;
import net.nullschool.collect.basic.*;
import net.nullschool.transform.Transform;
import net.nullschool.collect.*;
import net.nullschool.grains.*;
import net.nullschool.grains.generate.NamingPolicy.Name;
import net.nullschool.reflect.*;
import net.nullschool.util.MemoizedHashCode;
import net.nullschool.util.ObjectTools;

import javax.annotation.Generated;
import java.io.*;
import java.lang.reflect.Type;
import java.security.ProtectionDomain;
import java.util.*;

import static javassist.bytecode.SignatureAttribute.*;


/**
 * 2013-05-09<p/>
 *
 * Organizes types for code generation. Dynamically constructs new types as needed (using the javassist library),
 * transforms classes into their immutable analogs by delegating to {@link net.nullschool.grains.TypePolicy}, and provides a location to
 * statically define the common set of types required for code generation.<p/>
 *
 * Dynamic class construction allows the code generators to be driven entirely by Java reflection, particularly when
 * the generators require reflecting against classes that do not yet exist. For example, if the Person schema has
 * a property of type Address, then the PersonGrain implementation will have a property of type AddressGrain. However,
 * because PersonGrain may be generated before AddressGrain, there would exist no AddressGrain class for Java to
 * load and reflect against. In this scenario, a stub AddressGrain Class object is dynamically constructed using
 * javassist to allow code generation to continue.
 *
 * @author Cameron Beccario
 */
final class TypeTable {

    /**
     * The types required for code generation. The name of the enum constant is the identifier to use in the
     * string templates.
     */
    private enum WellKnownType {
        abstractGrain               (AbstractGrain.class),
        abstractGrainBuilder        (AbstractGrainBuilder.class),
        abstractGrainProxy          (AbstractGrainProxy.class),
        arrays                      (Arrays.class),
        basicCollections            (BasicCollections.class),
        collections                 (Collections.class),
        generated                   (Generated.class),
        grain                       (Grain.class),
        grainBuilder                (GrainBuilder.class),
        grainFactory                (GrainFactory.class),
        grainFactoryRef             (GrainFactoryRef.class),
        grainGenerator              (GrainGenerator.class),
        grainProperty               (GrainProperty.class),
        grainTools                  (GrainTools.class),
        illegalStateException       (IllegalStateException.class),
        indexOutOfBoundsException   (IndexOutOfBoundsException.class),
        invalidObjectException      (InvalidObjectException.class),
        iteratorTools               (IteratorTools.class),
        linkedHashSet               (LinkedHashSet.class),
        mapTools                    (MapTools.class),
        memoizedHashCode            (MemoizedHashCode.class),
        noSuchElementException      (NoSuchElementException.class),
        object                      (Object.class),
        objectInputStream           (ObjectInputStream.class),
        publicInterfaceRef          (PublicInterfaceRef.class),
        serializable                (Serializable.class),
        set                         (Set.class),
        simpleGrainProperty         (SimpleGrainProperty.class),
        string                      (String.class),
        transform                   (Transform.class),
        type                        (Type.class),
        typePolicy                  (TypePolicy.class),
        typeToken                   (TypeToken.class),

        abstractIterableMap         (new TypeToken<AbstractIterableMap<String, Object>>(){}),
        basisPropertyMap            (new TypeToken<ConstMap<String, GrainProperty>>(){}),
        collectionWildcard          (new TypeToken<Collection<?>>(){}),
        constMap                    (new TypeToken<ConstMap<String, Object>>(){}),
        constSortedMap              (new TypeToken<ConstSortedMap<String, Object>>(){}),
        entry                       (new TypeToken<Map.Entry<String, Object>>(){}),
        iterableMap                 (new TypeToken<IterableMap<String, Object>>(){}),
        mapIterator                 (new TypeToken<MapIterator<String, Object>>(){}),
        mapStringObjectWildcards    (new TypeToken<Map<? extends String, ?>>(){}),
        treeMap                     (new TypeToken<TreeMap<String, Object>>(){}),
        ;


        private final Type _type;

        WellKnownType(Class<?> clazz) { this._type = clazz; }
        WellKnownType(TypeToken<?> token) { this._type = token.asType(); }
    }


    private final NamingPolicy namingPolicy;
    private final TypePolicy typePolicy;
    private final ClassPool classPool;  // Pool used for dynamic construction of grain classes.
    private final ProtectionDomain protectionDomain;

    TypeTable(NamingPolicy namingPolicy, TypePolicy typePolicy) {
        this.namingPolicy = Objects.requireNonNull(namingPolicy);
        this.typePolicy = Objects.requireNonNull(typePolicy);
        this.classPool = new ClassPool();
        this.classPool.appendClassPath(new LoaderClassPath(deriveClassLoader()));
        this.protectionDomain = TypeTable.class.getProtectionDomain();
    }

    /**
     * Returns a map containing the common types required for code generation. The map keys are identifiers to be
     * used in the string templates.
     */
    Map<String, Type> wellKnownTypes() {
        Map<String, Type> map = new HashMap<>();
        for (WellKnownType wkt : WellKnownType.values()) {
            map.put(wkt.name(), wkt._type);
        }
        return Collections.unmodifiableMap(map);
    }

    private static ClassLoader deriveClassLoader() {
        // CONSIDER: use TypeTable.class.getClassLoader here? Then would be same CL as above WKT enum.
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * An object to hold both the Java Class object and its associated Javassist CtClass object together in one unit.
     */
    class ClassHandle {

        private final String name;
        private Class<?> clazz;
        private final CtClass ctClass;
        private final boolean isCreated;

        private ClassHandle(String name, Class<?> clazz, CtClass ctClass) {
            this.name = name;
            this.clazz = clazz;
            this.ctClass = ctClass;
            this.isCreated = clazz == null && ctClass != null;
        }

        /**
         * Returns true if the class is loaded by a Java class loader and the Class object is available.
         */
        boolean isLoaded() { return clazz != null; }

        /**
         * Returns true if the class was dynamically created using Javassist.
         */
        boolean isDynamicallyCreated() { return isCreated; }

        /**
         * Returns the Class object associated with this handle. If this handle refers to a dynamically created class,
         * then the class is first constructed using {@link CtClass#toClass(ClassLoader, ProtectionDomain)}.
         */
        Class<?> toClass() {
            try {
                return isLoaded() ? clazz : (clazz = ctClass.toClass(deriveClassLoader(), protectionDomain));
            }
            catch (Exception e) {
                throw new RuntimeException("Failed to convert " + name + " to a Class.", e);
            }
        }
    }

    /**
     * Loads the class objects for the specified fully qualified name. If the class could not be loaded because
     * it does not exist or because loading failed, then an empty ClassHandle is returned.
     *
     * @param name the fully qualified name of the class to load.
     * @return a class handle holding both the Class and CtClass representations of the desired type.
     */
    private ClassHandle loadClass(String name) {
        try {
            return new ClassHandle(name, deriveClassLoader().loadClass(name), classPool.get(name));
        }
        catch (ClassNotFoundException | LinkageError | NotFoundException e) {
            return new ClassHandle(name, null, null);
        }
    }

    /**
     * Assigns a generic signature to the specified new class. The generic signature is constructed by copying and
     * passing along the type parameters verbatim to the specified base class. For example, if the new class is named
     * "Foo" and the base class is {@code AbstractMap&lt;K, V&gt;}, then the generic signature is created as:
     * {@code Foo&lt;K, V&gt; extends AbstractMap&lt;K, V&gt;}.
     *
     * @param newClass the class to assign the generic signature on.
     * @param baseClass the base class.
     * @param baseSignature the base's class signature.
     */
    private static void assignGenericSignature(CtClass newClass, CtClass baseClass, ClassSignature baseSignature) {
        TypeParameter[] params = baseSignature.getParameters();
        TypeArgument[] args = new TypeArgument[params.length];
        for (int i = 0; i < params.length; i++) {
            TypeParameter tp = params[i];
            args[i] = new TypeArgument(new TypeVariable(tp.getName()));
        }
        ClassSignature cs;
        if (baseClass.isInterface()) {
            cs = new ClassSignature(
                baseSignature.getParameters(),
                null,
                new ClassType[] {new ClassType(baseClass.getName(), args)});
        }
        else {
            cs = new ClassSignature(
                baseSignature.getParameters(),
                new ClassType(baseClass.getName(), args),
                null);
        }
        newClass.setGenericSignature(cs.encode());
    }

    /**
     * Dynamically creates a new class having the specified name, inheriting the specified base class and having the
     * same generic type parameters of the base class, if any.
     *
     * @param name the fully qualified name of the new class.
     * @param inherits the base class.
     * @return the class handle.
     */
    ClassHandle createClass(String name, Class<?> inherits) {
        try {
            CtClass newClass = inherits == null || inherits.isInterface() ?
                classPool.makeInterface(name) :
                classPool.makeClass(name);
            newClass.setModifiers(Modifier.setPublic(newClass.getModifiers()));

            if (inherits != null) {
                CtClass baseClass = classPool.get(inherits.getName());
                ClassSignature baseSignature =
                    baseClass.getGenericSignature() != null ? toClassSignature(baseClass.getGenericSignature()) : null;
                TypeParameter[] params = baseSignature != null ? baseSignature.getParameters() : new TypeParameter[0];
                if (params.length > 0) {
                    // The base class is generic. Create equivalent type parameters on the new class.
                    assignGenericSignature(newClass, baseClass, baseSignature);
                }
                if (inherits.isInterface()) {
                    newClass.setInterfaces(new CtClass[] {baseClass});
                }
                else {
                    newClass.setSuperclass(baseClass);
                    if (inherits == Enum.class) {
                        newClass.setModifiers(newClass.getModifiers() | Modifier.ENUM);
                    }
                }
            }
            return new ClassHandle(name, null, newClass);
        }
        catch (NotFoundException | CannotCompileException | BadBytecode e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads the class object for the specified fully qualified name. If the class could not be loaded because
     * it does not exist or because loading failed, then a new class, extending the specified base class, is
     * dynamically created.
     *
     * @param name the fully qualified name of the class to load or create.
     * @param baseClass the class to extend if a new class is created.
     * @return the handle for the class.
     */
    private ClassHandle loadOrCreateClass(String name, Class<?> baseClass) {
        ClassHandle result = loadClass(name);
        return result.isLoaded() ? result : createClass(name, baseClass);
    }

    /**
     * Marks the enclosing class as having {@code nested} as an inner class.
     *
     * @param nested the nested class.
     * @param enclosing the enclosing class.
     */
    private void recordAsInnerClass(CtClass nested, CtClass enclosing) {
        ClassFile enclosingFile = enclosing.getClassFile();  // expected to be not frozen
        InnerClassesAttribute attribute = (InnerClassesAttribute)enclosingFile.getAttribute(InnerClassesAttribute.tag);
        if (attribute == null) {
            attribute = new InnerClassesAttribute(enclosingFile.getConstPool());
            enclosingFile.addAttribute(attribute);
        }
        ClassFile nestedFile = nested.getClassFile2();  // expected to be frozen
        attribute.append(
            nested.getName(),
            enclosing.getName(),
            nested.getSimpleName(),
            (nestedFile.getAccessFlags() & ~AccessFlag.SUPER) | AccessFlag.STATIC);
    }

    /**
     * Loads the nested class object for the specified name. If the nested class could not be loaded because
     * it does not exist or because loading failed, then a new static class, nested inside the specified enclosing
     * class, is dynamically created. If the nested class exists but the enclosing class does not, i.e., it was
     * dynamically created, then the enclosing class is marked as having this nested class as an inner class.
     *
     * @param name the fully qualified name of the nested class to load or create.
     * @param simpleName the simple name of the nested class.
     * @param enclosing the enclosing class.
     * @return the handle for the nested class.
     */
    private ClassHandle loadOrCreateNested(String name, String simpleName, ClassHandle enclosing) {
        ClassHandle nested = loadClass(name);
        if (nested.isLoaded()) {
            if (enclosing.isDynamicallyCreated()) {
                // If the enclosing class is brand new and we don't mark it as containing the nested class, then
                // the class loader will be unhappy. How can the nested class exist but the enclosing not exist?
                // One possibility is to delete the enclosing type's .class file and then run the generator...
                recordAsInnerClass(nested.ctClass, enclosing.ctClass);
            }
            return nested;
        }
        return new ClassHandle(name, null, enclosing.ctClass.makeNestedClass(simpleName, true));
    }

    synchronized Class<?> getGrainClass(Class<?> schema) {
        return loadOrCreateClass(namingPolicy.getName(schema, Name.grain), Grain.class).toClass();
    }

    synchronized Class<?> getGrainBuilderClass(Class<?> schema) {
        return loadOrCreateClass(namingPolicy.getName(schema, Name.builder), GrainBuilder.class).toClass();
    }

    /**
     * Returns a map containing all of the types that the specified schema produces during code generation. The map
     * keys are identifiers to be used in the string templates. If any of the types do not currently exist, stub
     * Class objects are dynamically constructed.
     */
    synchronized Map<String, Type> schemaTypes(Class<?> schema) {
        Map<String, Type> map = new HashMap<>();
        Map<Name, String> names = namingPolicy.getNames(schema);
        Map<Name, String> simpleNames = namingPolicy.getSimpleNames(schema);

        Class<?> targetGrain = getGrainClass(schema); // loadOrCreateClass(names.get(Name.grain), AbstractGrain.class);
        Class<?> targetBuilder = getGrainBuilderClass(schema);  // loadOrCreateClass(names.get(Name.builder), AbstractGrainBuilder.class);

        map.put("targetSchema", schema);
        map.put("targetGrain", targetGrain);
        map.put("targetBuilder", targetBuilder);

        ClassHandle targetFactory = loadOrCreateClass(names.get(Name.factory), Enum.class);
        ClassHandle targetGrainImpl =
            loadOrCreateNested(names.get(Name.grainImpl), simpleNames.get(Name.grainImpl), targetFactory);
        ClassHandle targetGrainProxy =
            loadOrCreateNested(names.get(Name.grainProxy), simpleNames.get(Name.grainProxy), targetFactory);
        ClassHandle targetBuilderImpl =
            loadOrCreateNested(names.get(Name.builderImpl), simpleNames.get(Name.builderImpl), targetFactory);

        // All types are loaded/created, so we can now "freeze" them into proper Java Class objects.
        map.put("targetFactory", targetFactory.toClass());
        map.put("targetGrainImpl", targetGrainImpl.toClass());
        map.put("targetGrainProxy", targetGrainProxy.toClass());
        map.put("targetBuilderImpl", targetBuilderImpl.toClass());

        return map;
    }

    /**
     * Translates the specified class to its immutable analog.
     *
     * @throws NullPointerException if clazz is null.
     * @throws IllegalArgumentException if an immutable representation cannot be determined.
     */
    synchronized Class<?> immutify(Class<?> clazz) {
        // First, use the TypePolicy to get the immutable representation of the class.
        Class<?> result = ObjectTools.coalesce(typePolicy.asImmutableType(clazz), Objects.requireNonNull(clazz));

        // Next, map a GrainSchema to its associated Grain implementation. This may require dynamic class construction
        // if the Grain implementation does not yet exist (because we haven't generated it yet).
        if (result.isAnnotationPresent(GrainSchema.class)) {
            result = getGrainClass(result); // loadOrCreateClass(namingPolicy.getName(result, Name.grain), AbstractGrain.class).toClass();
        }

        // Finally, the TypePolicy must agree that the resulting type is immutable.
        if (!typePolicy.isImmutableType(result)) {
            if (clazz == result) {
                throw new IllegalArgumentException(String.format("Do not know how to immutify: %s", result));
            }
            else {
                throw new IllegalArgumentException(
                    String.format("Do not know how to immutify: %s, mapped from: %s", result, clazz));
            }
        }

        return result;
    }
}
