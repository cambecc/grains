package net.nullschool.grains.generate.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Generated;
import net.nullschool.collect.ConstMap;
import net.nullschool.grains.Grain;
import net.nullschool.grains.GrainFactoryRef;

/**
 * Composition of Intrinsics and Grain. See {@link IntrinsicsFactory}.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
@GrainFactoryRef(IntrinsicsFactory.class)
public interface IntrinsicsGrain extends Intrinsics, Grain {

    //
    // Intrinsics Accessors
    //

    String getId();

    IntrinsicsGrain withId(String id);

    float get$float();

    IntrinsicsGrain with$float(float $float_);

    BigDecimal getBigDecimal();

    IntrinsicsGrain withBigDecimal(BigDecimal bigDecimal);

    BigInteger getBigInteger();

    IntrinsicsGrain withBigInteger(BigInteger bigInteger);

    boolean isBoolean();

    IntrinsicsGrain withBoolean(boolean boolean_);

    Boolean getBoxedBoolean();

    IntrinsicsGrain withBoxedBoolean(Boolean boxedBoolean);

    Double getBoxedDouble();

    IntrinsicsGrain withBoxedDouble(Double boxedDouble);

    Float getBoxedFloat();

    IntrinsicsGrain withBoxedFloat(Float boxedFloat);

    Long getBoxedLong();

    IntrinsicsGrain withBoxedLong(Long boxedLong);

    Short getBoxedShort();

    IntrinsicsGrain withBoxedShort(Short boxedShort);

    byte getByte();

    IntrinsicsGrain withByte(byte byte_);

    char getChar();

    IntrinsicsGrain withChar(char char_);

    char getChar_();

    IntrinsicsGrain withChar_(char char__);

    Character getCharacter();

    IntrinsicsGrain withCharacter(Character character);

    double getDouble();

    IntrinsicsGrain withDouble(double double_);

    Intrinsics.Color$ getEnum();

    IntrinsicsGrain withEnum(Intrinsics.Color$ enum_);

    float getFloat();

    IntrinsicsGrain withFloat(float float_);

    int getInt();

    IntrinsicsGrain withInt(int int_);

    Integer getInteger();

    IntrinsicsGrain withInteger(Integer integer);

    long getLong();

    IntrinsicsGrain withLong(long long_);

    short getShort();

    IntrinsicsGrain withShort(short short_);

    String getString();

    IntrinsicsGrain withString(String string);

    UUID getUUID();

    IntrinsicsGrain withUUID(UUID UUID);

    Byte getボックス化バイト();

    IntrinsicsGrain withボックス化バイト(Byte ボックス化バイト);


    //
    // Grain Methods
    //

    IntrinsicsGrain with(String key, Object value);

    IntrinsicsGrain withAll(Map<? extends String, ?> map);

    IntrinsicsGrain without(Object key);

    IntrinsicsGrain withoutAll(Collection<?> keys);

    IntrinsicsBuilder builder();

    ConstMap<String, Object> extensions();
}
