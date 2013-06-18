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

import java.util.*;


/**
 * 2013-02-12<p/>
 *
 * Converts fully qualified names to simple names, collecting a block of "import" statements in the process.
 *
 * @see ImportingPrinterFactory
 * @author Cameron Beccario
 */
final class Importer {

    private final String packageContext;

    // Foo -> a.b.c.Foo
    private final Map<String, String> imports = new HashMap<>();
    private final Set<String> wildcardPackages = new HashSet<>();

    Importer(String packageContext) {
        this.packageContext = packageContext;
        wildcardPackages.add("java.lang");
        wildcardPackages.add(packageContext);
    }

    String doImport(Class<?> clazz) {
        String fullyQualifiedName = clazz.getName();
        if (!GenerateTools.isJavaReserved(fullyQualifiedName)) {
            // Given a.b.c.Foo:
            //
            // a.b.c
            String packageName = fullyQualifiedName.substring(0, fullyQualifiedName.lastIndexOf('.'));
            // Foo
            String typeName = fullyQualifiedName.substring(fullyQualifiedName.lastIndexOf('.') + 1);

            // as long as x.y.z.Foo (or x.y.z.*) has not been imported, we can import a.b.c.Foo
            // UNDONE: visibility of consts and other types brought in by outer classes. these
            //         are all members of a class, or members declared in any of its containing or
            //         base classes, or members in the base classes of any containing class.
            // UNDONE: what if someone creates a type with same name as something in java.lang?
            if (imports.values().contains(fullyQualifiedName) || wildcardPackages.contains(packageName)) {
                return typeName;
            }
            else if (!imports.containsKey(typeName)) {
                imports.put(typeName, fullyQualifiedName);
                return typeName;
            }
        }
        return fullyQualifiedName;
    }

    public String getPackage() {
        return packageContext;
    }

    public Set<String> getImports() {
        return new TreeSet<>(imports.values());
    }
}
