package org.motechproject.metrics.api;

public interface Gauge<T> {
    T getValue();
}
