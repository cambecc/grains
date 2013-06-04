package net.nullschool.grains.generate.model;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.Generated;
import net.nullschool.collect.ConstList;
import net.nullschool.collect.ConstMap;
import net.nullschool.collect.ConstSet;
import net.nullschool.collect.IteratorTools;
import net.nullschool.collect.MapIterator;
import net.nullschool.collect.MapTools;
import net.nullschool.collect.basic.BasicCollections;
import net.nullschool.grains.AbstractGrain;
import net.nullschool.grains.AbstractGrainBuilder;
import net.nullschool.grains.AbstractGrainProxy;
import net.nullschool.grains.DefaultTypePolicy;
import net.nullschool.grains.GrainFactory;
import net.nullschool.grains.GrainFactoryRef;
import net.nullschool.grains.GrainProperty;
import net.nullschool.grains.GrainTools;
import net.nullschool.grains.SimpleGrainProperty;
import net.nullschool.grains.TypePolicy;
import net.nullschool.reflect.PublicInterfaceRef;
import net.nullschool.reflect.TypeToken;
import net.nullschool.transform.Transform;

/**
 * Factory for constructing Grain instances of Compound.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
public enum CompoundFactory implements GrainFactory {
    INSTANCE;

    private static final TypePolicy $POLICY = DefaultTypePolicy.INSTANCE;

    private static final TypeToken<ConstMap<String, ConstSet<PartGrain>>> $token0 =
        new TypeToken<ConstMap<String, ConstSet<PartGrain>>>(){};
    private static final TypeToken<ConstList<PartGrain>> $token1 =
        new TypeToken<ConstList<PartGrain>>(){};
    private static final TypeToken<ConstSet<PartGrain>> $token2 =
        new TypeToken<ConstSet<PartGrain>>(){};

    private static final ConstMap<String, GrainProperty> $PROPERTIES = GrainTools.asPropertyMap(
        new SimpleGrainProperty("firstPart", PartGrain.class),
        new SimpleGrainProperty("partGroups", $token0.asType()),
        new SimpleGrainProperty("remainingParts", $token1.asType()),
        new SimpleGrainProperty("secondPart", PartGrain.class),
        new SimpleGrainProperty("uniqueParts", $token2.asType()),
        new SimpleGrainProperty("unusedParts", $token1.asType()));

    private static final String[] $KEYS = $PROPERTIES.keySet().toArray(new String[6]);
    private static final CompoundGrain $DEFAULT = newBuilder().build();
    public static CompoundGrain defaultValue() { return $DEFAULT; }
    public static CompoundBuilder newBuilder() { return new CompoundBuilderImpl(); }

    public ConstMap<String, GrainProperty> getBasisProperties() { return $PROPERTIES; }
    public CompoundGrain getDefaultValue() { return defaultValue(); }
    public CompoundBuilder getNewBuilder() { return newBuilder(); }
    public String toString() { return getClass().getName(); }

    private static final Transform<ConstMap<String, ConstSet<PartGrain>>> $transform0 =
        $POLICY.newTransform($token0);
    private static final Transform<ConstList<PartGrain>> $transform1 =
        $POLICY.newTransform($token1);
    private static final Transform<ConstSet<PartGrain>> $transform2 =
        $POLICY.newTransform($token2);

    /**
     * Code generated implementation of CompoundGrain.
     */
    @PublicInterfaceRef(CompoundGrain.class)
    @GrainFactoryRef(CompoundFactory.class)
    private static final class CompoundGrainImpl
        extends AbstractGrain
        implements CompoundGrain, Serializable {

        private final PartGrain firstPart;
        private final ConstMap<String, ConstSet<PartGrain>> partGroups;
        private final ConstList<PartGrain> remainingParts;
        private final PartGrain secondPart;
        private final ConstSet<PartGrain> uniqueParts;
        private final ConstList<PartGrain> unusedParts;

        private final ConstMap<String, Object> $extensions;

        private CompoundGrainImpl(
            PartGrain firstPart, ConstMap<String, ConstSet<PartGrain>> partGroups, ConstList<PartGrain> remainingParts, 
            PartGrain secondPart, ConstSet<PartGrain> uniqueParts, ConstList<PartGrain> unusedParts, 
            ConstMap<String, Object> $extensions) {

            this.firstPart = firstPart;
            this.partGroups = partGroups;
            this.remainingParts = remainingParts;
            this.secondPart = secondPart;
            this.uniqueParts = uniqueParts;
            this.unusedParts = unusedParts;
            this.$extensions = $extensions;
        }

        public int size() { return 6 + $extensions.size(); }

        public MapIterator<String, Object> iterator() {
            return IteratorTools.chainMapIterators(new BasisIter($KEYS), $extensions.iterator());
        }

        public PartGrain getFirstPart() { return firstPart; }
        public CompoundGrain withFirstPart(PartGrain firstPart) {
            return new CompoundGrainImpl(
                firstPart, partGroups, remainingParts, secondPart, uniqueParts, unusedParts, 
                $extensions);
        }

        public ConstMap<String, ConstSet<PartGrain>> getPartGroups() { return partGroups; }
        public CompoundGrain withPartGroups(ConstMap<String, ConstSet<PartGrain>> partGroups) {
            return new CompoundGrainImpl(
                firstPart, partGroups, remainingParts, secondPart, uniqueParts, unusedParts, 
                $extensions);
        }

        public ConstList<PartGrain> getRemainingParts() { return remainingParts; }
        public CompoundGrain withRemainingParts(ConstList<PartGrain> remainingParts) {
            return new CompoundGrainImpl(
                firstPart, partGroups, remainingParts, secondPart, uniqueParts, unusedParts, 
                $extensions);
        }

        public PartGrain getSecondPart() { return secondPart; }
        public CompoundGrain withSecondPart(PartGrain secondPart) {
            return new CompoundGrainImpl(
                firstPart, partGroups, remainingParts, secondPart, uniqueParts, unusedParts, 
                $extensions);
        }

        public ConstSet<PartGrain> getUniqueParts() { return uniqueParts; }
        public CompoundGrain withUniqueParts(ConstSet<PartGrain> uniqueParts) {
            return new CompoundGrainImpl(
                firstPart, partGroups, remainingParts, secondPart, uniqueParts, unusedParts, 
                $extensions);
        }

        public ConstList<PartGrain> getUnusedParts() { return unusedParts; }
        public CompoundGrain withUnusedParts(ConstList<PartGrain> unusedParts) {
            return new CompoundGrainImpl(
                firstPart, partGroups, remainingParts, secondPart, uniqueParts, unusedParts, 
                $extensions);
        }

        public Object get(Object $key) {
            switch ((String)$key) {
                case "firstPart": return getFirstPart();
                case "partGroups": return getPartGroups();
                case "remainingParts": return getRemainingParts();
                case "secondPart": return getSecondPart();
                case "uniqueParts": return getUniqueParts();
                case "unusedParts": return getUnusedParts();
                default: return $extensions.get($key);
            }
        }

        private CompoundGrain with(String $key, Object $value, boolean $dissoc) {
            switch ($key) {
                case "firstPart": return withFirstPart((PartGrain)$value);
                case "partGroups": return withPartGroups($transform0.apply($value));
                case "remainingParts": return withRemainingParts($transform1.apply($value));
                case "secondPart": return withSecondPart((PartGrain)$value);
                case "uniqueParts": return withUniqueParts($transform2.apply($value));
                case "unusedParts": return withUnusedParts($transform1.apply($value));
            }
            ConstMap<String, Object> $newExtensions =
                $dissoc ? $extensions.without($key) : $extensions.with($key, $value);
            if ($newExtensions == $extensions) {
                return this;
            }
            return new CompoundGrainImpl(
                firstPart, partGroups, remainingParts, secondPart, uniqueParts, unusedParts, 
                $newExtensions);
        }

        public CompoundGrain with(String $key, Object $value) {
            return with($key, $value, false);
        }

        public CompoundGrain withAll(Map<? extends String, ?> $map) {
            return $map.isEmpty() ? this : MapTools.putAll(newBuilder(), $map).build();
        }

        public CompoundGrain without(Object $key) {
            return with((String)$key, null, true);
        }

        public CompoundGrain withoutAll(Collection<?> $keys) {
            return $keys.isEmpty() ? this : MapTools.removeAll(newBuilder(), $keys).build();
        }

        public CompoundBuilder newBuilder() {
            CompoundBuilderImpl $builder = new CompoundBuilderImpl();
            $builder.firstPart = this.firstPart;
            $builder.partGroups = this.partGroups;
            $builder.remainingParts = this.remainingParts;
            $builder.secondPart = this.secondPart;
            $builder.uniqueParts = this.uniqueParts;
            $builder.unusedParts = this.unusedParts;
            $builder.$extensions.putAll(this.$extensions);
            return $builder;
        }

        public ConstMap<String, Object> extensions() {
            return $extensions;
        }

        private Object writeReplace() { return new CompoundGrainProxy().setPayload(this); }
        private void readObject(ObjectInputStream in) throws InvalidObjectException {
            throw new InvalidObjectException("proxy expected");
        }
    }

    /**
     * Code generated serialization proxy for serializing instances of CompoundGrainImpl.
     */
    private static final class CompoundGrainProxy extends AbstractGrainProxy {
        private static final long serialVersionUID = 1;
        protected CompoundBuilder newBuilder() { return CompoundFactory.newBuilder(); }
    }

    /**
     * Code generated implementation of CompoundBuilder.
     */
    @PublicInterfaceRef(CompoundBuilder.class)
    @GrainFactoryRef(CompoundFactory.class)
    private static final class CompoundBuilderImpl
        extends AbstractGrainBuilder
        implements CompoundBuilder {

        private PartGrain firstPart;
        private ConstMap<String, ConstSet<PartGrain>> partGroups;
        private ConstList<PartGrain> remainingParts;
        private PartGrain secondPart;
        private ConstSet<PartGrain> uniqueParts;
        private ConstList<PartGrain> unusedParts;

        private final TreeMap<String, Object> $extensions = new TreeMap<>();

        public int size() { return 6 + $extensions.size(); }

        public MapIterator<String, Object> iterator() {
            return IteratorTools.chainMapIterators(new BasisIter($KEYS), IteratorTools.newMapIterator($extensions));
        }

        public PartGrain getFirstPart() { return firstPart; }
        public CompoundBuilder setFirstPart(PartGrain firstPart) {
            this.firstPart = firstPart;
            return this;
        }

        public ConstMap<String, ConstSet<PartGrain>> getPartGroups() { return partGroups; }
        public CompoundBuilder setPartGroups(ConstMap<String, ConstSet<PartGrain>> partGroups) {
            this.partGroups = partGroups;
            return this;
        }

        public ConstList<PartGrain> getRemainingParts() { return remainingParts; }
        public CompoundBuilder setRemainingParts(ConstList<PartGrain> remainingParts) {
            this.remainingParts = remainingParts;
            return this;
        }

        public PartGrain getSecondPart() { return secondPart; }
        public CompoundBuilder setSecondPart(PartGrain secondPart) {
            this.secondPart = secondPart;
            return this;
        }

        public ConstSet<PartGrain> getUniqueParts() { return uniqueParts; }
        public CompoundBuilder setUniqueParts(ConstSet<PartGrain> uniqueParts) {
            this.uniqueParts = uniqueParts;
            return this;
        }

        public ConstList<PartGrain> getUnusedParts() { return unusedParts; }
        public CompoundBuilder setUnusedParts(ConstList<PartGrain> unusedParts) {
            this.unusedParts = unusedParts;
            return this;
        }

        public Object get(Object $key) {
            switch ((String)$key) {
                case "firstPart": return getFirstPart();
                case "partGroups": return getPartGroups();
                case "remainingParts": return getRemainingParts();
                case "secondPart": return getSecondPart();
                case "uniqueParts": return getUniqueParts();
                case "unusedParts": return getUnusedParts();
                default: return $extensions.get($key);
            }
        }

        private Object put(String $key, Object $value, boolean $dissoc) {
            Object $original;
            switch ($key) {
                case "firstPart":
                    $original = getFirstPart();
                    setFirstPart((PartGrain)$value);
                    return $original;
                case "partGroups":
                    $original = getPartGroups();
                    setPartGroups($transform0.apply($value));
                    return $original;
                case "remainingParts":
                    $original = getRemainingParts();
                    setRemainingParts($transform1.apply($value));
                    return $original;
                case "secondPart":
                    $original = getSecondPart();
                    setSecondPart((PartGrain)$value);
                    return $original;
                case "uniqueParts":
                    $original = getUniqueParts();
                    setUniqueParts($transform2.apply($value));
                    return $original;
                case "unusedParts":
                    $original = getUnusedParts();
                    setUnusedParts($transform1.apply($value));
                    return $original;
                default:
                    return $dissoc ? $extensions.remove($key) : $extensions.put($key, $value);
            }
        }

        public Object put(String $key, Object $value) {
            return put($key, $value, false);
        }

        public Object remove(Object $key) {
            return put((String)$key, null, true);
        }

        public CompoundGrain build() {
            return new CompoundGrainImpl(
                firstPart, partGroups, remainingParts, secondPart, uniqueParts, unusedParts, 
                BasicCollections.asMap($extensions));
        }
    }
}
