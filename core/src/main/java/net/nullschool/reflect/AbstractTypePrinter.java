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

package net.nullschool.reflect;

/**
 * 2013-05-10<p/>
 *
 * A partial implementation of TypePrinter using a {@link StringBuilder} for string construction.
 *
 * @author Cameron Beccario
 */
public abstract class AbstractTypePrinter implements TypePrinter {

    protected final StringBuilder sb = new StringBuilder();

    @Override public TypePrinter print(char c) {
        sb.append(c);
        return this;
    }

    @Override public TypePrinter print(String s) {
        sb.append(s);
        return this;
    }

    @Override public String toString() {
        return sb.toString();
    }
}
