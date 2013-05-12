package net.nullschool.grains.generate;

import javassist.*;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.SignatureAttribute.*;
import net.nullschool.collect.*;
import net.nullschool.collect.basic.BasicConstMap;
import net.nullschool.collect.basic.BasicConstSet;
import net.nullschool.grains.*;
import net.nullschool.grains.generate.NamingPolicy.Name;
import net.nullschool.reflect.*;
import net.nullschool.util.MemoizedHashCode;
import net.nullschool.util.ObjectTools;

import javax.annotation.Generated;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import static javassist.bytecode.SignatureAttribute.*;


/**
 * 2013-05-09<p/>
 *
 * Organizes types for code generation. Dynamically constructs new types as needed (using the javassist library),
 * transforms classes into their immutable analogs by delegating to {@link ConstPolicy}, and provides a location to
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
        basicConstMap               (BasicConstMap.class),
        basicConstSet               (BasicConstSet.class),
        castFunction                (CastFunction.class),
        collections                 (Collections.class),
        constPolicy                 (ConstPolicy.class),
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
        serializable                (Serializable.class),
        set                         (Set.class),
        simpleGrainProperty         (SimpleGrainProperty.class),
        string                      (String.class),
        type                        (Type.class),
        typeToken                   (TypeToken.class),

        abstractIterableMap         (new TypeToken<AbstractIterableMap<String, Object>>(){}),
        basisPropertyMap            (new TypeToken<ConstMap<String, GrainProperty>>(){}),
        collectionWildcard          (new TypeToken<Collection<?>>(){}),
        constMap                    (new TypeToken<ConstMap<String, Object>>(){}),
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
    private final ConstPolicy constPolicy;
    private final ClassPool classPool;  // Pool used for dynamic construction of grain classes.

    TypeTable(NamingPolicy namingPolicy, ConstPolicy constPolicy) {
        this.namingPolicy = Objects.requireNonNull(namingPolicy);
        this.constPolicy = Objects.requireNonNull(constPolicy);
        this.classPool = new ClassPool();
        this.classPool.appendClassPath(new LoaderClassPath(deriveClassLoader()));
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

    private static Class<?> loadClass(String name) {
        try {
            return deriveClassLoader().loadClass(name);
        }
        catch (ClassNotFoundException e) {
            return null;
        }
    }

    private static void setGenericSignature(CtClass newClass, CtClass baseClass, ClassSignature baseSignature) {
        TypeParameter[] params = baseSignature.getParameters();
        TypeArgument[] args = new TypeArgument[params.length];
        for (int i = 0; i < params.length; i++) {
            TypeParameter tp = params[i];
            args[i] = new TypeArgument(new TypeVariable(tp.getName()));
        }
        ClassSignature cs =
            new ClassSignature(
                baseSignature.getParameters(),
                new ClassType(baseClass.getName(), args),
                null);
        newClass.setGenericSignature(cs.encode());
    }

    private static final TypeParameter[] NO_PARAMS = new TypeParameter[0];

    private Class<?> createClass(String name, Class<?> inherits) {
        try {
            CtClass newClass = classPool.makeClass(name);
            CtClass baseClass = classPool.get(inherits.getName());
            ClassSignature baseSignature =
                baseClass.getGenericSignature() != null ? toClassSignature(baseClass.getGenericSignature()) : null;
            TypeParameter[] params = baseSignature != null ? baseSignature.getParameters() : NO_PARAMS;
            if (params.length > 0) {
                // The base class is generic. Create equivalent type parameters on the new class.
                setGenericSignature(newClass, baseClass, baseSignature);
            }
            else {
                newClass.setSuperclass(baseClass);
            }
            return newClass.toClass();
        }
        catch (CannotCompileException | NotFoundException | BadBytecode e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Attempts to load the class with the specified name. If the class cannot be found, then a new class is
     * dynamically constructed. This constructed class will extend the class {@code inherits}.
     */
    private Class<?> loadOrCreateClass(String name, Class<?> inherits) {
        Class<?> result = loadClass(name);
        return result != null ? result : createClass(name, inherits);
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

        map.put("targetSchema", schema);

        Class<?> targetGrain = loadOrCreateClass(names.get(Name.grain), AbstractGrain.class);
        map.put("targetGrain", targetGrain);

        Class<?> targetBuilder = loadOrCreateClass(names.get(Name.builder), AbstractGrainBuilder.class);
        map.put("targetBuilder", targetBuilder);

        Class<?> targetFactory = loadClass(names.get(Name.factory));
        Class<?> targetGrainImpl = loadClass(names.get(Name.grainImpl));
        Class<?> targetGrainProxy = loadClass(names.get(Name.grainProxy));
        Class<?> targetBuilderImpl = loadClass(names.get(Name.builderImpl));

        if (targetFactory == null) {
            CtClass ctFactory = classPool.makeClass(names.get(Name.factory));
            CtClass ctGrainImpl = ctFactory.makeNestedClass(simpleNames.get(Name.grainImpl), true);
            CtClass ctGrainProxy = ctFactory.makeNestedClass(simpleNames.get(Name.grainProxy), true);
            CtClass ctBuilderImpl = ctFactory.makeNestedClass(simpleNames.get(Name.builderImpl), true);

            try {
                targetFactory = ctFactory.toClass();  // Must build enclosing class before enclosed classes.
                targetGrainImpl = ctGrainImpl.toClass();
                targetGrainProxy =  ctGrainProxy.toClass();
                targetBuilderImpl = ctBuilderImpl.toClass();
            }
            catch (CannotCompileException e) {
                throw new RuntimeException(e);
            }
        }

        map.put("targetFactory", targetFactory);
        map.put("targetGrainImpl", targetGrainImpl);
        map.put("targetGrainProxy", targetGrainProxy);
        map.put("targetBuilderImpl", targetBuilderImpl);

        return map;
    }

    /**
     * Translates the specified class to its immutable analog.
     *
     * @throws NullPointerException if clazz is null.
     * @throws IllegalArgumentException if an immutable representation cannot be determined.
     */
    synchronized Class<?> immutify(Class<?> clazz) {
        // First, use the ConstPolicy to get the immutable representation of the class.
        Class<?> result = ObjectTools.coalesce(constPolicy.translate(clazz), Objects.requireNonNull(clazz));

        // Next, map a GrainSchema to its associated Grain implementation. This may require dynamic class construction
        // if the Grain implementation does not yet exist (because we haven't generated it yet).
        if (result.getAnnotation(GrainSchema.class) != null) {
            result = loadOrCreateClass(namingPolicy.getName(result, Name.grain), AbstractGrain.class);
        }

        // Finally, the ConstPolicy must agree that the resulting type is immutable.
        if (!constPolicy.test(result)) {
            throw new IllegalArgumentException("do not know how to immutify: " + clazz + " translated as: " + result);
        }

        return result;
    }
}
