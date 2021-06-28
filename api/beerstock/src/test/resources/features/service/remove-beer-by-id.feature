Feature: Remove a Beer by ID

Scenario: User tries to delete a Beer with a valid ID
  When repository find by id method is called and result is found
  And repository delete by id method is called
  Then a success message response should be returned on delete beer by id

Scenario: User tries to delete a Beer with a invalid ID
  When repository find by id method is called and no result is found
  Then a BeerNotFoundException should be thrown on delete beer by id
