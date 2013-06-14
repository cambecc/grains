package net.nullschool.grains.generate;

import net.nullschool.grains.*;
import net.nullschool.reflect.*;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static net.nullschool.grains.generate.SymbolTable.*;


/**
 * 2013-06-14<p/>
 *
 * @author Cameron Beccario
 */
public class SymbolTableTest {

    interface Descendant<T> {
        T getParent();
    }

    public abstract static class Organism {
        public abstract int getId();

        public abstract void setBob(int bob);        // not a getter, so should be ignored.
        public abstract String getStuff(int index);  // index property, so should be ignored.
        public abstract String[] getStuff();         // index property, so should be ignored.
    }

    public abstract static class Animal extends Organism implements Descendant<UUID> {
        public abstract boolean isVertebrate();

        protected abstract String getName();  // not public, so should be ignored.
    }

    interface RawDescendant extends Descendant {
    }

    @Test
    public void test_collectDeclaredProperties_one_level_only() throws IntrospectionException {
        List<GrainProperty> result;

        result = collectDeclaredProperties(Organism.class);
        assertEquals(1, result.size());
        assertEquals("id", result.get(0).getName());
        assertEquals(int.class, result.get(0).getType());
        assertTrue(result.get(0).getFlags().isEmpty());

        result = collectDeclaredProperties(Animal.class);
        assertEquals(1, result.size());
        assertEquals("vertebrate", result.get(0).getName());
        assertEquals(boolean.class, result.get(0).getType());
        assertEquals(GrainProperty.Flag.IS_PROPERTY, result.get(0).getFlags().iterator().next());
    }

    @Test
    public void test_collectDeclaredProperties_of_generic_type() throws IntrospectionException {
        List<GrainProperty> result;

        // As non-parameterized class
        result = collectDeclaredProperties(Descendant.class);
        assertEquals(1, result.size());
        assertEquals("parent", result.get(0).getName());
        assertEquals(new LateTypeVariable<>("T", Descendant.class), result.get(0).getType());
        assertTrue(result.get(0).getFlags().isEmpty());

        // Parameterized as Descendant<String>
        result = collectDeclaredProperties(new TypeToken<Descendant<String>>(){}.asType());
        assertEquals(1, result.size());
        assertEquals("parent", result.get(0).getName());
        assertEquals(String.class, result.get(0).getType());
        assertTrue(result.get(0).getFlags().isEmpty());
    }

    @Test
    public void test_collectProperties_multiple_levels() throws IntrospectionException {
        List<GrainProperty> result;

        result = collectProperties(Organism.class);
        assertEquals(1, result.size());
        assertEquals("id", result.get(0).getName());
        assertEquals(int.class, result.get(0).getType());
        assertTrue(result.get(0).getFlags().isEmpty());

        result = collectProperties(Animal.class);
        assertEquals(3, result.size());
        assertEquals("vertebrate", result.get(0).getName());
        assertEquals(boolean.class, result.get(0).getType());
        assertEquals(GrainProperty.Flag.IS_PROPERTY, result.get(0).getFlags().iterator().next());
        assertEquals("id", result.get(1).getName());
        assertEquals(int.class, result.get(1).getType());
        assertTrue(result.get(1).getFlags().isEmpty());
        assertEquals("parent", result.get(2).getName());
        assertEquals(UUID.class, result.get(2).getType());
        assertTrue(result.get(2).getFlags().isEmpty());
    }

    @Test
    public void test_collectProperties_multiple_level_raw_generic() throws IntrospectionException {
        List<GrainProperty> result;

        result = collectProperties(RawDescendant.class);
        assertEquals(1, result.size());
        assertEquals("parent", result.get(0).getName());
        assertEquals(new LateTypeVariable<>("T", Descendant.class), result.get(0).getType());
        assertTrue(result.get(0).getFlags().isEmpty());
    }

    interface A              { int getA(); }  //    A   B
    interface B              { int getB(); }  //     \ / \
    interface C extends A, B { int getC(); }  //      C   D
    interface D extends B    { int getD(); }  //       \ /
    interface E extends C, D { int getE(); }  //        E

    @Test
    public void test_collectProperties_composite_type_with_diamond() throws IntrospectionException {
        List<GrainProperty> result;

        result = collectProperties(C.class);
        assertEquals(3, result.size());
        assertEquals("c", result.get(0).getName());
        assertEquals("a", result.get(1).getName());
        assertEquals("b", result.get(2).getName());

        result = collectProperties(E.class);
        assertEquals(5, result.size());
        assertEquals("e", result.get(0).getName());
        assertEquals("c", result.get(1).getName());
        assertEquals("d", result.get(2).getName());
        assertEquals("a", result.get(3).getName());
        assertEquals("b", result.get(4).getName());
    }

    interface P              { P getX(); }  //    P   Q
    interface Q              { Q getX(); }  //     \ /
    interface R extends P, Q { R getX(); }  //      R

    @Test
    public void test_collectProperties_with_duplicate_property_names() throws IntrospectionException {
        List<GrainProperty> result;

        result = collectProperties(R.class);
        assertEquals(3, result.size());
        assertEquals(R.class, result.get(0).getType());
        assertEquals(P.class, result.get(1).getType());
        assertEquals(Q.class, result.get(2).getType());
    }

    interface T                 { Object getX();  }  //    T  U  V
    interface U                 { Integer getX(); }  //     \ | /
    interface V                 { Number getX();  }  //      \|/
    interface W extends T, U, V {                 }  //       W

    @Test
    public void test_collectProperties_with_narrowest_type_in_upper_level() throws IntrospectionException {
        List<GrainProperty> result;

        result = collectProperties(W.class);
        assertEquals(3, result.size());
        assertEquals(Object.class, result.get(0).getType());
        assertEquals(Integer.class, result.get(1).getType());
        assertEquals(Number.class, result.get(2).getType());
    }

    @Test
    public void test_resolveProperties() throws IntrospectionException {
        List<GrainProperty> result;

        result = resolveProperties(collectProperties(C.class));
        assertEquals(3, result.size());
        assertEquals("c", result.get(0).getName());
        assertEquals("a", result.get(1).getName());
        assertEquals("b", result.get(2).getName());

        result = resolveProperties(collectProperties(R.class));
        assertEquals(1, result.size());
        assertEquals(R.class, result.get(0).getType());

        result = resolveProperties(collectProperties(W.class));
        assertEquals(1, result.size());
        assertEquals(Integer.class, result.get(0).getType());
    }
}
