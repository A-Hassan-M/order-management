package com.qeema.practicaltest.ordermanagement.bdds.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.test.context.ContextConfiguration;
import com.qeema.practicaltest.ordermanagement.openapi.model.Credentials;
import com.qeema.practicaltest.ordermanagement.openapi.model.LoginResponse;
import com.qeema.practicaltest.ordermanagement.openapi.api.AuthApiController;
import com.qeema.practicaltest.ordermanagement.domain.exceptions.AuthenticationFailedException;

@RequiredArgsConstructor
@ContextConfiguration(classes = {AuthApiController.class}) // Include AuthService in the context
public class AuthenticationStepDefinitions {

    private final AuthApiController authApi;

    private Credentials credentials;
    private ResponseEntity<LoginResponse> response;
    private Exception exception;

    @Given("a user with valid credentials {string} {string}")
    public void aUserWithValidCredentials(String email, String password) {
        credentials = new Credentials();
        credentials.setEmail(email);
        credentials.setPassword(password);
    }

    @Given("a user with invalid credentials {string} {string}")
    public void aUserWithInvalidCredentials(String email, String password) {
        credentials = new Credentials();
        credentials.setEmail(email);
        credentials.setPassword(password);
    }

    @When("the user submits the authentication request")
    public void theUserSubmitsTheAuthenticationRequest() {
        try {
            response = authApi.authenticate(credentials);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the user should receive a Bearer token in the response")
    public void theUserShouldReceiveABearerTokenInTheResponse() {
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getAccessToken());
    }

    @Then("the response should be unauthorized")
    public void theResponseShouldBeUnauthorized() {
        assertNotNull(exception);
        assertTrue(exception.getCause() instanceof AuthenticationFailedException);
    }
}
