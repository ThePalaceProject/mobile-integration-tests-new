Feature: Manage Libraries

  @tier2
  Scenario: Settings: Add library: general checks
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Account "Palace Bookshelf" is present on Accounts screen
    When Open Settings
    And Open Libraries on settings screen
    Then Button Add Library is displayed on libraries screen
    When Click Add library button on libraries screen
    Then Add library screen is opened
    And Libraries are sorted in alphabetical order on add account screen

  @tier2
  Scenario: Navigate by Tutorial
    Then Tutorial screen is opened
    And Each tutorial page can be opened on tutorial screen for android and close tutorial screen
    And Welcome screen is opened

  @tier2
  Scenario: Settings: Add library
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Account "Palace Bookshelf" is present on Accounts screen
    When Open Catalog
    And Get names of books on screen and save them as 'nameOfBooks'
    And Add 'LYRASIS Reads' account
    Then Category rows are loaded
    And List of books on screen is not equal to list of books saved as 'nameOfBooks'

  @tier2
  Scenario: Settings: Add Library: Check of the added libraries sorting
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Account "Palace Bookshelf" is present on Accounts screen
    When Add libraries through settings:
      | LYRASIS Reads            |
      | Plumas County Library    |
      | Escondido Public Library |
      | Granby Public Library    |
      | Victorville City Library |
    And Open Settings
    And Open Libraries on settings screen
    Then Libraries are sorted in alphabetical order on libraries screen
    When Click to 'Escondido Public Library' and save library name as 'libraryInfo' on libraries screen
    Then The screen with settings for 'libraryInfo' library is opened

  @tier2
  Scenario: Settings: Libraries: Remove library
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
    Then Account "LYRASIS Reads" is present on Accounts screen
    When Add 'Palace Bookshelf' account
    And Switch to 'LYRASIS Reads' from side menu
    And Remove 'Palace Bookshelf' account
    Then Account 'Palace Bookshelf' is not present on Accounts screen

  @tier2 @exclude_ios
  Scenario: Switch library bookshelf (ANDROID)
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Account "Palace Bookshelf" is present on Accounts screen
    When Add 'LYRASIS Reads' account
    And Open Catalog
    And Switch to 'Palace Bookshelf' from side menu
    And Open categories by chain and chain starts from CategoryScreen:
      | Summer Reading |
    And Click GET action button on the first EBOOK book on catalog books screen and save book as 'bookInfo'
    And Open Books
    Then EBOOK book with READ action button and 'bookInfo' bookInfo is present on books screen
    When Open Catalog
    And Return to previous screen for epub and pdf
    And Switch to 'LYRASIS Reads' from side menu
    And Open Books
    Then There are not books on books screen

  @tier2 @exclude_android
  Scenario: Switch library bookshelf (IOS)
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Account "Palace Bookshelf" is present on Accounts screen
    When Add 'LYRASIS Reads' account
    And Open Catalog
    And Switch to 'Palace Bookshelf' from side menu
    And Open categories by chain and chain starts from CategoryScreen:
      | Fiction            |
      | Historical Fiction |
    And Click GET action button on the first EBOOK book on catalog books screen and save book as 'bookInfo'
    And Open Books
    Then EBOOK book with READ action button and 'bookInfo' bookInfo is present on books screen
    When Open Catalog
    And Return to previous screen for epub and pdf
    And Switch to 'LYRASIS Reads' from side menu
    And Open Books
    Then There are not books on books screen

  @logout @returnBooks @tier1
  Scenario: Switch Library Reservations
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Plumas County Library" on Add library screen
    Then Account "Plumas County Library" is present on Accounts screen
    When Add 'LYRASIS Reads' account
    And Enter credentials for 'LYRASIS Reads' account
    Then Login is performed successfully
    When Open Catalog
    And Open search modal
    And Search 'unavailable' book of distributor 'Bibliotheca' and bookType 'EBOOK' from "LYRASIS Reads" and save as 'bookNameInfo'
    And Switch to 'eBooks' catalog tab
    Then Subcategory screen is present
    When Open EBOOK book with RESERVE action button and 'bookNameInfo' bookName on catalog books screen and save book as 'bookInfo'
    Then Book 'bookInfo' is opened on book details screen
    And Click RESERVE action button on book details screen
    Then Check that book contains REMOVE action button on book details screen
    When Open Holds
    Then EBOOK book with REMOVE action button and 'bookInfo' bookInfo is present on holds screen
    When Open Catalog
    And Open Catalog
    And Switch to 'Plumas County Library' from side menu
    And Open Holds
    And There are not books on holds screen

  @logout @tier1
  Scenario: Store library card
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
    Then Account "LYRASIS Reads" is present on Accounts screen
    When Enter credentials for 'LYRASIS Reads' account
    Then Login is performed successfully
    When Open account 'LYRASIS Reads'
    And Click the log out button on the account screen
    Then Logout is performed successfully

  @tier2
  Scenario: Logo: Add library: Check of adding a library
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Account "Palace Bookshelf" is present on Accounts screen
    When Open Catalog
    And Add 'LYRASIS Reads' account by the logo
    Then Category rows are loaded

  @tier2
  Scenario: Logo: Add Library: Check of sorting libraries
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Account "Palace Bookshelf" is present on Accounts screen
    When Open Catalog
    And Add libraries by the logo:
      | LYRASIS Reads            |
      | Plumas County Library    |
      | Escondido Public Library |
      | Granby Public Library    |
      | Victorville City Library |
    And Save 5 amount as 'amountKey'
    When Tap the logo on catalog screen
    Then The sorting of 'amountKey' libraries is alphabetical on find your library screen
    When Tap cancel button on find your library screen
    Then Category rows are loaded

  @tier2
  Scenario: Logo: Switch library
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Account "Palace Bookshelf" is present on Accounts screen
    When Open Catalog
    And Add libraries by the logo:
      | LYRASIS Reads            |
      | Plumas County Library    |
      | Escondido Public Library |
    And Tap the logo on catalog screen
    And Choose 'Palace Bookshelf' library on find your library screen
    Then Category rows are loaded