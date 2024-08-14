package com.qeema.practicaltest.ordermanagement.bdds.steps.config;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RequestCallback;
import org.springframework.http.client.ClientHttpRequest;

public class HeaderSettingRequestCallback implements RequestCallback {
    final HttpHeaders requestHeaders;

    private String body;

    public HeaderSettingRequestCallback(HttpHeaders requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public HeaderSettingRequestCallback(HttpHeaders requestHeaders, String body) {
        this.requestHeaders = requestHeaders;
        this.body = body;
    }

    @Override
    public void doWithRequest(ClientHttpRequest request) throws IOException {
        final HttpHeaders clientHeaders = request.getHeaders();
        clientHeaders.addAll(requestHeaders);
        if (null != body) {
            request.getBody().write(body.getBytes());
        }
    }
}