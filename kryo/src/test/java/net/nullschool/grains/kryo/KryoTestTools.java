package net.nullschool.grains.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Comparator;


/**
 * 2013-06-04<p/>
 *
 * @author Cameron Beccario
 */
class KryoTestTools {

    public static class NoSerializer extends Serializer<Object> {

        @Override public void write(Kryo kryo, Output output, Object object) {
            throw new AssertionError("invocation not expected for type: " + object.getClass());
        }

        @Override public Object read(Kryo kryo, Input input, Class<Object> type) {
            throw new AssertionError("invocation not expected for type: " + type);
        }
    }

    public static class ComparatorSerializer extends Serializer<Comparator> {

        enum Types {
            Reverse,
        }

        @Override public void write(Kryo kryo, Output output, Comparator object) {
            if (object == Collections.reverseOrder()) {
                output.writeInt(Types.Reverse.ordinal());
                return;
            }
            throw new IllegalArgumentException("unknown: " + object);
        }

        @Override public Comparator read(Kryo kryo, Input input, Class<Comparator> type) {
            int ordinal = input.readInt();
            switch (Types.values()[ordinal]) {
                case Reverse: return Collections.reverseOrder();
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    static Kryo newTestKryo() {
        Kryo kryo = KryoTools.newGrainKryo();
        kryo.setDefaultSerializer(NoSerializer.class);
        return kryo;
    }

    static Object roundTrip(Object obj, ByteArrayOutputStream out) {
        return roundTrip(obj, out, newTestKryo(), newTestKryo());
    }

    static Object roundTrip(Object obj, ByteArrayOutputStream out, Kryo outKryo, Kryo inKryo) {
        Output output = new Output(out);
        outKryo.writeClassAndObject(output, obj);
        output.close();

        Input input = new Input(out.toByteArray());
        return inKryo.readClassAndObject(input);
    }
}
