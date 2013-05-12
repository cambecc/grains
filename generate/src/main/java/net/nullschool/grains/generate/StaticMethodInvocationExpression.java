package net.nullschool.grains.generate;

import net.nullschool.reflect.TypeTools;

import java.lang.reflect.Method;
import java.lang.reflect.Type;


/**
 * 2013-05-08<p/>
 *
 * Represents the expression for invoking a static method.
 *
 * @author Cameron Beccario
 */
final class StaticMethodInvocationExpression implements Symbol {

    private final Type declaringClass;
    private final String name;
    private final TypePrinterFactory factory;

    StaticMethodInvocationExpression(Method method, TypePrinterFactory factory) {
        this(method.getDeclaringClass(), method.getName(), factory);
    }

    StaticMethodInvocationExpression(Type declaringClass, String name, TypePrinterFactory factory) {
        this.declaringClass = declaringClass;
        this.name = name;
        this.factory = factory;
    }

    @Override public String toString() {
        return TypeTools.toString(declaringClass, factory.newPrinter()) + '.' + name + "()";
    }
}
