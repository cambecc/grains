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

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Set;
import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-04-10<p/>
 *
 * @author Cameron Beccario
 */
public class SimpleGrainProperty implements GrainProperty {

    private final String name;
    private final Type type;
    private final Set<Flag> flags;

    public SimpleGrainProperty(String name, Type type, Set<Flag> flags) {
        this.name = Objects.requireNonNull(name);
        this.type = Objects.requireNonNull(type);
        this.flags = asSet(flags);
    }

    public SimpleGrainProperty(String name, Type type, Flag... flags) {
        this(name, type, asSet(flags));
    }

    @Override public String getName() {
        return name;
    }

    @Override public Type getType() {
        return type;
    }

    @Override public Set<Flag> getFlags() {
        return flags;
    }

    @Override public String toString() {
        return String.format("%s{%s, %s, %s}", SimpleGrainProperty.class.getSimpleName(), name, type, flags);
    }
}
