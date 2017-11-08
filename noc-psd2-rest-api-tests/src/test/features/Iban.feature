Feature: IBAN
  Scenario: Existed user and existed IBAN must return status code 200
    Given endpoint for IBAN "DE32700222009510260101" from bank "fidor"
    When IBAN data are received from server
    Then the status code from IBAN endpoint is 200

#  TODO add test for non existed IBAN
