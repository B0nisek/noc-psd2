Feature: IBAN
  Scenario: Existing user and existing IBAN from fidor bank must return status code 200
    Given endpoint for IBAN "DE32700222009510260101" from bank fidor
    When IBAN data are received for fidor bank
    Then the status code from IBAN endpoint is 200

#  TODO add test for erste scenario: Existed user and none existing IBAN from fidor bank must return status code 400
