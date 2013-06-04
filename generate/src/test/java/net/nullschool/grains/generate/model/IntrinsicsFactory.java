package net.nullschool.grains.generate.model;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import javax.annotation.Generated;
import net.nullschool.collect.ConstMap;
import net.nullschool.collect.IteratorTools;
import net.nullschool.collect.MapIterator;
import net.nullschool.collect.MapTools;
import net.nullschool.collect.basic.BasicConstMap;
import net.nullschool.grains.AbstractGrain;
import net.nullschool.grains.AbstractGrainBuilder;
import net.nullschool.grains.AbstractGrainProxy;
import net.nullschool.grains.GrainFactory;
import net.nullschool.grains.GrainFactoryRef;
import net.nullschool.grains.GrainProperty;
import net.nullschool.grains.GrainTools;
import net.nullschool.grains.SimpleGrainProperty;
import net.nullschool.reflect.PublicInterfaceRef;

/**
 * Factory for constructing Grain instances of Intrinsics.
 */
@Generated("net.nullschool.grains.generate.GrainGenerator")
public enum IntrinsicsFactory implements GrainFactory {
    INSTANCE;

    private static final ConstMap<String, GrainProperty> $PROPERTIES = GrainTools.asPropertyMap(
        new SimpleGrainProperty("id", String.class),
        new SimpleGrainProperty("$float", float.class),
        new SimpleGrainProperty("bigDecimal", BigDecimal.class),
        new SimpleGrainProperty("bigInteger", BigInteger.class),
        new SimpleGrainProperty("boolean", boolean.class, GrainProperty.Flag.IS_PROPERTY),
        new SimpleGrainProperty("boxedBoolean", Boolean.class),
        new SimpleGrainProperty("boxedDouble", Double.class),
        new SimpleGrainProperty("boxedFloat", Float.class),
        new SimpleGrainProperty("boxedLong", Long.class),
        new SimpleGrainProperty("boxedShort", Short.class),
        new SimpleGrainProperty("byte", byte.class),
        new SimpleGrainProperty("char", char.class),
        new SimpleGrainProperty("char_", char.class),
        new SimpleGrainProperty("character", Character.class),
        new SimpleGrainProperty("double", double.class),
        new SimpleGrainProperty("enum", Intrinsics.Color$.class),
        new SimpleGrainProperty("float", float.class),
        new SimpleGrainProperty("int", int.class),
        new SimpleGrainProperty("integer", Integer.class),
        new SimpleGrainProperty("long", long.class),
        new SimpleGrainProperty("short", short.class),
        new SimpleGrainProperty("string", String.class),
        new SimpleGrainProperty("URI", URI.class),
        new SimpleGrainProperty("UUID", UUID.class),
        new SimpleGrainProperty("ボックス化バイト", Byte.class));

    private static final String[] $KEYS = $PROPERTIES.keySet().toArray(new String[25]);
    private static final IntrinsicsGrain $DEFAULT = newBuilder().build();
    public static IntrinsicsGrain defaultValue() { return $DEFAULT; }
    public static IntrinsicsBuilder newBuilder() { return new IntrinsicsBuilderImpl(); }

    public ConstMap<String, GrainProperty> getBasisProperties() { return $PROPERTIES; }
    public IntrinsicsGrain getDefaultValue() { return defaultValue(); }
    public IntrinsicsBuilder getNewBuilder() { return newBuilder(); }
    public String toString() { return getClass().getName(); }

