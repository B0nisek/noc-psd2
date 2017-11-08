Feature: Get token after authentication
  Scenario: Regular User calls will receive successful response
    Given Authorize as regular user
    When a user retrieves the response
    Then the status code is 200
    And response includes the token

#    TODO try to authorize with none existing user