package org.motechproject.metrics.service;

import org.motechproject.metrics.config.ConsoleReporterConfig;
import org.motechproject.metrics.config.GraphiteReporterConfig;
import org.motechproject.metrics.config.MetricsConfig;

public interface MetricsConfigService {
    boolean isMetricsEnabled();
    MetricsConfig getMetricsConfig();
    void saveMetricsConfig(MetricsConfig config);
    ConsoleReporterConfig getConsoleReporterConfig();
    GraphiteReporterConfig getGraphiteReporterConfig();
}
