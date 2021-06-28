Feature: Find a Beer by ID

Scenario: User tries to retrieve a Beer with a valid ID
  When repository find by id method is called and result is found
  Then the corresponding beer entity response should be returned on find beer by id

Scenario: User tries to retrieve a Beer with a invalid ID
  When repository find by id method is called and no result is found
  Then a BeerNotFoundException should be thrown on find beer by id
