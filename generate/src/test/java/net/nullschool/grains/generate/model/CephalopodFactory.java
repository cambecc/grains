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
 * Factory for constructing Grain instances of Animal.Cephalopod.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
public enum CephalopodFactory implements GrainFactory {
    INSTANCE;

    private static final ConstMap<String, GrainProperty> $PROPERTIES = GrainTools.asPropertyMap(
        new SimpleGrainProperty("id", String.class),
        new SimpleGrainProperty("legCount", int.class));

    private static final String[] $KEYS = $PROPERTIES.keySet().toArray(new String[2]);
    private static final CephalopodGrain $DEFAULT = newBuilder().build();
    public static CephalopodGrain defaultValue() { return $DEFAULT; }
    public static CephalopodBuilder newBuilder() { return new CephalopodBuilderImpl(); }

    public ConstMap<String, GrainProperty> getBasisProperties() { return $PROPERTIES; }
    public CephalopodGrain getDefaultValue() { return defaultValue(); }
    public CephalopodBuilder getNewBuilder() { return newBuilder(); }
    public String toString() { return getClass().getName(); }

    /**
     * Code generated implementation of CephalopodGrain.
     */
    @PublicInterfaceRef(CephalopodGrain.class)
    @GrainFactoryRef(CephalopodFactory.class)
    private static final class CephalopodGrainImpl
        extends AbstractGrain
        implements CephalopodGrain, Serializable {

        private final String id;
        private final int legCount;

        private final ConstSortedMap<String, Object> $extensions;

        private CephalopodGrainImpl(
            String id, int legCount, 
            ConstSortedMap<String, Object> $extensions) {

            this.id = id;
            this.legCount = legCount;
            this.$extensions = $extensions;
        }

        public int size() { return 2 + $extensions.size(); }

        public MapIterator<String, Object> iterator() {
            return IteratorTools.chainMapIterators(new BasisIter($KEYS), $extensions.iterator());
        }

        public String getId() { return id; }
        public CephalopodGrain withId(String id) {
            return new CephalopodGrainImpl(
                id, legCount, 
                $extensions);
        }

        public int getLegCount() { return legCount; }
        public CephalopodGrain withLegCount(int legCount) {
            return new CephalopodGrainImpl(
                id, legCount, 
                $extensions);
        }

        public Object get(Object $key) {
            switch ((String)$key) {
                case "id": return getId();
                case "legCount": return getLegCount();
                default: return $extensions.get($key);
            }
        }

        private CephalopodGrain with(String $key, Object $value, boolean $dissoc) {
            switch ($key) {
                case "id": return withId((String)$value);
                case "legCount": return withLegCount($value == null ? 0 : (int)$value);
            }
            ConstSortedMap<String, Object> $newExtensions =
                $dissoc ? $extensions.without($key) : $extensions.with($key, $value);
            if ($newExtensions == $extensions) {
                return this;
            }
            return new CephalopodGrainImpl(
                id, legCount, 
                $newExtensions);
        }

        public CephalopodGrain with(String $key, Object $value) {
            return with($key, $value, false);
        }

        public CephalopodGrain withAll(Map<? extends String, ?> $map) {
            return $map.isEmpty() ? this : MapTools.putAll(newBuilder(), $map).build();
        }

        public CephalopodGrain without(Object $key) {
            return with((String)$key, null, true);
        }

        public CephalopodGrain withoutAll(Collection<?> $keys) {
            return $keys.isEmpty() ? this : MapTools.removeAll(newBuilder(), $keys).build();
        }

        public CephalopodBuilder newBuilder() {
            CephalopodBuilderImpl $builder = new CephalopodBuilderImpl();
            $builder.id = this.id;
            $builder.legCount = this.legCount;
            $builder.$extensions.putAll(this.$extensions);
            return $builder;
        }

        public ConstMap<String, Object> extensions() {
            return $extensions;
        }

        private Object writeReplace() { return new CephalopodGrainProxy().setPayload(this); }
        private void readObject(ObjectInputStream in) throws InvalidObjectException {
            throw new InvalidObjectException("proxy expected");
        }
    }

    /**
     * Code generated serialization proxy for serializing instances of CephalopodGrainImpl.
     */
    private static final class CephalopodGrainProxy extends AbstractGrainProxy {
        private static final long serialVersionUID = 1;
        protected CephalopodBuilder newBuilder() { return CephalopodFactory.newBuilder(); }
    }

    /**
     * Code generated implementation of CephalopodBuilder.
     */
    @PublicInterfaceRef(CephalopodBuilder.class)
    @GrainFactoryRef(CephalopodFactory.class)
    private static final class CephalopodBuilderImpl
        extends AbstractGrainBuilder
        implements CephalopodBuilder {

        private String id;
        private int legCount;

        private final TreeMap<String, Object> $extensions = new TreeMap<>();

        public int size() { return 2 + $extensions.size(); }

        public MapIterator<String, Object> iterator() {
            return IteratorTools.chainMapIterators(new BasisIter($KEYS), IteratorTools.newMapIterator($extensions));
        }

        public String getId() { return id; }
        public CephalopodBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public int getLegCount() { return legCount; }
        public CephalopodBuilder setLegCount(int legCount) {
            this.legCount = legCount;
            return this;
        }

        public Object get(Object $key) {
            switch ((String)$key) {
                case "id": return getId();
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

        public CephalopodGrain build() {
            return new CephalopodGrainImpl(
                id, legCount, 
                BasicCollections.asSortedMap($extensions));
        }
    }
}
