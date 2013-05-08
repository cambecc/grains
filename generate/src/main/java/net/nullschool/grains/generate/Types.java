package net.nullschool.grains.generate;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.SignatureAttribute;
import net.nullschool.collect.*;
import net.nullschool.collect.basic.BasicConstMap;
import net.nullschool.collect.basic.BasicConstSet;
import net.nullschool.grains.*;
import net.nullschool.reflect.*;
import net.nullschool.util.*;

import javax.annotation.Generated;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.*;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.*;

import static javassist.bytecode.SignatureAttribute.*;
import static net.nullschool.reflect.TypeTools.*;


/**
 * 2013-02-19<p/>
 *
 * @author Cameron Beccario
 */
final class Types {

    private final Configuration config;

    Types(Configuration config) {
        this.config = config;
    }

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
        cast                        (Cast.class),
        casts                       (Casts.class),
        iteratorTools               (IteratorTools.class),
        mapTools                    (MapTools.class),
        basicConstSet               (BasicConstSet.class),
        basicConstMap               (BasicConstMap.class),
        typeToken                   (TypeToken.class),
        memoizedHashCode            (MemoizedHashCode.class),
        grainTools                  (GrainTools.class),
        grainProperty               (GrainProperty.class),
        simpleGrainProperty         (SimpleGrainProperty.class),

        iterableMap                 (new TypeToken<IterableMap<String, Object>>(){}.asType()),
        abstractIterableMap         (new TypeToken<AbstractIterableMap<String, Object>>(){}.asType()),
        constMap                    (new TypeToken<ConstMap<String, Object>>(){}.asType()),
        basisPropertyMap            (new TypeToken<ConstMap<String, GrainProperty>>(){}.asType()),
        treeMap                     (new TypeToken<TreeMap<String, Object>>(){}.asType()),
        entry                       (new TypeToken<Map.Entry<String, Object>>(){}.asType()),
        mapIterator                 (new TypeToken<MapIterator<String, Object>>(){}.asType()),
        mapStringObjectWildcards    (new TypeToken<Map<? extends String, ?>>(){}.asType()),
        collectionWildcard          (new TypeToken<Collection<?>>(){}.asType()),
        ;

        private final Type handle;

