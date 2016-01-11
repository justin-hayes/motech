package org.motechproject.metrics.builder;

import org.motechproject.metrics.api.HealthCheck;
import org.motechproject.metrics.model.HealthCheckResult;

public final class HealthCheckResultBuilder {
    public static HealthCheck.Result healthy() {
        return new HealthCheckResult(com.codahale.metrics.health.HealthCheck.Result.healthy());
    }

    public static HealthCheck.Result healthy(String message) {
        return new HealthCheckResult(com.codahale.metrics.health.HealthCheck.Result.healthy(message));
    }

    public static HealthCheck.Result healthy(String message, Object... args) {
        return new HealthCheckResult(com.codahale.metrics.health.HealthCheck.Result.healthy(message, args));
    }

    public static HealthCheck.Result unhealthy(String message) {
        return new HealthCheckResult(com.codahale.metrics.health.HealthCheck.Result.unhealthy(message));
    }

    public static HealthCheck.Result unhealthy(String message, Object... args) {
        return new HealthCheckResult(com.codahale.metrics.health.HealthCheck.Result.unhealthy(message, args));
    }

    public static HealthCheck.Result unhealthy(Throwable error) {
        return new HealthCheckResult(com.codahale.metrics.health.HealthCheck.Result.unhealthy(error));
    }

    private HealthCheckResultBuilder() {}
}