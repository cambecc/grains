package net.nullschool.grains.generate;

import java.util.Map;


/**
 * 2013-02-17<p/>
 *
 * @author Cameron Beccario
 */
interface Template {

    GenerationResult invoke(Map<String, Object> arguments);
}
