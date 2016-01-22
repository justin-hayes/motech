package org.motechproject.metrics.ut.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.motechproject.metrics.config.MetricsConfigFacade;
import org.motechproject.metrics.model.Timer;
import org.motechproject.metrics.api.Timer.Context;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TimerTest {
    @Mock
    private MetricsConfigFacade metricsConfigFacade;

    private Timer timer;

    @Before
    public void setUp() {
        timer = new Timer(new com.codahale.metrics.Timer(), metricsConfigFacade);
    }

    @Test
    public void testTimerWithMetricsEnabled() throws InterruptedException {
        when(metricsConfigFacade.isMetricsEnabled()).thenReturn(true);

        Context context = timer.time();
        Thread.sleep(1000);
        context.stop();

        Thread.sleep(4000);

        assertEquals(1, timer.getCount());

        assertEquals(1, timer.getCount(), 0);
        assertEquals(0.2, timer.getOneMinuteRate(), .01);
        assertEquals(0.2, timer.getFiveMinuteRate(), .01);
        assertEquals(0.2, timer.getFifteenMinuteRate(), .01);
        assertEquals(0.2, timer.getMeanRate(), .01);
    }

    @Test
    public void testTimerWithMetricsDisabled() throws InterruptedException {
        when(metricsConfigFacade.isMetricsEnabled()).thenReturn(false);

        Context context = timer.time();
        Thread.sleep(1000);
        context.stop();

        assertEquals(0, timer.getCount(), 0);
        assertEquals(0, timer.getOneMinuteRate(), 0);
        assertEquals(0, timer.getFiveMinuteRate(), 0);
        assertEquals(0, timer.getFifteenMinuteRate(), 0);
        assertEquals(0, timer.getMeanRate(), 0);
    }

}
