package org.motechproject.metrics.model;

import org.motechproject.metrics.api.Snapshot;
import org.motechproject.metrics.config.MetricsConfigFacade;

import java.util.concurrent.TimeUnit;

public class Timer implements org.motechproject.metrics.api.Timer {
    private final com.codahale.metrics.Timer timer;
    private final MetricsConfigFacade metricsConfigFacade;

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

    private static final org.motechproject.metrics.api.Timer.Context DUMMY_CONTEXT = new org.motechproject.metrics.api.Timer.Context() {
        @Override
        public void stop() {}
    };

    public Timer(com.codahale.metrics.Timer timer, MetricsConfigFacade metricsConfigFacade) {
        this.timer = timer;
        this.metricsConfigFacade = metricsConfigFacade;
    }

    @Override
    public void update(long duration, TimeUnit unit) {
        if (metricsConfigFacade.isMetricsEnabled()) {
            timer.update(duration, unit);
        }
    }

    @Override
    public org.motechproject.metrics.api.Timer.Context time() {
        if (metricsConfigFacade.isMetricsEnabled()) {
            return new Context(timer.time());
        } else {
            return DUMMY_CONTEXT;
        }
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

    @Override
    public Snapshot getSnapshot() {
        return new org.motechproject.metrics.model.Snapshot(timer.getSnapshot());
    }
}