    /**
     * Code generated implementation of IntrinsicsGrain.
     */
    @PublicInterfaceRef(IntrinsicsGrain.class)
    @GrainFactoryRef(IntrinsicsFactory.class)
    private static final class IntrinsicsGrainImpl
        extends AbstractGrain
        implements IntrinsicsGrain, Serializable {

        private final String id;
        private final float $float_;
        private final BigDecimal bigDecimal;
        private final BigInteger bigInteger;
        private final boolean boolean_;
        private final Boolean boxedBoolean;
        private final Double boxedDouble;
        private final Float boxedFloat;
        private final Long boxedLong;
        private final Short boxedShort;
        private final byte byte_;
        private final char char_;
        private final char char__;
        private final Character character;
        private final double double_;
        private final Intrinsics.Color$ enum_;
        private final float float_;
        private final int int_;
        private final Integer integer;
        private final long long_;
        private final short short_;
        private final String string;
        private final URI URI;
        private final UUID UUID;
        private final Byte ボックス化バイト;

        private final ConstMap<String, Object> $extensions;

        private IntrinsicsGrainImpl(
            String id, float $float_, BigDecimal bigDecimal, BigInteger bigInteger, boolean boolean_, 
            Boolean boxedBoolean, Double boxedDouble, Float boxedFloat, Long boxedLong, Short boxedShort, 
            byte byte_, char char_, char char__, Character character, double double_, Intrinsics.Color$ enum_, 
            float float_, int int_, Integer integer, long long_, short short_, String string, URI URI, 
            UUID UUID, Byte ボックス化バイト, 
            ConstMap<String, Object> $extensions) {

            this.id = id;
            this.$float_ = $float_;
            this.bigDecimal = bigDecimal;
            this.bigInteger = bigInteger;
            this.boolean_ = boolean_;
            this.boxedBoolean = boxedBoolean;
            this.boxedDouble = boxedDouble;
            this.boxedFloat = boxedFloat;
            this.boxedLong = boxedLong;
            this.boxedShort = boxedShort;
            this.byte_ = byte_;
            this.char_ = char_;
            this.char__ = char__;
            this.character = character;
            this.double_ = double_;
            this.enum_ = enum_;
            this.float_ = float_;
            this.int_ = int_;
            this.integer = integer;
            this.long_ = long_;
            this.short_ = short_;
            this.string = string;
            this.URI = URI;
            this.UUID = UUID;
            this.ボックス化バイト = ボックス化バイト;
            this.$extensions = $extensions;
        }

        public int size() { return 25 + $extensions.size(); }

        public MapIterator<String, Object> iterator() {
            return IteratorTools.chainMapIterators(new BasisIter($KEYS), $extensions.iterator());
        }

        public String getId() { return id; }
        public IntrinsicsGrain withId(String id) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public float get$float() { return $float_; }
        public IntrinsicsGrain with$float(float $float_) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public BigDecimal getBigDecimal() { return bigDecimal; }
        public IntrinsicsGrain withBigDecimal(BigDecimal bigDecimal) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public BigInteger getBigInteger() { return bigInteger; }
        public IntrinsicsGrain withBigInteger(BigInteger bigInteger) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public boolean isBoolean() { return boolean_; }
        public IntrinsicsGrain withBoolean(boolean boolean_) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public Boolean getBoxedBoolean() { return boxedBoolean; }
        public IntrinsicsGrain withBoxedBoolean(Boolean boxedBoolean) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public Double getBoxedDouble() { return boxedDouble; }
        public IntrinsicsGrain withBoxedDouble(Double boxedDouble) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public Float getBoxedFloat() { return boxedFloat; }
        public IntrinsicsGrain withBoxedFloat(Float boxedFloat) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public Long getBoxedLong() { return boxedLong; }
        public IntrinsicsGrain withBoxedLong(Long boxedLong) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public Short getBoxedShort() { return boxedShort; }
        public IntrinsicsGrain withBoxedShort(Short boxedShort) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public byte getByte() { return byte_; }
        public IntrinsicsGrain withByte(byte byte_) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public char getChar() { return char_; }
        public IntrinsicsGrain withChar(char char_) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public char getChar_() { return char__; }
        public IntrinsicsGrain withChar_(char char__) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public Character getCharacter() { return character; }
        public IntrinsicsGrain withCharacter(Character character) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public double getDouble() { return double_; }
        public IntrinsicsGrain withDouble(double double_) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public Intrinsics.Color$ getEnum() { return enum_; }
        public IntrinsicsGrain withEnum(Intrinsics.Color$ enum_) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public float getFloat() { return float_; }
        public IntrinsicsGrain withFloat(float float_) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public int getInt() { return int_; }
        public IntrinsicsGrain withInt(int int_) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public Integer getInteger() { return integer; }
        public IntrinsicsGrain withInteger(Integer integer) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public long getLong() { return long_; }
        public IntrinsicsGrain withLong(long long_) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public short getShort() { return short_; }
        public IntrinsicsGrain withShort(short short_) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public String getString() { return string; }
        public IntrinsicsGrain withString(String string) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public URI getURI() { return URI; }
        public IntrinsicsGrain withURI(URI URI) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public UUID getUUID() { return UUID; }
        public IntrinsicsGrain withUUID(UUID UUID) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public Byte getボックス化バイト() { return ボックス化バイト; }
        public IntrinsicsGrain withボックス化バイト(Byte ボックス化バイト) {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public Object get(Object $key) {
            switch ((String)$key) {
                case "id": return getId();
                case "$float": return get$float();
                case "bigDecimal": return getBigDecimal();
                case "bigInteger": return getBigInteger();
                case "boolean": return isBoolean();
                case "boxedBoolean": return getBoxedBoolean();
                case "boxedDouble": return getBoxedDouble();
                case "boxedFloat": return getBoxedFloat();
                case "boxedLong": return getBoxedLong();
                case "boxedShort": return getBoxedShort();
                case "byte": return getByte();
                case "char": return getChar();
                case "char_": return getChar_();
                case "character": return getCharacter();
                case "double": return getDouble();
                case "enum": return getEnum();
                case "float": return getFloat();
                case "int": return getInt();
                case "integer": return getInteger();
                case "long": return getLong();
                case "short": return getShort();
                case "string": return getString();
                case "URI": return getURI();
                case "UUID": return getUUID();
                case "ボックス化バイト": return getボックス化バイト();
                default: return $extensions.get($key);
            }
        }

        private IntrinsicsGrain with(String $key, Object $value, boolean $dissoc) {
            switch ($key) {
                case "id": return withId((String)$value);
                case "$float": return with$float($value == null ? 0 : (float)$value);
                case "bigDecimal": return withBigDecimal((BigDecimal)$value);
                case "bigInteger": return withBigInteger((BigInteger)$value);
                case "boolean": return withBoolean($value == null ? false : (boolean)$value);
                case "boxedBoolean": return withBoxedBoolean((Boolean)$value);
                case "boxedDouble": return withBoxedDouble((Double)$value);
                case "boxedFloat": return withBoxedFloat((Float)$value);
                case "boxedLong": return withBoxedLong((Long)$value);
                case "boxedShort": return withBoxedShort((Short)$value);
                case "byte": return withByte($value == null ? 0 : (byte)$value);
                case "char": return withChar($value == null ? 0 : (char)$value);
                case "char_": return withChar_($value == null ? 0 : (char)$value);
                case "character": return withCharacter((Character)$value);
                case "double": return withDouble($value == null ? 0 : (double)$value);
                case "enum": return withEnum((Intrinsics.Color$)$value);
                case "float": return withFloat($value == null ? 0 : (float)$value);
                case "int": return withInt($value == null ? 0 : (int)$value);
                case "integer": return withInteger((Integer)$value);
                case "long": return withLong($value == null ? 0 : (long)$value);
                case "short": return withShort($value == null ? 0 : (short)$value);
                case "string": return withString((String)$value);
                case "URI": return withURI((URI)$value);
                case "UUID": return withUUID((UUID)$value);
                case "ボックス化バイト": return withボックス化バイト((Byte)$value);
            }
            ConstMap<String, Object> $newExtensions =
                $dissoc ? $extensions.without($key) : $extensions.with($key, $value);
            if ($newExtensions == $extensions) {
                return this;
            }
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                $newExtensions);
        }

        public IntrinsicsGrain with(String $key, Object $value) {
            return with($key, $value, false);
        }

        public IntrinsicsGrain withAll(Map<? extends String, ?> $map) {
            return $map.isEmpty() ? this : MapTools.putAll(newBuilder(), $map).build();
        }

        public IntrinsicsGrain without(Object $key) {
            return with((String)$key, null, true);
        }

        public IntrinsicsGrain withoutAll(Collection<?> $keys) {
            return $keys.isEmpty() ? this : MapTools.removeAll(newBuilder(), $keys).build();
        }

        public IntrinsicsBuilder newBuilder() {
            IntrinsicsBuilderImpl $builder = new IntrinsicsBuilderImpl();
            $builder.id = this.id;
            $builder.$float_ = this.$float_;
            $builder.bigDecimal = this.bigDecimal;
            $builder.bigInteger = this.bigInteger;
            $builder.boolean_ = this.boolean_;
            $builder.boxedBoolean = this.boxedBoolean;
            $builder.boxedDouble = this.boxedDouble;
            $builder.boxedFloat = this.boxedFloat;
            $builder.boxedLong = this.boxedLong;
            $builder.boxedShort = this.boxedShort;
            $builder.byte_ = this.byte_;
            $builder.char_ = this.char_;
            $builder.char__ = this.char__;
            $builder.character = this.character;
            $builder.double_ = this.double_;
            $builder.enum_ = this.enum_;
            $builder.float_ = this.float_;
            $builder.int_ = this.int_;
            $builder.integer = this.integer;
            $builder.long_ = this.long_;
            $builder.short_ = this.short_;
            $builder.string = this.string;
            $builder.URI = this.URI;
            $builder.UUID = this.UUID;
            $builder.ボックス化バイト = this.ボックス化バイト;
            $builder.$extensions.putAll(this.$extensions);
            return $builder;
        }

        public ConstMap<String, Object> extensions() {
            return $extensions;
        }

        private Object writeReplace() { return new IntrinsicsGrainProxy().setPayload(this); }
        private void readObject(ObjectInputStream in) throws InvalidObjectException {
            throw new InvalidObjectException("proxy expected");
        }
    }

