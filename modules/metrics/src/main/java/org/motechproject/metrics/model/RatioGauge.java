package org.motechproject.metrics.model;

public class RatioGauge implements org.motechproject.metrics.api.Gauge<Double> {
    private final com.codahale.metrics.RatioGauge ratioGauge;

    public RatioGauge(com.codahale.metrics.RatioGauge ratioGauge) {
        this.ratioGauge = ratioGauge;
    }

    @Override
    public Double getValue() {
        return ratioGauge.getValue();
    }
}
