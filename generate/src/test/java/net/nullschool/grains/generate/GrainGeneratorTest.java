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

package net.nullschool.grains.generate;

import net.nullschool.grains.generate.model.*;
import net.nullschool.grains.generate.model.Compound;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.Collection;


/**
 * 2013-03-24<p/>
 *
 * @author Cameron Beccario
 */
public class GrainGeneratorTest {

    private final Configuration config = new Configuration();

    private void print(Collection<String> errors) {
        for (String s : errors) {
            System.out.println(s);
        }
    }

    @Ignore @Test
    public void test_generate_const_interface() throws Exception {
        GrainGeneratorDriver generator = new GrainGeneratorDriver(config, new NamingPolicy());
        GenerationResult result = generator.generate(Compound.class, Templates.newGrainInterfaceTemplate(config));
        print(result.getErrors());
        System.out.println(result.getText());
    }

    @Ignore @Test
    public void test_generate_builder_interface() throws Exception {
        GrainGeneratorDriver generator = new GrainGeneratorDriver(config, new NamingPolicy());
        GenerationResult result = generator.generate(Compound.class, Templates.newBuilderInterfaceTemplate(config));
        print(result.getErrors());
        System.out.println(result.getText());
    }

    @Ignore @Test
    public void test_generate_factory_enum() throws Exception {
        GrainGeneratorDriver generator = new GrainGeneratorDriver(config, new NamingPolicy());
        GenerationResult result = generator.generate(Compound.class, Templates.newFactoryEnumTemplate(config));
        print(result.getErrors());
        System.out.println(result.getText());
    }

    @Ignore @Test
    public void test_generator_driver() throws Exception {
        GrainGenerator.main(
            new String[] {
                System.getProperty("user.home") + "/code/grains/generate/src/test/java",  // output
                "net.nullschool.grains.generate.model"});                                 // search packages
    }

    @Ignore @Test
    public void test_something() {
        PartGrain pc = PartFactory.DEFAULT().withMake(42);
        System.out.println(pc.toString());
        pc = pc.with("foo", "bar");
        pc = pc.with("something", pc);
        System.out.println(pc);
        PartBuilder builder = pc.builder();
        System.out.println(builder);
        builder.clear();
        System.out.println(builder);
        pc = pc.without("foo");
        pc = pc.without("something");
        pc = pc.without("value");

        pc = pc.with("test", "bob");
        System.out.println(pc);
        System.out.println(pc.keySet());
        System.out.println(pc.values());
        System.out.println(pc.entrySet());
    }

    @Ignore @Test
    public void test_serialization() throws Exception {

        CompoundBuilder cb = CompoundFactory.newBuilder();
        PartGrain pc = PartFactory.DEFAULT().withMake(42).with("hi", "bob");
        cb.setFirstPart(pc);
        cb.setSecondPart(pc);
        System.out.println(cb);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);
        out.writeObject(cb.build());
        out.close();
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));

        CompoundGrain cc = (CompoundGrain)in.readObject();
        System.out.println(System.identityHashCode(cc.getFirstPart()));
        System.out.println(System.identityHashCode(cc.getSecondPart()));
    }
}
