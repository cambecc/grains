package net.nullschool.grains.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.nullschool.grains.jackson.datatype.*;


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
        return configureForGrains(new ObjectMapper());
    }

    /**
     * Constructs a new Jackson ObjectMapper instance configured for serialization and deserialization of Grains,
     * using the specified JsonFactory.
     */
    public static ObjectMapper newGrainsObjectMapper(JsonFactory jf) {
        return configureForGrains(new ObjectMapper(jf));
    }

    /**
     * Configures the provided ObjectMapper to enable serialization and deserialization of Grains and its supported
     * types.
     *
     * @param mapper the mapper to configure.
     * @return a mapper capable of serializing Grains.
     */
    public static ObjectMapper configureForGrains(ObjectMapper mapper) {
        mapper.registerModule(new ConstCollectionModule());
        mapper.registerModule(new GrainsModule());
        return mapper;
    }
}
