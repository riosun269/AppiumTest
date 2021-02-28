Feature: Welcome test

  Background:
    Given I navigate to login page

  @check
  Scenario: Verify welcome screen
    Given I verify Hello button
    Then I see welcome message correctly
