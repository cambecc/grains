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

package net.nullschool.grains.msgpack;

import org.msgpack.MessageTypeException;
import org.msgpack.packer.Packer;
import org.msgpack.template.AbstractTemplate;
import org.msgpack.unpacker.Unpacker;

import java.io.IOException;


/**
 * 2013-06-05<p/>
 *
 * A MessagePack template that automatically handles reading and writing of null values.
 *
 * @author Cameron Beccario
 */
public abstract class AbstractNullableTemplate<T> extends AbstractTemplate<T> {

    /**
     * Writes the specified value to the provided packer. The value is never null.
     */
    protected abstract void writeValue(Packer packer, T value) throws IOException;

    @Override public void write(Packer packer, T value, boolean required) throws IOException {
        if (value == null) {
            if (required) {
                throw new MessageTypeException("Cannot write null when value is required: " + getClass().getName());
            }
            packer.writeNil();
            return;
        }
        writeValue(packer, value);
    }

    /**
     * Reads a value from the provided unpacker. The unpacker has already been inspected for 'nil', so this method
     * will return an instance, never null.
     *
     * @param unpacker the unpacker.
     * @param to the object instance to populate (if possible), or null if none is available.
     * @return the reconstituted value.
     * @throws IOException
     */
    protected abstract T readValue(Unpacker unpacker, T to) throws IOException;

    @Override public T read(Unpacker unpacker, T to, boolean required) throws IOException {
        if (!required && unpacker.trySkipNil()) {
            return null;
        }
        return readValue(unpacker, to);
    }
}
