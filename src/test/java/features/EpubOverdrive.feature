Feature: Read EPUB from Overdrive in A1QA

  Background:
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
    When Open Catalog
      And Open search modal

#  @logout @returnBooks @tier2
#  Scenario: Epub: Font settings: Check of increasing and reducing the text size
#    When Search for "The Woman in White" and save bookName as 'bookNameInfo'
#    And Click GET action button on EBOOK book with 'bookNameInfo' bookName on catalog books screen and save book as 'bookInfo'
#    And Open EBOOK book with READ action button and 'bookNameInfo' bookName on catalog books screen and save book as 'bookInfo'
#    And Click READ action button on book details screen
#    And Scroll page forward from 7 to 9 times
#    And Save font size as 'fontSize'
#    And Open font settings
#    And INCREASE_FONT of text
#    Then Font size 'fontSize' is increased
#    When Restart app
#    And Open Books
#    And Open EBOOK book with READ action button and 'bookInfo' bookInfo on books screen
#    And Click READ action button on book details screen
#    Then Font size 'fontSize' is increased
#    When Return to previous screen from epub
#    And Click READ action button on book details screen
#    Then Font size 'fontSize' is increased
#    When Save font size as 'fontSize'
#    And Open font settings
#    And DECREASE_FONT of text
#    Then Font size 'fontSize' is decreased
#    When Restart app
#    And Open Books
#    And Open EBOOK book with READ action button and 'bookInfo' bookInfo on books screen
#    And Click READ action button on book details screen
#    Then Font size 'fontSize' is decreased
#    When Return to previous screen from epub
#    And Click READ action button on book details screen
#    Then Font size 'fontSize' is decreased

#  @logout @returnBooks @tier2
#  Scenario: Epub: Font settings: Check of font style
#    When Search for "A Game of Thrones" and save bookName as 'bookNameInfo'
#    And Click GET action button on EBOOK book with 'bookNameInfo' bookName on catalog books screen and save book as 'bookInfo'
#    And Open EBOOK book with READ action button and 'bookNameInfo' bookName on catalog books screen and save book as 'bookInfo'
#    And Click READ action button on book details screen
#    And Scroll page forward from 7 to 9 times
#    And Open font settings
#    And Change font style to FONT_SERIF
#    Then Book text displays in FONT_SERIF font
#    When Restart app
#    And Open Books
#    And Open EBOOK book with READ action button and 'bookInfo' bookInfo on books screen
#    And Click READ action button on book details screen
#    Then Book text displays in FONT_SERIF font
#    When Return to previous screen from epub
#    And Click READ action button on book details screen
#    Then Book text displays in FONT_SERIF font
#    When Open font settings
#    And Change font style to FONT_SANS
#    Then Book text displays in FONT_SANS font
#    When Restart app
#    And Open Books
#    And Open EBOOK book with READ action button and 'bookInfo' bookInfo on books screen
#    And Click READ action button on book details screen
#    Then Book text displays in FONT_SANS font
#    When Return to previous screen from epub
#    And Click READ action button on book details screen
#    Then Book text displays in FONT_SANS font
#    When Open font settings
#    And Change font style to FONT_DYSLEXIC
#    Then Book text displays in FONT_DYSLEXIC font
#    When Restart app
#    And Open Books
#    And Open EBOOK book with READ action button and 'bookInfo' bookInfo on books screen
#    And Click READ action button on book details screen
#    Then Book text displays in FONT_DYSLEXIC font
#    When Return to previous screen from epub
#    And Click READ action button on book details screen
#    Then Book text displays in FONT_DYSLEXIC font

