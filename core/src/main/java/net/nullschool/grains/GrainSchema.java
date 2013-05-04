package net.nullschool.grains;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 2013-02-13<p/>
 *
 * @author Cameron Beccario
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface GrainSchema {

    String targetPackage() default "";
}
