package net.nullschool.grains.generate;

import net.nullschool.collect.basic.BasicConstMap;
import net.nullschool.grains.*;
import net.nullschool.reflect.ImmutabilityPolicy;
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
    private final NamingPolicy namingPolicy;
    private Member policyMember;

    GrainGeneratorDriver(Configuration config, NamingPolicy namingPolicy) {
        this.config = config;
        this.namingPolicy = namingPolicy;
        this.typeTable = buildTypeTable(config.getImmutabilityPolicy());
    }

    private TypeTable buildTypeTable(String accessString) {
        log.debug("Loading immutability policy: {}", accessString);
        int lastDot = accessString.lastIndexOf('.');
        String className = accessString.substring(0, lastDot);
        try {
            Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            String memberName = accessString.substring(lastDot + 1);
            try {
                // Try finding it as a field.
                Field field = clazz.getField(memberName);
                if (field != null && Modifier.isStatic(field.getModifiers())) {
                    ImmutabilityPolicy immutabilityPolicy = (ImmutabilityPolicy)field.get(null);
                    log.debug("Using {} found in {}.", immutabilityPolicy, field);
                    policyMember = field;
                    return new TypeTable(namingPolicy, immutabilityPolicy);
                }
            }
            catch (NoSuchFieldException e) {
                // Doesn't exist as a field.
            }
            try {
                // Try finding it as a method.
                Method method = clazz.getMethod(memberName);
                if (method != null && Modifier.isStatic(method.getModifiers())) {
                    ImmutabilityPolicy immutabilityPolicy = (ImmutabilityPolicy)method.invoke(null);
                    log.debug("Using {} found in {}.", immutabilityPolicy, method);
                    policyMember = method;
                    return new TypeTable(namingPolicy, immutabilityPolicy);
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
            TypePrinterFactory printerFactory = new ImportingPrinterFactory(imports);
//            TypePrinterFactory printerFactory = new TypePrinterFactory() {
//                @Override public TypePrinter newPrinter() {
//                    return new FullNamePrinter();
//                }
//            };
            SymbolTable symbols = new SymbolTable(schema, typeTable, printerFactory, policyMember);

            GenerationResult body = template.invoke(
                BasicConstMap.mapOf(
                    "grain", symbols.buildGrainSymbol(),
                    "type", symbols.buildTypes()));

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
