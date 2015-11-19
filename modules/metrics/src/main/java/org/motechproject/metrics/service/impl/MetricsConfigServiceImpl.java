package org.motechproject.metrics.service.impl;

import org.motechproject.metrics.config.ConsoleReporterConfig;
import org.motechproject.metrics.config.GraphiteReporterConfig;
import org.motechproject.metrics.config.MetricsConfig;
import org.motechproject.metrics.service.MetricsConfigService;
import org.motechproject.server.config.SettingsFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang.StringUtils.isNotBlank;

@Service("metricsConfigService")
public class MetricsConfigServiceImpl implements MetricsConfigService {
    private final SettingsFacade settingsFacade;

    private static final String CONFIG_FILE_NAME = "metrics.properties";

    public static final String METRICS_ENABLED = "metrics.enabled";

    public static final String CONSOLE_REPORTER_ENABLED = "reporter.console.enabled";
    public static final String CONSOLE_REPORTER_CONVERT_RATES_UNIT = "reporter.console.convertRatesUnit";
    public static final String CONSOLE_REPORTER_CONVERT_DURATIONS_UNIT = "reporter.console.convertDurationsUnit";
    public static final String CONSOLE_REPORTER_REPORTING_FREQUENCY_VALUE = "reporter.console.reportingFrequency.value";
    public static final String CONSOLE_REPORTER_REPORTING_FREQUENCY_UNIT = "reporter.console.reportingFrequency.unit";

    public static final String GRAPHITE_REPORTER_ENABLED = "reporter.graphite.enabled";
    public static final String GRAPHITE_REPORTER_GRAPHITE_SERVER_URI = "reporter.graphite.server.uri";
    public static final String GRAPHITE_REPORTER_GRAPHITE_SERVER_PORT = "reporter.graphite.server.port";
    public static final String GRAPHITE_REPORTER_CONVERT_RATES_UNIT = "reporter.graphite.convertRatesUnit";
    public static final String GRAPHITE_REPORTER_CONVERT_DURATIONS_UNIT = "reporter.graphite.convertDurationsUnit";
    public static final String GRAPHITE_REPORTER_REPORTING_FREQUENCY_VALUE = "reporter.graphite.reportingFrequency.value";
    public static final String GRAPHITE_REPORTER_REPORTING_FREQUENCY_UNIT = "reporter.graphite.reportingFrequency.unit";

    @Autowired
    public MetricsConfigServiceImpl(@Qualifier("metricsSettings") SettingsFacade settingsFacade) {
        this.settingsFacade = settingsFacade;
    }

    @Override
    public boolean isMetricsEnabled() {
        return Boolean.valueOf(settingsFacade.getProperty(METRICS_ENABLED));
    }

    @Override
    public MetricsConfig getMetricsConfig() {
        MetricsConfig config = new MetricsConfig();

        config.setMetricsEnabled(isMetricsEnabled());
        config.setConsoleReporterConfig(getConsoleReporterConfig());
        config.setGraphiteReporterConfig(getGraphiteReporterConfig());

        return config;
    }

    @Override
    public void saveMetricsConfig(MetricsConfig config) {
        Properties properties = settingsFacade.asProperties();

        properties.setProperty(METRICS_ENABLED, Boolean.toString(config.isMetricsEnabled()));

        setConsoleReporterProperties(properties, config.getConsoleReporterConfig());
        setGraphiteReporterProperties(properties, config.getGraphiteReporterConfig());

        settingsFacade.saveConfigProperties(CONFIG_FILE_NAME, properties);
    }

    @Override
    public ConsoleReporterConfig getConsoleReporterConfig() {
        ConsoleReporterConfig config = new ConsoleReporterConfig();

        config.setEnabled(Boolean.valueOf(getPropertyValue(CONSOLE_REPORTER_ENABLED)));
        config.setConvertRates(TimeUnit.valueOf(getPropertyValue(CONSOLE_REPORTER_CONVERT_RATES_UNIT)));
        config.setConvertDurations(TimeUnit.valueOf(getPropertyValue(CONSOLE_REPORTER_CONVERT_DURATIONS_UNIT)));
        config.setFrequency(Integer.valueOf(getPropertyValue(CONSOLE_REPORTER_REPORTING_FREQUENCY_VALUE)));
        config.setFrequencyUnit(TimeUnit.valueOf(getPropertyValue(CONSOLE_REPORTER_REPORTING_FREQUENCY_UNIT)));

        return config;
    }

    @Override
    public GraphiteReporterConfig getGraphiteReporterConfig() {
        GraphiteReporterConfig config = new GraphiteReporterConfig();

        config.setEnabled(Boolean.valueOf(getPropertyValue(GRAPHITE_REPORTER_ENABLED)));
        config.setServerUri(getPropertyValue(GRAPHITE_REPORTER_GRAPHITE_SERVER_URI));
        config.setServerPort(Integer.valueOf(getPropertyValue(GRAPHITE_REPORTER_GRAPHITE_SERVER_PORT)));
        config.setConvertRates(TimeUnit.valueOf(getPropertyValue(GRAPHITE_REPORTER_CONVERT_RATES_UNIT)));
        config.setConvertDurations(TimeUnit.valueOf(getPropertyValue(GRAPHITE_REPORTER_CONVERT_DURATIONS_UNIT)));
        config.setFrequency(Integer.valueOf(getPropertyValue(GRAPHITE_REPORTER_REPORTING_FREQUENCY_VALUE)));
        config.setFrequencyUnit(TimeUnit.valueOf(getPropertyValue(GRAPHITE_REPORTER_REPORTING_FREQUENCY_UNIT)));

        return config;
    }

    private void setConsoleReporterProperties(Properties properties, ConsoleReporterConfig config) {
        properties.setProperty(CONSOLE_REPORTER_ENABLED, Boolean.toString(config.isEnabled()));
        properties.setProperty(CONSOLE_REPORTER_CONVERT_RATES_UNIT, config.getConvertRates().toString());
        properties.setProperty(CONSOLE_REPORTER_CONVERT_DURATIONS_UNIT, config.getConvertDurations().toString());
        properties.setProperty(CONSOLE_REPORTER_REPORTING_FREQUENCY_VALUE, Integer.toString(config.getFrequency()));
        properties.setProperty(CONSOLE_REPORTER_REPORTING_FREQUENCY_UNIT, config.getFrequencyUnit().toString());
    }

    private void setGraphiteReporterProperties(Properties properties, GraphiteReporterConfig config) {
        properties.setProperty(GRAPHITE_REPORTER_ENABLED, Boolean.toString(config.isEnabled()));
        properties.setProperty(GRAPHITE_REPORTER_GRAPHITE_SERVER_URI, config.getServerUri());
        properties.setProperty(GRAPHITE_REPORTER_GRAPHITE_SERVER_PORT, Integer.toString(config.getServerPort()));
        properties.setProperty(GRAPHITE_REPORTER_CONVERT_RATES_UNIT, config.getConvertRates().toString());
        properties.setProperty(GRAPHITE_REPORTER_CONVERT_DURATIONS_UNIT, config.getConvertDurations().toString());
        properties.setProperty(GRAPHITE_REPORTER_REPORTING_FREQUENCY_VALUE, Integer.toString(config.getFrequency()));
        properties.setProperty(GRAPHITE_REPORTER_REPORTING_FREQUENCY_UNIT, config.getFrequencyUnit().toString());
    }

    private String getPropertyValue(final String propertyKey) {
        String propertyValue = settingsFacade.getProperty(propertyKey);
        return isNotBlank(propertyValue) ? propertyValue : null;
    }

}
