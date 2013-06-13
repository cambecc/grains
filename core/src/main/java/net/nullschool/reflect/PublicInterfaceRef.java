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

import java.lang.annotation.*;


/**
 * 2013-06-04<p/>
 *
 * Signifies which class represents the "public interface" of the annotated class, where "public interface" is a
 * publicly exported type intended for consumption by developers. This annotation is meant to be applied to non-public
 * implementations of a public abstract class or interface, allowing developers to map these non-public types to their
 * public equivalents.<p/>
 *
 * A hypothetical example: if the private member class {@code java.util.Collections.EmptyList} was annotated with
 * {@code @PublicInterfaceRef(List.class)}, then a custom serialization framework that encodes objects using fully
 * qualified type names would be able to conveniently encode instances of {@code EmptyList} as "java.util.List" rather
 * than "java.util.Collections.EmptyList" (which is a hidden implementation detail not intended for public export).<p/>
 *
 * Proper usage ensures the value of this annotation is a wider type than the type with this annotation.
 *
 * @author Cameron Beccario
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface PublicInterfaceRef {

    Class<?> value();
}
