/*
 * Copyright 2013 Cameron Beccario
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.nullschool.grains.msgpack;

import net.nullschool.collect.*;
import net.nullschool.collect.basic.*;
import org.msgpack.MessagePack;
import org.msgpack.template.*;

import java.net.URI;
import java.util.*;


/**
 * 2013-06-05<p/>
 *
 * @author Cameron Beccario
 */
public final class MessagePackTools {

    private MessagePackTools() {
        throw new AssertionError();
    }

    private static class GrainsMessagePack extends MessagePack {

        GrainsMessagePack(GrainsTemplateRegistry registry) {
            super(Objects.requireNonNull(registry));
        }
    }

    public static MessagePack newGrainsMessagePack() {
        GrainsTemplateRegistry registry = new GrainsTemplateRegistry();
        addDefaultTemplates(registry);
        addDefaultCollectionTemplates(registry);
        addDefaultBuilders(registry);
        return new GrainsMessagePack(registry);
    }

    public static MessagePack newGrainsMessagePack(GrainsTemplateRegistry registry) {
        return new GrainsMessagePack(registry);
    }

    public static GrainsTemplateRegistry addDefaultTemplates(GrainsTemplateRegistry registry) {
        registry.register(Object.class, new LateTemplate());
        registry.register(URI.class, new URITemplate());
        registry.register(UUID.class, new UUIDTemplate());
        registry.register(Void.class, new VoidTemplate());
        return registry;
    }

    public static GrainsTemplateRegistry addDefaultBuilders(GrainsTemplateRegistry registry) {
        registry.registerBuilder(new EnumTemplateBuilder());
        registry.registerBuilder(new VerboseGrainTemplateBuilder(registry));
        return registry;
    }

    public static GrainsTemplateRegistry addDefaultCollectionTemplates(GrainsTemplateRegistry registry) {
        Template<?> lateTemplate = registry.lookup(Object.class);

        // ConstList
        registry.register(ConstList.class, new BasicConstListTemplate(lateTemplate));
        GenericTemplate listTemplate = new GenericCollectionTemplate(registry, BasicConstListTemplate.class);
        registry.registerGeneric(ConstCollection.class, listTemplate);
        registry.registerGeneric(ConstList.class, listTemplate);
        registry.registerGeneric(BasicConstList.class, listTemplate);

        // ConstSet
        registry.register(ConstSet.class, new BasicConstSetTemplate(lateTemplate));
        GenericTemplate setTemplate = new GenericCollectionTemplate(registry, BasicConstSetTemplate.class);
        registry.registerGeneric(ConstSet.class, setTemplate);
        registry.registerGeneric(BasicConstSet.class, setTemplate);

        // ConstSortedSet
        registry.register(ConstSortedSet.class, new BasicConstSortedSetTemplate(lateTemplate));
        GenericTemplate sortedSetTemplate = new GenericCollectionTemplate(registry, BasicConstSortedSetTemplate.class);
        registry.registerGeneric(ConstSortedSet.class, sortedSetTemplate);
        registry.registerGeneric(BasicConstSortedSet.class, sortedSetTemplate);

        // ConstMap
        registry.register(ConstMap.class, new BasicConstMapTemplate(lateTemplate, lateTemplate));
        GenericTemplate mapTemplate = new GenericMapTemplate(registry, BasicConstMapTemplate.class);
        registry.registerGeneric(ConstMap.class, mapTemplate);
        registry.registerGeneric(BasicConstMap.class, mapTemplate);

        // ConstSortedMap
        registry.register(ConstSortedMap.class, new BasicConstSortedMapTemplate(lateTemplate, lateTemplate));
        GenericTemplate sortedMapTemplate = new GenericMapTemplate(registry, BasicConstSortedMapTemplate.class);
        registry.registerGeneric(ConstSortedMap.class, sortedMapTemplate);
        registry.registerGeneric(BasicConstSortedMap.class, sortedMapTemplate);

        return registry;
    }
}
