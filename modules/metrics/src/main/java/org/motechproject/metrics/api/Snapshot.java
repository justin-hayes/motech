package org.motechproject.metrics.api;

public interface Snapshot {
    double getValue(double quantile);
    long[] getValues();
    int size();
    double getMedian();
    double get75thPercentile();
    double get95thPercentile();
    double get99thPercentile();
    double get999thPercentile();
    long getMax();
    double getMean();
    long getMin();
    double getStdDev();
}
