@maintenance @exclude_android
Feature: iOS maintenance helpers

  @logout @logout_before_device_switch
  Scenario: Logout from active iOS session before device switch
    When Wait for 1 seconds
