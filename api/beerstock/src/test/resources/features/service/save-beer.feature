Feature: Save a Beer

Scenario: User tries to save a Beer with a name which is not in the database
  Given a valid Beer insert request is provided
  When repository find by name method is called and no result is found
  And repository save method is called
  Then a success message response should be returned

@DirtyContextAfter
Scenario: User tries to save a Beer with a name which is already in the database
  Given a valid Beer insert request is provided
  When repository find by name method is called and result is found
  Then a BeerAlreadyExistsException should be thrown
