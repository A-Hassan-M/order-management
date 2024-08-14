package com.qeema.practicaltest.ordermanagement.bdds.steps.config;

import java.util.List;
import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.boot.test.context.SpringBootTest;
import com.qeema.practicaltest.ordermanagement.OrderManagementApplication;

@CucumberContextConfiguration
@SpringBootTest(classes = OrderManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)// Replace with your main application class
@ActiveProfiles("test")
public class CucumberSpringConfiguration {
    static ResponseResults latestResponse = null;

    protected RestTemplate restTemplate = new RestTemplate();

    protected ResponseResults executeGet(String url, HttpHeaders headers) throws IOException {
        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate.execute(url, HttpMethod.GET, requestCallback, response -> {
            if (errorHandler.hadError) {
                return (errorHandler.getResults());
            } else {
                return (new ResponseResults(response));
            }
        });
        return latestResponse;
    }

    protected ResponseResults executePost(String url, String body, HttpHeaders headers) {
        if(headers == null){
            headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers, body);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();
        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate
                .execute(url, HttpMethod.POST, requestCallback, response -> {
                    if (errorHandler.hadError) {
                        return (errorHandler.getResults());
                    } else {
                        return (new ResponseResults(response));
                    }
                });
        return latestResponse;
    }

    private class ResponseResultErrorHandler implements ResponseErrorHandler {
        private ResponseResults results = null;
        private Boolean hadError = false;

        private ResponseResults getResults() {
            return results;
        }

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            hadError = response.getStatusCode().value() >= 400;
            return hadError;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            results = new ResponseResults(response);
        }
    }
}