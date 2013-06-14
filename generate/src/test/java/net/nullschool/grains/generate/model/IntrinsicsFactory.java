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
import net.nullschool.collect.ConstSortedMap;
import net.nullschool.collect.IteratorTools;
import net.nullschool.collect.MapIterator;
import net.nullschool.collect.MapTools;
import net.nullschool.collect.basic.BasicCollections;
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
        new SimpleGrainProperty("0", int.class),
        new SimpleGrainProperty("_char", char.class),
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

    private static final String[] $KEYS = $PROPERTIES.keySet().toArray(new String[26]);
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
        private final float _$float;
        private final int _0;
        private final char __char;
        private final BigDecimal bigDecimal;
        private final BigInteger bigInteger;
        private final boolean _boolean;
        private final Boolean boxedBoolean;
        private final Double boxedDouble;
        private final Float boxedFloat;
        private final Long boxedLong;
        private final Short boxedShort;
        private final byte _byte;
        private final char _char;
        private final Character character;
        private final double _double;
        private final Intrinsics.Color$ _enum;
        private final float _float;
        private final int _int;
        private final Integer integer;
        private final long _long;
        private final short _short;
        private final String string;
        private final URI URI;
        private final UUID UUID;
        private final Byte ボックス化バイト;

        private final ConstSortedMap<String, Object> $extensions;

        private IntrinsicsGrainImpl(
            String id, float _$float, int _0, char __char, BigDecimal bigDecimal, BigInteger bigInteger, 
            boolean _boolean, Boolean boxedBoolean, Double boxedDouble, Float boxedFloat, Long boxedLong, 
            Short boxedShort, byte _byte, char _char, Character character, double _double, Intrinsics.Color$ _enum, 
            float _float, int _int, Integer integer, long _long, short _short, String string, URI URI, 
            UUID UUID, Byte ボックス化バイト, 
            ConstSortedMap<String, Object> $extensions) {

            this.id = id;
            this._$float = _$float;
            this._0 = _0;
            this.__char = __char;
            this.bigDecimal = bigDecimal;
            this.bigInteger = bigInteger;
            this._boolean = _boolean;
            this.boxedBoolean = boxedBoolean;
            this.boxedDouble = boxedDouble;
            this.boxedFloat = boxedFloat;
            this.boxedLong = boxedLong;
            this.boxedShort = boxedShort;
            this._byte = _byte;
            this._char = _char;
            this.character = character;
            this._double = _double;
            this._enum = _enum;
            this._float = _float;
            this._int = _int;
            this.integer = integer;
            this._long = _long;
            this._short = _short;
            this.string = string;
            this.URI = URI;
            this.UUID = UUID;
            this.ボックス化バイト = ボックス化バイト;
            this.$extensions = $extensions;
        }

        public int size() { return 26 + $extensions.size(); }

        public MapIterator<String, Object> iterator() {
            return IteratorTools.chainMapIterators(new BasisIter($KEYS), $extensions.iterator());
        }

        public String getId() { return id; }
        public IntrinsicsGrain withId(String id) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public float get$float() { return _$float; }
        public IntrinsicsGrain with$float(float _$float) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public int get0() { return _0; }
        public IntrinsicsGrain with0(int _0) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public char get_char() { return __char; }
        public IntrinsicsGrain with_char(char __char) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public BigDecimal getBigDecimal() { return bigDecimal; }
        public IntrinsicsGrain withBigDecimal(BigDecimal bigDecimal) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public BigInteger getBigInteger() { return bigInteger; }
        public IntrinsicsGrain withBigInteger(BigInteger bigInteger) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public boolean isBoolean() { return _boolean; }
        public IntrinsicsGrain withBoolean(boolean _boolean) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public Boolean getBoxedBoolean() { return boxedBoolean; }
        public IntrinsicsGrain withBoxedBoolean(Boolean boxedBoolean) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public Double getBoxedDouble() { return boxedDouble; }
        public IntrinsicsGrain withBoxedDouble(Double boxedDouble) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public Float getBoxedFloat() { return boxedFloat; }
        public IntrinsicsGrain withBoxedFloat(Float boxedFloat) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public Long getBoxedLong() { return boxedLong; }
        public IntrinsicsGrain withBoxedLong(Long boxedLong) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public Short getBoxedShort() { return boxedShort; }
        public IntrinsicsGrain withBoxedShort(Short boxedShort) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public byte getByte() { return _byte; }
        public IntrinsicsGrain withByte(byte _byte) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public char getChar() { return _char; }
        public IntrinsicsGrain withChar(char _char) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public Character getCharacter() { return character; }
        public IntrinsicsGrain withCharacter(Character character) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public double getDouble() { return _double; }
        public IntrinsicsGrain withDouble(double _double) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public Intrinsics.Color$ getEnum() { return _enum; }
        public IntrinsicsGrain withEnum(Intrinsics.Color$ _enum) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public float getFloat() { return _float; }
        public IntrinsicsGrain withFloat(float _float) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public int getInt() { return _int; }
        public IntrinsicsGrain withInt(int _int) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public Integer getInteger() { return integer; }
        public IntrinsicsGrain withInteger(Integer integer) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public long getLong() { return _long; }
        public IntrinsicsGrain withLong(long _long) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public short getShort() { return _short; }
        public IntrinsicsGrain withShort(short _short) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public String getString() { return string; }
        public IntrinsicsGrain withString(String string) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public URI getURI() { return URI; }
        public IntrinsicsGrain withURI(URI URI) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public UUID getUUID() { return UUID; }
        public IntrinsicsGrain withUUID(UUID UUID) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public Byte getボックス化バイト() { return ボックス化バイト; }
        public IntrinsicsGrain withボックス化バイト(Byte ボックス化バイト) {
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                $extensions);
        }

        public Object get(Object $key) {
            switch ((String)$key) {
                case "id": return getId();
                case "$float": return get$float();
                case "0": return get0();
                case "_char": return get_char();
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
                case "0": return with0($value == null ? 0 : (int)$value);
                case "_char": return with_char($value == null ? 0 : (char)$value);
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
            ConstSortedMap<String, Object> $newExtensions =
                $dissoc ? $extensions.without($key) : $extensions.with($key, $value);
            if ($newExtensions == $extensions) {
                return this;
            }
            return new IntrinsicsGrainImpl(
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
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
            $builder._$float = this._$float;
            $builder._0 = this._0;
            $builder.__char = this.__char;
            $builder.bigDecimal = this.bigDecimal;
            $builder.bigInteger = this.bigInteger;
            $builder._boolean = this._boolean;
            $builder.boxedBoolean = this.boxedBoolean;
            $builder.boxedDouble = this.boxedDouble;
            $builder.boxedFloat = this.boxedFloat;
            $builder.boxedLong = this.boxedLong;
            $builder.boxedShort = this.boxedShort;
            $builder._byte = this._byte;
            $builder._char = this._char;
            $builder.character = this.character;
            $builder._double = this._double;
            $builder._enum = this._enum;
            $builder._float = this._float;
            $builder._int = this._int;
            $builder.integer = this.integer;
            $builder._long = this._long;
            $builder._short = this._short;
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
        private float _$float;
        private int _0;
        private char __char;
        private BigDecimal bigDecimal;
        private BigInteger bigInteger;
        private boolean _boolean;
        private Boolean boxedBoolean;
        private Double boxedDouble;
        private Float boxedFloat;
        private Long boxedLong;
        private Short boxedShort;
        private byte _byte;
        private char _char;
        private Character character;
        private double _double;
        private Intrinsics.Color$ _enum;
        private float _float;
        private int _int;
        private Integer integer;
        private long _long;
        private short _short;
        private String string;
        private URI URI;
        private UUID UUID;
        private Byte ボックス化バイト;

        private final TreeMap<String, Object> $extensions = new TreeMap<>();

        public int size() { return 26 + $extensions.size(); }

        public MapIterator<String, Object> iterator() {
            return IteratorTools.chainMapIterators(new BasisIter($KEYS), IteratorTools.newMapIterator($extensions));
        }

        public String getId() { return id; }
        public IntrinsicsBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public float get$float() { return _$float; }
        public IntrinsicsBuilder set$float(float _$float) {
            this._$float = _$float;
            return this;
        }

        public int get0() { return _0; }
        public IntrinsicsBuilder set0(int _0) {
            this._0 = _0;
            return this;
        }

        public char get_char() { return __char; }
        public IntrinsicsBuilder set_char(char __char) {
            this.__char = __char;
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

        public boolean isBoolean() { return _boolean; }
        public IntrinsicsBuilder setBoolean(boolean _boolean) {
            this._boolean = _boolean;
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

        public byte getByte() { return _byte; }
        public IntrinsicsBuilder setByte(byte _byte) {
            this._byte = _byte;
            return this;
        }

        public char getChar() { return _char; }
        public IntrinsicsBuilder setChar(char _char) {
            this._char = _char;
            return this;
        }

        public Character getCharacter() { return character; }
        public IntrinsicsBuilder setCharacter(Character character) {
            this.character = character;
            return this;
        }

        public double getDouble() { return _double; }
        public IntrinsicsBuilder setDouble(double _double) {
            this._double = _double;
            return this;
        }

        public Intrinsics.Color$ getEnum() { return _enum; }
        public IntrinsicsBuilder setEnum(Intrinsics.Color$ _enum) {
            this._enum = _enum;
            return this;
        }

        public float getFloat() { return _float; }
        public IntrinsicsBuilder setFloat(float _float) {
            this._float = _float;
            return this;
        }

        public int getInt() { return _int; }
        public IntrinsicsBuilder setInt(int _int) {
            this._int = _int;
            return this;
        }

        public Integer getInteger() { return integer; }
        public IntrinsicsBuilder setInteger(Integer integer) {
            this.integer = integer;
            return this;
        }

        public long getLong() { return _long; }
        public IntrinsicsBuilder setLong(long _long) {
            this._long = _long;
            return this;
        }

        public short getShort() { return _short; }
        public IntrinsicsBuilder setShort(short _short) {
            this._short = _short;
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
                case "0": return get0();
                case "_char": return get_char();
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
                case "0":
                    $original = get0();
                    set0($value == null ? 0 : (int)$value);
                    return $original;
                case "_char":
                    $original = get_char();
                    set_char($value == null ? 0 : (char)$value);
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
                id, _$float, _0, __char, bigDecimal, bigInteger, _boolean, boxedBoolean, boxedDouble, 
                boxedFloat, boxedLong, boxedShort, _byte, _char, character, _double, _enum, _float, 
                _int, integer, _long, _short, string, URI, UUID, ボックス化バイト, 
                BasicCollections.asSortedMap($extensions));
        }
    }
}
