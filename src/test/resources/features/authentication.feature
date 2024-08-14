#Feature: User Authentication
#  Scenario: User successfully authenticates
#    Given a user with valid credentials 'test@test.com' 'admin123456'
#    When the user submits the authentication request
#    Then the user should receive a Bearer token in the response
#
#  Scenario: User fails to authenticate with invalid credentials
#    Given a user with invalid credentials 'test@test.com' 'admin1234567'
#    When the user submits the authentication request
#    Then the response should be unauthorized
