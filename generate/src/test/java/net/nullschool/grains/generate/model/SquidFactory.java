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
 * Factory for constructing Grain instances of Animal.Squid.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
public enum SquidFactory implements GrainFactory {
    INSTANCE;

    private static final ConstMap<String, GrainProperty> $PROPERTIES = GrainTools.asPropertyMap(
        new SimpleGrainProperty("id", String.class),
        new SimpleGrainProperty("giant", boolean.class, GrainProperty.Flag.IS_PROPERTY),
        new SimpleGrainProperty("legCount", int.class));

    private static final String[] $KEYS = $PROPERTIES.keySet().toArray(new String[3]);
    private static final SquidGrain $DEFAULT = newBuilder().build();
    public static SquidGrain defaultValue() { return $DEFAULT; }
    public static SquidBuilder newBuilder() { return new SquidBuilderImpl(); }

    public ConstMap<String, GrainProperty> getBasisProperties() { return $PROPERTIES; }
    public SquidGrain getDefaultValue() { return defaultValue(); }
    public SquidBuilder getNewBuilder() { return newBuilder(); }
    public String toString() { return getClass().getName(); }

    /**
     * Code generated implementation of SquidGrain.
     */
    @PublicInterfaceRef(SquidGrain.class)
    @GrainFactoryRef(SquidFactory.class)
    private static final class SquidGrainImpl
        extends AbstractGrain
        implements SquidGrain, Serializable {

        private final String id;
        private final boolean giant;
        private final int legCount;

        private final ConstSortedMap<String, Object> $extensions;

        private SquidGrainImpl(
            String id, boolean giant, int legCount, 
            ConstSortedMap<String, Object> $extensions) {

            this.id = id;
            this.giant = giant;
            this.legCount = legCount;
            this.$extensions = $extensions;
        }

        public int size() { return 3 + $extensions.size(); }

        public MapIterator<String, Object> iterator() {
            return IteratorTools.chainMapIterators(new BasisIter($KEYS), $extensions.iterator());
        }

        public String getId() { return id; }
        public SquidGrain withId(String id) {
            return new SquidGrainImpl(
                id, giant, legCount, 
                $extensions);
        }

        public boolean isGiant() { return giant; }
        public SquidGrain withGiant(boolean giant) {
            return new SquidGrainImpl(
                id, giant, legCount, 
                $extensions);
        }

        public int getLegCount() { return legCount; }
        public SquidGrain withLegCount(int legCount) {
            return new SquidGrainImpl(
                id, giant, legCount, 
                $extensions);
        }

        public Object get(Object $key) {
            switch ((String)$key) {
                case "id": return getId();
                case "giant": return isGiant();
                case "legCount": return getLegCount();
                default: return $extensions.get($key);
            }
        }

        private SquidGrain with(String $key, Object $value, boolean $dissoc) {
            switch ($key) {
                case "id": return withId((String)$value);
                case "giant": return withGiant($value == null ? false : (boolean)$value);
                case "legCount": return withLegCount($value == null ? 0 : (int)$value);
            }
            ConstSortedMap<String, Object> $newExtensions =
                $dissoc ? $extensions.without($key) : $extensions.with($key, $value);
            if ($newExtensions == $extensions) {
                return this;
            }
            return new SquidGrainImpl(
                id, giant, legCount, 
                $newExtensions);
        }

        public SquidGrain with(String $key, Object $value) {
            return with($key, $value, false);
        }

        public SquidGrain withAll(Map<? extends String, ?> $map) {
            return $map.isEmpty() ? this : MapTools.putAll(newBuilder(), $map).build();
        }

        public SquidGrain without(Object $key) {
            return with((String)$key, null, true);
        }

        public SquidGrain withoutAll(Collection<?> $keys) {
            return $keys.isEmpty() ? this : MapTools.removeAll(newBuilder(), $keys).build();
        }

        public SquidBuilder newBuilder() {
            SquidBuilderImpl $builder = new SquidBuilderImpl();
            $builder.id = this.id;
            $builder.giant = this.giant;
            $builder.legCount = this.legCount;
            $builder.$extensions.putAll(this.$extensions);
            return $builder;
        }

        public ConstMap<String, Object> extensions() {
            return $extensions;
        }

        private Object writeReplace() { return new SquidGrainProxy().setPayload(this); }
        private void readObject(ObjectInputStream in) throws InvalidObjectException {
            throw new InvalidObjectException("proxy expected");
        }
    }

    /**
     * Code generated serialization proxy for serializing instances of SquidGrainImpl.
     */
    private static final class SquidGrainProxy extends AbstractGrainProxy {
        private static final long serialVersionUID = 1;
        protected SquidBuilder newBuilder() { return SquidFactory.newBuilder(); }
    }

    /**
     * Code generated implementation of SquidBuilder.
     */
    @PublicInterfaceRef(SquidBuilder.class)
    @GrainFactoryRef(SquidFactory.class)
    private static final class SquidBuilderImpl
        extends AbstractGrainBuilder
        implements SquidBuilder {

        private String id;
        private boolean giant;
        private int legCount;

        private final TreeMap<String, Object> $extensions = new TreeMap<>();

        public int size() { return 3 + $extensions.size(); }

        public MapIterator<String, Object> iterator() {
            return IteratorTools.chainMapIterators(new BasisIter($KEYS), IteratorTools.newMapIterator($extensions));
        }

        public String getId() { return id; }
        public SquidBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public boolean isGiant() { return giant; }
        public SquidBuilder setGiant(boolean giant) {
            this.giant = giant;
            return this;
        }

        public int getLegCount() { return legCount; }
        public SquidBuilder setLegCount(int legCount) {
            this.legCount = legCount;
            return this;
        }

        public Object get(Object $key) {
            switch ((String)$key) {
                case "id": return getId();
                case "giant": return isGiant();
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
                case "giant":
                    $original = isGiant();
                    setGiant($value == null ? false : (boolean)$value);
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

        public SquidGrain build() {
            return new SquidGrainImpl(
                id, giant, legCount, 
                BasicCollections.asSortedMap($extensions));
        }
    }
}
