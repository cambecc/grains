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
 * 2013-04-03<p/>
 *
 * A type printer that prints classes using their {@link Class#getName fully qualified names}.
 *
 * @author Cameron Beccario
 */
final class FullNamePrinter extends AbstractTypePrinter {

    @Override public TypePrinter print(Class<?> clazz) {
        sb.append(clazz != null ? clazz.getName() : null);
        return this;
    }
}
