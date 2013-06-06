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
import java.util.Currency;


/**
 * 2013-06-06<p/>
 *
 * @author Cameron Beccario
 */
public final class CurrencyTemplate extends AbstractNullableTemplate<Currency> {

    @Override protected void writeValue(Packer packer, Currency currency) throws IOException {
        packer.write(currency.getCurrencyCode());
    }

    @Override protected Currency readValue(Unpacker unpacker, Currency to) throws IOException {
        return Currency.getInstance(unpacker.readString());
    }
}
