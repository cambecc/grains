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

import java.util.Map;


/**
 * 2013-02-17<p/>
 *
 * Represents a code generation template that can be invoked with a set of arguments.
 *
 * @author Cameron Beccario
 */
interface TemplateHandle {

    GenerationResult invoke(Map<String, Object> arguments);
}