    /**
     * Code generated serialization proxy for serializing instances of IntrinsicsGrainImpl.
     */
    private static final class IntrinsicsGrainProxy extends AbstractGrainProxy {
        private static final long serialVersionUID = 1;
        protected IntrinsicsBuilder newBuilder() { return IntrinsicsFactory.newBuilder(); }
    }

    /**
     * Code generated implementation of IntrinsicsBuilder.
     */
    @PublicInterfaceRef(IntrinsicsBuilder.class)
    @GrainFactoryRef(IntrinsicsFactory.class)
    private static final class IntrinsicsBuilderImpl
        extends AbstractGrainBuilder
        implements IntrinsicsBuilder {

        private String id;
        private float $float_;
        private BigDecimal bigDecimal;
        private BigInteger bigInteger;
        private boolean boolean_;
        private Boolean boxedBoolean;
        private Double boxedDouble;
        private Float boxedFloat;
        private Long boxedLong;
        private Short boxedShort;
        private byte byte_;
        private char char_;
        private char char__;
        private Character character;
        private double double_;
        private Intrinsics.Color$ enum_;
        private float float_;
        private int int_;
        private Integer integer;
        private long long_;
        private short short_;
        private String string;
        private URI URI;
        private UUID UUID;
        private Byte ボックス化バイト;

        private final TreeMap<String, Object> $extensions = new TreeMap<>();

        public int size() { return 25 + $extensions.size(); }

        public MapIterator<String, Object> iterator() {
            return IteratorTools.chainMapIterators(new BasisIter($KEYS), IteratorTools.newMapIterator($extensions));
        }

        public String getId() { return id; }
        public IntrinsicsBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public float get$float() { return $float_; }
        public IntrinsicsBuilder set$float(float $float_) {
            this.$float_ = $float_;
            return this;
        }

        public BigDecimal getBigDecimal() { return bigDecimal; }
        public IntrinsicsBuilder setBigDecimal(BigDecimal bigDecimal) {
            this.bigDecimal = bigDecimal;
            return this;
        }

        public BigInteger getBigInteger() { return bigInteger; }
        public IntrinsicsBuilder setBigInteger(BigInteger bigInteger) {
            this.bigInteger = bigInteger;
            return this;
        }

        public boolean isBoolean() { return boolean_; }
        public IntrinsicsBuilder setBoolean(boolean boolean_) {
            this.boolean_ = boolean_;
            return this;
        }

        public Boolean getBoxedBoolean() { return boxedBoolean; }
        public IntrinsicsBuilder setBoxedBoolean(Boolean boxedBoolean) {
            this.boxedBoolean = boxedBoolean;
            return this;
        }

        public Double getBoxedDouble() { return boxedDouble; }
        public IntrinsicsBuilder setBoxedDouble(Double boxedDouble) {
            this.boxedDouble = boxedDouble;
            return this;
        }

        public Float getBoxedFloat() { return boxedFloat; }
        public IntrinsicsBuilder setBoxedFloat(Float boxedFloat) {
            this.boxedFloat = boxedFloat;
            return this;
        }

        public Long getBoxedLong() { return boxedLong; }
        public IntrinsicsBuilder setBoxedLong(Long boxedLong) {
            this.boxedLong = boxedLong;
            return this;
        }

        public Short getBoxedShort() { return boxedShort; }
        public IntrinsicsBuilder setBoxedShort(Short boxedShort) {
            this.boxedShort = boxedShort;
            return this;
        }

        public byte getByte() { return byte_; }
        public IntrinsicsBuilder setByte(byte byte_) {
            this.byte_ = byte_;
            return this;
        }

        public char getChar() { return char_; }
        public IntrinsicsBuilder setChar(char char_) {
            this.char_ = char_;
            return this;
        }

        public char getChar_() { return char__; }
        public IntrinsicsBuilder setChar_(char char__) {
            this.char__ = char__;
            return this;
        }

        public Character getCharacter() { return character; }
        public IntrinsicsBuilder setCharacter(Character character) {
            this.character = character;
            return this;
        }

        public double getDouble() { return double_; }
        public IntrinsicsBuilder setDouble(double double_) {
            this.double_ = double_;
            return this;
        }

        public Intrinsics.Color$ getEnum() { return enum_; }
        public IntrinsicsBuilder setEnum(Intrinsics.Color$ enum_) {
            this.enum_ = enum_;
            return this;
        }

        public float getFloat() { return float_; }
        public IntrinsicsBuilder setFloat(float float_) {
            this.float_ = float_;
            return this;
        }

        public int getInt() { return int_; }
        public IntrinsicsBuilder setInt(int int_) {
            this.int_ = int_;
            return this;
        }

        public Integer getInteger() { return integer; }
        public IntrinsicsBuilder setInteger(Integer integer) {
            this.integer = integer;
            return this;
        }

        public long getLong() { return long_; }
        public IntrinsicsBuilder setLong(long long_) {
            this.long_ = long_;
            return this;
        }

        public short getShort() { return short_; }
        public IntrinsicsBuilder setShort(short short_) {
            this.short_ = short_;
            return this;
        }

        public String getString() { return string; }
        public IntrinsicsBuilder setString(String string) {
            this.string = string;
            return this;
        }

        public URI getURI() { return URI; }
        public IntrinsicsBuilder setURI(URI URI) {
            this.URI = URI;
            return this;
        }

        public UUID getUUID() { return UUID; }
        public IntrinsicsBuilder setUUID(UUID UUID) {
            this.UUID = UUID;
            return this;
        }

        public Byte getボックス化バイト() { return ボックス化バイト; }
        public IntrinsicsBuilder setボックス化バイト(Byte ボックス化バイト) {
            this.ボックス化バイト = ボックス化バイト;
            return this;
        }

        public Object get(Object $key) {
            switch ((String)$key) {
                case "id": return getId();
                case "$float": return get$float();
                case "bigDecimal": return getBigDecimal();
                case "bigInteger": return getBigInteger();
                case "boolean": return isBoolean();
                case "boxedBoolean": return getBoxedBoolean();
                case "boxedDouble": return getBoxedDouble();
                case "boxedFloat": return getBoxedFloat();
                case "boxedLong": return getBoxedLong();
                case "boxedShort": return getBoxedShort();
                case "byte": return getByte();
                case "char": return getChar();
                case "char_": return getChar_();
                case "character": return getCharacter();
                case "double": return getDouble();
                case "enum": return getEnum();
                case "float": return getFloat();
                case "int": return getInt();
                case "integer": return getInteger();
                case "long": return getLong();
                case "short": return getShort();
                case "string": return getString();
                case "URI": return getURI();
                case "UUID": return getUUID();
                case "ボックス化バイト": return getボックス化バイト();
                default: return $extensions.get($key);
            }
        }

        private Object put(String $key, Object $value, boolean $dissoc) {
            Object $original;
            switch ($key) {
                case "id":
                    $original = getId();
                    setId((String)$value);
                    return $original;
                case "$float":
                    $original = get$float();
                    set$float($value == null ? 0 : (float)$value);
                    return $original;
                case "bigDecimal":
                    $original = getBigDecimal();
                    setBigDecimal((BigDecimal)$value);
                    return $original;
                case "bigInteger":
                    $original = getBigInteger();
                    setBigInteger((BigInteger)$value);
                    return $original;
                case "boolean":
                    $original = isBoolean();
                    setBoolean($value == null ? false : (boolean)$value);
                    return $original;
                case "boxedBoolean":
                    $original = getBoxedBoolean();
                    setBoxedBoolean((Boolean)$value);
                    return $original;
                case "boxedDouble":
                    $original = getBoxedDouble();
                    setBoxedDouble((Double)$value);
                    return $original;
                case "boxedFloat":
                    $original = getBoxedFloat();
                    setBoxedFloat((Float)$value);
                    return $original;
                case "boxedLong":
                    $original = getBoxedLong();
                    setBoxedLong((Long)$value);
                    return $original;
                case "boxedShort":
                    $original = getBoxedShort();
                    setBoxedShort((Short)$value);
                    return $original;
                case "byte":
                    $original = getByte();
                    setByte($value == null ? 0 : (byte)$value);
                    return $original;
                case "char":
                    $original = getChar();
                    setChar($value == null ? 0 : (char)$value);
                    return $original;
                case "char_":
                    $original = getChar_();
                    setChar_($value == null ? 0 : (char)$value);
                    return $original;
                case "character":
                    $original = getCharacter();
                    setCharacter((Character)$value);
                    return $original;
                case "double":
                    $original = getDouble();
                    setDouble($value == null ? 0 : (double)$value);
                    return $original;
                case "enum":
                    $original = getEnum();
                    setEnum((Intrinsics.Color$)$value);
                    return $original;
                case "float":
                    $original = getFloat();
                    setFloat($value == null ? 0 : (float)$value);
                    return $original;
                case "int":
                    $original = getInt();
                    setInt($value == null ? 0 : (int)$value);
                    return $original;
                case "integer":
                    $original = getInteger();
                    setInteger((Integer)$value);
                    return $original;
                case "long":
                    $original = getLong();
                    setLong($value == null ? 0 : (long)$value);
                    return $original;
                case "short":
                    $original = getShort();
                    setShort($value == null ? 0 : (short)$value);
                    return $original;
                case "string":
                    $original = getString();
                    setString((String)$value);
                    return $original;
                case "URI":
                    $original = getURI();
                    setURI((URI)$value);
                    return $original;
                case "UUID":
                    $original = getUUID();
                    setUUID((UUID)$value);
                    return $original;
                case "ボックス化バイト":
                    $original = getボックス化バイト();
                    setボックス化バイト((Byte)$value);
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

        public IntrinsicsGrain build() {
            return new IntrinsicsGrainImpl(
                id, $float_, bigDecimal, bigInteger, boolean_, boxedBoolean, boxedDouble, boxedFloat, 
                boxedLong, boxedShort, byte_, char_, char__, character, double_, enum_, float_, int_, 
                integer, long_, short_, string, URI, UUID, ボックス化バイト, 
                BasicConstMap.asMap($extensions));
        }
    }
}
