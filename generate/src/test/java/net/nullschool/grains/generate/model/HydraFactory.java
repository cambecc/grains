package net.nullschool.grains.generate.model;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.Generated;
import net.nullschool.collect.ConstMap;
import net.nullschool.collect.ConstSortedMap;
import net.nullschool.collect.IteratorTools;
import net.nullschool.collect.MapIterator;
import net.nullschool.collect.MapTools;
import net.nullschool.collect.basic.BasicCollections;
import net.nullschool.grains.AbstractGrain;
import net.nullschool.grains.AbstractGrainBuilder;
import net.nullschool.grains.AbstractGrainProxy;
import net.nullschool.grains.GrainFactory;
import net.nullschool.grains.GrainFactoryRef;
import net.nullschool.grains.GrainProperty;
import net.nullschool.grains.GrainTools;
import net.nullschool.grains.SimpleGrainProperty;
import net.nullschool.reflect.PublicInterfaceRef;

/**
 * Factory for constructing Grain instances of Animal.Hydra.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
public enum HydraFactory implements GrainFactory {
    INSTANCE;

    private static final ConstMap<String, GrainProperty> $PROPERTIES = GrainTools.asPropertyMap(
        new SimpleGrainProperty("id", String.class),
        new SimpleGrainProperty("age", int.class),
        new SimpleGrainProperty("legCount", int.class));

    private static final String[] $KEYS = $PROPERTIES.keySet().toArray(new String[3]);
    private static final HydraGrain $DEFAULT = newBuilder().build();
    public static HydraGrain defaultValue() { return $DEFAULT; }
    public static HydraBuilder newBuilder() { return new HydraBuilderImpl(); }

    public ConstMap<String, GrainProperty> getBasisProperties() { return $PROPERTIES; }
    public HydraGrain getDefaultValue() { return defaultValue(); }
    public HydraBuilder getNewBuilder() { return newBuilder(); }
    public String toString() { return getClass().getName(); }

    /**
     * Code generated implementation of HydraGrain.
     */
    @PublicInterfaceRef(HydraGrain.class)
    @GrainFactoryRef(HydraFactory.class)
    private static final class HydraGrainImpl
        extends AbstractGrain
        implements HydraGrain, Serializable {

        private final String id;
        private final int age;
        private final int legCount;

        private final ConstSortedMap<String, Object> $extensions;

        private HydraGrainImpl(
            String id, int age, int legCount, 
            ConstSortedMap<String, Object> $extensions) {

            this.id = id;
            this.age = age;
            this.legCount = legCount;
            this.$extensions = $extensions;
        }

        public int size() { return 3 + $extensions.size(); }

        public MapIterator<String, Object> iterator() {
            return IteratorTools.chainMapIterators(new BasisIter($KEYS), $extensions.iterator());
        }

        public String getId() { return id; }
        public HydraGrain withId(String id) {
            return new HydraGrainImpl(
                id, age, legCount, 
                $extensions);
        }

        public int getAge() { return age; }
        public HydraGrain withAge(int age) {
            return new HydraGrainImpl(
                id, age, legCount, 
                $extensions);
        }

        public int getLegCount() { return legCount; }
        public HydraGrain withLegCount(int legCount) {
            return new HydraGrainImpl(
                id, age, legCount, 
                $extensions);
        }

        public Object get(Object $key) {
            switch ((String)$key) {
                case "id": return getId();
                case "age": return getAge();
                case "legCount": return getLegCount();
                default: return $extensions.get($key);
            }
        }

        private HydraGrain with(String $key, Object $value, boolean $dissoc) {
            switch ($key) {
                case "id": return withId((String)$value);
                case "age": return withAge($value == null ? 0 : (int)$value);
                case "legCount": return withLegCount($value == null ? 0 : (int)$value);
            }
            ConstSortedMap<String, Object> $newExtensions =
                $dissoc ? $extensions.without($key) : $extensions.with($key, $value);
            if ($newExtensions == $extensions) {
                return this;
            }
            return new HydraGrainImpl(
                id, age, legCount, 
                $newExtensions);
        }

        public HydraGrain with(String $key, Object $value) {
            return with($key, $value, false);
        }

        public HydraGrain withAll(Map<? extends String, ?> $map) {
            return $map.isEmpty() ? this : MapTools.putAll(newBuilder(), $map).build();
        }

        public HydraGrain without(Object $key) {
            return with((String)$key, null, true);
        }

        public HydraGrain withoutAll(Collection<?> $keys) {
            return $keys.isEmpty() ? this : MapTools.removeAll(newBuilder(), $keys).build();
        }

        public HydraBuilder newBuilder() {
            HydraBuilderImpl $builder = new HydraBuilderImpl();
            $builder.id = this.id;
            $builder.age = this.age;
            $builder.legCount = this.legCount;
            $builder.$extensions.putAll(this.$extensions);
            return $builder;
        }

        public ConstMap<String, Object> extensions() {
            return $extensions;
        }

        private Object writeReplace() { return new HydraGrainProxy().setPayload(this); }
        private void readObject(ObjectInputStream in) throws InvalidObjectException {
            throw new InvalidObjectException("proxy expected");
        }
    }

    /**
     * Code generated serialization proxy for serializing instances of HydraGrainImpl.
     */
    private static final class HydraGrainProxy extends AbstractGrainProxy {
        private static final long serialVersionUID = 1;
        protected HydraBuilder newBuilder() { return HydraFactory.newBuilder(); }
    }

    /**
     * Code generated implementation of HydraBuilder.
     */
    @PublicInterfaceRef(HydraBuilder.class)
    @GrainFactoryRef(HydraFactory.class)
    private static final class HydraBuilderImpl
        extends AbstractGrainBuilder
        implements HydraBuilder {

        private String id;
        private int age;
        private int legCount;

        private final TreeMap<String, Object> $extensions = new TreeMap<>();

        public int size() { return 3 + $extensions.size(); }

        public MapIterator<String, Object> iterator() {
            return IteratorTools.chainMapIterators(new BasisIter($KEYS), IteratorTools.newMapIterator($extensions));
        }

        public String getId() { return id; }
        public HydraBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public int getAge() { return age; }
        public HydraBuilder setAge(int age) {
            this.age = age;
            return this;
        }

        public int getLegCount() { return legCount; }
        public HydraBuilder setLegCount(int legCount) {
            this.legCount = legCount;
            return this;
        }

        public Object get(Object $key) {
            switch ((String)$key) {
                case "id": return getId();
                case "age": return getAge();
                case "legCount": return getLegCount();
                default: return $extensions.get($key);
            }
        }

        private Object put(String $key, Object $value, boolean $dissoc) {
            Object $original;
            switch ($key) {
                case "id":
                    $original = getId();
                    setId((String)$value);
                    return $original;
                case "age":
                    $original = getAge();
                    setAge($value == null ? 0 : (int)$value);
                    return $original;
                case "legCount":
                    $original = getLegCount();
                    setLegCount($value == null ? 0 : (int)$value);
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

        public HydraGrain build() {
            return new HydraGrainImpl(
                id, age, legCount, 
                BasicCollections.asSortedMap($extensions));
        }
    }
}
