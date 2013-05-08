package net.nullschool.grains.generate.model;

import net.nullschool.grains.GrainSchema;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;


/**
 * 2013-02-09<p/>
 *
 * A Grain schema that uses all supported intrinsic types, except for collections. Also uses a variety of
 * names designed to cover as many naming corner cases as possible.
 *
 * @author Cameron Beccario
 */
@GrainSchema
public interface Intrinsics {

    enum Color$ {red, blue}

    boolean isBoolean();

    byte getByte();

    short getShort();

    int getInt();

    long getLong();

    float getFloat();

    float get$float();

    double getDouble();

    char getChar();

    char getChar_();

    Boolean getBoxedBoolean();

    Byte getボックス化バイト();

    Short getBoxedShort();

    Integer getInteger();

    Long getBoxedLong();

    Float getBoxedFloat();

    Double getBoxedDouble();

    Character getCharacter();

    String getString();

    String getId();

    BigInteger getBigInteger();

    BigDecimal getBigDecimal();

    UUID getUUID();

    Color$ getEnum();
}
