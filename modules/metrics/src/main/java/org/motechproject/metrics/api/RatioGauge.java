package org.motechproject.metrics.api;

public interface RatioGauge extends Gauge<Double> {
    interface Ratio {
        Ratio of(double numerator, double denominator);
        double getValue();
    }
}
