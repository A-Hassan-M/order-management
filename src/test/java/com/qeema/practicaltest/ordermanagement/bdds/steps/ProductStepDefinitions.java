package com.qeema.practicaltest.ordermanagement.bdds.steps;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;

@Slf4j
public class ProductStepDefinitions {

    private Response response;
    private final String baseUrl = "http://localhost:8080/api";
    private String accessToken;
    private Long productId;

    @Given("the user authenticates with email {string} and password {string}")
    public void theUserAuthenticates(String email, String password) {
        Response authResponse = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body("{ \"email\": \"" + email + "\", \"password\": \"" + password + "\" }")
                .when()
                .post(baseUrl + "/auth")
                .then()
                .statusCode(200)
                .extract()
                .response();

        accessToken = authResponse.jsonPath().getString("accessToken");
        log.info("Access Token: " + accessToken);
    }

    @When("the user adds a new product with name {string}, price {double}, and quantity {int}")
    public void theUserAddsANewProduct(String name, double price, int quantity) {
        response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body("{ \"name\": \"" + name + "\", \"price\": " + price + ", \"quantity\": " + quantity + " }")
                .when()
                .post(baseUrl + "/products");
        if(response.getStatusCode() == HttpStatus.CREATED.value())
            productId = response.jsonPath().getLong("Id");
        log.info("Add Product Response: " + response.getBody().asString());
    }

    @When("the user retrieves all products")
    public void theUserRetrievesAllProducts() {
        response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(baseUrl + "/products");

        log.info("Retrieve All Products Response: " + response.getBody().asString());
    }

    @When("the user retrieves the product with id")
    public void theUserRetrievesTheProductWithId() {
        response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(baseUrl + "/products/" + productId);

        log.info("Retrieve Product By ID Response: " + response.getBody().asString());
    }

    @When("the user updates the product to have name {string}, price {double}, and quantity {int}")
    public void theUserUpdatesTheProductWithIdToHaveDetails(String name, double price, int quantity) {
        response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body("{ \"name\": \"" + name + "\", \"price\": " + price + ", \"quantity\": " + quantity + " }")
                .when()
                .put(baseUrl + "/products/" + productId);

        log.info("Update Product Response: " + response.getBody().asString());
    }

    @When("the user deletes the product with id")
    public void theUserDeletesTheProductWithId() {
        response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(baseUrl + "/products/" + productId);

        log.info("Delete Product Response: " + response.getBody().asString());
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int statusCode) {
        response.then().statusCode(statusCode);
        log.info("Response Status Code: " + response.getStatusCode());
    }

    @Then("the response should contain the product name {string}")
    public void theResponseShouldContainTheProductName(String name) {
        response.then().body("name", equalTo(name));
    }

    @Then("the response should contain a list of products")
    public void theResponseShouldContainAListOfProducts() {
        response.then().body("size()", greaterThan(0));
    }

    @Then("the response should contain the product details with name {string}")
    public void theResponseShouldContainTheProductDetailsWithName(String name) {
        response.then().body("name", equalTo(name));
    }

    @Then("the user tries to retrieve the product with id")
    public void theUserTriesToRetrieveTheProductWithId() {
        response = given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(baseUrl + "/products/" + productId);

        log.info("Try Retrieve Product By ID Response: " + response.getBody().asString());
    }

    @Then("the response should contain the updated product name {string}")
    public void theResponseShouldContainTheUpdatedProductName(String name) {
        response.then().body("name", equalTo(name));
    }
}
