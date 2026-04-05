/*
* Copyright 2026 ALE International
*
* Permission is hereby granted, free of charge, to any person obtaining a copy of this 
* software and associated documentation files (the "Software"), to deal in the Software 
* without restriction, including without limitation the rights to use, copy, modify, merge, 
* publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons 
* to whom the Software is furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all copies or 
* substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
* BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
* NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
* DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.ale.o2g.test;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

/**
 * Test utility that captures SLF4J/Logback log messages for assertion.
 *
 * Usage:
 *   LogCaptor captor = LogCaptor.forClass(MaintenanceRest.class);
 *   captor.setLevel(Level.DEBUG);
 *   // ... run code ...
 *   assertTrue(captor.contains(Level.DEBUG, "getSystemStatus()"));
 *   captor.detach();
 */
public class LogCaptor {

    private final List<ILoggingEvent> events = new ArrayList<>();
    private final Logger logger;
    private final AppenderBase<ILoggingEvent> appender;

    private LogCaptor(Class<?> clazz) {
        this.logger = (Logger) LoggerFactory.getLogger(clazz);

        this.appender = new AppenderBase<>() {
            @Override
            protected void append(ILoggingEvent event) {
                events.add(event);
            }
        };
        this.appender.start();
        this.logger.addAppender(appender);
    }

    public static LogCaptor forClass(Class<?> clazz) {
        return new LogCaptor(clazz);
    }

    public void setLevel(Level level) {
        logger.setLevel(level);
    }

    public List<ILoggingEvent> getEvents() {
        return events;
    }

    public boolean contains(Level level, String message) {
        return events.stream().anyMatch(e ->
            e.getLevel().equals(level) &&
            e.getFormattedMessage().contains(message)
        );
    }

    public boolean containsLevel(Level level) {
        return events.stream().anyMatch(e -> e.getLevel().equals(level));
    }

    public void clear() {
        events.clear();
    }

    public void detach() {
        logger.detachAppender(appender);
        logger.setLevel(null); // restore inherited level
    }
}