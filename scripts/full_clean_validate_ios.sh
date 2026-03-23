#!/usr/bin/env bash

set -euo pipefail

MODE="${1:-dryrun}" # dryrun | full
DEVICE_KEY="${DEVICE_KEY:-iPhone15ProMax_17}"
TAG_EXPRESSION="${TAG_EXPRESSION:-((@smoke or @tier1 or @tier2) and not @ignore) and not @exclude_ios}"
APP_ID="${APP_ID:-${BS_APP_ID:-}}"
FEED_BOOTSTRAP_TIMEOUT_SECONDS="${FEED_BOOTSTRAP_TIMEOUT_SECONDS:-180}"

JAVA_HOME_DEFAULT="/opt/homebrew/opt/openjdk@11/libexec/openjdk.jdk/Contents/Home"
if [[ ( -z "${JAVA_HOME:-}" || ! -x "${JAVA_HOME:-}/bin/javac" ) && -d "$JAVA_HOME_DEFAULT" ]]; then
  export JAVA_HOME="$JAVA_HOME_DEFAULT"
fi

if [[ -z "${JAVA_HOME:-}" || ! -x "${JAVA_HOME:-}/bin/javac" ]]; then
  echo "JAVA_HOME is not set. Set JAVA_HOME to a JDK 11 path before running." >&2
  exit 1
fi

echo "==> Maven clean compile check"
mvn -q clean test-compile

echo "==> Full iOS dry-run validation for tags: $TAG_EXPRESSION"
mvn -q \
  -Dsurefire.parallel=none \
  -Dsurefire.threadCount=1 \
  -Dios.logoutMode=device_switch \
  -DskipFeedBootstrap=true \
  -DplatformName=ios \
  -Dcucumber.options="--tags '$TAG_EXPRESSION' --dry-run" \
  test \
  -Dtest=runners.TestRunner

if [[ "$MODE" == "dryrun" ]]; then
  echo "==> Dry-run mode complete."
  exit 0
fi

if [[ "$MODE" != "full" ]]; then
  echo "Unknown mode: $MODE (expected: dryrun|full)" >&2
  exit 1
fi

if [[ -z "${BROWSERSTACK_USERNAME:-}" || -z "${BROWSERSTACK_ACCESS_KEY:-}" || -z "$APP_ID" ]]; then
  echo "For full mode, set BROWSERSTACK_USERNAME, BROWSERSTACK_ACCESS_KEY, and APP_ID (or BS_APP_ID)." >&2
  exit 1
fi

echo "==> Starting real full regression on BrowserStack"
mvn clean test \
  -Dsurefire.parallel=none \
  -Dsurefire.threadCount=1 \
  -Dios.logoutMode=device_switch \
  -DskipFeedBootstrap=false \
  -DfeedBootstrapTimeoutSeconds="$FEED_BOOTSTRAP_TIMEOUT_SECONDS" \
  -Daquality.enabled=false \
  -DplatformName=ios \
  -DdriverSettings.ios.deviceKey="$DEVICE_KEY" \
  -DremoteConnectionUrl=https://hub-cloud.browserstack.com/wd/hub \
  -DdriverSettings.ios.capabilities."browserstack.user"="$BROWSERSTACK_USERNAME" \
  -DdriverSettings.ios.capabilities."browserstack.key"="$BROWSERSTACK_ACCESS_KEY" \
  -DdriverSettings.ios.capabilities.app="$APP_ID" \
  -Dcucumber.options="--tags '$TAG_EXPRESSION'" \
  -Dtest=runners.TestRunner
