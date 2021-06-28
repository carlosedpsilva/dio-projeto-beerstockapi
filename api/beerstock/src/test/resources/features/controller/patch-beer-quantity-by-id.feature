Feature: Patch a Beer quantity by ID

Scenario: User tries to increment a Beer quantity with a valid ID and valid quantity
  When service increment beer quantity method is called
  Then a success message response should be shown with status code ok on patch beer quantity

Scenario: User tries to increment a Beer quantity with a valid ID and invalid quantity
  When service increment beer quantity method is called and no increment is invalid
  Then a BeerStockExceededException should be thrown on patch beer quantity

Scenario: User tries to increment a Beer quantity with an invalid ID
  When service increment beer quantity method is called and no result is found
  Then a BeerNotFoundException should be thrown on patch beer quantity
