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

package net.nullschool.collect.basic;

import net.nullschool.collect.MapIterator;

import java.io.*;

import static net.nullschool.collect.basic.BasicTools.unionInto;
import static net.nullschool.util.ArrayTools.EMPTY_OBJECT_ARRAY;


/**
 * 2013-03-17<p/>
 *
 * A serialization proxy for AbstractBasicConstMap. This class handles serialization and deserialization for all
 * AbstractBasicConstMap implementations using the Java Serialization Proxy pattern.
 *
 * @author Cameron Beccario
 */
final class MapProxy implements Serializable {

    private static final long serialVersionUID = 1;
    private transient BasicConstMap<?, ?> map;

    MapProxy(BasicConstMap<?, ?> map) {
        this.map = map;
    }

    /**
     * Writes the map in the form {int_size, k0, v0, k1, v1, ..., kN, vN}.
     *
     * @param out the output stream.
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        final int size = map.size();
        out.writeInt(size);

        int i = 0;
        for (MapIterator<?, ?> iter = map.iterator(); iter.hasNext();) {
            out.writeObject(iter.next());
            out.writeObject(iter.value());
            i++;
        }

        if (i != size) {
            throw new IOException(String.format("Expected %s entries, but found %s: %s", size, i, map));
        }
    }

    /**
     * Instantiates the appropriate AbstractBasicConstMap implementation from the serialized form described
     * by {@link #writeObject}.
     *
     * @param in the stream containing the serialized form.
     * @return a size-appropriate implementation of AbstractBasicConstMap.
     */
    private BasicConstMap<?, ?> readMap(ObjectInputStream in) throws IOException, ClassNotFoundException {
        final int size = in.readInt();
        switch (size) {
            case 0: return BasicMap0.instance();
            case 1: return new BasicMap1<>(in.readObject(), in.readObject());
            default:
                Object[] keys = new Object[size];
                Object[] values = new Object[size];
                for (int i = 0; i < size; i++) {
                    keys[i] = in.readObject();
                    values[i] = in.readObject();
                }
                return BasicConstMap.condense(unionInto(EMPTY_OBJECT_ARRAY, EMPTY_OBJECT_ARRAY, keys, values));
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        map = readMap(in);
    }

    Object readResolve() {
        return map;
    }
}
