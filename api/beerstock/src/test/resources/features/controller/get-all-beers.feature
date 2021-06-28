Feature: Get all Beers

Scenario: User tries to get all Beers
  When service find all method is called
  Then a beer paged response should be shown we status ok on get all beers
