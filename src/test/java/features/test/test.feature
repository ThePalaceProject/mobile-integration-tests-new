Feature: test for Aquality

  @aquality
  Scenario: Check the possibility of editing data in search field
    When Close tutorial screen
    Then Welcome screen is opened
    When Close welcome screen
    Then Add library screen is opened
    When Add library "LYRASIS Reads" on Add library screen
      And Open search modal
      And Type text "Book" and save it as 'word'
      And Edit data by adding characters in search field and save it as 'newWord'
    Then Placeholder contains word 'newWord' text in search field