package org.motechproject.metrics.api;

public interface Histogram extends Counting, Sampling {
    void update(int value);
    void update(long value);
}
