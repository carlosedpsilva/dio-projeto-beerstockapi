Feature: Increment a Beer quantity by ID

Scenario: User tries to increment a Beer quantity with a valid ID and valid quantity
  When repository find by id method is called and result is found
  When service save method is called
  Then a success message response should be shown with status code ok on increment beer quantity

Scenario: User tries to increment a Beer quantity with a valid ID and invalid quantity
  When repository find by id method is called and result is found
  Then a BeerStockExceededException should be thrown on increment beer quantity

Scenario: User tries to increment a Beer quantity with an invalid ID
  When repository find by id method is called and no result is found
  Then a BeerNotFoundException should be thrown on increment beer quantity
