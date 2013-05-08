package net.nullschool.grains.generate;

import net.nullschool.collect.basic.BasicConstMap;
import net.nullschool.grains.GrainProperty;
import net.nullschool.grains.SimpleGrainProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 2013-03-24<p/>
 *
 * @author Cameron Beccario
 */
final class GrainGenerator {

    private static final Logger log = LoggerFactory.getLogger(GrainGenerator.class);


    private final Configuration config;
    private final Types types;

    public GrainGenerator(Configuration config) {
        this.config = config;
        this.types = new Types(config);
    }

    private static final Comparator<GrainProperty> propertyNameSortOrder = new Comparator<GrainProperty>() {

        private boolean isId(GrainProperty prop) {
            return "id".equalsIgnoreCase(prop.getName());
        }

        private int compareNames(String left, String right) {
            int cmp = left.compareToIgnoreCase(right);      // sort alphabetically, case-insensitive
            return cmp != 0 ? cmp : left.compareTo(right);  // when differ by case only, sort by case
        }

        @Override public int compare(GrainProperty left, GrainProperty right) {
            if (isId(left)) {
                if (!isId(right)) {
                    return -1;
                }
            }
            else if (isId(right)) {
                return 1;
            }
            return compareNames(left.getName(), right.getName());
        }
    };

    private static List<GrainProperty> resolveProperties(List<GrainProperty> properties) {
        Set<String> names = new HashSet<>();  // UNDONE: use a proper algorithm to handle name collisions.
        List<GrainProperty> results = new ArrayList<>();
        for (GrainProperty prop : properties) {
            if (!names.add(prop.getName())) {
                continue;
            }
            results.add(prop);
        }
        return results;
    }

    private BeanSymbol buildBean(Class<?> schema, TypePrinterFactory factory) throws IntrospectionException {
        int typeTokenIndex = 0;

        Map<Type, TypeTokenDecl> typeTokens = new LinkedHashMap<>();
        List<GrainProperty> properties = resolveProperties(GenerateTools.collectBeanProperties(schema));
        Collections.sort(properties, propertyNameSortOrder);

        List<PropertySymbol> symbols = new ArrayList<>();

        for (GrainProperty prop : properties) {
            Type immutableType = types.immutify(prop.getType());
            GrainProperty immutableProp = new SimpleGrainProperty(prop.getName(), immutableType, prop.getFlags());

            TypeTokenDecl typeTokenDecl = null;
            if (immutableType instanceof ParameterizedType) {
                typeTokenDecl = typeTokens.get(immutableType);
                if (typeTokenDecl == null) {
                    String name = "$" + typeTokenIndex++;
                    typeTokens.put(
                        immutableType,
                        typeTokenDecl = new TypeTokenDecl(
                            name,
                            immutableType,
                            new Checker(name + "Checker", immutableType, factory), factory));
                }
            }
            symbols.add(new PropertySymbol(immutableProp, factory, typeTokenDecl));
        }

        return new BeanSymbol(symbols, typeTokens.values());
    }

    public GenerationResult generate(Class<?> schema, Template template) {
        try {

            Imports imports = new Imports(Types.targetPackage(schema));
            TypePrinterFactory factory = new Importer(imports);
//            TypePrinterFactory factory = new TypePrinterFactory() {
//                @Override public TypePrinter newPrinter() {
//                    return new StringTypePrinter();
//                }
//            };

            GenerationResult body = template.invoke(
                BasicConstMap.mapOf(
                    "grain", buildBean(schema, factory),
                    "decl", types.targetDecls(schema),
                    "type", types.types(schema, factory)));

            GenerationResult importsBlock = Templates.newImportsBlockTemplate(config).invoke(
                BasicConstMap.mapOf("imports", (Object)imports));

            body.getErrors().addAll(importsBlock.getErrors());  // :(  errors should probably be logged.
            return new GenerationResult(importsBlock.getText() + body.getText(), body.getErrors());
        }
        catch (Exception e) {
            throw new RuntimeException("Error building " + schema, e);
        }
    }
}
