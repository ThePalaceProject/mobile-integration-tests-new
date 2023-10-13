Feature: Manage Libraries

  @palace
  Scenario: Settings: Add library: general checks
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
    When Open Settings
      And Open Libraries on Settings screen
    Then Button Add Library is displayed on libraries screen
    When Click Add library button on libraries screen
    Then Add library screen is opened
      And Libraries are sorted in alphabetical order on add account screen

  @palace
  Scenario: Navigate by Tutorial
    Then Tutorial screen is opened
      And Each tutorial page can be opened on Tutorial screen and close tutorial screen
      And Welcome screen is opened

  @palace
  Scenario: Settings: Add library
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
    When Open Catalog
      And Get names of books on screen and save them as 'nameOfBooks'
      And Add 'LYRASIS Reads' library in Libraries screen
    Then Category names are loaded on Catalog screen
      And List of books on screen is not equal to list of books saved as 'nameOfBooks'

  @palace
  Scenario: Settings: Add Library: Check of the added libraries sorting
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
    When Add libraries through settings:
      | LYRASIS Reads            |
      | Plumas County Library    |
      | Escondido Public Library |
      | Granby Public Library    |
      | Victorville City Library |
      And Open Settings
      And Open Libraries on Settings screen
    Then Libraries are sorted in alphabetical order on libraries screen
    When Click to 'Escondido Public Library' and save library name as 'libraryInfo' on libraries screen
    Then The screen with settings for 'libraryInfo' library is opened

  @palace
  Scenario: Settings: Libraries: Remove library
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Add 'Palace Bookshelf' library in Libraries screen
      And Switch to 'LYRASIS Reads' from side menu
      And Remove 'Palace Bookshelf' library
    Then Library 'Palace Bookshelf' is not present on Libraries screen

  @palace @exclude_ios
  Scenario: Switch library bookshelf (ANDROID)
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
    When Add 'LYRASIS Reads' library in Libraries screen
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

  @palace @exclude_android
  Scenario: Switch library bookshelf (IOS)
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
    When Add 'LYRASIS Reads' library in Libraries screen
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
    Then Library "Plumas County Library" is opened on Libraries screen
    When Add 'LYRASIS Reads' library in Libraries screen
      And Enter credentials for 'LYRASIS Reads' library
    Then Login is performed successfully
    When Activate sync bookmarks on Sign in screen
      And Open Catalog
      And Open search modal
      And Search 'unavailable' book of distributor 'Bibliotheca' and bookType 'EBOOK' and save as 'bookNameInfo'
      And Switch to 'eBooks' catalog tab
    Then Subcategory screen is opened
    When Open EBOOK book with RESERVE action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then Book 'bookInfo' is opened on book details screen
      And Click RESERVE action button on Book details screen
    Then Check that book contains REMOVE action button on Book details screen
    When Open Reservations
    Then EBOOK book with REMOVE action button and 'bookInfo' bookInfo is present on Reservations screen
    When Open Catalog
      And Open Catalog
      And Switch to 'Plumas County Library' from side menu
      And Open Reservations
    Then There are not books on Reservations screen

  @logout @tier1
  Scenario: Store library card
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Enter credentials for 'LYRASIS Reads' library
    Then Login is performed successfully
    When Open library 'LYRASIS Reads'
      And Click the log out button on the account screen
    Then Logout is performed successfully

  @palace
  Scenario: Logo: Add library: Check of adding a library
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
    When Open Catalog
      And Add 'LYRASIS Reads' account by the logo
    Then Category names are loaded on Catalog screen

  @palace
  Scenario: Logo: Add Library: Check of sorting libraries
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
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
    Then Category names are loaded on Catalog screen

  @palace
  Scenario: Logo: Switch library
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
    When Open Catalog
      And Add libraries by the logo:
      | LYRASIS Reads            |
      | Plumas County Library    |
      | Escondido Public Library |
      And Tap the logo on catalog screen
      And Choose 'Palace Bookshelf' library on find your library screen
    Then Category names are loaded on Catalog screen

  @smoke
  Scenario: Lyrasis Reads link: Make sure that there is a redirection to the Lyrasis Reads library with the list of books
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
    Then Catalog screen is opened
      And Category names are loaded on Catalog screen