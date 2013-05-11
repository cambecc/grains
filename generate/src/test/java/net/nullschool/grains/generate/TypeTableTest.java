package net.nullschool.grains.generate;

import net.nullschool.grains.GrainSchema;
import net.nullschool.reflect.DefaultConstPolicy;
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
        TypeTable table = new TypeTable(new NamingPolicy(), DefaultConstPolicy.INSTANCE);
        System.out.println(table.schemaTypes(Foo.class));
    }
}
