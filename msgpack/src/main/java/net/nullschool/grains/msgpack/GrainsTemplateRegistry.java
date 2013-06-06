package net.nullschool.grains.msgpack;

import net.nullschool.reflect.TypeTools;
import org.msgpack.MessageTypeException;
import org.msgpack.template.*;
import org.msgpack.template.builder.TemplateBuilder;

import java.lang.reflect.Type;
import java.util.*;


/**
 * 2013-06-06<p/>
 *
 * This class is not thread safe.
 *
 * @author Cameron Beccario
 */
public class GrainsTemplateRegistry extends TemplateRegistry {

    protected final List<TemplateBuilder> builders = new ArrayList<>();

    public GrainsTemplateRegistry() {
        super(null);
    }

    @Override protected void registerTemplatesWhichRefersRegistry() {
    }

    private Template<?> trySuperLookup(Type targetType) {
        try {
            return super.lookup(targetType);
        }
        catch (MessageTypeException e) {
            return null;
        }
    }

    public GrainsTemplateRegistry registerBuilder(TemplateBuilder builder) {
        builders.add(Objects.requireNonNull(builder));
        return this;
    }

    protected Template<?> buildAndRegisterTemplate(Type targetType) {
        for (TemplateBuilder builder : builders) {
            if (builder.matchType(targetType, false)) {
                Template<?> built = builder.buildTemplate(targetType);
                register(targetType, built);
                return built;
            }
        }
        return null;
    }

    @Override public void register(Class<?> targetClass) {
        Template<?> built = buildAndRegisterTemplate(targetClass);
        if (built == null) {
            super.register(targetClass);
        }
    }

    @Override public Template lookup(Type targetType) {
        Template result = trySuperLookup(targetType);

        if (result == null || result instanceof AnyTemplate) {
            // Lookup failed to find anything useful. Try building a template for the specified type.
            result = buildAndRegisterTemplate(targetType);
        }

        if (result == null) {
            throw new RuntimeException(
                String.format("Cannot find registered template for %s.", TypeTools.toString(targetType)));
        }

        return result;
    }

    @Override public boolean unregister(Type targetType) {
        return super.unregister(targetType);
    }

    @Override public void unregister() {
        builders.clear();
        super.unregister();
    }
}
