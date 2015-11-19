package org.motechproject.metrics.model;

import org.motechproject.metrics.service.MetricsConfigService;

public class Histogram implements org.motechproject.metrics.api.Histogram {
    private final com.codahale.metrics.Histogram histogram;
    private final MetricsConfigService metricsConfigService;

    public Histogram(com.codahale.metrics.Histogram histogram, MetricsConfigService metricsConfigService) {
        this.histogram = histogram;
        this.metricsConfigService = metricsConfigService;
    }

    @Override
    public void update(int value) {
        if (metricsConfigService.isMetricsEnabled()) {
            histogram.update(value);
        }
    }

    @Override
    public void update(long value) {
        if (metricsConfigService.isMetricsEnabled()) {
            histogram.update(value);
        }
    }

    @Override
    public long getCount() {
        return histogram.getCount();
    }
}
