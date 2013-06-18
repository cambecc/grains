package net.nullschool.grains.generate.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
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
public interface IntrinsicsGrain
    extends Intrinsics, Grain {

    //
    // Intrinsics Accessors
    //

    String getId();

    IntrinsicsGrain withId(String id);

    float get$float();

    IntrinsicsGrain with$float(float _$float);

    int get0();

    IntrinsicsGrain with0(int _0);

    char get_char();

    IntrinsicsGrain with_char(char __char);

    BigDecimal getBigDecimal();

    IntrinsicsGrain withBigDecimal(BigDecimal bigDecimal);

    BigInteger getBigInteger();

    IntrinsicsGrain withBigInteger(BigInteger bigInteger);

    boolean isBoolean();

    IntrinsicsGrain withBoolean(boolean _boolean);

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

    IntrinsicsGrain withByte(byte _byte);

    char getChar();

    IntrinsicsGrain withChar(char _char);

    Character getCharacter();

    IntrinsicsGrain withCharacter(Character character);

    double getDouble();

    IntrinsicsGrain withDouble(double _double);

    Intrinsics.Color$ getEnum();

    IntrinsicsGrain withEnum(Intrinsics.Color$ _enum);

    float getFloat();

    IntrinsicsGrain withFloat(float _float);

    int getInt();

    IntrinsicsGrain withInt(int _int);

    Integer getInteger();

    IntrinsicsGrain withInteger(Integer integer);

    long getLong();

    IntrinsicsGrain withLong(long _long);

    short getShort();

    IntrinsicsGrain withShort(short _short);

    String getString();

    IntrinsicsGrain withString(String string);

    URI getURI();

    IntrinsicsGrain withURI(URI URI);

    UUID getUUID();

    IntrinsicsGrain withUUID(UUID UUID);

    Byte getボックス化バイト();

    IntrinsicsGrain withボックス化バイト(Byte ボックス化バイト);


    //
    // Grain Methods
    //

    ConstMap<String, Object> extensions();

    IntrinsicsGrain with(String key, Object value);

    IntrinsicsGrain withAll(Map<? extends String, ?> map);

    IntrinsicsGrain without(Object key);

    IntrinsicsGrain withoutAll(Collection<?> keys);

    IntrinsicsBuilder newBuilder();
}
