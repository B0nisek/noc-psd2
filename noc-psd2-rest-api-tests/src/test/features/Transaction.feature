Feature: Transaction
  Scenario: Valid endpoint for fidor bank and existing IBAN must return status code 200
    Given transaction endpoint for IBAN "DE32700222009510260101" for fidor bank
    When transaction data are received for fidor bank
    Then the status code from transaction endpoint is 200

  Scenario: All existing transactions can be listed with valid token
    Given valid token from basic user
    When all transaction are recieved from server
    Then response contains "bankAccounts"

# TODO add tests for specific one transaction for fidor bank, url: http://localhost:8080/fidor/transaction/5