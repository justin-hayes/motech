package org.motechproject.metrics.ut.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.motechproject.metrics.config.MetricsConfigFacade;
import org.motechproject.metrics.model.Meter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MeterTest {
    @Mock
    private MetricsConfigFacade metricsConfigFacade;

    private Meter meter;

    @Before
    public void setUp() {
        meter = new Meter(new com.codahale.metrics.Meter(), metricsConfigFacade);
    }

    @Test
    public void testMeterWithMetricsEnabled() throws InterruptedException {
        when(metricsConfigFacade.isMetricsEnabled()).thenReturn(true);

        assertEquals(0, meter.getCount());
        assertEquals(0, meter.getMeanRate(), 0);
        assertEquals(0, meter.getOneMinuteRate(), 0);
        assertEquals(0, meter.getFiveMinuteRate(), 0);
        assertEquals(0, meter.getFifteenMinuteRate(), 0);

        meter.mark();
        assertEquals(1, meter.getCount());

        meter.mark(4);
        assertEquals(5, meter.getCount());

        Thread.sleep(5000);

        assertEquals(1, meter.getOneMinuteRate(), .01);
        assertEquals(1, meter.getFiveMinuteRate(), .01);
        assertEquals(1, meter.getFifteenMinuteRate(), .01);
        assertEquals(1, meter.getMeanRate(), .01);
    }

    @Test
    public void testMeterWithMetricsDisabled() throws InterruptedException {
        when(metricsConfigFacade.isMetricsEnabled()).thenReturn(false);

        assertEquals(0, meter.getCount());
        assertEquals(0, meter.getMeanRate(), 0);
        assertEquals(0, meter.getOneMinuteRate(), 0);
        assertEquals(0, meter.getFiveMinuteRate(), 0);
        assertEquals(0, meter.getFifteenMinuteRate(), 0);

        meter.mark();
        assertEquals(0, meter.getCount());

        meter.mark(4);
        assertEquals(0, meter.getCount());

        Thread.sleep(5000);

        assertEquals(0, meter.getOneMinuteRate(), 0);
        assertEquals(0, meter.getFiveMinuteRate(), 0);
        assertEquals(0, meter.getFifteenMinuteRate(), 0);
        assertEquals(0, meter.getMeanRate(), 0);
    }
}
