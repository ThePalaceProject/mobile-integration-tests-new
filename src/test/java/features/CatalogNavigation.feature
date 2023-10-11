Feature: Catalog Navigation module

  @palace
  Scenario: Return to last library catalog
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
    When Add 'LYRASIS Reads' library in Libraries screen
    Then Catalog screen is opened
    When Restart app
    Then Catalog screen is opened
      And Category names are loaded on Catalog screen

  @palace
  Scenario: Browse Categories in Palace Bookshelf
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Catalog screen is opened
      And Library "Palace Bookshelf" is opened on Libraries screen
    When Open Catalog
    Then Catalog screen is opened
    When Swipe up
      And Count of books in first category is more than 1
    When Get names of books on screen and save them as 'listOfBooksOnMainPage'
      And Open categories by chain and chain starts from CategoryScreen:
        | Nonfiction          |
        | Biography & Memoir  |
    Then Subcategory name is 'Biography & Memoir'
      And List of books on screen is not equal to list of books saved as 'listOfBooksOnMainPage'
    When Open first book in Subcategory List and save it as 'bookInfo'
    Then Book 'bookInfo' is opened on book details screen

  @palace
  Scenario: Check of the titles of books sections in Palace Bookshelf
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
    When Open Catalog
    Then Catalog screen is opened
      And Category names are correct on Catalog screen

  @palace
  Scenario Outline: Check of books sorting in Palace Bookshelf
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Libraries screen
    When Open Catalog
    Then Catalog screen is opened
    When Open categories by chain and chain starts from CategoryScreen:
      | Banned Books |
    Then Books are sorted by Author by default on subcategory screen in 'Palace Bookshelf'
      And There are sorting by '<type1>', '<type2>' and '<type3>' on Subcategory screen

    Scenarios:
      | type1  | type2          | type3 |
      | Author | Recently Added | Title |

#  Sorting doesn't work correctly
#  @palace
#  Scenario: Sort Lists in Palace Bookshelf
#    When Close tutorial screen
#    Then Welcome screen is opened
#    When Close welcome screen
#    Then Add library screen is opened
#    When Add library "Palace Bookshelf" on Add library screen
#    Then Library "Palace Bookshelf" is opened on Libraries screen
#    When Open Catalog
#    Then Catalog screen is opened
#    When Open categories by chain and chain starts from CategoryScreen:
#      | Banned Books |
#    Then Subcategory name is "Banned Books"
#    When Sort books by AUTHOR
#    Then Subcategory name is "Banned Books"
#      And Books are sorted by Author ascending
#    When Sort books by TITLE
#    Then Subcategory name is "Banned Books"
#    And Books are sorted by Title ascending
#    When Save list of books as 'listOfBooks'
#    And Sort books by RECENTLY_ADDED
#    Then Subcategory name is "Banned Books"
#    And List of books on subcategory screen is not equal to list of books saved as 'listOfBooks'

  @palace
  Scenario Outline: Check of tabs at the top of the screen in LYRASIS Reads
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
    Then Catalog screen is opened
      And There are types '<type1>', '<type2>' and '<type3>' of books on catalog book screen:
      And Section with books of '<type1>' type is opened on catalog book screen
    When Switch to '<type2>' catalog tab
    Then Section with books of '<type2>' type is opened on catalog book screen
    When Switch to '<type3>' catalog tab
    Then Section with books of '<type3>' type is opened on catalog book screen

    Scenarios:
      | type1 | type2  | type3      |
      | All   | eBooks | Audiobooks |

  @palace
  Scenario: Check of the titles of books sections in LYRASIS Reads
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
    Then Catalog screen is opened
      And Category names are correct on Catalog screen

  @palace
  Scenario Outline: Check of books sorting in LYRASIS Reads
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
    Then Catalog screen is opened
    When Open categories by chain and chain starts from CategoryScreen:
      | Baker & Taylor Axis360 Test |
    Then Books are sorted by Author by default on subcategory screen in 'LYRASIS Reads'
      And There are sorting by '<type1>', '<type2>' and '<type3>' on Subcategory screen

    Scenarios:
      | type1  | type2          | type3 |
      | Author | Recently Added | Title |

    #  Sorting doesn't work correctly
#  @tier2
#  Scenario: Sort Lists in LYRASIS
#    When Close tutorial screen
#    Then Welcome screen is opened
#    When Close welcome screen
#    Then Add library screen is opened
#    When Add library "LYRASIS Reads" on Add library screen
#    Then Library "LYRASIS Reads" is opened on Libraries screen
#    When Open Catalog
#    Then Catalog screen is opened
#    When Open categories by chain and chain starts from CategoryScreen:
#      | Baker & Taylor Axis360 Test |
#    Then Subcategory name is 'Baker & Taylor Axis360 Test'
#    When Sort books by AUTHOR
#    Then Subcategory name is 'Baker & Taylor Axis360 Test'
#      And Books are sorted by Author ascending
#    When Sort books by TITLE
#    Then Subcategory name is 'Baker & Taylor Axis360 Test'
#      And Books are sorted by Title ascending
#    When Save list of books as 'listOfBooks'
#      And Sort books by RECENTLY_ADDED
#    Then Subcategory name is 'Baker & Taylor Axis360 Test'
#      And List of books on subcategory screen is not equal to list of books saved as 'listOfBooks'

  @palace
  Scenario Outline: Check of books availability in LYRASIS Reads
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
    Then Catalog screen is opened
    When Open categories by chain and chain starts from CategoryScreen:
      | Baker & Taylor Axis360 Test |
    Then Subcategory name is 'Baker & Taylor Axis360 Test'
      And The book availability is ALL by default on Subcategory screen
      And There are availability by '<type1>', '<type2>' and '<type3>' on Subcategory screen

    Scenarios:
      | type1 | type2         | type3         |
      | All   | Available now | Yours to keep |

  @palace
  Scenario: Check all types of availability
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
    Then Catalog screen is opened
    When Open categories by chain and chain starts from CategoryScreen:
      | Baker & Taylor Axis360 Test |
    Then Subcategory name is 'Baker & Taylor Axis360 Test'
    When Change books visibility to show AVAILABLE_NOW
    Then All books can be loaned or downloaded
    When Change books visibility to show ALL
    Then Subcategory name is 'Baker & Taylor Axis360 Test'
    When Change books visibility to show YOURS_TO_KEEP
    Then All books can be downloaded

  @palace @exclude_android
  Scenario Outline: Check of books collections
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Libraries screen
    When Open Catalog
    Then Catalog screen is opened
    When Open categories by chain and chain starts from CategoryScreen:
      | Baker & Taylor Axis360 Test |
    Then Subcategory name is 'Baker & Taylor Axis360 Test'
    And Collections is Everything by default on subcategory screen
    And There are collection type by '<type1>' and '<type2>' on subcategory screen

    Scenarios:
      | type1      | type2         |
      | Everything | Popular Books |

  @smoke @logout @returnBooks @exclude_android
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

  @smoke @logout @returnBooks @exclude_android
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

#    More button is not available
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


    #More button is not available
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
#    When Wait for 3 seconds
#      Then More button is present on each section of books on Catalog screen
#    When Wait for 3 seconds
#    When Click More button from random book section and save name of section as 'sectionInfo' on Catalog screen
#    Then Book section 'sectionInfo' is opened
#    When Tap Back button on Subcategory screen
#    Then Catalog screen is opened