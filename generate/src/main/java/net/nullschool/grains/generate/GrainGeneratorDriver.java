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

import net.nullschool.grains.GrainTools;
import net.nullschool.grains.TypePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-03-24<p/>
 *
 * @author Cameron Beccario
 */
final class GrainGeneratorDriver {

    private static final Logger log = LoggerFactory.getLogger(GrainGeneratorDriver.class);


    private final Configuration config;
    private final TypeTable typeTable;
    private final NamingPolicy namingPolicy;
    private Member typePolicyMember;

    GrainGeneratorDriver(Configuration config, NamingPolicy namingPolicy) {
        this.config = config;
        this.namingPolicy = namingPolicy;
        this.typeTable = buildTypeTable(config.getTypePolicy());
    }

    private TypeTable buildTypeTable(String accessString) {
        log.debug("Loading type policy: {}", accessString);
        int lastDot = accessString.lastIndexOf('.');
        String className = accessString.substring(0, lastDot);
        try {
            Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            String memberName = accessString.substring(lastDot + 1);
            try {
                // Try finding it as a field.
                Field field = clazz.getField(memberName);
                if (field != null && Modifier.isStatic(field.getModifiers())) {
                    TypePolicy typePolicy = (TypePolicy)field.get(null);
                    log.debug("Using {} found in {}.", typePolicy, field);
                    typePolicyMember = field;
                    return new TypeTable(namingPolicy, typePolicy);
                }
            }
            catch (NoSuchFieldException e) {
                // Doesn't exist as a field.
            }
            try {
                // Try finding it as a method.
                Method method = clazz.getMethod(memberName);
                if (method != null && Modifier.isStatic(method.getModifiers())) {
                    TypePolicy typePolicy = (TypePolicy)method.invoke(null);
                    log.debug("Using {} found in {}.", typePolicy, method);
                    typePolicyMember = method;
                    return new TypeTable(namingPolicy, typePolicy);
                }
            }
            catch (NoSuchMethodException e) {
                // Doesn't exist as a method either.
            }
            throw new ReflectiveOperationException(
                String.format("Cannot find public static field or method with name '%s'", memberName));
        }
        catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(String.format("Failed to get instance of '%s'", className), e);
        }
    }

    public GenerationResult generate(Class<?> schema, Template template) {
        try {
            Importer importer = new Importer(GrainTools.targetPackageOf(schema));
            TypePrinterFactory printerFactory = new ImportingPrinterFactory(importer);
//            TypePrinterFactory printerFactory = new TypePrinterFactory() {
//                @Override public TypePrinter newPrinter() {
//                    return new FullNamePrinter();
//                }
//            };
            SymbolTable symbolTable = new SymbolTable(schema, typeTable, printerFactory, typePolicyMember);

            GenerationResult body = template.invoke(
                mapOf(
                    "type", symbolTable.buildTypeSymbols(),
                    "grain", symbolTable.buildGrainSymbol()));

            GenerationResult importsBlock = Templates.newImportsBlockTemplate(config).invoke(
                mapOf("imports", (Object)importer));

            body.getErrors().addAll(importsBlock.getErrors());
            return new GenerationResult(importsBlock.getText() + body.getText(), body.getErrors());
        }
        catch (Exception | Error e) {
            throw new RuntimeException("Unexpected while generating schema " + schema.getName(), e);
        }
    }
}
