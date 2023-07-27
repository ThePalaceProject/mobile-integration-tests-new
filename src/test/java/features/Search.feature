Feature: Search module

  @palace
  Scenario: Find a library and delete it
    When Close tutorial screen
      And Close welcome screen
      And Type 'Brookfield Library' library and save name as 'libraryName' on Add library screen
    Then Library 'libraryName' is displayed on Add library screen
    When Clear search field on Add library screen
    Then Search field is empty on Add library screen

  @palace
  Scenario Outline: Check that library name contains one or more entered latin letters
    When Close tutorial screen
      And Close welcome screen
      And Type word <word> and save as 'info' on Add library screen
    Then Libraries contain word 'info' on Add library screen

    Scenarios:
      |word    |
      |book    |
      |F       |
      |lyrasis |
      |LYRASIS |
      |lYrAsIs |

  @palace
  Scenario Outline: Enter invalid data
    When Close tutorial screen
      And Close welcome screen
      And Type word <data> and save as 'data' on Add library screen
    Then Search result is empty on Add library screen

    Scenarios:
      |data                                 |
      |книга                                |
      |9822                                 |
      |<font color=red>Red text</font>      |
      |<script>alert(‘hello world’)</script>|
      |@                                    |
      |$!                                   |

  @palace
  Scenario: Find a book in Palace Bookshelf
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
      And Open search modal
      And Search for 'El gato negro' and save bookName as 'bookNameInfo'
    Then EBOOK book with GET action button and 'bookNameInfo' bookName is displayed on Catalog books screen

  @palace
  Scenario: Delete a book from search bar in Palace Bookshelf
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
      And Open search modal
      And Type book 'El gato negro' and save it as 'bookNameInfo'
      And Clear search field on Catalog books screen
    Then Search field is empty on Catalog books screen

  @palace
  Scenario Outline: Check that books from search result contain one or more entered latin letters or numeric in Palace Bookshelf
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
      And Open search modal
      And Type word <word> and save as 'info' on Catalog books screen
    Then Books contain word 'info' on Catalog books screen

    Scenarios:
      | word          |
      | cat           |
      | a             |

  @palace
  Scenario Outline: Find a book with name in different font cases in Palace Bookshelf
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
      And Open search modal
      And Type word <word> and save as 'info' on Catalog books screen
    Then The first book has 'info' bookName on Catalog books screen

    Scenarios:
      | word          |
      | el gato negro |
      | EL GATO NEGRO |
      | eL gAto NeGrO |

  @palace
  Scenario Outline: Enter invalid data in book name in Palace Bookshelf
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
      And Open search modal
      And Type word <data> and save as 'info' on Catalog books screen
    Then There is no results on Catalog books screen

    Scenarios:
      | data                                  |
      | рнл                                   |
      | <font color=red></font>               |
      | <script>alert(‘hello world’)</script> |
      | @$                                    |
      | !                                     |

  @palace
  Scenario: Check a placeholder in LYRASIS Reads
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
      And Open search modal
    Then Placeholder contains "Search" text in search field

  @palace
  Scenario: Check the possibility of editing data in search field in LYRASIS Reads
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
      And Open search modal
      And Type book "Book" and save it as 'word'
      And Edit data by adding characters in search field and save it as 'newWord'
    Then Placeholder contains newWord 'newWord' text in search field

  @palace
  Scenario: Check of empty field in LYRASIS Reads
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
      And Open search modal
    Then There is no possibility to search with empty search field

  @palace
  Scenario: Check of displaying the search field after search a book in LYRASIS Reads
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
      And Open search modal
      And Search for "Book" and save bookName as 'BookNameInfo'
    Then The search field is displayed and contains 'BookNameInfo' book

  @palace
  Scenario: Find a book in LYRASIS Reads
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
      And Open search modal
      And Search for 'Silk Road' and save bookName as 'bookNameInfo'
    Then EBOOK book with GET action button and 'bookNameInfo' bookName is displayed on Catalog books screen

  @palace
  Scenario: Delete a book from search bar in LYRASIS Reads
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
      And Open search modal
      And Type book 'Silk Road' and save it as 'bookNameInfo'
      And Clear search field on Catalog books screen
    Then Search field is empty on Catalog books screen

  @palace
  Scenario Outline: Check that books from search result contain one or more entered latin letters or numeric in LYRASIS Reads
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
      And Open search modal
      And Type word <word> and save as 'info' on Catalog books screen
    Then Books contain word 'info' on Catalog books screen

    Scenarios:
      | word          |
      | cat           |
      | a             |
      | 1            |

  @palace
  Scenario Outline: Find a book with name in different font cases in LYRASIS Reads
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
      And Open search modal
      And Type word <word> and save as 'info' on Catalog books screen
    Then The first book has 'info' bookName on Catalog books screen

    Scenarios:
      |word       |
      | silk road |
      | SILK ROAD |
      | SiLk rOaD |

  @palace
  Scenario Outline: Enter invalid data in book name in LYRASIS Reads
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
      And Open search modal
      And Type word <data> and save as 'info' on Catalog books screen
    Then There is no results on Catalog books screen

    Scenarios:
      | data                                  |
      | рнл                                   |
      | <font color=red></font>               |
      | <script>alert(‘hello world’)</script> |
      | @$                                    |
      | !                                     |