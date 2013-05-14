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

import java.util.*;

/**
 * 2013-03-24<p/>
 *
 * A symbol that represents the property structure of a grain.
 *
 * @author Cameron Beccario
 */
final class GrainSymbol implements Symbol {
    
    private final List<? extends PropertySymbol> properties;
    private final List<? extends TypeTokenSymbol> typeTokens;
    private final Symbol typePolicyLoadExpression;

    GrainSymbol(
        List<? extends PropertySymbol> properties,
        Collection<? extends TypeTokenSymbol> typeTokens,
        Symbol typePolicyLoadExpression) {

        this.properties = Collections.unmodifiableList(new ArrayList<>(properties));
        this.typeTokens = Collections.unmodifiableList(new ArrayList<>(typeTokens));
        this.typePolicyLoadExpression = typePolicyLoadExpression;
    }

    public List<? extends PropertySymbol> getProperties() {
        return properties;
    }

    public List<? extends TypeTokenSymbol> getTypeTokens() {
        return typeTokens;
    }

    public Symbol getTypePolicy() {
        return typePolicyLoadExpression;
    }
}
