package org.motechproject.metrics.event;

public final class EventSubjects {
    public static final String INCREMENT_COUNTER = "metrics_counter_increment";
    public static final String DECREMENT_COUNTER = "metrics_counter_decrement";
    public static final String MARK_METER = "metrics_meter_mark";
    public static final String UPDATE_HISTOGRAM = "metrics_histogram_update";

    private EventSubjects() {}
}
