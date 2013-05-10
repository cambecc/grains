package net.nullschool.grains.generate;

import net.nullschool.reflect.*;

import java.lang.reflect.Type;


/**
 * 2013-05-10<p/>
 *
 * A type writer that prints classes using their {@link Class#getSimpleName simple names}. Enclosed classes are not
 * qualified by their enclosing class. For example, {@code java.util.Map.Entry} is rendered as {@code "Entry"} rather
 * than {@code "Map.Entry"}.
 *
 * @author Cameron Beccario
 */
final class SimpleNameWriter extends TypeWriter {

    /**
     * A type printer that prints classes using their simple name.
     */
    static final class SimpleNamePrinter extends AbstractTypePrinter {

        @Override public TypePrinter print(Class<?> clazz) {
            sb.append(clazz != null ? clazz.getSimpleName() : null);
            return this;
        }
    }

    SimpleNameWriter() {
        super(new SimpleNamePrinter());
    }

    /**
     * Prints just the simple name of the specified class. The enclosing type is ignored.
     */
    @Override protected TypePrinter invoke(Class<?> clazz, Type enclosing) {
        return printer.print(clazz);
    }
}