        WellKnownType(Type handle) {
            this.handle = handle;
        }
    }

    private static Map<String, TypeSymbol> wellKnownTypes(TypePrinterFactory factory) {
        Map<String, TypeSymbol> map = new HashMap<>();
        for (WellKnownType wkt : WellKnownType.values()) {
            map.put(wkt.name(), new TypeSymbol(wkt.handle, factory));
        }
        return map;
    }

    static String targetPackage(Class<?> schema) {
        GrainSchema annotation = schema.getAnnotation(GrainSchema.class);
        String result = annotation != null ? annotation.targetPackage() : "";
        if (result.isEmpty()) {
            result = schema.getPackage().getName();
        }
        return result;
    }

    private static Class<?> findClass(String name) {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(name);
        }
        catch (ClassNotFoundException e) {
            return null;
        }
    }

    private static Class<?> findClass(Class<?> owner, String nestedName) {
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

    private static CtClass createClass(String name) throws CannotCompileException {
        ClassPool pool = ClassPool.getDefault();
        return pool.makeClass(name);
    }

    private synchronized Class<?> findOrCreateClass(String name) {
        Class<?> result = findClass(name);
        if (result == null) {
            try {
                result = createClass(name).toClass();
            }
            catch (CannotCompileException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    private synchronized Class<?> findOrCreateClass(String name, Class<?> inherits) {
        Class<?> result = findClass(name);
        if (result == null) {
            try {
                CtClass newClass = createClass(name);
                CtClass inheritsClass = ClassPool.getDefault().get(inherits.getName());
                if (inheritsClass.getGenericSignature() != null) {
                    ClassSignature inheritSig =
                        toClassSignature(inheritsClass.getGenericSignature());
                    TypeParameter[] params = inheritSig.getParameters();
                    TypeArgument[] args = new TypeArgument[params.length];
                    for (int i = 0; i < params.length; i++) {
                        TypeParameter tp = params[i];
                        args[i] = new TypeArgument(new SignatureAttribute.TypeVariable(tp.getName()));
                    }
                    ClassSignature cs =
                        new ClassSignature(
                            inheritSig.getParameters(),
                            new ClassType(inherits.getName(), args),
                            null);
                    newClass.setGenericSignature(cs.encode());
                }
                return newClass.toClass();
            }
            catch (CannotCompileException | NotFoundException | BadBytecode e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    private Map<String, TypeSymbol> targetTypes(
        Class<?> schema,
        TypePrinterFactory factory) throws CannotCompileException {

        Map<String, TypeSymbol> map = new HashMap<>();
        map.put("schema", new TypeSymbol(schema, factory));

        String schemaName = schema.getSimpleName();
        String prefix = targetPackage(schema) + '.' + schemaName;

        Class<?> targetGrain = findOrCreateClass(prefix + "Grain");
        map.put("targetGrain", new TypeSymbol(targetGrain, factory));

        Class<?> targetBuilder = findOrCreateClass(prefix + "Builder");
        map.put("targetBuilder", new TypeSymbol(targetBuilder, factory));

        Class<?> targetFactory = findClass(prefix + "Factory");
        Class<?> targetGrainImpl = findClass(targetFactory, schemaName + "GrainImpl");
        Class<?> targetGrainProxy = findClass(targetFactory, schemaName + "GrainProxy");
        Class<?> targetBuilderImpl = findClass(targetFactory, schemaName + "BuilderImpl");

        if (targetFactory == null) {
            CtClass targetFactoryClass = createClass(prefix + "Factory");
            CtClass targetGrainImplClass = targetFactoryClass.makeNestedClass(schemaName + "GrainImpl", true);
            CtClass targetGrainProxyClass = targetFactoryClass.makeNestedClass(schemaName + "GrainProxy", true);
            CtClass targetBuilderImplClass = targetFactoryClass.makeNestedClass(schemaName + "BuilderImpl", true);

            targetFactory = targetFactoryClass.toClass();
            targetGrainImpl = targetGrainImplClass.toClass();
            targetGrainProxy =  targetGrainProxyClass.toClass();
            targetBuilderImpl = targetBuilderImplClass.toClass();
        }

        map.put("targetFactory", new TypeSymbol(targetFactory, factory));
        map.put("targetGrainImpl", new TypeSymbol(targetGrainImpl, factory));
        map.put("targetGrainProxy", new TypeSymbol(targetGrainProxy, factory));
        map.put("targetBuilderImpl", new TypeSymbol(targetBuilderImpl, factory));
//        map.put(
//            "targetGrainFactoryRef",
//            new TypeSymbol(
//                new LateParameterizedType(GrainFactory.Ref.class, GrainFactory.class, targetFactory),
//                factory));

        return map;
    }

    public synchronized Map<String, TypeSymbol> types(Class<?> schema, TypePrinterFactory factory) {
        Map<String, TypeSymbol> map = new HashMap<>();
        map.putAll(wellKnownTypes(factory));
        try {
            map.putAll(targetTypes(schema, factory));
        }
        catch (CannotCompileException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    public Map<String, TypeDecl> targetDecls(Class<?> schema) {
        Map<String, TypeDecl> map = new HashMap<>();
        map.put("targetGrain", new TypeDecl(schema.getSimpleName() + "Grain"));
        map.put("targetGrainImpl", new TypeDecl(schema.getSimpleName() + "GrainImpl"));
        map.put("targetGrainProxy", new TypeDecl(schema.getSimpleName() + "GrainProxy"));
        map.put("targetBuilder", new TypeDecl(schema.getSimpleName() + "Builder"));
        map.put("targetBuilderImpl", new TypeDecl(schema.getSimpleName() + "BuilderImpl"));
        map.put("targetFactory", new TypeDecl(schema.getSimpleName() + "Factory"));
        return map;
    }

    interface Transform {
        Class<?> invoke(Class<?> clazz);
    }

    static class Identity implements Transform {
        @Override
        public Class<?> invoke(Class<?> clazz) {
            if (clazz.isPrimitive()) {
                return clazz;
            }
            for (Class<?> base : immutableTypes) {
                if (base.isAssignableFrom(clazz)) {
                    return clazz;
                }
            }
            return null;
        }
    }

    class AsGrain implements Transform {
        @Override
        public Class<?> invoke(Class<?> clazz) {
            if (clazz.getAnnotation(GrainSchema.class) != null) {
                // UNDONE: figure out why Sample build needs to create classes here on clean build.
                return findOrCreateClass(targetPackage(clazz) + '.' + clazz.getSimpleName() + "Grain");
            }
            return null;
        }
    }

    class CollectionsTransforms implements Transform {
        @Override
        public Class<?> invoke(Class<?> clazz) {
            if (clazz == Map.class) {
                return findOrCreateClass(config.getMapClass(), Map.class);
            }
            if (clazz == Collection.class) {
                return findOrCreateClass(config.getCollectionClass(), Collection.class);
            }
            if (clazz == Set.class) {
                return findOrCreateClass(config.getSetClass(), Set.class);
            }
            if (clazz == List.class) {
                return findOrCreateClass(config.getListClass(), List.class);
            }
            return null;
        }
    }

    private final List<Transform> transforms = Arrays.asList(
        new Identity(),
        new AsGrain(),
        new CollectionsTransforms());

    private static final Set<Class<?>> immutableTypes = new HashSet<>(Arrays.asList(
        Boolean.class,
        Byte.class,
        Short.class,
        Integer.class,
        Long.class,
        BigInteger.class,
        BigDecimal.class,
        Float.class,
        Double.class,
        Character.class,
        String.class,
        Void.class,
        UUID.class,
        URI.class,
        Currency.class,
        Enum.class,             // UNDONE: should Enum itself be considered immutable?
        ConstCollection.class,
        ConstMap.class));

    private class Immutify extends AbstractTypeOperator<Type> {

        @Override public Class<?> invoke(Class<?> clazz) {
            for (Transform t : transforms) {
                Class<?> result = t.invoke(clazz);
                if (result != null) {
                    return result;
                }
            }
            throw new IllegalArgumentException("do not know how to immutify: " + clazz);
        }

        @Override public Type invoke(ParameterizedType pt) {
            Class<?> immutableRawType = invoke(TypeTools.erase(pt.getRawType()));
            return new LateParameterizedType(
                immutableRawType,
                immutableRawType.isMemberClass() ? immutify(pt.getOwnerType()) : pt.getOwnerType(),
                apply(this, pt.getActualTypeArguments()));
        }

        @Override public Type invoke(GenericArrayType gat) {
            return new LateGenericArrayType(invoke(gat.getGenericComponentType()));
        }

        @Override public Type invoke(WildcardType wt) {
            return wt.getLowerBounds().length > 0 ?
                new LateWildcardType("? super", apply(this, wt.getLowerBounds())) :
                new LateWildcardType("? extends", apply(this, wt.getUpperBounds()));
        }

        @Override public Type invoke(TypeVariable<?> tv) {
            return invoke(TypeTools.erase(tv));  // not sure if this makes sense
        }
    }

    public Type immutify(Type type) {
        Type instantiated = new Cook().invoke(new DeWildcard().invoke(type));
        try {
            return new Immutify().invoke(instantiated);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                String.format("Failed to immutify: %s (interpreted as: %s)", type, instantiated), e);
        }
    }
}
