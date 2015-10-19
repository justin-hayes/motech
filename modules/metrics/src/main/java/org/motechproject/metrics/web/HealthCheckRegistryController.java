package org.motechproject.metrics.web;

import com.codahale.metrics.health.HealthCheck;
import org.motechproject.metrics.service.HealthCheckRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.SortedMap;

@Controller
public class HealthCheckRegistryController {
    private HealthCheckRegistryService healthCheckRegistryService;

    @Autowired
    public HealthCheckRegistryController(HealthCheckRegistryService healthCheckRegistryService) {
        this.healthCheckRegistryService = healthCheckRegistryService;
    }

    @RequestMapping("/healthChecks")
    @ResponseBody
    public SortedMap<String, HealthCheck.Result> runHealthChecks() {
        return healthCheckRegistryService.runHealthChecks();
    }
}
