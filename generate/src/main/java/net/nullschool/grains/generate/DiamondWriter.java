package net.nullschool.grains.generate;

import net.nullschool.reflect.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * 2013-03-24<p/>
 *
 * A type operator that prints types exactly as {@link TypeWriter}, except the arguments of parameterized types
 * are omitted, making use of the <a href="http://docs.oracle.com/javase/tutorial/java/generics/types.html#diamond">
 * diamond operator</a>. For example, where {@link TypeWriter} renders a type as {@code List&lt;Integer&gt;}, this
 * class renders it as {@code List&lt;&gt;}.
 *
 * @author Cameron Beccario
 */
final class DiamondWriter extends TypeWriter {

    DiamondWriter(TypePrinter printer) {
        super(printer);
    }

    @Override public TypePrinter apply(ParameterizedType pt) {
        apply(TypeTools.erase(pt.getRawType()), pt.getOwnerType());
        Type[] typeArguments = pt.getActualTypeArguments();
        if (typeArguments.length > 0) {
            printer.print('<').print('>');
        }
        return printer;
    }
}
