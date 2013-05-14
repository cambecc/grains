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

package net.nullschool.util;


import java.util.Locale;


/**
 * 2013-02-27<p/>
 *
 * Utility methods for operating on strings.
 *
 * @author Cameron Beccario
 */
public enum StringTools {;

    /**
     * Capitalizes the specified string by changing the first character to upper case using {@link Locale#ENGLISH}
     * rules. If the string is null or empty, this method does nothing.
     *
     * @param s the string whose first character is to be made upper case.
     * @return the capitalized string.
     */
    public static String capitalize(String s) {
        return s == null || s.isEmpty() ? s : s.substring(0, 1).toUpperCase(Locale.ENGLISH) + s.substring(1);
    }
}
