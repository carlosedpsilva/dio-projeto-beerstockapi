Feature: Get a Beer by ID

Scenario: User tries to retrieve a Beer with a valid ID
  When service find by id method is called and result is found
  Then the corresponding beer entity response should be shown with status code ok on get beer by id

Scenario: User tries to retrieve a Beer with an invalid ID
  When service find by id method is called and no result is found
  Then an error message response should be shown with status code not found on get beer by id
