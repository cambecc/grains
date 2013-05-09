//package net.nullschool.grains.generate;
//
//import net.nullschool.collect.ConstCollection;
//import net.nullschool.collect.ConstList;
//import net.nullschool.collect.ConstMap;
//import net.nullschool.collect.ConstSet;
//import net.nullschool.grains.*;
//import net.nullschool.grains.generate.model.Compound;
//import net.nullschool.grains.generate.model.PartGrain;
//import net.nullschool.reflect.DefaultImmutabilityStrategy;
//import net.nullschool.reflect.TypeToken;
//import org.junit.Test;
//
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.net.URI;
//import java.util.*;
//
//import static org.junit.Assert.*;
//
//
///**
// * 2013-03-28<p/>
// *
// * @author Cameron Beccario
// */
//public class TypesTest {
//
//    private Types t = new Types(DefaultImmutabilityStrategy.INSTANCE, null);
//
//    @Test
//    public void test_immutify_known_immutable_types() {
//        assertSame(boolean.class, t.immutify(boolean.class));
//        assertSame(byte.class, t.immutify(byte.class));
//        assertSame(short.class, t.immutify(short.class));
//        assertSame(int.class, t.immutify(int.class));
//        assertSame(long.class, t.immutify(long.class));
//        assertSame(float.class, t.immutify(float.class));
//        assertSame(double.class, t.immutify(double.class));
//        assertSame(char.class, t.immutify(char.class));
//
//        assertSame(Boolean.class, t.immutify(Boolean.class));
//        assertSame(Byte.class, t.immutify(Byte.class));
//        assertSame(Short.class, t.immutify(Short.class));
//        assertSame(Integer.class, t.immutify(Integer.class));
//        assertSame(Long.class, t.immutify(Long.class));
//        assertSame(BigInteger.class, t.immutify(BigInteger.class));
//        assertSame(BigDecimal.class, t.immutify(BigDecimal.class));
//        assertSame(Float.class, t.immutify(Float.class));
//        assertSame(Double.class, t.immutify(Double.class));
//        assertSame(Character.class, t.immutify(Character.class));
//        assertSame(String.class, t.immutify(String.class));
//        assertSame(UUID.class, t.immutify(UUID.class));
//        assertSame(URI.class, t.immutify(URI.class));
//        assertSame(Currency.class, t.immutify(Currency.class));
//    }
//
//    enum Color {red, green}
//
//    @Test
//    public void test_immutify_enum() {
//        assertSame(Enum.class, t.immutify(Enum.class));
//        assertSame(Color.class, t.immutify(Color.class));
//    }
//
//    @GrainSchema
//    interface Foo {
//    }
//
//    @Test
//    public void test_immutify_const_grain() {
//        assertSame(Grain.class, t.immutify(Grain.class));
//        assertSame(PartGrain.class, t.immutify(Compound.Part.class));
//        assertSame(PartGrain.class, t.immutify(PartGrain.class));
//        assertEquals("class net.nullschool.grains.generate.FooGrain", t.immutify(Foo.class).toString());
//    }
//
//    @Test
//    public void test_immutify_generic_types() {
//        assertEquals(
//            new TypeToken<ConstCollection<Integer>>(){}.asType(),
//            t.immutify(new TypeToken<Collection<Integer>>(){}.asType()));
//        assertEquals(
//            new TypeToken<ConstList<Integer>>(){}.asType(),
//            t.immutify(new TypeToken<List<Integer>>(){}.asType()));
//        assertEquals(
//            new TypeToken<ConstSet<Integer>>(){}.asType(),
//            t.immutify(new TypeToken<Set<Integer>>(){}.asType()));
//        assertEquals(
//            new TypeToken<ConstMap<String, UUID>>(){}.asType(),
//            t.immutify(new TypeToken<Map<String, UUID>>(){}.asType()));
//
//        assertEquals(
//            new TypeToken<ConstList<ConstList<Integer>>>(){}.asType(),
//            t.immutify(new TypeToken<List<List<Integer>>>(){}.asType()));
//        assertEquals(
//            new TypeToken<ConstMap<ConstList<UUID>, ConstSet<PartGrain>>>(){}.asType(),
//            t.immutify(new TypeToken<Map<List<UUID>, Set<Compound.Part>>>(){}.asType()));
//
//        assertEquals(
//            new TypeToken<ConstList<Integer>>(){}.asType(),
//            t.immutify(new TypeToken<List<? extends Integer>>(){}.asType()));
//        assertEquals(
//            new TypeToken<ConstList<ConstList<Integer>>>(){}.asType(),
//            t.immutify(new TypeToken<List<? extends List<? extends Integer>>>(){}.asType()));
//        assertEquals(
//            new TypeToken<ConstMap<ConstList<UUID>, ConstSet<PartGrain>>>(){}.asType(),
//            t.immutify(new TypeToken<Map<? extends List<? extends UUID>, Set<? extends Compound.Part>>>(){}.asType()));
//
//        assertEquals(
//            new TypeToken<ConstList<Integer>>(){}.asType(),
//            t.immutify(new TypeToken<ConstList<Integer>>(){}.asType()));
//        assertEquals(
//            new TypeToken<ConstList<ConstList<Integer>>>(){}.asType(),
//            t.immutify(new TypeToken<ConstList<ConstList<Integer>>>(){}.asType()));
//    }
//
//    @Test
//    public void test_immutify_fails_on_non_immutable_types() {
//
//        try { t.immutify(Object.class);     fail(); } catch (IllegalArgumentException expected) {}
//        try { t.immutify(Number.class);     fail(); } catch (IllegalArgumentException expected) {}
//        try { t.immutify(String[].class);   fail(); } catch (IllegalArgumentException expected) {}
//        try { t.immutify(List.class);       fail(); } catch (IllegalArgumentException expected) {}
//        try { t.immutify(Date.class);       fail(); } catch (IllegalArgumentException expected) {}
//
//        // raw const collections are not immutable because their type arguments are implicitly Object
//        try { t.immutify(ConstCollection.class);    fail(); } catch (IllegalArgumentException expected) {}
//        try { t.immutify(ConstList.class);          fail(); } catch (IllegalArgumentException expected) {}
//        try { t.immutify(ConstSet.class);           fail(); } catch (IllegalArgumentException expected) {}
//        try { t.immutify(ConstMap.class);           fail(); } catch (IllegalArgumentException expected) {}
//
//        try { t.immutify(new TypeToken<List<Object>>(){}.asType()); fail(); }
//        catch (IllegalArgumentException expected) {}
//
//        try { t.immutify(new TypeToken<List<List>>(){}.asType()); fail(); }
//        catch (IllegalArgumentException expected) {}
//
//        try { t.immutify(new TypeToken<List<ConstList>>(){}.asType()); fail(); }
//        catch (IllegalArgumentException expected) {}
//
//        try { t.immutify(new TypeToken<ConstList<ConstList>>(){}.asType()); fail(); }
//        catch (IllegalArgumentException expected) {}
//
//        try { t.immutify(new TypeToken<List<?>>(){}.asType()); fail(); }
//        catch (IllegalArgumentException expected) {}
//
//        try { t.immutify(new TypeToken<List<? extends Number>>(){}.asType()); fail(); }
//        catch (IllegalArgumentException expected) {}
//
//        try { t.immutify(new TypeToken<List<? extends ConstList>>(){}.asType()); fail(); }
//        catch (IllegalArgumentException expected) {}
//
//        try { t.immutify(new TypeToken<List<? super Integer>>(){}.asType()); fail(); }
//        catch (IllegalArgumentException expected) {}
//
//        try { t.immutify(new TypeToken<List<? extends List<? super Integer>>>(){}.asType()); fail(); }
//        catch (IllegalArgumentException expected) {}
//    }
//
////    static class Container<C> {
////
////        class Item<E> {
////        }
////
////        static class Node<E> {
////        }
////
////        static class ConstNode<E> extends Node<E> {
////        }
////    }
////
////    static class ContainerConst<C> extends Container<C> {
////
////        class ItemConst<E> extends Item<E> {
////        }
////    }
////
////    interface Bob {
////
////        Container<? extends Compound>.Item<? extends Compound> getItem();
////
////        Container.Node<? extends Compound> getNode();
////    }
////
////    interface BobConst extends Bob {
////
////        ContainerConst<CompoundConst>.ItemConst<CompoundConst> getFoo();
////
////        Container.ConstNode<CompoundConst> getNode();  // NOTE the "namespace" class is the enclosing class of ConstNode
////    }
////
//////    static abstract class Container<C> implements ConstList<C> {
//////
//////        abstract class SampleInnerConstList<E> implements ConstList<E> {
//////        }
//////
//////        abstract static class SampleNestedConstList<E> implements ConstList<E> {
//////        }
//////    }
////
////    @Test
////    public void test_immutify_derived_list() {
////
////        Container.SampleInnerConstList<Integer> x;
////        Container<String>.SampleNestedConstList<Integer> y;
////
////        assertEquals(
////            new TypeToken<SampleInnerConstList<Integer>>(){}.asType(),
////            t.immutify(new TypeToken<SampleInnerConstList<Integer>>(){}.asType()));
////        assertEquals(
////            new TypeToken<SampleNestedConstList<Integer>>(){}.asType(),
////            t.immutify(new TypeToken<SampleNestedConstList<Integer>>(){}.asType()));
////    }
////
////    interface Fun<T extends Fun<T>> extends ConstList<Fun<T>> {
////    }
////
////    @Test
////    public void test_recursive_type_param() {
////        t.immutify(Fun.class);
////    }
////
////    interface Bar<T extends Bar<T, U>, U> extends ConstList<U> {
////
////    }
////
////    @Test//(expected = IllegalArgumentException.class)
////    public void test_recursive_type_parameters() {
////        t.immutify(Bar.class);
////    }
////
////
//}
