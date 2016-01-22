package org.motechproject.metrics.service.impl;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.RatioGauge;
import org.motechproject.metrics.api.Counter;
import org.motechproject.metrics.api.Gauge;
import org.motechproject.metrics.api.Histogram;
import org.motechproject.metrics.api.Meter;
import org.motechproject.metrics.api.Timer;
import org.motechproject.metrics.config.MetricsConfigFacade;
import org.motechproject.metrics.service.MetricRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service("metricRegistryService")
public class MetricRegistryServiceImpl implements MetricRegistryService {
    private final MetricRegistry metricRegistry;
    private final MetricsConfigFacade metricsConfigFacade;

    @Autowired
    public MetricRegistryServiceImpl(MetricRegistry metricRegistry, MetricsConfigFacade metricsConfigFacade) {
        this.metricRegistry = metricRegistry;
        this.metricsConfigFacade = metricsConfigFacade;
    }

    @Override
    public Counter counter(final String name) {
        com.codahale.metrics.Counter counter = metricRegistry.counter(name);
        return new org.motechproject.metrics.model.Counter(counter, metricsConfigFacade);
    }

    @Override
    public Histogram histogram(final String name) {
        com.codahale.metrics.Histogram histogram = metricRegistry.histogram(name);
        return new org.motechproject.metrics.model.Histogram(histogram, metricsConfigFacade);
    }

    @Override
    public Meter meter(final String name) {
        com.codahale.metrics.Meter meter = metricRegistry.meter(name);
        return new org.motechproject.metrics.model.Meter(meter, metricsConfigFacade);
    }

    @Override
    public Timer timer(final String name) {
        com.codahale.metrics.Timer timer = metricRegistry.timer(name);
        return new org.motechproject.metrics.model.Timer(timer, metricsConfigFacade);
    }

    @Override
    public <T> Gauge<T> registerGauge(final String name, final Gauge<T> gauge) {
        com.codahale.metrics.Gauge<T> theGauge = metricRegistry.register(name, new com.codahale.metrics.Gauge<T>() {
            @Override
            public T getValue() {
                return gauge.getValue();
            }
        });
        return new org.motechproject.metrics.model.Gauge<T>(theGauge);
    }

    @Override
    public <T extends Number> Gauge<Double> registerRatioGauge(final String name, Supplier<T> numerator, Supplier<T> denominator) {
        com.codahale.metrics.RatioGauge theGauge = metricRegistry.register(name, new RatioGauge() {
            @Override
            protected Ratio getRatio() {
                return Ratio.of(numerator.get().doubleValue(), denominator.get().doubleValue());
            }
        });

        return new org.motechproject.metrics.model.Gauge<Double>(theGauge);
    }

    @Override
    public boolean isRegistered(String name) {
        return metricRegistry.getNames().contains(name);
    }
}
