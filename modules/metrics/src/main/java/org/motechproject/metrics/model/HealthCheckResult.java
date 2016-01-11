package org.motechproject.metrics.model;

import org.motechproject.metrics.api.HealthCheck;

public class HealthCheckResult implements HealthCheck.Result {
    private final com.codahale.metrics.health.HealthCheck.Result result;

    public HealthCheckResult(com.codahale.metrics.health.HealthCheck.Result result) {
        this.result = result;
    }

    @Override
    public boolean isHealthy() {
        return result.isHealthy();
    }

    @Override
    public String getMessage() {
        return result.getMessage();
    }

    @Override
    public Throwable getError() {
        return result.getError();
    }
}