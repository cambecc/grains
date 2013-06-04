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

import net.nullschool.collect.ConstSet;

import java.nio.charset.Charset;
import java.nio.file.Path;
import static net.nullschool.collect.basic.BasicCollections.*;


/**
 * 2013-02-16<p/>
 *
 * @author Cameron Beccario
 */
public final class Configuration {

    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String DEFAULT_LINE_WIDTH = "100";
    public static final String DEFAULT_LINE_SEPARATOR = "\n";
    public static final String DEFAULT_TYPE_POLICY =
        "net.nullschool.grains.DefaultTypePolicy.INSTANCE";


    private Charset charset = Charset.forName(DEFAULT_ENCODING);
    private int lineWidth = Integer.parseInt(DEFAULT_LINE_WIDTH);
    private String lineSeparator = DEFAULT_LINE_SEPARATOR;
    private Path output;
    private ConstSet<String> searchPackages = emptySet();
    private ClassLoader searchLoader;
    private String typePolicy = DEFAULT_TYPE_POLICY;

    public Charset getCharset() {
        return charset;
    }

    public Configuration setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public Configuration setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        return this;
    }

    public String getLineSeparator() {
        return lineSeparator;
    }

    public Configuration setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
        return this;
    }

    public Path getOutput() {
        return output;
    }

    public Configuration setOutput(Path output) {
        this.output = output;
        return this;
    }

    public ConstSet<String> getSearchPackages() {
        return searchPackages;
    }

    public Configuration setSearchPackages(ConstSet<String> searchPackages) {
        this.searchPackages = searchPackages;
        return this;
    }

    public ClassLoader getSearchLoader() {
        return searchLoader;
    }

    public Configuration setSearchLoader(ClassLoader searchLoader) {
        this.searchLoader = searchLoader;
        return this;
    }

    public String getTypePolicy() {
        return typePolicy;
    }

    public void setTypePolicy(String typePolicy) {
        this.typePolicy = typePolicy;
    }
}
