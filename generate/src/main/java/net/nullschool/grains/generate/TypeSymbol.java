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

package net.nullschool.grains.generate;

import java.lang.reflect.Type;

import static java.util.Objects.requireNonNull;
import static net.nullschool.reflect.TypeTools.*;


/**
 * 2013-03-24<p/>
 *
 * A symbol for generating type names in a variety of formats.
 *
 * @author Cameron Beccario
 */
final class TypeSymbol implements Symbol {

    private final Type type;
    private final TypePrinterFactory factory;

    TypeSymbol(Type type, TypePrinterFactory factory) {
        this.type = requireNonNull(type, "null type");
        this.factory = requireNonNull(factory, "null factory");
    }

    /**
     * Returns this type using fully qualified names for each type argument.
     */
    public String getFullName() {
        return print(type);
    }

    /**
     * Returns this type, rendered with simple names only.
     */
    public String getSimpleName() {
        return new SimpleNameWriter().apply(type).toString();
    }

    /**
     * Returns this type with a diamond operator, if applicable. {@code List&lt;Long&gt;} becomes {@code List&lt;&gt;}.
     */
    public String getAsDiamond() {
        return new DiamondWriter(factory.newPrinter()).apply(type).toString();
    }

    /**
     * Returns this type as printed using this symbol's printer factory.
     */
    @Override public String toString() {
        return print(type, factory.newPrinter());
    }
}
