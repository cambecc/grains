package net.nullschool.grains.generate;

import net.nullschool.reflect.TypePrinter;


/**
 * 2013-02-12<p/>
 *
 * @author Cameron Beccario
 */
final class Importer implements TypePrinterFactory {

    private final Imports imports;

    Importer(Imports imports) {
        this.imports = imports;
    }

    private class ImportingPrinter implements TypePrinter {

        private final StringBuilder sb = new StringBuilder();

        @Override public TypePrinter print(char c) {
            sb.append(c);
            return this;
        }

        @Override public TypePrinter print(String s) {
            sb.append(s);
            return this;
        }

        @Override public TypePrinter print(Class<?> clazz) {
            sb.append(clazz != null ? imports.doImport(clazz) : null);
            return this;
        }

        @Override public String toString() {
            return sb.toString();
        }
    }

    @Override public TypePrinter newPrinter() {
        return new ImportingPrinter();
    }
}
