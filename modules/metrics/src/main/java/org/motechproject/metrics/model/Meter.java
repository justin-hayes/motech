package org.motechproject.metrics.model;

import org.motechproject.metrics.config.MetricsConfigFacade;

/**
 * A meter implementation that can be enabled or disabled depending on configuration settings.
 */
public class Meter implements org.motechproject.metrics.api.Meter {
    private final com.codahale.metrics.Meter meter;
    private final MetricsConfigFacade metricsConfigFacade;

    public Meter(com.codahale.metrics.Meter meter, MetricsConfigFacade metricsConfigFacade) {
        this.meter = meter;
        this.metricsConfigFacade = metricsConfigFacade;
    }

    /**
     * Record an event.
     */
    @Override
    public void mark() {
        if (metricsConfigFacade.isMetricsEnabled()) {
            meter.mark();
        }
    }

    /**
     * Record the provided number of events.
     *
     * @param n the number of events
     */
    @Override
    public void mark(long n) {
        if (metricsConfigFacade.isMetricsEnabled()) {
            meter.mark(n);
        }
    }

    /**
     * Get the number of recorded events.
     *
     * @return the number of recorded events
     */
    @Override
    public long getCount() {
        return meter.getCount();
    }

    /**
     * Get the fifteen-minute weighted average of events since the meter's creation.
     *
     * @return the fifteen-minute weighted average of events since the meter's creation
     */
    @Override
    public double getFifteenMinuteRate() {
        return meter.getFifteenMinuteRate();
    }

    /**
     * Get the five-minute weighted average of events since the meter's creation.
     *
     * @return the five-minute weighted average of events since the meter's creation
     */
    @Override
    public double getFiveMinuteRate() {
        return meter.getFiveMinuteRate();
    }

    /**
     * Get the mean average of events since the meter's creation.
     *
     * @return the mean average of events since the meter's creation
     */
    @Override
    public double getMeanRate() {
        return meter.getMeanRate();
    }

    /**
     * Get the one-minute weighted average of events since the meter's creation.
     *
     * @return the one-minute weighted average of events since the meter's creation
     */
    @Override
    public double getOneMinuteRate() {
        return meter.getOneMinuteRate();
    }
}
