package net.nullschool.grains.generate.model;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import javax.annotation.Generated;
import net.nullschool.collect.ConstList;
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
import net.nullschool.reflect.CastFunction;
import net.nullschool.reflect.DefaultImmutabilityStrategy;
import net.nullschool.reflect.ImmutabilityStrategy;
import net.nullschool.reflect.TypeToken;
import net.nullschool.util.MemoizedHashCode;

// @Generated("net.nullschool.grains.generate.GrainGenerator")
public enum ComposedFactory implements GrainFactory {
    INSTANCE;

    private static final ImmutabilityStrategy $STRATEGY = DefaultImmutabilityStrategy.instance();

    private static final TypeToken<ConstList<UUID>> $0 =
        new TypeToken<ConstList<UUID>>(){};

    private static final ConstMap<String, GrainProperty> $PROPERTIES = GrainTools.propertyMap(
        new SimpleGrainProperty("id", UUID.class),
        new SimpleGrainProperty("left", String.class),
        new SimpleGrainProperty("leftIds", $0.asType()),
        new SimpleGrainProperty("name", String.class),
        new SimpleGrainProperty("right", String.class),
        new SimpleGrainProperty("rightIds", $0.asType()),
        new SimpleGrainProperty("top", String.class));

    private static final String[] $KEYS = $PROPERTIES.keySet().toArray(new String[7]);
    private static final ComposedGrain $BASIS = builder().build();
    public static ComposedGrain DEFAULT() { return $BASIS; }
    public static ComposedBuilder builder() { return new ComposedBuilderImpl(); }

    public ConstMap<String, GrainProperty> getBasisProperties() { return $PROPERTIES; }
    public ComposedGrain getDefault() { return DEFAULT(); }
    public ComposedBuilder newBuilder() { return builder(); }
    public String toString() { return getClass().getName(); }

    private static final CastFunction<ConstList<UUID>> $0Cast =
        $STRATEGY.newCastFunction($0);

    private static final class ComposedGrainImpl
        extends AbstractGrain
        implements ComposedGrain, MemoizedHashCode, Serializable {

        private final UUID id;
        private final String left;
        private final ConstList<UUID> leftIds;
        private final String name;
        private final String right;
        private final ConstList<UUID> rightIds;
        private final String top;

        private final ConstMap<String, Object> $extensions;
        private transient volatile int $hashCode;

        private ComposedGrainImpl(
            UUID id, String left, ConstList<UUID> leftIds, String name, String right, ConstList<UUID> rightIds, 
            String top, 
            ConstMap<String, Object> $extensions) {

            this.id = id;
            this.left = left;
            this.leftIds = leftIds;
            this.name = name;
            this.right = right;
            this.rightIds = rightIds;
            this.top = top;
            this.$extensions = $extensions;
        }

        public int size() { return 7 + $extensions.size(); }

        public MapIterator<String, Object> iterator() {
            return IteratorTools.chainMapIterators(new AbstractGrain.BasisIter($KEYS), $extensions.iterator());
        }

        public UUID getId() { return id; }
        public ComposedGrain withId(UUID id) {
            return new ComposedGrainImpl(
                id, left, leftIds, name, right, rightIds, top, 
                $extensions);
        }

        public String getLeft() { return left; }
        public ComposedGrain withLeft(String left) {
            return new ComposedGrainImpl(
                id, left, leftIds, name, right, rightIds, top, 
                $extensions);
        }

        public ConstList<UUID> getLeftIds() { return leftIds; }
        public ComposedGrain withLeftIds(ConstList<UUID> leftIds) {
            return new ComposedGrainImpl(
                id, left, leftIds, name, right, rightIds, top, 
                $extensions);
        }

        public String getName() { return name; }
        public ComposedGrain withName(String name) {
            return new ComposedGrainImpl(
                id, left, leftIds, name, right, rightIds, top, 
                $extensions);
        }

        public String getRight() { return right; }
        public ComposedGrain withRight(String right) {
            return new ComposedGrainImpl(
                id, left, leftIds, name, right, rightIds, top, 
                $extensions);
        }

        public ConstList<UUID> getRightIds() { return rightIds; }
        public ComposedGrain withRightIds(ConstList<UUID> rightIds) {
            return new ComposedGrainImpl(
                id, left, leftIds, name, right, rightIds, top, 
                $extensions);
        }

        public String getTop() { return top; }
        public ComposedGrain withTop(String top) {
            return new ComposedGrainImpl(
                id, left, leftIds, name, right, rightIds, top, 
                $extensions);
        }

        public Object get(Object $key) {
            switch ((String)$key) {
                case "id": return getId();
                case "left": return getLeft();
                case "leftIds": return getLeftIds();
                case "name": return getName();
                case "right": return getRight();
                case "rightIds": return getRightIds();
                case "top": return getTop();
                default: return $extensions.get($key);
            }
        }

        private ComposedGrain with(String $key, Object $value, boolean $dissoc) {
            switch ($key) {
                case "id": return withId((UUID)$value);
                case "left": return withLeft((String)$value);
                case "leftIds": return withLeftIds($0Cast.apply($value));
                case "name": return withName((String)$value);
                case "right": return withRight((String)$value);
                case "rightIds": return withRightIds($0Cast.apply($value));
                case "top": return withTop((String)$value);
            }
            ConstMap<String, Object> $newExtensions =
                $dissoc ? $extensions.without($key) : $extensions.with($key, $value);
            if ($newExtensions == $extensions) {
                return this;
            }
            return new ComposedGrainImpl(
                id, left, leftIds, name, right, rightIds, top, 
                $newExtensions);
        }

        public ComposedGrain with(String $key, Object $value) {
            return with($key, $value, false);
        }

        public ComposedGrain withAll(Map<? extends String, ?> $map) {
            return $map.isEmpty() ? this : MapTools.putAll(builder(), $map).build();
        }

        public ComposedGrain without(Object $key) {
            return with((String)$key, null, true);
        }

        public ComposedGrain withoutAll(Collection<?> $keys) {
            return $keys.isEmpty() ? this : MapTools.removeAll(builder(), $keys).build();
        }

        public ComposedBuilder builder() {
            ComposedBuilderImpl $builder = new ComposedBuilderImpl();
            $builder.id = this.id;
            $builder.left = this.left;
            $builder.leftIds = this.leftIds;
            $builder.name = this.name;
            $builder.right = this.right;
            $builder.rightIds = this.rightIds;
            $builder.top = this.top;
            $builder.$extensions.putAll(this.$extensions);
            return $builder;
        }

        public ConstMap<String, Object> extensions() {
            return $extensions;
        }

        public int hashCode() {
            return $hashCode != 0 ? $hashCode : ($hashCode = (super.hashCode() + $extensions.hashCode()));
        }

        private Object writeReplace() { return new ComposedGrainProxy().setPayload(this); }
        private void readObject(ObjectInputStream in) throws InvalidObjectException {
            throw new InvalidObjectException("proxy expected");
        }
    }

