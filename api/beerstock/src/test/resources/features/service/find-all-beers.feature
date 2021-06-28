Feature: Find all Beers

Scenario: User tries to retrieve all Beers
  When repository find all method is called
  Then a beer paged response should be returned on find all beers
