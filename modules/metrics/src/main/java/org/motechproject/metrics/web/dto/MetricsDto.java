package org.motechproject.metrics.web.dto;

import java.util.Arrays;
import java.util.List;

public class MetricsDto {
    private MetricType type;
    private List<String> names;
    private RatioGaugeValue[] values;

    public MetricsDto(MetricType type, List<String> names, RatioGaugeValue[] values) {
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

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public RatioGaugeValue[] getValues() {
        return Arrays.copyOf(values, values.length);
    }

    public void setValues(RatioGaugeValue[] values) {
        this.values = Arrays.copyOf(values, values.length);
    }
}