    private static final class ComposedGrainProxy extends AbstractGrainProxy {
        private static final long serialVersionUID = 1;
        protected ComposedBuilder newBuilder() { return ComposedFactory.INSTANCE.newBuilder(); }
    }

    private static final class ComposedBuilderImpl
        extends AbstractGrainBuilder
        implements ComposedBuilder {

        private UUID id;
        private String left;
        private ConstList<UUID> leftIds;
        private String name;
        private String right;
        private ConstList<UUID> rightIds;
        private String top;

        private final TreeMap<String, Object> $extensions = new TreeMap<>();

        public int size() { return 7 + $extensions.size(); }

        public MapIterator<String, Object> iterator() {
            return IteratorTools.chainMapIterators(new AbstractGrainBuilder.BasisIter($KEYS), IteratorTools.newMapIterator($extensions));
        }

        public UUID getId() { return id; }
        public ComposedBuilder setId(UUID id) {
            this.id = id;
            return this;
        }

        public String getLeft() { return left; }
        public ComposedBuilder setLeft(String left) {
            this.left = left;
            return this;
        }

        public ConstList<UUID> getLeftIds() { return leftIds; }
        public ComposedBuilder setLeftIds(ConstList<UUID> leftIds) {
            this.leftIds = leftIds;
            return this;
        }

        public String getName() { return name; }
        public ComposedBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public String getRight() { return right; }
        public ComposedBuilder setRight(String right) {
            this.right = right;
            return this;
        }

        public ConstList<UUID> getRightIds() { return rightIds; }
        public ComposedBuilder setRightIds(ConstList<UUID> rightIds) {
            this.rightIds = rightIds;
            return this;
        }

        public String getTop() { return top; }
        public ComposedBuilder setTop(String top) {
            this.top = top;
            return this;
        }

        public Object get(Object $key) {
            switch ((String)$key) {
                case "id": return getId();
                case "left": return getLeft();
                case "leftIds": return getLeftIds();
                case "name": return getName();
                case "right": return getRight();
                case "rightIds": return getRightIds();
                case "top": return getTop();
                default: return $extensions.get($key);
            }
        }

        private Object put(String $key, Object $value, boolean $dissoc) {
            Object $original;
            switch ($key) {
                case "id":
                    $original = getId();
                    setId((UUID)$value);
                    return $original;
                case "left":
                    $original = getLeft();
                    setLeft((String)$value);
                    return $original;
                case "leftIds":
                    $original = getLeftIds();
                    setLeftIds($0Cast.apply($value));
                    return $original;
                case "name":
                    $original = getName();
                    setName((String)$value);
                    return $original;
                case "right":
                    $original = getRight();
                    setRight((String)$value);
                    return $original;
                case "rightIds":
                    $original = getRightIds();
                    setRightIds($0Cast.apply($value));
                    return $original;
                case "top":
                    $original = getTop();
                    setTop((String)$value);
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

        public ComposedGrain build() {
            return new ComposedGrainImpl(
                id, left, leftIds, name, right, rightIds, top, 
                BasicConstMap.asMap($extensions));
        }
    }
}
