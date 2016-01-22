package org.motechproject.metrics.model;

import org.motechproject.metrics.config.MetricsConfigFacade;

public class Counter implements org.motechproject.metrics.api.Counter {
    private final com.codahale.metrics.Counter counter;
    private final MetricsConfigFacade metricsConfigFacade;

    public Counter(com.codahale.metrics.Counter counter, MetricsConfigFacade metricsConfigFacade) {
        this.counter = counter;
        this.metricsConfigFacade = metricsConfigFacade;
    }

    @Override
    public void inc() {
        if (metricsConfigFacade.isMetricsEnabled()) {
            counter.inc();
        }
    }

    @Override
    public void inc(long n) {
        if (metricsConfigFacade.isMetricsEnabled()) {
            counter.inc(n);
        }
    }

    @Override
    public void dec() {
        if (metricsConfigFacade.isMetricsEnabled()) {
            counter.dec();
        }
    }

    @Override
    public void dec(long n) {
        if (metricsConfigFacade.isMetricsEnabled()) {
            counter.dec(n);
        }
    }

    @Override
    public long getCount() {
        return counter.getCount();
    }
}
