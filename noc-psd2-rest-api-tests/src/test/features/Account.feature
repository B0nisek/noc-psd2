Feature: Account data
  Scenario: Existed user must return status code 200
    Given endpoint for user "johndoe" from bank "fidor"
    When data are received from server
    Then the status code is 200

  Scenario: None Existed user must return status code 400
    Given endpoint for user "kraken" from bank "fidor"
    When data are received from server
    Then the status code is 400

  Scenario: Existed user must have customer name in response
    Given endpoint for user "johndoe" from bank "fidor"
    When data are received from server
    Then body contains "johndoe"

#  TODO students should add test for non existed bank
