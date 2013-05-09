package net.nullschool.grains.generate;

import net.nullschool.collect.basic.BasicConstMap;
import net.nullschool.grains.*;
import net.nullschool.reflect.ImmutabilityStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;

/**
 * 2013-03-24<p/>
 *
 * @author Cameron Beccario
 */
final class GrainGeneratorDriver {

    private static final Logger log = LoggerFactory.getLogger(GrainGeneratorDriver.class);


    private final Configuration config;
    private final TypeTable typeTable;
    private Member strategyMember;

    public GrainGeneratorDriver(Configuration config) {
        this.config = config;
        this.typeTable = buildTypeTable(config.getImmutabilityStrategy());
    }

    private TypeTable buildTypeTable(String accessString) {
        log.debug("Loading strategy: {}", accessString);
        int lastDot = accessString.lastIndexOf('.');
        String className = accessString.substring(0, lastDot);
        try {
            Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            String memberName = accessString.substring(lastDot + 1);
            try {
                // Try finding it as a field.
                Field field = clazz.getField(memberName);
                if (field != null && Modifier.isStatic(field.getModifiers())) {
                    ImmutabilityStrategy strategy = (ImmutabilityStrategy)field.get(null);
                    log.debug("Using {} found in {}.", strategy, field);
                    strategyMember = field;
                    return new TypeTable(new NamingPolicy(), strategy);
                }
            }
            catch (NoSuchFieldException e) {
                // Doesn't exist as a field.
            }
            try {
                // Try finding it as a method.
                Method method = clazz.getMethod(memberName);
                if (method != null && Modifier.isStatic(method.getModifiers())) {
                    ImmutabilityStrategy strategy = (ImmutabilityStrategy)method.invoke(null);
                    log.debug("Using {} found in {}.", strategy, method);
                    strategyMember = method;
                    return new TypeTable(new NamingPolicy(), strategy);
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
            Imports imports = new Imports(GrainTools.targetPackageOf(schema));
            TypePrinterFactory printerFactory = new Importer(imports);
//            TypePrinterFactory printerFactory = new TypePrinterFactory() {
//                @Override public TypePrinter newPrinter() {
//                    return new FullyQualifiedNamePrinter();
//                }
//            };
            SymbolTable symbols = new SymbolTable(schema, typeTable, printerFactory, strategyMember);

            GenerationResult body = template.invoke(
                BasicConstMap.mapOf(
                    "grain", symbols.buildBean(),
                    "decl", symbols.targetDecls(),
                    "type", symbols.types()));

            GenerationResult importsBlock = Templates.newImportsBlockTemplate(config).invoke(
                BasicConstMap.mapOf("imports", (Object)imports));

            body.getErrors().addAll(importsBlock.getErrors());  // :(  errors should probably be logged.
            return new GenerationResult(importsBlock.getText() + body.getText(), body.getErrors());
        }
        catch (Exception | Error e) {
            throw new RuntimeException("Unexpected while generating schema " + schema.getName(), e);
        }
    }
}