#  @logout @returnBooks @tier2
#  Scenario: Epub: Font settings: Check of text theme
#    When Search for "The Adventures of Sherlock Holmes" and save bookName as 'bookNameInfo'
#    And Click GET action button on EBOOK book with 'bookNameInfo' bookName on catalog books screen and save book as 'bookInfo'
#    And Open EBOOK book with READ action button and 'bookNameInfo' bookName on catalog books screen and save book as 'bookInfo'
#    And Click READ action button on book details screen
#    And Scroll page forward from 7 to 9 times
#    And Open font settings
#    When Change contrast to BLACK_TEXT_ON_WHITE
#    Then The BLACK_TEXT_ON_WHITE background is correct
#    When Restart app
#    And Open Books
#    And Open EBOOK book with READ action button and 'bookInfo' bookInfo on books screen
#    And Click READ action button on book details screen
#    Then The BLACK_TEXT_ON_WHITE background is correct
#    When Return to previous screen from epub
#    And Click READ action button on book details screen
#    Then The BLACK_TEXT_ON_WHITE background is correct
#    When Open font settings
#    And Change contrast to BLACK_TEXT_ON_SEPIA
#    Then The BLACK_TEXT_ON_SEPIA background is correct
#    When Restart app
#    And Open Books
#    And Open EBOOK book with READ action button and 'bookInfo' bookInfo on books screen
#    And Click READ action button on book details screen
#    Then The BLACK_TEXT_ON_SEPIA background is correct
#    When Return to previous screen from epub
#    And Click READ action button on book details screen
#    Then The BLACK_TEXT_ON_SEPIA background is correct
#    When Open font settings
#    And Change contrast to WHITE_TEXT_ON_BLACK
#    Then The WHITE_TEXT_ON_BLACK background is correct
#    When Restart app
#    And Open Books
#    And Open EBOOK book with READ action button and 'bookInfo' bookInfo on books screen
#    And Click READ action button on book details screen
#    Then The WHITE_TEXT_ON_BLACK background is correct
#    When Return to previous screen from epub
#    And Click READ action button on book details screen
#    Then The WHITE_TEXT_ON_BLACK background is correct

  @logout @returnBooks @tier2
  Scenario: Epub: Open book to last page read
    When Search for "Romeo and Juliet" and save bookName as 'bookNameInfo'
      And Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
      And Scroll page forward from 7 to 10 times
      And Save pageNumber as 'pageNumberKey' and chapterName as 'chapterNameKey' on epub reader screen
      And Wait for 3 seconds
      And Return to previous screen for epub and pdf
      And Click READ action button on Book details screen
    Then 'bookInfo' book is present on epub reader screen
      And PageNumber 'pageNumberKey' is correct
    When Scroll page forward from 3 to 4 times
      And Save pageNumber as 'pageNumberKey' and chapterName as 'chapterNameKey' on epub reader screen
      And Wait for 3 seconds
      And Restart app
      And Open Books
    Then EBOOK book with READ action button and 'bookInfo' bookInfo is present on books screen
    When Open EBOOK book with READ action button and 'bookInfo' bookInfo on books screen
      And Click READ action button on Book details screen
    Then 'bookInfo' book is present on epub reader screen
      And PageNumber 'pageNumberKey' is correct

  @logout @returnBooks @tier2
  Scenario: Epub: Navigate by Page
    When Search for "The Book Thief" and save bookName as 'bookNameInfo'
      And Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
    Then 'bookInfo' book is present on epub reader screen
    When Scroll page forward from 7 to 10 times
      And Open navigation bar on reader epub screen
      And Save pageNumber as 'pageNumberKey' and chapterName as 'chapterNameKey' on epub reader screen
      And Open navigation bar on reader epub screen
    Then Next page is opened and old page has 'pageNumberKey' pageNumber and 'chapterNameKey' chapterName on epub reader screen
    When Save pageNumber as 'pageNumberKey' and chapterName as 'chapterNameKey' on epub reader screen
      And Click on left book corner on epub reader screen
    Then Previous page is opened and old page has 'pageNumberKey' pageNumber and 'chapterNameKey' chapterName on epub reader screen

  @logout @returnBooks @tier2
  Scenario: Epub: Navigate by bookmarks
    When Search for "Moby Dick" and save bookName as 'bookNameInfo'
      And Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
      And Open navigation bar on reader epub screen
      And Add bookmark on reader epub screen
    Then Bookmark is displayed on reader epub screen
    When Save pageNumber as 'pageNumberKey' and chapterName as 'chapterNameKey' on epub reader screen
      And Save device time and date as 'deviceTimeDateKey'
      And Scroll page forward from 7 to 9 times
      And Add bookmark on reader epub screen
      And Save pageNumber as 'pageNumberKey2' and chapterName as 'chapterNameKey2' on epub reader screen
      And Save device time and date as 'deviceTimeDateKey2'
      And Open navigation bar on reader epub screen
      And Open bookmarks epub screen
    Then Bookmark with 'chapterNameKey' and 'deviceTimeDateKey' is displayed on bookmarks epub screen
      And Bookmark with 'chapterNameKey2' and 'deviceTimeDateKey2' is displayed on bookmarks epub screen
    When Open random bookmark and save chapter name as 'chapterNameKey3' on bookmarks epub screen
    Then 'chapterNameKey3' chapter name is displayed on reader epub screen

  @logout @returnBooks @tier2
  Scenario: Epub: Delete bookmarks
    When Search for "Little Women" and save bookName as 'bookNameInfo'
      And Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
      And Open navigation bar on reader epub screen
      And Add bookmark on reader epub screen
      And Delete bookmark on reader epub screen
    Then Bookmark is not displayed on reader epub screen
    When Scroll page forward from 7 to 9 times
      And Add bookmark on reader epub screen
      And Save pageNumber as 'pageNumberKey' and chapterName as 'chapterNameKey' on epub reader screen
      And Save device time and date as 'deviceTimeDateKey'
      And Open navigation bar on reader epub screen
      And Open bookmarks epub screen
      And Delete bookmark on bookmarks epub screen
    Then Bookmark with 'chapterNameKey' and 'deviceTimeDateKey' is not displayed on bookmarks epub screen
    When Return to reader epub screen from toc bookmarks epub screen
      And Click on left book corner on epub reader screen
    Then 'chapterNameKey' chapter name is displayed on reader epub screen
      And Bookmark is not displayed on reader epub screen