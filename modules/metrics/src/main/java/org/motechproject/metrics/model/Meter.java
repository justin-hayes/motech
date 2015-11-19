package org.motechproject.metrics.model;

import org.motechproject.metrics.service.MetricsConfigService;

public class Meter implements org.motechproject.metrics.api.Meter {
    private final com.codahale.metrics.Meter meter;
    private final MetricsConfigService metricsConfigService;

    public Meter(com.codahale.metrics.Meter meter, MetricsConfigService metricsConfigService) {
        this.meter = meter;
        this.metricsConfigService = metricsConfigService;
    }

    @Override
    public void mark() {
        if (metricsConfigService.isMetricsEnabled()) {
            meter.mark();
        }
    }

    @Override
    public void mark(long n) {
        if (metricsConfigService.isMetricsEnabled()) {
            meter.mark(n);
        }
    }

    @Override
    public long getCount() {
        return meter.getCount();
    }

    @Override
    public double getFifteenMinuteRate() {
        return meter.getFifteenMinuteRate();
    }

    @Override
    public double getFiveMinuteRate() {
        return meter.getFiveMinuteRate();
    }

    @Override
    public double getMeanRate() {
        return meter.getMeanRate();
    }

    @Override
    public double getOneMinuteRate() {
        return meter.getOneMinuteRate();
    }
}
