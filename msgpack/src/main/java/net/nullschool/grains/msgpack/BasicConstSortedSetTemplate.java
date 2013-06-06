package net.nullschool.grains.msgpack;

import net.nullschool.collect.ConstSortedSet;
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
public class BasicConstSortedSetTemplate extends AbstractNullableTemplate<ConstSortedSet> {

    private final Template<Object> elementTemplate;

    public BasicConstSortedSetTemplate(Template<?> elementTemplate) {
        @SuppressWarnings("unchecked") Template<Object> et = (Template<Object>)elementTemplate;
        this.elementTemplate = Objects.requireNonNull(et);
    }

    @Override protected void writeValue(Packer packer, ConstSortedSet set) throws IOException {
        packer.writeArrayBegin(set.size());
        for (Object element : set) {
            elementTemplate.write(packer, element, false);
        }
        packer.writeArrayEnd();
    }

    @Override protected ConstSortedSet readValue(Unpacker unpacker, ConstSortedSet to) throws IOException {
        ConstSortedSet<?> result;
        final int size = unpacker.readArrayBegin();
        switch (size) {
            case 0:
                result = emptySortedSet(null);
                break;
            case 1:
                result = sortedSetOf(null, elementTemplate.read(unpacker, null, false));
                break;
            default:
                Object[] elements = new Object[size];
                for (int i = 0; i < size; i++) {
                    elements[i] = elementTemplate.read(unpacker, null, false);
                }
                result = asSortedSet(null, elements);
        }
        unpacker.readArrayEnd();
        return result;
    }

    @Override public String toString() {
        return String.format("%s<%s>", getClass().getSimpleName(), elementTemplate);
    }
}
