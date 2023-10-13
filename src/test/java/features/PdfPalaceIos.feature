Feature: Read PDF in Palace Bookshelf on IOS

  Background:
    Given Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
    When Open Catalog
      And Open search modal
      And Search for 'Deep into Pharo' and save bookName as 'bookNameInfo'
      And Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
    Then EBOOK book with READ action button and 'bookInfo' bookInfo is present on Catalog books screen
    When Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
    Then Reader pdf screen is opened

  @palace @exclude_android
  Scenario: Navigate by Page
    Then Save page number as 'pageInfo' on pdf reader screen
    When Go to next page on reader pdf screen
    Then Page number increased by 1 from 'pageInfo' on pdf reader screen
    When Save page number as 'pageInfo2' on pdf reader screen
      And Go to previous page on reader pdf screen
    Then Page number decreased by 1 from 'pageInfo2' on pdf reader screen

  @palace @exclude_android
  Scenario: Open book to last page read
    When Swipe pdf page forward from 4 to 6 times on reader pdf screen
      And Save page number as 'pageNumber' on pdf reader screen
      And Return to previous screen for epub and pdf
      And Click READ action button on Book details screen
    Then Reader pdf screen is opened
      And Page number is equal to 'pageNumber' on pdf reader screen
    When Restart app
      And Open Books
    Then EBOOK book with READ action button and 'bookInfo' bookInfo is present on books screen
    When Open EBOOK book with READ action button and 'bookInfo' bookInfo on books screen
      And Click READ action button on Book details screen
    Then Reader pdf screen is opened
      And Page number is equal to 'pageNumber' on pdf reader screen

  @palace @exclude_android
  Scenario: Navigate by Thumbnails
    When Open TOC on pdf reader screen
    Then There are content list with thumbnails and chapter content on pdf toc screen
    When Open random thumbnail and save the number as 'pageInfo' on pdf toc screen
    Then Page number is equal to 'pageInfo' on pdf reader screen

  @palace @exclude_android
  Scenario: Search Pdf Functionality
    When Open search pdf screen
    Then Search pdf screen is opened
    When Close pdf search screen
    Then Reader pdf screen is opened
    When Open search pdf screen
    Then Search pdf screen is opened
    When Enter 'try' text on search pdf screen
      And Delete text in search line on search pdf screen
    Then Search field is empty on search pdf screen
    When Enter 'try' text on search pdf screen
    Then Found lines should contain 'try' in themselves on search pdf screen
    When Close pdf search screen

  @palace @exclude_android
  Scenario: Navigate by Pdf Search Results
    When Open search pdf screen
      And Enter 'try' text on search pdf screen
      And Open random found text and save page number as 'pageNumber' on search pdf screen
    Then Page number is equal to 'pageNumber' on pdf reader screen

  @palace @exclude_android
  Scenario: Navigate by Page slider
    When Save page number as 'pageNumber' on pdf reader screen
      And Slide page slider RIGHT on reader pdf screen
    Then The 'pageNumber' saved page number is less than the current page number on the reader pdf screen
    When Save page number as 'pageNumber' on pdf reader screen
      And Slide page slider LEFT on reader pdf screen
    Then The 'pageNumber' saved page number is greater than the current page number on the reader pdf screen

  @palace @exclude_android
  Scenario: Navigate by pdf bookmarks
    When Open bookmarks pdf screen
    Then Bookmarks pdf screen is opened
      And Amount of bookmarks is 0 on bookmarks pdf screen
    When Close toc bookmarks pdf screen
      And Go to next page on reader pdf screen
      And Add bookmark on reader pdf screen
#    Then Bookmark is displayed on reader pdf screen
      And Save page number as 'pageNumberInfo' on pdf reader screen
      And Go to next page on reader pdf screen
      And Add bookmark on reader pdf screen
      And Save page number as 'pageNumberInfo2' on pdf reader screen
      And Go to next page on reader pdf screen
      And Add bookmark on reader pdf screen
#      And Delete bookmark on reader pdf screen
#    Then Bookmark is not displayed on reader pdf screen
      And Open bookmarks pdf screen
    Then Amount of bookmarks is 2 on bookmarks pdf screen
    When Open the 0 bookmark on bookmarks pdf screen
    Then Page number is equal to 'pageNumberInfo' on pdf reader screen

  @palace @exclude_android
  Scenario: Check table of contents
    When Open TOC on pdf reader screen
      And Open text chapter content on pdf toc screen
    Then Text chapter content is opened on pdf toc screen
    When Open content with thumbnails on pdf toc screen
    Then Thumbnails of the book pages are displayed

  @palace @exclude_android
  Scenario: Navigate by Chapters
    When Open TOC on pdf reader screen
      And Open text chapter content on pdf toc screen
      And Open random chapter and save the number as 'pageNumberInfo' on pdf toc screen
    Then Page number is equal to 'pageNumberInfo' on pdf reader screen