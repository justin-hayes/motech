package org.motechproject.metrics.model;

import org.motechproject.metrics.config.MetricsConfigFacade;

public class Meter implements org.motechproject.metrics.api.Meter {
    private final com.codahale.metrics.Meter meter;
    private final MetricsConfigFacade metricsConfigFacade;

    public Meter(com.codahale.metrics.Meter meter, MetricsConfigFacade metricsConfigFacade) {
        this.meter = meter;
        this.metricsConfigFacade = metricsConfigFacade;
    }

    @Override
    public void mark() {
        if (metricsConfigFacade.isMetricsEnabled()) {
            meter.mark();
        }
    }

    @Override
    public void mark(long n) {
        if (metricsConfigFacade.isMetricsEnabled()) {
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
