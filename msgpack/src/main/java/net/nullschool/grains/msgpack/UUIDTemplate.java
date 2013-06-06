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

import org.msgpack.packer.Packer;
import org.msgpack.unpacker.Unpacker;

import java.io.IOException;
import java.util.UUID;


/**
 * 2013-06-05<p/>
 *
 * A MessagePack template that encodes UUIDs as an array of two longs: [most_significant_bits, least_significant_bits].
 * This ensures the UUID payload is one structured unit and is more compact than encoding as a string.
 *
 * @author Cameron Beccario
 */
public final class UUIDTemplate extends AbstractNullableTemplate<UUID> {

    @Override public void writeValue(Packer packer, UUID uuid) throws IOException {
        packer.writeArrayBegin(2);
        packer.write(uuid.getMostSignificantBits());
        packer.write(uuid.getLeastSignificantBits());
        packer.writeArrayEnd();
    }

    @Override public UUID readValue(Unpacker unpacker, UUID to) throws IOException {
        unpacker.readArrayBegin();
        UUID result = new UUID(unpacker.readLong(), unpacker.readLong());
        unpacker.readArrayEnd();
        return result;
    }
}
