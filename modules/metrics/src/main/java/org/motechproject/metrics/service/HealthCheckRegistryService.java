package org.motechproject.metrics.service;

import com.codahale.metrics.health.HealthCheck;

import java.util.SortedMap;
import java.util.SortedSet;
import java.util.concurrent.ExecutorService;

public interface HealthCheckRegistryService {
    void register(String name, HealthCheck healthCheck);
    void unregister(String name);
    SortedSet<String> getNames();
    HealthCheck.Result runHealthCheck(String name);
    SortedMap<String, HealthCheck.Result> runHealthChecks();
    SortedMap<String, HealthCheck.Result> runHealthChecks(ExecutorService executorService);
}
