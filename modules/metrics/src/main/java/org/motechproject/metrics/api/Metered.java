package org.motechproject.metrics.api;

public interface Metered extends Counting {
    @Override
    long getCount();

    double getFifteenMinuteRate();

    double getFiveMinuteRate();

    double getMeanRate();

    double getOneMinuteRate();
}
