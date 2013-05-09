package com.acme.model;

import net.nullschool.grains.GrainSchema;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.UUID;


/**
 * 2013-05-08<p/>
 *
 * @author Cameron Beccario
 */
@GrainSchema
public interface Order {

    enum Side {buy, sell}

    UUID getId();

    String getInstrument();

    Side getSide();

    BigDecimal getPrice();

    BigDecimal getQuantity();

    DateTime getDate();
}
