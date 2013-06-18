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
import java.util.Map;

import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-03-24<p/>
 *
 * A class that generates code for a grain schema using a {@link TemplateHandle}.
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

    /**
     * Create a new TypeTable using the TypePolicy instance referenced by the specified access expression.
     */
    private TypeTable buildTypeTable(String typePolicyAccessExpression) {
        // CONSIDER: clean up this code; it's ugly.

        // The access expression is a fully qualified name of a class and the name of a static member, separated by
        // a dot. We need to load the class, figure out if the member is a field or method, then get the TypePolicy
        // object returned by that field/method.

        log.debug("Loading type policy: {}", typePolicyAccessExpression);
        int lastDot = typePolicyAccessExpression.lastIndexOf('.');
        String className = typePolicyAccessExpression.substring(0, lastDot);
        String memberName = typePolicyAccessExpression.substring(lastDot + 1);
        try {
            // Load the class.
            Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            try {
                // Try finding the member as a field.
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
                // Try finding the member as a method.
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

    /**
     * Invokes the provided template with arguments built for the specified schema.
     */
    public GenerationResult generate(Class<?> schema, TemplateHandle templateHandle) {
        try {
            // Create an importer because we don't want the generator to fully qualify every single type.
            Importer importer = new Importer(GrainTools.targetPackageOf(schema));
            TypePrinterFactory printerFactory = new ImportingPrinterFactory(importer);

            // Build all the symbols we need for code generation and package them into an argument map.
            SymbolTable symbolTable = new SymbolTable(schema, typeTable, printerFactory, typePolicyMember);
            Map<String, Object> arguments = mapOf(
                "type", symbolTable.buildTypeSymbols(),
                "grain", symbolTable.buildGrainSymbol());

            // Generate the code.
            GenerationResult body = templateHandle.invoke(arguments);

            // Now generate all the "import" statements that will appear at the top of the file.
            arguments = mapOf("imports", (Object)importer);
            GenerationResult importsBlock = TemplateHandles.newImportsBlockTemplate(config).invoke(arguments);

            // Concatenate the two generated blocks; imports go first!
            body.getErrors().addAll(importsBlock.getErrors());
            return new GenerationResult(importsBlock.getText() + body.getText(), body.getErrors());
        }
        catch (Exception | Error e) {
            throw new RuntimeException(String.format("Unexpected while generating schema '%s'", schema.getName()), e);
        }
    }
}
