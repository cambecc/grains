package net.nullschool.reflect;

/**
 * 2013-04-03<p/>
 *
 * A type printer that prints classes using their {@link Class#getName fully qualified names}.
 *
 * @author Cameron Beccario
 */
final class FullNamePrinter extends AbstractTypePrinter {

    @Override public TypePrinter print(Class<?> clazz) {
        sb.append(clazz != null ? clazz.getName() : null);
        return this;
    }
}
