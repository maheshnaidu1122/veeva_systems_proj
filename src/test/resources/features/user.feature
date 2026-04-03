Feature: User Security

  Scenario: Invalid user creation (logical validation)
    When I create user with email "invalid_email"
    Then response should contain created username
    And response should not validate email format

  Scenario: Fetch non-existent user
    When I fetch user "user_<timestamp>"
    Then user should not be found
    And error message should contain "User not found"

  Scenario: Invalid login
    When I login with username "wrong_<timestamp>" and password "wrong"
    Then login should fail logically