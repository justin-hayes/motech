package org.motechproject.metrics.model;

import org.motechproject.metrics.service.MetricsConfigService;

public class Counter implements org.motechproject.metrics.api.Counter {
    private final com.codahale.metrics.Counter counter;
    private final MetricsConfigService metricsConfigService;

    public Counter(com.codahale.metrics.Counter counter, MetricsConfigService metricsConfigService) {
        this.counter = counter;
        this.metricsConfigService = metricsConfigService;
    }

    @Override
    public void inc() {
        if (metricsConfigService.isMetricsEnabled()) {
            counter.inc();
        }
    }

    @Override
    public void inc(long n) {
        if (metricsConfigService.isMetricsEnabled()) {
            counter.inc(n);
        }
    }

    @Override
    public void dec() {
        if (metricsConfigService.isMetricsEnabled()) {
            counter.dec();
        }
    }

    @Override
    public void dec(long n) {
        if (metricsConfigService.isMetricsEnabled()) {
            counter.dec(n);
        }
    }

    @Override
    public long getCount() {
        return counter.getCount();
    }
}
