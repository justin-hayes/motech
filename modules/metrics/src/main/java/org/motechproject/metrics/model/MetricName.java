package org.motechproject.metrics.model;

import com.codahale.metrics.MetricRegistry;

public final class MetricName {
    public static String get(String name, String... names) {
        return MetricRegistry.name(name, names);
    }

    public static String get(Class<?> klass, String... names) {
        return MetricRegistry.name(klass, names);
    }

    private MetricName() {}
}
