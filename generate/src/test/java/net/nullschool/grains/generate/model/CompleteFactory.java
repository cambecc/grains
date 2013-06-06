package net.nullschool.grains.generate.model;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.Collection;
import java.util.Currency;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import javax.annotation.Generated;
import net.nullschool.collect.ConstCollection;
import net.nullschool.collect.ConstList;
import net.nullschool.collect.ConstMap;
import net.nullschool.collect.ConstSet;
import net.nullschool.collect.ConstSortedMap;
import net.nullschool.collect.ConstSortedSet;
import net.nullschool.collect.IteratorTools;
import net.nullschool.collect.MapIterator;
import net.nullschool.collect.MapTools;
import net.nullschool.collect.basic.BasicCollections;
import net.nullschool.grains.AbstractGrain;
import net.nullschool.grains.AbstractGrainBuilder;
import net.nullschool.grains.AbstractGrainProxy;
import net.nullschool.grains.DefaultTypePolicy;
import net.nullschool.grains.GrainFactory;
import net.nullschool.grains.GrainFactoryRef;
import net.nullschool.grains.GrainProperty;
import net.nullschool.grains.GrainTools;
import net.nullschool.grains.SimpleGrainProperty;
import net.nullschool.grains.TypePolicy;
import net.nullschool.reflect.PublicInterfaceRef;
import net.nullschool.reflect.TypeToken;
import net.nullschool.transform.Transform;

