package net.nullschool.grains.generate.model;

import net.nullschool.grains.GrainSchema;

import java.util.UUID;


/**
 * 2013-03-05<p/>
 *
 * @author Cameron Beccario
 */
@GrainSchema
public interface Composed extends Top.Left<UUID>, Top.Right<UUID> {

    String getName();
}
