package org.motechproject.metrics.api;

public interface Meter extends Metered {
    void mark();
    void mark(long n);
}
