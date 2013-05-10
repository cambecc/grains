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
import net.nullschool.collect.basic.BasicConstMap;
import net.nullschool.grains.AbstractGrain;
import net.nullschool.grains.AbstractGrainBuilder;
import net.nullschool.grains.AbstractGrainProxy;
import net.nullschool.grains.GrainFactory;
import net.nullschool.grains.GrainProperty;
import net.nullschool.grains.GrainTools;
import net.nullschool.grains.SimpleGrainProperty;
import net.nullschool.reflect.CastFunction;
import net.nullschool.reflect.DefaultImmutabilityStrategy;
import net.nullschool.reflect.ImmutabilityStrategy;
import net.nullschool.reflect.TypeToken;
import net.nullschool.util.MemoizedHashCode;

/**
 * Factory for constructing Grain instances of Compound.
 */
// @Generated("net.nullschool.grains.generate.GrainGenerator")
public enum CompoundFactory implements GrainFactory {
    INSTANCE;

    private static final ImmutabilityStrategy $STRATEGY = DefaultImmutabilityStrategy.INSTANCE;

    private static final TypeToken<ConstMap<String, ConstSet<PartGrain>>> $0 =
        new TypeToken<ConstMap<String, ConstSet<PartGrain>>>(){};
    private static final TypeToken<ConstList<PartGrain>> $1 =
        new TypeToken<ConstList<PartGrain>>(){};
    private static final TypeToken<ConstSet<PartGrain>> $2 =
        new TypeToken<ConstSet<PartGrain>>(){};

    private static final ConstMap<String, GrainProperty> $PROPERTIES = GrainTools.asPropertyMap(
        new SimpleGrainProperty("firstPart", PartGrain.class),
        new SimpleGrainProperty("partGroups", $0.asType()),
        new SimpleGrainProperty("remainingParts", $1.asType()),
        new SimpleGrainProperty("secondPart", PartGrain.class),
        new SimpleGrainProperty("uniqueParts", $2.asType()),
        new SimpleGrainProperty("unusedParts", $1.asType()));

    private static final String[] $KEYS = $PROPERTIES.keySet().toArray(new String[6]);
    private static final CompoundGrain $DEFAULT = builder().build();
    public static CompoundGrain DEFAULT() { return $DEFAULT; }
    public static CompoundBuilder builder() { return new CompoundBuilderImpl(); }

    public ConstMap<String, GrainProperty> getBasisProperties() { return $PROPERTIES; }
    public CompoundGrain getDefault() { return DEFAULT(); }
    public CompoundBuilder newBuilder() { return builder(); }
    public String toString() { return getClass().getName(); }

    private static final CastFunction<ConstMap<String, ConstSet<PartGrain>>> $0Cast =
        $STRATEGY.newCastFunction($0);
    private static final CastFunction<ConstList<PartGrain>> $1Cast =
        $STRATEGY.newCastFunction($1);
    private static final CastFunction<ConstSet<PartGrain>> $2Cast =
        $STRATEGY.newCastFunction($2);

    /**
     * Code generated implementation of CompoundGrain.
     */
    private static final class CompoundGrainImpl
        extends AbstractGrain
        implements CompoundGrain, MemoizedHashCode, Serializable {

        private final PartGrain firstPart;
        private final ConstMap<String, ConstSet<PartGrain>> partGroups;
        private final ConstList<PartGrain> remainingParts;
        private final PartGrain secondPart;
        private final ConstSet<PartGrain> uniqueParts;
        private final ConstList<PartGrain> unusedParts;

        private final ConstMap<String, Object> $extensions;
        private transient volatile int $hashCode;

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
                case "partGroups": return withPartGroups($0Cast.apply($value));
                case "remainingParts": return withRemainingParts($1Cast.apply($value));
                case "secondPart": return withSecondPart((PartGrain)$value);
                case "uniqueParts": return withUniqueParts($2Cast.apply($value));
                case "unusedParts": return withUnusedParts($1Cast.apply($value));
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
            return $map.isEmpty() ? this : MapTools.putAll(builder(), $map).build();
        }

        public CompoundGrain without(Object $key) {
            return with((String)$key, null, true);
        }

        public CompoundGrain withoutAll(Collection<?> $keys) {
            return $keys.isEmpty() ? this : MapTools.removeAll(builder(), $keys).build();
        }

        public CompoundBuilder builder() {
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

        public int hashCode() {
            return $hashCode != 0 ? $hashCode : ($hashCode = (super.hashCode() + $extensions.hashCode()));
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
        protected CompoundBuilder newBuilder() { return CompoundFactory.INSTANCE.newBuilder(); }
    }

    /**
     * Code generated implementation of CompoundBuilder.
     */
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
                    setPartGroups($0Cast.apply($value));
                    return $original;
                case "remainingParts":
                    $original = getRemainingParts();
                    setRemainingParts($1Cast.apply($value));
                    return $original;
                case "secondPart":
                    $original = getSecondPart();
                    setSecondPart((PartGrain)$value);
                    return $original;
                case "uniqueParts":
                    $original = getUniqueParts();
                    setUniqueParts($2Cast.apply($value));
                    return $original;
                case "unusedParts":
                    $original = getUnusedParts();
                    setUnusedParts($1Cast.apply($value));
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
                BasicConstMap.asMap($extensions));
        }
    }
}
