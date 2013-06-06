/*
 * Copyright 2013 Cameron Beccario
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.nullschool.grains.generate.model;

import net.nullschool.grains.GrainSchema;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.*;


/**
 * 2013-06-06<p/>
 *
 * @author Cameron Beccario
 */
@GrainSchema
public interface Complete {

    enum Color {red, green}

    @GrainSchema
    public interface Node {

        int getId();

        Complete getComplete();  // cyclic type declaration
    }

    boolean getA();

    byte getB();

    short getC();

    int getD();

    long getE();

    BigInteger getF();

    float getG();

    double getH();

    BigDecimal getI();

    char getJ();

    String getK();

    UUID getL();

    URI getM();

    Currency getN();

    Color getO();

    Node getP();

    Collection<Integer> getQ();

    Collection<? extends Node> getR();

    List<Integer> getS();

    List<? extends Node> getT();

    Set<String> getU();

    Set<? extends Node> getV();

    Map<String, Integer> getW();

    Map<String, ? extends Node> getX();

    SortedSet<String> getY();

    SortedMap<Integer, ? extends Node> getZ();

    Map<String, ? extends Set<? extends List<? extends Node>>> getZa();
}
