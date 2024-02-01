Feature: Read EPUB in Palace Bookshelf

  Background:
    Given Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Catalog screen
    When Open search modal
      And Search for 'Flower Fables' and save bookName as 'bookNameInfo'

  @tier2
  Scenario: Navigate by Page
    When Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
    Then 'bookInfo' book is present on epub reader screen
    When Scroll page forward from 3 to 5 times
      And Open navigation bar on reader epub screen
      And Save pageNumber as 'pageNumberKey' and chapterName as 'chapterNameKey' on epub reader screen
      And Tap on right book corner on epub reader screen
    Then Next page is opened and old page has 'pageNumberKey' pageNumber and 'chapterNameKey' chapterName on epub reader screen
    When Save pageNumber as 'pageNumberKey' and chapterName as 'chapterNameKey' on epub reader screen
      And Click on left book corner on epub reader screen
    Then Previous page is opened and old page has 'pageNumberKey' pageNumber and 'chapterNameKey' chapterName on epub reader screen

  @tier2
  Scenario: Navigate by bookmarks
    When Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
      And Open navigation bar on reader epub screen
      And Add bookmark on reader epub screen
      And Save pageNumber as 'pageNumberKey' and chapterName as 'chapterNameKey' on epub reader screen
      And Save device time and date as 'deviceTimeDateKey'
      And Scroll page forward from 3 to 5 times
      And Add bookmark on reader epub screen
      And Save pageNumber as 'pageNumberKey2' and chapterName as 'chapterNameKey2' on epub reader screen
      And Save device time and date as 'deviceTimeDateKey2'
      And Open navigation bar on reader epub screen
      And Open bookmarks epub screen
    Then Bookmark with 'chapterNameKey' and 'deviceTimeDateKey' is displayed on bookmarks epub screen
      And Bookmark with 'chapterNameKey2' and 'deviceTimeDateKey2' is displayed on bookmarks epub screen
    When Open random bookmark and save chapter name as 'chapterNameKey3' on bookmarks epub screen
    Then 'chapterNameKey3' chapter name is displayed on reader epub screen

  @tier2
  Scenario: Delete bookmarks
    When Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
      And Open navigation bar on reader epub screen
      And Add bookmark on reader epub screen
      And Delete bookmark on reader epub screen
    Then Bookmark is not displayed on reader epub screen
    When Scroll page forward from 4 to 7 times
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

  @tier2
  Scenario: Navigate by Table of Contents Menu
    When Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
    Then Random chapter of epub can be opened from toc epub screen

  @tier2
  Scenario: Navigate View options
    When Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
      And Return to previous screen for epub and pdf
      And Click READ action button on Book details screen
    Then 'bookInfo' book is present on epub reader screen
    When Open TOC epub screen
    Then Toc epub screen is opened
    When Close TOC epub screen
      And Open font and background settings epub screen
    Then Font and background settings epub screen is opened

#  @tier2 @exclude_android
#  Scenario: Font settings: Check of increasing and reducing the text size
#    When Click GET action button on EBOOK book with 'bookNameInfo' bookName on catalog books screen and save book as 'bookInfo'
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
#    And I DECREASE_FONT of text
#    Then Font size 'fontSize' is decreased
#    When Restart app
#    And Open Books
#    And Open EBOOK book with READ action button and 'bookInfo' bookInfo on books screen
#    And Click READ action button on book details screen
#    Then Font size 'fontSize' is decreased
#    When Return to previous screen from epub
#    And Click READ action button on book details screen
#    Then Font size 'fontSize' is decreased

#  @tier2 @exclude_android
#  Scenario: Font settings: Check of font style
#    When Click GET action button on EBOOK book with 'bookNameInfo' bookName on catalog books screen and save book as 'bookInfo'
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

#  @tier2 @exclude_android
#  Scenario: Font settings: Check of text theme
#    When Click GET action button on EBOOK book with 'bookNameInfo' bookName on catalog books screen and save book as 'bookInfo'
#    And Open EBOOK book with READ action button and 'bookNameInfo' bookName on catalog books screen and save book as 'bookInfo'
#    And Click READ action button on book details screen
#    And Scroll page forward from 7 to 9 times
#    And Open font settings
#    And Change contrast to BLACK_TEXT_ON_WHITE
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

  @tier2
  Scenario: Open book to last page read
    When Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
      And Scroll page forward from 3 to 6 times
      And Save pageNumber as 'pageNumberKey' and chapterName as 'chapterNameKey' on epub reader screen
      And Wait for 3 seconds
      And Return to previous screen for epub and pdf
      And Click READ action button on Book details screen
    Then 'bookInfo' book is present on epub reader screen
      And PageNumber 'pageNumberKey' is correct
      And Scroll page forward from 3 to 4 times
      And Save pageNumber as 'pageNumberKey' and chapterName as 'chapterNameKey' on epub reader screen
      And Wait for 3 seconds
      And Restart app
      And Open Books
    Then EBOOK book with READ action button and 'bookInfo' bookInfo is present on books screen
    When Open EBOOK book with READ action button and 'bookInfo' bookInfo on books screen
      And Click READ action button on Book details screen
    Then 'bookInfo' book is present on epub reader screen
      And PageNumber 'pageNumberKey' is correct