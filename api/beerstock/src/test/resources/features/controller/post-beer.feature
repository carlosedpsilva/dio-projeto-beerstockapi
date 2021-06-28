Feature: Post a Beer

Scenario: User tries to Post a Beer with a name which is not in the database
  Given a valid Beer insert request is provided
  When service save method is called
  Then a success message response should be shown with status code created on post beer

Scenario: User tries to Post a Beer with a name which is already in the database
  Given a valid Beer insert request is provided
  When service save method is called and a Beer already exists with the provided name
  Then an error message response should be shown with status code conflict on post beer

@DirtyContextAfter
Scenario: User tries to Post a Beer with an invalid insert request
  Given an invalid Beer insert request is provided
  Then an error message response should be shown with status code bad request on post beer
