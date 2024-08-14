package com.qeema.practicaltest.ordermanagement.bdds.steps.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;

@Getter
public class ResponseResults {
    private final ClientHttpResponse theResponse;
    private final String body;

    ResponseResults(final ClientHttpResponse response) throws IOException {
        this.theResponse = response;
        final InputStream bodyInputStream = response.getBody();
        final StringWriter stringWriter = new StringWriter();
        IOUtils.copy(bodyInputStream, stringWriter, Charset.defaultCharset());
        this.body = stringWriter.toString();
    }
}