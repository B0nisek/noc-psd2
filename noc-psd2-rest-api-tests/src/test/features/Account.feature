Feature: Account
  Scenario: Existing user from fidor bank must return status code 200
    Given endpoint for user "johndoe" from bank fidor
    When account data are received for fidor bank
    Then the status code from account endpoint is 200

  Scenario: None existing user must return status code 400 by reaching existing endpoint of fidor bank
    Given endpoint for user "kraken" from bank fidor
    When account data are received for fidor bank
    Then the status code from account endpoint is 400

  Scenario: Existing user from fidor bank must have customer name in response
    Given endpoint for user "johndoe" from bank fidor
    When account data are received for fidor bank
    Then body in account contains "johndoe"

  Scenario: Existing user from fidor bank must have information if account is verified
    Given endpoint for user "johndoe" from bank fidor
    When account data are received for fidor bank
    Then body in account contains is_verified section with value

#  TODO implement the following scenario: Existed user from erste bank must return status code 200
