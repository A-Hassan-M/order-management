Feature: Order Management System
  As a user, I want to manage orders and products in the order management system.

  Background:
    Given the user is authenticated with email "test@test.com" and password "admin123456"

  Scenario: Creating an order
    When the user creates a new product with name "Product A", price 999.99, and quantity 10
    And the user creates a new product with name "Product B", price 999.99, and quantity 10
    And the user creates an order with products:
      """
      {
        "products": [
          {
            "productName": "Product A",
            "quantity": 2
          },
          {
            "productName": "Product B",
            "quantity": 3
          }
        ]
      }
      """
    Then the order is successfully created with status code 201
    Then the order message should be 'Order is now processing'

  Scenario: Listing all orders
    When the user lists all orders
    Then the list of orders is retrieved with status code 200
    Then the order status should be 'PENDING'
