package org.motechproject.metrics.web;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.SortedMap;

/**
 * Initiates health checks from the user interface and returns the result.
 */
@Controller
public class HealthCheckRegistryController {
    private HealthCheckRegistry healthCheckRegistry;

    @Autowired
    public HealthCheckRegistryController(HealthCheckRegistry healthCheckRegistry) {
        this.healthCheckRegistry = healthCheckRegistry;
    }

    /**
     * Runs all registered health checks and returns the results.
     *
     * @return the health check results
     */
    @RequestMapping("/healthChecks")
    @ResponseBody
    public SortedMap<String, HealthCheck.Result> runHealthChecks() {
        return healthCheckRegistry.runHealthChecks();
    }
}
