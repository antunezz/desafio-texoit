Feature: Photo Album
  As Eduardo (persona)
  I want to access a photo album
  To see the photos

  Scenario Outline:
    Given I am at the jsonplaceholder home page
    When I access the "Guide" menu
    And I select the album with id=1
    Then the album information is displayed as a JSON array
    And the photo with id=<id> is displayed
    And the title is "<title>"
    And the url is "<url>"
    And the thumbnailUrl is "<thumbnailUrl>"

    Examples:
      | id | title                                    | url                                    | thumbnailUrl                           |
      | 6  | accusamus ea aliquid et amet sequi nemo  | https://via.placeholder.com/600/56a8c2 | https://via.placeholder.com/150/56a8c2 |
      | 9  | qui eius qui autem sed                   | https://via.placeholder.com/600/51aa97 | https://via.placeholder.com/150/51aa97 |
      | 14 | est necessitatibus architecto ut laborum | https://via.placeholder.com/600/61a65  | https://via.placeholder.com/150/61a65  |
