package net.nullschool.grains.generate;

import net.nullschool.grains.GrainSchema;
import net.nullschool.grains.generate.model.Intrinsics;
import net.nullschool.reflect.DefaultImmutabilityStrategy;
import org.junit.Test;


/**
 * 2013-05-10<p/>
 *
 * @author Cameron Beccario
 */
public class TypeTableTest {

    @GrainSchema(targetPackage = "com.acme")
    interface Foo {
    }

    @Test
    public void test_() {
        TypeTable table = new TypeTable(new NamingPolicy(), DefaultImmutabilityStrategy.INSTANCE);
        System.out.println(table.schemaTypes(Foo.class));
    }
}
