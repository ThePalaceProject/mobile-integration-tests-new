Feature: Distributors

  @logout @returnBooks @tier1
  Scenario Outline: Reserving from Book Detail View in LYRASIS Reads
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Enter credentials for 'LYRASIS Reads' library
    Then Login is performed successfully
    When Activate sync bookmarks on Sign in screen
      And Open Catalog
      And Open search modal
      And Search 'unavailable' book of distributor '<distributor>' and bookType '<bookType>' and save as 'bookNameInfo'
      And Switch to '<tabName>' catalog tab
      And Open <bookType> book with RESERVE action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click RESERVE action button on Book details screen
    Then Check that book contains REMOVE action button on Book details screen

    Scenarios:
      | distributor        | bookType  | tabName    |
      | Bibliotheca        | EBOOK     | eBooks     |
      | Bibliotheca        | AUDIOBOOK | Audiobooks |
      | Axis 360           | EBOOK     | eBooks     |
      | Axis 360           | AUDIOBOOK | Audiobooks |
      | Palace Marketplace | EBOOK     | eBooks     |
      | Palace Marketplace | AUDIOBOOK | Audiobooks |
      | Biblioboard        | EBOOK     | eBooks     |

  @logout @returnBooks @tier1
  Scenario Outline: Getting and returning books from Book Detail View in LYRASIS Reads
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Enter credentials for 'LYRASIS Reads' library
    Then Login is performed successfully
    When Open Catalog
      And Open search modal
      And Search 'available' book of distributor '<distributor>' and bookType '<bookType>' and save as 'bookNameInfo'
      And Switch to '<tabName>' catalog tab
      And Open <bookType> book with GET action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click GET action button on Book details screen
    Then Check that book contains <buttonBookDetailsView> action button on Book details screen
    When Click RETURN action button on Book details screen
    Then Check that book contains GET action button on Book details screen
    When Click GET action button on Book details screen
    Then Check that book contains <buttonBookDetailsView> action button on Book details screen
    When Click <buttonBookDetailsView> action button on Book details screen
      And Wait for 3 seconds
    Then Book 'bookInfo' with <bookType> type is present on epub or pdf or audiobook screen

    Scenarios:
      | distributor        | bookType  | tabName    | buttonBookDetailsView |
      | Bibliotheca        | EBOOK     | eBooks     | READ                  |
      | Bibliotheca        | AUDIOBOOK | Audiobooks | LISTEN                |
      | Axis 360           | EBOOK     | eBooks     | READ                  |
      | Axis 360           | AUDIOBOOK | Audiobooks | LISTEN                |
      | Palace Marketplace | EBOOK     | eBooks     | READ                  |
      | Palace Marketplace | AUDIOBOOK | Audiobooks | LISTEN                |
      | Biblioboard        | EBOOK     | eBooks     | READ                  |
      | Biblioboard        | AUDIOBOOK | Audiobooks | LISTEN                |

  @logout @returnBooks @tier2
  Scenario: Getting and returning a book from Book Detail View for Overdrive
    Given Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
    When Turn on test mode
      And Enable hidden libraries
      And Open Catalog
      And Add "A1QA Test Library" account by the logo
    Then Library "A1QA Test Library" is opened on Libraries screen
    When Enter credentials for "A1QA Test Library" library
    Then Login is performed successfully
    When Activate sync bookmarks on Sign in screen
      And Open Catalog
      And Open search modal
      And Search for "Silk Road" and save bookName as 'bookNameInfo'
      And Open EBOOK book with GET action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click GET action button on Book details screen
    Then Check that book contains READ action button on Book details screen
    When Click RETURN action button on Book details screen
    Then Check that book contains GET action button on Book details screen
    When Click GET action button on Book details screen
    Then Check that book contains READ action button on Book details screen
    When Click READ action button on Book details screen
      And Wait for 3 seconds
    Then Book 'bookInfo' with EBOOK type is present on epub or pdf or audiobook screen

  @logout @returnBooks @tier2
  Scenario: Getting and returning an audiobook from Book Detail View for Overdrive
    Given Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
    When Turn on test mode
      And Enable hidden libraries
      And Open Catalog
      And Add "A1QA Test Library" account by the logo
    Then Library "A1QA Test Library" is opened on Libraries screen
    When Enter credentials for "A1QA Test Library" library
    Then Login is performed successfully
    When Activate sync bookmarks on Sign in screen
      And Open Catalog
      And Open search modal
      And Search for "We Are Water" and save bookName as 'bookNameInfo'
      And Open AUDIOBOOK book with GET action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click GET action button on Book details screen
    Then Check that book contains LISTEN action button on Book details screen
    When Click RETURN action button on Book details screen
    Then Check that book contains GET action button on Book details screen
    When Click GET action button on Book details screen
    Then Check that book contains LISTEN action button on Book details screen
    When Click LISTEN action button on Book details screen
      And Wait for 3 seconds
    Then Book 'bookInfo' with AUDIOBOOK type is present on epub or pdf or audiobook screen

  @tier2
  Scenario: Getting and returning a book from Book Detail View for Palace Bookshelf
    Given Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search for "Jane Eyre" and save bookName as 'bookNameInfo'
      And Open EBOOK book with GET action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click GET action button on Book details screen
    Then Check that book contains READ action button on Book details screen
    When Click RETURN action button on Book details screen
    Then Check that book contains GET action button on Book details screen
    When Click GET action button on Book details screen
    Then Check that book contains READ action button on Book details screen
    When Click READ action button on Book details screen
      And Wait for 3 seconds
    Then Book 'bookInfo' with AUDIOBOOK type is present on epub or pdf or audiobook screen

  @logout @returnBooks @tier1
  Scenario Outline: Check of canceling the downloading from book details view for LYRASIS Reads
    Given Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Enter credentials for 'LYRASIS Reads' library
    Then Login is performed successfully
    When Activate sync bookmarks on Sign in screen
      And Open Catalog
      And Open search modal
      And Search 'available' book of distributor '<distributor>' and bookType '<bookType>' and save as 'bookNameInfo'
      And Switch to '<tabName>' catalog tab
      And Open <bookType> book with GET action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click GET action button and cancel downloading by click CANCEL button on book detail screen
    Then Check that book contains DOWNLOAD action button on Book details screen
      And Check that book contains RETURN action button on Book details screen

    Scenarios:
      | distributor        | bookType  | tabName    |
      | Bibliotheca        | EBOOK     | eBooks     |
      | Bibliotheca        | AUDIOBOOK | Audiobooks |
      | Palace Marketplace | EBOOK     | eBooks     |
      | Palace Marketplace | AUDIOBOOK | Audiobooks |
      | Axis 360           | EBOOK     | eBooks     |
      | Axis 360           | AUDIOBOOK | Audiobooks |
      | Biblioboard        | EBOOK     | eBooks     |
      | Biblioboard        | AUDIOBOOK | Audiobooks |

  @logout @returnBooks @tier2
  Scenario: Check of canceling the downloading from book details view for Overdrive
    Given Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
    When Turn on test mode
      And Enable hidden libraries
    When Open Catalog
      And Add "A1QA Test Library" account by the logo
    Then Library "A1QA Test Library" is opened on Libraries screen
    When Enter credentials for "A1QA Test Library" library
    Then Login is performed successfully
    When Activate sync bookmarks on Sign in screen
      And Open Catalog
      And Open search modal
      And Search for "The Fallen" and save bookName as 'bookNameInfo'
      And Open EBOOK book with GET action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click GET action button and cancel downloading by click CANCEL button on book detail screen
    Then Check that book contains DOWNLOAD action button on Book details screen
      And Check that book contains RETURN action button on Book details screen