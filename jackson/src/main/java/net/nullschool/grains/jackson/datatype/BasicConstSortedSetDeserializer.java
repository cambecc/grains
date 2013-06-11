package net.nullschool.grains.jackson.datatype;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.CollectionType;
import net.nullschool.collect.ConstSortedSet;
import net.nullschool.collect.basic.BasicCollections;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 2013-06-07<p/>
 *
 * @author Cameron Beccario
 */
final class BasicConstSortedSetDeserializer
    extends StdDeserializer<ConstSortedSet>
    implements ContextualDeserializer {

    private static final long serialVersionUID = 1;


    private final CollectionType setType;
    private final JsonDeserializer<?> elementDeserializer;
    private final TypeDeserializer elementTypeDeserializer;

    public BasicConstSortedSetDeserializer(
        CollectionType setType,
        JsonDeserializer<?> elementDeserializer,
        TypeDeserializer elementTypeDeserializer) {

        super(setType.getRawClass());
        if (!Comparable.class.isAssignableFrom(setType.getContentType().getRawClass())) {
            throw new IllegalArgumentException(String.format("%s element type %s does not implement %s",
                setType.getRawClass().getName(),
                setType.getContentType().getRawClass().getName(),
                Comparable.class));
        }
        this.setType = setType;
        this.elementDeserializer = elementDeserializer;
        this.elementTypeDeserializer = elementTypeDeserializer;
    }

    @Override public JsonDeserializer<?> createContextual(
        DeserializationContext ctxt,
        BeanProperty property) throws JsonMappingException {

        JsonDeserializer<?> ed = elementDeserializer != null ?
            elementDeserializer :
            ctxt.findContextualValueDeserializer(setType.getContentType(), property);
        TypeDeserializer etd = elementTypeDeserializer != null ?
            elementTypeDeserializer.forProperty(property) :
            null;

        return ed == elementDeserializer && etd == elementTypeDeserializer ?
            this :
            new BasicConstSortedSetDeserializer(setType, ed, etd);
    }

    @Override public Object deserializeWithType(
        JsonParser jp,
        DeserializationContext ctxt,
        TypeDeserializer typeDeserializer) throws IOException {

        return typeDeserializer.deserializeTypedFromArray(jp, ctxt);
    }

    @Override public ConstSortedSet<?> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (jp.getCurrentToken() != JsonToken.START_ARRAY) {
            throw ctxt.mappingException(setType.getRawClass());
        }

        JsonDeserializer<?> ed = elementDeserializer;
        TypeDeserializer etd = elementTypeDeserializer;
        List<Object> elements = new ArrayList<>();
        JsonToken token;

        if (etd != null) {
            while ((token = jp.nextToken()) != JsonToken.END_ARRAY) {
                elements.add(token == JsonToken.VALUE_NULL ? null : ed.deserializeWithType(jp, ctxt, etd));
            }
        }
        else {
            while ((token = jp.nextToken()) != JsonToken.END_ARRAY) {
                elements.add(token == JsonToken.VALUE_NULL ? null : ed.deserialize(jp, ctxt));
            }
        }
        return BasicCollections.asSortedSet(null, elements);
    }
}
