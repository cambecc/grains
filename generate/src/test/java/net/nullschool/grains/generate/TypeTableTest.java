package net.nullschool.grains.generate;

import net.nullschool.grains.DefaultTypePolicy;
import net.nullschool.grains.GrainSchema;
import org.junit.Ignore;
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

    @Test @Ignore
    public void test() {
        TypeTable table = new TypeTable(new NamingPolicy(), DefaultTypePolicy.INSTANCE);
        System.out.println(table.schemaTypes(Foo.class));
    }
}
