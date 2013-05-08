package net.nullschool.reflect;

import net.nullschool.collect.*;

import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.*;


/**
 * 2013-05-08<p/>
 *
 * @author Cameron Beccario
 */
public class DefaultImmutabilityStrategy implements ImmutabilityStrategy {

    private static final DefaultImmutabilityStrategy INSTANCE = new DefaultImmutabilityStrategy();
    public static DefaultImmutabilityStrategy instance() { return INSTANCE; }

    private final Set<Class<?>> immutableTypes = new HashSet<>();
    private final Set<Class<?>> immutableBases = new HashSet<>();
    private final Map<Class<?>, Class<?>> translations = new HashMap<>();

    void registerType(Class<?> clazz) {
        immutableTypes.add(clazz);
        if (!Modifier.isFinal(clazz.getModifiers())) {
            immutableBases.add(clazz);
        }
    }

    void registerTranslation(Class<?> from, Class<?> to) {
        translations.put(from, to);
        registerType(to);
    }

    DefaultImmutabilityStrategy() {
        registerType(boolean.class);
        registerType(byte.class);
        registerType(short.class);
        registerType(int.class);
        registerType(long.class);
        registerType(float.class);
        registerType(double.class);
        registerType(char.class);
        registerType(void.class);
        registerType(Boolean.class);
        registerType(Byte.class);
        registerType(Short.class);
        registerType(Integer.class);
        registerType(Long.class);
        registerType(BigInteger.class);
        registerType(BigDecimal.class);
        registerType(Float.class);
        registerType(Double.class);
        registerType(Character.class);
        registerType(String.class);
        registerType(Void.class);
        registerType(UUID.class);
        registerType(URI.class);
        registerType(Currency.class);
        registerType(Enum.class);
        registerType(ConstCollection.class);
        registerType(ConstMap.class);

        registerTranslation(Collection.class, ConstCollection.class);
        registerTranslation(List.class, ConstList.class);
        registerTranslation(Set.class, ConstSet.class);
        registerTranslation(SortedSet.class, ConstSortedSet.class);
        registerTranslation(Map.class, ConstMap.class);
        registerTranslation(SortedMap.class, ConstSortedMap.class);
    }

    @Override public boolean test(Class<?> clazz) {
        if (immutableTypes.contains(clazz)) {
            return true;
        }
        for (Class<?> base : immutableBases) {
            if (base.isAssignableFrom(clazz)) {
                return true;
            }
        }
        return false;
    }

    @Override public <T> T requireImmutable(T t) {
        if (!test(t.getClass())) {
            throw new IllegalArgumentException("not immutable");
        }
        return t;
    }

    @Override public Class<?> translate(Class<?> clazz) {
        return translations.get(clazz);
    }

    @Override public <T> CastFunction<T> newCastFunction(TypeToken<T> token) {
        return Casts.buildChecker(token);
    }
}
