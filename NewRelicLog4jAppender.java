package com.nr.util;

import com.google.common.collect.ImmutableMap;
import com.newrelic.api.agent.NewRelic;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import java.util.Map;

public class NewRelicLog4jAppender extends AppenderSkeleton {

    @Override
    protected void append(final LoggingEvent event) {
        final Map<String, String> params = ImmutableMap.of(
                "message", event.getRenderedMessage(),
                "loggerName", event.getLoggerName(),
                "level", event.getLevel().toString(),
                "threadName", event.getThreadName()
        );

        /*final ThrowableInformation throwableInformation = event.getThrowableInformation();
        if (throwableInformation != null) {
            NewRelic.noticeError(throwableInformation.getThrowable(), params);
        } else {
            NewRelic.noticeError(event.getRenderedMessage(), params);
        }
        */
        NewRelic.getAgent().getInsights().recordCustomEvent('Log4J', params);

    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }
}