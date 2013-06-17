package net.nullschool.grains.jackson.datatype;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import net.nullschool.collect.basic.BasicToolsTest;
import net.nullschool.grains.generate.model.*;
import net.nullschool.grains.jackson.JacksonTools;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static net.nullschool.collect.basic.BasicCollections.listOf;
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
    public void test_complete_serialization_json() throws IOException {
        CompleteGrain expected = newCompleteBuilderWithSampleValues().build();
        assertEquals(27, expected.size());

        ObjectMapper mapper = JacksonTools.newGrainsObjectMapper();
        String data = mapper.writeValueAsString(expected);

        assertEquals(
            "{'a':true,'b':1,'c':2,'d':2,'e':3,'f':1,'g':1.0,'h':-1.0,'i':10,'j':'a','k':'hello','l':" +
                "'1bd31d66-eda2-4395-a2a7-510bd581e3ab','m':'http://nullschool.net','o':'green','p':" +
                "{'id':1},'q':[1,2],'r':[{'id':1},{'id':2}],'s':[2,3],'t':[{'id':2},{'id':3}],'u':['a','b'],'v':" +
                "[{'id':4},{'id':5}],'w':{'a':1,'b':2},'x':{'a':{'id':6},'b':{'id':7}},'y':['x','y'],'z':{'1':" +
                "{'id':8},'2':{'id':9}},'za':{'a':[[{'id':1},{'id':2}]]},'zb':[['a','b'],['c','d']]}",
            data.replace('\"', '\''));  // replace quotes for sanity

        CompleteGrain actual = mapper.readValue(data, CompleteGrain.class);

        assertEquals(expected, actual);
        assertEquals(
            BasicToolsTest.asTypeHierarchy(expected),
            BasicToolsTest.asTypeHierarchy(actual));
    }

    @Test
    public void test_complete_serialization_smile() throws IOException {
        CompleteGrain expected = newCompleteBuilderWithSampleValues().build();
        assertEquals(27, expected.size());

        ObjectMapper mapper = JacksonTools.newGrainsObjectMapper(new SmileFactory());
        byte[] data = mapper.writeValueAsBytes(expected);

        assertEquals(
            ":)a1fa80a#80bc280cc480dc480ec680f&810180g(3|00080h)1?x000000080i*80815080j@a80kDhello80lc1bd31d6" +
                "6-eda2-4395-a2a7-510bd581e3ab80mThttp://nullschool.net80oDgreen80pfa81idc2fb80qf8c2c4f980rf8" +
                "faOc2fbfaOc4fbf980sf8c4c6f980tf8faOc4fbfaOc6fbf980uf8@a@bf980vf8faOc8fbfaOcafbf980wfa@c2Ac4f" +
                "b80xfa@faOccfbAfaOcefbfb80yf8@x@yf980zfa801faOd0fb802faOd2fbfb81zafa@f8f8faOc2fbfaOc4fbf9f9f" +
                "b81zbf8f8@a@bf9f8@c@df9f9fb",
            BasicToolsTest.asReadableString(data));

        CompleteGrain actual = mapper.readValue(data, CompleteGrain.class);

        assertEquals(expected, actual);
        assertEquals(
            BasicToolsTest.asTypeHierarchy(expected),
            BasicToolsTest.asTypeHierarchy(actual));
    }

    @Ignore @Test
    public void test_complete_serialization_xml() throws IOException {
        // UNDONE: xml support
        CompleteGrain expected = newCompleteBuilderWithSampleValues().build();
        assertEquals(27, expected.size());

        ObjectMapper mapper = JacksonTools.newGrainsObjectMapper(new XmlFactory());
        String data = mapper.writeValueAsString(expected);

        assertEquals(
            "",
            data);

        CompleteGrain actual = mapper.readValue(data, CompleteGrain.class);

        assertEquals(expected, actual);
        assertEquals(
            BasicToolsTest.asTypeHierarchy(expected),
            BasicToolsTest.asTypeHierarchy(actual));
    }

    @Test
    public void test_complete_serialization_yaml() throws IOException {
        CompleteGrain expected = newCompleteBuilderWithSampleValues().build();
        assertEquals(27, expected.size());

        ObjectMapper mapper = JacksonTools.newGrainsObjectMapper(new YAMLFactory());
        String data = mapper.writeValueAsString(expected);

        assertEquals(
            "---|a: true|b: 1|c: 2|d: 2|e: 3|f: 1|g: 1.0|h: -1.0|i: 10|j: 'a'|k: 'hello'|l: '1bd31d66-eda2-43" +
                "95-a2a7-510bd581e3ab'|m: 'http://nullschool.net'|o: 'green'|p:|  id: 1|q:|- 1|- 2|r:|- id: 1" +
                "|- id: 2|s:|- 2|- 3|t:|- id: 2|- id: 3|u:|- 'a'|- 'b'|v:|- id: 4|- id: 5|w:|  a: 1|  b: 2|x:" +
                "|  a:|    id: 6|  b:|    id: 7|y:|- 'x'|- 'y'|z:|  1:|    id: 8|  2:|    id: 9|za:|  a:|  - " +
                "- id: 1|    - id: 2|zb:|- - 'a'|  - 'b'|- - 'c'|  - 'd'|",
            data.replace('\"', '\'').replace('\n', '|'));  // replace quotes and line breaks for sanity

        CompleteGrain actual = mapper.readValue(data, CompleteGrain.class);

        assertEquals(expected, actual);
        assertEquals(
            BasicToolsTest.asTypeHierarchy(expected),
            BasicToolsTest.asTypeHierarchy(actual));
    }

    @Test
    public void test_sparse_serialization_json() throws IOException {
        CompleteGrain expected = CompleteFactory.defaultValue();

        ObjectMapper mapper = JacksonTools.newGrainsObjectMapper();
        String data = mapper.writeValueAsString(expected);

        assertEquals("{}", data);

        CompleteGrain actual = mapper.readValue(data, CompleteGrain.class);

        assertEquals(expected, actual);
        assertEquals(
            BasicToolsTest.asTypeHierarchy(expected),
            BasicToolsTest.asTypeHierarchy(actual));
    }

    @Test
    public void test_extension_serialization_json() throws IOException {
        NodeGrain expected = NodeFactory.newBuilder().setId(10).build();

        expected = expected.with("x", 1);
        expected = expected.with("y", 2);
        expected = expected.with("z", null);  // expected that this extension key will be dropped
        expected = expected.with("extraMap", mapOf("a", 1, "b", 2));
        expected = expected.with("extraList", listOf(1, 2));

        ObjectMapper mapper = JacksonTools.newGrainsObjectMapper();
        String data = mapper.writeValueAsString(expected);

        assertEquals(
            "{'id':10,'extraList':[1,2],'extraMap':{'a':1,'b':2},'x':1,'y':2}",
            data.replace('\"', '\''));

        NodeGrain actual = mapper.readValue(data, NodeGrain.class);

        expected = expected.without("z");
        assertEquals(expected, actual);
        assertEquals(
            BasicToolsTest.asTypeHierarchy(expected),
            BasicToolsTest.asTypeHierarchy(actual));
    }
}
