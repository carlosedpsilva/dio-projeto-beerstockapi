Feature: Delete a Beer by ID

Scenario: User tries to retrieve a Beer with a valid ID
  When service delete by id method is called and result is found
  Then a success message response should be shown with status code created on delete beer by id

Scenario: User tries to retrieve a Beer with an invalid ID
  When service delete by id method is called and no result is found
  Then an error message response should be shown with status code not found on delete beer by id
