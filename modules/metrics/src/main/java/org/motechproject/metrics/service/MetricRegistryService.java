package org.motechproject.metrics.service;

import org.motechproject.metrics.model.MeteredRate;
import org.motechproject.metrics.model.MetricType;

import java.util.SortedSet;

public interface MetricRegistryService {
    boolean remove(String name);
    SortedSet<String> getNames();

    void initializeMetric(String name, MetricType type);

    void incrementCounter(String counterName);
    void incrementCounter(String counterName, long incrementValue);
    void decrementCounter(String counterName);
    void decrementCounter(String counterName, long decrementValue);
    void markMeter(String meterName);
    void markMeter(String meterName, long markValue);
    void updateHistogram(String histogramName, int updateValue);
    void updateHistogram(String histogramName, long updateValue);

    void registerMeteredRatioGauge(String gaugeName, String numeratorName, MetricType numeratorType, String denominatorName, MetricType denominatorType, MeteredRate meteredRate);
    void registerCountedRatioGauge(String gaugeName, String numeratorName, MetricType numeratorType, String denominatorName, MetricType denominatorType);
}
