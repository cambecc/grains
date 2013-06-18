package net.nullschool.grains.generate.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.UUID;
import javax.annotation.Generated;
import net.nullschool.grains.GrainBuilder;
import net.nullschool.grains.GrainFactoryRef;

/**
 * Composition of Intrinsics and GrainBuilder. See {@link IntrinsicsFactory}.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
@GrainFactoryRef(IntrinsicsFactory.class)
public interface IntrinsicsBuilder
    extends Intrinsics, GrainBuilder {

    //
    // Intrinsics Accessors
    //

    String getId();

    IntrinsicsBuilder setId(String id);

    float get$float();

    IntrinsicsBuilder set$float(float _$float);

    int get0();

    IntrinsicsBuilder set0(int _0);

    char get_char();

    IntrinsicsBuilder set_char(char __char);

    BigDecimal getBigDecimal();

    IntrinsicsBuilder setBigDecimal(BigDecimal bigDecimal);

    BigInteger getBigInteger();

    IntrinsicsBuilder setBigInteger(BigInteger bigInteger);

    boolean isBoolean();

    IntrinsicsBuilder setBoolean(boolean _boolean);

    Boolean getBoxedBoolean();

    IntrinsicsBuilder setBoxedBoolean(Boolean boxedBoolean);

    Double getBoxedDouble();

    IntrinsicsBuilder setBoxedDouble(Double boxedDouble);

    Float getBoxedFloat();

    IntrinsicsBuilder setBoxedFloat(Float boxedFloat);

    Long getBoxedLong();

    IntrinsicsBuilder setBoxedLong(Long boxedLong);

    Short getBoxedShort();

    IntrinsicsBuilder setBoxedShort(Short boxedShort);

    byte getByte();

    IntrinsicsBuilder setByte(byte _byte);

    char getChar();

    IntrinsicsBuilder setChar(char _char);

    Character getCharacter();

    IntrinsicsBuilder setCharacter(Character character);

    double getDouble();

    IntrinsicsBuilder setDouble(double _double);

    Intrinsics.Color$ getEnum();

    IntrinsicsBuilder setEnum(Intrinsics.Color$ _enum);

    float getFloat();

    IntrinsicsBuilder setFloat(float _float);

    int getInt();

    IntrinsicsBuilder setInt(int _int);

    Integer getInteger();

    IntrinsicsBuilder setInteger(Integer integer);

    long getLong();

    IntrinsicsBuilder setLong(long _long);

    short getShort();

    IntrinsicsBuilder setShort(short _short);

    String getString();

    IntrinsicsBuilder setString(String string);

    URI getURI();

    IntrinsicsBuilder setURI(URI URI);

    UUID getUUID();

    IntrinsicsBuilder setUUID(UUID UUID);

    Byte getボックス化バイト();

    IntrinsicsBuilder setボックス化バイト(Byte ボックス化バイト);


    //
    // GrainBuilder Methods
    //

    IntrinsicsGrain build();
}
