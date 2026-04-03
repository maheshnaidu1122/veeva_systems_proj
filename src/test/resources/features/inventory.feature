Feature: Inventory Analysis

  Scenario: Compare inventory and API list

    Given I fetch inventory
    Then API response should be successful
    And I extract available count

    When I fetch pets with status "available"
    Then pet list response should be successful
    And I count pets in list

    Then both counts should match