# Mobile integration tests

This repository contains integration tests for the Android and iOS code of the [Palace](https://thepalaceproject.org/) application

# About framework

JDK 11 is used in this project. As a framework for automation testing was selected [aquality](https://github.com/aquality-automation/aquality-appium-mobile-java) framework.
This framework designed to simplify automation of Android and iOS mobile applications using Appium. Most of performed methods are logged using LOG4), so it is easy to see a history of performed actions in log.

## Cucumber

Cucumber is used in this project. It is a tool that supports BDD (behavior driver development). Central to the Cucumber BDD approach is its ordinary language parser Gherkin. It allows expected software behaviors to be specified in a logical language that every person can understand.

## JUnit

The next big part of the solution is a test runner. As the test runner JUnit is used. This framework permits validate results of the tests and run tests in pair with Cucumber.

# How to run tests

Tests are created for Palace Bookshelf, A1QA Test Library and LYRASIS Reads libraries and run on iOS and Android platforms on such devices:

* Android
  - Samsung Galaxy S23 Ultra (Android 13)
  - Samsung Galaxy S22 Ultra (Android 12)
  - Samsung Galaxy S21 (Android 12)
  - Samsung Galaxy Tab S9 (Android 13)
  - Google Pixel 8 Pro (Android 14)
  - Google Pixel 7 Pro (Android 13)

* iOS
  - iPhone 15 Pro Max (iOS 17)
  - iPhone 14 Pro Max (iOS 16)
  - iPhone 13 Pro Max (iOS 15)
  - iPad Pro 2022 (iOS 16)
  - iPhone XR (iOS 15)
  - iPhone SE 2020 (iOS 16) 
  - iPhone 8 (iOS 15)

## Run via GitHub Actions
All tests run at night in BrowserStack. Runs are configured with GitHub Actions in [maven.yml file](https://github.com/ThePalaceProject/mobile-integration-tests-new/blob/main/.github/workflows/maven.yml). Test run time on each device is configured in a file that describes it using cron expressions. These devices are described in repositories for [Android](https://github.com/ThePalaceProject/android-binaries/tree/main/.github/workflows) and [iOS](https://github.com/ThePalaceProject/ios-binaries/tree/master/.github/workflows) devices. In the [Actions](https://github.com/ThePalaceProject/mobile-integration-tests-new/actions) section click the **Run workflow** button and start tests configured by tiers, app version and git branch. Below attached the screenshot with Ul of the Github actions solution.

![Screenshot_2](https://github.com/ThePalaceProject/mobile-integration-tests-new/assets/33911738/b1ddcfc0-daa2-46ef-894c-ea1fbac32165)

Select the **Test Run** option in the left menu and click the **Run workflow**. Options to configure:
1. ﻿﻿_Use workflow from_ - git branch to launch (e.g. main)
2. _Tags to run_ - tags (e.g. @tier1 @tier2, @smoke) if to run a specific tier. @tier1 - Tests for LYRASIS Reads library, @tier2 - tests for A1QA Test Library and Palace Bookshelf libraries, @smoke - smoke tests.
3. _﻿﻿app_url of uploaded to BS link_ - the app URL that can be gotten in the App Automate on the Browserstack 
4. _build name of given application_ - assembly name can be anything
5. _Platform name_ - ios/android
6. _device name and version for the autotests_ - device name to run tests. They can be found in the [iOS](https://github.com/ThePalaceProject/ios-binaries/tree/master/.github/workflows) repository for iOS devices and [Android](https://github.com/ThePalaceProject/android-binaries/tree/main/.github/workflows) repository for the Android.

After configuring the test run, click the **Run workflow** green button to apply the run

## Run locally
Tests can also be run locally (e.g. by **Intellij Idea**). The [settings.json](https://github.com/ThePalaceProject/mobile-integration-tests-new/blob/main/src/test/resources/settings.json) file is used to configure device, application build and platform. Write down target platformName, remoteConnectionUrl, deviceKey, username and access key from BrowserStack and app link to run tests.

![Screenshot_1](https://github.com/ThePalaceProject/mobile-integration-tests-new/assets/33911738/79dab8b2-0a15-44d4-a04b-591fb5264128)

* _platformName_ - android/ios
* _remoteConnectionUrl_ - https://hub-cloud.browserstack.com/wd/hub
* _deviceKey_ - the device name where to run tests. Can be found in [devices.json](https://github.com/ThePalaceProject/mobile-integration-tests-new/blob/main/src/test/resources/devices.json) file
* _app_ - the link to the build on iOS or Android from the Browserstack.
* _browserstack.user_ - user name of the Browserstack account.
* _browserstack.key_ - key of the Browserstack account

All devices are described in [devices.json](https://github.com/ThePalaceProject/mobile-integration-tests-new/blob/main/src/test/resources/devices.json) file.

![Screenshot_3](https://github.com/ThePalaceProject/mobile-integration-tests-new/assets/33911738/4167f0af-3b7c-49ae-8fc6-e938f8245095)

For LYRASIS Reads and A1QA Test library libraries credentials are needed. It can be configured in [config.json](https://github.com/ThePalaceProject/mobile-integration-tests-new/blob/main/src/test/resources/config.json) file.

![Screenshot_4](https://github.com/ThePalaceProject/mobile-integration-tests-new/assets/33911738/b653ca3d-d989-4a23-851b-60423aa3d557)

* _libraryName_ - LYRASISReads/A1QATestLibrary
* _barcode_ - barcode
* _pin_ - password

Feature files are used to run tests.

![Screenshot_5](https://github.com/ThePalaceProject/mobile-integration-tests-new/assets/33911738/09d6e84e-2a9e-48b4-bc40-1183a816be94)

![Screenshot_6](https://github.com/ThePalaceProject/mobile-integration-tests-new/assets/33911738/868dbde7-b1f0-4a2f-bcab-b3edd27a326c)

## Run via maven
`mvn clean test -Daquality.buildName="NameOfTheBuild" -Daquality.token=TRACKING_API_TOKEN -Daquality.suiteName="@tier1/@tier2/@tier3" -Daquality.environment="ios/Android" -Dcredentials.LYRASIS.${{secrets.BookCard }}=${{secrets.BookPin }} -Dcredentials.LYRASIS.${{secrets.BookCardLyrasis2 }}=${{secrets.BookPinLyrasis2 }} -Dcredentials."The New York Public Library".${{secrets.BookCardNYPL }}=${{secrets.BookPinNYPL }} -DremoteConnectionUrl=RemoteUrlToTheBrowserstack -DplatformName=${{github.event.inputs.platform_name}} -DdriverSettings.${{github.event.inputs.platform_name}}.capabilities.app=${{github.event.inputs.bs_app_link}} -Dcucumber.options="--tags '${{github.event.inputs.test_tag}} and not @exclude_${{github.event.inputs.platform_name}}'"`
