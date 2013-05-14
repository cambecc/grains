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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * 2013-03-24<p/>
 *
 * A type operator that prints types exactly as {@link TypeWriter}, except the arguments of parameterized types
 * are omitted, making use of the <a href="http://docs.oracle.com/javase/tutorial/java/generics/types.html#diamond">
 * diamond operator</a>. For example, where {@link TypeWriter} renders a type as {@code List&lt;Integer&gt;}, this
 * class renders it as {@code List&lt;&gt;}.
 *
 * @author Cameron Beccario
 */
final class DiamondWriter extends TypeWriter {

    DiamondWriter(TypePrinter printer) {
        super(printer);
    }

    @Override public TypePrinter apply(ParameterizedType pt) {
        apply(TypeTools.erase(pt.getRawType()), pt.getOwnerType());
        Type[] typeArguments = pt.getActualTypeArguments();
        if (typeArguments.length > 0) {
            printer.print('<').print('>');
        }
        return printer;
    }
}
