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

import org.stringtemplate.v4.STErrorListener;
import org.stringtemplate.v4.misc.STMessage;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 2013-03-24<p/>
 *
 * Collects errors emitted by the StringTemplate engine for later reporting.
 *
 * @author Cameron Beccario
 */
final class ErrorCollector implements STErrorListener {

    private final Collection<String> errors = new CopyOnWriteArrayList<>();

    Collection<String> getErrors() {
        return errors;
    }

    @Override public void compileTimeError(STMessage msg) {
        errors.add("compileTimeError: " + msg);
    }

    @Override public void runTimeError(STMessage msg) {
        errors.add("runTimeError: " + msg);
    }

    @Override public void IOError(STMessage msg) {
        errors.add("IOError: " + msg);
    }

    @Override public void internalError(STMessage msg) {
        errors.add("internalError: " + msg);
    }
}
