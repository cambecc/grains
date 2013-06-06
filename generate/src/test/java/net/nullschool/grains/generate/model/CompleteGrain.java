package net.nullschool.grains.generate.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.Collection;
import java.util.Currency;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Generated;
import net.nullschool.collect.ConstCollection;
import net.nullschool.collect.ConstList;
import net.nullschool.collect.ConstMap;
import net.nullschool.collect.ConstSet;
import net.nullschool.collect.ConstSortedMap;
import net.nullschool.collect.ConstSortedSet;
import net.nullschool.grains.Grain;
import net.nullschool.grains.GrainFactoryRef;

/**
 * Composition of Complete and Grain. See {@link CompleteFactory}.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
@GrainFactoryRef(CompleteFactory.class)
public interface CompleteGrain extends Complete, Grain {

    //
    // Complete Accessors
    //

    boolean getA();

    CompleteGrain withA(boolean a);

    byte getB();

    CompleteGrain withB(byte b);

    short getC();

    CompleteGrain withC(short c);

    int getD();

    CompleteGrain withD(int d);

    long getE();

    CompleteGrain withE(long e);

    BigInteger getF();

    CompleteGrain withF(BigInteger f);

    float getG();

    CompleteGrain withG(float g);

    double getH();

    CompleteGrain withH(double h);

    BigDecimal getI();

    CompleteGrain withI(BigDecimal i);

    char getJ();

    CompleteGrain withJ(char j);

    String getK();

    CompleteGrain withK(String k);

    UUID getL();

    CompleteGrain withL(UUID l);

    URI getM();

    CompleteGrain withM(URI m);

    Currency getN();

    CompleteGrain withN(Currency n);

    Complete.Color getO();

    CompleteGrain withO(Complete.Color o);

    NodeGrain getP();

    CompleteGrain withP(NodeGrain p);

    ConstCollection<Integer> getQ();

    CompleteGrain withQ(ConstCollection<Integer> q);

    ConstCollection<NodeGrain> getR();

    CompleteGrain withR(ConstCollection<NodeGrain> r);

    ConstList<Integer> getS();

    CompleteGrain withS(ConstList<Integer> s);

    ConstList<NodeGrain> getT();

    CompleteGrain withT(ConstList<NodeGrain> t);

    ConstSet<String> getU();

    CompleteGrain withU(ConstSet<String> u);

    ConstSet<NodeGrain> getV();

    CompleteGrain withV(ConstSet<NodeGrain> v);

    ConstMap<String, Integer> getW();

    CompleteGrain withW(ConstMap<String, Integer> w);

    ConstMap<String, NodeGrain> getX();

    CompleteGrain withX(ConstMap<String, NodeGrain> x);

    ConstSortedSet<String> getY();

    CompleteGrain withY(ConstSortedSet<String> y);

    ConstSortedMap<Integer, NodeGrain> getZ();

    CompleteGrain withZ(ConstSortedMap<Integer, NodeGrain> z);

    ConstMap<String, ConstSet<ConstList<NodeGrain>>> getZa();

    CompleteGrain withZa(ConstMap<String, ConstSet<ConstList<NodeGrain>>> za);


    //
    // Grain Methods
    //

    ConstMap<String, Object> extensions();

    CompleteGrain with(String key, Object value);

    CompleteGrain withAll(Map<? extends String, ?> map);

    CompleteGrain without(Object key);

    CompleteGrain withoutAll(Collection<?> keys);

    CompleteBuilder newBuilder();
}
