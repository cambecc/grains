package net.nullschool.grains.msgpack;

import net.nullschool.collect.ConstMap;
import net.nullschool.collect.MapIterator;
import org.msgpack.packer.Packer;
import org.msgpack.template.Template;
import org.msgpack.unpacker.Unpacker;

import java.io.IOException;
import java.util.Objects;

import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-06-06<p/>
 *
 * @author Cameron Beccario
 */
public class BasicConstMapTemplate extends AbstractNullableTemplate<ConstMap> {

    private final Template<Object> keyTemplate;
    private final Template<Object> valueTemplate;

    public BasicConstMapTemplate(Template<?> keyTemplate, Template<?> valueTemplate) {
        @SuppressWarnings("unchecked") Template<Object> kt = (Template<Object>)keyTemplate;
        this.keyTemplate = Objects.requireNonNull(kt);
        @SuppressWarnings("unchecked") Template<Object> vt = (Template<Object>)valueTemplate;
        this.valueTemplate = Objects.requireNonNull(vt);
    }

    @Override protected void writeValue(Packer packer, ConstMap map) throws IOException {
        packer.writeMapBegin(map.size());
        for (MapIterator<?, ?> iter = map.iterator(); iter.hasNext();) {
            keyTemplate.write(packer, iter.next(), false);
            valueTemplate.write(packer, iter.value(), false);
        }
        packer.writeMapEnd();
    }

    @Override protected ConstMap readValue(Unpacker unpacker, ConstMap to) throws IOException {
        ConstMap<?, ?> result;
        final int size = unpacker.readMapBegin();
        switch (size) {
            case 0:
                result = emptyMap();
                break;
            case 1:
                result = mapOf(keyTemplate.read(unpacker, null, false), valueTemplate.read(unpacker, null, false));
                break;
            default:
                Object[] keys = new Object[size];
                Object[] values = new Object[size];
                for (int i = 0; i < size; i++) {
                    keys[i] = keyTemplate.read(unpacker, null, false);
                    values[i] = valueTemplate.read(unpacker, null, false);
                }
                result = asMap(keys, values);
        }
        unpacker.readMapEnd();
        return result;
    }

    @Override public String toString() {
        return String.format("%s<%s, %s>", getClass().getSimpleName(), keyTemplate, valueTemplate);
    }
}
