package org.motechproject.metrics.config;

public class MetricsConfig {
    private boolean metricsEnabled;
    private ConsoleReporterConfig consoleReporterConfig;
    private GraphiteReporterConfig graphiteReporterConfig;

    public boolean isMetricsEnabled() {
        return metricsEnabled;
    }

    public void setMetricsEnabled(boolean metricsEnabled) {
        this.metricsEnabled = metricsEnabled;
    }

    public ConsoleReporterConfig getConsoleReporterConfig() {
        return consoleReporterConfig;
    }

    public void setConsoleReporterConfig(ConsoleReporterConfig consoleReporterConfig) {
        this.consoleReporterConfig = consoleReporterConfig;
    }

    public GraphiteReporterConfig getGraphiteReporterConfig() {
        return graphiteReporterConfig;
    }

    public void setGraphiteReporterConfig(GraphiteReporterConfig graphiteReporterConfig) {
        this.graphiteReporterConfig = graphiteReporterConfig;
    }
}
