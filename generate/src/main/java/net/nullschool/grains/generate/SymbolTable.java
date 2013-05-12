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

    private static Type cook(Type type) {
        return new Cook().apply(type);
    }

    private Type immutify(Type type) {
        return new Immutify(typeTable).apply(type);
    }

    private static Type dewildcard(Type type) {
        return new DeWildcard().apply(type);
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

        Map<Type, TypeTokenSymbol> typeTokens = new LinkedHashMap<>();
        List<GrainProperty> properties = resolveProperties(GenerateTools.collectBeanProperties(schema));
        Collections.sort(properties, GrainPropertyComparator.INSTANCE);

        List<PropertySymbol> symbols = new ArrayList<>();

        for (GrainProperty prop : properties) {
            Type immutableType = dewildcard(immutify(cook(prop.getType())));
            GrainProperty immutableProp = new SimpleGrainProperty(prop.getName(), immutableType, prop.getFlags());
            TypeSymbol immutableTypeSymbol = new TypeSymbol(immutableType, printerFactory);

            TypeTokenSymbol typeTokenSymbol = null;
            if (immutableType instanceof ParameterizedType) {
                typeTokenSymbol = typeTokens.get(immutableType);
                if (typeTokenSymbol == null) {
                    String name = "$" + typeTokenIndex++;
                    typeTokens.put(
                        immutableType,
                        typeTokenSymbol = new TypeTokenSymbol(
                            name,
                            immutableTypeSymbol,
                            new FieldSymbol(name + "Cast", immutableTypeSymbol)));
                }
            }
            symbols.add(new PropertySymbol(immutableProp, printerFactory, typeTokenSymbol));
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
