Feature: Catalog Navigation module

  Background:
    Given Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened

  @tier2
  Scenario: Return to last library catalog
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Catalog screen
    When Add 'LYRASIS Reads' library in Libraries screen
    Then Catalog screen is opened
    When Restart app
    Then Catalog screen is opened
      And Category names are loaded on Catalog screen

  @tier2
  Scenario: Check of the titles of books sections in Palace Bookshelf
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Catalog screen
      And Catalog screen is opened
      And Category names are correct on Catalog screen

  @tier2
  Scenario Outline: Check of books sorting in Palace Bookshelf
    When Add library "Palace Bookshelf" on Add library screen
    Then Library "Palace Bookshelf" is opened on Catalog screen
      And Catalog screen is opened
    When Open categories by chain and chain starts from CategoryScreen:
      | DPLA Publications |
    Then Books are sorted by Author by default on subcategory screen in 'Palace Bookshelf'
      And There are sorting by '<type1>', '<type2>' and '<type3>' on Subcategory screen in 'Palace Bookshelf'

    Scenarios:
      | type1  | type2          | type3 |
      | Author | Recently Added | Title |

  @tier2
  Scenario Outline: Check of tabs at the top of the screen in LYRASIS Reads
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Catalog screen
      And Catalog screen is opened
      And There are types '<type1>', '<type2>' and '<type3>' of books on catalog book screen:
      And Section with books of '<type1>' type is opened on catalog book screen
    When Switch to '<type2>' catalog tab
    Then Section with books of '<type2>' type is opened on catalog book screen
    When Switch to '<type3>' catalog tab
    Then Section with books of '<type3>' type is opened on catalog book screen

    Scenarios:
      | type1 | type2  | type3      |
      | All   | eBooks | Audiobooks |

  @tier2
  Scenario: Check of the titles of books sections in LYRASIS Reads
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Catalog screen
      And Catalog screen is opened
      And Category names are correct on Catalog screen

  @tier2
  Scenario Outline: Check of books sorting in LYRASIS Reads
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Catalog screen
      And Catalog screen is opened
    When Open categories by chain and chain starts from CategoryScreen:
      | Baker & Taylor Axis360 Test |
      And Swipe sort options
    Then Books are sorted by Author by default on subcategory screen in 'LYRASIS Reads'
      And There are sorting by '<type1>', '<type2>' and '<type3>' on Subcategory screen in "LYRASIS Reads"

    Scenarios:
      | type1  | type2          | type3 |
      | Author | Recently Added | Title |

  @tier2
  Scenario Outline: Check of books availability in LYRASIS Reads
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Catalog screen
      And Catalog screen is opened
    When Open categories by chain and chain starts from CategoryScreen:
      | Baker & Taylor Axis360 Test |
    Then Subcategory name is 'Baker & Taylor Axis360 Test'
      And The book availability is ALL by default on Subcategory screen
      And There are availability by '<type1>', '<type2>' and '<type3>' on Subcategory screen

    Scenarios:
      | type1 | type2         | type3         |
      | All   | Available now | Yours to keep |

  @tier2
  Scenario: Check all types of availability
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Catalog screen
      And Catalog screen is opened
    When Open categories by chain and chain starts from CategoryScreen:
      | Baker & Taylor Axis360 Test |
    Then Subcategory name is 'Baker & Taylor Axis360 Test'
    When Change books visibility to show AVAILABLE_NOW
    Then All books can be loaned or downloaded
    When Change books visibility to show ALL
    Then Subcategory name is 'Baker & Taylor Axis360 Test'
    When Change books visibility to show YOURS_TO_KEEP
    Then All books can be downloaded

  @tier2 @exclude_android
  Scenario Outline: Check of books collections
    When Add library "LYRASIS Reads" on Add library screen
    Then Library "LYRASIS Reads" is opened on Catalog screen
      And Catalog screen is opened
    When Open categories by chain and chain starts from CategoryScreen:
      | Baker & Taylor Axis360 Test |
    Then Subcategory name is 'Baker & Taylor Axis360 Test'
    And Collections is Everything by default on subcategory screen
    And There are collection type by '<type1>' and '<type2>' on subcategory screen

    Scenarios:
      | type1      | type2         |
      | Everything | Popular Books |

#  notifications are after opening the app
#  @smoke1 @logout @returnBooks @exclude_android
#  Scenario: Catalog: Perform check of Reserve button and Allow notifications
#    When Add library "LYRASIS Reads" on Add library screen
#    Then Library "LYRASIS Reads" is opened on Libraries screen
#    When Enter credentials for "LYRASIS Reads" library
#    Then Login is performed successfully
#    When Activate sync bookmarks on Sign in screen
#      And Open Catalog
#      And Open search modal
#      And Search 'unavailable' book of distributor 'Palace Marketplace' and bookType 'AUDIOBOOK' and save as 'bookNameInfo'
#      And Open AUDIOBOOK book with RESERVE action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
#      And Click RESERVE action button without closing alert on Book details screen
#    Then There is an alert to allow notifications
#    When Tap ALLOW button on the alert
#    Then Alert to allow notification is not displayed
#      And Check that book contains REMOVE action button on Book details screen

#  notifications are after opening the app
#  @smoke1 @logout @returnBooks @exclude_android
#  Scenario: Catalog: Alert: Perform check of "Don't Allow" button
#    When Add library "LYRASIS Reads" on Add library screen
#    Then Library "LYRASIS Reads" is opened on Libraries screen
#    When Enter credentials for "LYRASIS Reads" library
#    Then Login is performed successfully
#    When Activate sync bookmarks on Sign in screen
#      And Open Catalog
#      And Open search modal
#      And Search 'unavailable' book of distributor 'Palace Marketplace' and bookType 'AUDIOBOOK' and save as 'bookNameInfo'
#      And Open AUDIOBOOK book with RESERVE action button and 'bookNameInfo' bookName on Catalog books screen and save book as 'bookInfo'
#      And Click RESERVE action button without closing alert on Book details screen
#    Then There is an alert to allow notifications
#    When Don't Allow notifications on the alert
#    Then Alert to allow notification is not displayed
#      And Check that book contains REMOVE action button on Book details screen

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