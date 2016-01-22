package org.motechproject.metrics.model;

import org.motechproject.metrics.api.Snapshot;
import org.motechproject.metrics.config.MetricsConfigFacade;

public class Histogram implements org.motechproject.metrics.api.Histogram {
    private final com.codahale.metrics.Histogram histogram;
    private final MetricsConfigFacade metricsConfigFacade;

    public Histogram(com.codahale.metrics.Histogram histogram, MetricsConfigFacade metricsConfigFacade) {
        this.histogram = histogram;
        this.metricsConfigFacade = metricsConfigFacade;
    }

    @Override
    public void update(int value) {
        if (metricsConfigFacade.isMetricsEnabled()) {
            histogram.update(value);
        }
    }

    @Override
    public void update(long value) {
        if (metricsConfigFacade.isMetricsEnabled()) {
            histogram.update(value);
        }
    }

    @Override
    public long getCount() {
        return histogram.getCount();
    }

    @Override
    public Snapshot getSnapshot() {
        return new org.motechproject.metrics.model.Snapshot(histogram.getSnapshot());
    }
}
