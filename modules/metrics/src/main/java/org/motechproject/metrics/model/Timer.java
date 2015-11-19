package org.motechproject.metrics.model;

import org.motechproject.metrics.service.MetricsConfigService;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Timer implements org.motechproject.metrics.api.Timer {
    private final com.codahale.metrics.Timer timer;
    private final MetricsConfigService metricsConfigService;

    private static final class Context implements org.motechproject.metrics.api.Timer.Context {
        private final com.codahale.metrics.Timer.Context context;

        private Context(com.codahale.metrics.Timer.Context context) {
            this.context = context;
        }

        @Override
        public void stop() {
            this.context.stop();
        }
    }

    public Timer(com.codahale.metrics.Timer timer, MetricsConfigService metricsConfigService) {
        this.timer = timer;
        this.metricsConfigService = metricsConfigService;
    }

    @Override
    public void update(long duration, TimeUnit unit) {
        if (metricsConfigService.isMetricsEnabled()) {
            timer.update(duration, unit);
        }
    }

    @Override
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public <T> T time(Callable<T> event) throws Exception {
        return timer.time(event);
    }

    @Override
    public org.motechproject.metrics.api.Timer.Context time() {
        return new Context(timer.time());
    }

    @Override
    public long getCount() {
        return timer.getCount();
    }

    @Override
    public double getFifteenMinuteRate() {
        return timer.getFifteenMinuteRate();
    }

    @Override
    public double getFiveMinuteRate() {
        return timer.getFiveMinuteRate();
    }

    @Override
    public double getMeanRate() {
        return timer.getMeanRate();
    }

    @Override
    public double getOneMinuteRate() {
        return timer.getOneMinuteRate();
    }
}
