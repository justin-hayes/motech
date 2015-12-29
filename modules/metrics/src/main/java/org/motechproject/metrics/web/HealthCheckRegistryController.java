package org.motechproject.metrics.web;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.SortedMap;

@Controller
public class HealthCheckRegistryController {
    private HealthCheckRegistry healthCheckRegistry;

    @Autowired
    public HealthCheckRegistryController(HealthCheckRegistry healthCheckRegistry) {
        this.healthCheckRegistry = healthCheckRegistry;
    }

    @RequestMapping("/healthChecks")
    @ResponseBody
    public SortedMap<String, HealthCheck.Result> runHealthChecks() {
        return healthCheckRegistry.runHealthChecks();
    }
}
