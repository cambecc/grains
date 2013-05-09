package net.nullschool.grains.generate;

import net.nullschool.grains.*;

import java.beans.IntrospectionException;
import java.lang.reflect.*;
import java.util.*;


/**
 * 2013-05-09<p/>
 *
 * @author Cameron Beccario
 */
final class SymbolTable {

    private final Class<?> schema;
    private final TypeTable typeTable;
    private final TypePrinterFactory printerFactory;
    private final Member strategyMember;

    SymbolTable(Class<?> schema, TypeTable typeTable, TypePrinterFactory printerFactory, Member strategyMember) {
        this.schema = schema;
        this.typeTable = typeTable;
        this.printerFactory = printerFactory;
        this.strategyMember = strategyMember;
    }

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

    public BeanSymbol buildBean() throws IntrospectionException {
        int typeTokenIndex = 0;

        Map<Type, TypeTokenDecl> typeTokens = new LinkedHashMap<>();
        List<GrainProperty> properties = resolveProperties(GenerateTools.collectBeanProperties(schema));
        Collections.sort(properties, GrainPropertyComparator.INSTANCE);

        List<PropertySymbol> symbols = new ArrayList<>();

        for (GrainProperty prop : properties) {
            Type immutableType = Immutify.apply(prop.getType(), typeTable);
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
                            new CastFunctionSymbol(name + "Cast", immutableType, printerFactory), printerFactory));
                }
            }
            symbols.add(new PropertySymbol(immutableProp, printerFactory, typeTokenDecl));
        }

        return new BeanSymbol(symbols, typeTokens.values());
    }

    private Map<String, TypeSymbol> targetTypes()  {
        Map<String, TypeSymbol> map = new HashMap<>();
        map.put("schema", new TypeSymbol(schema, printerFactory));
        for (Map.Entry<String, Type> entry : typeTable.schemaTypes(schema).entrySet()) {
            map.put(entry.getKey(), new TypeSymbol(entry.getValue(), printerFactory));
        }
        return map;
    }

    public synchronized Map<String, Symbol> types() {
        Map<String, Symbol> map = new HashMap<>();
        for (Map.Entry<String, Type> entry : typeTable.wellKnownTypes().entrySet()) {
            map.put(entry.getKey(), new TypeSymbol(entry.getValue(), printerFactory));
        }

        map.putAll(targetTypes());

        if (strategyMember instanceof Method) {
            map.put("strategy", new StaticMethodInvocationExpression((Method)strategyMember, printerFactory));
        }
        else if (strategyMember instanceof Field) {
            map.put("strategy", new StaticFieldLoadExpression((Field)strategyMember, printerFactory));
        }
        return map;
    }

    public Map<String, TypeDecl> targetDecls() {
        Map<String, TypeDecl> map = new HashMap<>();
        map.put("targetGrain", new TypeDecl(schema.getSimpleName() + "Grain"));
        map.put("targetGrainImpl", new TypeDecl(schema.getSimpleName() + "GrainImpl"));
        map.put("targetGrainProxy", new TypeDecl(schema.getSimpleName() + "GrainProxy"));
        map.put("targetBuilder", new TypeDecl(schema.getSimpleName() + "Builder"));
        map.put("targetBuilderImpl", new TypeDecl(schema.getSimpleName() + "BuilderImpl"));
        map.put("targetFactory", new TypeDecl(schema.getSimpleName() + "Factory"));
        return map;
    }
}
