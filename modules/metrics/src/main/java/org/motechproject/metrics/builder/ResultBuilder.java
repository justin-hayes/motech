package org.motechproject.metrics.builder;

import org.motechproject.metrics.api.HealthCheck;

public final class ResultBuilder {
    public static HealthCheck.Result healthy() {
        return new Result(com.codahale.metrics.health.HealthCheck.Result.healthy());
    }

    public static HealthCheck.Result healthy(String message) {
        return new Result(com.codahale.metrics.health.HealthCheck.Result.healthy(message));
    }

    public static HealthCheck.Result healthy(String message, Object... args) {
        return new Result(com.codahale.metrics.health.HealthCheck.Result.healthy(message, args));
    }

    public static HealthCheck.Result unhealthy(String message) {
        return new Result(com.codahale.metrics.health.HealthCheck.Result.unhealthy(message));
    }

    public static HealthCheck.Result unhealthy(String message, Object... args) {
        return new Result(com.codahale.metrics.health.HealthCheck.Result.unhealthy(message, args));
    }

    public static HealthCheck.Result unhealthy(Throwable error) {
        return new Result(com.codahale.metrics.health.HealthCheck.Result.unhealthy(error));
    }

    private ResultBuilder() {}

    private static final class Result implements HealthCheck.Result {
        private final com.codahale.metrics.health.HealthCheck.Result result;

        private Result(com.codahale.metrics.health.HealthCheck.Result result) {
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
}