package org.motechproject.metrics.service;

import org.motechproject.metrics.api.HealthCheck;

import java.util.SortedSet;

public interface HealthCheckRegistryService {
    void register(String name, HealthCheck healthCheck);
    void unregister(String name);
    SortedSet<String> getNames();
}
