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
public interface IntrinsicsBuilder extends Intrinsics, GrainBuilder {

    //
    // Intrinsics Accessors
    //

    String getId();

    IntrinsicsBuilder setId(String id);

    float get$float();

    IntrinsicsBuilder set$float(float $float_);

    BigDecimal getBigDecimal();

    IntrinsicsBuilder setBigDecimal(BigDecimal bigDecimal);

    BigInteger getBigInteger();

    IntrinsicsBuilder setBigInteger(BigInteger bigInteger);

    boolean isBoolean();

    IntrinsicsBuilder setBoolean(boolean boolean_);

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

    IntrinsicsBuilder setByte(byte byte_);

    char getChar();

    IntrinsicsBuilder setChar(char char_);

    char getChar_();

    IntrinsicsBuilder setChar_(char char__);

    Character getCharacter();

    IntrinsicsBuilder setCharacter(Character character);

    double getDouble();

    IntrinsicsBuilder setDouble(double double_);

    Intrinsics.Color$ getEnum();

    IntrinsicsBuilder setEnum(Intrinsics.Color$ enum_);

    float getFloat();

    IntrinsicsBuilder setFloat(float float_);

    int getInt();

    IntrinsicsBuilder setInt(int int_);

    Integer getInteger();

    IntrinsicsBuilder setInteger(Integer integer);

    long getLong();

    IntrinsicsBuilder setLong(long long_);

    short getShort();

    IntrinsicsBuilder setShort(short short_);

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
