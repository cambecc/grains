package net.nullschool.grains.generate;

import javassist.*;
import javassist.bytecode.BadBytecode;
import net.nullschool.collect.*;
import net.nullschool.collect.basic.BasicConstMap;
import net.nullschool.collect.basic.BasicConstSet;
import net.nullschool.grains.*;
import net.nullschool.reflect.*;
import net.nullschool.util.MemoizedHashCode;
import net.nullschool.util.ObjectTools;
import javassist.bytecode.SignatureAttribute.*;
import net.nullschool.grains.generate.NamingPolicy.Kind;

import javax.annotation.Generated;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import static javassist.bytecode.SignatureAttribute.*;


/**
 * 2013-05-09<p/>
 *
 * @author Cameron Beccario
 */
final class TypeTable {

    private enum WellKnownType {
        object                      (Object.class),
        string                      (String.class),
        type                        (Type.class),
        set                         (Set.class),
        linkedHashSet               (LinkedHashSet.class),
        collections                 (Collections.class),
        arrays                      (Arrays.class),
        serializable                (Serializable.class),
        objectInputStream           (ObjectInputStream.class),
        indexOutOfBoundsException   (IndexOutOfBoundsException.class),
        noSuchElementException      (NoSuchElementException.class),
        illegalStateException       (IllegalStateException.class),
        invalidObjectException      (InvalidObjectException.class),
        generated                   (Generated.class),

        grain                       (Grain.class),
        abstractGrain               (AbstractGrain.class),
        grainBuilder                (GrainBuilder.class),
        abstractGrainBuilder        (AbstractGrainBuilder.class),
        grainFactory                (GrainFactory.class),
        grainFactoryRef             (GrainFactoryRef.class),
        abstractGrainProxy          (AbstractGrainProxy.class),
        grainGenerator              (GrainGenerator.class),
        castFunction                (CastFunction.class),
        iteratorTools               (IteratorTools.class),
        mapTools                    (MapTools.class),
        basicConstSet               (BasicConstSet.class),
        basicConstMap               (BasicConstMap.class),
        typeToken                   (TypeToken.class),
        memoizedHashCode            (MemoizedHashCode.class),
        grainTools                  (GrainTools.class),
        grainProperty               (GrainProperty.class),
        simpleGrainProperty         (SimpleGrainProperty.class),
        immutabilityStrategy        (ImmutabilityStrategy.class),

        iterableMap                 (new TypeToken<IterableMap<String, Object>>(){}),
        abstractIterableMap         (new TypeToken<AbstractIterableMap<String, Object>>(){}),
        constMap                    (new TypeToken<ConstMap<String, Object>>(){}),
        basisPropertyMap            (new TypeToken<ConstMap<String, GrainProperty>>(){}),
        treeMap                     (new TypeToken<TreeMap<String, Object>>(){}),
        entry                       (new TypeToken<Map.Entry<String, Object>>(){}),
        mapIterator                 (new TypeToken<MapIterator<String, Object>>(){}),
        mapStringObjectWildcards    (new TypeToken<Map<? extends String, ?>>(){}),
        collectionWildcard          (new TypeToken<Collection<?>>(){}),
        ;


        private final Type _type;

        WellKnownType(Class<?> clazz) {
            this._type = clazz;
        }

        WellKnownType(TypeToken<?> token) {
            this._type = token.asType();
        }
    }

    private final NamingPolicy namingPolicy;
    private final ImmutabilityStrategy strategy;
    private final ClassPool classPool;

    TypeTable(NamingPolicy namingPolicy, ImmutabilityStrategy strategy) {
        this.namingPolicy = Objects.requireNonNull(namingPolicy);
        this.strategy = Objects.requireNonNull(strategy);
        this.classPool = new ClassPool();
        // UNDONE: use TypeTable.class.getClassLoader here? Then would be same CL as above WKT enum.
        this.classPool.appendClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
    }

    public Map<String, Type> wellKnownTypes() {
        Map<String, Type> map = new HashMap<>();
        for (WellKnownType wkt : WellKnownType.values()) {
            map.put(wkt.name(), wkt._type);
        }
        return Collections.unmodifiableMap(map);
    }

