package org.motechproject.metrics.api;

import java.util.concurrent.TimeUnit;

public interface Timer extends Metered {
    interface Context {
        void stop();
    }

    void update(long duration, TimeUnit unit);
    Context time();
}
