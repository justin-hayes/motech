package org.motechproject.sms.http.service;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.lang.StringUtils;
import org.motechproject.sms.http.SmsDeliveryFailureException;
import org.motechproject.sms.http.template.Authentication;
import org.motechproject.sms.http.template.SmsHttpTemplate;
import org.motechproject.sms.http.TemplateReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

@Service
public class SmsHttpService {
    @Autowired
    private TemplateReader templateReader;
    @Autowired
    private HttpClient commonsHttpClient;

    private SmsHttpTemplate template;

    private static final String DEFAULT_TEMPLATE_FILE = "/sms-http-template.json";
    private static Logger log = LoggerFactory.getLogger(SmsHttpService.class);

    private SmsHttpService(){
    }

    public SmsHttpService(TemplateReader templateReader, HttpClient commonsHttpClient, String templateFile) {
        this.template = templateReader.getTemplate(templateFile);
        this.commonsHttpClient = commonsHttpClient;
    }

    public SmsHttpService(TemplateReader templateReader, HttpClient commonsHttpClient) {
        this(templateReader, commonsHttpClient, DEFAULT_TEMPLATE_FILE);
    }

    public void sendSms(List<String> recipients, String message) throws SmsDeliveryFailureException {
        if (CollectionUtils.isEmpty(recipients) || StringUtils.isEmpty(message))
            throw new IllegalArgumentException("Recipients or Message should not be empty");

        String response;
        HttpMethod httpMethod = null;
        try {
            httpMethod = template.generateRequestFor(recipients, message).get(0);
            setAuthenticationInfo(template.getAuthentication());
            int status = commonsHttpClient.executeMethod(httpMethod);
            response = httpMethod.getResponseBodyAsString();
            log.info("HTTP Status:" + status + "|Response:" + response);
        } catch (Exception e) {
            log.error("SMSDeliveryFailure due to : ", e);
            throw new SmsDeliveryFailureException(e);
        } finally {
            if (httpMethod != null) httpMethod.releaseConnection();
        }

        if (response == null || !response.toLowerCase().contains(template.getResponseSuccessCode().toLowerCase())) {
            log.error(String.format("SMS delivery failed. Retrying...; Response: %s", response));
            throw new SmsDeliveryFailureException();
        }

        log.debug("SMS with message %s sent successfully to %s:", message, StringUtils.join(recipients.iterator(), ","));
    }

    private void setAuthenticationInfo(Authentication authentication) {
        if (authentication == null) return;

        commonsHttpClient.getParams().setAuthenticationPreemptive(true);
        commonsHttpClient.getState().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(authentication.getUsername(), authentication.getPassword()));
    }
}
