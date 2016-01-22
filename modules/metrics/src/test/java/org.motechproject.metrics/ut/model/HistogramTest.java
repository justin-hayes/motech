package org.motechproject.metrics.ut.model;

import com.codahale.metrics.ExponentiallyDecayingReservoir;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.motechproject.metrics.api.Snapshot;
import org.motechproject.metrics.config.MetricsConfigFacade;
import org.motechproject.metrics.model.Histogram;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HistogramTest {
    @Mock
    private MetricsConfigFacade metricsConfigFacade;

    private Histogram histogram;

    @Before
    public void setUp() {
        histogram = new Histogram(new com.codahale.metrics.Histogram(new ExponentiallyDecayingReservoir()), metricsConfigFacade);
    }

    @Test
    public void testHistogramWithMetricsEnabled() {
        when(metricsConfigFacade.isMetricsEnabled()).thenReturn(true);

        for (int i = 1; i <= 9; i++) {
            histogram.update(i);
        }

        assertEquals(9, histogram.getCount());

        histogram.update(10L);

        assertEquals(10, histogram.getCount());

        Snapshot snapshot = histogram.getSnapshot();
        assertEquals(8.0, snapshot.get75thPercentile(), 0);
        assertEquals(10, snapshot.get95thPercentile(), 0);
        assertEquals(10, snapshot.get99thPercentile(), 0);
        assertEquals(10, snapshot.get999thPercentile(), 0);
        assertEquals(10, snapshot.getMax());
        assertEquals(5.5, snapshot.getMean(), .001);
        assertEquals(6.0, snapshot.getMedian(), 0);
        assertEquals(1, snapshot.getMin());
        assertEquals(2.872, snapshot.getStdDev(), .001);
        assertEquals(6.0, snapshot.getValue(.5), 0);
        assertArrayEquals(new long[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, snapshot.getValues());
    }

    @Test
    public void testHistogramWithMetricsDisabled() {
        when(metricsConfigFacade.isMetricsEnabled()).thenReturn(false);

        for (int i = 1; i <= 9; i++) {
            histogram.update(i);
        }

        assertEquals(0, histogram.getCount());

        histogram.update(10L);

        assertEquals(0, histogram.getCount());

        Snapshot snapshot = histogram.getSnapshot();
        assertEquals(0, snapshot.get75thPercentile(), 0);
        assertEquals(0, snapshot.get95thPercentile(), 0);
        assertEquals(0, snapshot.get99thPercentile(), 0);
        assertEquals(0, snapshot.get999thPercentile(), 0);
        assertEquals(0, snapshot.getMax());
        assertEquals(0, snapshot.getMean(), 0);
        assertEquals(0, snapshot.getMedian(), 0);
        assertEquals(0, snapshot.getMin());
        assertEquals(0, snapshot.getStdDev(), 0);
        assertEquals(0, snapshot.getValue(.5), 0);
        assertArrayEquals(new long[]{}, snapshot.getValues());
    }
}
