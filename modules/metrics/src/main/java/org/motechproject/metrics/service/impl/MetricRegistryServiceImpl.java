package org.motechproject.metrics.service.impl;

import com.codahale.metrics.Counting;
import com.codahale.metrics.Metered;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.RatioGauge;
import org.motechproject.metrics.model.MeteredRate;
import org.motechproject.metrics.model.MetricType;
import org.motechproject.metrics.service.MetricRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.SortedSet;

import com.codahale.metrics.RatioGauge.Ratio;

@Service("metricRegistryService")
public class MetricRegistryServiceImpl implements MetricRegistryService {
    private final MetricRegistry registry;

    @Autowired
    public MetricRegistryServiceImpl(MetricRegistry metricRegistry) {
        this.registry = metricRegistry;
    }

    @Override
    public boolean remove(String name) {
        return registry.remove(name);
    }

    @Override
    public SortedSet<String> getNames() {
        return registry.getNames();
    }

    @Override
    public void initializeMetric(String name, MetricType type) {
        getMetric(name, type);
    }

    @Override
    public void incrementCounter(String counterName) {
        registry.counter(counterName).inc();
    }

    @Override
    public void incrementCounter(String counterName, long incrementValue) {
        registry.counter(counterName).inc(incrementValue);
    }

    @Override
    public void decrementCounter(String counterName) {
        registry.counter(counterName).dec();
    }

    @Override
    public void decrementCounter(String counterName, long decrementValue) {
        registry.counter(counterName).dec(decrementValue);
    }

    @Override
    public void markMeter(String meterName) {
        registry.meter(meterName).mark();
    }

    @Override
    public void markMeter(String meterName, long markValue) {
        registry.meter(meterName).mark(markValue);
    }

    @Override
    public void updateHistogram(String histogramName, int updateValue) {
        registry.histogram(histogramName).update(updateValue);
    }

    @Override
    public void updateHistogram(String histogramName, long updateValue) {
        registry.histogram(histogramName).update(updateValue);
    }

    @Override
    public void registerMeteredRatioGauge(String gaugeName, String numeratorName, MetricType numeratorType, String denominatorName, MetricType denominatorType, MeteredRate meteredRate) {
        Metered numerator = getMeteredMetric(numeratorName, numeratorType);
        Metered denominator = getMeteredMetric(denominatorName, denominatorType);

        registry.register(gaugeName, new RatioGauge() {
            @Override
            protected Ratio getRatio() {
                return getMeteredRatio(numerator, denominator, meteredRate);
            }
        });
    }

    @Override
    public void registerCountedRatioGauge(String gaugeName, String numeratorName, MetricType numeratorType, String denominatorName, MetricType denominatorType) {
        Counting numerator = getCountingMetric(numeratorName, numeratorType);
        Counting denominator = getCountingMetric(denominatorName, denominatorType);

        registry.register(gaugeName, new RatioGauge() {
            @Override
            protected Ratio getRatio() {
                return Ratio.of(numerator.getCount(), denominator.getCount());
            }
        });
    }

    private Counting getCountingMetric(String name, MetricType type) {
        return (Counting) getMetric(name, type);
    }

    private Metric getMetric(String name, MetricType type) {
        Metric ret = null;

        switch (type) {
            case COUNTER:
                ret = registry.counter(name);
                break;
            case METER:
                ret = registry.meter(name);
                break;
            case HISTOGRAM:
                ret = registry.histogram(name);
                break;
            case TIMER:
                ret = registry.timer(name);
                break;
            default:
                break;
        }

        return ret;
    }

    private Metered getMeteredMetric(String name, MetricType type) {
        return (Metered) getMetric(name, type);
    }

    private Ratio getMeteredRatio(Metered numerator, Metered denominator, MeteredRate meteredRate) {
        RatioGauge.Ratio ret = null;

        switch (meteredRate) {
            case MEAN:
                ret = Ratio.of(numerator.getMeanRate(), denominator.getMeanRate());
                break;
            case ONE_MINUTE:
                ret = Ratio.of(numerator.getOneMinuteRate(), denominator.getOneMinuteRate());
                break;
            case FIVE_MINUTES:
                ret = Ratio.of(numerator.getFiveMinuteRate(), denominator.getFiveMinuteRate());
                break;
            case FIFTEEN_MINUTES:
                ret = Ratio.of(numerator.getFifteenMinuteRate(), denominator.getFifteenMinuteRate());
                break;
            default:
                break;
        }

        return ret;
    }

    public void createCountedRatioGauge(String name, String numName, String denName) {
        Counting num = registry.meter(numName);
        Counting den = registry.timer(denName);

        registry.register(name, new RatioGauge() {
            @Override
            protected Ratio getRatio() {
                return Ratio.of(num.getCount(), den.getCount());
            }
        });
    }
}
