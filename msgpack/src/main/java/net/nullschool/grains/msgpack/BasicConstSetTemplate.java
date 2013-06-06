package net.nullschool.grains.msgpack;

import net.nullschool.collect.ConstSet;
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
public class BasicConstSetTemplate extends AbstractNullableTemplate<ConstSet> {

    private final Template<Object> elementTemplate;

    public BasicConstSetTemplate(Template<?> elementTemplate) {
        @SuppressWarnings("unchecked") Template<Object> et = (Template<Object>)elementTemplate;
        this.elementTemplate = Objects.requireNonNull(et);
    }

    @Override protected void writeValue(Packer packer, ConstSet set) throws IOException {
        packer.writeArrayBegin(set.size());
        for (Object element : set) {
            elementTemplate.write(packer, element, false);
        }
        packer.writeArrayEnd();
    }

    @Override protected ConstSet readValue(Unpacker unpacker, ConstSet to) throws IOException {
        ConstSet<?> result;
        final int size = unpacker.readArrayBegin();
        switch (size) {
            case 0:
                result = emptySet();
                break;
            case 1:
                result = setOf(elementTemplate.read(unpacker, null, false));
                break;
            default:
                Object[] elements = new Object[size];
                for (int i = 0; i < size; i++) {
                    elements[i] = elementTemplate.read(unpacker, null, false);
                }
                result = asSet(elements);
        }
        unpacker.readArrayEnd();
        return result;
    }

    @Override public String toString() {
        return String.format("%s<%s>", getClass().getSimpleName(), elementTemplate);
    }
}
