package org.motechproject.metrics.service.impl;

import com.codahale.metrics.health.HealthCheckRegistry;
import org.motechproject.metrics.api.HealthCheck;
import org.motechproject.metrics.service.HealthCheckRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.SortedSet;

@Service("healthCheckRegistryService")
public class HealthCheckRegistryServiceImpl implements HealthCheckRegistryService {
    private HealthCheckRegistry healthCheckRegistry;

    @Autowired
    public HealthCheckRegistryServiceImpl(HealthCheckRegistry healthCheckRegistry) {
        this.healthCheckRegistry = healthCheckRegistry;
    }

    @Override
    public void register(String name, HealthCheck healthCheck) {
        healthCheckRegistry.register(name, new com.codahale.metrics.health.HealthCheck() {
            @Override
            @SuppressWarnings("PMD.SignatureDeclareThrowsException")
            protected Result check() throws Exception {
                HealthCheck.Result wrapped = healthCheck.check();
                com.codahale.metrics.health.HealthCheck.Result result;
                if (wrapped.isHealthy()) {
                    result = com.codahale.metrics.health.HealthCheck.Result.healthy();
                } else {
                    result = com.codahale.metrics.health.HealthCheck.Result.unhealthy(wrapped.getMessage());
                }
                return result;
            }
        });
    }

    @Override
    public void unregister(String name) {
        healthCheckRegistry.unregister(name);
    }

    @Override
    public SortedSet<String> getNames() {
        return healthCheckRegistry.getNames();
    }
}
