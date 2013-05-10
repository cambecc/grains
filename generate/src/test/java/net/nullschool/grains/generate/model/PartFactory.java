package net.nullschool.grains.generate.model;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.Generated;
import net.nullschool.collect.ConstMap;
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
import net.nullschool.reflect.DefaultImmutabilityPolicy;
import net.nullschool.reflect.ImmutabilityPolicy;
import net.nullschool.util.MemoizedHashCode;

/**
 * Factory for constructing Grain instances of Compound.Part.
 */
// @Generated("net.nullschool.grains.generate.GrainGenerator")
public enum PartFactory implements GrainFactory {
    INSTANCE;

    private static final ImmutabilityPolicy $POLICY = DefaultImmutabilityPolicy.INSTANCE;

    private static final ConstMap<String, GrainProperty> $PROPERTIES = GrainTools.asPropertyMap(
        new SimpleGrainProperty("make", int.class),
        new SimpleGrainProperty("model", int.class));

    private static final String[] $KEYS = $PROPERTIES.keySet().toArray(new String[2]);
    private static final PartGrain $DEFAULT = builder().build();
    public static PartGrain DEFAULT() { return $DEFAULT; }
    public static PartBuilder builder() { return new PartBuilderImpl(); }

    public ConstMap<String, GrainProperty> getBasisProperties() { return $PROPERTIES; }
    public PartGrain getDefault() { return DEFAULT(); }
    public PartBuilder newBuilder() { return builder(); }
    public String toString() { return getClass().getName(); }

    /**
     * Code generated implementation of PartGrain.
     */
    private static final class PartGrainImpl
        extends AbstractGrain
        implements PartGrain, MemoizedHashCode, Serializable {

        private final int make;
        private final int model;

        private final ConstMap<String, Object> $extensions;
        private transient volatile int $hashCode;

        private PartGrainImpl(
            int make, int model, 
            ConstMap<String, Object> $extensions) {

            this.make = make;
            this.model = model;
            this.$extensions = $extensions;
        }

        public int size() { return 2 + $extensions.size(); }

        public MapIterator<String, Object> iterator() {
            return IteratorTools.chainMapIterators(new BasisIter($KEYS), $extensions.iterator());
        }

        public int getMake() { return make; }
        public PartGrain withMake(int make) {
            return new PartGrainImpl(
                make, model, 
                $extensions);
        }

        public int getModel() { return model; }
        public PartGrain withModel(int model) {
            return new PartGrainImpl(
                make, model, 
                $extensions);
        }

        public Object get(Object $key) {
            switch ((String)$key) {
                case "make": return getMake();
                case "model": return getModel();
                default: return $extensions.get($key);
            }
        }

        private PartGrain with(String $key, Object $value, boolean $dissoc) {
            switch ($key) {
                case "make": return withMake($value == null ? 0 : (int)$value);
                case "model": return withModel($value == null ? 0 : (int)$value);
            }
            ConstMap<String, Object> $newExtensions =
                $dissoc ? $extensions.without($key) : $extensions.with($key, $value);
            if ($newExtensions == $extensions) {
                return this;
            }
            return new PartGrainImpl(
                make, model, 
                $newExtensions);
        }

        public PartGrain with(String $key, Object $value) {
            return with($key, $value, false);
        }

        public PartGrain withAll(Map<? extends String, ?> $map) {
            return $map.isEmpty() ? this : MapTools.putAll(builder(), $map).build();
        }

        public PartGrain without(Object $key) {
            return with((String)$key, null, true);
        }

        public PartGrain withoutAll(Collection<?> $keys) {
            return $keys.isEmpty() ? this : MapTools.removeAll(builder(), $keys).build();
        }

        public PartBuilder builder() {
            PartBuilderImpl $builder = new PartBuilderImpl();
            $builder.make = this.make;
            $builder.model = this.model;
            $builder.$extensions.putAll(this.$extensions);
            return $builder;
        }

        public ConstMap<String, Object> extensions() {
            return $extensions;
        }

        public int hashCode() {
            return $hashCode != 0 ? $hashCode : ($hashCode = (super.hashCode() + $extensions.hashCode()));
        }

        private Object writeReplace() { return new PartGrainProxy().setPayload(this); }
        private void readObject(ObjectInputStream in) throws InvalidObjectException {
            throw new InvalidObjectException("proxy expected");
        }
    }

    /**
     * Code generated serialization proxy for serializing instances of PartGrainImpl.
     */
    private static final class PartGrainProxy extends AbstractGrainProxy {
        private static final long serialVersionUID = 1;
        protected PartBuilder newBuilder() { return PartFactory.INSTANCE.newBuilder(); }
    }

    /**
     * Code generated implementation of PartBuilder.
     */
    private static final class PartBuilderImpl
        extends AbstractGrainBuilder
        implements PartBuilder {

        private int make;
        private int model;

        private final TreeMap<String, Object> $extensions = new TreeMap<>();

        public int size() { return 2 + $extensions.size(); }

        public MapIterator<String, Object> iterator() {
            return IteratorTools.chainMapIterators(new BasisIter($KEYS), IteratorTools.newMapIterator($extensions));
        }

        public int getMake() { return make; }
        public PartBuilder setMake(int make) {
            this.make = make;
            return this;
        }

        public int getModel() { return model; }
        public PartBuilder setModel(int model) {
            this.model = model;
            return this;
        }

        public Object get(Object $key) {
            switch ((String)$key) {
                case "make": return getMake();
                case "model": return getModel();
                default: return $extensions.get($key);
            }
        }

        private Object put(String $key, Object $value, boolean $dissoc) {
            Object $original;
            switch ($key) {
                case "make":
                    $original = getMake();
                    setMake($value == null ? 0 : (int)$value);
                    return $original;
                case "model":
                    $original = getModel();
                    setModel($value == null ? 0 : (int)$value);
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

        public PartGrain build() {
            return new PartGrainImpl(
                make, model, 
                BasicConstMap.asMap($extensions));
        }
    }
}
