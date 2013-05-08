package net.nullschool.grains.generate;

import net.nullschool.collect.basic.BasicConstSet;
import net.nullschool.grains.GrainProperty;
import net.nullschool.grains.SimpleGrainProperty;
import net.nullschool.reflect.LateParameterizedType;
import net.nullschool.reflect.TypeTools;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import net.nullschool.grains.GrainProperty.Flag;

/**
 * 2013-02-10<p/>
 *
 * @author Cameron Beccario
 */
enum GenerateTools {;

    private static final Set<String> javaReservedWords =
        new HashSet<>(
            Arrays.asList(
                "abstract", "assert", "boolean", "break", "byte", "case",
                "catch", "char", "class", "const", "continue", "default",
                "do", "double", "else", "enum", "extends", "false",
                "final", "finally", "float", "for", "goto", "if",
                "implements", "import", "instanceof", "int", "interface", "long",
                "native", "new", "null", "package", "private", "protected",
                "public", "return", "short", "static", "strictfp", "super",
                "switch", "synchronized", "this", "throw", "throws", "transient",
                "true", "try", "void", "volatile", "while"));

    static boolean isGrainReservedWord(String s) {
        return s.startsWith("$") || s.endsWith("_");
    }

    private GenerateTools() {
        throw new AssertionError();
    }

    static boolean isJavaReserved(String s) {
        return javaReservedWords.contains(s);
    }

    static String escape(String identifier) {
        return javaReservedWords.contains(identifier) || isGrainReservedWord(identifier) ?
            identifier + '_' :
            identifier;
    }

    static LateParameterizedType asLateParameterizedType(Type type) {
        return type instanceof ParameterizedType ? LateParameterizedType.copyOf((ParameterizedType)type) : null;
    }

    static Type genericSuperclass(Type type) {
        LateParameterizedType lpt = asLateParameterizedType(type);
        return lpt != null ? lpt.getSuperclass() : ((Class<?>)type).getGenericSuperclass();
    }

    static Type[] genericInterfaces(Type type) {
        LateParameterizedType lpt = asLateParameterizedType(type);
        return lpt != null ? lpt.getInterfaces() : ((Class<?>)type).getGenericInterfaces();
    }

    private static Set<Flag> flagsFor(PropertyDescriptor pd) {
        return pd.getReadMethod().getName().startsWith("is") ?
            BasicConstSet.setOf(Flag.IS_PROPERTY) :
            BasicConstSet.<Flag>emptySet();
    }

    private static List<GrainProperty> collectBeanPropertiesOf(Type type) throws IntrospectionException {
        List<GrainProperty> properties = new ArrayList<>();
        BeanInfo bi = Introspector.getBeanInfo(TypeTools.erase(type));
        for (PropertyDescriptor pd : bi.getPropertyDescriptors()) {

            Type returnType = pd.getReadMethod().getGenericReturnType();
            LateParameterizedType lpt = asLateParameterizedType(type);
            if (lpt != null) {
                returnType = lpt.resolve(returnType);
            }
            properties.add(new SimpleGrainProperty(pd.getName(), returnType, flagsFor(pd)));
        }
        return properties;
    }

    static List<GrainProperty> collectBeanProperties(Type type) throws IntrospectionException {
        List<GrainProperty> results = new ArrayList<>();
        Set<Type> visited = new HashSet<>();
        visited.add(null);
        Deque<Type> workList = new LinkedList<>();
        workList.add(type);

        while (!workList.isEmpty()) {

            Type current = workList.removeFirst();
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);

            results.addAll(collectBeanPropertiesOf(current));

            workList.add(genericSuperclass(current));
            Collections.addAll(workList, genericInterfaces(current));
        }
        return results;
    }
}
