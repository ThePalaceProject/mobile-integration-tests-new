# Mobile integration tests

This repository contains integration tests for the Android and iOS code of the [Palace](https://thepalaceproject.org/) application

# How to run tests

## Run via GitHub Actions
All tests run at night in BrowserStack. Runs are configured with GitHub Actions in [maven.yml file](https://github.com/ThePalaceProject/mobile-integration-tests-new/blob/main/.github/workflows/maven.yml). Test run time on each device is configured in a file that describes it using cron expressions. These devices are described in repositories for [Android](https://github.com/ThePalaceProject/android-binaries/tree/main/.github/workflows) and [iOS](https://github.com/ThePalaceProject/ios-binaries/tree/master/.github/workflows) devices. In the [Actions](https://github.com/ThePalaceProject/mobile-integration-tests-new/actions) section you could also click the **Run workflow** button and start tests configured by tiers, app version and git branch. Below attached the screenshot with Ul of the Github actions solution.

![Screenshot_2](https://github.com/ThePalaceProject/mobile-integration-tests-new/assets/33911738/b1ddcfc0-daa2-46ef-894c-ea1fbac32165)
