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

import org.junit.Test;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.ThreadFactory;

import static org.junit.Assert.*;
import static net.nullschool.util.ThreadTools.*;
import static org.mockito.Mockito.*;

/**
 * 2013-04-05<p/>
 *
 * @author Cameron Beccario
 */
public class ThreadToolsTest {

    @Test
    public void test_daemon_thread_factory() {
        Runnable r = mock(Runnable.class);
        Thread thread = new Thread(r);
        thread.setDaemon(false);
        ThreadFactory factory = mock(ThreadFactory.class);
        when(factory.newThread(any(Runnable.class))).thenReturn(thread);

        assertSame(thread, newDaemonThreadFactory(factory).newThread(r));
        assertTrue(thread.isDaemon());
        verify(factory).newThread(r);
    }

    @Test
    public void test_naming_thread_factory() {
        Runnable r = mock(Runnable.class);
        Thread thread = new Thread(r);
        thread.setName("Foo");
        ThreadFactory factory = mock(ThreadFactory.class);
        when(factory.newThread(any(Runnable.class))).thenReturn(thread);

        assertSame(thread, newNamingThreadFactory("#%d %s", factory).newThread(r));
        assertEquals("#0 Foo", thread.getName());
        verify(factory).newThread(r);
    }

    @Test
    public void test_context_thread_factory() {
        Runnable r = mock(Runnable.class);
        Thread thread = new Thread(r);
        ThreadFactory factory = mock(ThreadFactory.class);
        when(factory.newThread(any(Runnable.class))).thenReturn(thread);

        ClassLoader cl = new URLClassLoader(new URL[] {});
        assertSame(thread, newContextThreadFactory(cl, factory).newThread(r));
        assertSame(cl, thread.getContextClassLoader());
        verify(factory).newThread(r);
    }
}
