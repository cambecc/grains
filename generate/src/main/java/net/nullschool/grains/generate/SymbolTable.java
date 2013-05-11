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
    private final Member constPolicyMember;

    SymbolTable(Class<?> schema, TypeTable typeTable, TypePrinterFactory printerFactory, Member constPolicyMember) {
        this.schema = schema;
        this.typeTable = typeTable;
        this.printerFactory = printerFactory;
        this.constPolicyMember = constPolicyMember;
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

    public GrainSymbol buildGrainSymbol() throws IntrospectionException {
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

        Symbol constPolicyLoadExpression = null;
        if (!typeTokens.isEmpty()) {
            if (constPolicyMember instanceof Method) {
                constPolicyLoadExpression =
                    new StaticMethodInvocationExpression((Method)constPolicyMember, printerFactory);
            }
            else if (constPolicyMember instanceof Field) {
                constPolicyLoadExpression = new StaticFieldLoadExpression((Field)constPolicyMember, printerFactory);
            }
        }
        return new GrainSymbol(symbols, typeTokens.values(), constPolicyLoadExpression);
    }

    public Map<String, Symbol> buildTypes() {
        Map<String, Symbol> map = new HashMap<>();
        for (Map.Entry<String, Type> entry : typeTable.wellKnownTypes().entrySet()) {
            map.put(entry.getKey(), new TypeSymbol(entry.getValue(), printerFactory));
        }
        for (Map.Entry<String, Type> entry : typeTable.schemaTypes(schema).entrySet()) {
            map.put(entry.getKey(), new TypeSymbol(entry.getValue(), printerFactory));
        }
        return map;
    }
}
