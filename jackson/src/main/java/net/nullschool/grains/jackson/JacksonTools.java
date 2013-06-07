package net.nullschool.grains.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.nullschool.grains.jackson.datatype.GrainsModule;
import net.nullschool.grains.jackson.datatype.ser.GrainSerializerFactory;


/**
 * 2013-06-07<p/>
 *
 * @author Cameron Beccario
 */
public final class JacksonTools {

    private JacksonTools() {
        throw new AssertionError();
    }

    /**
     * Constructs a new Jackson ObjectMapper instance configured for serialization and deserialization of Grains.
     */
    public static ObjectMapper newGrainsObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializerFactory(new GrainSerializerFactory(null));
        mapper.registerModule(new GrainsModule());
        return mapper;
    }
}
