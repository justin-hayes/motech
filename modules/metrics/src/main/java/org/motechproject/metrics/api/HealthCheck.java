package org.motechproject.metrics.api;

public interface HealthCheck {
    interface Result {
        boolean isHealthy();
        String getMessage();
        Throwable getError();
    }

    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    Result check() throws Exception;
}
