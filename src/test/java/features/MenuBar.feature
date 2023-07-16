Feature: Menu Bar module

  @palace
  Scenario Outline: Check of menu bar in Palace Bookshelf
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
      And There is a menu bar at the bottom of the screen
      And There are tabs '<tab1>', '<tab2>' and '<tab3>' at the bottom of the screen

    Scenarios:
      | tab1    | tab2     | tab3     |
      | Catalog | My Books | Settings |

  @palace
  Scenario: Check of the tabs in Palace Bookshelf
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
      And Open Catalog
    Then Catalog screen is opened
    When Open Books
    Then Books screen is opened
    When Open Settings
    Then Settings screen is opened

  @palace
  Scenario Outline: Check of menu bar in LYRASIS Reads
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
      And There is a menu bar at the bottom of the screen
    And There are tabs '<tab1>', '<tab2>', '<tab3>' and '<tab4>' at the bottom of the screen

    Scenarios:
      | tab1    | tab2     | tab3         | tab4     |
      | Catalog | My Books | Reservations | Settings |

  @palace
  Scenario: Check of the tabs in LYRASIS Reads
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
      And Open Catalog
    Then Catalog screen is opened
    When Open Books
    Then Books screen is opened
    When Open Reservations
    Then Reservations screen is opened
    When Open Settings
    Then Settings screen is opened