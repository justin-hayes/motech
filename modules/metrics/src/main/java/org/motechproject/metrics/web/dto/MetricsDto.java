package org.motechproject.metrics.web.dto;

import java.util.Arrays;
import java.util.Set;
import java.util.SortedSet;

public class MetricsDto {
    private MetricType type;
    private SortedSet<String> names;
    private RatioGaugeValue[] values;

    public MetricsDto(MetricType type, SortedSet<String> names, RatioGaugeValue[] values) {
        this.type = type;
        this.names = names;
        this.values = Arrays.copyOf(values, values.length);
    }

    public MetricType getType() {
        return type;
    }

    public void setType(MetricType type) {
        this.type = type;
    }

    public Set<String> getNames() {
        return names;
    }

    public void setNames(SortedSet<String> names) {
        this.names = names;
    }

    public RatioGaugeValue[] getValues() {
        return Arrays.copyOf(values, values.length);
    }

    public void setValues(RatioGaugeValue[] values) {
        this.values = Arrays.copyOf(values, values.length);
    }
}
