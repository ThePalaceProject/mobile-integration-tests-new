package enums.epub;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TabsTocAndBookmarksEpub {

    BOOKMARKS("Bookmarks", "BOOKMARKS"),
    TOC("Contents", "TABLE OF CONTENTS");

    private final String iosValue;
    private final String androidValue;

    public String getValue() {
        return AqualityServices.getApplication().getPlatformName() == PlatformName.ANDROID ?
                androidValue
                : iosValue;
    }
}