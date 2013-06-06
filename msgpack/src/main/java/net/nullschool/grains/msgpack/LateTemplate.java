package net.nullschool.grains.msgpack;

import net.nullschool.collect.basic.BasicCollections;
import org.msgpack.packer.Packer;
import org.msgpack.type.*;
import org.msgpack.unpacker.Unpacker;

import java.io.IOException;


/**
 * 2013-06-05<p/>
 *
 * A MessagePack template for writing and reading objects of types known only at runtime.
 * <ul>
 *     <li>When writing, the object's runtime type is used to select an appropriate template.</li>
 *     <li>When reading, where there is no specified type for the resulting object, an object appropriate for
 *         representing the value embedded in the stream is constructed.</li>
 * </ul>
 *
 * @author Cameron Beccario
 */
public class LateTemplate extends AbstractNullableTemplate<Object> {

    @Override protected void writeValue(Packer packer, Object value) throws IOException {
        packer.write(value);
    }

    @Override protected Object readValue(Unpacker unpacker, Object to) throws IOException {
        return to == null ? processValue(unpacker.readValue()) : unpacker.read(to);
    }

    protected Object processBoolean(BooleanValue value) {
        return value.getBoolean();
    }

    protected Object processInteger(IntegerValue value) {
        long number = value.getLong();
        // prefer int representation if value fits.
        if (Integer.MIN_VALUE <= number && number <= Integer.MAX_VALUE) {
            return (int)number;
        }
        return number;
    }

    protected Object processFloat(FloatValue value) {
        return value.getDouble();
    }

    protected Object processArray(ArrayValue value) {
        Value[] payload = value.getElementArray();
        final int size = payload.length;
        Object[] elements = new Object[size];
        for (int i = 0; i < size; i++) {
            elements[i] = processValue(payload[i]);
        }
        return BasicCollections.asList(elements);
    }

    protected Object processMap(MapValue value) {
        Value[] payload = value.getKeyValueArray();
        final int size = payload.length / 2;
        Object[] keys = new Object[size];
        Object[] values = new Object[size];
        int pi = 0;
        for (int i = 0; i < size; i++) {
            keys[i] = processValue(payload[pi++]);
            values[i] = processValue(payload[pi++]);
        }
        return BasicCollections.asMap(keys, values);
    }

    protected Object processRaw(RawValue value) {
        return value.getString();
    }

    protected Object processUnknown(Value value) {
        return value;
    }

    protected Object processValue(Value value) {
        switch (value.getType()) {
            case NIL: return null;
            case BOOLEAN: return processBoolean(value.asBooleanValue());
            case INTEGER: return processInteger(value.asIntegerValue());
            case FLOAT: return processFloat(value.asFloatValue());
            case ARRAY: return processArray(value.asArrayValue());
            case MAP: return processMap(value.asMapValue());
            case RAW: return processRaw(value.asRawValue());
            default: return processUnknown(value);
        }
    }
}
