package net.nullschool.grains.generate.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.Currency;
import java.util.UUID;
import javax.annotation.Generated;
import net.nullschool.collect.ConstCollection;
import net.nullschool.collect.ConstList;
import net.nullschool.collect.ConstMap;
import net.nullschool.collect.ConstSet;
import net.nullschool.collect.ConstSortedMap;
import net.nullschool.collect.ConstSortedSet;
import net.nullschool.grains.GrainBuilder;
import net.nullschool.grains.GrainFactoryRef;

/**
 * Composition of Complete and GrainBuilder. See {@link CompleteFactory}.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
@GrainFactoryRef(CompleteFactory.class)
public interface CompleteBuilder extends Complete, GrainBuilder {

    //
    // Complete Accessors
    //

    boolean getA();

    CompleteBuilder setA(boolean a);

    byte getB();

    CompleteBuilder setB(byte b);

    short getC();

    CompleteBuilder setC(short c);

    int getD();

    CompleteBuilder setD(int d);

    long getE();

    CompleteBuilder setE(long e);

    BigInteger getF();

    CompleteBuilder setF(BigInteger f);

    float getG();

    CompleteBuilder setG(float g);

    double getH();

    CompleteBuilder setH(double h);

    BigDecimal getI();

    CompleteBuilder setI(BigDecimal i);

    char getJ();

    CompleteBuilder setJ(char j);

    String getK();

    CompleteBuilder setK(String k);

    UUID getL();

    CompleteBuilder setL(UUID l);

    URI getM();

    CompleteBuilder setM(URI m);

    Currency getN();

    CompleteBuilder setN(Currency n);

    Complete.Color getO();

    CompleteBuilder setO(Complete.Color o);

    NodeGrain getP();

    CompleteBuilder setP(NodeGrain p);

    ConstCollection<Integer> getQ();

    CompleteBuilder setQ(ConstCollection<Integer> q);

    ConstCollection<NodeGrain> getR();

    CompleteBuilder setR(ConstCollection<NodeGrain> r);

    ConstList<Integer> getS();

    CompleteBuilder setS(ConstList<Integer> s);

    ConstList<NodeGrain> getT();

    CompleteBuilder setT(ConstList<NodeGrain> t);

    ConstSet<String> getU();

    CompleteBuilder setU(ConstSet<String> u);

    ConstSet<NodeGrain> getV();

    CompleteBuilder setV(ConstSet<NodeGrain> v);

    ConstMap<String, Integer> getW();

    CompleteBuilder setW(ConstMap<String, Integer> w);

    ConstMap<String, NodeGrain> getX();

    CompleteBuilder setX(ConstMap<String, NodeGrain> x);

    ConstSortedSet<String> getY();

    CompleteBuilder setY(ConstSortedSet<String> y);

    ConstSortedMap<Integer, NodeGrain> getZ();

    CompleteBuilder setZ(ConstSortedMap<Integer, NodeGrain> z);

    ConstMap<String, ConstSet<ConstList<NodeGrain>>> getZa();

    CompleteBuilder setZa(ConstMap<String, ConstSet<ConstList<NodeGrain>>> za);


    //
    // GrainBuilder Methods
    //

    CompleteGrain build();
}
