package net.nullschool.grains;

import net.nullschool.collect.MapIterator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * 2013-03-11<p/>
 *
 * An abstract serialization proxy for use by {@link Grain} implementations. This class handles serialization and
 * deserialization for all Grain implementations using the Java Serialization Proxy pattern. Grains are serialized as
 * a sequence of key-value pairs. Grains are deserialized by instantiating a {@link GrainBuilder}, adding to it all
 * the key-value pairs from the ObjectInputStream, then calling {@link GrainBuilder#build}.<p/>
 *
 * To use this proxy, the Grain implementer should extend this class, provide an implementation of {@link #getFactory},
 * and then define a {@code writeReplace} method which constructs the proxy and sets the grain as the proxy's payload.
 * For example:
 * <pre>
 * class MyGrain implements Grain, Serializable {
 *     ...
 *     private Object writeReplace() {
 *         return new MyGrainProxy().setPayload(this);
 *     }
 * }
 *
 * class MyGrainProxy extends AbstractGrainProxy {
 *     protected GrainFactory getFactory() {
 *         return MyGrainFactory.INSTANCE;
 *     }
 * }
 * </pre>
 *
 * @author Cameron Beccario
 */
public abstract class AbstractGrainProxy implements Serializable {
    private static final long serialVersionUID = 1;

    private transient Grain grain;

    /**
     * Sets the Grain instance to serialize when the {@link Serializable writeObject} method is invoked.
     *
     * @param grain the object to serialize.
     * @return this proxy.
     */
    public AbstractGrainProxy setPayload(Grain grain) {
        this.grain = grain;
        return this;
    }

    /**
     * Returns the factory that is used by this proxy to construct new Grain instances during deserialization.
     */
    protected abstract GrainFactory getFactory();

    /**
     * Writes the grain in the form {int_size, k0, v0, k1, v1, ..., kN, vN}.
     *
     * @param out the output stream.
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        final Grain grain = this.grain;
        out.defaultWriteObject();
        int size = grain.size();
        out.writeInt(size);

        int i = 0;
        for (MapIterator<String, Object> iter = grain.iterator(); iter.hasNext();) {
            out.writeObject(iter.next());
            out.writeObject(iter.value());
            i++;
        }

        if (i != size) {
            throw new IOException(String.format("Expected %s entries, but found %s: %s", size, i, grain));
        }
    }

    /**
     * Constructs a grain instance from the serialized form described by {@link #writeObject}. The grain's type is
     * determined by the factory and the builders it produces.
     *
     * @param in the stream containing the serialized form.
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        GrainBuilder builder = getFactory().newBuilder();
        in.defaultReadObject();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            String key = (String)in.readObject();
            Object value = in.readObject();
            builder.put(key, value);
        }
        grain = builder.build();
    }

    protected Object readResolve() {
        return grain;
    }
}
