package net.nullschool.grains.generate;

import net.nullschool.reflect.TypeToken;
import net.nullschool.reflect.TypeTools;
import org.junit.Test;
import net.nullschool.grains.generate.SimpleNameWriter.SimpleNamePrinter;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;


/**
 * 2013-05-10<p/>
 *
 * @author Cameron Beccario
 */
public class SimpleNameWriterTest {

    @Test
    public void test_parameterized_type() {
        Type type = new TypeToken<Set<Map.Entry<Map.Entry, Integer>>>(){}.asType();
        assertEquals("Set<Map.Entry<Map.Entry, Integer>>", TypeTools.toString(type, new SimpleNamePrinter()));
        assertEquals("Set<Entry<Entry, Integer>>", new SimpleNameWriter().invoke(type).toString());
    }
}
