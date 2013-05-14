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
 * @author Cameron Beccario
 */
final class TemplateDecl implements Template {

    private static final String TEMPLATE_ENCODING = "UTF-8";


    private final String name;
    private final String[] resources;
    private final Configuration config;

    TemplateDecl(Configuration config, String name, String... resources) {
        this.config = config;
        this.name = name;
        this.resources = resources.clone();
    }

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

    private static STGroup newGroupFromResources(String[] resourceNames, ErrorCollector errorCollector) {
        STGroup result = newGroupFromResource(resourceNames[0], errorCollector);
        for (int i = 1; i < resourceNames.length; i++) {
            STGroup dependency = newGroupFromResource(resourceNames[i], errorCollector);
            result.importTemplates(dependency);
        }
        return result;
    }

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

    @Override
    public GenerationResult invoke(Map<String, Object> arguments) {
        ErrorCollector errorCollector = new ErrorCollector();
        STGroup group = newGroupFromResources(resources, errorCollector);

        ST template = group.getInstanceOf(name);
        if (template == null) {
            if (errorCollector.getErrors().isEmpty()) {
                throw new RuntimeException(
                    String.format("Template '%s' not found in '%s'", name, Arrays.toString(resources)));
            }
            throw new RuntimeException(
                String.format("Errors getting template '%s': %s", name, errorCollector.getErrors()));
        }

        for (Map.Entry<String, Object> argument : arguments.entrySet()) {
            template.add(argument.getKey(), argument.getValue());
        }

        return new GenerationResult(write(template), errorCollector.getErrors());
    }
}
