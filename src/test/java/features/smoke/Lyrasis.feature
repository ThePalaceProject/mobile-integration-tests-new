Feature: Smoke tests in LYRASIS Reads library

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

  @test
  Scenario: Search: Perform check that the text field appears after clicking "Search" icon
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
    Then Catalog screen is opened
      And Category names are loaded on Catalog screen
    When Open search modal
    Then The search field is displayed

  @smoke
  Scenario: Search: Perform check that the field allows you to enter characters
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
    Then Catalog screen is opened
      And Category names are loaded on Catalog screen
    When Open search modal
      And Type text "book" and save it as 'bookInfo'
    Then Placeholder contains word 'bookInfo' text in search field

  @smoke
  Scenario: Search: Perform check of finding a book in Palace Bookshelf
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Catalog screen is opened
      And Category names are loaded on Catalog screen
    When Open search modal
      And Search for 'El gato negro' and save bookName as 'bookNameInfo'
    Then EBOOK book with GET action button and 'bookNameInfo' bookName is displayed on Catalog books screen

  @smoke
  Scenario: Search: Perform check of finding a book in LYRASIS Reads
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
    Then Catalog screen is opened
      And Category names are loaded on Catalog screen
    When Open search modal
      And Search for 'Silk Road' and save bookName as 'bookNameInfo'
    Then EBOOK book with GET action button and 'bookNameInfo' bookName is displayed on Catalog books screen

  @smoke
  Scenario: Search: Perform check of the Delete button in Palace Bookshelf
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Catalog screen is opened
      And Category names are loaded on Catalog screen
    When Open search modal
      And Type text 'El gato negro' and save it as 'bookNameInfo'
      And Clear search field on Catalog books screen
    Then Search field is empty on Catalog books screen

  @smoke
  Scenario: Search: Perform check of the Delete button in LYRASIS Reads
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
    Then Catalog screen is opened
      And Category names are loaded on Catalog screen
    When Open search modal
      And Type text 'Silk Road' and save it as 'bookNameInfo'
      And Clear search field on Catalog books screen
    Then Search field is empty on Catalog books screen

#    need to fix
#  @smoke
#  Scenario: Catalog navigation: "More" button: Perform check of the button in Palace Bookshelf
#    When Close tutorial screen
#    Then Welcome screen is opened
#    When Close welcome screen
#    Then Add library screen is opened
#    When Add library "Palace Bookshelf" on Add library screen
#    Then Library "Palace Bookshelf" is opened on Libraries screen
#    When Open Catalog
#    Then Catalog screen is opened
#      And More button is present on each section of books on Catalog screen
#    When Click More button from random book section and save name of section as 'sectionInfo' on Catalog screen
#    Then Book section 'sectionInfo' is opened
#    When Tap Back button on Subcategory screen
#    Then Catalog screen is opened


    #need to fix
#  @smoke
#  Scenario: Catalog navigation: "More" button: Perform check of the button in LYRASIS Reads
#    When Close tutorial screen
#    Then Welcome screen is opened
#    When Close welcome screen
#    Then Add library screen is opened
#    When Add library "LYRASIS Reads" on Add library screen
#    Then Library "LYRASIS Reads" is opened on Libraries screen
#    When Open Catalog
#    Then Catalog screen is opened
#    And More button is present on each section of books on Catalog screen
#    When Click More button from random book section and save name of section as 'sectionInfo' on Catalog screen
#    Then Book section 'sectionInfo' is opened
#    When Tap Back button on Subcategory screen
#    Then Catalog screen is opened

  @smoke
  Scenario: Book detail view: Perform check of Get button before log in from the Settings tab
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search for "Lemon" and save bookName as 'bookNameInfo'
      And Open book with GET action button and 'bookNameInfo' bookName on catalog books screen
      And Click GET action button on Book details screen
    Then Sing in screen is opened

  @smoke
  Scenario: Book detail view: Get: Log in: Perform check of availability of required interface elements
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search for "Libertie" and save bookName as 'bookNameInfo'
      And Open book with GET action button and 'bookNameInfo' bookName on catalog books screen
      And Click GET action button on Book details screen
    Then Sing in screen is opened
      And All fields and links are displayed on Sign in screen

