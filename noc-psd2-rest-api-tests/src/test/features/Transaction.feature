Feature: Transaction
  Scenario: Valid endpoint for existed bank and existed IBAN must return status code 200
    Given transaction endpoint for IBAN "DE32700222009510260101" from bank "fidor"
    When transaction data are received from server
    Then the status code from transaction endpoint is 200

# TODO add tests for specific one transaction url: http://localhost:8080/fidor/transaction/5