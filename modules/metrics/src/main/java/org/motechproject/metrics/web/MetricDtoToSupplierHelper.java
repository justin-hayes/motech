package org.motechproject.metrics.web;

import com.codahale.metrics.Counting;
import com.codahale.metrics.Metered;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import org.motechproject.metrics.exception.MetricNotFoundException;
import org.motechproject.metrics.web.dto.MetricDto;

import java.util.function.Supplier;

public final class MetricDtoToSupplierHelper {
    public static Supplier<Number> getSupplier(MetricRegistry metricRegistry, MetricDto dto) {
        Metric metric = getMetric(metricRegistry, dto);
        Supplier<Number> ret;

        switch (dto.getValue()) {
            case COUNT:
                ret = new Supplier<Number>() {
                    @Override
                    public Long get() {
                        return ((Counting) metric).getCount();
                    }
                };
                break;
            case MEAN:
                ret = new Supplier<Number>() {
                    @Override
                    public Double get() {
                        return ((Metered) metric).getMeanRate();
                    }
                };
                break;
            case ONE_MINUTE:
                ret = new Supplier<Number>() {
                    @Override
                    public Double get() {
                        return ((Metered) metric).getOneMinuteRate();
                    }
                };
                break;
            case FIVE_MINUTE:
                ret = new Supplier<Number>() {
                    @Override
                    public Double get() {
                        return ((Metered) metric).getFiveMinuteRate();
                    }
                };
                break;
            case FIFTEEN_MINUTE:
                ret = new Supplier<Number>() {
                    @Override
                    public Double get() {
                        return ((Metered) metric).getFifteenMinuteRate();
                    }
                };
                break;
            default:
                ret = null;
                break;
        }

        return ret;
    }

    private static Metric getMetric(MetricRegistry metricRegistry, MetricDto dto) {
        Metric ret = null;

        switch (dto.getType()) {
            case COUNTER:
                ret = metricRegistry.getCounters(new MetricFilter() {
                    @Override
                    public boolean matches(String name, Metric metric) {
                        return name.equals(dto.getName());
                    }
                }).get(dto.getName());
                break;
            case GAUGE:
                ret = metricRegistry.getGauges(new MetricFilter() {
                    @Override
                    public boolean matches(String name, Metric metric) {
                        return name.equals(dto.getName());
                    }
                }).get(dto.getName());
            case HISTOGRAM:
                ret = metricRegistry.getHistograms(new MetricFilter() {
                    @Override
                    public boolean matches(String name, Metric metric) {
                        return name.equals(dto.getName());
                    }
                }).get(dto.getName());
                break;
            case METER:
                ret = metricRegistry.getMeters(new MetricFilter() {
                    @Override
                    public boolean matches(String name, Metric metric) {
                        return name.equals(dto.getName());
                    }
                }).get(dto.getName());
                break;
            case TIMER:
                ret = metricRegistry.getTimers(new MetricFilter() {
                    @Override
                    public boolean matches(String name, Metric metric) {
                        return name.equals(dto.getName());
                    }
                }).get(dto.getName());
                break;
        }

        if (ret == null) {
            String msg = String.format("Unable to find metric of type %s with name: %s", dto.getType(), dto.getName());
            throw new MetricNotFoundException(msg);
        }

        return ret;
    }

    private MetricDtoToSupplierHelper() {}
}
