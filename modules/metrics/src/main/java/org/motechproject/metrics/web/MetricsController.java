package org.motechproject.metrics.web;

import com.codahale.metrics.Counting;
import com.codahale.metrics.Metered;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import org.motechproject.metrics.exception.MetricNotFoundException;
import org.motechproject.metrics.web.dto.MetricsDto;
import org.motechproject.metrics.web.dto.MetricDto;
import org.motechproject.metrics.web.dto.MetricType;
import org.motechproject.metrics.web.dto.RatioGaugeDto;
import org.motechproject.metrics.service.MetricRegistryService;
import org.motechproject.metrics.web.dto.RatioGaugeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Controller
public class MetricsController {
    private final MetricRegistry metricRegistry;
    private final MetricRegistryService metricRegistryService;

    @Autowired
    public MetricsController(MetricRegistry metricRegistry, MetricRegistryService metricRegistryService) {
        this.metricRegistry = metricRegistry;
        this.metricRegistryService = metricRegistryService;
    }

    @RequestMapping(value = "/metrics", method = RequestMethod.GET)
    @ResponseBody
    public List<MetricsDto> getMetrics() {
        List<MetricsDto> ret = new ArrayList<>();

        ret.add(new MetricsDto(MetricType.COUNTER, new ArrayList<String>(metricRegistry.getCounters().keySet()), new RatioGaugeValue[]{RatioGaugeValue.COUNT}));
        ret.add(new MetricsDto(MetricType.GAUGE, new ArrayList<String>(metricRegistry.getGauges().keySet()), RatioGaugeValue.values()));
        ret.add(new MetricsDto(MetricType.HISTOGRAM, new ArrayList<String>(metricRegistry.getHistograms().keySet()), RatioGaugeValue.values()));
        ret.add(new MetricsDto(MetricType.METER, new ArrayList<String>(metricRegistry.getMeters().keySet()), RatioGaugeValue.values()));
        ret.add(new MetricsDto(MetricType.TIMER, new ArrayList<String>(metricRegistry.getTimers().keySet()), RatioGaugeValue.values()));

        return ret;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/metrics/ratioGauge", method = RequestMethod.POST)
    public void createRatioGauge(@RequestBody RatioGaugeDto dto) {
        metricRegistryService.registerRatioGauge(dto.getName(), getSupplier(dto.getNumerator()), getSupplier(dto.getDenominator()));
    }

    private Supplier<Number> getSupplier(MetricDto dto) {
        Metric metric = getMetric(dto);
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

    private Metric getMetric(MetricDto dto) {
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

}

