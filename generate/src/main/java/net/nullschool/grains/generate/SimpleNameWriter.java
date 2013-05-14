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

import net.nullschool.reflect.*;

import java.lang.reflect.Type;


/**
 * 2013-05-10<p/>
 *
 * A type writer that prints classes using their {@link Class#getSimpleName simple names}. Note that enclosed classes
 * are not qualified by their enclosing class. For example, {@code java.util.Map.Entry} is rendered as {@code "Entry"}
 * rather than {@code "Map.Entry"}.
 *
 * @author Cameron Beccario
 */
final class SimpleNameWriter extends TypeWriter {

    /**
     * A type printer that prints classes using their simple name.
     */
    static final class SimpleNamePrinter extends AbstractTypePrinter {

        @Override public TypePrinter print(Class<?> clazz) {
            sb.append(clazz != null ? clazz.getSimpleName() : null);
            return this;
        }
    }

    SimpleNameWriter() {
        super(new SimpleNamePrinter());
    }

    /**
     * Prints just the simple name of the specified class. The enclosing type is ignored.
     */
    @Override protected TypePrinter apply(Class<?> clazz, Type enclosing) {
        return printer.print(clazz);
    }
}
