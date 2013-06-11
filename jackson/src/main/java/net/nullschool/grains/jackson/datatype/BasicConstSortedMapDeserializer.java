package net.nullschool.grains.jackson.datatype;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.MapType;
import net.nullschool.collect.ConstSortedMap;
import net.nullschool.collect.basic.BasicCollections;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 2013-06-07<p/>
 *
 * @author Cameron Beccario
 */
final class BasicConstSortedMapDeserializer
    extends StdDeserializer<ConstSortedMap>
    implements ContextualDeserializer {

    private static final long serialVersionUID = 1;


    private final MapType mapType;
    private final KeyDeserializer keyDeserializer;
    private final JsonDeserializer<?> valueDeserializer;
    private final TypeDeserializer valueTypeDeserializer;

    public BasicConstSortedMapDeserializer(
        MapType mapType,
        KeyDeserializer keyDeserializer,
        JsonDeserializer<?> valueDeserializer,
        TypeDeserializer valueTypeDeserializer) {

        super(mapType.getRawClass());
        if (!Comparable.class.isAssignableFrom(mapType.getKeyType().getRawClass())) {
            throw new IllegalArgumentException(String.format("%s key type %s does not implement %s",
                mapType.getRawClass().getName(),
                mapType.getContentType().getRawClass().getName(),
                Comparable.class));
        }
        this.mapType = mapType;
        this.keyDeserializer = keyDeserializer;
        this.valueDeserializer = valueDeserializer;
        this.valueTypeDeserializer = valueTypeDeserializer;
    }

    @Override public JsonDeserializer<?> createContextual(
        DeserializationContext ctxt,
        BeanProperty property) throws JsonMappingException {

        KeyDeserializer kd = keyDeserializer != null ?
            keyDeserializer :
            ctxt.findKeyDeserializer(mapType.getKeyType(), property);
        JsonDeserializer<?> vd = valueDeserializer != null ?
            valueDeserializer :
            ctxt.findContextualValueDeserializer(mapType.getContentType(), property);
        TypeDeserializer vtd = valueTypeDeserializer != null ?
            valueTypeDeserializer.forProperty(property) :
            null;

        return kd == keyDeserializer && vd == valueDeserializer && vtd == valueTypeDeserializer ?
            this :
            new BasicConstSortedMapDeserializer(mapType, kd, vd, vtd);
    }

    @Override public Object deserializeWithType(
        JsonParser jp,
        DeserializationContext ctxt,
        TypeDeserializer typeDeserializer) throws IOException {

        return typeDeserializer.deserializeTypedFromObject(jp, ctxt);
    }

    @Override public ConstSortedMap<?, ?> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken token = jp.getCurrentToken();
        if (token == JsonToken.START_OBJECT) {
            token = jp.nextToken();
            if (token != JsonToken.FIELD_NAME && token != JsonToken.END_OBJECT) {
                throw ctxt.mappingException(mapType.getRawClass());
            }
        }
        else if (token != JsonToken.FIELD_NAME) {
            throw ctxt.mappingException(mapType.getRawClass());
        }

        KeyDeserializer kd = keyDeserializer;
        JsonDeserializer<?> vd = valueDeserializer;
        TypeDeserializer vtd = valueTypeDeserializer;

        List<Object> keys = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        while (token == JsonToken.FIELD_NAME) {
            String key = jp.getCurrentName();
            keys.add(kd != null ? kd.deserializeKey(key, ctxt) : key);
            values.add(jp.nextToken() == JsonToken.VALUE_NULL ?
                null :
                vtd != null ?
                    vd.deserializeWithType(jp, ctxt, vtd) :
                    vd.deserialize(jp, ctxt));
            token = jp.nextToken();
        }
        return BasicCollections.asSortedMap(null, keys.toArray(), values.toArray());
    }
}
