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
