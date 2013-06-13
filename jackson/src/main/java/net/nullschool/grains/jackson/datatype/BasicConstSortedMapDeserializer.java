package net.nullschool.grains.jackson.datatype;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.MapType;
import net.nullschool.collect.ConstSortedMap;

import java.util.List;

import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-06-07<p/>
 *
 * @author Cameron Beccario
 */
final class BasicConstSortedMapDeserializer extends AbstractBasicConstMapDeserializer<ConstSortedMap> {

    private static final long serialVersionUID = 1;


    BasicConstSortedMapDeserializer(
        MapType mapType,
        KeyDeserializer keyDeserializer,
        JsonDeserializer<?> valueDeserializer,
        TypeDeserializer valueTypeDeserializer) {

        super(mapType, keyDeserializer, valueDeserializer, valueTypeDeserializer);
        if (!Comparable.class.isAssignableFrom(mapType.getKeyType().getRawClass())) {
            throw new IllegalArgumentException(String.format("%s key type %s does not implement %s",
                mapType.getRawClass().getName(),
                mapType.getContentType().getRawClass().getName(),
                Comparable.class));
        }
    }

    @Override JsonDeserializer<?> withDeserializers(KeyDeserializer kd, JsonDeserializer<?> vd, TypeDeserializer vtd) {
        return kd == keyDeserializer && vd == valueDeserializer && vtd == valueTypeDeserializer ?
            this :
            new BasicConstSortedMapDeserializer(mapType, kd, vd, vtd);
    }

    @Override ConstSortedMap emptyResult() {
        return emptySortedMap(null);
    }

    @Override ConstSortedMap resultOf(Object key, Object value) {
        return sortedMapOf(null, key, value);
    }

    @Override ConstSortedMap asResult(List<Object> keys, List<Object> values) {
        return asSortedMap(null, keys.toArray(), values.toArray());
    }
}
