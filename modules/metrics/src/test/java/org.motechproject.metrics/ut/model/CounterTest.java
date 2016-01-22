package org.motechproject.metrics.ut.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.motechproject.metrics.config.MetricsConfigFacade;
import org.motechproject.metrics.model.Counter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CounterTest {
    @Mock
    private MetricsConfigFacade metricsConfigFacade;

    private Counter counter;

    @Before
    public void setUp() {
        counter = new Counter(new com.codahale.metrics.Counter(), metricsConfigFacade);
    }

    @Test
    public void testCounterWithMetricsEnabled() {
        when(metricsConfigFacade.isMetricsEnabled()).thenReturn(true);

        assertEquals(counter.getCount(), 0);

        counter.inc();
        assertEquals(counter.getCount(), 1);

        counter.inc(4);
        assertEquals(counter.getCount(), 5);

        counter.dec();
        assertEquals(counter.getCount(), 4);

        counter.dec(3);
        assertEquals(counter.getCount(), 1);
    }

    @Test
    public void testCounterWithMetricsDisabled() {
        when(metricsConfigFacade.isMetricsEnabled()).thenReturn(false);

        assertEquals(counter.getCount(), 0);

        counter.inc();
        assertEquals(counter.getCount(), 0);

        counter.inc(4);
        assertEquals(counter.getCount(), 0);

        counter.dec();
        assertEquals(counter.getCount(), 0);

        counter.dec(3);
        assertEquals(counter.getCount(), 0);
    }
}
