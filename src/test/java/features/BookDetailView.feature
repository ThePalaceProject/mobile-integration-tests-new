Feature: Book detail view screen

  Background:
    Given Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened

  @tier2
  Scenario Outline: Check of a book title and author in LYRASIS Reads
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search 'available' book of distributor '<distributor>' and bookType '<bookType>' and save as 'bookNameInfo'
      And Switch to '<tabName>' catalog tab
      And Open <bookType> book with GET action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then Book 'bookInfo' is opened on book details screen
      And Book 'bookInfo' has correct title and author name on Book details screen
      And The book cover is displayed on Book details screen

    Scenarios:
      | distributor        | bookType  | tabName    |
      | Bibliotheca        | EBOOK     | eBooks     |
      | Bibliotheca        | AUDIOBOOK | Audiobooks |
      | Axis 360           | EBOOK     | eBooks     |
      | Axis 360           | AUDIOBOOK | Audiobooks |
      | Palace Marketplace | EBOOK     | eBooks     |
      | Palace Marketplace | AUDIOBOOK | Audiobooks |
      | Biblioboard        | EBOOK     | eBooks     |
      | Biblioboard        | AUDIOBOOK | Audiobooks |

  @tier2
  Scenario Outline: Check of a book format in LYRASIS Reads
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search 'available' book of distributor '<distributor>' and bookType '<bookType>' and save as 'bookNameInfo'
      And Switch to '<tabName>' catalog tab
      And Open <bookType> book with GET action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then Book 'bookInfo' is opened on book details screen
      And Book format in Information section is displayed on Book details screen
      And Book format in Information section is '<format>' on Book details screen
    Scenarios:
      | distributor        | bookType  | tabName    | format    |
      | Bibliotheca        | EBOOK     | eBooks     | eBook     |
      | Bibliotheca        | AUDIOBOOK | Audiobooks | Audiobook |
      | Axis 360           | EBOOK     | eBooks     | eBook     |
      | Axis 360           | AUDIOBOOK | Audiobooks | Audiobook |
      | Palace Marketplace | EBOOK     | eBooks     | eBook     |
      | Palace Marketplace | AUDIOBOOK | Audiobooks | Audiobook |
      | Biblioboard        | EBOOK     | eBooks     | PDF       |
      | Biblioboard        | AUDIOBOOK | Audiobooks | Audiobook |

  @tier2
  Scenario Outline: Check of a "More..." button in Description section in LYRASIS Reads
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search 'available' book of distributor '<distributor>' and bookType '<bookType>' and save as 'bookNameInfo'
      And Switch to '<tabName>' catalog tab
      And Open <bookType> book with GET action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then Book 'bookInfo' is opened on book details screen
      And Description is not empty on Book details screen
      And Button More in Description is available on Book details screen

    Scenarios:
      | distributor        | bookType  | tabName    |
      | Bibliotheca        | EBOOK     | eBooks     |
      | Bibliotheca        | AUDIOBOOK | Audiobooks |
      | Axis 360           | EBOOK     | eBooks     |
      | Axis 360           | AUDIOBOOK | Audiobooks |
      | Palace Marketplace | EBOOK     | eBooks     |
      | Palace Marketplace | AUDIOBOOK | Audiobooks |
      | Biblioboard        | EBOOK     | eBooks     |
      | Biblioboard        | AUDIOBOOK | Audiobooks |

  @tier2
  Scenario Outline: Check fields in Information section in LYRASIS Reads
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search 'available' book of distributor '<distributor>' and bookType '<bookType>' and save as 'bookNameInfo'
      And Switch to '<tabName>' catalog tab
      And Open <bookType> book with GET action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then Book 'bookInfo' is opened on book details screen
      And Publisher and Categories in Information section are displayed on book details screen
      And Publisher and Categories in Information section are correct on book details screen
      And Distributor is equal to '<distributor>' on book details screen

    Scenarios:
      | distributor        | bookType  | tabName    |
      | Bibliotheca        | EBOOK     | eBooks     |
      | Bibliotheca        | AUDIOBOOK | Audiobooks |
      | Axis 360           | EBOOK     | eBooks     |
      | Axis 360           | AUDIOBOOK | Audiobooks |
      | Palace Marketplace | EBOOK     | eBooks     |
      | Palace Marketplace | AUDIOBOOK | Audiobooks |
      | Biblioboard        | EBOOK     | eBooks     |
      | Biblioboard        | AUDIOBOOK | Audiobooks |

  @tier2
  Scenario Outline: Check related books section in LYRASIS
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search 'available' book of distributor '<distributor>' and bookType '<bookType>' and save as 'bookNameInfo'
      And Switch to '<tabName>' catalog tab
      And Open <bookType> book with GET action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then Book 'bookInfo' is opened on book details screen
      And Related books section is displayed on book details screen
      And There is a list of related books on book details screen
      And More button in related books section is available on book details screen

    Scenarios:
      | distributor        | bookType  | tabName    |
      | Bibliotheca        | EBOOK     | eBooks     |
      | Bibliotheca        | AUDIOBOOK | Audiobooks |
      | Axis 360           | EBOOK     | eBooks     |
      | Axis 360           | AUDIOBOOK | Audiobooks |
      | Palace Marketplace | EBOOK     | eBooks     |
      | Palace Marketplace | AUDIOBOOK | Audiobooks |
      | Biblioboard        | EBOOK     | eBooks     |
      | Biblioboard        | AUDIOBOOK | Audiobooks |

