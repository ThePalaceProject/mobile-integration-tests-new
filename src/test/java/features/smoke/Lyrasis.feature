Feature: Smoke tests in LYRASIS Reads library



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





