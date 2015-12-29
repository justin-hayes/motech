package org.motechproject.metrics.web;

import com.codahale.metrics.MetricRegistry;
import org.motechproject.metrics.web.dto.MetricsDto;
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

import static org.motechproject.metrics.web.MetricDtoToSupplierHelper.getSupplier;

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
        metricRegistryService.registerRatioGauge(dto.getName(),
                getSupplier(metricRegistry, dto.getNumerator()),
                getSupplier(metricRegistry, dto.getDenominator()));
    }



}

