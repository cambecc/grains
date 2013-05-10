package net.nullschool.grains.generate;

import net.nullschool.reflect.AbstractTypePrinter;
import net.nullschool.reflect.TypePrinter;


/**
 * 2013-02-12<p/>
 *
 * @author Cameron Beccario
 */
final class ImportingPrinterFactory implements TypePrinterFactory {

    private final Imports imports;

    ImportingPrinterFactory(Imports imports) {
        this.imports = imports;
    }

    private class ImportingPrinter extends AbstractTypePrinter {

        @Override public ImportingPrinter print(Class<?> clazz) {
            sb.append(clazz != null ? imports.doImport(clazz) : null);
            return this;
        }
    }

    @Override public TypePrinter newPrinter() {
        return new ImportingPrinter();
    }
}
