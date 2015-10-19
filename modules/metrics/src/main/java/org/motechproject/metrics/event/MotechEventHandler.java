package org.motechproject.metrics.event;

import org.motechproject.event.MotechEvent;
import org.motechproject.event.listener.annotations.MotechListener;
import org.motechproject.metrics.service.MetricRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MotechEventHandler {
    private final MetricRegistryService metricRegistryService;

    @MotechListener(subjects = {EventSubjects.INCREMENT_COUNTER, EventSubjects.DECREMENT_COUNTER})
    public void incrementCounter(MotechEvent event) {
        String metricName = event.getParameters().get(EventParams.METRIC_NAME).toString();
        long value = (long) event.getParameters().get(EventParams.METRIC_VALUE);

        if (event.getSubject() == EventSubjects.INCREMENT_COUNTER) {
            metricRegistryService.incrementCounter(metricName, value);
        } else {
            metricRegistryService.decrementCounter(metricName, value);
        }
    }

    @MotechListener(subjects = EventSubjects.MARK_METER)
    public void markMeter(MotechEvent event) {
        String metricName = event.getParameters().get(EventParams.METRIC_NAME).toString();
        long value = (long) event.getParameters().get(EventParams.METRIC_VALUE);

        metricRegistryService.markMeter(metricName, value);
    }

    @MotechListener(subjects = EventSubjects.UPDATE_HISTOGRAM)
    public void updateHistogram(MotechEvent event) {
        String metricName = event.getParameters().get(EventParams.METRIC_NAME).toString();
        long value = (long) event.getParameters().get(EventParams.METRIC_VALUE);

        metricRegistryService.updateHistogram(metricName, value);
    }

    @Autowired
    public MotechEventHandler(MetricRegistryService metricRegistryService) {
        this.metricRegistryService = metricRegistryService;
    }
}
