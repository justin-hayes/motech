package org.motechproject.metrics.service.impl;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import org.motechproject.metrics.service.HealthCheckRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.SortedMap;
import java.util.SortedSet;
import java.util.concurrent.ExecutorService;

@Service("healthCheckRegistryService")
public class HealthCheckRegistryServiceImpl implements HealthCheckRegistryService {
    private HealthCheckRegistry healthCheckRegistry;

    @Autowired
    public HealthCheckRegistryServiceImpl(HealthCheckRegistry healthCheckRegistry) {
        this.healthCheckRegistry = healthCheckRegistry;
    }

    @Override
    public void register(String name, HealthCheck healthCheck) {
        healthCheckRegistry.register(name, healthCheck);
    }

    @Override
    public void unregister(String name) {
        healthCheckRegistry.unregister(name);
    }

    @Override
    public SortedSet<String> getNames() {
        return healthCheckRegistry.getNames();
    }

    @Override
    public HealthCheck.Result runHealthCheck(String name) {
        return healthCheckRegistry.runHealthCheck(name);
    }

    @Override
    public SortedMap<String, HealthCheck.Result> runHealthChecks() {
        return healthCheckRegistry.runHealthChecks();
    }

    @Override
    public SortedMap<String, HealthCheck.Result> runHealthChecks(ExecutorService executorService) {
        return healthCheckRegistry.runHealthChecks(executorService);
    }
}
