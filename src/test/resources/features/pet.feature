Feature: Pet CRUD

  Scenario: Create Pet
    Given I create a pet with name "pet_<timestamp>" and status "available"
    Then API response should be successful
    And I store pet id from response

  Scenario: Get Pet
    Given I create a pet with name "pet_<timestamp>" and status "available"
    And I store pet id from response
    When I get the pet by stored id
    Then pet name should match stored name
    And pet status should be "available"

  Scenario: Update Pet
    Given I create a pet with name "pet_<timestamp>" and status "available"
    And I store pet id from response
    When I update pet status to "sold"
    Then API response should be successful

  Scenario: Delete Pet
    Given I create a pet with name "pet_<timestamp>" and status "available"
    And I store pet id from response
    When I delete the pet
    Then API response should be successful