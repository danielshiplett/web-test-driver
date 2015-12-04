@web-site @functional @test1
Feature: Validate Main Unauthenticated View Layout
    As an unauthenticated user of site
    I want to see the Main Unauthenticated View
    And verify that all required elements are rendered

Scenario: Validate Main Unauthenticated View Layout
    Given I navigate to the site
    Then I should see all the required Main Unauthenticated View elements