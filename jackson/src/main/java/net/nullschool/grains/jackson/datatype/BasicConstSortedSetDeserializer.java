package net.nullschool.grains.jackson.datatype;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.CollectionType;
import net.nullschool.collect.ConstSortedSet;

import java.util.List;

import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-06-07<p/>
 *
 * @author Cameron Beccario
 */
final class BasicConstSortedSetDeserializer extends AbstractBasicConstCollectionSerializer<ConstSortedSet> {

    private static final long serialVersionUID = 1;


    BasicConstSortedSetDeserializer(
        CollectionType setType,
        JsonDeserializer<?> elementDeserializer,
        TypeDeserializer elementTypeDeserializer) {

        super(setType, elementDeserializer, elementTypeDeserializer);
        if (!Comparable.class.isAssignableFrom(setType.getContentType().getRawClass())) {
            throw new IllegalArgumentException(String.format("%s element type %s does not implement %s",
                setType.getRawClass().getName(),
                setType.getContentType().getRawClass().getName(),
                Comparable.class));
        }
    }

    @Override JsonDeserializer<?> withDeserializers(JsonDeserializer<?> ed, TypeDeserializer etd) {
        return ed == elementDeserializer && etd == elementTypeDeserializer ?
            this :
            new BasicConstSortedSetDeserializer(collectionType, ed, etd);
    }

    @Override ConstSortedSet emptyResult() {
        return emptySortedSet(null);
    }

    @Override ConstSortedSet resultOf(Object element) {
        return sortedSetOf(null, element);
    }

    @Override ConstSortedSet asResult(List<Object> elements) {
        return asSortedSet(null, elements);
    }
}