/**
 * Factory for constructing Grain instances of Complete.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
public enum CompleteFactory implements GrainFactory {
    INSTANCE;

    private static final TypePolicy $POLICY = DefaultTypePolicy.INSTANCE;

    private static final TypeToken<ConstCollection<Integer>> $token0 =
        new TypeToken<ConstCollection<Integer>>(){};
    private static final TypeToken<ConstCollection<NodeGrain>> $token1 =
        new TypeToken<ConstCollection<NodeGrain>>(){};
    private static final TypeToken<ConstList<Integer>> $token2 =
        new TypeToken<ConstList<Integer>>(){};
    private static final TypeToken<ConstList<NodeGrain>> $token3 =
        new TypeToken<ConstList<NodeGrain>>(){};
    private static final TypeToken<ConstSet<String>> $token4 =
        new TypeToken<ConstSet<String>>(){};
    private static final TypeToken<ConstSet<NodeGrain>> $token5 =
        new TypeToken<ConstSet<NodeGrain>>(){};
    private static final TypeToken<ConstMap<String, Integer>> $token6 =
        new TypeToken<ConstMap<String, Integer>>(){};
    private static final TypeToken<ConstMap<String, NodeGrain>> $token7 =
        new TypeToken<ConstMap<String, NodeGrain>>(){};
    private static final TypeToken<ConstSortedSet<String>> $token8 =
        new TypeToken<ConstSortedSet<String>>(){};
    private static final TypeToken<ConstSortedMap<Integer, NodeGrain>> $token9 =
        new TypeToken<ConstSortedMap<Integer, NodeGrain>>(){};
    private static final TypeToken<ConstMap<String, ConstSet<ConstList<NodeGrain>>>> $token10 =
        new TypeToken<ConstMap<String, ConstSet<ConstList<NodeGrain>>>>(){};

    private static final ConstMap<String, GrainProperty> $PROPERTIES = GrainTools.asPropertyMap(
        new SimpleGrainProperty("a", boolean.class),
        new SimpleGrainProperty("b", byte.class),
        new SimpleGrainProperty("c", short.class),
        new SimpleGrainProperty("d", int.class),
        new SimpleGrainProperty("e", long.class),
        new SimpleGrainProperty("f", BigInteger.class),
        new SimpleGrainProperty("g", float.class),
        new SimpleGrainProperty("h", double.class),
        new SimpleGrainProperty("i", BigDecimal.class),
        new SimpleGrainProperty("j", char.class),
        new SimpleGrainProperty("k", String.class),
        new SimpleGrainProperty("l", UUID.class),
        new SimpleGrainProperty("m", URI.class),
        new SimpleGrainProperty("n", Currency.class),
        new SimpleGrainProperty("o", Complete.Color.class),
        new SimpleGrainProperty("p", NodeGrain.class),
        new SimpleGrainProperty("q", $token0.asType()),
        new SimpleGrainProperty("r", $token1.asType()),
        new SimpleGrainProperty("s", $token2.asType()),
        new SimpleGrainProperty("t", $token3.asType()),
        new SimpleGrainProperty("u", $token4.asType()),
        new SimpleGrainProperty("v", $token5.asType()),
        new SimpleGrainProperty("w", $token6.asType()),
        new SimpleGrainProperty("x", $token7.asType()),
        new SimpleGrainProperty("y", $token8.asType()),
        new SimpleGrainProperty("z", $token9.asType()),
        new SimpleGrainProperty("za", $token10.asType()));

    private static final String[] $KEYS = $PROPERTIES.keySet().toArray(new String[27]);
    private static final CompleteGrain $DEFAULT = newBuilder().build();
    public static CompleteGrain defaultValue() { return $DEFAULT; }
    public static CompleteBuilder newBuilder() { return new CompleteBuilderImpl(); }

    public ConstMap<String, GrainProperty> getBasisProperties() { return $PROPERTIES; }
    public CompleteGrain getDefaultValue() { return defaultValue(); }
    public CompleteBuilder getNewBuilder() { return newBuilder(); }
    public String toString() { return getClass().getName(); }

    private static final Transform<ConstCollection<Integer>> $transform0 =
        $POLICY.newTransform($token0);
    private static final Transform<ConstCollection<NodeGrain>> $transform1 =
        $POLICY.newTransform($token1);
    private static final Transform<ConstList<Integer>> $transform2 =
        $POLICY.newTransform($token2);
    private static final Transform<ConstList<NodeGrain>> $transform3 =
        $POLICY.newTransform($token3);
    private static final Transform<ConstSet<String>> $transform4 =
        $POLICY.newTransform($token4);
    private static final Transform<ConstSet<NodeGrain>> $transform5 =
        $POLICY.newTransform($token5);
    private static final Transform<ConstMap<String, Integer>> $transform6 =
        $POLICY.newTransform($token6);
    private static final Transform<ConstMap<String, NodeGrain>> $transform7 =
        $POLICY.newTransform($token7);
    private static final Transform<ConstSortedSet<String>> $transform8 =
        $POLICY.newTransform($token8);
    private static final Transform<ConstSortedMap<Integer, NodeGrain>> $transform9 =
        $POLICY.newTransform($token9);
    private static final Transform<ConstMap<String, ConstSet<ConstList<NodeGrain>>>> $transform10 =
        $POLICY.newTransform($token10);

    /**
     * Code generated implementation of CompleteGrain.
     */
    @PublicInterfaceRef(CompleteGrain.class)
    @GrainFactoryRef(CompleteFactory.class)
    private static final class CompleteGrainImpl
        extends AbstractGrain
        implements CompleteGrain, Serializable {

        private final boolean a;
        private final byte b;
        private final short c;
        private final int d;
        private final long e;
        private final BigInteger f;
        private final float g;
        private final double h;
        private final BigDecimal i;
        private final char j;
        private final String k;
        private final UUID l;
        private final URI m;
        private final Currency n;
        private final Complete.Color o;
        private final NodeGrain p;
        private final ConstCollection<Integer> q;
        private final ConstCollection<NodeGrain> r;
        private final ConstList<Integer> s;
        private final ConstList<NodeGrain> t;
        private final ConstSet<String> u;
        private final ConstSet<NodeGrain> v;
        private final ConstMap<String, Integer> w;
        private final ConstMap<String, NodeGrain> x;
        private final ConstSortedSet<String> y;
        private final ConstSortedMap<Integer, NodeGrain> z;
        private final ConstMap<String, ConstSet<ConstList<NodeGrain>>> za;

        private final ConstSortedMap<String, Object> $extensions;

        private CompleteGrainImpl(
            boolean a, byte b, short c, int d, long e, BigInteger f, float g, double h, BigDecimal i, 
            char j, String k, UUID l, URI m, Currency n, Complete.Color o, NodeGrain p, ConstCollection<Integer> q, 
            ConstCollection<NodeGrain> r, ConstList<Integer> s, ConstList<NodeGrain> t, ConstSet<String> u, 
            ConstSet<NodeGrain> v, ConstMap<String, Integer> w, ConstMap<String, NodeGrain> x, ConstSortedSet<String> y, 
            ConstSortedMap<Integer, NodeGrain> z, ConstMap<String, ConstSet<ConstList<NodeGrain>>> za, 
            ConstSortedMap<String, Object> $extensions) {

            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.e = e;
            this.f = f;
            this.g = g;
            this.h = h;
            this.i = i;
            this.j = j;
            this.k = k;
            this.l = l;
            this.m = m;
            this.n = n;
            this.o = o;
            this.p = p;
            this.q = q;
            this.r = r;
            this.s = s;
            this.t = t;
            this.u = u;
            this.v = v;
            this.w = w;
            this.x = x;
            this.y = y;
            this.z = z;
            this.za = za;
            this.$extensions = $extensions;
        }

        public int size() { return 27 + $extensions.size(); }

        public MapIterator<String, Object> iterator() {
            return IteratorTools.chainMapIterators(new BasisIter($KEYS), $extensions.iterator());
        }

        public boolean getA() { return a; }
        public CompleteGrain withA(boolean a) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public byte getB() { return b; }
        public CompleteGrain withB(byte b) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public short getC() { return c; }
        public CompleteGrain withC(short c) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public int getD() { return d; }
        public CompleteGrain withD(int d) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public long getE() { return e; }
        public CompleteGrain withE(long e) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public BigInteger getF() { return f; }
        public CompleteGrain withF(BigInteger f) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public float getG() { return g; }
        public CompleteGrain withG(float g) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public double getH() { return h; }
        public CompleteGrain withH(double h) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public BigDecimal getI() { return i; }
        public CompleteGrain withI(BigDecimal i) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public char getJ() { return j; }
        public CompleteGrain withJ(char j) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public String getK() { return k; }
        public CompleteGrain withK(String k) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public UUID getL() { return l; }
        public CompleteGrain withL(UUID l) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public URI getM() { return m; }
        public CompleteGrain withM(URI m) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public Currency getN() { return n; }
        public CompleteGrain withN(Currency n) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public Complete.Color getO() { return o; }
        public CompleteGrain withO(Complete.Color o) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public NodeGrain getP() { return p; }
        public CompleteGrain withP(NodeGrain p) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public ConstCollection<Integer> getQ() { return q; }
        public CompleteGrain withQ(ConstCollection<Integer> q) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public ConstCollection<NodeGrain> getR() { return r; }
        public CompleteGrain withR(ConstCollection<NodeGrain> r) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public ConstList<Integer> getS() { return s; }
        public CompleteGrain withS(ConstList<Integer> s) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public ConstList<NodeGrain> getT() { return t; }
        public CompleteGrain withT(ConstList<NodeGrain> t) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public ConstSet<String> getU() { return u; }
        public CompleteGrain withU(ConstSet<String> u) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public ConstSet<NodeGrain> getV() { return v; }
        public CompleteGrain withV(ConstSet<NodeGrain> v) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public ConstMap<String, Integer> getW() { return w; }
        public CompleteGrain withW(ConstMap<String, Integer> w) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public ConstMap<String, NodeGrain> getX() { return x; }
        public CompleteGrain withX(ConstMap<String, NodeGrain> x) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public ConstSortedSet<String> getY() { return y; }
        public CompleteGrain withY(ConstSortedSet<String> y) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public ConstSortedMap<Integer, NodeGrain> getZ() { return z; }
        public CompleteGrain withZ(ConstSortedMap<Integer, NodeGrain> z) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public ConstMap<String, ConstSet<ConstList<NodeGrain>>> getZa() { return za; }
        public CompleteGrain withZa(ConstMap<String, ConstSet<ConstList<NodeGrain>>> za) {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $extensions);
        }

        public Object get(Object $key) {
            switch ((String)$key) {
                case "a": return getA();
                case "b": return getB();
                case "c": return getC();
                case "d": return getD();
                case "e": return getE();
                case "f": return getF();
                case "g": return getG();
                case "h": return getH();
                case "i": return getI();
                case "j": return getJ();
                case "k": return getK();
                case "l": return getL();
                case "m": return getM();
                case "n": return getN();
                case "o": return getO();
                case "p": return getP();
                case "q": return getQ();
                case "r": return getR();
                case "s": return getS();
                case "t": return getT();
                case "u": return getU();
                case "v": return getV();
                case "w": return getW();
                case "x": return getX();
                case "y": return getY();
                case "z": return getZ();
                case "za": return getZa();
                default: return $extensions.get($key);
            }
        }

        private CompleteGrain with(String $key, Object $value, boolean $dissoc) {
            switch ($key) {
                case "a": return withA($value == null ? false : (boolean)$value);
                case "b": return withB($value == null ? 0 : (byte)$value);
                case "c": return withC($value == null ? 0 : (short)$value);
                case "d": return withD($value == null ? 0 : (int)$value);
                case "e": return withE($value == null ? 0 : (long)$value);
                case "f": return withF((BigInteger)$value);
                case "g": return withG($value == null ? 0 : (float)$value);
                case "h": return withH($value == null ? 0 : (double)$value);
                case "i": return withI((BigDecimal)$value);
                case "j": return withJ($value == null ? 0 : (char)$value);
                case "k": return withK((String)$value);
                case "l": return withL((UUID)$value);
                case "m": return withM((URI)$value);
                case "n": return withN((Currency)$value);
                case "o": return withO((Complete.Color)$value);
                case "p": return withP((NodeGrain)$value);
                case "q": return withQ($transform0.apply($value));
                case "r": return withR($transform1.apply($value));
                case "s": return withS($transform2.apply($value));
                case "t": return withT($transform3.apply($value));
                case "u": return withU($transform4.apply($value));
                case "v": return withV($transform5.apply($value));
                case "w": return withW($transform6.apply($value));
                case "x": return withX($transform7.apply($value));
                case "y": return withY($transform8.apply($value));
                case "z": return withZ($transform9.apply($value));
                case "za": return withZa($transform10.apply($value));
            }
            ConstSortedMap<String, Object> $newExtensions =
                $dissoc ? $extensions.without($key) : $extensions.with($key, $value);
            if ($newExtensions == $extensions) {
                return this;
            }
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                $newExtensions);
        }

        public CompleteGrain with(String $key, Object $value) {
            return with($key, $value, false);
        }

        public CompleteGrain withAll(Map<? extends String, ?> $map) {
            return $map.isEmpty() ? this : MapTools.putAll(newBuilder(), $map).build();
        }

        public CompleteGrain without(Object $key) {
            return with((String)$key, null, true);
        }

        public CompleteGrain withoutAll(Collection<?> $keys) {
            return $keys.isEmpty() ? this : MapTools.removeAll(newBuilder(), $keys).build();
        }

        public CompleteBuilder newBuilder() {
            CompleteBuilderImpl $builder = new CompleteBuilderImpl();
            $builder.a = this.a;
            $builder.b = this.b;
            $builder.c = this.c;
            $builder.d = this.d;
            $builder.e = this.e;
            $builder.f = this.f;
            $builder.g = this.g;
            $builder.h = this.h;
            $builder.i = this.i;
            $builder.j = this.j;
            $builder.k = this.k;
            $builder.l = this.l;
            $builder.m = this.m;
            $builder.n = this.n;
            $builder.o = this.o;
            $builder.p = this.p;
            $builder.q = this.q;
            $builder.r = this.r;
            $builder.s = this.s;
            $builder.t = this.t;
            $builder.u = this.u;
            $builder.v = this.v;
            $builder.w = this.w;
            $builder.x = this.x;
            $builder.y = this.y;
            $builder.z = this.z;
            $builder.za = this.za;
            $builder.$extensions.putAll(this.$extensions);
            return $builder;
        }

        public ConstMap<String, Object> extensions() {
            return $extensions;
        }

        private Object writeReplace() { return new CompleteGrainProxy().setPayload(this); }
        private void readObject(ObjectInputStream in) throws InvalidObjectException {
            throw new InvalidObjectException("proxy expected");
        }
    }

    /**
     * Code generated serialization proxy for serializing instances of CompleteGrainImpl.
     */
    private static final class CompleteGrainProxy extends AbstractGrainProxy {
        private static final long serialVersionUID = 1;
        protected CompleteBuilder newBuilder() { return CompleteFactory.newBuilder(); }
    }

    /**
     * Code generated implementation of CompleteBuilder.
     */
    @PublicInterfaceRef(CompleteBuilder.class)
    @GrainFactoryRef(CompleteFactory.class)
    private static final class CompleteBuilderImpl
        extends AbstractGrainBuilder
        implements CompleteBuilder {

        private boolean a;
        private byte b;
        private short c;
        private int d;
        private long e;
        private BigInteger f;
        private float g;
        private double h;
        private BigDecimal i;
        private char j;
        private String k;
        private UUID l;
        private URI m;
        private Currency n;
        private Complete.Color o;
        private NodeGrain p;
        private ConstCollection<Integer> q;
        private ConstCollection<NodeGrain> r;
        private ConstList<Integer> s;
        private ConstList<NodeGrain> t;
        private ConstSet<String> u;
        private ConstSet<NodeGrain> v;
        private ConstMap<String, Integer> w;
        private ConstMap<String, NodeGrain> x;
        private ConstSortedSet<String> y;
        private ConstSortedMap<Integer, NodeGrain> z;
        private ConstMap<String, ConstSet<ConstList<NodeGrain>>> za;

        private final TreeMap<String, Object> $extensions = new TreeMap<>();

        public int size() { return 27 + $extensions.size(); }

        public MapIterator<String, Object> iterator() {
            return IteratorTools.chainMapIterators(new BasisIter($KEYS), IteratorTools.newMapIterator($extensions));
        }

        public boolean getA() { return a; }
        public CompleteBuilder setA(boolean a) {
            this.a = a;
            return this;
        }

        public byte getB() { return b; }
        public CompleteBuilder setB(byte b) {
            this.b = b;
            return this;
        }

        public short getC() { return c; }
        public CompleteBuilder setC(short c) {
            this.c = c;
            return this;
        }

        public int getD() { return d; }
        public CompleteBuilder setD(int d) {
            this.d = d;
            return this;
        }

        public long getE() { return e; }
        public CompleteBuilder setE(long e) {
            this.e = e;
            return this;
        }

        public BigInteger getF() { return f; }
        public CompleteBuilder setF(BigInteger f) {
            this.f = f;
            return this;
        }

        public float getG() { return g; }
        public CompleteBuilder setG(float g) {
            this.g = g;
            return this;
        }

        public double getH() { return h; }
        public CompleteBuilder setH(double h) {
            this.h = h;
            return this;
        }

        public BigDecimal getI() { return i; }
        public CompleteBuilder setI(BigDecimal i) {
            this.i = i;
            return this;
        }

        public char getJ() { return j; }
        public CompleteBuilder setJ(char j) {
            this.j = j;
            return this;
        }

        public String getK() { return k; }
        public CompleteBuilder setK(String k) {
            this.k = k;
            return this;
        }

        public UUID getL() { return l; }
        public CompleteBuilder setL(UUID l) {
            this.l = l;
            return this;
        }

        public URI getM() { return m; }
        public CompleteBuilder setM(URI m) {
            this.m = m;
            return this;
        }

        public Currency getN() { return n; }
        public CompleteBuilder setN(Currency n) {
            this.n = n;
            return this;
        }

        public Complete.Color getO() { return o; }
        public CompleteBuilder setO(Complete.Color o) {
            this.o = o;
            return this;
        }

        public NodeGrain getP() { return p; }
        public CompleteBuilder setP(NodeGrain p) {
            this.p = p;
            return this;
        }

        public ConstCollection<Integer> getQ() { return q; }
        public CompleteBuilder setQ(ConstCollection<Integer> q) {
            this.q = q;
            return this;
        }

        public ConstCollection<NodeGrain> getR() { return r; }
        public CompleteBuilder setR(ConstCollection<NodeGrain> r) {
            this.r = r;
            return this;
        }

        public ConstList<Integer> getS() { return s; }
        public CompleteBuilder setS(ConstList<Integer> s) {
            this.s = s;
            return this;
        }

        public ConstList<NodeGrain> getT() { return t; }
        public CompleteBuilder setT(ConstList<NodeGrain> t) {
            this.t = t;
            return this;
        }

        public ConstSet<String> getU() { return u; }
        public CompleteBuilder setU(ConstSet<String> u) {
            this.u = u;
            return this;
        }

        public ConstSet<NodeGrain> getV() { return v; }
        public CompleteBuilder setV(ConstSet<NodeGrain> v) {
            this.v = v;
            return this;
        }

        public ConstMap<String, Integer> getW() { return w; }
        public CompleteBuilder setW(ConstMap<String, Integer> w) {
            this.w = w;
            return this;
        }

        public ConstMap<String, NodeGrain> getX() { return x; }
        public CompleteBuilder setX(ConstMap<String, NodeGrain> x) {
            this.x = x;
            return this;
        }

        public ConstSortedSet<String> getY() { return y; }
        public CompleteBuilder setY(ConstSortedSet<String> y) {
            this.y = y;
            return this;
        }

        public ConstSortedMap<Integer, NodeGrain> getZ() { return z; }
        public CompleteBuilder setZ(ConstSortedMap<Integer, NodeGrain> z) {
            this.z = z;
            return this;
        }

        public ConstMap<String, ConstSet<ConstList<NodeGrain>>> getZa() { return za; }
        public CompleteBuilder setZa(ConstMap<String, ConstSet<ConstList<NodeGrain>>> za) {
            this.za = za;
            return this;
        }

        public Object get(Object $key) {
            switch ((String)$key) {
                case "a": return getA();
                case "b": return getB();
                case "c": return getC();
                case "d": return getD();
                case "e": return getE();
                case "f": return getF();
                case "g": return getG();
                case "h": return getH();
                case "i": return getI();
                case "j": return getJ();
                case "k": return getK();
                case "l": return getL();
                case "m": return getM();
                case "n": return getN();
                case "o": return getO();
                case "p": return getP();
                case "q": return getQ();
                case "r": return getR();
                case "s": return getS();
                case "t": return getT();
                case "u": return getU();
                case "v": return getV();
                case "w": return getW();
                case "x": return getX();
                case "y": return getY();
                case "z": return getZ();
                case "za": return getZa();
                default: return $extensions.get($key);
            }
        }

        private Object put(String $key, Object $value, boolean $dissoc) {
            Object $original;
            switch ($key) {
                case "a":
                    $original = getA();
                    setA($value == null ? false : (boolean)$value);
                    return $original;
                case "b":
                    $original = getB();
                    setB($value == null ? 0 : (byte)$value);
                    return $original;
                case "c":
                    $original = getC();
                    setC($value == null ? 0 : (short)$value);
                    return $original;
                case "d":
                    $original = getD();
                    setD($value == null ? 0 : (int)$value);
                    return $original;
                case "e":
                    $original = getE();
                    setE($value == null ? 0 : (long)$value);
                    return $original;
                case "f":
                    $original = getF();
                    setF((BigInteger)$value);
                    return $original;
                case "g":
                    $original = getG();
                    setG($value == null ? 0 : (float)$value);
                    return $original;
                case "h":
                    $original = getH();
                    setH($value == null ? 0 : (double)$value);
                    return $original;
                case "i":
                    $original = getI();
                    setI((BigDecimal)$value);
                    return $original;
                case "j":
                    $original = getJ();
                    setJ($value == null ? 0 : (char)$value);
                    return $original;
                case "k":
                    $original = getK();
                    setK((String)$value);
                    return $original;
                case "l":
                    $original = getL();
                    setL((UUID)$value);
                    return $original;
                case "m":
                    $original = getM();
                    setM((URI)$value);
                    return $original;
                case "n":
                    $original = getN();
                    setN((Currency)$value);
                    return $original;
                case "o":
                    $original = getO();
                    setO((Complete.Color)$value);
                    return $original;
                case "p":
                    $original = getP();
                    setP((NodeGrain)$value);
                    return $original;
                case "q":
                    $original = getQ();
                    setQ($transform0.apply($value));
                    return $original;
                case "r":
                    $original = getR();
                    setR($transform1.apply($value));
                    return $original;
                case "s":
                    $original = getS();
                    setS($transform2.apply($value));
                    return $original;
                case "t":
                    $original = getT();
                    setT($transform3.apply($value));
                    return $original;
                case "u":
                    $original = getU();
                    setU($transform4.apply($value));
                    return $original;
                case "v":
                    $original = getV();
                    setV($transform5.apply($value));
                    return $original;
                case "w":
                    $original = getW();
                    setW($transform6.apply($value));
                    return $original;
                case "x":
                    $original = getX();
                    setX($transform7.apply($value));
                    return $original;
                case "y":
                    $original = getY();
                    setY($transform8.apply($value));
                    return $original;
                case "z":
                    $original = getZ();
                    setZ($transform9.apply($value));
                    return $original;
                case "za":
                    $original = getZa();
                    setZa($transform10.apply($value));
                    return $original;
                default:
                    return $dissoc ? $extensions.remove($key) : $extensions.put($key, $value);
            }
        }

        public Object put(String $key, Object $value) {
            return put($key, $value, false);
        }

        public Object remove(Object $key) {
            return put((String)$key, null, true);
        }

        public CompleteGrain build() {
            return new CompleteGrainImpl(
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, za, 
                BasicCollections.asSortedMap($extensions));
        }
    }
}
