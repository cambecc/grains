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

import net.nullschool.collect.ConstSet;
import net.nullschool.collect.basic.BasicConstSet;
import net.nullschool.grains.GrainProperty;
import net.nullschool.util.StringTools;

import java.util.Set;

import static net.nullschool.grains.GrainProperty.Flag.IS_PROPERTY;
import static net.nullschool.reflect.TypeTools.erase;


/**
 * 2013-03-24<p/>
 *
 * A symbol that represents the details of one property.
 *
 * @author Cameron Beccario
 */
final class PropertySymbol implements Symbol {

    private final GrainProperty prop;
    private final String fieldName;   // name of private internal field (potentially escaped)
    private final String getterName;  // name of get method
    private final String setterName;  // name of set method
    private final String witherName;  // name of with method
    private final TypeSymbol typeSymbol;
    private final TypeTokenSymbol typeToken;
    private final ConstSet<StaticFieldLoadExpression> flags;

    PropertySymbol(GrainProperty prop, TypePrinterFactory factory, TypeTokenSymbol typeToken) {
        this.prop = prop;
        this.fieldName = GenerateTools.escape(prop.getName());
        String capitalized = StringTools.capitalize(prop.getName());
        this.getterName = (prop.getFlags().contains(IS_PROPERTY) ? "is" : "get") + capitalized;
        this.setterName = "set" + capitalized;
        this.witherName = "with" + capitalized;
        this.typeSymbol = new TypeSymbol(prop.getType(), factory);
        this.typeToken = typeToken;

        ConstSet<StaticFieldLoadExpression> flags = BasicConstSet.emptySet();
        for (GrainProperty.Flag flag : prop.getFlags()) {
            flags = flags.with(new StaticFieldLoadExpression(GrainProperty.Flag.class, flag.name(), factory));
        }
        this.flags = flags;
    }

    /**
     * The property's public name. Examples: "quantity", "enabled", "string"
     */
    public String getName() {
        return prop.getName();
    }

    /**
     * The ({@link GenerateTools#escape potentially escaped}) name of the field where the property's value is
     * stored. Examples: "quantity", "enabled", "string_"
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * The name of the property's get accessor method. Examples: "getQuantity", "isEnabled", "getString"
     */
    public String getGetterName() {
        return getterName;
    }

    /**
     * The name of the property's set accessor method. Examples: "getQuantity", "setEnabled", "setString"
     */
    public String getSetterName() {
        return setterName;
    }

    /**
     * The name of the property's with accessor method. Examples: "withQuantity", "withEnabled", "withString"
     */
    public String getWitherName() {
        return witherName;
    }

    /**
     * The property's type as a TypeSymbol.
     */
    public TypeSymbol getType() {
        return typeSymbol;
    }

    /**
     * This property's default value as the string representation needed during code generation.
     */
    public String getDefault() {
        Class<?> clazz = erase(prop.getType());
        if (clazz == boolean.class) {
            return "false";
        }
        else if (clazz.isPrimitive() && clazz != void.class) {
            return "0";
        }
        return null;
    }

    /**
     * This property's associated type token, if any. TypeTokens are required for properties with a generic type.
     */
    public TypeTokenSymbol getTypeToken() {
        return typeToken;
    }

    /**
     * The set of {@link GrainProperty.Flag flags} for this property, represented as enum constant symbols.
     */
    public Set<StaticFieldLoadExpression> getFlags() {
        return flags;
    }
}