#    test
  @smoke @logout @returnBooks
  Scenario: Book detail view: Get: Log in: Perform check of logging in
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
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

  @smoke @logout @returnBooks
  Scenario: Catalog: Perform check of Reserve button and Allow notifications
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Enter credentials for "LYRASIS Reads" library
    Then Login is performed successfully
    When Open Catalog
      And Open search modal
      And Search for "Fahrenheit 451" and save bookName as 'bookNameInfo'
      And Open AUDIOBOOK book with RESERVE action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click RESERVE action button without closing alert on Book details screen
    Then There is an alert to allow notifications
    When Tap ALLOW button on the alert
    Then Alert to allow notification is not displayed
      And Check that book contains REMOVE action button on Book details screen

    @smoke @logout @returnBooks
    Scenario: Catalog: Alert: Perform check of "Don't Allow" button
      When Close tutorial screen
      Then Welcome screen is opened
      When Close welcome screen
      Then Add library screen is opened
      When Add library "LYRASIS Reads" on Add library screen
      Then Library "LYRASIS Reads" is opened on Libraries screen
      When Enter credentials for "LYRASIS Reads" library
      Then Login is performed successfully
      When Open Catalog
        And Open search modal
        And Search for "Fahrenheit 451" and save bookName as 'bookNameInfo'
        And Open AUDIOBOOK book with RESERVE action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
        And Click RESERVE action button without closing alert on Book details screen
      Then There is an alert to allow notifications
      When Tap Don't Allow button on the alert
      Then Alert to allow notification is not displayed
        And Check that book contains REMOVE action button on Book details screen

  @smoke @logout @returnBooks
  Scenario: Reservations: Perform check of book appearance and remove it
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
      And Search for "Fahrenheit 451" and save bookName as 'bookNameInfo'
      And Switch to 'Audiobooks' catalog tab
      And Click RESERVE action button on AUDIOBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then AUDIOBOOK book with REMOVE action button and 'bookInfo' bookInfo is present on Catalog books screen
    When Open Reservations
    Then AUDIOBOOK book with REMOVE action button and 'bookInfo' bookInfo is present on Reservations screen
    When Open AUDIOBOOK book with REMOVE action button and 'bookInfo' bookInfo on Reservations screen
      And Click REMOVE action button on Book details screen
      And Open Reservations
      And Wait for 7 seconds
    Then AUDIOBOOK book with REMOVE action button and 'bookInfo' bookInfo is not present on Reservations screen

  @smoke @logout @returnBooks @exclude_android
  Scenario: Reservations: Perform check of Remove button and cancel removing
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
      And Search for "Fahrenheit 451" and save bookName as 'bookNameInfo'
      And Switch to 'Audiobooks' catalog tab
      And Click RESERVE action button on AUDIOBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then AUDIOBOOK book with REMOVE action button and 'bookInfo' bookInfo is present on Catalog books screen
    When Open Reservations
    Then AUDIOBOOK book with REMOVE action button and 'bookInfo' bookInfo is present on Reservations screen
    When Open AUDIOBOOK book with REMOVE action button and 'bookInfo' bookInfo on Reservations screen
      And Click REMOVE action button without closing alert on Book details screen
    Then There is an alert to remove reservations with REMOVE and CANCEL buttons
    When Tap CANCEL button on the alert
    Then Check that book contains REMOVE action button on Book details screen

  @smoke
  Scenario: Settings: Perform check of the Settings tab availability of required interface elements in Sign in screen
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
      And Open Settings
      And Open Libraries on Settings screen
      And Open "LYRASIS Reads" library on Setting screen
    Then Library "LYRASIS Reads" is opened on Account screen
      And All fields and links are displayed on Account screen
    And All fields and links are displayed on Sign in screen

  @smoke
  Scenario: Settings: Log in: Perform check of  the link Content Licenses
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
      And Open Settings
      And Open Libraries on Settings screen
      And Open 'LYRASIS Reads' library on Setting screen
      And Open Content Licenses on Account screen
    Then Content Licenses screen is opened

#   from here
  @smoke @logout @returnBooks
  Scenario: Read ebooks: Pages: Perform check of reader navigating (swiping left and right)
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
      And Search for "Educational Visions" and save bookName as 'bookNameInfo'
      And Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
    Then 'bookInfo' book is present on epub reader screen
    When Swipe to the next page from 7 to 10 times on Reader epub screen
      And Save pageNumber as 'pageNumberKey' and chapterName as 'chapterNameKey' on epub reader screen
      And Go to next page on Reader epub screen
    Then Next page is opened and old page has 'pageNumberKey' pageNumber and 'chapterNameKey' chapterName on epub reader screen
    When Save pageNumber as 'pageNumberKey' and chapterName as 'chapterNameKey' on epub reader screen
      And Tap on right book corner on epub reader screen
    Then Next page is opened and old page has 'pageNumberKey' pageNumber and 'chapterNameKey' chapterName on epub reader screen
    When Save pageNumber as 'pageNumberKey' and chapterName as 'chapterNameKey' on epub reader screen
      And Click on left book corner on epub reader screen
    Then Previous page is opened and old page has 'pageNumberKey' pageNumber and 'chapterNameKey' chapterName on epub reader screen
    When Save pageNumber as 'pageNumberKey' and chapterName as 'chapterNameKey' on epub reader screen
      And Go to previous page on reader epub screen
    Then Previous page is opened and old page has 'pageNumberKey' pageNumber and 'chapterNameKey' chapterName on epub reader screen

  @smoke @logout @returnBooks
  Scenario: Read ebooks: Table of contents: Perform check of navigation
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
      And Search for "The High 5 Habit" and save bookName as 'bookNameInfo'
      And Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
    Then 'bookInfo' book is present on epub reader screen
    When Open TOC epub screen
    Then TOC screen is opened
    When Switch to bookmarks on toc epub screen
    Then Bookmark epub screen is opened

  @smoke @logout @returnBooks
  Scenario: Read ebooks: Table of contents: Contents: Perform check of Contents navigation
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
      And Search for "Quicksand" and save bookName as 'bookNameInfo'
      And Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
    Then 'bookInfo' book is present on epub reader screen
    When Open TOC epub screen
      And Open random chapter of epub and save it as 'chapterName' from toc epub screen
    Then Chapter 'chapterName' is opened on epub reader screen

  @smoke @logout @returnBooks @exclude_android
  Scenario:  IOS: Read pdfs: Pages: Perform check of reader navigating (swiping left and right)
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
      And Search for "Race Cars" and save bookName as 'bookNameInfo'
      And Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then EBOOK book with READ action button and 'bookInfo' bookInfo is present on Catalog books screen
    When Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
    Then Reader pdf screen is opened
    When Save page number as 'pageInfo' on pdf reader screen
      And Go to next page on reader pdf screen
    Then Page number increased by 1 from 'pageInfo' on pdf reader screen
    When Save page number as 'pageInfo2' on pdf reader screen
      And Go to previous page on reader pdf screen
    Then Page number decreased by 1 from 'pageInfo2' on pdf reader screen