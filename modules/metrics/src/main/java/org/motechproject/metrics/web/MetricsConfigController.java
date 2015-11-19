package org.motechproject.metrics.web;

import org.motechproject.metrics.MetricRegistryInitializer;
import org.motechproject.metrics.config.MetricsConfig;
import org.motechproject.metrics.service.MetricsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class MetricsConfigController {
    private final MetricsConfigService metricsConfigService;
    private final MetricRegistryInitializer metricRegistryInitializer;

    @Autowired
    public MetricsConfigController(MetricsConfigService metricsConfigService,
                                   MetricRegistryInitializer metricRegistryInitializer) {
        this.metricsConfigService = metricsConfigService;
        this.metricRegistryInitializer = metricRegistryInitializer;
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET)
    @ResponseBody
    public MetricsConfig getMetricsConfig() {
        return metricsConfigService.getMetricsConfig();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/config", method = RequestMethod.POST)
    public void saveSettings(@RequestBody MetricsConfig metricsConfig) {
        metricsConfigService.saveMetricsConfig(metricsConfig);
        metricRegistryInitializer.init();
    }

    @RequestMapping(value = "/config/timeUnits", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, TimeUnit[]> getAcceptableTimeUnits() {
        Map<String, TimeUnit[]> acceptableTimeUnits = new HashMap<>();
        acceptableTimeUnits.put("data", TimeUnit.values());
        return acceptableTimeUnits;
    }
}
