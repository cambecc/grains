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

package net.nullschool.grains;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 2013-04-19<p/>
 *
 * Defines which {@link GrainFactory} class can be used to construct instances of the annotated type. For example,
 * if {@code FooFactory} is the factory for {@code FooGrain}, then this association is made explicit with the
 * following:
 * <pre>
 * &#64;GrainFactoryRef(FooFactory.class)
 * interface FooGrain extends Grain {
 *     ...
 * }
 * </pre>
 *
 * This annotation makes it possible to retrieve the GrainFactory instance given the Grain's type using the
 * {@link GrainTools#factoryFor} utility method.
 *
 * @author Cameron Beccario
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface GrainFactoryRef {

    Class<? extends GrainFactory> value();
}
