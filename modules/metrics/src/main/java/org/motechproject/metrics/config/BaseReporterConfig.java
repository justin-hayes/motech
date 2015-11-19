package org.motechproject.metrics.config;

import java.util.concurrent.TimeUnit;

public abstract class BaseReporterConfig {
    private boolean enabled;
    private TimeUnit convertRates;
    private TimeUnit convertDurations;
    private int frequency;
    private TimeUnit frequencyUnit;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public TimeUnit getConvertRates() {
        return convertRates;
    }

    public void setConvertRates(TimeUnit convertRates) {
        this.convertRates = convertRates;
    }

    public TimeUnit getConvertDurations() {
        return convertDurations;
    }

    public void setConvertDurations(TimeUnit convertDurations) {
        this.convertDurations = convertDurations;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public TimeUnit getFrequencyUnit() {
        return frequencyUnit;
    }

    public void setFrequencyUnit(TimeUnit frequencyUnit) {
        this.frequencyUnit = frequencyUnit;
    }
}
