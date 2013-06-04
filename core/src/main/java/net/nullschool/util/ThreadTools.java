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

import java.util.IllegalFormatException;
import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 2013-02-09<p/>
 *
 * Utility methods for operating on threads.
 *
 * @author Cameron Beccario
 */
public class ThreadTools {

    private ThreadTools() {
        throw new AssertionError();
    }

    /**
     * Returns a {@link ThreadFactory} that configures threads constructed by an existing ThreadFactory as
     * {@link Thread#setDaemon daemon threads}.
     *
     * @param wrapped the thread factory to wrap.
     * @return a daemon thread factory.
     * @throws NullPointerException if {@code wrapped} is null.
     */
    public static ThreadFactory newDaemonThreadFactory(final ThreadFactory wrapped) {
        Objects.requireNonNull(wrapped);
        return new ThreadFactory() {
            @Override public Thread newThread(Runnable r) {
                Thread t = wrapped.newThread(r);
                if (!t.isDaemon()) {
                    t.setDaemon(true);
                }
                return t;
            }
        };
    }

    /**
     * Returns a {@link ThreadFactory} that configures threads constructed by an existing ThreadFactory with
     * names using the given format string, as described by {@link String#format}. The {@code format} string
     * can optionally make use of two substitutions: 1) an integer that increments upon each call to
     * {@link ThreadFactory#newThread}, and 2) the name of the thread as originally assigned by the wrapped
     * thread factory. For example, the format string {@code "Thread %s"} will cause this ThreadFactory to name
     * the resulting threads "Thread 0", "Thread 1", ... and so on.
     *
     * @param format the format string to be used for naming new threads.
     * @param wrapped the thread factory to wrap.
     * @return a thread factory that names threads according to the specified format.
     * @throws NullPointerException if {@code wrapped} or {@code format} is null.
     * @throws IllegalFormatException if {@code format} contains illegal syntax or is incompatible with the
     *                                described substitutions.
     */
    public static ThreadFactory newNamingThreadFactory(final String format, final ThreadFactory wrapped) {
        // noinspection ResultOfMethodCallIgnored
        String.format(format, 0, "test name");  // make sure the format string will work
        Objects.requireNonNull(wrapped);
        final AtomicInteger threadNumber = new AtomicInteger(0);
        return new ThreadFactory() {
            @Override public Thread newThread(Runnable r) {
                Thread t = wrapped.newThread(r);
                t.setName(String.format(format, threadNumber.getAndIncrement(), t.getName()));
                return t;
            }
        };
    }

    /**
     * Returns a {@link ThreadFactory} that configures threads constructed by an existing ThreadFactory with
     * the specified {@link Thread#setContextClassLoader context ClassLoader}. A null {@code classLoader}
     * indicates the system class loader.
     *
     * @param wrapped the thread factory to wrap.
     * @return a thread factory that configures new threads with a specific context ClassLoader.
     * @throws NullPointerException if {@code wrapped} is null.
     */
    public static ThreadFactory newContextThreadFactory(final ClassLoader classLoader, final ThreadFactory wrapped) {
        Objects.requireNonNull(wrapped);
        return new ThreadFactory() {
            @Override public Thread newThread(Runnable r) {
                Thread t = wrapped.newThread(r);
                t.setContextClassLoader(classLoader);
                return t;
            }
        };
    }
}
