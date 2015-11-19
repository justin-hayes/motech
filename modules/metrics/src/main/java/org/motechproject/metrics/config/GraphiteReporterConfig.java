package org.motechproject.metrics.config;

public class GraphiteReporterConfig extends BaseReporterConfig {
    private String serverUri;
    private int serverPort;

    public String getServerUri() {
        return serverUri;
    }

    public void setServerUri(String serverUri) {
        this.serverUri = serverUri;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
}
