Feature: Smoke tests in LYRASIS Reads library

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
      And Search for "Foundations of Trusted Autonomy" and save bookName as 'bookNameInfo'
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

