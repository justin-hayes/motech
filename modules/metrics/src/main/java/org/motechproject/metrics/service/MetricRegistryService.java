package org.motechproject.metrics.service;

import org.motechproject.metrics.api.Counter;
import org.motechproject.metrics.api.Gauge;
import org.motechproject.metrics.api.Histogram;
import org.motechproject.metrics.api.Meter;
import org.motechproject.metrics.api.Timer;

import java.util.function.Supplier;

public interface MetricRegistryService {
    void enableMetrics();
    void disableMetrics();
    Counter counter(final String name);
    Histogram histogram(final String name);
    Meter meter(final String name);
    Timer timer(final String name);
    <T> Gauge<T> registerGauge(final String name, final Gauge<T> gauge);
    <T extends Number> Gauge<Double> registerRatioGauge(final String name, Supplier<T> numerator, Supplier<T> denominator);
    boolean isRegistered(String name);
}