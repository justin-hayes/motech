package org.motechproject.metrics.model;

public class Snapshot implements org.motechproject.metrics.api.Snapshot {
    private final com.codahale.metrics.Snapshot snapshot;

    public Snapshot(com.codahale.metrics.Snapshot snapshot) {
        this.snapshot = snapshot;
    }

    @Override
    public double getValue(double quantile) {
        return snapshot.getValue(quantile);
    }

    @Override
    public long[] getValues() {
        return snapshot.getValues();
    }

    @Override
    public int size() {
        return snapshot.size();
    }

    @Override
    public double getMedian() {
        return snapshot.getMedian();
    }

    @Override
    public double get75thPercentile() {
        return snapshot.get75thPercentile();
    }

    @Override
    public double get95thPercentile() {
        return snapshot.get95thPercentile();
    }

    @Override
    public double get99thPercentile() {
        return snapshot.get99thPercentile();
    }

    @Override
    public double get999thPercentile() {
        return snapshot.get999thPercentile();
    }

    @Override
    public long getMax() {
        return snapshot.getMax();
    }

    @Override
    public double getMean() {
        return snapshot.getMean();
    }

    @Override
    public long getMin() {
        return snapshot.getMin();
    }

    @Override
    public double getStdDev() {
        return snapshot.getStdDev();
    }
}
