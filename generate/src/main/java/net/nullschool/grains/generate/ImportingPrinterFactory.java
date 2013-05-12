package net.nullschool.grains.generate;

import net.nullschool.reflect.AbstractTypePrinter;
import net.nullschool.reflect.TypePrinter;


/**
 * 2013-02-12<p/>
 *
 * A factory for TypePrinters tied to a particular Importer. Types printed by this factory will be registered in the
 * importer, building up a set of "import" statements that can then be used in the header of a generated source file.
 * For example, when the class {@code java.util.List} is printed, just the text "List" is emitted to the file, with
 * the importer registering an "import java.util.List" statement.
 *
 * @author Cameron Beccario
 */
final class ImportingPrinterFactory implements TypePrinterFactory {

    private final Importer importer;

    ImportingPrinterFactory(Importer importer) {
        this.importer = importer;
    }

    private class ImportingPrinter extends AbstractTypePrinter {

        @Override public ImportingPrinter print(Class<?> clazz) {
            sb.append(clazz != null ? importer.doImport(clazz) : null);
            return this;
        }
    }

    @Override public TypePrinter newPrinter() {
        return new ImportingPrinter();
    }
}
