Feature: Cross Endpoint Validation

  Scenario: Validate pet exists in sold list
    Given I create a pet with name "pet_<timestamp>" and status "available"
    Then API response should be successful
    And I store pet id from response
    When I update pet status to "sold"
    Then API response should be successful
    When I fetch pets with status "sold"
    Then pet list response should be successful
    When I search for stored pet id in list
    Then pet should exist in the list