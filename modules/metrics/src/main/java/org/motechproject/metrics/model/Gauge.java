package org.motechproject.metrics.model;

public class Gauge<T> implements org.motechproject.metrics.api.Gauge<T> {
    private final com.codahale.metrics.Gauge<T> gauge;

    public Gauge(com.codahale.metrics.Gauge<T> gauge) {
        this.gauge = gauge;
    }

    @Override
    public T getValue() {
        return gauge.getValue();
    }
}
