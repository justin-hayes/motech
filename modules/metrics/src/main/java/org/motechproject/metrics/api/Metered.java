package org.motechproject.metrics.api;

public interface Metered extends Counting {
    double getFifteenMinuteRate();

    double getFiveMinuteRate();

    double getMeanRate();

    double getOneMinuteRate();
}
