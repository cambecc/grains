package net.nullschool.grains.generate;

import net.nullschool.collect.ConstSet;
import net.nullschool.collect.basic.BasicConstSet;
import net.nullschool.grains.GrainProperty;
import net.nullschool.util.StringTools;

import java.util.Set;

import static net.nullschool.reflect.TypeTools.*;
import static net.nullschool.grains.GrainProperty.Flag.*;


/**
 * 2013-03-24<p/>
 *
 * @author Cameron Beccario
 */
final class PropertySymbol {

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

    public String getName() {
        return prop.getName();
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getGetterName() {
        return getterName;
    }

    public String getSetterName() {
        return setterName;
    }

    public String getWitherName() {
        return witherName;
    }

    public TypeSymbol getType() {
        return typeSymbol;
    }

    public String getDefault() {
        Class<?> clazz = erase(prop.getType());
        return clazz != null && clazz.isPrimitive() ? clazz == boolean.class ? "false" : "0" : null;
    }

    public TypeTokenSymbol getTypeToken() {
        return typeToken;
    }

    public Set<StaticFieldLoadExpression> getFlags() {
        return flags;
    }
}
