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

import org.stringtemplate.v4.*;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;


/**
 * 2013-02-17<p/>
 *
 * Represents a StringTemplate template that can be invoked with a set of arguments.
 *
 * @author Cameron Beccario
 */
final class TemplateHandleDecl implements TemplateHandle {

    // The template files are encoded in UTF-8.
    private static final String TEMPLATE_ENCODING = "UTF-8";


    private final String name;  // the name of the template to load from the template group file (.stg)
    private final String[] resources;  // the other template group files (.stg) that are used by this template
    private final Configuration config;

    /**
     * Builds a new template handle.
     *
     * @param config the generator configuration.
     * @param name the name of the StringTemplate template to load.
     * @param resources the StringTemplate files (embedded as resources in the JAR) needed to invoke this template.
     */
    TemplateHandleDecl(Configuration config, String name, String... resources) {
        this.config = config;
        this.name = name;
        this.resources = resources.clone();
    }

    /**
     * Load a JAR resource as a StringTemplate group.
     *
     * @param resourceName the name of the resource to load.
     * @param errorCollector the error listener.
     * @return the loaded resource as a StringTemplate group.
     */
    private static STGroup newGroupFromResource(String resourceName, ErrorCollector errorCollector) {
        URL resource = GrainGeneratorDriver.class.getResource(resourceName);
        if (resource == null) {
            throw new RuntimeException(
                String.format("Cannot find resource '%s' via %s.", resource, GrainGeneratorDriver.class));
        }
        STGroup result = new STGroupFile(resource, TEMPLATE_ENCODING, '<', '>');
        result.setListener(errorCollector);
        return result;
    }

    /**
     * Loads a set of JAR resources as a StringTemplate group.
     *
     * @param resourceNames the names of the resources to load.
     * @param errorCollector the error listener.
     * @return the loaded resources imported into one StringTemplate group.
     */
    private static STGroup newGroupFromResources(String[] resourceNames, ErrorCollector errorCollector) {
        // CONSIDER: do this operation once and remember the result. Invariant for all schemas being generated.
        STGroup result = newGroupFromResource(resourceNames[0], errorCollector);
        for (int i = 1; i < resourceNames.length; i++) {
            STGroup dependency = newGroupFromResource(resourceNames[i], errorCollector);
            result.importTemplates(dependency);
        }
        return result;
    }

    /**
     * Invokes StringTemplate and collects the output as a String.
     */
    private String write(ST template) {
        try {
            StringWriter sw = new StringWriter(4096);
            STWriter stWriter = new AutoIndentWriter(sw, config.getLineSeparator());
            stWriter.setLineWidth(config.getLineWidth());
            template.write(stWriter);
            return sw.toString();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override public GenerationResult invoke(Map<String, Object> arguments) {
        // Load the StringTemplates from the JAR.
        ErrorCollector errorCollector = new ErrorCollector();
        STGroup group = newGroupFromResources(resources, errorCollector);

        // Create an instance of the template we want to invoke.
        ST template = group.getInstanceOf(name);
        if (template == null) {
            if (errorCollector.getErrors().isEmpty()) {
                throw new RuntimeException(
                    String.format("Template '%s' not found in '%s'", name, Arrays.toString(resources)));
            }
            throw new RuntimeException(
                String.format("Errors getting template '%s': %s", name, errorCollector.getErrors()));
        }

        // Add all the arguments.
        for (Map.Entry<String, Object> argument : arguments.entrySet()) {
            template.add(argument.getKey(), argument.getValue());
        }

        String output = write(template);
        return new GenerationResult(output, errorCollector.getErrors());
    }
}
