package org.motechproject.metrics;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.health.jvm.ThreadDeadlockHealthCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HealthCheckRegistryInitializer {
    @Autowired
    private HealthCheckRegistry healthCheckRegistry;

    @PostConstruct
    public void init() {
        ThreadDeadlockHealthCheck threadDeadlockHealthCheck = new ThreadDeadlockHealthCheck();
        healthCheckRegistry.register("threadDeadlockHealthCheck", threadDeadlockHealthCheck);
    }
}
