package net.nullschool.reflect;

import org.junit.Test;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;


/**
 * 2013-05-02<p/>
 *
 * @author Cameron Beccario
 */
public class EraserTest {

    @Test
    public void test_eraser() {
        Eraser eraser = Eraser.INSTANCE;

        assertSame(Object.class, eraser.invoke(Object.class));
        assertSame(Object[].class, eraser.invoke(Object[].class));
        assertSame(Set[].class, eraser.invoke(new JavaToken<Set<?>[]>(){}.asGenericArrayType()));
        assertSame(Object.class, eraser.invoke(new LateWildcardType("?")));
        assertSame(Object.class, eraser.invoke(new LateWildcardType("? super", Comparable.class)));
        assertSame(Comparable.class, eraser.invoke(new LateWildcardType("? extends", Comparable.class)));
        assertSame(Map.class, eraser.invoke(new LateWildcardType("? extends", Map.class, HashMap.class)));
        assertSame(Set.class, eraser.invoke(new JavaToken<Set<Integer>>(){}.asParameterizedType()));
        assertSame(Object.class, eraser.invoke(new LateTypeVariable<Class>("E", Set.class)));
        assertSame(Map.class, eraser.invoke(new LateTypeVariable<Class>("E", Set.class, Map.class)));
        assertSame(Map.class, eraser.invoke(new LateTypeVariable<Class>("E", Set.class, Map.class, HashMap.class)));

        assertSame(Object.class, eraser.invoke((Type)Object.class));
        assertSame(Object[].class, eraser.invoke((Type)Object[].class));
        assertSame(Set[].class, eraser.invoke(new JavaToken<Set<?>[]>(){}.asType()));
        assertSame(Object.class, eraser.invoke((Type)new LateWildcardType("?")));
        assertSame(Object.class, eraser.invoke((Type)new LateWildcardType("? super", Comparable.class)));
        assertSame(Comparable.class, eraser.invoke((Type)new LateWildcardType("? extends", Comparable.class)));
        assertSame(Map.class, eraser.invoke((Type)new LateWildcardType("? extends", Map.class, HashMap.class)));
        assertSame(Set.class, eraser.invoke(new JavaToken<Set<Integer>>(){}.asType()));
        assertSame(Object.class, eraser.invoke((Type)new LateTypeVariable<Class>("E", Set.class)));
        assertSame(Map.class, eraser.invoke((Type)new LateTypeVariable<Class>("E", Set.class, Map.class)));
        assertSame(Map.class, eraser.invoke((Type)new LateTypeVariable<Class>("E", Set.class, Map.class, HashMap.class)));

        assertNull(eraser.invoke((Type)null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_eraser_unknown() {
        Eraser.INSTANCE.invoke(new Type(){});
    }
}