    private static Class<?> loadClass(String name) {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(name);
        }
        catch (ClassNotFoundException e) {
            return null;
        }
    }

    private static Class<?> loadClass(Class<?> owner, String nestedName) {
        if (owner == null) {
            return null;
        }
        for (Class<?> declared : owner.getDeclaredClasses()) {
            if (declared.getSimpleName().equals(nestedName)) {
                return declared;
            }
        }
        return null;
    }

    private Class<?> loadOrCreateClass(String name, Class<?> inherits) {
        Class<?> result = loadClass(name);
        if (result == null) {
            try {
                CtClass newClass = classPool.makeClass(name);
                CtClass inheritsClass = classPool.get(inherits.getName());
                if (inheritsClass.getGenericSignature() != null) {
                    ClassSignature inheritSig =
                        toClassSignature(inheritsClass.getGenericSignature());
                    TypeParameter[] params = inheritSig.getParameters();
                    if (params.length > 0) {
                        TypeArgument[] args = new TypeArgument[params.length];
                        for (int i = 0; i < params.length; i++) {
                            TypeParameter tp = params[i];
                            args[i] = new TypeArgument(new TypeVariable(tp.getName()));
                        }
                        ClassSignature cs =
                            new ClassSignature(
                                inheritSig.getParameters(),
                                new ClassType(inherits.getName(), args),
                                null);
                        newClass.setGenericSignature(cs.encode());
                    }
                    else {
                        newClass.setSuperclass(inheritsClass);
                    }
                }
                else {
                    newClass.setSuperclass(inheritsClass);
                }
                return newClass.toClass();
            }
            catch (CannotCompileException | NotFoundException | BadBytecode e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public synchronized Map<String, Type> schemaTypes(Class<?> schema) {
        Map<String, Type> map = new HashMap<>();

        Class<?> targetGrain = loadOrCreateClass(namingPolicy.name(schema, Kind.grain), AbstractGrain.class);
        map.put("targetGrain", targetGrain);

        Class<?> targetBuilder = loadOrCreateClass(namingPolicy.name(schema, Kind.builder), AbstractGrainBuilder.class);
        map.put("targetBuilder", targetBuilder);

        Class<?> targetFactory = loadClass(namingPolicy.name(schema, Kind.factory));
        Class<?> targetGrainImpl = loadClass(namingPolicy.name(schema, Kind.grainImpl)); //loadClass(targetFactory, schemaName + "GrainImpl");
        Class<?> targetGrainProxy = loadClass(namingPolicy.name(schema, Kind.proxy)); //loadClass(targetFactory, schemaName + "GrainProxy");
        Class<?> targetBuilderImpl = loadClass(namingPolicy.name(schema, Kind.builderImpl)); //loadClass(targetFactory, schemaName + "BuilderImpl");

        if (targetFactory == null) {
            String schemaName = schema.getSimpleName();
            CtClass targetFactoryClass = classPool.makeClass(namingPolicy.name(schema, Kind.factory));
            CtClass targetGrainImplClass = targetFactoryClass.makeNestedClass(schemaName + "GrainImpl", true);
            CtClass targetGrainProxyClass = targetFactoryClass.makeNestedClass(schemaName + "GrainProxy", true);
            CtClass targetBuilderImplClass = targetFactoryClass.makeNestedClass(schemaName + "BuilderImpl", true);

            try {
                targetFactory = targetFactoryClass.toClass();
                targetGrainImpl = targetGrainImplClass.toClass();
                targetGrainProxy =  targetGrainProxyClass.toClass();
                targetBuilderImpl = targetBuilderImplClass.toClass();
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

    public synchronized Class<?> immutify(Class<?> clazz) {
        Class<?> result = ObjectTools.coalesce(strategy.translate(clazz), Objects.requireNonNull(clazz));
        if (result.getAnnotation(GrainSchema.class) != null) {
            result = loadOrCreateClass(namingPolicy.name(result, Kind.grain), AbstractGrain.class);
        }
        if (!strategy.test(result)) {
            throw new IllegalArgumentException("do not know how to immutify: " + clazz + " translated as: " + result);
        }
        return result;
    }
}
