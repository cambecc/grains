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

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import static net.nullschool.reflect.TypeTools.*;


/**
 * 2013-04-13<p/>
 *
 * Represents the expression for loading the value of a static field.
 *
 * @author Cameron Beccario
 */
final class StaticFieldLoadExpression implements Symbol {
    // CONSIDER: combine with FieldSymbol?

    private final Type declaringClass;
    private final String name;
    private final TypePrinterFactory factory;

    StaticFieldLoadExpression(Field field, TypePrinterFactory factory) {
        this(field.getDeclaringClass(), field.getName(), factory);
    }

    StaticFieldLoadExpression(Type declaringClass, String name, TypePrinterFactory factory) {
        this.declaringClass = declaringClass;
        this.name = name;
        this.factory = factory;
    }

    @Override public String toString() {
        return print(declaringClass, factory.newPrinter()) + '.' + name;
    }
}
