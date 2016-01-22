package org.motechproject.metrics.it;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.metrics.api.Counter;
import org.motechproject.metrics.api.Gauge;
import org.motechproject.metrics.api.Histogram;
import org.motechproject.metrics.api.Meter;
import org.motechproject.metrics.api.Timer;
import org.motechproject.metrics.service.MetricRegistryService;
import org.motechproject.testing.osgi.BasePaxIT;
import org.motechproject.testing.osgi.container.MotechNativeTestContainerFactory;
import org.ops4j.pax.exam.ExamFactory;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerSuite;

import javax.inject.Inject;

import java.util.function.Supplier;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerSuite.class)
@ExamFactory(MotechNativeTestContainerFactory.class)
public class MetricRegistryServiceBundleIT extends BasePaxIT {
    @Inject
    private MetricRegistryService metricRegistryService;

    @Test
    public void shouldRegisterMetricRegistryServiceInstance() throws Exception {
        assertNotNull(metricRegistryService);
    }

    @Test
    public void shouldCreateCounter() {
        Counter counter = metricRegistryService.counter("counter");
        assertNotNull(counter);
    }

    @Test
    public void shouldCreateHistogram() {
        Histogram histogram = metricRegistryService.histogram("histogram");
        assertNotNull(histogram);
    }

    @Test
    public void shouldCreateMeter() {
        Meter meter = metricRegistryService.meter("meter");
        assertNotNull(meter);
    }

    @Test
    public void shouldCreateTimer() {
        Timer timer =  metricRegistryService.timer("timer");
        assertNotNull(timer);
    }

    @Test
    public void shouldRegisterGauge() {
        Gauge<Double> gauge = metricRegistryService.registerGauge("gauge", new Gauge<Double>() {
            @Override
            public Double getValue() {
                return 1.0;
            }
        });
        assertNotNull(gauge);
    }

    @Test
    public void shouldRegisterRatioGauge() {
        Gauge<Double> ratioGauge = metricRegistryService.registerRatioGauge("ratioGauge",
                new Supplier<Double>() {
                    @Override
                    public Double get() {
                        return 1.0;
                    }
                },
                new Supplier<Double>(){
                    @Override
                    public Double get() {
                        return 2.0;
                    }
                });
        assertNotNull(ratioGauge);
    }

    @Test
    public void shouldIdentifyMetricAsRegistered() {
        metricRegistryService.counter("counter");
        assertTrue(metricRegistryService.isRegistered("counter"));
    }

    @Test
    public void shouldIdentifyMetricAsNotRegistered() {
        assertFalse(metricRegistryService.isRegistered("foo"));
    }
}
