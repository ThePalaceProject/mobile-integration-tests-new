Feature: Smoke tests in LYRASIS Reads library



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
    When Activate sync bookmarks on Sign in screen
      And Open Catalog
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
      When Activate sync bookmarks on Sign in screen
        And Open Catalog
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
    When Activate sync bookmarks on Sign in screen
      And Open Catalog
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
    When Activate sync bookmarks on Sign in screen
      And Open Catalog
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
    When Activate sync bookmarks on Sign in screen
      And Open Catalog
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
    When Activate sync bookmarks on Sign in screen
      And Open Catalog
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
    When Activate sync bookmarks on Sign in screen
      And Open Catalog
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
    When Activate sync bookmarks on Sign in screen
      And Open Catalog
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

  @smoke @logout @returnBooks
  Scenario: Read pdfs: Table of contents: Perform check of navigation
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
      And Search for "Race Cars" and save bookName as 'bookNameInfo'
      And Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then EBOOK book with READ action button and 'bookInfo' bookInfo is present on Catalog books screen
    When Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
    Then Reader pdf screen is opened
    When Open TOC on pdf reader screen
    Then There are content list with thumbnails and chapter content on pdf toc screen
    When Open text chapter content on pdf toc screen
    Then Text chapter content is opened on pdf toc screen
    When Open content with thumbnails on pdf toc screen
    Then Thumbnails of the book pages are displayed

  @smoke @logout @returnBooks @exclude_android
  Scenario: iOS: Read pdfs: Search: Perform check that the field allows you to enter characters and delete them
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
      And Search for "Race Cars" and save bookName as 'bookNameInfo'
      And Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then EBOOK book with READ action button and 'bookInfo' bookInfo is present on Catalog books screen
    When Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
    Then Reader pdf screen is opened
    When Open search pdf screen
      And Enter 'try' text and save it as 'searchedText' on search pdf screen
    Then The field allows to enter characters and contains 'searchedText' on search pdf screen
    When Delete text in search line on search pdf screen
    Then Search field is empty on search pdf screen

  @smoke @logout @returnBooks @exclude_android
  Scenario: iOS: Read pdfs: Search: Perform check of finding a word in the book
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
      And Search for "Race Cars" and save bookName as 'bookNameInfo'
      And Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then EBOOK book with READ action button and 'bookInfo' bookInfo is present on Catalog books screen
    When Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    And Click READ action button on Book details screen
    Then Reader pdf screen is opened
    When Open search pdf screen
      And Enter 'try' text on search pdf screen
      And Open random found text and save page number as 'pageNumber' on search pdf screen
    Then Page number is equal to 'pageNumber' on pdf reader screen

  @smoke @logout @returnBooks
  Scenario: Read pdfs: Perform check of back button
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
      And Search for "Race Cars" and save bookName as 'bookNameInfo'
      And Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then EBOOK book with READ action button and 'bookInfo' bookInfo is present on Catalog books screen
    When Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
    Then Reader pdf screen is opened
    When Close pdf reader by back button
    Then Book "bookInfo" is opened on book details screen

  @smoke @logout @returnBooks
  Scenario: Read pdfs: Table of contents: Contents with thumbnails: Perform check of navigation
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
      And Search for "Race Cars" and save bookName as 'bookNameInfo'
      And Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then EBOOK book with READ action button and 'bookInfo' bookInfo is present on Catalog books screen
    When Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    And Click READ action button on Book details screen
    Then Reader pdf screen is opened
    When Open TOC on pdf reader screen
      And Open content with thumbnails on pdf toc screen
    Then Thumbnails of the book pages are displayed
    When Open random thumbnail and save the number as 'pageInfo' on pdf toc screen
      And Return to pdf reader screen from pdf toc screen
    Then Page number is equal to 'pageInfo' on pdf reader screen

  @smoke @logout @returnBooks
  Scenario: Read pdfs: Table of contents: Chapter content: Perform check of navigation
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
      And Search for "Comprehension and Critical Thinking Grade 6" and save bookName as 'bookNameInfo'
      And Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then EBOOK book with READ action button and 'bookInfo' bookInfo is present on Catalog books screen
    When Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
    Then Reader pdf screen is opened
    When Open TOC on pdf reader screen
      And Open text chapter content on pdf toc screen
    Then Text chapter content is opened on pdf toc screen
    When Open random chapter and save the number as 'pageInfo' on pdf toc screen
      And Return to pdf reader screen from pdf toc screen
    Then Page number is equal to 'pageInfo' on pdf reader screen

  @smoke @logout @returnBooks @exclude_ios
  Scenario: Android: Read pdfs: Table of contents: Perform check of navigation of TOC button
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
      And Search for "Comprehension and Critical Thinking Grade 6" and save bookName as 'bookNameInfo'
      And Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then EBOOK book with READ action button and 'bookInfo' bookInfo is present on Catalog books screen
    When Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
    Then Reader pdf screen is opened
    When Open TOC on pdf reader screen
    Then There are content list with thumbnails and chapter content on pdf toc screen
    When Return to pdf reader screen from pdf toc screen
    Then PDF toc screen is closed
    When Open TOC on pdf reader screen
      And Open TOC on pdf reader screen
    Then PDF toc screen is closed
    When Open TOC on pdf reader screen
      And Close pdf toc screen by back button
    Then PDF toc screen is closed

  @smoke @logout @returnBooks @exclude_ios
  Scenario: Android: Read pdfs: Perform check of Settings
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
      And Search for "Comprehension and Critical Thinking Grade 6" and save bookName as 'bookNameInfo'
      And Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then EBOOK book with READ action button and 'bookInfo' bookInfo is present on Catalog books screen
    When Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
    Then Reader pdf screen is opened
    When Open pdf settings screen on pdf reader screen
    Then PDF settings screen is opened

  @smoke @logout @returnBooks @exclude_ios
  Scenario: Android: Read pdfs: Perform check of scrolling by default (down and up)
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
      And Search for "Comprehension and Critical Thinking Grade 6" and save bookName as 'bookNameInfo'
      And Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then EBOOK book with READ action button and 'bookInfo' bookInfo is present on Catalog books screen
    When Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
    Then Reader pdf screen is opened
    When Save page number as 'pageInfo' on pdf reader screen
      And Open pdf settings screen on pdf reader screen
    Then Vertical scrolling is chosen by default on settings screen
    When Open pdf settings screen on pdf reader screen
      And Scroll page down on pdf reader screen
    Then Page number is not equal to 'pageInfo' on pdf reader screen
    When Save page number as 'pageInfo2' on pdf reader screen
      And Scroll page up on pdf reader screen
    Then Page number is not equal to 'pageInfo2' on pdf reader screen

  @smoke @logout @returnBooks
  Scenario: Audiobooks: Perform check of Listen and Back button
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
      And Search for "Down The Hatch" and save bookName as 'bookNameInfo'
      And Switch to 'Audiobooks' catalog tab
      And Open AUDIOBOOK book with GET action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click GET action button on Book details screen
    Then Check that book contains LISTEN action button on Book details screen
    When Click LISTEN action button on Book details screen
    Then Audio player screen of book 'bookInfo' is opened
    When Return to previous screen from audio player screen
    Then Book 'bookInfo' is opened on book details screen

  @smoke @logout @returnBooks
  Scenario: Audiobooks: Perform check of Play button and Pause buttons
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
      And Search for "Down The Hatch" and save bookName as 'bookNameInfo'
      And Switch to 'Audiobooks' catalog tab
      And Open AUDIOBOOK book with GET action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click GET action button on Book details screen
    Then Check that book contains LISTEN action button on Book details screen
    When Click LISTEN action button on Book details screen
    Then Audio player screen of book 'bookInfo' is opened
    When Tap play button on audio player screen
    Then Pause button is present on audio player screen
    When Tap pause button on audio player screen
    Then Play button is present on audio player screen
      And Book is not playing on audio player screen