package org.motechproject.metrics.model;

import org.motechproject.metrics.config.MetricsConfigFacade;

/**
 * A counter implementation that can be enabled or disabled depending on configuration settings.
 */
public class Counter implements org.motechproject.metrics.api.Counter {
    private final com.codahale.metrics.Counter counter;
    private final MetricsConfigFacade metricsConfigFacade;

    public Counter(com.codahale.metrics.Counter counter, MetricsConfigFacade metricsConfigFacade) {
        this.counter = counter;
        this.metricsConfigFacade = metricsConfigFacade;
    }

    /**
     * If metrics are enabled, increment the counter by one. Otherwise, do nothing.
     */
    @Override
    public void inc() {
        if (metricsConfigFacade.isMetricsEnabled()) {
            counter.inc();
        }
    }

    /**
     * If metrics are enabled, increment the counter by the provided value. Otherwise, do nothing.
     * @param n the value to increment
     */
    @Override
    public void inc(long n) {
        if (metricsConfigFacade.isMetricsEnabled()) {
            counter.inc(n);
        }
    }

    /**
     * If metrics are enabled, decrement the counter by one. Otherwise, do nothing.
     */
    @Override
    public void dec() {
        if (metricsConfigFacade.isMetricsEnabled()) {
            counter.dec();
        }
    }

    /**
     * If metrics are enabled, decrement the counter by the provided value. Otherwise, do nothing.
     *
     * @param n the value to decrement
     */
    @Override
    public void dec(long n) {
        if (metricsConfigFacade.isMetricsEnabled()) {
            counter.dec(n);
        }
    }

    /**
     * Get the current count
     *
     * @return the count
     */
    @Override
    public long getCount() {
        return counter.getCount();
    }
}
