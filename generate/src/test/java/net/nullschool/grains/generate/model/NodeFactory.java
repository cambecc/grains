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
 * Factory for constructing Grain instances of Complete.Node.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
public enum NodeFactory implements GrainFactory {
    INSTANCE;

    private static final ConstMap<String, GrainProperty> $PROPERTIES = GrainTools.asPropertyMap(
        new SimpleGrainProperty("id", int.class),
        new SimpleGrainProperty("complete", CompleteGrain.class));

    private static final String[] $KEYS = $PROPERTIES.keySet().toArray(new String[2]);
    private static final NodeGrain $DEFAULT = newBuilder().build();
    public static NodeGrain defaultValue() { return $DEFAULT; }
    public static NodeBuilder newBuilder() { return new NodeBuilderImpl(); }

    public ConstMap<String, GrainProperty> getBasisProperties() { return $PROPERTIES; }
    public NodeGrain getDefaultValue() { return defaultValue(); }
    public NodeBuilder getNewBuilder() { return newBuilder(); }
    public String toString() { return getClass().getName(); }

    /**
     * Code generated implementation of NodeGrain.
     */
    @PublicInterfaceRef(NodeGrain.class)
    @GrainFactoryRef(NodeFactory.class)
    private static final class NodeGrainImpl
        extends AbstractGrain
        implements NodeGrain, Serializable {

        private final int id;
        private final CompleteGrain complete;

        private final ConstSortedMap<String, Object> $extensions;

        private NodeGrainImpl(
            int id, CompleteGrain complete, 
            ConstSortedMap<String, Object> $extensions) {

            this.id = id;
            this.complete = complete;
            this.$extensions = $extensions;
        }

        public int size() { return 2 + $extensions.size(); }

        public MapIterator<String, Object> iterator() {
            return IteratorTools.chainMapIterators(new BasisIter($KEYS), $extensions.iterator());
        }

        public int getId() { return id; }
        public NodeGrain withId(int id) {
            return new NodeGrainImpl(
                id, complete, 
                $extensions);
        }

        public CompleteGrain getComplete() { return complete; }
        public NodeGrain withComplete(CompleteGrain complete) {
            return new NodeGrainImpl(
                id, complete, 
                $extensions);
        }

        public Object get(Object $key) {
            switch ((String)$key) {
                case "id": return getId();
                case "complete": return getComplete();
                default: return $extensions.get($key);
            }
        }

        private NodeGrain with(String $key, Object $value, boolean $dissoc) {
            switch ($key) {
                case "id": return withId($value == null ? 0 : (int)$value);
                case "complete": return withComplete((CompleteGrain)$value);
            }
            ConstSortedMap<String, Object> $newExtensions =
                $dissoc ? $extensions.without($key) : $extensions.with($key, $value);
            if ($newExtensions == $extensions) {
                return this;
            }
            return new NodeGrainImpl(
                id, complete, 
                $newExtensions);
        }

        public NodeGrain with(String $key, Object $value) {
            return with($key, $value, false);
        }

        public NodeGrain withAll(Map<? extends String, ?> $map) {
            return $map.isEmpty() ? this : MapTools.putAll(newBuilder(), $map).build();
        }

        public NodeGrain without(Object $key) {
            return with((String)$key, null, true);
        }

        public NodeGrain withoutAll(Collection<?> $keys) {
            return $keys.isEmpty() ? this : MapTools.removeAll(newBuilder(), $keys).build();
        }

        public NodeBuilder newBuilder() {
            NodeBuilderImpl $builder = new NodeBuilderImpl();
            $builder.id = this.id;
            $builder.complete = this.complete;
            $builder.$extensions.putAll(this.$extensions);
            return $builder;
        }

        public ConstMap<String, Object> extensions() {
            return $extensions;
        }

        private Object writeReplace() { return new NodeGrainProxy().setPayload(this); }
        private void readObject(ObjectInputStream in) throws InvalidObjectException {
            throw new InvalidObjectException("proxy expected");
        }
    }

    /**
     * Code generated serialization proxy for serializing instances of NodeGrainImpl.
     */
    private static final class NodeGrainProxy extends AbstractGrainProxy {
        private static final long serialVersionUID = 1;
        protected NodeBuilder newBuilder() { return NodeFactory.newBuilder(); }
    }

    /**
     * Code generated implementation of NodeBuilder.
     */
    @PublicInterfaceRef(NodeBuilder.class)
    @GrainFactoryRef(NodeFactory.class)
    private static final class NodeBuilderImpl
        extends AbstractGrainBuilder
        implements NodeBuilder {

        private int id;
        private CompleteGrain complete;

        private final TreeMap<String, Object> $extensions = new TreeMap<>();

        public int size() { return 2 + $extensions.size(); }

        public MapIterator<String, Object> iterator() {
            return IteratorTools.chainMapIterators(new BasisIter($KEYS), IteratorTools.newMapIterator($extensions));
        }

        public int getId() { return id; }
        public NodeBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public CompleteGrain getComplete() { return complete; }
        public NodeBuilder setComplete(CompleteGrain complete) {
            this.complete = complete;
            return this;
        }

        public Object get(Object $key) {
            switch ((String)$key) {
                case "id": return getId();
                case "complete": return getComplete();
                default: return $extensions.get($key);
            }
        }

        private Object put(String $key, Object $value, boolean $dissoc) {
            Object $original;
            switch ($key) {
                case "id":
                    $original = getId();
                    setId($value == null ? 0 : (int)$value);
                    return $original;
                case "complete":
                    $original = getComplete();
                    setComplete((CompleteGrain)$value);
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

        public NodeGrain build() {
            return new NodeGrainImpl(
                id, complete, 
                BasicCollections.asSortedMap($extensions));
        }
    }
}
