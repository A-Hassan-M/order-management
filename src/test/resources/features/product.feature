Feature: Product CRUD Operations

  Background:
    Given the user authenticates with email "test@test.com" and password "admin123456"

  Scenario: Create a new product
    When the user adds a new product with name "Laptop", price 999.99, and quantity 10
    Then the response status should be 201
    And the response should contain the product name "Laptop"

  Scenario: Create a duplicate product
    When the user adds a new product with name "Laptop", price 999.99, and quantity 10
    Then the response status should be 400

  Scenario: List all products
    When the user retrieves all products
    Then the response status should be 200
    And the response should contain a list of products

  Scenario: Retrieve a product by ID
    Given the user adds a new product with name "Smartphone", price 499.99, and quantity 20
    When the user retrieves the product with id
    Then the response status should be 200
    And the response should contain the product details with name "Smartphone"

  Scenario: Update a product
    Given the user adds a new product with name "Tablet", price 299.99, and quantity 15
    When the user updates the product to have name "Updated Tablet", price 349.99, and quantity 10
    Then the response status should be 200
    And the response should contain the updated product name "Updated Tablet"

  Scenario: Delete a product
    Given the user adds a new product with name "Headphones", price 89.99, and quantity 30
    When the user deletes the product with id
    Then the response status should be 204
    And the user tries to retrieve the product with id
    And the response status should be 404
