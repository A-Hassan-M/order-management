package com.qeema.practicaltest.ordermanagement.bdds.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class OrderStepDefinitions {

    private String baseUrl = "http://localhost:8080/api";
    private String accessToken;
    private Response response;

    @When("the user creates a new product with name {string}, price {double}, and quantity {int}")
    public void theUserAddsANewProduct(String name, double price, int quantity) {
        response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body("{ \"name\": \"" + name + "\", \"price\": " + price + ", \"quantity\": " + quantity + " }")
                .when()
                .post(baseUrl + "/products")
                .then().statusCode(201).extract().response();
    }

    @Given("the user is authenticated with email {string} and password {string}")
    public void theUserAuthenticates(String email, String password) {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body("{ \"email\": \"" + email + "\", \"password\": \"" + password + "\" }")
                .when().post(baseUrl + "/auth")
                .then().statusCode(200).extract().response();
        accessToken = response.jsonPath().getString("accessToken");
    }

    @When("the user creates an order with products:")
    public void theUserCreatesAnOrder(String orderJson) {
        response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(orderJson)
                .when()
                .post(baseUrl + "/orders");
    }

    @Then("the order is successfully created with status code {int}")
    public void theOrderIsSuccessfullyCreated(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @When("the user lists all orders")
    public void theUserListsAllOrders() {
        response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(baseUrl + "/orders");
    }

    @Then("the list of orders is retrieved with status code {int}")
    public void theListOfOrdersIsRetrieved(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("the order message should be {string}")
    public void checkOrderMessage(String orderMessage) {
        response.then().body("message", equalToIgnoringCase(orderMessage));
    }

    @Then("the order status should be {string}")
    public void checkOrderStatus(String orderStatus) {

        response.then().body("[0].status", equalToIgnoringCase(orderStatus));
    }


}
