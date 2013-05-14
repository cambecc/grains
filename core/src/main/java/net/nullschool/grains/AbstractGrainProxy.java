/*
 * Copyright 2013 Cameron Beccario
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.nullschool.grains;

import net.nullschool.collect.MapIterator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * 2013-03-11<p/>
 *
 * An abstract serialization proxy for use by {@link Grain} implementations. This class follows the Java Serialization
 * Proxy pattern. Grains that make use of this proxy are serialized as the sequence of key-value pairs encountered
 * when {@link Grain#iterator iterating} over the grain. Grains are deserialized by instantiating a
 * {@link GrainBuilder}, adding to it all the key-value pairs from the ObjectInputStream, then calling
 * {@link GrainBuilder#build}.<p/>
 *
 * To use this proxy, the Grain implementer should extend this class, provide an implementation of {@link #newBuilder},
 * then define a {@code writeReplace} method which constructs the proxy and sets the grain as the proxy's payload.
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
 *     protected GrainBuilder newBuilder() {
 *         return new MyGrainBuilder();
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
     * Returns a new builder instance used to deserialize the grain. When all key-value pairs from the input stream
     * are put into the builder, the {@link GrainBuilder#build} method is invoked and the return value is used as the
     * deserialization result.
     */
    protected abstract GrainBuilder newBuilder();

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
     * determined by the builders it returns from {@link #newBuilder}.
     *
     * @param in the stream containing the serialized form.
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        GrainBuilder builder = newBuilder();
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
