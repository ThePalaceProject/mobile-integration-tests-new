Feature: Read EPUB in Lyrasis Reads

  Background:
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

#  @logout @returnBooks @tier1
#  Scenario Outline: Font settings: Check of increasing and reducing the text size
#    When Search 'available' book of distributor '<distributor>' and bookType 'EBOOK' from "LYRASIS Reads" and save as 'bookNameInfo'
#      And Click GET action button on EBOOK book with 'bookNameInfo' bookName on catalog books screen and save book as 'bookInfo'
#      And Open EBOOK book with READ action button and 'bookNameInfo' bookName on catalog books screen and save book as 'bookInfo'
#      And Click READ action button on book details screen
#      And Scroll page forward from 7 to 9 times
#      And Save font size as 'fontSize'
#      And Open font settings
#      And INCREASE_FONT of text
#    Then Font size 'fontSize' is increased
#    When Restart app
#      And Open Books
#      And Open EBOOK book with READ action button and 'bookInfo' bookInfo on books screen
#      And Click READ action button on book details screen
#    Then Font size 'fontSize' is increased
#    When Return to previous screen from epub
#      And Click READ action button on book details screen
#    Then Font size 'fontSize' is increased
#    When Save font size as 'fontSize'
#      And Open font settings
#      And DECREASE_FONT of text
#    Then Font size 'fontSize' is decreased
#    When Restart app
#      And Open Books
#      And Open EBOOK book with READ action button and 'bookInfo' bookInfo on books screen
#      And Click READ action button on book details screen
#    Then Font size 'fontSize' is decreased
#    When Return to previous screen from epub
#      And Click READ action button on book details screen
#    Then Font size 'fontSize' is decreased
#
#    Scenarios:
#      | distributor        |
#      | Bibliotheca        |
#      | Palace Marketplace |
#      | Axis 360           |

#  @logout @returnBooks @tier1
#  Scenario Outline: Font settings: Check of font style
#    When Search 'available' book of distributor '<distributor>' and bookType 'EBOOK' from "LYRASIS Reads" and save as 'bookNameInfo'
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
#
#    Scenarios:
#      | distributor        |
#      | Bibliotheca        |
#      | Palace Marketplace |
#      | Axis 360           |

#  @logout @returnBooks @tier1
#  Scenario Outline: Font settings: Check of text theme
#    When Search 'available' book of distributor '<distributor>' and bookType 'EBOOK' from "LYRASIS Reads" and save as 'bookNameInfo'
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
#
#    Scenarios:
#      | distributor        |
#      | Bibliotheca        |
#      | Palace Marketplace |
#      | Axis 360           |

  @logout @returnBooks @tier1
  Scenario Outline: Open book to last page read
    When Search 'available' book of distributor '<distributor>' and bookType 'EBOOK' and save as 'bookNameInfo'
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

    Scenarios:
      | distributor        |
      | Bibliotheca        |
      | Palace Marketplace |
      | Axis 360           |

  @logout @returnBooks @tier1
  Scenario Outline: Navigate by bookmarks
    When Search 'available' book of distributor '<distributor>' and bookType 'EBOOK' and save as 'bookNameInfo'
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

    Scenarios:
      | distributor        |
      | Bibliotheca        |
      | Palace Marketplace |
      | Axis 360           |

  @logout @returnBooks @tier1
  Scenario Outline: Delete bookmarks
    When Search 'available' book of distributor '<distributor>' and bookType 'EBOOK' and save as 'bookNameInfo'
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

    Scenarios:
      | distributor        |
      | Bibliotheca        |
      | Palace Marketplace |
      | Axis 360           |

  @smoke @logout @returnBooks
  Scenario: Read ebooks: Pages: Perform check of reader navigating (swiping left and right)
    When Search for "The Giants Return" and save bookName as 'bookNameInfo'
      And Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
      And Wait for 5 seconds
    Then 'bookInfo' book is present on epub reader screen
    When Scroll page forward from 7 to 10 times
      And Save pageNumber as 'pageNumberKey' and chapterName as 'chapterNameKey' on epub reader screen
      And Go to next page on Reader epub screen
    Then Next page is opened and old page has 'pageNumberKey' pageNumber and 'chapterNameKey' chapterName on epub reader screen
    When Save pageNumber as 'pageNumberKey' and chapterName as 'chapterNameKey' on epub reader screen
      And Tap on right book corner on epub reader screen
    Then Next page is opened and old page has 'pageNumberKey' pageNumber and 'chapterNameKey' chapterName on epub reader screen
    When Save pageNumber as 'pageNumberKey' and chapterName as 'chapterNameKey' on epub reader screen
      And Click on left book corner on epub reader screen
    Then Previous page is opened and old page has 'pageNumberKey' pageNumber and 'chapterNameKey' chapterName on epub reader screen

#    When Save pageNumber as 'pageNumberKey' and chapterName as 'chapterNameKey' on epub reader screen
#      And Go to previous page on reader epub screen
#    Then Previous page is opened and old page has 'pageNumberKey' pageNumber and 'chapterNameKey' chapterName on epub reader screen

  @smoke @logout @returnBooks
  Scenario: Read ebooks: Table of contents: Perform check of navigation
    When Search for "The Giants Return" and save bookName as 'bookNameInfo'
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
    When Search for "Faith, Finance, and Economy" and save bookName as 'bookNameInfo'
      And Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
    Then 'bookInfo' book is present on epub reader screen
    When Open random chapter of epub and save it as 'chapterName' from toc epub screen
    Then Chapter 'chapterName' is opened on epub reader screen

  @smoke @logout @returnBooks
  Scenario: Read ebooks: Search: Perform check of Search icon
    When Search for "The Voyages of Doctor Dolittle" and save bookName as 'bookNameInfo'
      And Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
    Then 'bookInfo' book is present on epub reader screen
    When Tap search icon on epub reader screen
    Then Search epub screen is opened

  @smoke @logout @returnBooks
  Scenario: Read ebooks: Search: Perform check of entering characters
    When Search for "The Story of Doctor Dolittle" and save bookName as 'bookNameInfo'
      And Click GET action button on EBOOK book with 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Open EBOOK book with READ action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
      And Click READ action button on Book details screen
    Then 'bookInfo' book is present on epub reader screen
    When Tap search icon on epub reader screen
      And Enter 'try' text and save it as 'searchedText' on search epub screen
    Then The field allows to enter characters and contains 'searchedText' on search epub screen