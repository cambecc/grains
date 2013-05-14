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

import net.nullschool.reflect.TypeTools;

import java.lang.reflect.Method;
import java.lang.reflect.Type;


/**
 * 2013-05-08<p/>
 *
 * Represents the expression for invoking a static method.
 *
 * @author Cameron Beccario
 */
final class StaticMethodInvocationExpression implements Symbol {

    private final Type declaringClass;
    private final String name;
    private final TypePrinterFactory factory;

    StaticMethodInvocationExpression(Method method, TypePrinterFactory factory) {
        this(method.getDeclaringClass(), method.getName(), factory);
    }

    StaticMethodInvocationExpression(Type declaringClass, String name, TypePrinterFactory factory) {
        this.declaringClass = declaringClass;
        this.name = name;
        this.factory = factory;
    }

    @Override public String toString() {
        return TypeTools.toString(declaringClass, factory.newPrinter()) + '.' + name + "()";
    }
}
