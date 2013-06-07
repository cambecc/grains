package net.nullschool.grains.jackson.datatype;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.nullschool.collect.basic.BasicToolsTest;
import net.nullschool.grains.generate.model.*;
import net.nullschool.grains.jackson.JacksonTools;
import org.junit.Test;

import java.io.IOException;

import static net.nullschool.collect.basic.BasicCollections.mapOf;
import static net.nullschool.grains.generate.model.CompleteTest.newCompleteBuilderWithSampleValues;
import static org.junit.Assert.assertEquals;


/**
 * 2013-06-07<p/>
 *
 * @author Cameron Beccario
 */
public class JacksonTest {

    @Test
    public void test_complete_serialization() throws IOException {
        CompleteGrain expected = newCompleteBuilderWithSampleValues().build();
        assertEquals(27, expected.size());

        ObjectMapper mapper = JacksonTools.newGrainsObjectMapper();
        byte[] data = mapper.writeValueAsBytes(expected);

        assertEquals(
            "{'a':true,'b':1,'c':2,'d':2,'e':3,'f':1,'g':1.0,'h':-1.0,'i':10,'j':'a','k':'hello','l':" +
                "'1bd31d66-eda2-4395-a2a7-510bd581e3ab','m':'http://nullschool.net','n':'USD','o':'green','p':" +
                "{'id':1},'q':[1,2],'r':[{'id':1},{'id':2}],'s':[2,3],'t':[{'id':2},{'id':3}],'u':['a','b'],'v':" +
                "[{'id':4},{'id':5}],'w':{'a':1,'b':2},'x':{'a':{'id':6},'b':{'id':7}},'y':['x','y'],'z':{'1':" +
                "{'id':8},'2':{'id':9}},'za':{'a':[[{'id':1},{'id':2}]]}}",
            BasicToolsTest.asReadableString(data).replace('\"', '\''));  // replace quotes for sanity

        CompleteGrain actual = mapper.readValue(data, CompleteGrain.class);

        assertEquals(expected, actual);
        assertEquals(
            BasicToolsTest.asTypeHierarchy(expected),
            BasicToolsTest.asTypeHierarchy(actual));
    }

    @Test
    public void test_sparse_serialization() throws IOException {
        CompleteGrain expected = CompleteFactory.defaultValue();

        ObjectMapper mapper = JacksonTools.newGrainsObjectMapper();
        byte[] data = mapper.writeValueAsBytes(expected);

        assertEquals(
            "{}",
            BasicToolsTest.asReadableString(data).replace('\"', '\''));

        CompleteGrain actual = mapper.readValue(data, CompleteGrain.class);

        assertEquals(expected, actual);
        assertEquals(
            BasicToolsTest.asTypeHierarchy(expected),
            BasicToolsTest.asTypeHierarchy(actual));
    }

    @Test
    public void test_extension_serialization() throws IOException {
        NodeGrain expected = NodeFactory.newBuilder().setId(10).build();

        expected = expected.with("x", 1);
        expected = expected.with("y", 2);
        expected = expected.with("z", null);  // expected that this extension key will be dropped
        expected = expected.with("extra", mapOf("a", 1, "b", 2));

        ObjectMapper mapper = JacksonTools.newGrainsObjectMapper();
        byte[] data = mapper.writeValueAsBytes(expected);

        assertEquals(
            "{'id':10,'extra':{'a':1,'b':2},'x':1,'y':2}",
            BasicToolsTest.asReadableString(data).replace('\"', '\''));

        NodeGrain actual = mapper.readValue(data, NodeGrain.class);

        expected = expected.without("z");
        assertEquals(expected, actual);
        assertEquals(
            BasicToolsTest.asTypeHierarchy(expected),
            BasicToolsTest.asTypeHierarchy(actual));
    }
}