#  sorting doesn't work correctly
#  @tier1
#  Scenario Outline: Check of sorting books in related books in LYRASIS
#    When I add "LYRASIS Reads" account from welcomeScreen
#      And I open Catalog
#      And I open search modal
#    And I search 'available' book of distributor '<distributor>' and bookType '<bookType>' and save as 'bookNameInfo'
#    And I switch to '<tabName>' catalog tab
#      And Open <bookType> book with GET action button and 'bookNameInfo' bookName on catalog books screen and save book as 'bookInfo'
#    Then Book 'bookInfo' is opened on book details screen
#    When Tap More... button in related books on book details view
#      And I sort books by AUTHOR
#    Then Books are sorted by Author ascending
#    When I save list of books as 'listOfBooks'
#      And I sort books by RECENTLY_ADDED
#    Then List of books on subcategory screen is not equal to list of books saved as 'listOfBooks'
#    When I sort books by TITLE
#    Then Books are sorted by Title ascending
#
#    Scenarios:
#      | distributor        | bookType  | tabName    |
#      | Bibliotheca        | EBOOK     | eBooks     |
#      | Bibliotheca        | AUDIOBOOK | Audiobooks |
#      | Axis 360           | EBOOK     | eBooks     |
#      | Axis 360           | AUDIOBOOK | Audiobooks |
#      | Palace Marketplace | EBOOK     | eBooks     |
#      | Palace Marketplace | AUDIOBOOK | Audiobooks |
#      | Biblioboard        | EBOOK     | eBooks     |
#      | Biblioboard        | AUDIOBOOK | Audiobooks |

  @tier2
  Scenario: Check of a book title and author in Palace Bookshelf
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search for "Persuasion" and save bookName as 'bookNameInfo'
      And Open EBOOK book with GET action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then Book 'bookInfo' is opened on book details screen
      And Book 'bookInfo' has correct title and author name on Book details screen
      And The book cover is displayed on Book details screen

  @tier2
  Scenario: Check of a "More..." button in Description section in Palace Bookshelf
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search for "Persuasion" and save bookName as 'bookNameInfo'
      And Open EBOOK book with GET action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then Book 'bookInfo' is opened on book details screen
      And Description is not empty on Book details screen
      And Button More in Description is available on Book details screen

  @tier2
  Scenario: Check fields in Information section in Palace Bookshelf
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search for "Persuasion" and save bookName as 'bookNameInfo'
      And Open EBOOK book with GET action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then Book 'bookInfo' is opened on book details screen
      And Publisher and Categories in Information section are correct on book details screen

  @tier2
  Scenario: Check related books section in Palace Bookshelf
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search for "Persuasion" and save bookName as 'bookNameInfo'
      And Open EBOOK book with GET action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then Book 'bookInfo' is opened on book details screen
      And Related books section is displayed on book details screen
      And There is a list of related books on book details screen
      And More button in related books section is available on book details screen

