Feature: Pet CRUD Operations

  Scenario: Create Pet (C)
    Given I create a pet
    Then pet should be created successfully

  Scenario: Read Pet (R)
    Given I create a pet
    When I fetch the pet
    Then pet should be available

  Scenario: Update Pet (U)
    Given I create a pet
    When I update pet status to "sold"
    Then pet status should be updated

  Scenario: Delete Pet (D)
    Given I create a pet
    When I delete the pet
    Then pet should be deleted