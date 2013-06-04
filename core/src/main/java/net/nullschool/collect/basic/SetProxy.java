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

import java.io.*;

import static net.nullschool.collect.basic.BasicTools.unionInto;
import static net.nullschool.util.ArrayTools.EMPTY_OBJECT_ARRAY;
import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-03-16<p/>
 *
 * A serialization proxy for AbstractBasicConstSet. This class handles serialization and deserialization for all
 * AbstractBasicConstSet implementations using the Java Serialization Proxy pattern.
 *
 * @author Cameron Beccario
 */
final class SetProxy implements Serializable {

    private static final long serialVersionUID = 1;
    private transient BasicConstSet<?> set;

    SetProxy(BasicConstSet<?> set) {
        this.set = set;
    }

    /**
     * Writes the set in the form {int_size, E0, E1, ..., En}.
     *
     * @param out the output stream.
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        final int size = set.size();
        out.writeInt(size);
        for (int i = 0; i < size; i++) {
            out.writeObject(set.get(i));
        }
    }

    /**
     * Instantiates the appropriate AbstractBasicConstSet implementation from the serialized form described
     * by {@link #writeObject}.
     *
     * @param in the stream containing the serialized form.
     * @return a size-appropriate implementation of AbstractBasicConstSet.
     */
    private BasicConstSet<?> readSet(ObjectInputStream in) throws IOException, ClassNotFoundException {
        final int size = in.readInt();
        switch (size) {
            case 0: return BasicSet0.instance();
            case 1: return new BasicSet1<>(in.readObject());
            default:
                Object[] elements = new Object[size];
                for (int i = 0; i < size; i++) {
                    elements[i] = in.readObject();
                }
                return condenseToSet(unionInto(EMPTY_OBJECT_ARRAY, elements));
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        set = readSet(in);
    }

    Object readResolve() {
        return set;
    }
}
