package net.nullschool.grains;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 2013-02-13<p/>
 *
 * Marks a type as a Grain schema. A Grain schema is an interface which defines the set of properties to use as the
 * basis for a generated grain implementation. The optional {@code targetPackage} attribute defines into which
 * package the code-generated output classes should be placed, defaulting to the schema's package when
 * {@code targetPackage} is undefined.
 *
 * @author Cameron Beccario
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface GrainSchema {

    /**
     * The package to use for the generated grain implementation.
     */
    String targetPackage() default "";
}