#    sorting doesn't work correctly
#  @tier1
#  Scenario: Check of sorting books in related books in Palace
#    When I add "Palace Bookshelf" account from welcomeScreen
#      And I open Catalog
#      And I open search modal
#      And I search for "Persuasion" and save bookName as 'bookNameInfo'
#      And Open EBOOK book with GET action button and 'bookNameInfo' bookName on catalog books screen and save book as 'bookInfo'
#    Then Book 'bookInfo' is opened on book details screen
#    When Tap More... button in related books on book details view
#    And I sort books by AUTHOR
#    Then Books are sorted by Author ascending
#    When I save list of books as 'listOfBooks'
#    And I sort books by RECENTLY_ADDED
#    Then List of books on subcategory screen is not equal to list of books saved as 'listOfBooks'
#    When I sort books by TITLE
#    Then Books are sorted by Title ascending

  @tier2
  Scenario: Check of a book title and author in Overdrive
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
    When Turn on test mode
      And Enable hidden libraries
      And Open Catalog
      And Add "A1QA Test Library" account by the logo
    Then Library "A1QA Test Library" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search for "The Woman in White" and save bookName as 'bookNameInfo'
      And Open EBOOK book with GET action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then Book 'bookInfo' is opened on book details screen
      And Book 'bookInfo' has correct title and author name on Book details screen
      And The book cover is displayed on Book details screen

  @tier2
  Scenario: Check of a "More..." button in Description section in Overdrive
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
    When Turn on test mode
      And Enable hidden libraries
      And Open Catalog
      And Add "A1QA Test Library" account by the logo
    Then Library "A1QA Test Library" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search for "The Oregon Trail" and save bookName as 'bookNameInfo'
      And Open EBOOK book with GET action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then Book 'bookInfo' is opened on book details screen
      And Description is not empty on Book details screen
      And Button More in Description is available on Book details screen

  @tier2
  Scenario: Check fields in Information section in Overdrive
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
    When Turn on test mode
      And Enable hidden libraries
      And Open Catalog
      And Add "A1QA Test Library" account by the logo
    Then Library "A1QA Test Library" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search for "The Count of Monte Cristo" and save bookName as 'bookNameInfo'
      And Open EBOOK book with GET action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then Book 'bookInfo' is opened on book details screen
      And Publisher and Categories in Information section are correct on book details screen
      And Distributor is equal to 'Overdrive' on book details screen

  @tier2
  Scenario: Check related books section in Overdrive
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
    When Turn on test mode
      And Enable hidden libraries
      And Open Catalog
      And Add "A1QA Test Library" account by the logo
    Then Library "A1QA Test Library" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search for "The Woman in White" and save bookName as 'bookNameInfo'
      And Open EBOOK book with GET action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then Book 'bookInfo' is opened on book details screen
      And Related books section is displayed on book details screen
      And There is a list of related books on book details screen
      And More button in related books section is available on book details screen

  @tier2
  Scenario: Get button: Check of availability of required interface elements
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search for "Libertie" and save bookName as 'bookNameInfo'
      And Open book with GET action button and 'bookNameInfo' bookName on catalog books screen
      And Click GET action button on Book details screen
    Then Sing in screen is opened
      And All fields and links are displayed on Sign in screen

  @tier2 @exclude_android
  Scenario: Get button: check of Library Card field
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search for "The Hobbit" and save bookName as 'bookNameInfo'
      And Open AUDIOBOOK book with GET action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click GET action button on Book details screen
    Then Sing in screen is opened
      And There is a placeholder Library Card in the Library Card field on Sign in screen

  @tier2 @exclude_android
  Scenario: Get button: check of Password field
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search for "The Hidden" and save bookName as 'bookNameInfo'
      And Open book with GET action button and 'bookNameInfo' bookName on catalog books screen
      And Click GET action button on Book details screen
    Then Sing in screen is opened
      And There is a placeholder "Password" in the Password field on Sign in screen

  @tier2
  Scenario: Get: Sign in: Check of loging in with leaving the Library Card field empty
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search for "Brain" and save bookName as 'bookNameInfo'
      And Open book with GET action button and 'bookNameInfo' bookName on catalog books screen
      And Click GET action button on Book details screen
    Then Sing in screen is opened
    When Enter a valid Password for "LYRASIS Reads" library on Sign in screen
    Then Sign in button is disabled on Sign in screen

  @tier2
  Scenario: Get: Sign in: Check of loging in with leaving the Password field empty
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search for "Above World" and save bookName as 'bookNameInfo'
      And Open book with GET action button and 'bookNameInfo' bookName on catalog books screen
      And Click GET action button on Book details screen
    Then Sing in screen is opened
    When Enter a valid Library card "01230000000098" on Sign in screen
    Then Sign in button is disabled on Sign in screen

  @tier2
  Scenario: Get: Sign in: Library card: Check that the field allows you to edit the data
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search for "Big Fish" and save bookName as 'bookNameInfo'
      And Open book with GET action button and 'bookNameInfo' bookName on catalog books screen
      And Click GET action button on Book details screen
    Then Sing in screen is opened
    When Enter a Library card with 14 numbers and save it as 'libraryCard' on Sign in screen
      And Edit data by adding "22" in Library card field and save it as 'newLibraryCard' on sign in screen
    Then There is a placeholder 'newLibraryCard' in the Library Card field on Sign in screen

  @tier2
  Scenario Outline: Get: Sign in: Library card: Check of less than minimum allowed or more than maximum characters
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search for "As the Crows Fly" and save bookName as 'bookNameInfo'
      And Open book with GET action button and 'bookNameInfo' bookName on catalog books screen
      And Click GET action button on Book details screen
    Then Sing in screen is opened
    When Enter a Library card with <numbers> numbers and save it as 'libraryCard' on Sign in screen
      And Enter a valid Password for "LYRASIS Reads" library on Sign in screen
      And Tap the Sign in button on Sign in screen
    Then There is an alert "Invalid credentials" on Sign in screen

    Scenarios:
      | numbers |
      | 13      |
      | 15      |

  @tier2
  Scenario: Get: Log in: Library card: Check that the field doesn't allow characters except numbers
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search for "The Last Goodnight" and save bookName as 'bookNameInfo'
      And Open book with GET action button and 'bookNameInfo' bookName on catalog books screen
      And Click GET action button on Book details screen
    Then Sing in screen is opened
    When Enter a Library card with 14 latin letters and save it as 'libraryCard' on Sign in screen
      And Enter a valid Password for "LYRASIS Reads" library on Sign in screen
      And Tap the Sign in button on Sign in screen
    Then There is an alert "Invalid credentials" on Sign in screen

  @smoke
  Scenario: Book detail view: Perform check of Get button before log in from the Settings tab
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
    And Open search modal
    And Search for "Eat That Frog!" and save bookName as 'bookNameInfo'
    And Open book with GET action button and 'bookNameInfo' bookName on catalog books screen
    And Click GET action button on Book details screen
    Then Sing in screen is opened

  @smoke
  Scenario: Book detail view: Get: Log in: Perform check of availability of required interface elements
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search for "Libertie" and save bookName as 'bookNameInfo'
      And Open book with GET action button and 'bookNameInfo' bookName on catalog books screen
      And Click GET action button on Book details screen
    Then Sing in screen is opened
      And All fields and links are displayed on Sign in screen

  @smoke @logout @returnBooks
  Scenario: Book detail view: Get: Log in: Perform check of logging in
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search for "Renaissance Futurities" and save bookName as 'bookNameInfo'
      And Open book with GET action button and 'bookNameInfo' bookName on catalog books screen
      And Click GET action button on Book details screen
    Then Sing in screen is opened
    When Save library "LYRASIS Reads" for log out
      And Enter valid credentials fot "LYRASIS Reads" library on Sign in screen
    Then Check that book contains READ action button on Book details screen