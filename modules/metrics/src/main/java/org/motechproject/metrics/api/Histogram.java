package org.motechproject.metrics.api;

public interface Histogram extends Counting {
    void update(int value);
    void update(long value);
}
