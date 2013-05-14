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

package org.slf4j.impl;

import net.nullschool.grains.generate.plugin.MavenLoggerFactory;
import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;


/**
 * 2013-02-27<p/>
 *
 * An implementation of SLF4J's static logger binder pattern that binds SLF4J to {@link MavenLoggerFactory}.
 *
 * @author Cameron Beccario
 */
public enum StaticLoggerBinder implements LoggerFactoryBinder {
    INSTANCE;

    public static StaticLoggerBinder getSingleton() {
        return INSTANCE;
    }

    public static final String REQUESTED_API_VERSION = "1.6.99";

    @Override public ILoggerFactory getLoggerFactory() {
        return MavenLoggerFactory.INSTANCE;
    }

    @Override public String getLoggerFactoryClassStr() {
        return MavenLoggerFactory.class.getName();
    }
}
