package net.nullschool.grains;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 2013-04-19<p/>
 *
 * Defines which {@link GrainFactory} class can be used to construct instances of the annotated type. For example,
 * if {@code FooFactory} is the factory for {@code FooGrain}, then this association is made explicit with the
 * following:
 * <pre>
 * &#64;GrainFactoryRef(FooFactory.class)
 * interface FooGrain extends Grain {
 *     ...
 * }
 * </pre>
 *
 * This annotation makes it possible to retrieve the GrainFactory instance given the Grain's type using the
 * {@link GrainTools#factoryFor} utility method.
 *
 * @author Cameron Beccario
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface GrainFactoryRef {

    Class<? extends GrainFactory> value();
}
