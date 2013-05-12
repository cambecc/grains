package net.nullschool.grains.generate;

import net.nullschool.reflect.TypeTools;

import java.lang.reflect.Field;
import java.lang.reflect.Type;


/**
 * 2013-04-13<p/>
 *
 * Represents the expression for loading the value of a static field.
 *
 * @author Cameron Beccario
 */
final class StaticFieldLoadExpression implements Symbol {
    // CONSIDER: combine with FieldSymbol?

    private final Type declaringClass;
    private final String name;
    private final TypePrinterFactory factory;

    StaticFieldLoadExpression(Field field, TypePrinterFactory factory) {
        this(field.getDeclaringClass(), field.getName(), factory);
    }

    StaticFieldLoadExpression(Type declaringClass, String name, TypePrinterFactory factory) {
        this.declaringClass = declaringClass;
        this.name = name;
        this.factory = factory;
    }

    @Override public String toString() {
        return TypeTools.toString(declaringClass, factory.newPrinter()) + '.' + name;
    }
}
