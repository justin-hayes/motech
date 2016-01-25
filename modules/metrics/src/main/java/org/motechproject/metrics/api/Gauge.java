package org.motechproject.metrics.api;

/**
 * Represents a metric that returns a value.
 *
 * @param <T> the type of the returned value
 */
public interface Gauge<T> {
    /**
     * Get the current value.
     *
     * @return the metric's current value
     */
    T getValue();
}
